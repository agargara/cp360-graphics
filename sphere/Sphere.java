package sphere;

/**
 * Represents a 3D sphere that is 
 * lit by a light source and ambient light.
 * @author David
 */
public class Sphere {

	int radius;						// Radius of the sphere
	double[] lightSource;			// Location of light source
	double 	lightScale;				// Scale for light location
	double	ambientLight;			// Constant amount of ambient light
	double 	diffuseLight;			// Constant amount of diffuse light
	
	/**
	 * Constructs a sphere with default values.
	 */
	public Sphere(){
		radius = 128;
		lightScale = 1;
		ambientLight = 0.3;			// Amount of ambient light
		diffuseLight = 0.7;			// Amount of diffuse light
		lightSource = new double[]{0, 0, 128};	// Place light 128 units away from origin
	}

	/**
	 * Calculates the illumination of a given point on the sphere.
	 * @param x the x coordinate
	 * @param y the y coordinate
	 * @return the amount of illumination of the point
	 */
	public double getIllumination(double x, double y){
		double z = Math.sqrt(radius * radius - y*y - x*x);	// Calculate z coordinate
		// Calculate diffuse light:
		double diffuse = (diffuseLight * 
						  dotProduct(x, y, z, lightSource[0], lightSource[1], lightSource[2]));
		if (diffuse < 0)	// If no diffuse light, use ambient light only
			return ambientLight;
		else				// Otherwise use the sum
			return diffuse + ambientLight;
	}

	/**
	 * Returns the normalized dot product of two 2d vectors.
	 * @param x
	 * @param y
	 * @param d
	 * @param e
	 * @return
	 */
	private double dotProduct(double x, double y, double z, double d, double e, double f){
		double length1 = Math.sqrt(x*x + y*y + z*z);	// Calculate length of [x, y]
		double length2 = Math.sqrt(d*d + e*e + f*f);	// Calculate length of [d, e]
		// Normalize vectors:
		x /= length1;
		y /= length1;
		z /= length1;
		d /= length2;
		e /= length2;
		f /= length2;
		// Return dot product:
		return (x * d) + (y * e) + (z * f);
	}

	public int getRadius() {
		return radius;
	}
	
	/**
	 * Change the location of the light
	 * @param x the x coordinate of the light source
	 * @param y the y coordinate of the light source
	 */
	public void setLightLocation(double x, double y){
		lightSource[0] = x * lightScale;
		lightSource[1] = y * lightScale;
	}
}
