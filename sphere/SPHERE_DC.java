package sphere;

import javax.swing.*;

/**
 * Control class for Sphere Program.
 * Draws a sphere on the screen.
 */
public class SPHERE_DC {
	/**
	 * Main method which draws a frame with a canvas and control panel.
	 */
	public static void main(String[] args){
		JFrame myFrame = new JFrame();
		myFrame.setTitle("SPHERE");

		// Set the window to close when the user clicks X:
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myFrame.add(new SpherePanel());		// Panel for drawing sphere
		myFrame.pack();
		myFrame.setResizable(false);
		myFrame.setVisible(true);
		myFrame.setLocation(80, 40);		// Move window down
    }
}