// Comp 6721 AI, Project 2, fall 2016
// Ashley Lee 26663486
// Federico O'Reilly Regueiro 40012304

package reversi;

// interface allowing us to plug in respective heuristics
public interface Heuristic {
	double evaluateBoard(Board board, int turn);
}
