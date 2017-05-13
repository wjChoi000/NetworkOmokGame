package model;

import java.awt.Point;
import java.util.Vector;

public class StoneArray {
	public static final int WHITE_STONE= 1;
	public static final int BLACK_STONE=-1;
	public static final int NUMBER_OF_CHECKER = 19;
	/*private Vector<Point> whitePV = new Vector<Point>();
	private Vector<Point> blackPV = new Vector<Point>();
	*/
	 //0 = nothing, 1 = white, -1=black
	private int[][] existStone  = {
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}
	};
	public StoneArray(){
		init();
	}
	private void init(){
		/*existStone = new int[NUMBER_OF_CHECKER][NUMBER_OF_CHECKER];
		for(int i=0; i<NUMBER_OF_CHECKER; i++){
			for(int j=0; j<NUMBER_OF_CHECKER; j++)
				existStone[i][j] =0;
		}*/
		
	}
	
	// stone add, return color of stone. if error return 0
	public int addStone(int x, int y, int color){
		if(color == BLACK_STONE || color == WHITE_STONE){
			if(existStone[y][x] == 0){
				existStone[y][x] = color;
				return color;
			}
			else{
				System.out.println("stone vector : exist stone.");
			}
		}else{
			System.out.println("stone vector : color error.");
		}
		return 0;
	}
	
	//if game is end, return win stone's color, else return 0
	public int endGame(){
		
		int serialBlack = 0;
		int serialWhite = 0;
		int preStone;
		
		//vertical
		for(int i=0; i<NUMBER_OF_CHECKER; i++){
			preStone = existStone[0][i];
			if(preStone == BLACK_STONE)
				serialBlack = 1;
			else if(preStone == WHITE_STONE)
				serialWhite = 1;
			
			for(int j = 1; j<NUMBER_OF_CHECKER;j++){
				if(existStone[j][i] == BLACK_STONE){
					serialBlack++;
					serialWhite = 0;
				}
				else if(existStone[j][i] ==WHITE_STONE){
					serialBlack = 0;
					serialWhite++;
				}else{
					serialBlack = 0;
					serialWhite = 0;
				}
				
				if(serialBlack ==5){
					System.out.println("vertical black win ");
					return BLACK_STONE;
				}
				else if(serialWhite ==5){
					System.out.println("vertical white win");
					return WHITE_STONE;
				}
				preStone = existStone[j][i];
			}
		}
		// horizontal
		for(int i=0; i<NUMBER_OF_CHECKER; i++){
			preStone = existStone[i][0];
			if(preStone == BLACK_STONE)
				serialBlack = 1;
			else if(preStone == WHITE_STONE)
				serialWhite = 1;
			
			for(int j = 1; j<NUMBER_OF_CHECKER;j++){
				if(existStone[i][j] == BLACK_STONE){
					serialBlack++;
					serialWhite = 0;
				}
				else if(existStone[i][j] ==WHITE_STONE){
					serialBlack = 0;
					serialWhite++;
				}else{
					serialBlack = 0;
					serialWhite = 0;
				}
				
				if(serialBlack ==5){
					System.out.println("horizontal black win");
					return BLACK_STONE;
				}
				else if(serialWhite ==5){
					System.out.println("horizontal white win");
					return WHITE_STONE;
				}
				preStone = existStone[j][i];
			}
		}
		
		int serialNum=0;
		//diagonal right
		for(int i=0; i<NUMBER_OF_CHECKER-4; i++){	
			for(int j = 0; j<NUMBER_OF_CHECKER-4;j++){
				if(existStone[i][j] !=0){
					preStone = existStone[i][j];
					for(serialNum =1; serialNum<5; serialNum++){
						if(existStone[i+serialNum][j+serialNum] !=preStone)	
							break;
					}
					
					if(serialNum ==5){
						System.out.println("diagonal right"+preStone+" win");
						return preStone;
					}
				}
			}
		}
		
		//diagonal left
		for(int i=0; i<NUMBER_OF_CHECKER-4; i++){	
			for(int j = 4; j<NUMBER_OF_CHECKER;j++){
				if(existStone[i][j] !=0){
					preStone = existStone[i][j];
					for(serialNum =1; serialNum<5; serialNum++){
						if(existStone[i+serialNum][j-serialNum] !=preStone)	
							break;
					}
					
					if(serialNum ==5){
						System.out.println("diagonal left "+preStone+" win");
						return preStone;
					}
				}
			}
		}
		
		return 0;
	}
	
	void print(){
		for(int i=0; i<NUMBER_OF_CHECKER; i++){
			for(int j=0; j <NUMBER_OF_CHECKER; j++){
				System.out.printf("%2d",existStone[i][j]);
			}
			System.out.printf("\n");
		}
	}
	
	/*public static void main(String args[]){
		StoneVector a= new StoneVector();
		//herizon test
		a.addStone(5,5,BLACK_STONE);
		a.addStone(5,6,BLACK_STONE);
		a.addStone(5,7,BLACK_STONE);
		a.addStone(5,8,BLACK_STONE);
		a.addStone(5,9,BLACK_STONE);
		
		a.addStone(5, 9, WHITE_STONE);
		a.addStone(6, 9, WHITE_STONE);
		a.addStone(7, 9, WHITE_STONE);
		a.addStone(8, 9, WHITE_STONE);
		a.addStone(12, 9, WHITE_STONE);
		
		for(int i=0; i<4; i++){
			a.addStone(0+i, 14+i, BLACK_STONE);
		}
		
		for(int i=0; i<4; i++){
			a.addStone(18-i, 0+i, BLACK_STONE);
		}
		a.print();
		
		System.out.println("result : "+a.endGame());
	}*/
	
}
