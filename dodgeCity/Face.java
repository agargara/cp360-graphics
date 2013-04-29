package dodgeCity;

import java.awt.Color;

/**
 * Represents a face of a polygon. Has three vertices that represent the face.
 * Contains methods for calculating the normal, testing visibility, and rotating.
 * @author David
 *
 */
public class Face implements Comparable<Object>{
	
	Color faceColor;		// The color of this face
	Cube myCube;			// The cube that contains this face
	double[][] vertices;	// The vertices that describe this face
	
	/**
	 * Constructs a face.
	 * @param _myCube the cube that contains this face
	 * @param _faceColor the color of this face
	 * @param _vertices the vertices that describe this face
	 */
	public Face(Cube _myCube, Color _faceColor, 
				double[][] _vertices){
		myCube = _myCube;
		faceColor = _faceColor;
		vertices = _vertices;
	}
	
	/**
	 * Constructs an empty face with n vertices.
	 */
	public Face(Cube _myCube, Color _faceColor, int n) {
		myCube = _myCube;
		faceColor = _faceColor;
		vertices = new double[n][3];
	}

	/**
	 * @param i the vertex to get
	 * @return the ith vertex 
	 */
	public double[] getVertex(int i){
		return vertices[i];
	}
	
	/**
	 * @return the vertices that describe this face
	 */
	public double[][] getVertices(){
		return vertices;
	}
	
	/**
	 * @return the number of vertices on this face
	 */
	public int numVertices(){
		return vertices.length;
	}
	
	/**
	 * Calculates the normal vector for this face.
	 * @return the vector perpendicular to this face.
	 */
	public double[] calculateNormal(){
		return calculateNormal(vertices);
	}
	
	/**
	 * Calculates the normal vector for a given face.
	 * @return the vector perpendicular to the given face.
	 */
	public double[] calculateNormal(double[][] v){
		double[] normal = new double[3];
		// Calculate three vectors on the plane of the face:
		double[] i = {v[1][0]-v[0][0], v[2][0]-v[0][0]};
		double[] j = {v[1][1]-v[0][1], v[2][1]-v[0][1]};
		double[] k = {v[1][2]-v[0][2], v[2][2]-v[0][2]};
	
		// Use determinant to calculate normal, use negative to get normal pointing away from cube
		normal[0] =  -(	(j[0] * k[1]) - (j[1] * k[0]));
		normal[1] =    ((i[0] * k[1]) - (i[1] * k[0]));
		normal[2] =  -(	(i[0] * j[1]) - (i[1] * j[0]));
		return normal;
	}

	/**
	 * Calculates the visibility of this face based on a given eye location.
	 * @param eye the coordinates of the camera
	 * @return true if this face is visible to the camera
	 */
	public boolean isVisible(double eye) {
		// double[][] v = rotate();							// Get the rotated face
		double[][] v = vertices;
		double[] eyeDifference = {	0-v[0][0],	// Get vector from eye to first vertex 
									0-v[0][1],
									eye-v[0][2]};
		// Find dot product of the normal and eyeDifference
		double dotProduct = 0;
		for(int i=0; i<eyeDifference.length; i++){
			dotProduct += (eyeDifference[i] * calculateNormal(v)[i]);
		}
		if (dotProduct > 0)	// Test sign of dot product
			return true;
		else
			return false;
	}
	
	/**
	 * Rotates a single vertex around all axes.
	 * @param v the vertex to rotate
	 * @return the rotated vertex
	 */
	public double[] rotate(double[] v) {
		double[][] rm = myCube.getRotationMatrix(); // Get the rotation matrix
		// Multiply the given vertex by the rotation matrix:
		double[] returnV = 
			{	rm[0][0] * v[0] + rm[0][1] * v[1] + rm[0][2] * v[2],
				rm[1][0] * v[0] + rm[1][1] * v[1] + rm[1][2] * v[2],
				rm[2][0] * v[0] + rm[2][1] * v[1] + rm[2][2] * v[2]	};
		return returnV;
	}

	/**
	 * @return the color of this face
	 */
	public Color getColor() {
		return faceColor;
	}

	/**
	 * Translates a single vertex.
	 * @param v the vertex to translate
	 * @param translation how much to translate v
	 * @return
	 */
	public double[] translate(double[] v, double[] translation) {
		double[] returnV = new double[v.length];
		for (int i=0; i<v.length; i++)
			returnV[i] = v[i] + translation[i];
		return returnV;
	}

	public double[] scale(double[] v, double scaleFactor) {
		double[] returnV = new double[v.length];
		for (int i=0; i<3; i++)
			returnV[i] = v[i] * scaleFactor;
		return returnV;
	}
	
	/**
	 * Sorts faces by the lowest z-value of all vertices.
	 */
	public int compareTo(Object o1) {
		Face c = (Face)o1;
        if (this.getMinZ() == c.getMinZ()){
        	// If minZ are equal, sort by the rest of the vertices.
        	for (int i = 0; i<vertices.length; i++){
        		for (int j = 0; j<vertices[0].length; j++){
        			if (this.vertices[i][j] < c.vertices[i][j])
        				return -1;
        			else if (this.vertices[i][j] > c.vertices[i][j])
        				return 1;
        		}
        	}
            return 0;
        }else if (this.getMinZ() > c.getMinZ())
            return 1;
        else
            return -1;
    }

	/**
	 * @return the minimum z-value of all vertices.
	 */
	private double getMinZ() {
		double minZ = vertices[0][2]; 
		for (int i=1; i<vertices.length; i++){
			if(vertices[i][2] < minZ)
				minZ = vertices[i][2];
		}
		return minZ;
	}

	/**
	 * Adds the ith vertex to this face.
	 * @param v the vertex to add
	 */
	public void addVertex(int i, double[] v) {
		vertices[i] = v;
	}

	/**
	 * Applies the global rotation matrix to a given vertex.
	 * @param v the vertex to rotate
	 * @return the rotated vertex
	 */
	public double[] rotateGlobal(double[] v) {
		double[][] rm = myCube.getGlobalRotationMatrix(); // Get the global rotation matrix
		// Multiply the given vertex by the global rotation matrix:
		double[] returnV = 
			{	rm[0][0] * v[0] + rm[0][1] * v[1] + rm[0][2] * v[2],
				rm[1][0] * v[0] + rm[1][1] * v[1] + rm[1][2] * v[2],
				rm[2][0] * v[0] + rm[2][1] * v[1] + rm[2][2] * v[2]	};
		return returnV;
	}

	public double[] translateGlobal(double[] v) {
		return translate(v, new double[]{0, myCube.eye/4, -(myCube.eye)});// Translate back and down so eye is above
	}
}
