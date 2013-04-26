import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.BoxLayout;
import java.awt.BorderLayout;
import javax.swing.ImageIcon;

import java.util.Random;

/**
 * The game board. Will start the game, and respond to user input.
 * 
 * @version 2.0
 * @author Kristina Flaherty
 */
@SuppressWarnings("serial")
public class BoardPanel extends JPanel{

    /*
     * The playing field.
     */
    private PlayingField field;

	/*
	* Color 2D array used to create board from loaded game
	*/

	private Color[][] loadedGame;
	
	//private boolean gameLoaded;

    /*
     * The active piece the player controls
     */
    private Piece active;

    /*
     * Game timer
     */
    private final Timer timer;

    /*
     * The default pixel dimension per tile.
     */
    private Dimension tileDimension = new Dimension(20, 20);
    
    private Random rand = new Random();

	private int clearCount = 0;
	private int level = 1;
	private InfoPanel infoPanel;
	private int fallCount;

    /**
     * Creates a game board and starts the game.
     * 
     * @param cols number of columns in the game (width)
     * @param rows number of rows in the game (height)
     */
    public BoardPanel(int cols, int rows, InfoPanel infoPanel) {

        setPreferredSize(new Dimension(cols * tileDimension.width, rows * tileDimension.height));
/*
		setLayout(new BorderLayout());
		infoPanel = new JPanel();
		add(infoPanel, BorderLayout.EAST);
		infoPanel.setPreferredSize(new Dimension((cols * tileDimension.width)/3, rows * tileDimension.height));
		*/

        setFocusable(true);
        addKeyListener(new KeyController());

		addComponentListener(new SizeListener()); //this is to resize

        field = new PlayingField(cols, rows);
		this.infoPanel = infoPanel;
		loadedGame = new Color[cols][rows];

	//	infoPanel = new JPanel();
		

        timer = new Timer(500, new FallListener());
        
        generateNewPiece(rand.nextInt(7)); //create the active piece
		infoPanel.setPieceImage();
		
		/*
		Object[] options = {"Start new game",
		                    "Load saved game",
		                    "Cancel"};
		int n = JOptionPane.showOptionDialog(this,
		    "Select and Option",
		    JOptionPane.YES_NO_CANCEL_OPTION,
		    JOptionPane.QUESTION_MESSAGE,
		    null,
		    options,
		    options[2]);
		*/
		
		/**
		* On starting the game, JOptionPane Prompts the user to start a new game or load a saved game
		* Depending on the option, the appropiate variables are initialized and the game begins
		*
		*/
		
		
		Object[] options = {"Start a new game",
		                    "Load a saved game"};
		int startChoice = JOptionPane.showOptionDialog(this,"Please select an option", "TETRIS",JOptionPane.YES_NO_OPTION,
		    JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
		
		if(startChoice == JOptionPane.YES_OPTION){
			//System.out.println("here");
			timer.start();
		}
		else if(startChoice == JOptionPane.NO_OPTION){
			
			
			//Object[] possibilities = {"ham", "spam", "yam"};
			String loadName = (String)JOptionPane.showInputDialog(
			                    this,
			                    "Please enter the name of the game to load",
			                    "Load Tetris Game",
			                    JOptionPane.PLAIN_MESSAGE,
			                    null,
			                    null,
			                    "File name");

			//If a string was returned, say so.
		//	if ((s != null) && (s.length() > 0)) {
			//    setLabel(... " + s + "!");
			//    return;
		//	}
		
			String file = loadName + ".txt";
			
			BufferedReader br = null;
			try{
				int count = 0;
				
				//String file = loadName.getText() + ".txt";
			
				String currentLine; //current line being read
			
				br = new BufferedReader(new FileReader(file));
				
				while ((currentLine = br.readLine()) != null){
					//System.out.println(currentLine);
					count++;
					
					if(count == 1){
						//System.out.println(currentLine);
						
						infoPanel.setLevel(Integer.parseInt(currentLine));
					}
					
					else if(count == 3){
						//System.out.println(currentLine);
						infoPanel.setReadScore(Integer.parseInt(currentLine));
					}
					
					else if(count == 5){
						//System.out.println(currentLine);
						String pieceType = currentLine;
						
						String[] tokens = pieceType.split("@");
						
						pieceType = tokens[0];
						//System.out.println(pieceType);
						
						if(pieceType.equals("LPieceLeft")){
							infoPanel.setPieceNum(6);
							//nextLabel.setIcon(lpieceleft);
						}
						if(pieceType.equals("LPieceRight")){
							infoPanel.setPieceNum(5);
							//nextLabel.setIcon(lpieceleft);
						}
						if(pieceType.equals("ZPieceRight")){
							infoPanel.setPieceNum(3);
							//nextLabel.setIcon(lpieceleft);
						}
						if(pieceType.equals("ZPieceLeft")){
							infoPanel.setPieceNum(4);
							//nextLabel.setIcon(lpieceleft);
						}
						if(pieceType.equals("TPiece")){
							infoPanel.setPieceNum(2);
							//nextLabel.setIcon(lpieceleft);
						}
						if(pieceType.equals("LinePiece")){
							infoPanel.setPieceNum(0);
							//nextLabel.setIcon(lpieceleft);
						}
						if(pieceType.equals("SquarePiece")){
							infoPanel.setPieceNum(1);
							//nextLabel.setIcon(lpieceleft);
						}
						
					generateNewPiece(infoPanel.pieceNum());
					timer.start();	
				}//end else
				
						else if(count == 7){
							//System.out.println(currentLine);
							String[] tokens = currentLine.split(";");

							for(int x = 0; x<tokens.length; x++){
								if(!tokens[x].equals("null")){
									loadedGame[0][x] = new Color(Integer.parseInt(tokens[x]));
									//System.out.print(loadedGame[0][x]);
								}
							}

						}
						else if(count == 8){
							//System.out.println(currentLine);
							String[] tokens = currentLine.split(";");

							for(int x = 0; x<tokens.length; x++){
								if(!tokens[x].equals("null")){
									loadedGame[1][x] = new Color(Integer.parseInt(tokens[x]));
								}
							}

						}
						else if(count == 9){
							//System.out.println(currentLine);
							String[] tokens = currentLine.split(";");

							for(int x = 0; x<tokens.length; x++){
								if(!tokens[x].equals("null")){
									loadedGame[2][x] = new Color(Integer.parseInt(tokens[x]));
								}
							}

						}
						else if(count == 10){
							//System.out.println(currentLine);
							String[] tokens = currentLine.split(";");

							for(int x = 0; x<tokens.length; x++){
								if(!tokens[x].equals("null")){
									loadedGame[3][x] = new Color(Integer.parseInt(tokens[x]));
								}
							}

						}
						else if(count == 11){
							//System.out.println(currentLine);
							String[] tokens = currentLine.split(";");

							for(int x = 0; x<tokens.length; x++){
								if(!tokens[x].equals("null")){
									loadedGame[4][x] = new Color(Integer.parseInt(tokens[x]));
								}
							}

						}
						else if(count == 12){
							//System.out.println(currentLine);
							String[] tokens = currentLine.split(";");

							for(int x = 0; x<tokens.length; x++){
								if(!tokens[x].equals("null")){
									loadedGame[5][x] = new Color(Integer.parseInt(tokens[x]));
								}
							}

						}
						else if(count == 13){
							//System.out.println(currentLine);
							String[] tokens = currentLine.split(";");

							for(int x = 0; x<tokens.length; x++){
								if(!tokens[x].equals("null")){
									loadedGame[6][x] = new Color(Integer.parseInt(tokens[x]));
								}
							}

						}
						else if(count == 14){
							//System.out.println(currentLine);
							String[] tokens = currentLine.split(";");

							for(int x = 0; x<tokens.length; x++){
								if(!tokens[x].equals("null")){
									loadedGame[7][x] = new Color(Integer.parseInt(tokens[x]));
								}
							}

						}
						else if(count == 15){
							//System.out.println(currentLine);
							String[] tokens = currentLine.split(";");

							for(int x = 0; x<tokens.length; x++){
								if(!tokens[x].equals("null")){
									loadedGame[8][x] = new Color(Integer.parseInt(tokens[x]));
								}
							}

						}
						else if(count == 16){
							//System.out.println(currentLine);
							String[] tokens = currentLine.split(";");

							for(int x = 0; x<tokens.length; x++){
								if(!tokens[x].equals("null")){
									loadedGame[9][x] = new Color(Integer.parseInt(tokens[x]));
								}
							}

						}
						else if(count == 18){
							fallCount = Integer.parseInt(currentLine);
						}
						
			} //end while
			//	br.close();
			
	//	gameLoaded = true;
		field.setBoard(loadedGame);
		
		br.close();
		timer.start();
	}
		catch(IOException ex){
			JOptionPane.showMessageDialog(null,
			    "The filename you entered is not valid",
			    "Load Error",
			    JOptionPane.PLAIN_MESSAGE);
		}
	}//end else if

		
		
    }

    /**
     * Start the Tetris game.
     */
    public void start()
    {
        timer.start();
    }

    /**
     * Set active to a new, random piece, random number num is determined in InfoPanel
	 * @param int num random number passed in from InfoPanel that chooses random piece
     */
    private void generateNewPiece(int num) {
        switch (num)
        {
			//start the pieces off with negative y values so they enter
			//starting at the bottom of the piece rather than the top
            case 0:
                active = new LinePiece(field, field.WIDTH / 2, -2);
                break;

            case 1:
				active = new SquarePiece(field, field.WIDTH / 2, -1);
				break;
				
			case 2:
				active = new TPiece(field, field.WIDTH / 2, -1);
				break;
			
			case 3:
				active = new ZPieceRight(field, field.WIDTH / 2, -1);
				break;
			
			case 4:
				active = new ZPieceLeft(field, field.WIDTH / 2, -1);
				break;
			
			case 5:
				active = new LPieceRight(field, field.WIDTH / 2, -3);
				break;
			
			case 6:
				active = new LPieceLeft(field, field.WIDTH / 2, -3);
				break;
			}
			
			fallCount++;
			//System.out.print(fallCount);
			
    }

//	public boolean gameLoaded(){
//		return gameLoaded;
//	}
	
	
//	public void setLoaded(boolean gameLoaded){
//		this.gameLoaded = gameLoaded;
//	}


    /**
     * Move the current piece down and checks for a win/loss or placement
     * situation. If the piece fails to move down anymore (attemptMove returns false),
     * then the piece settles. Also checks for full row
     */
    private void moveDownActive() {

        /* Move the piece down */
        if (!active.attemptMove(active.getX(), active.getY() + 1))
        {
			//if(gameLoaded()){
			//	field.setBoard(loadedGame);
			//	setLoaded(false);
		//	}
            active.settlePiece(); 
			field.checkForClears();  ///check this
			infoPanel.setScore(field.getNumClear());
			field.setNumClear(0);
            generateNewPiece(infoPanel.pieceNum());
			infoPanel.setActive(active);
			infoPanel.setPieceImage(); //come back to this
			infoPanel.setField(field.getState());
			infoPanel.setCount(fallCount);
			
        }
		//field.checkForClears();

		/* Check if the game is over */
        if (field.isGameOver()) {
            timer.stop();
            int choice = JOptionPane.showConfirmDialog(this, "Play Again?", "You Lose!", JOptionPane.YES_NO_OPTION);
            
            if (choice == JOptionPane.YES_OPTION) //restart game
            {
				field.resetBoard();
				timer.restart();
			}
			else //No option
			{
				System.exit(0);
			}
        }
    }

	/*
	* Paints the piece and the board on the panel
	*/

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

		/* Pass the graphics object and the current tileDimension */
        field.drawBoard(g, tileDimension);	// The field knows how many rows
											// and cols of tiles it has, therefore it
											// only needs to know the dimension
											// of each tile

        active.draw(g, tileDimension);
    }

    /**
     * The timer action. Each time step, move the piece down.
     */
    
    private class FallListener implements ActionListener
    {    
	
		/**
	     * Method that calls moveDownActive and Repaint. Also checks for number of pieces
		 * that have fallen and sets level and timer delay based on that number
		 *
		 * @param ActionEvent event, event that indicated timer action
	     */
	
		public void actionPerformed(ActionEvent event) {
			if (active != null)
				moveDownActive();
				repaint();
				
				if (fallCount >= 10 && fallCount <20){
					infoPanel.setLevel(2);
					timer.setDelay(450);
				}
				else if (fallCount >= 20 && fallCount <30){
					infoPanel.setLevel(3);
					timer.setDelay(400);
				}
				else if (fallCount >= 30 && fallCount <40){
					infoPanel.setLevel(4);
					timer.setDelay(350);
				}
				else if (fallCount >= 40 && fallCount <50){
					infoPanel.setLevel(5);
					timer.setDelay(300);
				}
				else if (fallCount >= 50 && fallCount < 60){
					infoPanel.setLevel(6);
					timer.setDelay(250);
				}
				else if (fallCount >= 60 && fallCount <70){
					infoPanel.setLevel(7);
					timer.setDelay(225);
				}
				else if (fallCount >= 70 && fallCount <80){
					infoPanel.setLevel(8);
					timer.setDelay(215);
				}
				else if (fallCount >= 80 && fallCount <90){
					infoPanel.setLevel(9);
					timer.setDelay(200);
				}
				else if (fallCount >= 90 && fallCount <100){
					infoPanel.setLevel(10);
					timer.setDelay(190);
				}
				else if (fallCount >= 100 && fallCount <110){
					infoPanel.setLevel(11);
					timer.setDelay(180);
				}
				else if (fallCount >= 110 && fallCount <120){
					infoPanel.setLevel(12);
					timer.setDelay(170);
				}
				else if (fallCount >= 120 && fallCount <130){
					infoPanel.setLevel(13);
					timer.setDelay(160);
				}
				else if (fallCount >= 130 && fallCount <140){
					infoPanel.setLevel(14);
					timer.setDelay(150);
				}
				else if (fallCount >= 140 && fallCount <150){
					infoPanel.setLevel(15);
					timer.setDelay(140);
				}
		}
	}
	
	/**
	* Checks to see if the panel has been resized
	*/
	
	private class SizeListener implements ComponentListener{
		
		/**
		* If panel has been resized, this changes the dimension fo the tiles
		*
		*@param ComponentEvent e, event that checks for panel resize
		*/
		
		public void componentResized(ComponentEvent e){
			tileDimension = new Dimension(getWidth()/10, getHeight()/25);
		}
		public void componentHidden(ComponentEvent e) {}
		public void componentMoved(ComponentEvent e) {}
		public void componentShown(ComponentEvent e) {}
		
	}
	
	
	
	

    /**
     * Handle keystrokes.
     * 
     */
    private class KeyController extends KeyAdapter {
	
		/**
	     * Handles keystrokes and repaints afterwards
	     * 
		 *@param KeyEvent key, checks for key strokes  
	     */

        @Override
        public void keyPressed(final KeyEvent key) {
            if (active != null) {

                int oldX = active.getX();
                int oldY = active.getY();

                switch (key.getKeyCode()) {
                    case KeyEvent.VK_RIGHT:
                        active.attemptMove(oldX + 1, oldY); //move right
                        break;
                    case KeyEvent.VK_LEFT:
                        active.attemptMove(oldX - 1, oldY); //move left
                        break;
                    case KeyEvent.VK_SPACE:
                        active.attemptRotation(); //rotate
                        break;
                    case KeyEvent.VK_DOWN:
                        moveDownActive(); //move down
                        break;
                    case KeyEvent.VK_ESCAPE: //quit game
                        System.exit(0);
                        break;
                }

                repaint();
            }
        }
    }
}
