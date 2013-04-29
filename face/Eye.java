package face;

/**
 * A shape that represents an eye.
 */
public class Eye extends FacialFeature {
	public Eye(){
		super(32, 8, 20, 20);
		int[] xCoords = new int[]{0, 1, 2, 3, 2, 1};
		int[] yCoords = new int[]{0, 1, 1, 0, -1, -1};
		for(int i=0; i<xCoords.length; i++)
			super.addPoint(xCoords[i], yCoords[i]);
	}
}
