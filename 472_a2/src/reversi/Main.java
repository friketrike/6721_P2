package reversi;

public class Main {

	public static void main(String[] args) {
		
		// TODO strange way of invoking things, maybe as if 
		// 1 agent vs player, 2 agents, 2 players or piping input
		GamePlay game = new GamePlay();
		game.startGameAgents();
		
		// alternatively call human vs Ai (startGame()) where we could chose a color
		// or call playing a single move given a string input for a board to pipe output back...
		
		// TODO this would all require a bit of parsing, ugh, maybe tonight
		
	}
	
}
