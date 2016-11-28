

package reversi;

public class Piped {
	// TODO this is kind of the way to do it... not enough time to get it working :(
	public static void main(String[] args) {
		
		if (args.length < 2) {
			System.err.println("You must input a board string and a player");
			System.exit(-1);
		}
		
		String stringBoard = args[0];
		char color = args[1].charAt(0);
		int player;
		if (color == 'b' || color == 'B'){
			player = GamePlay.BLACK;
			Negamax.blackHeuristic = new FedeHeuristic();
		} else {
			player = GamePlay.WHITE;
			Negamax.blackHeuristic = new FedeHeuristic();
		}
		Board b = new Board(stringBoard);
		Negamax max = new Negamax(b, player);
		Position play = max.doMiniMax();
		if (play != null)
			b.doMove(play, player);
		b.printBoard(true);

	}

}
