import java.awt.Color;
import java.awt.Point;

/**
 * A Tetris piece model for the T piece
 * 
 * @version 2.0
 * @author Kristina Flaherty
 */


public class TPiece extends Piece{ //works @ 7:16

	private final static boolean[][] rotation0;
	private final static boolean[][] rotation1;
	private final static boolean[][] rotation2;
	private final static boolean[][] rotation3;

    static {
		rotation3 = new boolean[][] {
         		{false, true, false, false}, 
				{true, true, false, false},
				{false, true, false, false},
				{false, false, false, false}
        };
        
        rotation2 = new boolean[][]
        {
				{false, true, false, false}, 
				{true, true, true, false}, 
				{false, false, false, false},
				{false, false, false, false}                             
        };
        
        rotation1 = new boolean[][] {
         		{false, true, false, false},
				{false, true, true, false},
				{false, true, false, false},
				{false, false, false, false}
        };
        
        rotation0 = new boolean[][]
        {
				{false, false, false, false}, 
				{true, true, true, false}, 
				{false, true, false, false},
				{false, false, false, false}                             
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
    private final static boolean[][][] model = {rotation0, rotation1, rotation2, rotation3};

/**
* Constructor of the T piece that creates the T piece when called in Board Panel
*
* @param PlayingField field takes in a PlayingField object to access and store the state of the board
* @param int x is the x position of the T piece on the board
* @param int y is the y position of the T piece on the board
*/
        
    public TPiece(PlayingField field, int x, int y) {
        super(field, x, y, model, Color.MAGENTA);
    }
}
