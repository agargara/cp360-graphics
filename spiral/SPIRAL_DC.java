package spiral;

/**
 * Control class for Spiraling Squares.
 * This program draws concentric squares that spiral inward.
 * The user can alter the number of squares on the board,
 * the number of iterations, and lambda (the amount of rotation.)
 */

import java.awt.*;

import javax.swing.*;

public class SPIRAL_DC{
	
	/**
	 * Main method, which simply draws a frame with a canvas and control panel.
	 */
    public static void main(String[] args){
		JFrame myFrame = new JFrame();
		myFrame.setTitle("Spiraling Squares");

		// Set the window to close when the user clicks X:
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		SquarePanel myPanel = new SquarePanel(600);		// Panel for drawing squares

		myFrame.setLayout(new BorderLayout());
		myFrame.add(myPanel, BorderLayout.WEST);
		myFrame.add(new ControlPanel(myPanel), BorderLayout.EAST); 	// Place controls on right side
		myFrame.setSize(900,600); 
		myFrame.setResizable(false);
		myFrame.setVisible(true);
		myFrame.setLocation(80, 40);
		
		// Draw separate color chooser window:
		JFrame colorChooserFrame = new JFrame();
		colorChooserFrame.setTitle("Choose Your Colors");
		colorChooserFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ColorChooserPanel colorChooser = new ColorChooserPanel(myPanel);
		colorChooserFrame.add(colorChooser);
		colorChooserFrame.setSize(450,550); 
		colorChooserFrame.setResizable(false);
		colorChooserFrame.setVisible(true);
		colorChooserFrame.setLocation(700, 40);
    }
} // SPIRAL_DC

