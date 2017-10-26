package reversi;

import java.util.ArrayList;
import java.util.Random;

public class AI_easy extends ReversiRule {

	public static void go(int color) {

		ArrayList<Position> curPossiblePos = new ArrayList<Position>(Board.possiblePos);

		int max = -1000000;

		ArrayList<Position> goodPos = new ArrayList<Position>();

		for (Position pos : curPossiblePos) {
			goToNow();

			// pass a dummy arrayList =.=
			go(pos.getX(), pos.getY(), color, false, new ArrayList<Position>(Board.history)); 

			int value = getEvaluateValue(color);

//			System.out.println("for " + pos.getX() + "," + pos.getY() + " value = " + value);
			
			// if the positions are good enough, add them into goodPos
			if (value > max + 5) {
				goodPos.clear();
				goodPos.add(pos);
				max = value;
			} else if (value > max - 5) {
				goodPos.add(pos);
			}
		}

		goToNow();

		//randomly select one of good position
		Random rand = new Random();
		int choice = rand.nextInt(goodPos.size());
		int resultX = goodPos.get(choice).getX();
		int resultY = goodPos.get(choice).getY();
		go(resultX, resultY, color);

	}
}









