//package myrmidonChess;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
import java.awt.GridLayout;

/*** Written by Yoong Shen Wei ***/
@SuppressWarnings("serial")
public final class Board extends JPanel{
	
	/** Singleton Implementation **/
	// Create STATIC instance for this class - SINGLETON
	private static Board board = new Board();
	
	// Create a private constructor, so that it cannot be instantiated
	private Board(){};
	
	// This method returns static instance of the class
	public static Board getBoard() {
		return board;
	}
	
	// Declaration of grid on chess board, row = 6, column = 7
	public static final int ROWS = 6;
	public static final int COLUMNS = 7;
	public static JButton[][] grid = new JButton[ROWS][COLUMNS];
	private static Piece[][] pieceManager = new Piece[ROWS][COLUMNS];
	private static PieceFactory pieceFactory = new PieceFactory();
	public static int turn = 0;  // Initial turn = 0
	//private static int redTurn = 0;  // Initial turn = 0
	//private static int blueTurn = 0;  // Initial turn = 0
	private static boolean pieceSelected = false;
	
	//to store the buttons that a piece can move to 
	private static ArrayList<Integer> availableBtn = new ArrayList<Integer>(); 
	
	//the store previous pieces which is EmptyWhite
	private static Piece prevPiece = pieceFactory.createPiece("Empty","White");
	private static int prevRow=0;
	private static int prevCol=0;
	
	private static ArrayList<Piece> eatenPiece=new ArrayList<Piece>();
	
	public static JPanel insertPanel() {
		
		// create grid layout on the chess board
		board.setLayout(new GridLayout(ROWS, COLUMNS, 1, 1));
		board.setSize(200,200);
		
		// assign color to each grid
		for (int r = 0; r < ROWS; r++) 
		{
			for (int c = 0; c < COLUMNS;  c++) 
			{
				String btnName = r+","+c;
				// Create a temporary single grid to be colored
				JButton g = new JButton();
				g.setActionCommand(btnName);
				
				if ((c % 2 == 1 && r % 2 == 1) || (c % 2 == 0 && r % 2 == 0)) {
					g.setBackground(Color.WHITE);
					
				} else {
					g.setBackground(Color.BLACK);
				}
				
				g.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						// Set board color
						//resetBoardColor();

						// To obtain the row and column index of piece clicked by the user
						String temp = g.getActionCommand();
						int row = Integer.parseInt(temp.substring(0,1));
						int col = Integer.parseInt(temp.substring(2));		

						// Action 1: Click to select a piece
						if(pieceManager[row][col].getColor().equalsIgnoreCase(getPlayerTurn()) && pieceSelected == false)	{
							if(pieceSelected == false) {
								prevRow = row;
								prevCol = col;
								prevPiece = pieceManager[row][col];

								Piece newPiece = pieceFactory.createPiece(pieceManager[row][col].getType(),pieceManager[row][col].getColor());
								availableBtn = newPiece.showMove(pieceManager, row, col);
					
								for (int r = 0; r < ROWS; r++) {
									for (int c = 0; c < COLUMNS;  c++) {
										if (pieceManager[r][c].getType()=="Empty")
										{
											continue;
										}
										String color = pieceManager[r][c].getColor();
										String type = pieceManager[r][c].getType();
										//System.out.println(type+color+": "+r+","+c);
									}
								}
								
								pieceSelected = true;
							}
						}
						
						else if (pieceManager[row][col].getType().equalsIgnoreCase(prevPiece.getType()) && pieceManager[row][col].getColor().equalsIgnoreCase(prevPiece.getColor()) && pieceSelected == true){ 
							System.out.println(prevPiece.getType() + prevPiece.getColor());
								// Action 2: Click to deselect a piece if the same piece is clicked
								System.out.println(pieceManager[row][col].getColor());
								prevRow = 0;
								prevCol = 0;
								resetBoardColor();
								pieceSelected = false;
							}
								
						// Action 3: Click to move a piece (valid move)
						else if(pieceManager[row][col].getColor() != getPlayerTurn() && pieceSelected == true && grid[row][col].getBackground().equals(Color.GREEN)) {
							String color=prevPiece.getColor();
							String type=prevPiece.getType();
							for(int i = 0; i < availableBtn.size(); i++) {
								int x=i;
								int y=i+1;
								i++;
								if(row == availableBtn.get(x) && col == availableBtn.get(y)) 
								{
									if(pieceManager[row][col].getColor() != color && pieceManager[row][col].getColor() != "White")
									{
										String losePieceType=null;
										String losePieceColor=null;
										JFrame f;
										
										if(pieceManager[row][col].getColor() != color && pieceManager[row][col].getColor() != "White")
										{
											eatenPiece.add(pieceFactory.createPiece(pieceManager[row][col].getType(),pieceManager[row][col].getColor()));
											setEatenPieceFromBoard();
										
											losePieceType=pieceManager[row][col].getType();
											losePieceColor=pieceManager[row][col].getColor();
										}
										pieceManager[row][col].setType(type);
										pieceManager[row][col].setColor(color);
									
										pieceManager[prevRow][prevCol].setType("Empty");
										pieceManager[prevRow][prevCol].setColor("White");
										
										if(losePieceType=="Sun")
										{
											resetBoardColor();
											pieceSelected = false;
											availableBtn.clear();
											setPiece();	
											f=new JFrame();
											switch(losePieceColor)
											{
												case "Red":
												
													JOptionPane.showMessageDialog(f,"Blue Won!");
													break;
												
												case "Blue":
												
													JOptionPane.showMessageDialog(f,"Red Won!");
													break;
											
											}
										}
										else
										{
											// update turn
											turn++;
											Piece.transform(pieceManager, turn);
											setTurnFromBoard();
											resetBoardColor();
											pieceSelected = false;
											availableBtn.clear();
											setPiece();
										}
				
								}
							}
						}
						
						else {
							// Action 4: Click to move a piece (invalid move)
							if(pieceSelected == true && grid[row][col].getBackground() != Color.GREEN) {
								resetBoardColor();
								pieceSelected = false;
							}
						}
					}
				});
				
				grid[r][c] = g; 
			}
		}
		
		// insert grids into chess board
		for (int r = 0; r < ROWS; r++) {
			for (int c = 0; c < COLUMNS;  c++) {
				board.add(grid[r][c]);
			}
		}
		
		initialPosition();
		return board;
	}
	
	/* Returns an ImageIcon, or null if the path was invalid. */
	private static ImageIcon loadImage(String path) {
	    java.net.URL imgURL = Board.class.getResource(path);
	    if (imgURL != null) {
	        return new ImageIcon(imgURL);
	    } else {
	        System.err.println("Couldn't find file: " + path);
	        return null;
	    }
	}
	
	/* initialize and display pieces */
	public static void initialPosition(){
		for (int r = 0; r < ROWS; r++) {
			for (int c = 0; c < COLUMNS;  c++) {
				pieceManager[r][c] = pieceFactory.createPiece("Empty", "White");
			}
		}
		pieceManager[5][1] = pieceFactory.createPiece("Triangle","Red");
		pieceManager[5][5] = pieceFactory.createPiece("Triangle","Red");
		pieceManager[0][1] = pieceFactory.createPiece("Triangle","Blue");
		pieceManager[0][5] = pieceFactory.createPiece("Triangle","Blue");

		pieceManager[5][0] = pieceFactory.createPiece("Plus","Red");
		pieceManager[5][6] = pieceFactory.createPiece("Plus","Red");
		pieceManager[0][0] = pieceFactory.createPiece("Plus","Blue");
		pieceManager[0][6] = pieceFactory.createPiece("Plus","Blue");
		
		pieceManager[5][2] = pieceFactory.createPiece("Chevron","Red");
		pieceManager[5][4] = pieceFactory.createPiece("Chevron","Red");
		pieceManager[0][2] = pieceFactory.createPiece("Chevron","Blue");
		pieceManager[0][4] = pieceFactory.createPiece("Chevron","Blue");
		
		
		pieceManager[0][3] = pieceFactory.createPiece("Sun","Blue");
		pieceManager[5][3] = pieceFactory.createPiece("Sun","Red");

		setPiece();
		
	}
	
	/* Set the Board color (alternating black and white grid) */
	private static void resetBoardColor(){
		for (int r = 0; r < ROWS; r++) {
			for (int c = 0; c < COLUMNS;  c++) {
				if ((c % 2 == 1 && r % 2 == 1) || (c % 2 == 0 && r % 2 == 0)) {
					grid[r][c].setBackground(Color.WHITE);
				}
				else {
					grid[r][c].setBackground(Color.BLACK);
				}
			}
		}
	
	}
	
	/* Insert the image of pieces into the grid */
	private static void setPiece()
	{
		for (int r = 0; r < ROWS; r++) 
		{
			for (int c = 0; c < COLUMNS;  c++) 
			{
				if (pieceManager[r][c].getType()=="Empty"){
					grid[r][c].setIcon(null);
					continue;
				}
				String color = pieceManager[r][c].getColor();
				String type = pieceManager[r][c].getType();
				String fileName = color+type+".png";
				grid[r][c].setIcon(loadImage(fileName));
			}
		}
	}
	
    /* Return player turn [Blue, Red] */
    public static String getPlayerTurn(){
		if(turn % 2 == 0){
        	return ("Blue");
		}
		else{
        	return ("Red");
       	} 
	}
	
	/* Update "Current turn" in the GameInfo when a player makes a move */
	public static void setTurnFromBoard(){
		GameInfo.changeCurrentTurn(getPlayerTurn());
		
	}
	/* Update eatenpiece in the GameInfo*/
	public static void setEatenPieceFromBoard()
	{
		GameInfo.updateEatenPiece(eatenPiece);
	}
	
	/* Return pieceManager for Saving a game*/
	public static String printPM()
	{
        String position = "";
		for (int r = 0; r < ROWS; r++) 
		{
			for (int c = 0; c < COLUMNS;  c++) 
			{
				String color = pieceManager[r][c].getColor();
				String type = pieceManager[r][c].getType();
				System.out.println(type+color+": "+r+","+c);
                position = position + color + type + ",";
			}
		}
		System.out.println("--------------------------------------------");
        System.out.println(position);
		System.out.println("--------------------------------------------");
        return position;
	}
    public static void loadGame(String text){
        ArrayList<Piece> loadGameArrayList = new ArrayList<Piece>();
	    //Piece[][] pieceManager = new Piece[ROWS][COLUMNS];
        String color = "";
        String type = "";
        int count = 0;
        int r = 0;
        int c = 0;
        for(int i = 0; i<text.length();){
            //System.out.println(i);
            int j = i + 1;
            if(text.substring(i,j).equals("R")){
                int k = i+3;
                color = text.substring(i,k);
                //System.out.print(color);
                i = k;
            }
            else if (text.substring(i,j).equals("B")){
                int k = i+4;
                color = text.substring(i,k);
                //System.out.print(color);
                i = k;
            }
            else if (text.substring(i,j).equals("W")){
                int k = i+5;
                color = text.substring(i,k);
                //System.out.print(color);
                i = k;
            }
            else if (text.substring(i,j).equals("E")){
                int k = i+5;
                type = text.substring(i,k);
                //System.out.println(type);
                i = k;
            }else if (text.substring(i,j).equals("P")){
                int k = i+4;
                type = text.substring(i,k);
                //System.out.println(type);
                i = k;
            }else if (text.substring(i,j).equals("T")){
                int k = i+8;
                type = text.substring(i,k);
                //System.out.println(type);
                i = k;
            }else if (text.substring(i,j).equals("C")){
                int k = i+7;
                type = text.substring(i,k);
                //System.out.println(type);
                i = k;
            }else if (text.substring(i,j).equals("S")){
                int k = i+3;
                type = text.substring(i,k);
                //System.out.println(type);
                i = k;
            }
            else{
                i++;
                pieceManager[r][c++] = pieceFactory.createPiece(type,color);
                if(c==7){
                    c = 0;
                    r++;
                }
            }
        }
        setPiece();
        turn = 0;
        setTurnFromBoard();
        availableBtn.clear();
        eatenPiece.clear();
    }
	/*
	public static void printEatenPiece()
	{
		for (int r = 0; r < ROWS; r++) 
		{
			for (int c = 0; c < COLUMNS;  c++) 
			{
				if (pieceManager[r][c].getColor()=="Red")
				{
					System.out.println( pieceManager[r][c].getType()+"")
					
				}
				else
				{
					
				}
				
				;
				System.out.println(type+color+": "+r+","+c);
			}
		}
		String r = "Red:\n";
		String b = "Blue:\n";
		for(Piece
 	}
	*/
	
}
