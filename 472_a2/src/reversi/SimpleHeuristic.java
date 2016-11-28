// Comp 6721 AI, Project 2, fall 2016
// Ashley Lee 26663486
// Federico O'Reilly Regueiro 40012304

package reversi;

public class SimpleHeuristic implements Heuristic {

	@Override
	public double evaluateBoard(Board board, int turn) {
		int count = 0;
		for(int i = 0; i < Board.BOARD_SIZE; ++i){
			for(int j = 0; j < Board.BOARD_SIZE; ++j){
				count += board.getPlayerAtPos(new Position(j,i));
			}
		}
		// We have to use opposite for evaluation since we're not interested in
		// the player who's moving next but the one who moved here...
		return (double)count * GamePlay.opposite(turn);
	}
}
