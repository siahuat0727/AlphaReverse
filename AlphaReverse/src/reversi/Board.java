package reversi;

import java.util.ArrayList;

// Board 一定要被 initialize 

public class Board {

	public static final int WHITE = 1;
	public static final int BLACK = -1;
	
	// 计算子数
	public static int blackCount, whiteCount;
	
	// 棋盘大小
	public static int SIZE; 
	
	// 棋盘状况， 0表空， 1表白， -1表黑
	public static int[][] board;
	
	// 目前下棋方可以下的点， 用来作画面暗示，可透过.size 取得 总数
	public static ArrayList<Position> possiblePos;
	
	public static ArrayList<Position> history;

	public static void initialize() {
		initialize(8);
	}

	// 大小可调
	public static void initialize(int size) {
		if(size <= 4)
			SIZE = 4;
		else
			SIZE = size + (size & 1); // 偶数为佳
		
		startAgain();
		possiblePos = new ArrayList<Position>();
		history = new ArrayList<Position>();
		ReversiRule.updateWeight();
		ReversiRule.setMobility();
	}
	
	public static void startAgain(){
		board = new int[SIZE][SIZE];
		board[SIZE / 2 - 1][SIZE / 2 - 1] = board[SIZE / 2][SIZE / 2] = WHITE; // |white|black|
		board[SIZE / 2 - 1][SIZE / 2] = board[SIZE / 2][SIZE / 2 - 1] = BLACK; // |black|white|
		blackCount = 2;
		whiteCount = 2;
	}

	// for debug 
	public static void printBoard() {
		for (int i = -2; i < SIZE; ++i) {
			if (i == -2) {
				System.out.print("  y");
				for (int k = 0; k < SIZE; ++k)
					System.out.print("  " + k);
				System.out.println("");
			} else if (i == -1) {
				System.out.println("x");
			} else {
				System.out.println("");
				System.out.print(i + "  ");
				for (int j = 0; j < SIZE; ++j) {
					System.out.print("  ");
					if (board[i][j] == WHITE)
						System.out.print("o");
					else if (board[i][j] == BLACK)
						System.out.print("x");
					else
						System.out.print(" ");
				}
				System.out.println("");
			}
		}
	}
}
