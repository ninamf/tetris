import java.awt.Color;
import java.awt.Point;

/**
 * A Tetris piece model for the Z piece left (the left pointing Z piece)
 * 
 * @version 2.0
 * @author Kristina Flaherty
 */
public class ZPieceLeft extends Piece{ //works on 4/19

	private final static boolean[][] rotation0;
	private final static boolean[][] rotation1;

    static {
	
		rotation0 = new boolean[][] {
			
            {false, true, true, false}, 
			{true, true, false, false}, 
			{false, false, false, false},
			{false, false, false, false}
			/*
			
                {false, true, true, false}, 
				{false, false, true, true}, 
				{false, false, false, false},
				{false, false, false, false}
				*/
        };
        
        rotation1 = new boolean[][] {
	
           	{true, false, false, false}, 
			{true, true, false, false}, 
			{false, true, false, false},
			{false, false, false, false}
	
	/*
	
                {false, true, false, false}, 
				{true, true, false, false}, 
				{true, false, false, false},
				{false, false, false, false}
				*/
        };

    }
    
    /*
     * The rotation model of the piece
     * If the tile does exist in that position
     * (for that rotation), value = true
     * else false
     * The model is inverted when visualizing as a grid:
     * [rotationIndex][x offset][y offset]
     */
    private final static boolean[][][] model = {rotation0, rotation1, rotation0, rotation1};

/**
* Constructor of the Z piece that creates the Z piece when called in Board Panel
*
* @param PlayingField field takes in a PlayingField object to access and store the state of the board
* @param int x is the x position of the Zpiece on the board
* @param int y is the y position of the Z piece on the board
*/
        
    public ZPieceLeft(PlayingField field, int x, int y) {
        super(field, x, y, model, Color.PINK);
    }
}