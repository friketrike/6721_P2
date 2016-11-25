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

	@Override
	public boolean equals(Object o) {
		if (((Position) o).getX() == x && ((Position) o).getY() == y)
			return true;
		return false;
	}

	public boolean isValid() {
		return(x < 8 && x >= 0 && y < 8 && y >= 0);
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
	
	// TODO, implement alphanumeric position, to string and to int (parsing might be a pain)

}
