// Comp 6721 AI, Project 2, fall 2016
// Ashley Lee 26663486
// Federico O'Reilly Regueiro 40012304

package reversi;
import java.util.List;
import java.util.Scanner;
// import java.util.Stack;
import java.lang.Math;

// Negamax is like minimax except min turns are multiplied by 1, 
// simplifying implementation slightly
public class Negamax {

	// Here's the interfacing - upon instantiation (ie driver code), 
	// plug in a heuristic that implements the Heuristic 
	// interface and we're good to go, no need for the source, just the byte-code
	public static Heuristic blackHeuristic;
	public static Heuristic whiteHeuristic;
	
	public static int plyDepth;
	private int ithChild = 1;
	private Node root;
	private Heuristic h;
	
	
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
	
	// generate possible subsequent boards
	private void generateSubTree(Node parent) {
		
		List<Position> moves = parent.board.getValidMoves(parent.turn);
		for (int i = 0; i < moves.size(); i++) {
			Node child = new Node(parent, moves.get(i));
			if (child.depth < plyDepth) {
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

	// evalueate leaves and propagate values upwards
	public void evaluateNegamax(Node n, double alpha, double beta) {
		
		// evaluate all leaves first, traverse away from the root
		// then use leaves' value to decide internal nodes
		
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
	
	// helper method for instantiation
	public static void choosePly(Scanner in) {
		
		System.out.println("Choose a ply-depth, (1 to 6 works fine, 6 might crash)");
		
		boolean isValid = false;
		int input = 0;
		while (!isValid) {
			input = in.nextInt();
			if (input < 1 || input > 7) {
				System.out.println("Invalid input, try again");
			} else isValid = true;
		}
			plyDepth = input;
	}
	
	public static void chooseHeuristic(int ai, Scanner in) {
		
		String color = GamePlay.turnToString(ai);
		System.out.println("Choose a heuristic for " + color + " Ai:");
		System.out.println("1 - Simple Heuristic - piece count");
		System.out.println("2 - Ashley's Heuristic");
		System.out.println("3 - Fede's Heuristic");
		
		boolean isValid = false;
		int input = 0;
		while (!isValid) {
			input = in.nextInt();
			if (input < 1 || input > 3) {
				System.out.println("Invalid input, try again");
			} else isValid = true;
		}
		switch (input) {
			case 1:
				if (ai == GamePlay.BLACK)
					blackHeuristic = new SimpleHeuristic();
				else
					whiteHeuristic = new SimpleHeuristic();
				break;
			case 2:
				if (ai == GamePlay.BLACK)
					blackHeuristic = new PosWeightHeuristic();
				else
					whiteHeuristic = new PosWeightHeuristic();
				break;
			case 3:
				if (ai == GamePlay.BLACK)
					blackHeuristic = new FedeHeuristic();
				else
					whiteHeuristic = new FedeHeuristic();
				break;
			default:
				System.out.println("We shouldn't be here...");
		}
	}
}
