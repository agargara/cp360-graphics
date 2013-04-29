package dodgeCity;

import javax.swing.JPanel;

public class AnimationThread implements Runnable{

	JPanel canvas;
	long delay = 14;		// How often to calculate animation
	
	public AnimationThread(JPanel _canvas){
		canvas = _canvas;
	}
	
	@Override
	public void run() {
		while(true){
			canvas.repaint();
			try {
				Thread.sleep(delay);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
