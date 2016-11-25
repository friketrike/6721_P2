package reversi;
import java.util.ArrayList;
import java.util.List;

import directions.*;

public class Board {

	public static int BOARD_SIZE = 8;
	private int board[][];
	public static MvCmd [] directions = {new Up(), new Left(), new UpLeft(), new UpRight(),
								new DownLeft(), new DownRight(), new Right(), new Down()};

	// Default ctor
	public Board() {
		
		assert(BOARD_SIZE % 2 == 0);
		board = new int[BOARD_SIZE][BOARD_SIZE];
		
		int s = BOARD_SIZE / 2;
		board[s-1][s] = GamePlay.BLACK;
		board[s][s-1] = GamePlay.BLACK;
		board[s-1][s-1] = GamePlay.WHITE;
		board[s][s] = GamePlay.WHITE;
	}
	
	// Copy ctor
	public Board(Board anotherBoard) {
		
		assert(BOARD_SIZE % 2 == 0);
		board = new int[BOARD_SIZE][BOARD_SIZE];
		
		for(int i = 0; i < BOARD_SIZE; ++i){
			for(int j = 0; j < BOARD_SIZE; ++j){
				board[i][j]=anotherBoard.board[i][j];
			}
		}
	}
	
	// TODO constructor which makes a board from a string so that we can play against another agent

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

	// lighten up the look of the code by removing indexing
	public int getPlayerAtPos(Position pos) {
		
		return board[pos.getX()][pos.getY()];
	}
	
	// lighten up the look of the code by removing indexing
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
		List<Position> validMoves = new ArrayList<>();

		for (int i = 0; i < pos.size(); ++i) {
			for (int j = 0; j < directions.length; ++j) {
				Position newPos = directions[j].move(pos.get(i));
				if (isDirValid(newPos, directions[j], turn))
					validMoves.add(pos.get(i));
			}
		}

		return validMoves;
	}
	
	public List<Position> getFlips(Position pos, int turn) {
		
		List<Position> results = new ArrayList<>();

		for (int j = 0; j < directions.length; ++j) {
			Position newPos = directions[j].move(pos);
			while (isDirValid(newPos, directions[j], turn)) {
				results.add(newPos);
				newPos = directions[j].move(newPos);
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
		
		List<Position> locations = new ArrayList<>();
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