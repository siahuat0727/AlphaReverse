package reversi;

import java.util.ArrayList;

public class AI_hard extends AI_medium {

	public static void go(int color) {

		Position goodPos;

		// when it is about to finish, don't consider weight and mobility but
		// consider for the most chess pieces
		if (getTotalStep() - getStep() + 1 <= 10) {
			updateWeight(1);
			goodPos = minMax(10, color);
		} else {
			updateWeight(0);
			goodPos = minMax(5, color);
		}

		goToNow();

		go(goodPos.getX(), goodPos.getY(), color);
	}

	// find the best step after trying all depth of some steps
	public static Position minMax(int depth, int color) {

		int bestValue = -1000;
		Position bestMove = new Position(0, 0);

		ArrayList<Position> possibleMove = new ArrayList<Position>(checkWhereCanMove(color));
		ArrayList<Position> historyUntilNow = new ArrayList<Position>(Board.history);

		for (Position move : possibleMove) {

			goToNow(historyUntilNow);

			go(move.getX(), move.getY(), color, false, historyUntilNow);

			int value = -alphaBeta(depth - 1, -color, new ArrayList<Position>(historyUntilNow), -100000, 100000);
			historyUntilNow.set(historyUntilNow.size() - 1, new Position(-1, -1));

			if (value > bestValue) {
				bestValue = value;
				bestMove = new Position(move);
			}
		}

		return bestMove;
	}

	// add two variable alpha and beta to avoid keeping search for step that
	// will not be a good step
	public static int alphaBeta(int depth, int color, ArrayList<Position> historyUntilNow, int alpha, int beta) {
		int bestValue = -1000;

		if (depth <= 0)
			return getEvaluateValue(color);

		if (!canIgo(color, historyUntilNow)) {
			if (!canIgo(-color, historyUntilNow)) {
				return getEvaluateValue(color);
			}
			return -alphaBeta(depth, -color, historyUntilNow, -beta, -alpha);
		}

		ArrayList<Position> possibleMove = new ArrayList<Position>(checkWhereCanMove(color));

		for (Position move : possibleMove) {

			goToNow(historyUntilNow);

			go(move.getX(), move.getY(), color, false, historyUntilNow);

			int value = -alphaBeta(depth - 1, -color, new ArrayList<Position>(historyUntilNow), -beta, -alpha);
			if (value > alpha) {
				if (value >= beta)
					return value;
				alpha = value;
			}
			bestValue = value > bestValue ? value : bestValue;

			historyUntilNow.set(historyUntilNow.size() - 1, new Position(-1, -1));
		}
		return bestValue;
	}
}