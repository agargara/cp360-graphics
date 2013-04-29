package face;

/**
 * A shape that represents an eyebrow.
 */
public class Eyebrow extends FacialFeature {
	public Eyebrow(){
		super(32, 16, 50, 50);
		int[] xCoords = new int[]{0, 1, 2, 1, 0};
		int[] yCoords = new int[]{0, -2, 0, -1, 0};
		for(int i=0; i<xCoords.length; i++)
			super.addPoint(xCoords[i], yCoords[i]);
	}
}
