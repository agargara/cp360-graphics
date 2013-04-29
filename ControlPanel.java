/**
 * Modified slightly from code originally by Steven Janke.
 * Draws three sliders which control the RGB components of the circles.
 */

import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;

@SuppressWarnings("serial")
class ControlPanel extends JPanel implements ChangeListener
 {
 	ColorPanel cPanel;
	JSlider redSlider, greenSlider, blueSlider;
 
 	public ControlPanel(ColorPanel cp)
	{
		cPanel=cp;	
		
		setLayout(new GridLayout(1,3,30,10));
		
    	redSlider = new JSlider(JSlider.VERTICAL,0,255,100);
		redSlider.setMajorTickSpacing(50);
		redSlider.setMinorTickSpacing(10);
		redSlider.setPaintTicks(true);
		redSlider.setPaintLabels(true);
		redSlider.addChangeListener(this);
		JLabel rlabel = new JLabel("Red");
		JPanel r = new JPanel();
		r.setLayout(new BoxLayout(r, BoxLayout.Y_AXIS));
		r.add(rlabel);
    	r.add(redSlider);
		add(r);
		
		
		greenSlider = new JSlider(JSlider.VERTICAL,0,255,100);
		greenSlider.addChangeListener(this);
		greenSlider.setMajorTickSpacing(50);
		greenSlider.setMinorTickSpacing(10);
		greenSlider.setPaintTicks(true);
		greenSlider.setPaintLabels(true);

		JLabel glabel = new JLabel("Green");
		JPanel g = new JPanel();
		g.setLayout(new BoxLayout(g, BoxLayout.Y_AXIS));
		g.add(glabel);
      g.add(greenSlider);
		add(g);
		    
      blueSlider = new JSlider(JSlider.VERTICAL,0,255,100);
		blueSlider.addChangeListener(this);
		blueSlider.setMajorTickSpacing(50);
		blueSlider.setMinorTickSpacing(10);
		blueSlider.setPaintTicks(true);
		blueSlider.setPaintLabels(true);

		JLabel blabel = new JLabel("Blue");
		JPanel b = new JPanel();
		b.setLayout(new BoxLayout(b, BoxLayout.Y_AXIS));
		b.add(blabel);
      b.add(blueSlider);
		add(b);
		
		cPanel.changeColor(redSlider.getValue(), greenSlider.getValue(), blueSlider.getValue());
   }//end constructor
   
   public void stateChanged(ChangeEvent ev){
	   	cPanel.changeColor(redSlider.getValue(), greenSlider.getValue(), blueSlider.getValue());
   }//end stateChanged

}