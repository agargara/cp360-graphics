import java.awt.*;
import java.awt.geom.Path2D;
import javax.swing.*;

/**
 * Draws some nice looking gradient shapes.
 */
public class ShadedTriangles{
	
	/**
	 * Main method simply draws the frame.
	 */
    public static void main(String[] args){
		JFrame myFrame = new JFrame();
		myFrame.setTitle("Shaded Triangles");

		// Set the window to close when the user clicks X:
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		ShadingPanel myPanel = new ShadingPanel();
		myFrame.add(myPanel);
		myFrame.pack();
		myFrame.setResizable(false);
		myFrame.setVisible(true);
    }
} // ShadedTriangles

@SuppressWarnings("serial")
class ShadingPanel extends JPanel {
	int size;
	
	public ShadingPanel(){
		size = 700;
		setPreferredSize(new Dimension(size,size));	// Resize the canvas.
	}
	
	public void paintComponent(Graphics g){
 		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;
		
		// Fill the background: 
		GradientPaint background = new GradientPaint(
				0, 0, Color.BLACK, 0, size, Color.BLUE);
		g2d.setPaint(background);
		g2d.fillRect(0, 0, size, size);
		
		// Fill the large triangle:
		Path2D.Float largeTriangle = new Path2D.Float();
		largeTriangle.moveTo(size/2, 0);
		largeTriangle.lineTo(0, size);
		largeTriangle.lineTo(size, size);
		GradientPaint background2 = new GradientPaint(
				0, 0, Color.BLUE, 0, size, Color.BLACK);
		g2d.setPaint(background2);
		g2d.fill(largeTriangle);
		
		// Fill the small triangle:
		Path2D.Float smallTriangle = new Path2D.Float();
		smallTriangle.moveTo(size/2, size);
		smallTriangle.lineTo(size/4, size/2);
		smallTriangle.lineTo(size/2 + size/4, size/2);
		background = new GradientPaint(
				0, size/2, Color.BLACK, 0, size, Color.BLUE);
		g2d.setPaint(background);
		g2d.fill(smallTriangle);
	 }
}

