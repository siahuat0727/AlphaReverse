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
	
	protected static boolean go(int xPos, int yPos, int color, boolean checkOnly){
		return go(xPos, yPos, color, checkOnly, Board.history);
	}

	protected static boolean go(int xPos, int yPos, int color, boolean checkOnly, ArrayList<Position> history) {
		if(xPos < 0)
			return false;
		if (!checkOnly)
			history.set(history.size() - 1, new Position(xPos, yPos));

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
	public static boolean canIgo(int color){
		return canIgo(color, Board.history);
	}
	
	protected static boolean canIgo(int color, ArrayList<Position> history){
		
		history.add(new Position(-1, -1));

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
	public static void printWhereCanMove() {
		for (Position pos : Board.possiblePos)
			pos.print();
		System.out.println("");
	}

	private static final int [][] DIRECTION = new int[][]{{1, 1}, {0, 1}, {-1, 1}, {-1, 0}, {-1, -1}, {0, -1}, {1, -1}, {1, 0}};
	
	private static boolean move(int dir) {
//		if (dir == 0 || dir == 6 || dir == 7)
//			x++;
//		if (dir == 0 || dir == 1 || dir == 2)
//			y++;
//		if (dir == 2 || dir == 3 || dir == 4)
//			x--;
//		if (dir == 4 || dir == 5 || dir == 6)
//			y--;
		
		x += DIRECTION[dir][0];
		y += DIRECTION[dir][1];
		
		if (x < 0 || x >= Board.SIZE || y < 0 || y >= Board.SIZE)
			return false;
		return true;
	}
	
	protected static void goToThis(){
		goToThis(Board.history);
	}
	
	protected static void goToThis(ArrayList<Position> history){
		Board.startAgain();
		int historyColor = Board.WHITE;
		
//		String str = "history: ";
		
		for (Position move : history) {
//			str += move;
			go(move.getX(), move.getY(), historyColor);
			historyColor *= -1;
		}
//		System.out.println(str);
//		Board.printBoard();
	}

	protected static void updateWeight() {
		weight = new int[][] { { 500, -10, 5, 5, 5, 5, -10, 500 }, { -10, -50, 1, 1, 1, 1, -50, -10 },
				{ 5, 1, 1, 1, 1, 1, 1, 5 }, { 5, 1, 1, 1, 1, 1, 1, 5 }, { 5, 1, 1, 1, 1, 1, 1, 5 },
				{ 5, 1, 1, 1, 1, 1, 1, 5 }, { -10, -50, 1, 1, 1, 1, -50, -10 }, { 500, -10, 5, 5, 5, 5, -10, 500 }, };
	}

	protected static void setMobility() {
		mobility = new int[][] { { 5, 1, 3, 3, 3, 3, 1, 5 }, { 1, 1, 2, 2, 2, 2, 1, 1 }, { 3, 2, 2, 2, 2, 2, 2, 3 },
				{ 3, 2, 2, 2, 2, 2, 2, 3 }, { 3, 2, 2, 2, 2, 2, 2, 3 }, { 3, 2, 2, 2, 2, 2, 2, 3 },
				{ 1, 1, 2, 2, 2, 2, 1, 1 }, { 5, 1, 3, 3, 3, 3, 1, 5 }, };
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
//		System.out.print(" weight = "+ans);
		return ans;
	}

	protected static int getMobility(int color) {
		int ans = 0;
		checkWhereCanMove(color);
		for (Position pos : Board.possiblePos) {
			ans += mobility[pos.getX()][pos.getY()];
		}

//		System.out.print(" mobility = "+ans);
		return 2 * ans;
	}
	
	protected static int getEvaluateValue(int color){
		int weightValue = getWeight(color);
		
		int mobilityEnemy = getMobility(color);
		
		return weightValue + mobilityEnemy;
	}
	
	// for debug
	protected static String getName(int color){
		if(color == Board.WHITE)
			return "white";
		else
			return "black";
	}
	
	protected static int getStep(){
		return Board.whiteCount + Board.blackCount + 1; 
	}
	
	protected static int getTotalStep(){
		return Board.SIZE * Board.SIZE;
	}

}
