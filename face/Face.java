package face;

/**
 * A shape that represents a face.
 */
public class Face extends FacialFeature {
	public Face(){
		super(48, 48, 60, 60);
		super.moveTo(0, 0);
		super.quadTo(2, -1, 4, 0);
		super.quadTo(6, 4, 4, 8);
		super.quadTo(2, 9, 0, 8);
		super.quadTo(-2, 4, 0, 0);
	}
}
