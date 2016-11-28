// Comp 6721 AI, Project 2, fall 2016
// Ashley Lee 26663486
// Federico O'Reilly Regueiro 40012304

package reversi;

import java.util.Scanner;

public class Interactive {

	public static void main(String[] args) {
		
		System.out.println("Interactive match between a human and an Ai"); 
		
		Scanner in = new Scanner(System.in);
		int humanColor = chooseHumanColor(in);
		
		int ai = GamePlay.opposite(humanColor);
		
		Negamax.chooseHeuristic(ai, in);
		
		Negamax.choosePly(in);
		
		GamePlay game = new GamePlay();
		game.startInteractiveGame(humanColor, in);
		
		in.close();
	}
	
	private static int chooseHumanColor(Scanner in) {

		
		System.out.println("Choose a color for the human player, 1 - Black, 2 - White");
		
		boolean isValid = false;
		int input = 0;
		while (!isValid) {
			input = in.nextInt();
			if (input < 1 || input > 2) {
				System.out.println("Invalid input, try again");
			} else isValid = true;
		}
		
		int color = 0;
		switch (input) {
			case 1:
				color = GamePlay.BLACK;
				break;
			case 2:
				color = GamePlay.WHITE;
				break;
			default:
				System.out.println("Oops!");
				break;
		}
		return color;
	}

}
