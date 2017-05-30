package reversi;

import java.util.ArrayList;

public class ReversiRule {

	private static int x, y;

	private static int[][] weight;

	private static int[][] mobility;

	private static ArrayList<Position> predictPossiblePos = new ArrayList<Position>();

	// 请先确保该点是在目前possiblePos里面的才呼叫此method
	public static boolean go(int xPos, int yPos, int color) {
		return go(xPos, yPos, color, false);
	}

	private static boolean go(int xPos, int yPos, int color, boolean checkOnly) {
		if(xPos < 0)
			return false;
		if (!checkOnly)
			Board.history.set(Board.nTurn - 1, new Position(xPos, yPos));

		int[][] posToReverse = new int[Board.SIZE][2];
		
		boolean canMove = false;
		if (Board.board[xPos][yPos] != 0)
			return false;
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

	// 判断color方可不可以走下一步，每次走前 一定一定 要呼叫这method
	public static boolean canIgo(int color) {
		Board.nTurn++;
		Board.history.add(new Position(-1, -1));

		checkWhereCanMove(color);
		return !Board.possiblePos.isEmpty();
	}

	// save the results in Board.possiblePos
	private static boolean checkWhereCanMove(int color) {

		if (Board.possiblePos.isEmpty() == false)
			Board.possiblePos.clear();

		for (int x = 0; x < Board.SIZE; x++)
			for (int y = 0; y < Board.SIZE; y++)
				if (go(x, y, color, true))
					Board.possiblePos.add(new Position(x, y));

		return !Board.possiblePos.isEmpty();
	}

	// debug 用
	public static void printWhereCanMove() {
		for (int i = 0; i < Board.possiblePos.size(); ++i)
			System.out.print("( " + Board.possiblePos.get(i).getX() + "," + Board.possiblePos.get(i).getY() + " ) ");
		System.out.println("");
	}

	private static boolean move(int dir) {
		if (dir == 0 || dir == 6 || dir == 7)
			x++;
		if (dir == 0 || dir == 1 || dir == 2)
			y++;
		if (dir == 2 || dir == 3 || dir == 4)
			x--;
		if (dir == 4 || dir == 5 || dir == 6)
			y--;
		if (x < 0 || x >= Board.SIZE || y < 0 || y >= Board.SIZE)
			return false;
		return true;
	}
	
	public static void goToNow(){
		Board.startAgain();
		int historyColor = Board.WHITE;
		for (int j = 0; j < Board.nTurn-1; ++j) {
//			System.out.println("for "+j);
			Position pos = Board.history.get(j);
			go(pos.getX(), pos.getY(), historyColor);
			historyColor *= -1;
		}
	}

	public static void updateWeight() {
		weight = new int[][] { { 50, -50, 5, 5, 5, 5, -50, 50 }, { -50, -50, 1, 1, 1, 1, -50, -50 },
				{ 5, 1, 1, 1, 1, 1, 1, 5 }, { 5, 1, 1, 1, 1, 1, 1, 5 }, { 5, 1, 1, 1, 1, 1, 1, 5 },
				{ 5, 1, 1, 1, 1, 1, 1, 5 }, { -50, -50, 1, 1, 1, 1, -50, -50 }, { 50, -50, 5, 5, 5, 5, -50, 50 }, };
	}

	public static void setMobility() {
		mobility = new int[][] { { 5, 1, 3, 3, 3, 3, 1, 5 }, { 1, 1, 2, 2, 2, 2, 1, 1 }, { 3, 2, 2, 2, 2, 2, 2, 3 },
				{ 3, 2, 2, 2, 2, 2, 2, 3 }, { 3, 2, 2, 2, 2, 2, 2, 3 }, { 3, 2, 2, 2, 2, 2, 2, 3 },
				{ 1, 1, 2, 2, 2, 2, 1, 1 }, { 5, 1, 3, 3, 3, 3, 1, 5 }, };
	}

	public static int getWeight(int color) {
		int ans = 0;
		for (int i = 0; i < Board.SIZE; ++i) {
			for (int j = 0; j < Board.SIZE; ++j) {
				if (Board.board[i][j] == color)
					ans += weight[i][j];
				else if (Board.board[i][j] == -1 * color)
					ans -= weight[i][j];
			}
		}
		return ans;
	}

	public static int getMobility(int color) {
		int ans = 0;
		checkWhereCanMove(color);
		for (int i = 0; i < Board.possiblePos.size(); ++i) {
			Position pos = Board.possiblePos.get(i);
			ans += mobility[pos.getX()][pos.getY()];
		}
		return 5 * ans;
	}
	
	public static String getName(int color){
		if(color == Board.WHITE)
			return "white";
		else
			return "black";
	}

}
