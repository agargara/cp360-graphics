package spiral;

/**
 * A control panel for the spiraling squares application.
 * Draws three sliders for number of squares, iterations, 
 * and lambda (amount of rotation).
 */

import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;

@SuppressWarnings("serial")
class ControlPanel extends JPanel implements ChangeListener{
 	SquarePanel myPanel;
	JSlider squareSlider, iterationSlider, lambdaSlider;	// The three control sliders
 
 	public ControlPanel(SquarePanel _myPanel){
		myPanel = _myPanel;	
		
		setLayout(new GridLayout(1,3,10,10));	// 1 row, 3 cols, hgap of 10, vgap of 10
		
		// Add slider for number of squares:
    	squareSlider = new JSlider(JSlider.VERTICAL,1,32,4);
		squareSlider.setMajorTickSpacing(16);
		squareSlider.setMinorTickSpacing(4);
		squareSlider.setPaintTicks(true);
		squareSlider.setPaintLabels(true);
		squareSlider.addChangeListener(this);
		JLabel rlabel = new JLabel("N");
		JPanel r = new JPanel();
		r.setLayout(new BoxLayout(r, BoxLayout.Y_AXIS));
		r.add(rlabel);
    	r.add(squareSlider);
		add(r);
		
		// Add slider for iterations:
		iterationSlider = new JSlider(JSlider.VERTICAL,0,64,16);
		iterationSlider.addChangeListener(this);
		iterationSlider.setMajorTickSpacing(8);
		iterationSlider.setMinorTickSpacing(2);
		iterationSlider.setPaintTicks(true);
		iterationSlider.setPaintLabels(true);
		JLabel glabel = new JLabel("i");
		JPanel g = new JPanel();
		g.setLayout(new BoxLayout(g, BoxLayout.Y_AXIS));
		g.add(glabel);
		g.add(iterationSlider);
		add(g);
		
		// Add slider for lambda:
		lambdaSlider = new JSlider(JSlider.VERTICAL,0,255,80);
		lambdaSlider.addChangeListener(this);
		lambdaSlider.setMajorTickSpacing(50);
		lambdaSlider.setMinorTickSpacing(10);
		lambdaSlider.setPaintTicks(true);
		lambdaSlider.setPaintLabels(true);
		JLabel blabel = new JLabel("L");
		JPanel b = new JPanel();
		b.setLayout(new BoxLayout(b, BoxLayout.Y_AXIS));
		b.add(blabel);
		b.add(lambdaSlider);
		add(b);
		
		add(Box.createRigidArea(new Dimension(4,600)));	// Add box for padding
		
		myPanel.updateValues(squareSlider.getValue(), iterationSlider.getValue(), (lambdaSlider.getValue()/255.0));
   }//end constructor
   
 	/**
 	 * Update all values whenever a slider is moved.
 	 */
   public void stateChanged(ChangeEvent ev){
	   myPanel.updateValues(squareSlider.getValue(), iterationSlider.getValue(), (lambdaSlider.getValue()/255.0));
   }//end stateChanged

}