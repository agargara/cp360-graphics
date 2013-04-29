package cube;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.LinkedList;

import javax.swing.JPanel;

/**
 * This is the panel that contains and draws the cube. It uses
 * mouse input to rotate the cube on the x-y axis.
 * @author David
 *
 */
@SuppressWarnings("serial")
public class CubePanel extends JPanel implements MouseMotionListener, MouseListener {
	
	Cube myCube;				// The cube to rotate
	boolean rotating;			// True if the cube is rotating with the mouse
	
	/**
	 * Constructs a Cube Panel
	 */
	public CubePanel(){
		myCube = new Cube();
		rotating = false;
		setPreferredSize(new Dimension(800, 600));
		addMouseMotionListener(this);
		addMouseListener(this);
	}
	
	/**
	 * Paint the cube by painting each face
	 */
	public void paintComponent(Graphics g){
 		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;
		g2d.translate(getWidth()/2, getHeight()/2);			// Translate to center.
		g2d.scale(1, -1);									// Flip vertically.
		
		LinkedList<Face> myFaces = myCube.getFaces();	// Get faces from myCube.
		for (int i=0; i<myFaces.size(); i++){			// Draw every face.
			if(myCube.isWireframe())
				g2d.draw(myCube.makePolygon(myFaces.get(i)));
			else{
				g2d.setPaint(myFaces.get(i).getColor());
				g2d.fill(myCube.makePolygon(myFaces.get(i)));
			}
		}
	}
	/**
	 * Rotate the cube on the x-y axis when the mouse 
	 * is moved, based on the distance of the mouse from the center.
	 */
	public void mouseMoved(MouseEvent e) {
		if(rotating){
			double xRotation = -( CUBE_DC.ROTATION_SPEED *
									((e.getX() - (getWidth()/2.0)) / getWidth()));
			double yRotation = -( CUBE_DC.ROTATION_SPEED *
									((e.getY() - (getHeight()/2.0)) / getHeight()));
			myCube.rotateXY(xRotation, yRotation);
			repaint();
		}
	}
	
	public void mouseDragged(MouseEvent e) {}

	/**
	 * Turn wireframe on/off
	 */
	public void toggleWireframe() {
		myCube.toggleWireframe();
	}

	/**
	 * Toggle rotation when mouse is clicked
	 */
	public void mousePressed(MouseEvent e) {
		rotating = !rotating;
	}
	
	public void mouseClicked(MouseEvent arg0) {}
	public void mouseEntered(MouseEvent arg0) {}
	public void mouseExited(MouseEvent arg0)  {}
	public void mouseReleased(MouseEvent arg0) {}

	
	/**
	 * Updates the arbitrary axis, as long as not all components are 0.
	 * @param axisX the x component
	 * @param axisY the y component
	 * @param axisZ the z component
	 * @param theta the angle to rotate
	 */
	public void updateAxis(double axisX, double axisY, double axisZ, double theta) {
		if (!(axisX == 0 && axisY == 0 && axisZ == 0)){
			myCube.updateAxis(axisX, axisY, axisZ, theta);
			repaint();
		}
	}

	/**
	 * Scales the cube
	 * @param value the scale amount
	 */
	public void scale(int value) {
		myCube.scale(value);
		repaint();
	}
}
