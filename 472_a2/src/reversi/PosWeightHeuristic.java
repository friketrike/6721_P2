package reversi;

public class PosWeightHeuristic implements Heuristic {
	
	@Override
	public double evaluateBoard(Board board, int turn) {
		
		int count = 0;
		
		// hardcoding the weights
		int[][] posWeight=new int[8][8];
		posWeight[0][0]=posWeight[0][7]=posWeight[7][0]=posWeight[7][7]=99;
		posWeight[0][1]=posWeight[0][6]=posWeight[1][0]=posWeight[1][7]=posWeight[6][0]=posWeight[6][7]=posWeight[7][1]=posWeight[7][6]=-8;
		posWeight[0][2]=posWeight[0][5]=posWeight[2][0]=posWeight[2][7]=posWeight[5][0]=posWeight[5][7]=posWeight[7][2]=posWeight[7][5]=8;
		posWeight[0][3]=posWeight[0][4]=posWeight[3][0]=posWeight[3][7]=posWeight[4][0]=posWeight[4][7]=posWeight[7][3]=posWeight[7][4]=6;
		posWeight[1][1]=posWeight[1][6]=posWeight[6][1]=posWeight[6][6]=-24;
		posWeight[1][2]=posWeight[1][5]=posWeight[2][1]=posWeight[2][6]=posWeight[5][1]=posWeight[5][6]=posWeight[6][2]=posWeight[6][5]=-4;
		posWeight[1][3]=posWeight[1][4]=posWeight[3][1]=posWeight[3][6]=posWeight[4][1]=posWeight[4][6]=posWeight[6][3]=posWeight[6][4]=-3;
		posWeight[2][2]=posWeight[2][5]=posWeight[5][2]=posWeight[5][5]=7;
		posWeight[2][3]=posWeight[2][4]=posWeight[3][2]=posWeight[3][5]=posWeight[4][2]=posWeight[4][5]=posWeight[5][3]=posWeight[5][4]=4;
		posWeight[3][3]=posWeight[3][4]=posWeight[4][3]=posWeight[4][4]=0;
		
		for(int i = 0; i < Board.BOARD_SIZE; ++i){
			for(int j = 0; j < Board.BOARD_SIZE; ++j){
				if (board.getPlayerAtPos(new Position(j,i)) == turn )
					count -= posWeight[i][j];
				else if (board.getPlayerAtPos(new Position(j,i)) == GamePlay.opposite(turn) )
					count += posWeight[i][j];
			}
		}
		return (double) count;
	}

}
