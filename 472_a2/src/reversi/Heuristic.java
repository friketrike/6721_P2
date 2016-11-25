package reversi;

public interface Heuristic {
	double evaluateBoard(Board board, int turn);
}
