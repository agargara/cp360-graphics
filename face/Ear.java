package face;

/**
 * A shape that represents an ear.
 */
public class Ear extends FacialFeature {
	public Ear(){
		super(4, 8, 80, 80);
		super.moveTo(0, 0);
		super.quadTo(-6, -4, 0, -8);
		super.quadTo(-3, -6, -2, -4);
		super.quadTo(-3, -2, 0, 0);
	}
}
