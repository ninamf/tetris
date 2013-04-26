import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;


/**
 * Creates the playing field of pieces that have been settled
 * 
 * @version 2.0
 * @author Kristina Flaherty
 */

public class PlayingField {

    /*
     * Game board. Colors are settled pieces, nulls are empty spots.
     * Each element in the array corresponds to a 2-D grid of "tiles"
     */
    private final Color[][] board;
	private Color[][] boardCopy;

	private int numClear;
    
    private Color emptyTile = Color.GRAY;

    public final int WIDTH, HEIGHT; //can be public because constants can't be changed

    /**
     * Create a playing field.
     * 
     * @param cols number of columns in the field (width)
     * @param rows number of rows in the width (height)
     */
    public PlayingField(int cols, int rows) {

		WIDTH = cols;
		HEIGHT = rows;
		
        board = new Color[WIDTH][HEIGHT]; //board arrange by x,y / width,height **************REMEMBER THIS****************
        
    }

    /**
     * Checks if the tile at position is free.
     * 
     * @param x the x position to check
     * @param y the y position to check
     * @return true if piece is legal and vacant to move to.
     */
    public boolean isTileVacant(int x, int y) {
        if (x < 0 || x >= board.length || y >= board[0].length)
            return false;

        if (y < 0) //this implementation allows pieces to exist above the board
            return true;

        return board[x][y] == null; //if a slot is null, then it is considered empty
    }

    /**
     * Set the tile at a given position. Tiles wont be set if they are off-sceen
     * 
     * @param x the x position of the tile
     * @param y the y position of the tile
     * @param color color to set tile to.
     */
    public void setTileColor(int x, int y, Color color) {
        if (x >= 0 && x < board.length && y >= 0 && y < board[0].length)
            board[x][y] = color;
    }

	
	/**
     * Checks the board for full rows, and if full, replaces all row with the row
	 * above it, starting from the bottom row
     * 
     */
	
	public void checkForClears(){ //works @ 8:55
		int count = 0;
		for(int y = HEIGHT-1; y>=0; y--){  //should iterate from bottom to top 
			count = 0; //makes sure count is restarted 
			for(int x = 0; x<board.length; x++){ //check left to right, then go up a row
				if(board[x][y] != null){
					count++;
					if(count == board.length){ //if there is a full row
						numClear++;
						for(int r=y; r>=0; r--){
							for(int c = 0; c<board.length; c++){
								if(r-1>=0){ //check for bounds
									board[c][r] = board[c][r-1]; //reassigns board pieces
								}
							}
						}
						//System.out.print(numClear);
						checkForClears(); //to start over check
					}
				}
			}
		}
	}
	
	/**
	* number of rows cleared
	*
	*@return number of rows cleared when checkForClears is called
	*/
	
	public int getNumClear(){
		return numClear;
	}
	
	/**
	* sets number of rows cleared
	*
	*@param num set to numClear
	*/
	
	public void setNumClear(int num){
		numClear = num;
	}
	
	/**
	* gives current state of playing field
	*
	*@return copy of current playing field
	*/
	
	public Color[][] getState(){
		boardCopy = board;
		return boardCopy;
	}
	
	/**
	* sets the board with a game a user has loaded
	*
	*@param board that will be set to current playing field
	*/
	
	public void setBoard(Color[][] currentState){
		for(int x =0; x<board.length; x++){
			for (int y = 0; y<board[x].length; y++){
				board[x][y] = currentState[x][y];
			}
		}
	}








    /**
     * Check the top row for a loss condition.
     * 
     * @return true if loss detected, false otherwise.
     */
    public boolean isGameOver() {
        for (int x = 0; x < board.length; x++) {
            if (board[x][0] != null) //check top row, remember x, y
            {
                return true;
            }
        }

        return false;
    }


    /**
     * Clear the board.
     */
    public void resetBoard() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = null;
            }
        }
    }

    /**
     * Draw the game board
     * 
     * @param g Graphics context to draw on (from BoardPanel)
     * @param tileDim the dimensions of an individual tile
     */
    public void drawBoard(Graphics g, Dimension tileDim) {

		boolean drawGrid = false;

        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board[0].length; y++) {

																					//fix later
                if (board[x][y] == null)
                {
                    g.setColor(emptyTile);
                    if (drawGrid)
						g.draw3DRect(x * tileDim.width, y * tileDim.height, tileDim.width, tileDim.height, true);
                    else
						g.drawRect(x * tileDim.width, y * tileDim.height, tileDim.width, tileDim.height);
                }
				else
                {
                    g.setColor(board[x][y]);
                    g.fill3DRect(x * tileDim.width, y * tileDim.height, tileDim.width, tileDim.height, true);
                }
            }
        }
    }
}
