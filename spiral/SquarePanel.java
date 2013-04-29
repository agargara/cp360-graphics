package spiral;

import java.awt.*;

import javax.swing.*;

@SuppressWarnings("serial")
/**
 * This class is a canvas on which spiraling squares are drawn based on
 * a number of squares, iterations, and a gien lambda.
 */
class SquarePanel extends JPanel {

	int numSquares;			// How many squares to draw on the screen
	int iterations;			// How many squares to draw within each square
	double lambda;			// The percentage along the line of a square to draw the next square
	int panelWidth;			// The total width of the whole panel
	int width;				// The width of one square
	Color col1, col2;		// The colors of the gradient
	
	/**
	 * Constructs a square panel of a given width.
	 * @param _panelWidth the width of the panel
	 */
	public SquarePanel(int _panelWidth){
		panelWidth = _panelWidth;
		this.setPreferredSize(new Dimension(panelWidth, panelWidth));
		col1 = Color.GRAY;
		col2 = Color.LIGHT_GRAY;
	}
	
	/**
	 * Updates the values of three variables.
	 * @param _numSquares the number of squares to draw on the screen
	 * @param _iterations how many squares to draw within each square
	 * @param _lambda the percentage along the line of a square to draw the next square
	 */
	public void updateValues(int _numSquares, int _iterations, double _lambda) {
		numSquares = _numSquares;
		iterations = _iterations;
		lambda = _lambda;
		width = (int)(panelWidth / numSquares);
		repaint();
	}
	
	/**
	 * Paints the squares on the screen
	 */
	public void paintComponent(Graphics g){
 		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;

		for(int i=0; i<numSquares; i++){		// Loop for rows
			for(int j=0; j<numSquares; j++){	// Loop for cols
				// Calculate where to draw the original square:
				int offsetX = i * width;
				int offsetY = j * width;
				int[] firstXCoords = {offsetX, offsetX+width, offsetX+width, offsetX};
				int[] firstYCoords = {offsetY, offsetY, offsetY+width, offsetY+width};
				
				// Create first square:
				SpiralSquare mySquare = new SpiralSquare(firstXCoords, firstYCoords);
				
				for(int k = 0; k<iterations; k++){			// Draw new squares within first
					GradientPaint myGradient;				// Gradient for painting
					if(k%2 == 0){							// Swap colors every other square
						myGradient = new GradientPaint(
							mySquare.getTopVertex(), col1, mySquare.getBottomVertex(), col2);
					}else{
						myGradient = new GradientPaint(
								mySquare.getTopVertex(), col2, mySquare.getBottomVertex(), col1);
					}
					g2d.setPaint(myGradient);
					g2d.fill(mySquare);			// Fill square with gradient
					if((i+j)%2 == 0)			// Alternate rotation for even/odd rows
						mySquare = mySquare.createNewSquare(lambda);
					else
						mySquare = mySquare.createNewSquare(1-lambda);
				}
			}
		}
	}

	/**
	 * Changes the colors of the gradient.
	 * @param color		the first color
	 * @param color2	the second color
	 */
	public void updateColors(Color color1, Color color2) {
		col1 = color1;
		col2 = color2;
		repaint();
	}
}
