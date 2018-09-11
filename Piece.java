//package myrmidonChess;

import java.util.ArrayList;

public abstract class Piece {
    //private String type;
    //private String color;

    public abstract String getType();
    public abstract void setType(String type);
    public abstract String getColor();
    public abstract void setColor(String color);
    public abstract ArrayList<Integer> showMove(Piece[][] pieceManager, int r, int c);
    
    public static void transform(Piece[][] pieceManager, int turn, String color) {
    	int counter = turn;
    	if(counter != 0 && counter % 3 == 0) {
    		for(int x = 0; x < Board.ROWS; x++) {
    			for(int y = 0; y < Board.COLUMNS; y++) {
    	    		if (pieceManager[x][y].getType() == "Triangle" && pieceManager[x][y].getColor() == color ) {
    	    			pieceManager[x][y] = new Chevron(pieceManager[x][y].getColor());
    	            } else if (pieceManager[x][y].getType() == "Chevron" && pieceManager[x][y].getColor() == color ) {
    	            	pieceManager[x][y] = new Plus(pieceManager[x][y].getColor());
    	            } else if (pieceManager[x][y].getType() == "Plus" && pieceManager[x][y].getColor() == color ) {
    	            	pieceManager[x][y] = new Triangle(pieceManager[x][y].getColor());
    	            } else {
    	                // do nothing
    	            }
    			}
    		}
    	}
    }
}