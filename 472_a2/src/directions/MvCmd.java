// Comp 6721 AI, Project 2, fall 2016
// Ashley Lee 26663486
// Federico O'Reilly Regueiro 40012304

package directions;

import reversi.Position;

public interface MvCmd {
	Position move(Position origin);
}