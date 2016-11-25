package reversi;
import java.util.ArrayList;
import java.util.List;

import directions.*;

public class Board {

	private int board[][] = new int[8][8];
	public static List<MvCmd> directions = new ArrayList<MvCmd>();

	// 
	public Board() {
		
		initializeDirections();
		
		board[3][4] = GamePlay.WHITE;
		board[4][3] = GamePlay.WHITE;
		board[3][3] = GamePlay.BLACK;
		board[4][4] = GamePlay.BLACK;
	}
	
	public Board(Board anotherBoard) {
		
		initializeDirections();
		
		for(int i =0; i<anotherBoard.board.length; ++i){
			for(int j=0; j<anotherBoard.board.length; ++j){
				board[i][j]=anotherBoard.board[i][j];
			}
		}
	}
	
	// TODO constructor which makes a board from a string so that we can play against another agent
	
	private void initializeDirections() {
		directions.add(new Up());
		directions.add(new Left());
		directions.add(new UpLeft());
		directions.add(new UpRight());
		directions.add(new DownRight());
		directions.add(new DownLeft());
		directions.add(new Right());
		directions.add(new Down());
	}

	public void doMove(Position pos, int turn) {
		
		List<Position> flips = getFlips(pos, turn);
		doFlips(flips);
		setPlayerAtPos(pos, turn);
	}

	public void doFlips(List<Position> flips) {
		
		for (int i = 0; i < flips.size(); ++i) {
			setPlayerAtPos(flips.get(i), 
					GamePlay.opposite(getPlayerAtPos(flips.get(i))));
		}
	}

	public int getPlayerAtPos(Position pos) {
		
		return board[pos.getX()][pos.getY()];
	}
	
	public void setPlayerAtPos(Position pos, int turn) {
		
		board[pos.getX()][pos.getY()] = turn;
	}

	public int[] getBoardScore() {
		
		int[] out = new int[2];
		for (int i = 0; i < 8; ++i) {
			for (int j = 0; j < 8; ++j) {
				if (board[j][i] == GamePlay.BLACK) out[0]++;
				else if (board[j][i] == GamePlay.WHITE) out[1]++;
			}
		}
		return out;
	}

	public List<Position> getValidMoves(int turn) {
		
		List<Position> pos = getEmptySquares(); 
		List<Position> results = new ArrayList<Position>();

		for (int i = 0; i < pos.size(); ++i) {
			for (int j = 0; j < directions.size(); ++j) {
				Position newPos = directions.get(j).move(pos.get(i));
				if (isDirValid(newPos, directions.get(j), turn))
					results.add(pos.get(i));
			}
		}

		return results;
	}
	
	public List<Position> getFlips(Position pos, int turn) {
		
		List<Position> results = new ArrayList<Position>();

		for (int j = 0; j < directions.size(); ++j) {
			Position newPos = directions.get(j).move(pos);
			while (isDirValid(newPos, directions.get(j), turn)) {
				results.add(newPos);
				newPos = directions.get(j).move(newPos);
			}
		}

		return results;
	}

	public boolean isDirValid(Position curPos, MvCmd dir, int turn) {
		
		Position newPos = curPos;
		
		while (newPos.isValid() && getPlayerAtPos(newPos) == GamePlay.opposite(turn)) {
			newPos = dir.move(newPos);
		}
		
		return (newPos.isValid() && newPos != curPos && getPlayerAtPos(newPos) == turn);
	}

	public List<Position> getEmptySquares() {
		
		List<Position> locations = new ArrayList<Position>();
		for (int i = 0; i < 8; ++i) {
			for (int j = 0; j < 8; ++j) {
				if (board[i][j] == 0) {
					Position pos = new Position(i, j);
					locations.add(pos);
				}
			}
		}
		
		return locations;
	}

	// Prints the board
	public void printBoard() {
		System.out.println("(");
		for (int i = 0; i < board.length; i++) {
			System.out.print("    (");
			for (int j = 0 ; j < board.length; j++) {
				char C;
				if (board[j][i] == 0) C = '0';
				else if (board[j][i] == GamePlay.BLACK) C = 'B';
				else if (board[j][i] == GamePlay.WHITE) C = 'W';
				else C = '?';
				System.out.print(C);
				if (j < board.length - 1) System.out.print(", ");
			} 
			System.out.println(")");
		}
		System.out.println(")");
	}

}