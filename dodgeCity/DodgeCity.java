package dodgeCity;

import java.awt.Color;

import javax.swing.*;

/**
 * Control class for the Dodge Game.
 * Draws the game frame on the screen.
 */
public class DodgeCity {
	// Constants for the room size:
	public static final double ROOM_SIZE_X = 1280;
	public static final double ROOM_SIZE_Y = 512;
	public static final double ROOM_SIZE_Z = 1280;
	
	/**
	 * Main method which draws a frame with a canvas and control panel.
	 */
	public static void main(String[] args){
		JFrame myFrame = new JFrame();
		myFrame.setTitle("DODGE CITY");
		// Set the window to close when the user clicks X:
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Panel for drawing game
		DodgePanel myDodgePanel = new DodgePanel();
		int gray = 30;
		myDodgePanel.setBackground(new Color(gray, gray/2, gray/4));
		
		myFrame.add(myDodgePanel);		
		myFrame.pack();
		myFrame.setResizable(true);
		myFrame.setVisible(true);
		myFrame.setLocation(80, 40);	// Move window down
    }
}
