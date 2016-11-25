package reversi;
import java.util.ArrayList;
import java.util.List;

public class Node {

	public int depth;
	public double value = -Double.MAX_VALUE;
	public Board board;
	public Node parent;
	public List<Node> children = new ArrayList<Node>(); 
	public Position move;
	public int turn;
	
	// root node ctor
	public Node(Board board, int turn) {
		this.board = board;
		this.turn = turn;
		parent = null;
		move = null;
	}
	
	// subtree constructor 
	public Node(Node parent, Position move) { 
		this.parent = parent;
		this.move = move;
		depth = parent.depth + 1; 
		board = new Board(parent.board);
		board.doMove(move, parent.turn);
		turn = GamePlay.opposite(parent.turn);
	}
	
	public void addChild(Node child) {
		
		children.add(child);
	}
	
}
