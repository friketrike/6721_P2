// Comp 6721 AI, Project 2, fall 2016
// Ashley Lee 26663486
// Federico O'Reilly Regueiro 40012304

package reversi;
import java.util.List;
import java.util.Scanner;

public class GamePlay {

	Board board;
	int turn;
	int pass;
	
	static final public int BLACK = 1;
	static final public int WHITE = -1;
	
	static final public int opposite(int turn) {
		return turn * -1;
	}
	
	static public String turnToString(int turn) {
		return (turn == BLACK) ? "Black" : "White";
	}

	public GamePlay() {
		
		board = new Board();
		turn = BLACK;
		pass = 0;
	}
	
	// TODO Board whatchamacallit_one_move(Board b, turn? t)
	// think of a nice way of calling gamePlay for a single turn
	
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

	public void startInteractiveGame(int humanColor, Scanner in) {
		
		if (humanColor == WHITE){
			board.printBoard();
			if ( doMinimaxMove(turn) )
				pass = 0;
			else
				++pass;
			turn = opposite(turn);
		}
			
		while (pass < 2) {
			board.printBoard();
			List<Position> moves = board.getValidMoves(turn);
			if (moves.size() > 0) {
				System.out.println("\nIt's " + turnToString(turn)
						+ "'s turn, available moves:");
				for (int i = 0; i < moves.size(); i++) {
					System.out.print((i+1) + " - " + moves.get(i).toString() + ", ");
				}
				System.out.println("Enter a choice");
				boolean isValid = false;
				int move = 0;
				while (!isValid) {
					move = in.nextInt();
					if (move < 1 || move > moves.size()) {
						System.out.println("Invalid input, try again");
					} else isValid = true;
				}
				
				Position myMove = moves.get(move-1);
				pass = 0;
				board.doMove(myMove, turn);
				board.printBoard();
				turn = opposite(turn);
				if ( doMinimaxMove(turn) )
					pass = 0;
				else
					++pass;
				turn = opposite(turn);
				
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
	}
	
	// Call the Ai
	public boolean doMinimaxMove(int turn){
		Negamax max = new Negamax(board, turn);
		Position play = max.doMiniMax();
		if (play != null)
			board.doMove(play, turn);
		return (play != null);
	}

}
