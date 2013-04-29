import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class JPG_DC {
	/**
	 * Main method which draws a frame with a canvas and control panel.
	 */
	public static void main(String[] args){
		try {
			BufferedImage myImage = ImageIO.read(new File("test.jpg"));
			int w = myImage.getWidth();
			int h = myImage.getHeight();
			
			// Draw shapes
			Graphics2D g2d = myImage.createGraphics();
			for (int i=0; i<w; i+=40)
				for(int j=h; j>0; j-=80)
						g2d.drawOval(i, j, 20, 20*j);
			
			// Play with pixels
			for(int i=0; i<w; i++){
				for(int j=0; j<h; j++){
					int color = myImage.getRGB((w-i)%w, (h-j)%h);
					color >>= (j+i)%2;	// bit shift
					myImage.setRGB(i, j, color);
				}
			}
			ImageIO.write(myImage, "jpg", new File("result.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
