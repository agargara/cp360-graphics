import java.awt.*;
import javax.swing.JComponent;

@SuppressWarnings("serial")
public class TreePanel extends JComponent {
	
	int iterations;			// Number of iterations
	double theta;			// Default angle to branch from
	double length;			// Default branch length
	double scale;			// Default amount to scale branch
	
	public TreePanel(){
		iterations = 16;
		theta = (Math.PI / 16);
		length = 64;
		scale = 1.1;
		setPreferredSize(new Dimension(800, 600));
	}
	
	/**
	 * Paint the sphere
	 */
	public void paintComponent(Graphics g){
 		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;
		drawTree(g2d, getWidth()/2, getHeight(), Math.PI / 2, 0, length);
	}

	private void drawTree(Graphics2D g2d, int x, int y, double dir, int level, double length) {
		if (level < iterations){
			int strokeSize = iterations - (level*2);	// Calculate stroke size
			if (strokeSize < 1)	// Stop stroke size shrinking at 1
				strokeSize = 1;
			g2d.setStroke(new BasicStroke(strokeSize));
			if(level > 10)
				g2d.setPaint(new Color(20, 120, 20));
			else
				g2d.setPaint(new Color(85, 0, 0));
			int x2 = x + (int)(length * Math.cos(dir));
			int y2 = y - (int)(length * Math.sin(dir));
			g2d.drawLine(x, y, x2, y2);
			drawTree(g2d, x2, y2, dir + theta, level+1, length/scale);
			drawTree(g2d, x2, y2, dir - theta, level+1, length/scale);
		}
	}
}
