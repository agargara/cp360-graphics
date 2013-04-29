package dodgeCity;

import java.awt.Color;
import java.awt.Polygon;
import java.awt.geom.Ellipse2D;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;

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
public class Cube implements Comparable<Object>{
	
	double eye;
	double sceneRotation = -Math.PI/4;	// Global rotation on x-axis for all cubes
	double[] center;			// The center of this cube in world coordinates.
	Face[] faces;				// The array of faces that make this cube.
	double[][] vertices; 		// The array of vertices
	double scale;					// How much to scale the cube
	double len = 1; 			// distance of each vertex from origin
	double rotationMatrix[][];	// The rotation matrix
	double[] arbitraryAxis;		// An arbitrary axis around which to rotate
	double arbitraryRotation;	// The angle to rotate around the arbitrary axis
	boolean wireframe;			// Set to true for see-through cube
	
	/**
	 * Initializes a cube centered at the origin
	 * with sides of length 2.
	 */
	public Cube(double startX, double startY, double startZ, double _scale, double _eye){
		eye = _eye;
		center = new double[]{startX, startY, startZ};
		wireframe = false;						// Default wireframe value
		scale = _scale;							// Scale amount
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
	 * Sorts cubes by the lowest z-value of all faces.
	 */
	public int compareTo(Object arg0) {			
		return this.getMinFace().compareTo(
						((Cube)arg0).getMinFace()  );
	}
	
	/*
	 * @return the z coordinate of the eye
	 */
	public double getEye(){
		return eye;
	}

	/**
	 * @param i the index of the face to get the color of
	 * @return the color of the given face.
	 */
	public Color getFaceColor(int i) {
		return faces[i].getColor();
	}

	/**
	 * Returns a set of transformed faces to be drawn.
	 * @param sceneRotation an extra amount to rotate the faces
	 * @return a set of transformed faces to be drawn.
	 */
	public SortedSet<Face> getFaces() {
		SortedSet<Face> myFaces = Collections.synchronizedSortedSet(new TreeSet<Face>());
		for(Face f : faces){
			if(!wireframe){
				if (makeTransformedFace(f).isVisible(eye))
					myFaces.add(makeTransformedFace(f));
			}else{
				myFaces.add(makeTransformedFace(f));
				
			}
		}
		return myFaces;
	}
	
	/**
	 * Calculates the global rotation matrix based on the global rotation around the x-axis.
	 * @return the global rotation matrix
	 */
	public double[][] getGlobalRotationMatrix() {
		double[] axis = {1, 0, 0};	// Rotate around x-axis
		return rotateAroundAxis(axis, sceneRotation);
	}
	
	/**
	 * @return the Face with the lowest z-value.
	 */
	public Face getMinFace(){
		Face minFace = makeTransformedFace(faces[0]);
		for(int i=1; i<faces.length; i++){	// Find min face
			if (minFace.compareTo(makeTransformedFace(faces[i])) == -1)
				minFace = makeTransformedFace(faces[i]);
		}
		return minFace;
	}

	/**
	 * @return the rotation matrix for this cube
	 */
	public double[][] getRotationMatrix(){
		return preconcatenateRotation(arbitraryAxis, arbitraryRotation);
	}

	/**
	 * @return true if the cube is a wireframe cube
	 */
	public boolean isWireframe() {
		return wireframe;
	}
	
	/**
	 * Constructs a 2D polygon from a face with the correct 
	 * perspective, scale and rotation.
	 * @param face the face to transform into a polygon
	 * @return the 2D polygon version of the face.
	 */
	public Polygon makePolygon(Face face) {
		Polygon myPoly = new Polygon();
		for(double[] v : face.getVertices()){	// Loop through all vertices
			// Calculate perspective of vertex:
			myPoly.addPoint(	(int)Math.round((v[0] / (1 - (v[2] / eye)))), 
								(int)Math.round((v[1] / (1 - (v[2] / eye))))	);
		}
		return myPoly;
	}

	/**
	 * Constructs a transformed face from a given face.
	 * @param face the face to transform
	 * @return the transformed face
	 */
	public Face makeTransformedFace(Face face) {
		double[][] oldV = face.getVertices();
		Face returnFace = new Face(this, face.getColor(), oldV.length);
		for(int i=0; i<oldV.length; i++){	// Loop through all vertices
			double[] v = makeTransformedVertex(oldV[i], face);
			returnFace.addVertex(i, v);			// Add transformed vertex to face
		}
		return returnFace;
	}

	private double[] makeTransformedVertex(double[] oldV, Face face) {
		// Calculate rotated, translated, scaled vertex:
		double[] v = face.scale(oldV, scale);
		v = face.rotate(v);
		v = face.translate(v, center);
		// Extra scene rotation:
		v = face.rotateGlobal(v);						// Rotate around eye
		v = face.translateGlobal(v);			// Global translation 
		return v;
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
	 * Concatenates a rotation to the rotation matrix.
	 */
	private double[][] preconcatenateRotation(double[] axis, double theta) {
		return multiplyMatrices(rotateAroundAxis(axis, theta), rotationMatrix);
	}

	/**
	 * Adds to the x-y-z rotation of the cube.
	 * @param _xRotation the angle to rotate on the x-axis (in radians)
	 * @param _yRotation the angle to rotate on the y-axis (in radians)
	 * @param _zRotation the angle to rotate on the y-axis (in radians)
	 */
	public void rotate(double _xRotation, double _yRotation, double _zRotation) {
		if(_xRotation != 0)
			rotationMatrix = preconcatenateRotation(new double[]{0, 1, 0}, _xRotation);
		if(_yRotation != 0)
			rotationMatrix = preconcatenateRotation(new double[]{1, 0, 0}, _yRotation);
		if(_zRotation != 0)
			rotationMatrix = preconcatenateRotation(new double[]{0, 0, 1}, _zRotation);
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
	
	/**
	 * Scales the cube
	 * @param value the scale amount
	 */
	public void scale(int value) {
		scale = value;
	}

	/**
	 * Sets the x-y rotation of the cube.
	 * @param _xRotation the angle to rotate on the x-axis (in radians)
	 * @param _yRotation the angle to rotate on the y-axis (in radians)
	 */
	public void setRotateXY(double _xRotation, double _yRotation) {
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
	 * Sets the x-value of the center.
	 * @param xNew the new x-value of the center.
	 */
	public void setX(double xNew) {
		center[0] = xNew;
	}

	/**
	 * Sets the z-value of the center.
	 * @param zNew the new z-value of the center.
	 */
	public void setZ(double zNew) {
		center[2] = zNew;
	}

	public void toggleWireframe(){
		wireframe = !wireframe;
	}

	public void translate(double i, double j, double k) {
		center[0] += i;
		center[1] += j;
		center[2] += k;
	}

	public void updateAxis(double axisX, double axisY, double axisZ, double theta) {
		arbitraryAxis[0] = axisY;
		arbitraryAxis[1] = axisX;
		arbitraryAxis[2] = axisZ;
		arbitraryRotation = ((Math.PI/2 * theta) / 255);
	}

	/**
	 * @param i the face to get
	 * @return the ith face
	 */
	public Face getFace(int i) {
		return makeTransformedFace(faces[i]);
	}

	/**
	 * Generates a fake 2D shadow to project onto the x-z plane for this cube
	 * @return a simple shadow
	 */
	public Ellipse2D makeCircularShadow() {
		double[] coordinates = getVertexOnGround();		// Get vertex on ground
		double distance = coordinates[1] - center[1]; 	// Distance from ground
		if(distance < 0)								// Don't draw shadows above ground
			return null;
		distance = (DodgeCity.ROOM_SIZE_Y)/distance;
		if (distance > 10)								// Don't draw huge shadows
			return null;
		double shadowSize = (scale) * distance;
		double x = (coordinates[0] / (1 - (coordinates[2] / eye))) - (shadowSize/2.0);
		double y = (coordinates[1] / (1 - (coordinates[2] / eye))) - (shadowSize);
		return new Ellipse2D.Double(x, y, shadowSize, shadowSize);
	}
	
	/**
	 * Generates a fake 2D shadow to project onto the x-z plane for this cube
	 * @return a simple shadow
	 */
	public Polygon makeShadow() {
		Face bottomFace = faces[5];
		Polygon myShadow = new Polygon();
		for (double[] v : bottomFace.getVertices()){
			
			// Calculate distance from cube to plane:
			double distance = center[1]; 			// Get distance from ground
			if(distance < 0)						// Don't draw shadows from cubes below ground
				return null;
			distance /= (DodgeCity.ROOM_SIZE_Y/2.0);		// Normalize distance
			
			// Perform transformations:
			v = bottomFace.scale(v, (scale*distance)+scale);		// Scale
			v = bottomFace.rotate(v);
			
			// Translate to ground:
			double[] groundCenter = {center[0], 0, center[2]};
			v = bottomFace.translate(v, groundCenter);
			// Extra scene transformation:
			v = bottomFace.rotateGlobal(v);						// Rotate around eye
			v = bottomFace.translateGlobal(v);					// Translate appropriately	
			
			
			
			double x = (v[0] / (1 - (v[2] / eye)));// - (shadowSize/2.0);
			double y = (v[1] / (1 - (v[2] / eye)));// - (shadowSize);
			myShadow.addPoint((int)x, (int)y);
		}
		return myShadow;
	}
	
	/**
	 * @return the darkness of this cube's shadow
	 */
	public double getShadowDarkness() {
		double[] coordinates = getVertexOnGround();		// Get vertex on ground
		double darkness = coordinates[1] - center[1]; 	// Get distance from center 
		darkness /= (DodgeCity.ROOM_SIZE_Y*2);
		if (darkness < 0)
			darkness = 0;
		else if (darkness > 1)
			darkness = 1;
		return darkness;
	}
	
	/**
	 * Finds the vertex on the x-z plane that is the shadow of the center.
	 * @param face a face to use the methods of
	 * @return the vertex on the ground
	 */
	private double[] getVertexOnGround() {
		Face myFace = new Face(this, null, 3);	// Create an empty face for methods
		double[] v = {0, 0, 0};
		// Calculate rotated, translated, scaled vertex:
		v = myFace.scale(v, scale);
		v = myFace.rotate(v);
		double[] groundCenter = {center[0], 0, center[2]};
		v = myFace.translate(v, groundCenter);
		// Extra scene rotation:
		v = myFace.rotateGlobal(v);						// Rotate around eye
		v = myFace.translateGlobal(v);					// Translate appropriately	
		return v;
	}

	public void changeColor(Color myColor) {
		for (Face f : faces){
			f.faceColor = myColor;
		}
	}
}
