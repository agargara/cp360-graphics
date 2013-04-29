package face;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Control panel for the Face Drawing program.
 * Has buttons for adding facial features, instructions
 * for keyboard shortcuts, and a color chooser for the
 * fill color.
 * @author David
 *
 */
@SuppressWarnings("serial")
public class ControlPanel extends JPanel implements ActionListener, ChangeListener{
 	
	public static final int WIDTH = 440;	// Width of the control panel
	
	FacePanel myPanel;						// The Face Panel to control
	JPanel buttonPanel;						// Panel for buttons
	JButton addFace;						// Adds a face outline
	JButton addEye;							// Adds an eye
	JButton addNose;						// Adds a nose
	JButton addMouth;						// Adds a mouth
	JButton addEyebrow;						// Adds an eyebrow
	JButton addEar;							// Adds an ear
	JButton cycleSelected;					// Cycles through selected elements
	JButton fillSelected;					// Fills selected element
	JButton delete;							// Deletes selected element
	JColorChooser myChooser;				// Color chooser for fill
 
 	public ControlPanel(FacePanel _myPanel){
		myPanel = _myPanel;	
		setPreferredSize(new Dimension(WIDTH, FacePanel.SIZE));
		
		// Create button panel and align it on the Y-axis:
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.PAGE_AXIS));
		
		// Create color chooser with default color of light gray
		myChooser = new JColorChooser(Color.LIGHT_GRAY);
		myChooser.getSelectionModel().addChangeListener(this);
		
		// Instantiate all buttons:
		addFace = new JButton("Add Face");
		addEye = new JButton("Add Eye");
		addNose = new JButton("Add Nose");
		addMouth = new JButton("Add Mouth");
		addEyebrow = new JButton("Add Eyebrow");
		addEar = new JButton("Add Ear");
		cycleSelected = new JButton("Cycle Selected");
		fillSelected = new JButton("Fill");
		delete = new JButton("Delete");
		
		addFace.addActionListener(this);
		addEye.addActionListener(this);
		addNose.addActionListener(this);
		addMouth.addActionListener(this);
		addEyebrow.addActionListener(this);
		addEar.addActionListener(this);
		cycleSelected.addActionListener(this);
		fillSelected.addActionListener(this);
		delete.addActionListener(this);

		// Add instruction text:
		add(new JLabel(
				"<html>" +
				"wasd: \t Scale <p>" +
				"zx: \t Rotate <p>" +
				"fg: \t Flip <p>" +
				"q: \t Cycle" +
				"</html>"));
		
		// Add all other elements
		buttonPanel.add(addFace);
		buttonPanel.add(addEye);
		buttonPanel.add(addNose);
		buttonPanel.add(addMouth);
		buttonPanel.add(addEyebrow);
		buttonPanel.add(addEar);
		buttonPanel.add(cycleSelected);
		buttonPanel.add(fillSelected);
		buttonPanel.add(delete);
		add(buttonPanel);
		add(myChooser);
   }//end constructor

	@Override
	/**
	 * Handle various button presses by calling the appropriate methods
	 * in the Face Panel.
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == addEye){
			myPanel.addEye();
		}else if(e.getSource() == addFace){
			myPanel.addFace();
		}else if(e.getSource() == addNose){
			myPanel.addNose();
		}else if(e.getSource() == addMouth){
			myPanel.addMouth();
		}else if(e.getSource() == addEyebrow){
			myPanel.addEyebrow();
		}else if(e.getSource() == addEar){
			myPanel.addEar();
		}else if(e.getSource() == cycleSelected){
			myPanel.cycleSelected();
		}else if(e.getSource() == fillSelected){
			myPanel.fillSelected();
		}else if(e.getSource() == delete){
			myPanel.delete();
		}
	}

	/**
	 * Listen for a change of color.
	 */
	public void stateChanged(ChangeEvent e) {
		myPanel.updateColor(myChooser.getColor());
	}
}