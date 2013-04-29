package cube;

import java.awt.Color;
import java.awt.Polygon;
import java.util.LinkedList;

/**
 * Represents a three-dimensional cube. Contains methods
 * for rotation, matrix multiplication, and generating
 * drawable polygons from the faces of the cube.
 * 
 * Also keeps track of an arbitrary axis and rotation around
 * this axis.
 * 
 * @author David
 *
 */
public class Cube {
	
	double[] eyeLocation = {0, 0, 64};	// The coordinates of the eye
	Face[] faces;				// The array of faces that make this cube.
	double[][] vertices; 		// The array of vertices
	int scale;					// The default scale amount
	double len = 1; 			// distance of each vertex from origin
	double rotationMatrix[][];	// The rotation matrix
	double[] arbitraryAxis;		// An arbitrary axis around which to rotate
	double arbitraryRotation;	// The angle to rotate around the arbitrary axis
	boolean wireframe;			// Set to true for see-through cube
	
	/**
	 * Initializes a cube centered at the origin
	 * with sides of length 2.
	 */
	public Cube(){
		wireframe = false;						// Default wireframe
		scale = 64;								// Default scale
		faces = new Face[6];					// Initialize face array
		arbitraryAxis = new double[]{0, 0, 1};	// Default arbitrary axis is z-axis
		rotationMatrix = new double[][]			// Default identity matrix
		                {	{1, 0, 0, 0},
		                	{0, 1, 0, 0},
		                	{0, 0, 1, 0},
		                	{0, 0, 0, 1} };
		
		// Initialize vertices:
		vertices = new double[8][];
		vertices[0] = new double[]{len, len, len};
		vertices[1] = new double[]{len, len, -len};
		vertices[2] = new double[]{len, -len, -len};
		vertices[3] = new double[]{len, -len, len};
		vertices[4] = new double[]{-len, len, len};
		vertices[5] = new double[]{-len, len, -len};
		vertices[6] = new double[]{-len, -len, -len};
		vertices[7] = new double[]{-len, -len, len};
		
		// Initialize faces:
		int base = 80;	// Base color
		faces[0] = new Face(this, new Color(base, base, base), 
						new double[][]{vertices[0], vertices[1], vertices[2], vertices[3]});
		faces[1] = new Face(this, new Color(base*2, base, base), 
						new double[][]{vertices[4], vertices[0], vertices[3], vertices[7]});
		faces[2] = new Face(this, new Color(base, base*2, base), 
						new double[][]{vertices[5], vertices[4], vertices[7], vertices[6]});
		faces[3] = new Face(this, new Color(base, base, base*2), 
						new double[][]{vertices[1], vertices[5], vertices[6], vertices[2]});
		faces[4] = new Face(this, new Color(base*2, base*2, base), 
						new double[][]{vertices[5], vertices[1], vertices[0], vertices[4]});
		faces[5] = new Face(this, new Color(base, base*2, base*2), 
						new double[][]{vertices[7], vertices[3], vertices[2], vertices[6]});
	}

	/**
	 * Gives a list of all faces that should be drawn.
	 * @return a list of faces to be drawn.
	 */
	public LinkedList<Face> getFaces() {
		LinkedList<Face> myFaces = new LinkedList<Face>();
		for(int i=0; i<faces.length; i++){
			if(!wireframe){
				if (faces[i].isVisible(eyeLocation))
					myFaces.add(faces[i]);
			}else{
				myFaces.add(faces[i]);
			}
		}
		return myFaces;
	}
	
	/**
	 * @param i the index of the face to get the color of
	 * @return the color of the given face.
	 */
	public Color getFaceColor(int i) {
		return faces[i].getColor();
	}

	/**
	 * Constructs a 2D polygon from a face with the correct 
	 * perspective, scale and rotation.
	 * @param face the face to transform into a polygon
	 * @return the 2D polygon version of the face.
	 */
	public Polygon makePolygon(Face face) {
		Polygon myPoly = new Polygon();
		for(int i=0; i<face.numVertices(); i++){	// Loop through all 4 vertices
			double[] v = face.rotate(face.getVertex(i));	// Get rotated vertex
			double x = (v[0] / (1 - (v[2] / eyeLocation[2])));	// Calculate perspective x
			double y = (v[1] / (1 - (v[2] / eyeLocation[2])));	// Calculate perspective y
			x *= scale;							// Scale vertex
			y *= scale;							// Scale vertex
			myPoly.addPoint((int)x, (int)y);
		}
		return myPoly;
	}

	/**
	 * Sets the x-y rotation of the cube.
	 * @param _xRotation the angle to rotate on the x-axis (in radians)
	 * @param _yRotation the angle to rotate on the y-axis (in radians)
	 */
	public void rotateXY(double _xRotation, double _yRotation) {
		rotationMatrix = new double[][]
		      		                {	{1, 0, 0, 0},
		      		                	{0, 1, 0, 0},
		      		                	{0, 0, 1, 0},
		      		                	{0, 0, 0, 1} };	// Initialize rotation matrix
		if(_xRotation != 0)
			rotationMatrix = preconcatenateRotation(new double[]{0, 1, 0}, _xRotation);
		if(_yRotation != 0)
			rotationMatrix = preconcatenateRotation(new double[]{1, 0, 0}, _yRotation);
	}
	
	/**
	 * Concatenates a rotation to the rotation matrix.
	 */
	private double[][] preconcatenateRotation(double[] axis, double theta) {
		return multiplyMatrices(rotateAroundAxis(axis, theta), rotationMatrix);
	}

	/**
	 * Multiplies two 4x4 matrices.
	 * @param m1 the first matrix
	 * @param m2 the second matrix
	 * @return the multiplied matrix
	 */
	private double[][] multiplyMatrices(double[][] m1, double[][] m2) {
		double[][] result = new double[4][4];
		for(int j=0; j<4; j++){			
			for(int i=0; i<4; i++){		
				double value = 0;
				for(int k=0; k<4; k++)	
					value += m1[i][k] * m2[k][j];
				result[i][j] = value;
			}
		}
		return result;
	}

	/**
	 * Returns the rotation matrix for rotating the cube around a given arbitrary axis.
	 * @param axis the vector that represents the arbitrary axis
	 * @param theta the angle to rotate around the arbitrary axis (in radians)
	 */
	public double[][] rotateAroundAxis(double[] axis, double theta) {
		// Initial calculations to save time:
		double c = Math.cos(theta);			// Calculate cos
		double s = Math.sin(theta);			// Calculate sin
		double t = 1 - Math.cos(theta);		// Calculate 1-cos
		
		// Variables to represent axis components:
		double ax = axis[0];
		double ay = axis[1];
		double az = axis[2];
		// Find the length of the axis vector:
		double axisLength = Math.abs(Math.sqrt(ax*ax + ay*ay + az*az));
		// Normalize the axis variables:
		ax /= axisLength;
		ay /= axisLength;
		az /= axisLength;
		return new double[][]
			{	{t*ax*ax + c, 		t*ax*ay + s*az, 	t*ax*az - s*ay, 	0},
				{t*ax*ay - s*az, 	t*ay*ay + c, 		t*ay*az + s*ax,		0},
				{t*ax*ay + s*ay, 	t*ay*az - s*ax,		t*az*az + c, 		0},
				{0, 				0, 					0, 					1}	};
	}
	
	public void toggleWireframe(){
		wireframe = !wireframe;
	}

	/**
	 * @return true if the cube is a wireframe cube
	 */
	public boolean isWireframe() {
		return wireframe;
	}

	/**
	 * Scales the cube
	 * @param value the scale amount
	 */
	public void scale(int value) {
		scale = value;
	}
	
	/**
	 * @return the rotation matrix for this cube
	 */
	public double[][] getRotationMatrix(){
		return preconcatenateRotation(arbitraryAxis, arbitraryRotation);
	}

	public void updateAxis(double axisX, double axisY, double axisZ, double theta) {
		arbitraryAxis[0] = axisY;
		arbitraryAxis[1] = axisX;
		arbitraryAxis[2] = axisZ;
		arbitraryRotation = ((CUBE_DC.ROTATION_SPEED * theta) / 255);
	}
}
