package uimain;

public class AI {

	public static void go(int color, int AI_level) {
		switch (AI_level) {
		case 1:
			AI_easy.go(color);
			break;
		case 2:
			AI_medium.go(color);
			break;
		case 3:
			AI_hard.go(color);
			break;
		}
	}

}
