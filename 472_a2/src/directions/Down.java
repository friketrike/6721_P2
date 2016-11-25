package directions;

import reversi.Position;

public class Down implements MvCmd {

	@Override
	public Position move(Position origin) {
		Position destination = new Position(origin.getX(), origin.getY() + 1);
		return destination;
	}

}