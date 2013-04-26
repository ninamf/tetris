import java.awt.*;
import java.awt.event.*;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.ImageIcon;
import java.io.*;
import javax.swing.Box;
import java.io.BufferedReader;
import java.io.FileReader;
import javax.swing.JOptionPane;
//import java.swing.Timer;

import java.util.Random;

/**
 * Creates size panel which displays next piece, score, level, and save option
 * 
 * @verison 2.0
 * @author Kristina Flaherty
 */

public class InfoPanel extends JPanel{
	
	private int scoreTotal = 0;
	private int level = 1;
	private int scoreTimesAmount = 100;
	private int count;
	private int pieceNum;
	private Color[][] playingField;
	private Color[][] loadedGame;
	private boolean gameLoaded = false;
	private Timer timer;
	
	private Piece piece;
	
	private JLabel scoreLabel;
	private JLabel nextLabel;
	private JLabel saveNameAs;
	private JLabel loadNameAs;
	private JButton save;
	private JButton load;
	private JTextField saveName;
	private JTextField loadName;
	private JLabel levelLabel;
	private JLabel nextPiece;
	private JLabel tetrisPic;
	private JButton startNew;
	
	private ImageIcon tpiece = new ImageIcon("tpiece.png");
	private ImageIcon linepiece = new ImageIcon("linepiece.png");
	private ImageIcon zpieceright = new ImageIcon("zpieceright.png");
	private ImageIcon zpieceleft = new ImageIcon("zpieceleft.png");
	private ImageIcon lpieceright = new ImageIcon("lpieceright.png");
	private ImageIcon lpieceleft = new ImageIcon("lpieceleft.png");
	private ImageIcon squarepiece = new ImageIcon("squarepiece.png");
	private ImageIcon tetrispic = new ImageIcon("tetrispic.png");
	
	private Random rand;
	
	/**
	 * Creates the InfoPanel
	*/
	
	public InfoPanel(){
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBackground(Color.GRAY);
		
		rand = new Random();
		
		loadedGame = new Color[10][25];
		

		
		tetrisPic = new JLabel(tetrispic);
		tetrisPic.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		scoreLabel = new JLabel("Score: " + scoreTotal);
		scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		nextLabel = new JLabel();
		nextLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		//nextLabel.setMaximumSize(new Dimension(100, 100) );
		
		save = new JButton("Save Game");
		save.setAlignmentX(Component.CENTER_ALIGNMENT);
		save.addActionListener(new SaveListener());
		save.setFocusable(false);
	
	//	load = new JButton("Load Game");
	//	load.setAlignmentX(Component.CENTER_ALIGNMENT);
	//	load.addActionListener(new LoadListener());
		
		saveName = new JTextField(15);
		saveName.setMaximumSize(new Dimension(100, 20) );
		saveName.setAlignmentX(Component.CENTER_ALIGNMENT);
		saveName.setFocusable(false);
		
	//	loadName = new JTextField(15);
	//	loadName.setMaximumSize(new Dimension(100, 20) );
	//	loadName.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		levelLabel = new JLabel("Level: " + level);
		levelLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		nextPiece = new JLabel("Next Piece:");
		nextPiece.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		saveNameAs = new JLabel("Save Game As:");
		saveNameAs.setAlignmentX(Component.CENTER_ALIGNMENT);
		
	//	loadNameAs = new JLabel("Enter Name of Game to Load:");
	//	loadNameAs.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		
		
		add(tetrisPic);
		add(Box.createVerticalStrut(20));
		add(nextPiece);
		add(nextLabel);
		add(Box.createVerticalStrut(20));
		add(levelLabel);
		add(Box.createVerticalStrut(20));
		add(scoreLabel);
		add(Box.createVerticalStrut(20));
		add(save);
		
		
	}
	
	/**
	 * Gets the current level, and sets it to be displayed on the panel
	 * 
	 * @param int lev, the level the player is on
	 */
	
	public void setLevel(int lev){
		level = lev;
		levelLabel.setText("Level: " + level);
		revalidate();
	}
	
	
	

	
	/*
	
	public void setTimer(Timer timer){
		this.timer = timer;
	}
	
	public Timer getTimer(){
		return timer;
	}
	*/
	
	/**
	 * Gets the current score, and sets it to be displayed on the panel
	 * 
	 * @param int count, the current score of the player
	 */
	
	public void setScore(int count){
		scoreTotal = scoreTotal + (count * (level * scoreTimesAmount));
		scoreLabel.setText("Score: " + scoreTotal);
		revalidate();
	}
	
	/**
	 * Gets the score that is read from a game file then sets the panel display
	 * 
	 * @param int score, the saved score of the player
	 */
	
	public void setReadScore(int score){
		scoreLabel.setText("Score: " + score);
		scoreTotal = score;
		revalidate();
	}
	
	/*
	
	public void setPiece(Piece piece){
		this.piece = piece;
	}
	*/
	
	/**
	* @return pieceNum which is the number associated with a specific piece type
	*/
	
	public int pieceNum(){
		return pieceNum;
	}
	
	/**
	* sets the pieceNum, which is the number associated with a specific piece type
	*
	* @param int num, num set as pieceNum
	*/
	
	public void setPieceNum(int num){
		pieceNum = num;
	}
	
	/**
	* gets the current state of the playing field
	*
	* @param Color[][] field, which represents the current playing field
	*/
	
	public void setField(Color[][] field){
		playingField = field;
		//loadedGame = new Color[field.length][field[0].length];
	}
	
	/**
	* gets the current active piece to be able to save for later game load
	*
	* @param Piece active, the current active piece which can be saved to load later
	*/
	
	public void setActive(Piece active){
		piece = active;
	}
	
	/**
	* sets the count of pieces played to keep track of level
	*
	* @param int count, number of pieces that have fallen
	*/
	
	public void setCount(int count){
		this.count = count;
	}
	
	/*
	
	public Color[][] getLoadedGame(){
		return loadedGame;
	}
	
	
	public boolean gameLoaded(){
		return gameLoaded;
	}
	
	
	public void setLoaded(boolean gameLoaded){
		this.gameLoaded = gameLoaded;
	}
	*/
	
	
	
	
	/**
	* Selects a random num, which corresponds to a piece to display as next piece
	* This number is accessed by BoardPanel to create next piece that matches
	* Sets the image of the next piece indicator
	*/
	
	public void setPieceImage(){
		pieceNum = rand.nextInt(7);
		if(pieceNum == 0){
			nextLabel.setIcon(linepiece);
		}
		else if(pieceNum == 2){
			nextLabel.setIcon(tpiece);
		}
		else if(pieceNum == 5){
			nextLabel.setIcon(lpieceright);
		}
		else if(pieceNum == 6){
			nextLabel.setIcon(lpieceleft);
		}
		else if(pieceNum == 4){
			nextLabel.setIcon(zpieceleft);
		}
		else if(pieceNum == 3){
			nextLabel.setIcon(zpieceright);
		}
		else if(pieceNum == 1){
			nextLabel.setIcon(squarepiece);
		}
	}
	

	/**
	* Responds to user option to save
	*
	*/
	
	private class SaveListener implements ActionListener{
		
		/**
		* Trys to save file by writing into a text file, if unsuccessful, an error message is given
		*
		* @param ActionEvent e, responds to save button being pressed
		*/
		
		public void actionPerformed(ActionEvent e){
			
			try{
				String saveName = (String)JOptionPane.showInputDialog(
				                    null,
				                    "Save game as:",
				                    "Save Tetris Game",
				                    JOptionPane.PLAIN_MESSAGE,
				                    null,
				                    null,
				                    "File name");
			
			String filename = saveName + ".txt";
			
			FileWriter fw = new FileWriter (filename);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter pw = new PrintWriter(bw);
			
			pw.print(level);
			pw.println();
			pw.println();
			
			pw.print(scoreTotal);
			pw.println();
			pw.println();
			
			pw.print(piece);
			pw.println();
			pw.println();
			
			for(int x = 0; x<playingField.length; x++){
				for(int y = 0; y < playingField[x].length; y++){
					if(playingField[x][y] == null){
					pw.print(playingField[x][y] + ";");
					}
					else{
						pw.print(playingField[x][y].getRGB() + ";");
					}
				}
				pw.println();
			}
			
			pw.println();
			pw.print(count);
			
			
			JOptionPane.showMessageDialog(null,
			    "You have successfully saved your game!",
			    "Saved Game",
			    JOptionPane.PLAIN_MESSAGE);
			
			pw.close();
		}
		catch(IOException ex){
			JOptionPane.showMessageDialog(null,
			    "The file name you entered is invalid, please try again",
			    "Save Error",
			    JOptionPane.PLAIN_MESSAGE);
			
		}
		catch(Exception ge){
			JOptionPane.showMessageDialog(null,
			    "There was an error with your save, please do not try to save an empty board",
			    "Save Error",
			    JOptionPane.PLAIN_MESSAGE);
		}
		
	}
}
/*

	private class LoadListener implements ActionListener{
		
		public void actionPerformed(ActionEvent e){
			
			BufferedReader br = null;
			
			
			try{
				
				
				int count = 0;
				
				String file = loadName.getText() + ".txt";
			
				String currentLine; //current line being read
			
				br = new BufferedReader(new FileReader(file));
				
				while ((currentLine = br.readLine()) != null){
					//System.out.println(currentLine);
					count++;
					
					if(count == 1){
						System.out.println(currentLine);
						
						setLevel(Integer.parseInt(currentLine));
					}
					
					else if(count == 3){
						System.out.println(currentLine);
						setReadScore(Integer.parseInt(currentLine));
					}
					
					else if(count == 5){
						System.out.println(currentLine);
						String pieceType = currentLine;
						
						String[] tokens = pieceType.split("@");
						
						pieceType = tokens[0];
						System.out.println(pieceType);
						
						if(pieceType.equals("LPieceLeft")){
							pieceNum = 6;
							nextLabel.setIcon(lpieceleft);
						}
				}//end else
				
						else if(count == 7){
							System.out.println(currentLine);
							String[] tokens = currentLine.split(";");

							for(int x = 0; x<tokens.length; x++){
								if(!tokens[x].equals("null")){
									loadedGame[0][x] = new Color(Integer.parseInt(tokens[x]));
								}
							}

						}
						else if(count == 8){
							System.out.println(currentLine);
							String[] tokens = currentLine.split(";");

							for(int x = 0; x<tokens.length; x++){
								if(!tokens[x].equals("null")){
									loadedGame[0][x] = new Color(Integer.parseInt(tokens[x]));
								}
							}

						}
						else if(count == 9){
							System.out.println(currentLine);
							String[] tokens = currentLine.split(";");

							for(int x = 0; x<tokens.length; x++){
								if(!tokens[x].equals("null")){
									loadedGame[0][x] = new Color(Integer.parseInt(tokens[x]));
								}
							}

						}
						else if(count == 10){
							System.out.println(currentLine);
							String[] tokens = currentLine.split(";");

							for(int x = 0; x<tokens.length; x++){
								if(!tokens[x].equals("null")){
									loadedGame[0][x] = new Color(Integer.parseInt(tokens[x]));
								}
							}

						}
						else if(count == 11){
							System.out.println(currentLine);
							String[] tokens = currentLine.split(";");

							for(int x = 0; x<tokens.length; x++){
								if(!tokens[x].equals("null")){
									loadedGame[0][x] = new Color(Integer.parseInt(tokens[x]));
								}
							}

						}
						else if(count == 12){
							System.out.println(currentLine);
							String[] tokens = currentLine.split(";");

							for(int x = 0; x<tokens.length; x++){
								if(!tokens[x].equals("null")){
									loadedGame[0][x] = new Color(Integer.parseInt(tokens[x]));
								}
							}

						}
						else if(count == 13){
							System.out.println(currentLine);
							String[] tokens = currentLine.split(";");

							for(int x = 0; x<tokens.length; x++){
								if(!tokens[x].equals("null")){
									loadedGame[0][x] = new Color(Integer.parseInt(tokens[x]));
								}
							}

						}
						else if(count == 14){
							System.out.println(currentLine);
							String[] tokens = currentLine.split(";");

							for(int x = 0; x<tokens.length; x++){
								if(!tokens[x].equals("null")){
									loadedGame[0][x] = new Color(Integer.parseInt(tokens[x]));
								}
							}

						}
						else if(count == 15){
							System.out.println(currentLine);
							String[] tokens = currentLine.split(";");

							for(int x = 0; x<tokens.length; x++){
								if(!tokens[x].equals("null")){
									loadedGame[0][x] = new Color(Integer.parseInt(tokens[x]));
								}
							}

						}
						else if(count == 16){
							System.out.println(currentLine);
							String[] tokens = currentLine.split(";");

							for(int x = 0; x<tokens.length; x++){
								if(!tokens[x].equals("null")){
									loadedGame[0][x] = new Color(Integer.parseInt(tokens[x]));
								}
							}

						}
						

				
			} //end while
					 
				
				
			//	br.close();
			
		gameLoaded = true;
		
		br.close();
	}
		catch(IOException ex){
			
		}
		
	}
}
*/




	
	/*
	public static void main(String[] args){
		JFrame frame = new JFrame();
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 10 (wide) x 25 (tall) playing grid
        InfoPanel boardPanel = new InfoPanel();
		frame.add(boardPanel);

        frame.pack();
        frame.setVisible(true);

        //boardPanel.start();

    
	}
	*/
	
}