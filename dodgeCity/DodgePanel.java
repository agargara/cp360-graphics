package dodgeCity;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;
import javax.swing.*;
import javax.sound.sampled.*;

/**
 * This is the panel that contains and draws the cube. It uses
 * mouse input to rotate the cube on the x-y axis.
 * @author David
 *
 */
@SuppressWarnings("serial")
public class DodgePanel extends JPanel{
	Clip clip;						// The music clip
	boolean mute;					// True to mute music
	boolean gameOver;				// True upon failure
	boolean restarting;				// True if restarting game
	boolean playingSFX;				// True when playing sound fx
	int score;						// Total score
	int highScore;					// High score
	double eye = DodgeCity.ROOM_SIZE_Z;	// z-location of eye
	SortedSet<Cube> myCubes;		// Sorted set of falling cubes
	Player player;					// The player
	CubeThread myCubeThread;		// Thread for moving cubes
	AnimationThread animThread;		// Thread for animating
	long framesElapsed;				// Helps keep track of frames per second
	long timer;						// Keeps track of time elapsed
	double fps;						// Frames per second
	
	// Fonts for text:
	Font defaultFont = new Font("Helvetica", Font.PLAIN, 12);
	Font bigFont = new Font("Helvetica", Font.BOLD, 72);
	
	/**
	 * Constructs a Cube Panel
	 */
	public DodgePanel(){
		gameOver = false;
		restarting = true;
		playingSFX = false;
		mute = false;
		score = 0;
		highScore = 0;
		// Try to read in the high score file
		try{
		    FileReader fstream = new FileReader("highscore");
		        BufferedReader in = new BufferedReader(fstream);
		    String score = in.readLine();
		    in.close();
		    highScore = Integer.parseInt(score);
	 	}catch (Exception e){
		    	e.printStackTrace();
		}
		
		
		// Instantiate objects
		myCubes = Collections.synchronizedSortedSet(new TreeSet<Cube>());
		player = new Player(0, 0, 0, 16, eye);
		player.rotate(Math.PI/4, 0, 0);	// Rotate player slightly
		
		// Make and start threads:
		myCubeThread = new CubeThread(this, myCubes, player, eye);
		animThread = new AnimationThread(this);
		new Thread(animThread).start();
		
		// Start timer for fps
		framesElapsed = 0;
		timer = System.nanoTime();
		
		// Add keyboard bindings:
		getActionMap().put("w",new AbstractAction(){
			public void actionPerformed(ActionEvent e) {handleKey('w');}});
		getActionMap().put("a",new AbstractAction(){
			public void actionPerformed(ActionEvent e) {handleKey('a');}});
		getActionMap().put("s",new AbstractAction(){
			public void actionPerformed(ActionEvent e) {handleKey('s');}});
		getActionMap().put("d",new AbstractAction(){
			public void actionPerformed(ActionEvent e) {handleKey('d');}});
		getActionMap().put("released w",new AbstractAction(){
			public void actionPerformed(ActionEvent e) {handleKey('W');}});
		getActionMap().put("released a",new AbstractAction(){
			public void actionPerformed(ActionEvent e) {handleKey('A');}});
		getActionMap().put("released s",new AbstractAction(){
			public void actionPerformed(ActionEvent e) {handleKey('S');}});
		getActionMap().put("released d",new AbstractAction(){
			public void actionPerformed(ActionEvent e) {handleKey('D');}});
		getActionMap().put("space",new AbstractAction(){
			public void actionPerformed(ActionEvent e) {handleKey('r');}});
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke('w'),"w");
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke('a'),"a");
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke('s'),"s");
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke('d'),"d");
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke("released W"),"released w");
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke("released A"),"released a");
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke("released S"),"released s");
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke("released D"),"released d");
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke("UP"),"w");
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke("LEFT"),"a");
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke("DOWN"),"s");
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke("RIGHT"),"d");
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke("released UP"),"released w");
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke("released LEFT"),"released a");
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke("released DOWN"),"released s");
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke("released RIGHT"),"released d");
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke("SPACE"),"space");
		
		// Resize window
		setPreferredSize(new Dimension(800, 600));
	}
	
	private void playSound(String fileName) {
		if(!mute){
			try {
			    File musicFile = new File(fileName);	// Load sound file
			    AudioInputStream sound;					// Open input stream:
				sound = AudioSystem.getAudioInputStream(musicFile);
				// load the sound into a Clip
			    DataLine.Info info = new DataLine.Info(Clip.class, sound.getFormat());
			    clip = (Clip) AudioSystem.getLine(info);
			    clip.open(sound);
			    
			    // Add a listener to stop or loop the music once it stops
			    clip.addLineListener(new LineListener() {
				      public void update(LineEvent event) {
				        if (event.getType() == LineEvent.Type.STOP) {
				        	event.getLine().close();
				        	if (restarting){
				        		if(playingSFX){	// If the start sound is stopping, start the music looping
				        			playingSFX = false;
				        			playSound("music.wav");
				        			restart();
				        		}
				        	} else {
				        		if(!playingSFX && !gameOver){	// If the music is looping, loop
				        			playSound("music.wav");
				        		}
				        	}
				        }
				      }
				    });
			    // play the sound clip
			    clip.start();
			} catch (FileNotFoundException e){
				System.err.println("The music was not found! Where did it go?");
			} catch (UnsupportedAudioFileException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (LineUnavailableException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Paint the game scene.
	 */
	public void paintComponent(Graphics g){
 		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;
		if(gameOver || restarting){
			g2d.setPaint(Color.LIGHT_GRAY);
			g2d.fillRect(0, 0, getWidth(), getHeight());
			g2d.setPaint(Color.BLACK);
			if(gameOver){
				g2d.setFont(defaultFont);
				g2d.drawString("DEATH", (int)(getWidth()/2), (int)(getHeight()/2.1));
				g2d.drawString("Your score: "+score, (int)(getWidth()/2.3), (int)(getHeight()/2)+14);
				g2d.drawString("High score: "+highScore, (int)(getWidth()/2.3), (int)(getHeight()/2)+28);
				g2d.drawString("Press SPACE to try again", (int)(getWidth()/2.3), (int)(getHeight()/2)+80);
				
				if (score == highScore){
					if(Math.random() > 0.5)
						g2d.drawString("NEW HIGH SCORE!", (int)(getWidth()/2.3), (int)(getHeight()/2)+42);
				}
			}else{
				g2d.setFont(bigFont);
				if(Math.random() > 0.10)
					g2d.setColor(Color.BLACK);
				else
					g2d.setColor(new Color((int)(Math.random()*255), (int)(Math.random()*255), (int)(Math.random()*255)));
				g2d.drawString("DODGE CITY", (int)(getWidth()/8), (int)(getHeight()/2));
				g2d.setColor(Color.BLACK);
				g2d.setFont(defaultFont);
				g2d.drawString("use arrows or wasd to move", (int)(getWidth()/2.3), (int)(getHeight()/2)+20);
				g2d.drawString("press SPACE to start", (int)(getWidth()/2.3), (int)(getHeight()/2)+40);

			}
		}else{
			// Draw fps counter and score:
			drawFPS(g2d);
			// Draw scene:
			g2d.translate(getWidth()/2, getHeight() / 1.2);		// Translate origin to center and a little down.
			g2d.scale(1, -1);									// Flip vertically.
			drawScene(g2d);
		}
	}

	/**
	 * Computes and draws the approximate frames per second that the paintComponent is redrawn.
	 * @param g2d
	 */
	private void drawFPS(Graphics2D g2d) {
		framesElapsed++;									// Increment frame counter
		long elapsedTime = System.nanoTime() - timer;		// Compute elapsed time
		fps = (double)framesElapsed / (elapsedTime / 1000000000.0);	// frames / second
		g2d.setPaint(Color.WHITE);
		g2d.drawString("fps: "+ new DecimalFormat("0.00").format(fps), 2, 12);	// Format to 2 decimal places
		if ((elapsedTime / 1000000000.0) > 8)				// Reset counter every 8 seconds
			resetCounter();
		
		// Draw score:
		g2d.drawString("high score: "+ highScore, 2, 24);
		g2d.drawString("your score: "+ score, 2, 36);
	}

	/**
	 * Resets the counter for fps
	 */
	private void resetCounter() {
		framesElapsed = 0;
		timer = System.nanoTime();
	}
	
	/**
	 * Handles key events by calling the appropriate methods.
	 * @param c the key to handle
	 */
	private void handleKey(char c) {
		switch(c){
			case 'w' :
				player.move(0, -1);
				break;
			case 'a' :
				player.move(-1, 0);
				break;
			case 's' :
				player.move(0, 1);
				break;
			case 'd' :
				player.move(1, 0);
				break;
			case 'W' :
				player.stopZ();
				break;
			case 'A' :
				player.stopX();
				break;
			case 'S' :
				player.stopZ();
				break;
			case 'D' :
				player.stopX();
				break;
			case 'r' :					// Restart or start game:
				if(restarting){
					playingSFX = true;
					playSound("start.wav");
				}else if(gameOver){
					restarting = true;
					playingSFX = true;
					playSound("start.wav");
				}
					
				break;
		}
	}
	
	public void drawScene(Graphics2D g2d) {
		drawRoom(g2d);										// Draw the walls and floor.
		drawShadows(g2d);									// Draw all shadows.	
		drawPlayer(g2d);									// Draw the player.
		drawCubes(g2d);										// Draw all cubes.	
	}
	

	/**
	 * Draws all the shadows on the screen.
	 * @param g2d
	 */
	private void drawShadows(Graphics2D g2d) {
		synchronized(myCubes){
			for(Cube myCube : myCubes){
				double darkness = myCube.getShadowDarkness();
				Color shadowColor = new Color(0, 0, 0, (float)darkness);
				g2d.setPaint(shadowColor);
				Shape myShadow = myCube.makeShadow();
				if (myShadow != null)
					g2d.fill(myShadow);
			}
		}
	}
	/**
	 * Draws all the cubes on the screen.
	 * @param g2d
	 */
	private void drawCubes(Graphics2D g2d) {
		synchronized(myCubes){
			TreeSet<Cube> toRemove = new TreeSet<Cube>();
			for(Cube myCube : myCubes){
				if(myCube.center[1] < (-myCube.scale)){		// Remove cubes if they fall off screen
					score++;					// Increase score
					if (score > highScore)		// Increase high score 
						highScore = score;
					toRemove.add(myCube);		// Mark cube for removal (cannot remove right away, or will throw ConcurrentModificationException)
				}else{
					SortedSet<Face> myFaces = myCube.getFaces();		// Get transformed, visible faces from myCube.
					for (Face f : myFaces){							// Draw all faces.
						g2d.setPaint(f.getColor());
						g2d.fill(myCube.makePolygon(f));
					}
				}
			}
			for (Cube c : toRemove)		// Remove cubes that were marked for deletion
				myCubes.remove(c);
		}
	}
	
	/**
	 * Draws the player character.
	 * @param g2d
	 */
	private void drawPlayer(Graphics2D g2d) {
		SortedSet<Face> myFaces = player.getFaces();
		for (Face f : myFaces){							// Draw all faces.
			g2d.setStroke(new BasicStroke(2));	// Thicken the stroke
			g2d.setPaint(Color.BLACK);			// Draw a black outline
			g2d.draw(player.makePolygon(f));	// Stroke the outline
			g2d.setPaint(f.getColor());
			g2d.fill(player.makePolygon(f));
		}
	}
	
	/**
	 * Draws the floor and walls of the room.
	 * @param g2d
	 */
	private void drawRoom(Graphics2D g2d) {
		double s = DodgeCity.ROOM_SIZE_X;
		Cube floor = new Cube(0, s/2, 0, s/2, eye);
		g2d.setPaint(Color.LIGHT_GRAY);
		g2d.fill(player.makePolygon(floor.getFace(5))); // Get bottom face
	}

	/**
	 * Loses the game
	 */
	public void lose() {
		clip.stop();		// Stop music on game over
		gameOver = true;
		// Write high score to output file
		if (score >= highScore){
			try{
			    FileWriter fstream = new FileWriter("highscore");
			        BufferedWriter out = new BufferedWriter(fstream);
			    out.write(""+highScore);
			    out.close();
		 	}catch (Exception e){
			    	e.printStackTrace();
			}
		}
	}
	
	/**
	 * Starts a new game
	 */
	private void restart(){
		score = 0;					// Reset score
		myCubes.removeAll(myCubes);	// Clear cubes
		myCubeThread = new CubeThread(this, myCubes, player, eye);
		restarting = false;
		gameOver = false;
		new Thread(myCubeThread).start();
	}
}
