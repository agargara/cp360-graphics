package sphere;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

/**
 * This is the panel that contains and draws the sphere. 
 * It shades the sphere based on the location of a light source.
 * @author David
 *
 */
@SuppressWarnings("serial")
public class SpherePanel extends JPanel implements MouseMotionListener, MouseListener {
	
	Sphere mySphere;				// The sphere to shade
	Color sphereColor;				// Color of sphere
	BufferedImage sphereImage;		// Buffered image that represents the sphere
	float hue;						// Hue of sphere
	
	/**
	 * Constructs a Sphere Panel
	 */
	public SpherePanel(){
		mySphere = new Sphere();
		setPreferredSize(new Dimension(800, 600));
		addMouseMotionListener(this);
		addMouseListener(this);
		setBackground(Color.black);
		hue = (float) 0;
	}
	
	/**
	 * Paints the sphere
	 */
	public void paintComponent(Graphics g){
 		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;
		int r = mySphere.getRadius();							// Get radius
		g2d.translate((getWidth()/2) - r, (getHeight()/2) -r);	// Move to center
		
		// Make the buffered image
		sphereImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB); 
		
		// Loop through points on circle:
		for (int y=-r; y<r; y++){					// Loop through all y values
			int xBound = (int)Math.sqrt(r*r - y*y);	// Calculate x bounds
			for (int x=-xBound; x<xBound; x++){
				double illumination = mySphere.getIllumination(x, y);	// Calculate illumination
				if (illumination > 1)
					illumination = 1;
				if (illumination > 0)
					sphereColor = Color.getHSBColor(hue, 1, (float)(illumination));
				else
					sphereColor = Color.black;
				sphereImage.setRGB(x+r, y+r, sphereColor.getRGB());
			}
		}
		g2d.drawImage(sphereImage, null, 0, 0);
	}
	
	public void mouseMoved(MouseEvent e) {
		mySphere.setLightLocation(e.getX() - (getWidth()/2), e.getY() - (getHeight()/2));
		hue+=0.001;
		repaint();
	}
	public void mouseDragged(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseClicked(MouseEvent arg0) {}
	public void mouseEntered(MouseEvent arg0) {}
	public void mouseExited(MouseEvent arg0)  {}
	public void mouseReleased(MouseEvent arg0) {}
}
