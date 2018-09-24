//package myrmidonChess;

import java.awt.Color;

import java.util.ArrayList;



public class Plus extends Piece  
{
	private String type;
	private String color;
	public Plus(String color)
	{
		this.setType("Plus");
       		this.setColor(color);
	}
	 public String getType(){
	        return type;
   	}
	public void setType(String type){
	   	this.type = type;
	}
	public String getColor(){
	   	return color;
	}
	public void setColor(String color){
	  	 this.color = color;
	}
	
	public void checkOccupied(ArrayList<Integer> a) {
		// TODO
	}
    	/* (Lucinda,) */ 	
	public ArrayList<Integer> showMove(Piece[][] pieceManager, int r, int c)
	{
		ArrayList<Integer> a = new ArrayList<Integer>();
		//Haven't done (if blockage then cannot move)[PIECE]
		//Verify pawn can go backwards direction? 
		
		// Initialize 4 paths blocked flag
        boolean[] pathBlocked = new boolean[4];
        for(int x = 0; x < pathBlocked.length; x++) {
        	pathBlocked[x] = false;
        }
		
		for(int step = 1; step<=2; step++){
			
			// Vertical movement
			int r1 = r + step;
			int c1 = c + step;
			int r2 = r - step;
			int c2 = c - step;
			
			// Available movement for path 1
			if((r>=0 && r<=5) && (c2>=0 && c2<=6)){
				if(step == 1) {
                	if(pieceManager[r][c2].getColor() != pieceManager[r][c].getColor())
                    {
		        	    Board.grid[r][c2].setBackground(Color.GREEN);
		        	    a.add(r);
		        	    a.add(c2);
                    }
                	if(pieceManager[r][c2].getType() != "Empty") {
                		pathBlocked[0] = true;
                	}
                }
            	if(step == 2) {
                	if(pathBlocked[0] == false) {
                		if(pieceManager[r][c2].getColor() != pieceManager[r][c].getColor())
                        {
    		        	    Board.grid[r][c2].setBackground(Color.GREEN);
    		        	    a.add(r);
    		        	    a.add(c2);
                        }
                	}
                }
			}
			
			// Available movement for path 2
			if((r1>=0 && r1<=5) && (c>=0 && c<=6)){
				if(step == 1) {
                	if(pieceManager[r1][c].getColor() != pieceManager[r][c].getColor())
                    {
		        	    Board.grid[r1][c].setBackground(Color.GREEN);
		        	    a.add(r1);
		        	    a.add(c);
                    }
                	if(pieceManager[r1][c].getType() != "Empty") {
                		pathBlocked[1] = true;
                	}
                }
            	if(step == 2) {
                	if(pathBlocked[1] == false) {
                		if(pieceManager[r1][c].getColor() != pieceManager[r][c].getColor())
                        {
    		        	    Board.grid[r1][c].setBackground(Color.GREEN);
    		        	    a.add(r1);
    		        	    a.add(c);
                        }
                	}
                }
			}
			
			// Available movement for path 3
			if((r>=0 && r<=5) && (c1>=0 && c1<=6)){
				if(step == 1) {
                	if(pieceManager[r][c1].getColor() != pieceManager[r][c].getColor())
                    {
		        	    Board.grid[r][c1].setBackground(Color.GREEN);
		        	    a.add(r);
		        	    a.add(c1);
                    }
                	if(pieceManager[r][c1].getType() != "Empty") {
                		pathBlocked[2] = true;
                	}
                }
            	if(step == 2) {
                	if(pathBlocked[2] == false) {
                		if(pieceManager[r][c1].getColor() != pieceManager[r][c].getColor())
                        {
    		        	    Board.grid[r][c1].setBackground(Color.GREEN);
    		        	    a.add(r);
    		        	    a.add(c1);
                        }
                	}
                }
			}
			
			// Available movement for path 4
			if((r2>=0 && r2<=5) && (c>=0 && c<=6)){
				if(step == 1) {
                	if(pieceManager[r2][c].getColor() != pieceManager[r][c].getColor())
                    {
		        	    Board.grid[r2][c].setBackground(Color.GREEN);
		        	    a.add(r2);
		        	    a.add(c);
                    }
                	if(pieceManager[r2][c].getType() != "Empty") {
                		pathBlocked[3] = true;
                	}
                }
            	if(step == 2) {
                	if(pathBlocked[3] == false) {
                		if(pieceManager[r2][c].getColor() != pieceManager[r][c].getColor())
                        {
    		        	    Board.grid[r2][c].setBackground(Color.GREEN);
    		        	    a.add(r2);
    		        	    a.add(c);
                        }
                	}
                }
			}
		}
		
		// Reset pathBlocked after each move
		for(int x = 0; x < pathBlocked.length; x++) {
        	pathBlocked[x] = false;
        }
		
		return a;
	} //end SHOWMOVE
	
}
