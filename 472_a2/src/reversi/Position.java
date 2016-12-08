// Comp 6721 AI, Project 2, fall 2016
// Ashley Lee 26663486
// Federico O'Reilly Regueiro 40012304

package reversi;
public class Position {
	int x;
	int y;

	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Position(Position pos){
		x = pos.x;
		y = pos.y;
	}
	
	// takes a coordinate position of the form [a-h][1-8]
	public Position(String standard) {
		this.x = (int)(standard.charAt(0) - 'a');
		this.y = (int)(standard.charAt(1) - '1');
	}

	@Override
	public boolean equals(Object o) {
		if (((Position) o).getX() == x && ((Position) o).getY() == y)
			return true;
		return false;
	}
	
	@Override
	public int hashCode() {
	    return 101 * x + y;
	}

	public boolean isValid() {
		return(x < Board.BOARD_SIZE && x >= 0 && y < Board.BOARD_SIZE && y >= 0);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public String toString() {
		char xCoord = (char) ('a'+x);
		String coord = "" + xCoord + (y+1);
		return coord;
	}
}
