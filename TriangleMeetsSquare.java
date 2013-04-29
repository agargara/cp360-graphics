
/**
 * A simple program that lets the user to draw a triangle 
 * with three squares attached to each side.
 *
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TriangleMeetsSquare{
	
	/**
	 * Main method simply draws the frame.
	 */
    public static void main(String[] args){
		TriangleCanvas myCanvas = new TriangleCanvas();
		JFrame myFrame = new JFrame();
		myFrame.setTitle("A Triangle and Three Squares");

		// Set the window to close when the user clicks X:
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		myFrame.add(myCanvas);
		myFrame.pack(); 
		myFrame.setResizable(true);
		myFrame.setVisible(true);
    }
} // TriangleMeetsSquare

@SuppressWarnings("serial")
class TriangleCanvas extends JPanel implements MouseListener, MouseMotionListener {

	Color[] myColors;
	int x1, x2, y1, y2;		// Coordinates of triangle hypotenuse
	
 	/**
 	 * Constructs a canvas for drawing a triangle
 	 */
 	public TriangleCanvas () {
 		myColors = new Color[4];				// Create an array of colors to use.
 		for(int i=1; i<=myColors.length; i++)	// Initialize the array.
 			myColors[i-1] = new Color(i%255, (i%2)*200, (i*64)%255);
 		x1=x2=y1=y2=0;							// Initialize line coordinates.
 		setPreferredSize(new Dimension(820,410));	// Resize the canvas.
 		setBackground(Color.lightGray);
 		addMouseListener(this);
 		addMouseMotionListener(this);
 	}
 	 
 	/**
 	 * Draws the required shapes
 	 */
 	public void paintComponent(Graphics g){
 		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;
		
		// Create the triangle based on the hypotenuse:
		int[] xCoords = {x1, x1, x2, 0};
		int[] yCoords = {y1, y2, y2, 0};
		Polygon triangle = new Polygon(xCoords,yCoords,3);
		g2d.setPaint(myColors[0]);
		g2d.fillPolygon(triangle);
		
		Polygon myRect;		// Will represent rectangles
		int width = x2 - x1;
		int height = y2 - y1;
		
		// Draw the accompanying rectangles:
		g2d.setPaint(myColors[1]);	// Hypotenuse rectangle
		xCoords = new int[] {x1, x2, x2+height, x1+height};
		yCoords = new int[] {y1, y2, y2-width, y1-width};
		myRect = new Polygon(xCoords,yCoords,4);
		g2d.fill(myRect);
		
		g2d.setPaint(myColors[2]);	// Vertical rectangle
		xCoords = new int[] {x1, x1, x1-height, x1-height};
		yCoords = new int[] {y1, y2, y2, y1};
		myRect = new Polygon(xCoords,yCoords,4);
		g2d.fill(myRect);
		
		g2d.setPaint(myColors[3]);	// Horizontal rectangle
		xCoords = new int[] {x1, x2, x2, x1};
		yCoords = new int[] {y2, y2, y2+width, y2+width};
		myRect = new Polygon(xCoords,yCoords,4);
		g2d.fill(myRect);
	 }
	 
	 public void mouseClicked(MouseEvent e){}	 
	 public void mouseEntered(MouseEvent e){}
	 public void mouseExited(MouseEvent e){} 
	 public void mousePressed(MouseEvent e){
		 x1 = e.getX();	// Use mouse location for first point coordinates
		 y1 = e.getY();	
		 x2 = y2 = 0;	// Reinitalize second point on line
		// Cycle through the available colors when dragging the mouse:
		 Color temp = myColors[0];
		 for(int i=0; i<myColors.length-1; i++)
			 myColors[i] = myColors[i+1];
		 myColors[myColors.length-1] = temp;
		repaint();
	 }	
	 
	 public void mouseReleased(MouseEvent e){}

	public void mouseDragged(MouseEvent e) {
		x2 = e.getX();	// When dragging, update the second point
		y2 = e.getY();	// coordinates for the line	
		repaint();
	}

	public void mouseMoved(MouseEvent e) {}	
}// TriangleCanvas