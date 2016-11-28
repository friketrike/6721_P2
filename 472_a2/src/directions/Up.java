// Comp 6721 AI, Project 2, fall 2016
// Ashley Lee 26663486
// Federico O'Reilly Regueiro 40012304

package directions;

import reversi.Position;

public class Up implements MvCmd {

	@Override
	public Position move(Position origin) {
		Position destination = new Position(origin.getX(), origin.getY() - 1);
		return destination;
	}
}
