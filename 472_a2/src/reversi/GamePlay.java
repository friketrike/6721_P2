package reversi;
import java.util.List;
import java.util.Scanner;

public class GamePlay {

	Board board;
	int turn;
	int pass;
	
	static public int BLACK = 1;
	static public int WHITE = -1;
	
	static public int opposite(int turn) {
		return turn * -1;
	}
	
	static public String turnToString(int turn) {
		return (turn == BLACK) ? "Black" : "White";
	}

	public GamePlay() {
		
		board = new Board();
		turn = BLACK;
		pass = 0;
		
		startGameAgents();
	}
	
	// TODO Board whatchamacallit_one_move(Board b, turn? t)
	
	public void startGameAgents () {
		
		int pass = 0;
		while (pass < 2) {
			board.printBoard();
			if ( doMinimaxMove(turn) )
				pass = 0;
			else
				++pass;
			turn = opposite(turn);
		}
		
		System.out.println("No more moves available.  Game Over");
		int[] results = board.getBoardScore();
		System.out.println("Black had: " + results[0]
				+ " points and White had: " + results[1]);
	}

	public void startGame() {
		
		Scanner in = new Scanner(System.in);
		
		while (pass < 2) {
			board.printBoard();
			List<Position> moves = board.getValidMoves(turn);
			if (moves.size() > 0) {
				System.out.println("\nIt is " + turnToString(turn)
						+ "'s turn.");
				System.out.println("Enter coordinates below where you want to move");
				System.out.print("X: ");
				int x = in.nextInt();
				System.out.print("Y: ");
				int y = in.nextInt();
				Position myMove = new Position(x, y);
				if (moves.contains(myMove) && myMove.isValid()) {
					System.out.println("Valid move");
					pass = 0;
					board.doMove(myMove, turn);
					turn = opposite(turn);
					if ( doMinimaxMove(turn) )
						pass = 0;
					else
						++pass;
					turn = opposite(turn);
				} 
				else {
					System.out.println("Invalid move");
				}
			}
			else {
				System.out.println("You have nowhere to go.");
				++pass;
				turn = opposite(turn);
				if ( doMinimaxMove(turn) )
					pass = 0;
				else
					++pass;
				turn = opposite(turn);
			}
		}
		
		System.out.println("No more moves available.  Game Over");
		int[] results = board.getBoardScore();
		System.out.println("Black had: " + results[0]
				+ " points and White had: " + results[1]);
		in.close();
	}
	
	public boolean doMinimaxMove(int turn){
		Minimax max = new Minimax(turn, board);
		Position play = max.doMiniMax();
		if (play != null)
			board.doMove(max.doMiniMax(), turn);
		return (play != null);
	}

}
