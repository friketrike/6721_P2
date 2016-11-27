package reversi;
import java.util.List;
// import java.util.Stack;
import java.lang.Math;

public class Negamax {

	static public int PLY_DEPTH = 3;
	private int ithChild = 1;
	private Node root;
	private Heuristic h;
	// Here's the interfacing - plug in a heuristic that implements the Heuristic 
	// interface and we're good to go, no need for the source, just the byte-code
	Heuristic blackHeuristic = new FedeHeuristic();
	Heuristic whiteHeuristic = new SimpleHeuristic();
	
	public Negamax(Board passedBoard, int turn) {
		
		root = new Node(passedBoard, turn);
		generateSubTree(root);
		double beta = Double.MAX_VALUE;
		double alpha = -Double.MAX_VALUE;
		h = (turn == GamePlay.BLACK) ? blackHeuristic : whiteHeuristic;
		System.out.print("Evaluating node: 1");
		evaluateNegamax(root, alpha, beta);
		System.out.println(' ');
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
		
		if (root.children.isEmpty()) return null;
		double bestVal = -root.children.get(0).value;
		Position bestMove = root.children.get(0).move;
		for (int i = 1; i < root.children.size(); i++){
			Node child = root.children.get(i);
			if (-child.value > bestVal) {
				bestVal = -child.value;
				bestMove = child.move;
			}
		}
		
		return bestMove;
	}

	public void evaluateNegamax(Node n, double alpha, double beta) {
		
		// evaluate all leaves first, traverse away from the root
		// then use leaves' value to decide internal nodes
		
		// TODO Stack<Node> nodes = new Stack<>(); would make this quicker
		if(!n.children.isEmpty()) {
			for (int i = 0; i < n.children.size(); i++){
				// this is just for the display to work
				int spaces = ((int)Math.log10(ithChild-1))+1;
				for (int j = 0; j < spaces; j++)
					System.out.print('\b');
				System.out.print(ithChild);
				System.out.flush();
				Node child = n.children.get(i);
				++ithChild;
				evaluateNegamax(child, -beta, -alpha); // turn into stack for speedup?
				alpha = (-child.value > alpha) 
						 ? -child.value : alpha;
				n.value = alpha;
				if (alpha >= beta) {
					break;
				}
			}
		}
		// evaluate leaves
		else {
			n.value = h.evaluateBoard(n.board, n.turn);
		}
	}
}
