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
class ColorChooserPanel extends JPanel implements ChangeListener{
 	SquarePanel myPanel;
	JColorChooser myChooser1;
	JColorChooser myChooser2;
	
 	public ColorChooserPanel(SquarePanel _myPanel){
		myPanel = _myPanel;	
		myChooser1 = new JColorChooser(Color.GRAY);
		myChooser2 = new JColorChooser(Color.LIGHT_GRAY);
		myChooser1.setPreviewPanel(new JPanel());		// Remove preview panel
		myChooser2.setPreviewPanel(new JPanel());		// Remove preview panel
		myChooser1.getSelectionModel().addChangeListener(this);
		myChooser2.getSelectionModel().addChangeListener(this);
		add(myChooser1);
		add(myChooser2);
		myPanel.updateColors(myChooser1.getColor(), myChooser2.getColor());
   }//end constructor
   
 	/**
 	 * Update all values whenever a slider is moved.
 	 */
   public void stateChanged(ChangeEvent ev){
	   myPanel.updateColors(myChooser1.getColor(), myChooser2.getColor());
   }//end stateChanged

}