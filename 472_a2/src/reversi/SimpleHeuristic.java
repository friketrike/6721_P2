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
		return (double)turn * count;
	}
}
