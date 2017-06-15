package uimain;

import java.util.ArrayList;

// Board 憡����蕩憿抬蜀���蕭 initialize 

public class Board {

	public static final int WHITE = 1;
	public static final int BLACK = -1;
	
	// �����嚙賣���蕭
	public static int blackCount, whiteCount;
	
	// 瞈∵��迆敹��嚙�
	public static int SIZE; 
	
	// 瞈∵��迆敹����瘨單 0��������蕭 1�������蕭 -1����赫嚙�
	public static int[][] board;
	
	// ����儒�蝔���璉���什�������憳桅����蕭 ����釆��������憡蝎�站蝷甈����遙嚙�.size �甈�辣嚙� ���盔��蕭
	public static ArrayList<Position> possiblePos;
	
	public static int nTurn;
	
	public static ArrayList<Position> history;

	public static void initialize() {
		initialize(8);
	}

	// 憍Ｗ�瘥甈�嚙�
	public static void initialize(int size) {
		if(size <= 4)
			SIZE = 4;
		else
			SIZE = size + (size & 1); // �戭剖��蝔�撠�
		
		board = new int[SIZE][SIZE];
		board[SIZE / 2 - 1][SIZE / 2 - 1] = board[SIZE / 2][SIZE / 2] = WHITE; // |��憪���
		board[SIZE / 2 - 1][SIZE / 2] = board[SIZE / 2][SIZE / 2 - 1] = BLACK; // |憪���
		nTurn = 0;
		blackCount = 2;
		whiteCount = 2;
		possiblePos = new ArrayList<Position>();
		history = new ArrayList<Position>();
		ReversiRule.updateWeight();
		ReversiRule.setMobility();
	}
	
	public static void startAgain(){
		blackCount = 2;
		whiteCount = 2;
		board = new int[SIZE][SIZE];
		board[SIZE / 2 - 1][SIZE / 2 - 1] = board[SIZE / 2][SIZE / 2] = WHITE; // |��憪���
		board[SIZE / 2 - 1][SIZE / 2] = board[SIZE / 2][SIZE / 2 - 1] = BLACK; // |憪���
	}

	// debug ���
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
						System.out.print("0");
					else if (board[i][j] == BLACK)
						System.out.print("X");
					else
						System.out.print(" ");
				}
				System.out.println("");
			}
		}
	}
}
