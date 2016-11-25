package reversi;
import java.util.ArrayList;
import java.util.List;

public class Minimax {

	int turn;
	Node minimaxTree;

	public Minimax(int turn, Board passedBoard) {
		
		this.turn = turn;
		Position position = new Position(0, 0);
		minimaxTree = new Node(position);
		minimaxTree.board = passedBoard;
		}

	// method will start the minimax search.
	public Position doMiniMax() {
		
		List<Position> pos = minimaxTree.board.getValidMoves(turn);
		if (!pos.isEmpty())
			return pos.get(0);
		else
			return null;
		
		//generateTreeWrap(turn);
		//return evaluateTreeWrapper(minimaxTree);
	}

	public void generateTreeWrap(int turn) {
		
		minimaxTree.height = 0;
		minimaxTree.turn = turn;
		minimaxTree.isMax = true;
		minimaxTree.children = new ArrayList<Node>();
		
		Node tree = genTree(minimaxTree);
		minimaxTree = tree;
	}
	
	public Node genTree(Node node){
		
		if(node.height<=2){
			if(node.height%2==0 && node.height>1){
				node.turn = GamePlay.opposite(node.turn);
			}
			
			if(node.height%2!=0){
				node.turn = GamePlay.opposite(node.turn);
			}
			
			List<Position> posMoves = node.board.getValidMoves(node.turn);
			for(int i=0; i<posMoves.size(); ++i){
				Node nextNode = new Node(posMoves.get(i));
				nextNode.board = node.board;
				nextNode.turn = node.turn;
				nextNode.height = node.height + 1;
				Board nodeBoard = new Board();
				
				
				nodeBoard = new Board(node.board);
				nodeBoard.doMove(new Position(nextNode.move), (nextNode.turn));
				nextNode.board = nodeBoard;
				nextNode.children = new ArrayList<Node>();
				Node kidNode = genTree(nextNode);
				node.children.add(kidNode);
			}
			return node;
		}
		
		return node;
	}
	

	public Position evaluateTreeWrapper(Node node){
		
		node = evaluate(node);
		for(int i=0; i<node.children.size(); ++i){
			if(node.children.get(i).value==node.value){
				return node.children.get(i).move;
			}
		}
		return null;
	}
	
	public Node evaluate(Node node){
		
		if(!node.children.isEmpty()){
			int[] scores = new int[node.children.size()];
			for(int i=0; i<node.children.size(); ++i){
				Node evalNode = evaluate(node.children.get(i));
				scores[i]=evalNode.value;
			}
			
			if(node.isMax){
				
				int max = scores[0];
				for ( int j = 1; j < scores.length; ++j) {
				    if ( scores[j] > max) {
				    	max = scores[j];
				    }
				}
				    node.value=max;
				    return node;
			}
			
			else if(!node.isMax) {
				int min = scores[0];
				for ( int i = 1; i < scores.length; ++i) {
				    if ( scores[i] < min) {
				    	min = scores[i];
				    }
				}
				node.value=min;
				return node;
			}
		} 
		else {
			int idx = (turn == GamePlay.BLACK) ? 0 : 1;
			node.value = node.board.getBoardScore()[idx];
			return node;
		}

		return null;
	}
	
}
