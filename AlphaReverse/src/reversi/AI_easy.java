package reversi;

import java.util.ArrayList;
import java.util.Random;

public class AI_easy extends ReversiRule {

	public static void go(int color) {
		// TODO Auto-generated method stub\

		ArrayList<Position> curPossiblePos = new ArrayList<Position>();
		for(int i = 0; i < Board.possiblePos.size(); ++i)
			curPossiblePos.add(Board.possiblePos.get(i));
		
		int max = -1000000;
		
		ArrayList<Position> goodPos = new ArrayList<Position>();
		
		for(int i = 0; i < curPossiblePos.size(); ++i){
			Position pos = curPossiblePos.get(i);
			goToNow();
			go(pos.getX(), pos.getY(), color);
			
			int weightValue = getWeight(color);
			int mobilityEnemy = getMobility(-1*color);
			int value =  weightValue - mobilityEnemy + 200;
			System.out.println("for "+pos.getX()+","+pos.getY()+" weight = "+weightValue+" mobEne = "+mobilityEnemy+" value = "+value);			
			if(value > max){
				goodPos.clear();
				goodPos.add(pos);
				max = value;
			}else if(value == max){
				goodPos.add(pos);
			}
		}
		
		goToNow();

		Random rand = new Random();
		int choice = rand.nextInt(goodPos.size());
		int resultX = goodPos.get(choice).getX();
		int resultY = goodPos.get(choice).getY();
		go(resultX, resultY, color);

	}

}













