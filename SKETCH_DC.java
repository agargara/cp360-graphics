import java.awt.event.*;
import javax.swing.*;

public class SKETCH_DC {
	public static void main(String[] args){
		JFrame myFrame = new JFrame();
		myFrame.setTitle("Sketch!");

		// Set the window to close when the user clicks X:
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		SketchPanel myPanel = new SketchPanel();
		myFrame.add(myPanel);
		myFrame.setSize(800,600); 
		myFrame.setResizable(false);
		myFrame.setVisible(true);
    }
}

@SuppressWarnings("serial")
class SketchPanel extends JPanel implements ActionListener, MouseMotionListener, MouseListener {
	
	int x1, y1, x2, y2;
	JButton clear;
	
	public SketchPanel(){
		x1 = 0;
		y1 = 0;
		x2 = 0;
		y2 = 0;
		clear = new JButton("Clear");
		clear.addActionListener(this);
		add(clear);
		addMouseListener(this);
		addMouseMotionListener(this);
	}
	
	public void mouseDragged(MouseEvent e) {
		x2 = e.getX();
		y2 = e.getY();
		getGraphics().drawLine(x1, y1, x2, y2);
		x1 = x2;
		y1 = y2;
	}
	
	public void mousePressed(MouseEvent e) {
		x1 = e.getX();
		y1 = e.getY();
	}

	public void mouseMoved(MouseEvent e) {}
	public void mouseClicked(MouseEvent arg0) {}
	public void mouseEntered(MouseEvent arg0) {}
	public void mouseExited(MouseEvent arg0) {}
	public void mouseReleased(MouseEvent arg0) {}

	public void actionPerformed(ActionEvent arg0) {
		super.repaint();
	}
}
