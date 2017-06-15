package reversi;

import java.util.Scanner;

public class Test {

	public static void main(String[] args) {

		Board.initialize(8);

		Board.printBoard();

		Scanner scanner = new Scanner(System.in);

		int AI_level = 1;
		int AI_level_black = 2;
		int AI_level_white = 3;

		int noMove = 0; // 连续两次 noMove 是游戏结束的唯一依据
		
		int AIcolor = Board.WHITE; // can be changed
		
		int curColor = Board.BLACK;
		
		boolean AI_vs_AI = true;

		System.out.println(" start game, press enter to continue......");
		scanner.nextLine();
		
		while (noMove < 2) {

			if (ReversiRule.canIgo(curColor) == false) {
				noMove++;
				System.out.println(ReversiRule.getName(curColor)+" can't move, press enter to continue......");
				scanner.nextLine();
			} else {
				noMove = 0;
				System.out.print(ReversiRule.getName(curColor)+" can move at ");
				ReversiRule.printWhereCanMove();
				
				if (!AI_vs_AI && curColor != AIcolor){
					int dx,dy;
				System.out.print("Please enter the position to move (ie. x y): ");
					dx = scanner.nextInt();
					dy = scanner.nextInt();
					ReversiRule.go(dx, dy, curColor);
				}else
					AI.go(curColor, curColor == Board.BLACK ? 3 : 2);
				
				System.out.print(ReversiRule.getName(curColor)+" move at ");
				System.out.println(Board.history.get(Board.history.size()-1));
				//ReversiRule.printHistory();
				Board.printBoard();

				System.out.println("white " + Board.whiteCount + " vs black " + Board.blackCount);
				System.out.println(ReversiRule.getName(curColor)+" finished, press enter to continue......");
				scanner.nextLine();
			}
			curColor = -curColor;
		}

		int diff = Board.whiteCount - Board.blackCount;
		System.out.println("white " + Board.whiteCount + " vs black " + Board.blackCount);
		System.out.println(diff > 0 ? "white WIN !!" : diff < 0 ? "black WIN !!" : "TIE !!");

		scanner.close();
	}
}
