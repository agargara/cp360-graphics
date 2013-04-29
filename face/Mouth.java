package face;

/**
 * A shape that represents a mouth.
 */
public class Mouth extends FacialFeature {
	public Mouth(){
		super(16, 16, 20, 20);
		super.moveTo(0, 0);
		super.quadTo(4, 1, 8, 0);
		super.quadTo(4, 2, 0, 0);
	}
}
