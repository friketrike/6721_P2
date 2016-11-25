package directions;

import reversi.Position;

public class UpRight implements MvCmd {

	@Override
	public Position move(Position origin) {
		Position destination = new Position(origin.getX() + 1, origin.getY() - 1);
		return destination;
	}

}