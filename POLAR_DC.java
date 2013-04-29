import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class POLAR_DC {
	public static void main(String[] args){
		JFrame myFrame = new JFrame();
		myFrame.setTitle("Polar Coordinates");

		// Set the window to close when the user clicks X:
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		PolarPanel myPanel = new PolarPanel();
		myFrame.add(myPanel);
		myFrame.setSize(800,700); 
		myFrame.setResizable(false);
		myFrame.setVisible(true);
    }
}

@SuppressWarnings("serial")
class PolarPanel extends JPanel{
	
	int iterations, scaleFactor, equationType;
	double a, increment;
	
	public PolarPanel(){
		equationType = 9;	// Type of equation to use (see below for table)
		a = .05;				// Constant used in some equations
		iterations = 32;		// How long to keep drawing theta
		increment = 0.1;	// Amount by which to increase theta
		scaleFactor =64;	// How much to scale our drawing
	}
	
	public void paintComponent(Graphics g){
 		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;
		g2d.translate(400, 350);
		g2d.scale(1, -1);
		double theta = 0;
		int x1=(int)( calculateR(equationType, 0) * Math.cos(theta));
		int y1=(int)( calculateR(equationType, 0) * Math.sin(theta));
		for(theta=increment; theta<iterations; theta+=increment){
			int x2=(int)( calculateR(equationType, theta) * Math.cos(theta));
			int y2=(int)( calculateR(equationType, theta) * Math.sin(theta));
			g2d.drawLine(x1, y1, x2, y2);
			x1 = x2;
			y1 = y2;
		}
	}

	/**
	 * Calculates R for a given theta 
	 * and equation type.
	 * 
	 * Equation codes:
	 * 1: r=Math.cos3theta
	 * 2: r=a*theta
	 * 3: r=a(1+Math.cos(theta))
	 * 4: r=a(1+2Math.cos(theta)
	 * 5: r^8=Math.cos3(theta)
	 * 6: r=1+2Math.cos4(theta)
	 * 7: r=1+2Math.cos3(theta)
	 * 8: r=e^Math.cos(theta)-2Math.cos(4theta)+(sin(theta/12))^5
	 * 9: r=e^sin(theta)-2Math.cos(4theta)+(Math.cos(theta/4))^3 
	 * 
	 * @param type the type of equation to use
	 * @param theta the angle with which to calculate R
	 * @return the radius
	 */
	private double calculateR(int type, double theta) {
		double r = 0;
		switch(type){
			case 1 :
				r = scaleFactor * (Math.cos(3*theta));
				break;
			case 2 :
				r = scaleFactor * (a * theta);
				break;
			case 3 : 
				r = scaleFactor * (a * (1+ Math.cos(theta)));
				break;
			case 4 : 
				r = scaleFactor * (a * (1+ 2 * Math.cos(theta)));
				break;
			case 5 : 
				r = scaleFactor * (Math.pow(Math.cos(3 * theta), -8));	// 8th root 
				break;
			case 6 : 
				r = scaleFactor * ( 1+2*Math.cos(4 * theta));
				break;
			case 7 : 
				r = scaleFactor * ( 1+2*Math.cos(3 * theta));
				break;
			case 8 : 
				r = scaleFactor * (
						Math.pow(Math.E, Math.cos(theta))
						- (2*Math.cos(4*theta))
						+ (Math.pow(Math.sin(theta/12), 5)));
				break;
			case 9 : 
				r = scaleFactor * (
						Math.pow(Math.E, Math.sin(theta))
						-(2*Math.cos(4*theta))
						+(Math.pow(Math.cos(theta/4), 3))); 
				break;
		}
		return r;
		
	}
}
