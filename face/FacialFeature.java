package face;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;

/**
 * Represents a facial feature that is a shape which
 * can be drawn. The shape can be represented by
 * a Polygon or by a Path2D.
 * @author David
 *
 */
public class FacialFeature {
	
	AffineTransform myTransform;	// Affine transformation of object
	Polygon myPolygon;				// Polygon that might represent this facial feature
	Path2D myPath;					// Path2D that might represent this facial feature
	boolean filled;					// True if this feature should be filled
	Color fillColor;				// Color for filling this feature
	
	/**
	 * Constructs a Facial Feature.
	 * @param defaultXScale the default scale factor on the x axis.
	 * @param defaultYScale the default scale factor on the y axis.
	 * @param defaultX		the default x location of this feature.
	 * @param defaultY		the default y location of this feature.
	 */
	public FacialFeature(int defaultXScale, int defaultYScale, int defaultX, int defaultY){
		myPolygon = new Polygon();
		myPath = new Path2D.Double();
		myTransform = new AffineTransform();
		filled = false;							// Object is not filled by default.
		scale(defaultXScale, defaultYScale);	// Scale to the default size
		translate(defaultX, defaultY);			// Translate to the default coordinates
	}
	

	/**
	 * Scales this facial feature
	 * @param scaleFactor1 the x scale factor
	 * @param scaleFactor2 the y scale factor
	 */
	public void scale(double scaleFactor1, double scaleFactor2){
		double origX = myTransform.getTranslateX();	// Get original x and y values
		double origY = myTransform.getTranslateY();
		AffineTransform scaleTransform = new AffineTransform();
		scaleTransform.setToScale(scaleFactor1, scaleFactor2);
		myTransform.preConcatenate(scaleTransform);
		// Translate figure back to original point after scaling:
		translate(-(origX*(scaleFactor1 - 1)), -(origY*(scaleFactor2 - 1)));
	}

	/**
	 * Translates this facial feature
	 * @param x how much to translate in the x direction
	 * @param y how much to translate in the y direction
	 */
	public void translate(double x, double y){
		AffineTransform translateTransform = new AffineTransform();
		translateTransform.setToTranslation(x, y);
		myTransform.preConcatenate(translateTransform);
	}
	
	/**
	 * Rotates this facial feature
	 * @param rotationDegrees radians to rotate
	 */
	public void rotate(double rotationDegrees) {
		AffineTransform rotateTransform = new AffineTransform();
		rotateTransform.setToRotation(rotationDegrees,
				getCenterX(),
				getCenterY());
		myTransform.preConcatenate(rotateTransform);
	}
	
	/**
	 * @return the center x of the figure.
	 */
	private double getCenterX() {
		return (myTransform.getTranslateX() + (getWidth() / 2.0));
	}
	
	/**
	 * @return the center y of the figure.
	 */
	private double getCenterY() {
		return (myTransform.getTranslateY() + (getHeight() / 2.0));
	}

	/**
	 * Return the width of this facial feature. Calculation
	 * depends on whether this facial feature uses a polygon
	 * or Path2D.
	 * @return the width of this facial feature
	 */
	private double getWidth(){
		double width = 0;
		if (myPolygon.npoints > 0)
			width = myPolygon.getBounds2D().getWidth();
		else
			width = myPath.getBounds2D().getWidth();
		return width;
	}
	
	/**
	 * Return the height of this facial feature. Calculation
	 * depends on whether this facial feature uses a polygon
	 * or Path2D.
	 * @return the height of this facial feature
	 */
	private double getHeight(){
		double height = 0;
		if (myPolygon.npoints > 0)
			height = myPolygon.getBounds2D().getHeight();
		else
			height = myPath.getBounds2D().getHeight();
		return height;
	}
	
	/**
	 * @return this facial feature after applying the transform.
	 */
	public Shape getTransformedFeature(){
		Shape myReturnShape;
		if (myPolygon.npoints > 0)
			myReturnShape = myTransform.createTransformedShape(myPolygon);
		else
			myReturnShape = myTransform.createTransformedShape(myPath);
		return myReturnShape ;
	}

	/**
	 * Calls Polygon.addPoint()
	 */
	public void addPoint(int i, int j) {
		myPolygon.addPoint(i, j);
	}

	/**
	 * Calls Path2D.moveTo()
	 */
	public void moveTo(double i, double j) {
		myPath.moveTo(i, j);
	}
	
	/**
	 * Calls Path2D.quadTo()
	 */
	public void quadTo(double i, double j, double k, double l) {
		myPath.quadTo(i, j, k, l);
	}

	/**
	 * Indicates that this facial feature should be filled
	 * with the given fill color.
	 * @param _fillColor the color with which to fill this facial feature
	 */
	public void fill(Color _fillColor) {
		filled = true;
		fillColor = _fillColor;
	}
	
	/*
	 * @return true if this shape should be filled
	 */
	public boolean isFilled(){
		return filled;
	}

	/*
	 * @return the color of this shape
	 */
	public Color getFillColor() {
		return fillColor;
	}
}
