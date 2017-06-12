package reversi;

import java.util.Scanner;

public class Test {

	public static void mmain(String[] args) {

		// 初始化棋盘
		Board.initialize(8);

		Board.printBoard();

		Scanner scanner = new Scanner(System.in);

		int AI_level = 1;

		int noMove = 0; // 连续两次 noMove 是游戏结束的唯一依据
		
		boolean AI_VS_AI = true;

		while (AI_VS_AI) {
			if (noMove == 2)
				break;

			if (ReversiRule.canIgo(Board.WHITE) == false) {
				noMove++;
				System.out.println("white can't move, press enter to continue......");
				scanner.nextLine();
			} else {
				noMove = 0;
//				System.out.println("white turn :");
				System.out.print("white can move at ");
				ReversiRule.printWhereCanMove();
//				System.out.println("press enter to continue......");
//				scanner.nextLine();
				
				AI.go(Board.WHITE, 2);
				Board.printBoard();

				System.out.println("white " + Board.whiteCount + " vs black " + Board.blackCount);
				System.out.print("white move at ");
				Board.history.get(Board.history.size()-1).print();
				System.out.println("");
				System.out.println("white finished, press enter to continue......");
				scanner.nextLine();
			}
			
			if (noMove == 2)
				break;

			if (ReversiRule.canIgo(Board.BLACK) == false) {
				noMove++;
				System.out.println("black can't move, press enter to continue......");
				scanner.nextLine();
			} else {
				noMove = 0;
//				System.out.println("black turn :");
				System.out.print("black can move at ");
				ReversiRule.printWhereCanMove();
//				System.out.println("press enter to continue......");
//				scanner.nextLine();
				
				AI.go(Board.BLACK, 3);
				
				Board.printBoard();

				System.out.println("white " + Board.whiteCount + " vs black " + Board.blackCount);
				System.out.print("black move at ");
				Board.history.get(Board.history.size()-1).print();
				System.out.println("");
				System.out.println("black finished, press enter to continue......");
				scanner.nextLine();
			}
			
		}

		int diff = Board.whiteCount - Board.blackCount;
		System.out.println("white " + Board.whiteCount + " vs black " + Board.blackCount);
		System.out.println(diff > 0 ? "white WIN !!" : diff < 0 ? "black WIN !!" : "TIE !!");

		scanner.close();
	}
}
