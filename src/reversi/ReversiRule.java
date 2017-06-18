package reversi;

import java.util.ArrayList;

public class ReversiRule {

	private static int x, y;

	private static int[][] weight;

	private static int[][] mobility;

//	A little bit hard to write =.=
//	private static ArrayList<Position> predictPossiblePos = new ArrayList<Position>(); // to be used in alpha-beta with history table 

	protected static boolean go(int xPos, int yPos, int color) {
		return go(xPos, yPos, color, false);
	}

	protected static boolean go(int xPos, int yPos, int color, boolean checkOnly) {
		return go(xPos, yPos, color, checkOnly, Board.history);
	}

	// save the result after go in Board.board[][]
	protected static boolean go(int xPos, int yPos, int color, boolean checkOnly, ArrayList<Position> history) {
		if (xPos < 0)
			return false;
		if (!checkOnly)
			history.set(history.size() - 1, new Position(xPos, yPos));

		int[][] posToReverse = new int[Board.SIZE][2];

		boolean canMove = false;
		if (Board.board[xPos][yPos] != 0) {
			return false;
		}
		for (int dir = 0; dir < 8; dir++) {
			x = xPos;
			y = yPos;
			int curCount = 0;
			while (true) {
				if (move(dir) == false) {
					curCount = 0;
					break;
				}
				if (Board.board[x][y] == -color) {
					curCount++;
					posToReverse[curCount][0] = x;
					posToReverse[curCount][1] = y;
				} else if (Board.board[x][y] == 0) {
					curCount = 0;
					break;
				} else {
					break;
				}
			}
			if (curCount != 0) {
				canMove = true;
				if (checkOnly)
					return true;
				if (color == Board.WHITE) {
					Board.whiteCount += curCount;
					Board.blackCount -= curCount;
				} else {
					Board.blackCount += curCount;
					Board.whiteCount -= curCount;
				}
				while (curCount > 0) {
					x = posToReverse[curCount][0];
					y = posToReverse[curCount][1];
					Board.board[x][y] *= -1;
					curCount--;
				}
			}
		}

		if (canMove) {
			Board.board[xPos][yPos] = color;
			if (color == Board.WHITE)
				Board.whiteCount++;
			else
				Board.blackCount++;
			return true;
		} else
			return false;
	}

	protected static boolean canIgo(int color) {
		return canIgo(color, Board.history);
	}

	// must be called exactly once before each step
	protected static boolean canIgo(int color, ArrayList<Position> history) {
		history.add(new Position(-1, -1));
		return IcanGo(color);
	}

	// check whether this color can go and save the result in Board.possiblePos
	protected static boolean IcanGo(int color) {
		checkWhereCanMove(color);
		return !Board.possiblePos.isEmpty();
	}

	// save the results in Board.possiblePos
	protected static ArrayList<Position> checkWhereCanMove(int color) {
		if (Board.possiblePos.isEmpty() == false)
			Board.possiblePos.clear();

		for (int x = 0; x < Board.SIZE; x++)
			for (int y = 0; y < Board.SIZE; y++)
				if (go(x, y, color, true))
					Board.possiblePos.add(new Position(x, y));

		return Board.possiblePos;
	}

	// for debug
	protected static void printWhereCanMove() {
		for (Position pos : Board.possiblePos)
			pos.print();
		System.out.println("");
	}

	private static final int[][] DIRECTION = new int[][] { { 1, 1 }, { 0, 1 }, { -1, 1 }, { -1, 0 }, { -1, -1 },
			{ 0, -1 }, { 1, -1 }, { 1, 0 } };

	private static boolean move(int dir) {

		x += DIRECTION[dir][0];
		y += DIRECTION[dir][1];

		if (x < 0 || x >= Board.SIZE || y < 0 || y >= Board.SIZE)
			return false;
		return true;
	}

	protected static void goToNow() {
		goToNow(Board.history);
	}

	// run the board from start to now(depends on history)
	protected static void goToNow(ArrayList<Position> history) {
		Board.startAgain();
		int historyColor = Board.BLACK;

		for (Position move : history) {
			go(move.getX(), move.getY(), historyColor);
			historyColor *= -1;
		}
		// System.out.print("go to: ");
		// printHistory(history);
		// System.out.println("result after go to this : ");
		// Board.printBoard();
	}

	protected static void updateWeight() {
		updateWeight(0);
	}

	// n == 0 when start( it is not that important compare to mobility )
	// n == 1 when about to finished ( find the step to get the most chess pieces )
	protected static void updateWeight(int n) {
		final int cornerValue = 100;
		if (n == 0) {
			if (weight != null && weight[0][0] == cornerValue)
				return;

			weight = new int[Board.SIZE][Board.SIZE];

			// edge(the other position will be assigned again)
			for (int i = 0; i < Board.SIZE; ++i) {
				for (int j = 0; j < Board.SIZE; ++j) {
					weight[i][j] = 5;
				}
			}

			// middle
			for (int i = 1; i < Board.SIZE - 1; ++i) {
				for (int j = 1; j < Board.SIZE - 1; ++j) {
					weight[i][j] = 1;
				}
			}

			// corner
			weight[0][0] = weight[0][Board.SIZE
					- 1] = weight[Board.SIZE - 1][0] = weight[Board.SIZE - 1][Board.SIZE - 1] = cornerValue;

			// around corner
			weight[0][1] = weight[0][Board.SIZE - 2] = weight[1][0] = weight[1][Board.SIZE
					- 1] = weight[Board.SIZE - 2][0] = weight[Board.SIZE - 2][Board.SIZE
							- 1] = weight[Board.SIZE - 1][1] = weight[Board.SIZE - 1][Board.SIZE - 2] = -10;

			// corner duijiao(?)
			weight[1][1] = weight[1][Board.SIZE
					- 2] = weight[Board.SIZE - 2][1] = weight[Board.SIZE - 2][Board.SIZE - 2] = -50;

		} else {
			if (weight != null && weight[0][0] == 1)
				return;
			for (int i = 0; i < Board.SIZE; ++i) {
				for (int j = 0; j < Board.SIZE; ++j) {
					weight[i][j] = 1;
				}
			}
		}

		// for (int i = 0; i < Board.SIZE; ++i) {
		// for (int j = 0; j < Board.SIZE; ++j) {
		// System.out.print(weight[i][j] + " ");
		// }
		// System.out.println("");
		// }
	}

	protected static void setMobility() {
		setMobility(0);
	}

	// this is very important when game is just start(n == 0)
	// no use when about to finish(n == 1)
	protected static void setMobility(int n) {
		final int cornerValue = 5;
		if (n == 0) {
			if (mobility != null && mobility[0][0] == cornerValue)
				return;

			mobility = new int[Board.SIZE][Board.SIZE];

			// edge(the other position will be assigned again)
			for (int i = 0; i < Board.SIZE; ++i) {
				for (int j = 0; j < Board.SIZE; ++j) {
					mobility[i][j] = 3;
				}
			}

			// middle
			for (int i = 1; i < Board.SIZE - 1; ++i) {
				for (int j = 1; j < Board.SIZE - 1; ++j) {
					mobility[i][j] = 2;
				}
			}

			// corner
			mobility[0][0] = mobility[0][Board.SIZE
					- 1] = mobility[Board.SIZE - 1][0] = mobility[Board.SIZE - 1][Board.SIZE - 1] = cornerValue;

			// around corner
			mobility[0][1] = mobility[0][Board.SIZE - 2] = mobility[1][0] = mobility[1][Board.SIZE
					- 1] = mobility[Board.SIZE - 2][0] = mobility[Board.SIZE - 2][Board.SIZE
							- 1] = mobility[Board.SIZE - 1][1] = mobility[Board.SIZE - 1][Board.SIZE - 2] = 1;

			// corner duijiao(?)
			mobility[1][1] = mobility[1][Board.SIZE
					- 2] = mobility[Board.SIZE - 2][1] = mobility[Board.SIZE - 2][Board.SIZE - 2] = 1;

		} else {
			if (mobility != null && mobility[0][0] == 0)
				return;
			for (int i = 0; i < Board.SIZE; ++i) {
				for (int j = 0; j < Board.SIZE; ++j) {
					mobility[i][j] = 0;
				}
			}
		}

	}

	protected static int getWeight(int color) {
		int ans = 0;
		for (int i = 0; i < Board.SIZE; ++i) {
			for (int j = 0; j < Board.SIZE; ++j) {
				if (Board.board[i][j] == color)
					ans += weight[i][j];
				else if (Board.board[i][j] == -1 * color)
					ans -= weight[i][j];
			}
		}
		// System.out.print(" weight = "+ans);
		return ans;
	}

	protected static int getMobility(int color) {
		int ans = 0;
		checkWhereCanMove(color);
		for (Position pos : Board.possiblePos) {
			ans += mobility[pos.getX()][pos.getY()];
		}

		// System.out.print(" mobility = "+ans);
		return 3 * ans; // can adjust the weight of mobility in evaluateValue
	}

	protected static int getEvaluateValue(int color) {
		int weightValue = getWeight(color);

		int mobilityEnemy = getMobility(color);

		return weightValue + mobilityEnemy;
	}

	// for debug
	protected static String getName(int color) {
		if (color == Board.WHITE)
			return "white";
		else
			return "black";
	}

	protected static void printHistory() {
		printHistory(Board.history);
	}

	protected static void printHistory(ArrayList<Position> history) {
		for (Position pos : history)
			System.out.print(pos + " ");
		System.out.println("");
	}

	protected static int getStep() {
		return Board.whiteCount + Board.blackCount + 1;
	}

	protected static int getTotalStep() {
		return Board.SIZE * Board.SIZE;
	}
	
	// support only in human v.s. AI mode
	protected static void undo() {
		if (Board.history.size() >= 3) {
			Board.history.remove(Board.history.size() - 2);
			Board.history.remove(Board.history.size() - 2);
			goToNow();
		}
	}

}