package reversi;

import java.util.ArrayList;

public class AI_medium extends AI_easy {

	public static void go(int color) {
		Position goodPos;

		// when it is about to finish, don't consider weight and mobility but
		// the most chess pieces
		if (getTotalStep() - getStep() + 1 <= 8) {
			updateWeight(1);
			setMobility(1);
			goodPos = minMax(8, color);
		} else {
			updateWeight(0);
			setMobility(0);
			goodPos = minMax(4, color);
		}

		goToNow();

		go(goodPos.getX(), goodPos.getY(), color);
	}

	// find the best step after trying all depth of steps
	public static Position minMax(int depth, int color) {
		int bestValue = -1000;
		Position bestMove = new Position(0, 0);

		// System.out.println("before minmax");
		// Board.printBoard();

		ArrayList<Position> possibleMove = new ArrayList<Position>(checkWhereCanMove(color));
		ArrayList<Position> historyUntilNow = new ArrayList<Position>(Board.history);

		// for(Position move : Board.history){
		// move.print();
		// }

		for (Position move : possibleMove) {

			goToNow(historyUntilNow);

			go(move.getX(), move.getY(), color, false, historyUntilNow);

			// System.out.println("try "+move);
			// Board.printBoard();

			int value = -negaMax(depth - 1, -color, new ArrayList<Position>(historyUntilNow));
			historyUntilNow.set(historyUntilNow.size() - 1, new Position(-1, -1));

			// System.out.println("value = "+value);
			if (value > bestValue) {
				bestValue = value;
				bestMove = new Position(move);
			}
		}

		return bestMove;
	}

	// A better way to design the method instead of writing two method Min and
	// Max that are almost the same
	public static int negaMax(int depth, int color, ArrayList<Position> historyUntilNow) {
		int bestValue = -1000;

		if (depth <= 0)
			return getEvaluateValue(color);

		if (!canIgo(color, historyUntilNow)) {
			if (!canIgo(-color, historyUntilNow)) {
				return getEvaluateValue(color);
			}
			return -negaMax(depth, -color, historyUntilNow);
		}

		ArrayList<Position> possibleMove = new ArrayList<Position>(checkWhereCanMove(color));

		for (Position move : possibleMove) {

			// String str = "inside for history: ";
			// for (Position pos : historyUntilNow)
			// str += pos;
			// System.out.println(str);

			goToNow(historyUntilNow);

			go(move.getX(), move.getY(), color, false, historyUntilNow);
			// str = "after go history: ";
			// for (Position pos : historyUntilNow)
			// str += pos;
			// System.out.println(str);

			// goToThis(historyUntilNow);

			int value = -negaMax(depth - 1, -color, new ArrayList<Position>(historyUntilNow));
			bestValue = value > bestValue ? value : bestValue;

			// System.out.println("for "+move+"value = "+value);
			// str = "bottom for history: ";
			// for (Position pos : historyUntilNow)
			// str += pos;
			// System.out.println(str);

			// Board.printBoard();

			historyUntilNow.set(historyUntilNow.size() - 1, new Position(-1, -1));
		}
		return bestValue;

	}

}