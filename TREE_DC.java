import javax.swing.JFrame;

public class TREE_DC {
	/**
	 * Main method which draws a frame with a canvas and control panel.
	 */
	public static void main(String[] args){
		JFrame myFrame = new JFrame();
		myFrame.setTitle("TREE");

		// Set the window to close when the user clicks X:
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myFrame.add(new TreePanel());		// Panel for drawing tree
		myFrame.pack();
		myFrame.setResizable(false);
		myFrame.setVisible(true);
		myFrame.setLocation(80, 40);		// Move window down
    }
}
