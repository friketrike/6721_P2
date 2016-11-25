package reversi;
import java.util.List;

public class Negamax {

	static private int PLY_DEPTH = 2;
	private Node root;
	// Here's the interfacing - plug in a heuristic that implements the Heuristic 
	// interface and we're good to go, no need for the source, just the byte-code
	Heuristic blackHeuristic = new SimpleHeuristic();
	Heuristic whiteHeuristic = new SimpleHeuristic();
	
	public Negamax(Board passedBoard, int turn) {
		
		root = new Node(passedBoard, turn);
		generateSubTree(root);
		double beta = Double.MAX_VALUE;
		double alpha = -Double.MAX_VALUE;
		evaluateNegamax(root, alpha, beta);
	}
	
	private void generateSubTree(Node parent) {
		
		List<Position> moves = parent.board.getValidMoves(parent.turn);
		for (int i = 0; i < moves.size(); i++) {
			Node child = new Node(parent, moves.get(i));
			if (child.depth < PLY_DEPTH) {
				generateSubTree(child);
			}
			parent.children.add(child);
		}
	}
	

	// method will start the minimax search.
	public Position doMiniMax() {
		
		Position bestMove = null;
		for (int i = 0; i < root.children.size(); i++){
			Node child = root.children.get(i);
			if (child.value == -root.value) bestMove = child.move;
		}
		
		return bestMove;
	}

	public void evaluateNegamax(Node n, double alpha, double beta) {
		
		// evaluate all leaves first, traverse away from the root
		// then use leaves' value to decide internal nodes
		if(!n.children.isEmpty()) { 
			for (int i = 0; i < n.children.size(); i++){
				Node child = n.children.get(i);
				evaluateNegamax(child, -beta, -alpha);
				alpha = (-child.value > alpha) 
						 ? -child.value : alpha;
				n.value = alpha;
				if (alpha > beta) {
					break;
				}
			}
		}
		// evaluate leaves, plug in appropriate heuristic per player
		else {
			Heuristic h = (root.turn == GamePlay.BLACK) ? blackHeuristic : whiteHeuristic;
			n.value = h.evaluateBoard(n.board, n.turn);
			alpha = (n.value > alpha) ? n.value : alpha;
			beta = (n.value < beta) ? n.value : beta;
		}
	}
	
//	// This is where we should plug in heuristics
//	private double evaluateBoard(Board board, int turn){
//		
//		// just use greedy piece count for now
//		// NOTE this relies on turn and pieces = {-1,1} and empty = 0
//		int count = 0;
//		for(int i = 0; i < Board.BOARD_SIZE; ++i){
//			for(int j = 0; j < Board.BOARD_SIZE; ++j){
//				count += board.getPlayerAtPos(new Position(j,i));
//			}
//		}
//		return (double)turn * count;
//	}
	
}
