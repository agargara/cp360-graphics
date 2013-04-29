package face;

import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;

/**
 * Control class for Face Program.
 * This program allows the user to generate features of a
 * pseudo-human face and apply affine transformations to them.
 * 
 * This class also handles the creation of global Keyboard Shortcuts.
 */
public class FACE_DC {
	
	static FacePanel myPanel;
	
	/**
	 * Main method which draws a frame with a canvas and control panel.
	 */
    @SuppressWarnings("serial")
	public static void main(String[] args){
		JFrame myFrame = new JFrame();
		myFrame.setTitle("Draw a Face");

		// Set the window to close when the user clicks X:
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		myPanel = new FacePanel();					// Panel for drawing face
		ControlPanel myControls = new ControlPanel(myPanel);	// Panel for controls
		
		myFrame.setLayout(new BorderLayout());
		myFrame.add(myPanel, BorderLayout.WEST);
		myFrame.add(myControls, BorderLayout.EAST); 			// Place controls on right side
		myFrame.setSize(FacePanel.SIZE + ControlPanel.WIDTH,FacePanel.SIZE); 
		myFrame.setResizable(false);
		myFrame.setVisible(true);
		myFrame.setLocation(80, 40);
		
		// Create action mappings for keyboard shortcuts:
		myPanel.getActionMap().put("rotateLeft",new AbstractAction(){
			public void actionPerformed(ActionEvent e) {rotateLeft();}});
		myPanel.getActionMap().put("rotateRight",new AbstractAction(){
			public void actionPerformed(ActionEvent e) {rotateRight();}});
		myPanel.getActionMap().put("scaleVertUp",new AbstractAction(){
			public void actionPerformed(ActionEvent e) {scaleVertUp();}});
		myPanel.getActionMap().put("scaleHorizDown",new AbstractAction(){
			public void actionPerformed(ActionEvent e) {scaleHorizDown();}});
		myPanel.getActionMap().put("scaleVertDown",new AbstractAction(){
			public void actionPerformed(ActionEvent e) {scaleVertDown();}});
		myPanel.getActionMap().put("scaleHorizUp",new AbstractAction(){
			public void actionPerformed(ActionEvent e) {scaleHorizUp();}});
		myPanel.getActionMap().put("flipX",new AbstractAction(){
			public void actionPerformed(ActionEvent e) {flipX();}});
		myPanel.getActionMap().put("flipY",new AbstractAction(){
			public void actionPerformed(ActionEvent e) {flipY();}});
		myPanel.getActionMap().put("cycle",new AbstractAction(){
			public void actionPerformed(ActionEvent e) {cycle();}});
		
		// Map keyboard keys to the action mappings:
		myPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke('z'),"rotateLeft");
		myPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke('x'),"rotateRight");
		myPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke('w'),"scaleVertUp");
		myPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke('a'),"scaleHorizDown");
		myPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke('s'),"scaleVertDown");
		myPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke('d'),"scaleHorizUp");
		myPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke('f'),"flipX");
		myPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke('g'),"flipY");
		myPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke('q'),"cycle");
    }

    protected static void cycle() {
		myPanel.cycleSelected();
	}
    
	protected static void flipX() {
		myPanel.flipX();
	}
	
	protected static void flipY() {
		myPanel.flipY();
	}

	protected static void rotateLeft() {
		myPanel.rotateLeft();
	}
	
	protected static void rotateRight() {
		myPanel.rotateRight();
	}
	
	protected static void scaleVertUp() {
		myPanel.scaleVertUp();
	}
	
	protected static void scaleHorizDown() {
		myPanel.scaleHorizDown();
	}
	
	protected static void scaleVertDown() {
		myPanel.scaleVertDown();
	}
	
	protected static void scaleHorizUp() {
		myPanel.scaleHorizUp();
	}
} // FACE_DC

