// Comp 6721 AI, Project 2, fall 2016
// Ashley Lee 26663486
// Federico O'Reilly Regueiro 40012304

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
	
	
	// Potentially used for piping input in the form of a string board
	public Board(String boardString) {
		boardString = boardString.replace("(", "");
		boardString = boardString.replace(",", "");
		boardString = boardString.replace(")", "");
		boardString = boardString.replaceAll("//s+", "");
		int squares = Board.BOARD_SIZE * Board.BOARD_SIZE;
		assert(boardString.length() == squares);
		for (int i = 0; i < squares; i++) {
			int row = i / Board.BOARD_SIZE;
			int col = i % Board.BOARD_SIZE;
			board[col][row] = charToToken(boardString.charAt(i));
		}
	}
	
	private int charToToken(char c) {
		int token = 0;
		if (c == 'B') token = GamePlay.BLACK;
		else if (c == 'W') token = GamePlay.WHITE;
		return token;
	}

	// Place token on the board
	public void doMove(Position pos, int turn) {
		
		List<Position> flips = getFlips(pos, turn);
		doFlips(flips);
		setPlayerAtPos(pos, turn);
	}

	// flip pieces when 'captured'
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

	// tally end results
	public int[] getBoardScore() {
		
		int[] out = new int[2];
		for (int i = 0; i < BOARD_SIZE; ++i) {
			for (int j = 0; j < BOARD_SIZE; ++j) {
				if (board[j][i] == GamePlay.BLACK) out[0]++;
				else if (board[j][i] == GamePlay.WHITE) out[1]++;
			}
		}
		return out;
	}

	// Where can we move next?
	public List<Position> getValidMoves(int turn) {
		
		List<Position> pos = getEmptySquares(); 
		List<Position> validMoves = new ArrayList<>();

		for (int i = 0; i < pos.size(); ++i) {
			for (int j = 0; j < directions.length; ++j) {
				Position newPos = directions[j].move(pos.get(i));
				if (isDirValid(newPos, directions[j], turn) && !validMoves.contains(pos.get(i)))
					validMoves.add(pos.get(i));
			}
		}

		return validMoves;
	}
	
	// Find positions where we'd need to flip upon token placement
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

	// Will there be flips along this line?
	public boolean isDirValid(Position curPos, MvCmd dir, int turn) {
		
		Position newPos = curPos;
		
		while (newPos.isValid() && getPlayerAtPos(newPos) == GamePlay.opposite(turn)) {
			newPos = dir.move(newPos);
		}
		
		return (newPos.isValid() && newPos != curPos && getPlayerAtPos(newPos) == turn);
	}

	// Get all empty squares on the board
	public List<Position> getEmptySquares() {
		
		List<Position> locations = new ArrayList<>();
		for (int i = 0; i < BOARD_SIZE; ++i) {
			for (int j = 0; j < BOARD_SIZE; ++j) {
				if (board[i][j] == 0) {
					Position pos = new Position(i, j);
					locations.add(pos);
				}
			}
		}
		
		return locations;
	}
	
	// When will java get default 
	// wrapper just so we can print the board on one line too
	public void printBoard(){
		boolean oneLiner = false;
		printBoard(oneLiner);
	}

	// Prints the board
	public void printBoard(boolean oneLiner) {
		System.out.print("(");
		if (!oneLiner) System.out.println("");
		for (int i = 0; i < board.length; i++) {
			if (!oneLiner) System.out.print("    ");
			System.out.print("(");
			for (int j = 0 ; j < board.length; j++) {
				char C;
				if (board[j][i] == 0) C = '0';
				else if (board[j][i] == GamePlay.BLACK) C = 'B';
				else if (board[j][i] == GamePlay.WHITE) C = 'W';
				else C = '?';
				System.out.print(C);
				if (j < board.length - 1) System.out.print(", ");
			} 
			System.out.print(")");
			if (!oneLiner) System.out.println("");
			else System.out.print(", ");
		}
		System.out.println(")");
	}

}