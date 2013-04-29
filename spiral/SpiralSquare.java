package spiral;

import java.awt.Polygon;
import java.awt.geom.Point2D;

/**
 * Represents a square that contains multiple squares within it.
 * Is a type of polygon.
 * @author David Cummings
 *
 */
@SuppressWarnings("serial")
public class SpiralSquare extends Polygon{
	int[] xCoords;	// x coordinates of vertices
	int[] yCoords;	// y coordinates of vertices

	/**
	 * Constructs a spiral square out of a set of vertices.
	 * @param _xCoords the x coordinates of the vertices
	 * @param _yCoords the y coordinates of the vertices
	 */
	public SpiralSquare(int[] _xCoords, int[] _yCoords){
		super(_xCoords, _yCoords, _xCoords.length);	// Construct the related polygon.
		xCoords = _xCoords;
		yCoords = _yCoords;
	}
	
	/**
	 * Creates a new square within this square for a given lambda.
	 * @param lambda the amount of rotation, a number between 0 and 1
	 * that represents the percent along the line to place the new vertex.
	 */
	public SpiralSquare createNewSquare(double lambda){
		int[] newX = new int[4];	// New x coordinates
		int[] newY = new int[4];	// New y coordinates
		// For each vertex, use the following formula to calculate the new vertex:
		//		x_new = (1 - lambda) * x1 + (lambda) * x2
		//		y_new = (1 - lambda) * y1 + (lambda) * y2
		for(int i=0; i<newX.length; i++){
			newX[i] = (int)(((1-lambda)*xCoords[i]) + ((lambda)*xCoords[(i+1)%4]));
			newY[i] = (int)(((1-lambda)*yCoords[i]) + ((lambda)*yCoords[(i+1)%4]));
		}
		return new SpiralSquare(newX, newY);
	}

	/**
	 * @return the top left vertex of the square
	 */
	public Point2D getTopVertex() {
		return new Point2D.Double(xCoords[0], yCoords[0]);
	}

	/**
	 * @return the bottom right vertex of the square
	 */
	public Point2D getBottomVertex() {
		return new Point2D.Double(xCoords[2], yCoords[2]);
	}
}
