package cube;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Control panel for the Cube Drawing Program. Contains sliders
 * for rotating around an arbitray axis and scaling, buttons
 * for toggling wireframe and constructing an arbitrary axis,
 * and text fields for defining this axis.
 * @author David
 *
 */
@SuppressWarnings("serial")
public class ControlPanel extends JPanel implements ActionListener, ChangeListener{

	CubePanel myPanel;
	JButton toggleWireframe, constructAxis;	// Buttons for toggling wireframe and constructing an axis
	JSlider axisRotation;			// Slider for rotating around an arbitrary axis
	JSlider scale;					// Slider for scaling the cube
	JTextField xText, yText, zText;	// Text fields for coordinates of arbitrary axis
	double axisX, axisY, axisZ;		// Doubles that represent the arbitrary axis
	
	public ControlPanel(CubePanel _myPanel) {
		myPanel = _myPanel;
		axisX = 0;
		axisY = 0;
		axisZ = 1;
		
		// Set layout to vertical axis
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		// Add wireframe button
		toggleWireframe = new JButton("Toggle Wireframe");
		toggleWireframe.addActionListener(this);
		add(toggleWireframe);
		
		// Add axis slider:
		axisRotation = new JSlider(JSlider.VERTICAL,1,255,64);
		axisRotation.setMajorTickSpacing(32);
		axisRotation.setMinorTickSpacing(16);
		axisRotation.setPaintTicks(true);
		axisRotation.setPaintLabels(true);
		axisRotation.addChangeListener(this);
		add(axisRotation);
		add(new JLabel("Axis Rotation"));
		
		// Add scale slider
		scale = new JSlider(JSlider.VERTICAL,1,255,64);
		scale.setMajorTickSpacing(32);
		scale.setMinorTickSpacing(16);
		scale.setPaintTicks(true);
		scale.setPaintLabels(true);
		scale.addChangeListener(this);
		add(scale);
		add(new JLabel("Scale"));
		
		// Add text fields:
		xText = new JTextField("0.0", 4);
		add(xText);
		yText = new JTextField("0.0", 4);
		add(yText);
		zText = new JTextField("1.0", 4);
		add(zText);
		
		// Add axis construction button
		constructAxis = new JButton("Construct Arbitrary Axis");
		constructAxis.addActionListener(this);
		add(constructAxis);
	}

	/**
	 * Detect button presses
	 */
	public void actionPerformed(ActionEvent e) {
		if((JButton)e.getSource() == toggleWireframe){		// Turn wireframe on/off
			myPanel.toggleWireframe();
			myPanel.repaint();
		} else if((JButton)e.getSource() == constructAxis){	// Construct an arbitrary axis
			try{
				axisX = Double.parseDouble(xText.getText());
				axisY = Double.parseDouble(yText.getText());
				axisZ = Double.parseDouble(zText.getText());
				myPanel.updateAxis(axisX, axisY, axisZ, axisRotation.getValue());
			}catch(NumberFormatException x){
				System.out.println("Invalid axis coordinates.");
			}
		}
	}

	/**
	 * Detect slider changes
	 */
	public void stateChanged(ChangeEvent e) {
		if ((JSlider)e.getSource() == axisRotation)	// Rotate on arbitrary axis
			myPanel.updateAxis(axisX, axisY, axisZ, axisRotation.getValue());
		else if ((JSlider)e.getSource() == scale)	// Scale cube
			myPanel.scale(scale.getValue());
	}

}
