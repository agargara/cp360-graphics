
/**
 * Draws three overlapping circles that represent R, G and B components.
 * The intersections display the combination of the intersecting colors.
 *
 */

import java.awt.BorderLayout;
import javax.swing.*;

public class ColorTest{
	
	/**
	 * Main method simply draws the frame.
	 */
    public static void main(String[] args){
		JFrame myFrame = new JFrame();
		myFrame.setTitle("Color Test");

		// Set the window to close when the user clicks X:
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		ColorPanel myPanel = new ColorPanel();

		myFrame.setLayout(new BorderLayout());
		myFrame.add(myPanel, BorderLayout.WEST);
		myFrame.add(new ControlPanel(myPanel), BorderLayout.EAST);
		myFrame.setSize(500,250); 
		myFrame.setResizable(false);
		myFrame.setVisible(true);
    }
} // ColorTest

