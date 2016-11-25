package directions;

import reversi.Position;

public interface MvCmd {
	Position move(Position origin);
}