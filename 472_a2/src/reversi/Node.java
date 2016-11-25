package reversi;
import java.util.List;

public class Node {

	public int height;
	public int value;
	public Board board;
	public Board secBoard; // what's this?
	public List<Node> children;
	public double alpha;
	public double beta;
	public Position move;
	public int turn;
	public boolean isMax;

	public Node(Position move){
		this.move=move;
	}
	
	public Node(Node otherNode){
		
	}


}
