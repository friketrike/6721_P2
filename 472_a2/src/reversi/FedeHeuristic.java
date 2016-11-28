// Comp 6721 AI, Project 2, fall 2016
// Federico O'Reilly Regueiro 40012304

package reversi;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import directions.*;

public class FedeHeuristic implements Heuristic {
	
	private static final int MAX = Board.BOARD_SIZE - 1;
	private Position[] theCorners = {new Position(0, 0), new Position(0, MAX), 
									 new Position(MAX, 0), new Position(MAX, MAX)} ; 
	
	private MvCmd[][] edges = {{new Down(), new Right()}, {new Up(), new Right()}, 
				  			   {new Down(), new Left()}, {new Up(), new Left()}};
	
	private MvCmd[][] in = {{new Down(), new Right(), new DownRight()}, {new Up(), new Right(), new UpRight()},
							{new Down(), new Left(), new DownLeft()}, {new Up(), new Left(), new UpLeft()}};
	
	// down,right ;     up, right;     down, left;     up, left;

	@Override
	public double evaluateBoard(Board board, int turn) {

		int ply = Negamax.plyDepth;
		//int remainingMoves = board.getEmptySquares().size();
		double parity = pieceParity(board, turn);
		//System.out.println("parity = " + parity);
		double mobility = mobility(board, turn);
		//System.out.println("mobility = " + mobility);
		double stability = stability(board, turn);
		//System.out.println("stability: = " + stability);
		int corners = corners(board, turn);
		//System.out.println("corners: = " + corners);
		
		// Weighting
		double par = ply * parity;// * ((64-remainingMoves)/64);
		double mob = 2 * mobility;// * ((64-remainingMoves)/64);
		double cor = 8 * corners / ply;
		double stabl = 8 * stability;
		
		return par + mob + cor + stabl;
	}
	
	// NOTE for all evaluations:
	// We have to use opposite for evaluation since we're not interested in
	// the player who's moving next but the one who moved here...
	
	private double pieceParity(Board board, int turn){
		
		int parity = 0;
		for (int i = 0; i < Board.BOARD_SIZE; ++i) {
			for (int j = 0; j < Board.BOARD_SIZE; ++j) {
				parity += board.getPlayerAtPos(new Position(j, i));
			}
		}
		return (double)parity * GamePlay.opposite(turn);
	}
	
	private double stability(Board board, int turn) {
		
		List<Position> opponentMoves = board.getValidMoves(turn);
		Set<Position> flips = new HashSet<>();
		Set<Position> stable = new HashSet<>();
		for (int i = 0; i < opponentMoves.size(); i++) {
			List<Position> temp = board.getFlips(opponentMoves.get(i), turn);
			for (int j = 0; j < temp.size(); j++) {
				flips.add(temp.get(j));
			}
		}
		
		Position pos;
		for (int i = 0; i < 4; i++) {
			pos = theCorners[i];
			do {
				Position vScanPos = pos;
				while (vScanPos.isValid() && board.getPlayerAtPos(vScanPos) == GamePlay.opposite(turn)) {
					stable.add(vScanPos);
					vScanPos = edges[i][0].move(vScanPos);
				}
				pos = edges[i][1].move(pos);
			} while (pos.isValid() && board.getPlayerAtPos(pos) == GamePlay.opposite(turn)) ;
		}
		
		int stability = -flips.size() + (3 * stable.size());
		return (double)stability;
	}
	
	private int corners(Board board, int turn) {
		
		int corners = 0;
		for (int i = 0; i < 4; i++) {
			if (board.getPlayerAtPos(theCorners[i]) == turn) {
				corners++;
			}
			else if (board.getPlayerAtPos(theCorners[i]) == 0) {
				for (int j = 0; j < 3; j++){
					Position in1 = in[i][j].move(theCorners[i]);
					Position in2 = in[i][j].move(in1);
					if (board.getPlayerAtPos(in1) == GamePlay.opposite(turn) 
							&& board.getPlayerAtPos(in2) == turn){
						corners--;
					}
				}
			}
		}
		return corners;
	}
	
	private double mobility(Board board, int turn) {
		
		int myMoves = board.getValidMoves(GamePlay.opposite(turn)).size();
		if (myMoves == 0) return -1000; // we don't like passing
		int yourMoves = board.getValidMoves(turn).size();
		double mobility = ((double)myMoves-yourMoves) / ((double)myMoves+yourMoves);
		return mobility * GamePlay.opposite(turn);	
	}

}
