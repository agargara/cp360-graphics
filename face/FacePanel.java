package face;

import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;
import javax.swing.*;

@SuppressWarnings("serial")
/**
 * This class is the panel on which facial features are drawn.
 * When painting, it loops through a linked list of facial features
 * and draws or fills them, applying the appropriate transforms.
 */
class FacePanel extends JPanel implements MouseListener, MouseMotionListener {

	public static final int SIZE = 640;			// Constant size of the face panel
	LinkedList<FacialFeature> facialFeatures;	// Linked list of all facial features.
	double rotationDegrees;						// The amount to rotate a shape (radians)
	double scaleDegrees;						// The amount to scale a shape
	int selectedItem;							// Keeps track of which feature is selected.
	int clickX, clickY;							// Keeps track of where mouse was last clicked.
	Color selectionColor = Color.BLUE;			// Color of selection box
	Color fillColor;							// Color of fill
	
	/**
	 * Constructs a face panel for drawing a face.
	 */
	public FacePanel(){
		this.setPreferredSize(new Dimension(SIZE-10, SIZE));
		selectedItem = 0;
		rotationDegrees = .2;
		scaleDegrees = 1.1;
		facialFeatures = new LinkedList<FacialFeature>();
		fillColor = Color.LIGHT_GRAY;
		addMouseListener(this);
		addMouseMotionListener(this);
	}

	/**
	 * Paints all the shapes on the screen.
	 */
	public void paintComponent(Graphics g){
 		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;
		g2d.setStroke(new BasicStroke(2));			// Set stroke width to 2
		
		for(FacialFeature i : facialFeatures){		// Loop through all features
			if(i.isFilled()){		// Fill feature if needed
				g2d.setPaint(i.getFillColor());
				g2d.fill(i.getTransformedFeature());
			}	
			g2d.setPaint(Color.BLACK);					// Set stroke color to black
			g2d.draw(i.getTransformedFeature());		// Outline shape
		}
		// Draw box around selected item
		if(!facialFeatures.isEmpty()){
			g2d.setPaint(selectionColor);
			g2d.draw(facialFeatures.get(selectedItem).getTransformedFeature().getBounds2D());
		}
	}
	
	/**
	 * Rotates the currently selected shape left.
	 */
	public void rotateLeft() {
		if(!facialFeatures.isEmpty()){
			facialFeatures.get(selectedItem).rotate(-rotationDegrees);
			repaint();
		}
	}
	
	/**
	 * Rotates the currently selected shape right.
	 */
	public void rotateRight() {
		if(!facialFeatures.isEmpty()){
			facialFeatures.get(selectedItem).rotate(rotationDegrees);
			repaint();
		}
	}
	
	/**
	 * Flips the current shape on the X-axis.
	 */
	public void flipX() {
		if(!facialFeatures.isEmpty()){
			facialFeatures.get(selectedItem).scale(-1, 1);
			repaint();
		}
	}
	
	/**
	 * Flips the current shape on the Y-axis.
	 */
	public void flipY() {
		if(!facialFeatures.isEmpty()){
			facialFeatures.get(selectedItem).scale(1, -1);
			repaint();
		}
	}
	
	/**
	 * The following methods simply scale the shape in different ways.
	 */
	public void scaleVertUp() {
		if(!facialFeatures.isEmpty()){
			facialFeatures.get(selectedItem).scale(1, scaleDegrees);
			repaint();
		}
	}
	public void scaleVertDown() {
		if(!facialFeatures.isEmpty()){
			facialFeatures.get(selectedItem).scale(1, (2-scaleDegrees));
			repaint();
		}
	}
	public void scaleHorizUp() {
		if(!facialFeatures.isEmpty()){
			facialFeatures.get(selectedItem).scale(scaleDegrees, 1);
			repaint();
		}
	}
	public void scaleHorizDown() {
		if(!facialFeatures.isEmpty()){
			facialFeatures.get(selectedItem).scale((2-scaleDegrees), 1);
			repaint();
		}
	}
		
	/**
	 * Adds a facial outline to this face panel.
	 */
	public void addFace() {
		facialFeatures.add(new Face());	 // Add a face
		selectedItem = facialFeatures.size() - 1;
		repaint();
	}
	
	/**
	 * Adds an eye to this face panel.
	 */
	public void addEye() {
		facialFeatures.add(new Eye());	// Add an eye
		selectedItem = facialFeatures.size() - 1;
		repaint();
	}
	
	/**
	 * Adds an eye to this face panel.
	 */
	public void addNose() {
		facialFeatures.add(new Nose());	// Add a nose
		selectedItem = facialFeatures.size() - 1;
		repaint();
	}
	
	/**
	 * Adds an eye to this face panel.
	 */
	public void addMouth() {
		facialFeatures.add(new Mouth());	// Add a mouth
		selectedItem = facialFeatures.size() - 1;
		repaint();
	}
	
	/**
	 * Adds an eye to this face panel.
	 */
	public void addEyebrow() {
		facialFeatures.add(new Eyebrow());	// Add an eyebrow
		selectedItem = facialFeatures.size() - 1;
		repaint();
	}
	
	/**
	 * Adds an eye to this face panel.
	 */
	public void addEar() {
		facialFeatures.add(new Ear());	// Add an ear
		selectedItem = facialFeatures.size() - 1;
		repaint();
	}
	
	/**
	 * Selects the next element (wraps around).
	 * Places the newly selected item at the end of the list.
	 */
	public void cycleSelected(){
		if(!facialFeatures.isEmpty()){
			selectedItem = (selectedItem + 1)%facialFeatures.size();
			repaint();
		}
	}
	
	/**
	 * Uses the current fill color to fill the currently
	 * selected shape on the screen.
	 */
	public void fillSelected(){
		facialFeatures.get(selectedItem).fill(fillColor);
		repaint();
	}

	/**
	 * Deletes the currently selected item.
	 */
	public void delete() {
		if(!facialFeatures.isEmpty()){
			facialFeatures.remove(selectedItem);
			repaint();
			if(!facialFeatures.isEmpty())
				selectedItem = selectedItem % facialFeatures.size();
		}
	}
	
	/**
	 * Changes the current fill color.
	 * @param color the color to change to
	 */
	public void updateColor(Color _color) {
		fillColor = _color;
	}
	
	@Override
	/**
	 * Drag the currently selected shape when the mouse is dragged.
	 */
	public void mouseDragged(MouseEvent e) {
		if(!facialFeatures.isEmpty()){
			int moveX = e.getX() - clickX;	// Translate based on
			int moveY = e.getY() - clickY;	// Previous location of the mouse
			facialFeatures.get(selectedItem).translate(moveX, moveY);
			repaint();
			clickX = e.getX();
			clickY = e.getY();
		}
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {}
	@Override
	public void mouseClicked(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
	
	@Override
	/**
	 * When the mouse is pressed, get the location of the mouse.
	 */
	public void mousePressed(MouseEvent e) {
		clickX = e.getX();
		clickY = e.getY();
	}
	@Override
	public void mouseReleased(MouseEvent e) {}
}
