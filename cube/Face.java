package cube;

import java.awt.Color;

/**
 * Represents a face of a polygon. Has three vertices that represent the face.
 * Contains methods for calculating the normal, testing visibility, and rotating.
 * @author David
 *
 */
public class Face {
	
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
	 * @param i the vertex to get
	 * @return the ith vertex 
	 */
	public double[] getVertex(int i){
		return vertices[i];
	}
	
	/**
	 * @return the number of vertices on this face
	 */
	public int numVertices(){
		return vertices.length;
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
	 * @param eyeLocation the coordinates of the camera
	 * @return true if this face is visible to the camera
	 */
	public boolean isVisible(double[] eyeLocation) {
		double[][] v = rotate();							// Get the rotated face
		double[] eyeDifference = {	eyeLocation[0]-v[0][0],	// Get vector from eye to first vertex 
									eyeLocation[1]-v[0][1],
									eyeLocation[2]-v[0][2]};
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
	 * Rotates all vertices for this face.
	 * @return the rotated vertices
	 */
	private double[][] rotate() {
		double[][] rotatedVertices = new double[vertices.length][];
		for(int i=0; i<vertices.length; i++)	// Loop through all vertices
			rotatedVertices[i] = rotate(vertices[i]);
		return rotatedVertices;
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
}
