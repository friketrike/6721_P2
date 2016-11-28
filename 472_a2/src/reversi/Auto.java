// Comp 6721 AI, Project 2, fall 2016
// Ashley Lee 26663486
// Federico O'Reilly Regueiro 40012304

package reversi;

import java.util.Scanner;

public class Auto {

	public static void main(String[] args) {
		
		System.out.println("Automatic match between two Ais"); 
		
		Scanner in = new Scanner(System.in);
		Negamax.chooseHeuristic(GamePlay.BLACK, in);
		Negamax.chooseHeuristic(GamePlay.WHITE, in);
		
		Negamax.choosePly(in);
		in.close();
		
		GamePlay game = new GamePlay();
		game.startGameAgents();
	}
}
