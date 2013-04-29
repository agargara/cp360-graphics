/**
 *  This class is a panel on which we will draw three intersecting circles.
 */

import java.awt.*;
import java.awt.geom.*;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ColorPanel extends JPanel {

	int red, blue, green;							// Represent RGB components
	
 	/**
 	 * Constructs a canvas for drawing
 	 */
 	public ColorPanel() {
 		setPreferredSize(new Dimension(250,250));	// Resize the panel.
 	}
 	 
 	/**
 	 * Draws the three circles and fills their intersections.
 	 */
 	public void paintComponent(Graphics g){
 		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;

		/** Shape the circles: */
		int size = 120;						// Size of circles
		size += (Math.random()*10 - 5);		// Randomly alter size every time we repaint (for fun)
		Ellipse2D.Float redCircle = new Ellipse2D.Float(10, 10, size, size);
		Ellipse2D.Float blueCircle = new Ellipse2D.Float(90, 10, size, size);
		Ellipse2D.Float greenCircle = new Ellipse2D.Float(50, 60, size, size);
		
		/** Fill the circles: */
		g2d.setPaint(new Color(red, 0, 0));			// Draw red circle
		g2d.fill(redCircle);
		g2d.setPaint(new Color(0, green, 0));		// Draw green circle
		g2d.fill(greenCircle);
		g2d.setPaint(new Color(0, 0, blue));		// Draw blue circle
		g2d.fill(blueCircle);
		
		g2d.setPaint(new Color(red, green, 0));		// Draw RG intersection
		Area rg = new Area(redCircle);
		rg.intersect(new Area(greenCircle));
		g2d.fill(rg);
		
		g2d.setPaint(new Color(red, 0, blue));		// Draw RB intersection
		Area rb = new Area(redCircle);
		rb.intersect(new Area(blueCircle));
		g2d.fill(rb);
		
		g2d.setPaint(new Color(0, green, blue));	// Draw GB intersection
		Area gb = new Area(greenCircle);
		gb.intersect(new Area(blueCircle));
		g2d.fill(gb);
		
		g2d.setPaint(new Color(red, green, blue));	// Draw RGB intersection
		Area rgb = new Area(redCircle);
		rgb.intersect(new Area(greenCircle));
		rgb.intersect(new Area(blueCircle));
		g2d.fill(rgb);
	 }

 	/**
 	 * Changes the three RGB components and repaints.
 	 * @param redComp the R component
 	 * @param greenComp the G component
 	 * @param blueComp the B component
 	 */
	public void changeColor(int redComp, int greenComp, int blueComp) {
		red = redComp;
		green = greenComp;
		blue = blueComp;
		repaint();
	}
}// ColorCanvas
