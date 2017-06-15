package reversi;

import java.util.ArrayList;

public class ReversiRule {

	private static int x, y;

	private static int[][] weight;

	private static int[][] mobility;

	private static ArrayList<Position> predictPossiblePos = new ArrayList<Position>();

	// 霂瑕�＆靽砲������ossiblePos������甇孑ethod
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

	// ��color��銝隞亥粥銝�甇伐��活韏啣�� 銝�摰�摰� 閬�餈ethod
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
		int historyColor = Board.BLACK;
				
		for (Position move : history) {
			go(move.getX(), move.getY(), historyColor);
			historyColor *= -1;
		}
//		System.out.print("go to: ");
//		printHistory(history);
//		System.out.println("result after go to this : ");
//		Board.printBoard();
	}

	protected static void updateWeight() {
		updateWeight(0);
	}
	
	
	protected static void updateWeight(int n) {
		final int cornerValue = 100;
		if(n == 0){
			if(weight != null && weight[0][0] == cornerValue)
				return;
			
			weight = new int[Board.SIZE][Board.SIZE];
			
			// edge(some position will be assigned again)
			for(int i = 0; i < Board.SIZE; ++i){
				for(int j = 0; j < Board.SIZE; ++j){
					weight[i][j] = 5;
				}
			}
			
			// middle
			for(int i = 1; i < Board.SIZE-1; ++i){
				for(int j = 1; j < Board.SIZE-1; ++j){
					weight[i][j] = 1;
				}
			}
			
			// corner
			weight[0][0] = weight[0][Board.SIZE-1] = weight[Board.SIZE-1][0] = weight[Board.SIZE-1][Board.SIZE-1] = cornerValue;
			
			// around corner
			weight[0][1] = weight[0][Board.SIZE-2] = weight[1][0] = weight[1][Board.SIZE-1] = weight[Board.SIZE-2][0] = weight[Board.SIZE-2][Board.SIZE-1] = weight[Board.SIZE-1][1] = weight[Board.SIZE-1][Board.SIZE-2] = -10;
			
			// corner duijiao(?)
			weight[1][1] = weight[1][Board.SIZE-2] = weight[Board.SIZE-2][1] = weight[Board.SIZE-2][Board.SIZE-2] = -50;
				
		}else{
			for(int i = 0; i < Board.SIZE; ++i){
				for(int j = 0; j < Board.SIZE; ++j){
					weight[i][j] = 1;
				}
			}
		}
		
		
		
//		for(int i = 0; i < 8; ++i){
//			for(int j = 0; j < 8; ++j){
//				System.out.print(weight[i][j]+ "   ");
//			}
//			System.out.println("");
//		}
	}

	protected static void setMobility() {
		setMobility(0);
	}
	
	protected static void setMobility(int n) {
		final int cornerValue = 5;
		if(n == 0){
			if(mobility != null && mobility[0][0] == cornerValue)
				return;
			
			mobility = new int[Board.SIZE][Board.SIZE];
			
			// edge(some position will be assigned again)
			for(int i = 0; i < Board.SIZE; ++i){
				for(int j = 0; j < Board.SIZE; ++j){
					mobility[i][j] = 3;
				}
			}
			
			// middle
			for(int i = 1; i < Board.SIZE-1; ++i){
				for(int j = 1; j < Board.SIZE-1; ++j){
					mobility[i][j] = 2;
				}
			}
			
			// corner
			mobility[0][0] = mobility[0][Board.SIZE-1] = mobility[Board.SIZE-1][0] = mobility[Board.SIZE-1][Board.SIZE-1] = cornerValue;
			
			// around corner
			mobility[0][1] = mobility[0][Board.SIZE-2] = mobility[1][0] = mobility[1][Board.SIZE-1] = mobility[Board.SIZE-2][0] = mobility[Board.SIZE-2][Board.SIZE-1] = mobility[Board.SIZE-1][1] = mobility[Board.SIZE-1][Board.SIZE-2] = 1;
			
			// corner duijiao(?)
			mobility[1][1] = mobility[1][Board.SIZE-2] = mobility[Board.SIZE-2][1] = mobility[Board.SIZE-2][Board.SIZE-2] = 1;
				
		}else{
			for(int i = 0; i < Board.SIZE; ++i){
				for(int j = 0; j < Board.SIZE; ++j){
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

	public static void printHistory(){
		printHistory(Board.history);
	}
	
	public static void printHistory(ArrayList<Position> history){
		for(Position pos : history)
			System.out.print(pos+" ");
		System.out.println("");
	}
	
	protected static int getStep(){
		return Board.whiteCount + Board.blackCount + 1; 
	}
	
	protected static int getTotalStep(){
		return Board.SIZE * Board.SIZE;
	}

}