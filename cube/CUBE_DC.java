package cube;

import java.awt.*;
import javax.swing.*;

/**
 * Control class for Cube Program.
 * Draws a cube on the screen.
 */
public class CUBE_DC {
	
	public static final double ROTATION_SPEED = Math.PI * 2;	// Global speed of rotation.
	
	/**
	 * Main method which draws a frame with a canvas and control panel.
	 */
	public static void main(String[] args){
		JFrame myFrame = new JFrame();
		myFrame.setTitle("CUBE");

		// Set the window to close when the user clicks X:
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		CubePanel myPanel = new CubePanel();					// Panel for drawing face
		ControlPanel myControls = new ControlPanel(myPanel);	// Panel for controls
		
		myFrame.setLayout(new BorderLayout());
		myPanel.setBackground(Color.BLACK);
		myFrame.add(myPanel, BorderLayout.WEST);				// Draw cube on left side
		myFrame.add(myControls, BorderLayout.EAST); 			// Place controls on right side
		myFrame.pack();
		myFrame.setResizable(false);
		myFrame.setVisible(true);
		myFrame.setLocation(80, 40);		// Move window down
    }
}