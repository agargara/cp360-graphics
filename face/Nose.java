package face;

/**
 * A shape that represents a nose.
 */
public class Nose extends FacialFeature {
	public Nose(){
		super(16, 16, 20, 20);
		super.moveTo(0, 0);
		super.quadTo(0, 2, -1, 3);
		super.quadTo(-1, 4, 0, 5);
		super.quadTo(2, 6, 4, 5);
		super.quadTo(5, 3, 4, 4);
		super.quadTo(4, 0, 4, 4);
	}
}
