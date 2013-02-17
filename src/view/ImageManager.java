package view;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.*;
/**
 * Object responsible for loading images. Makes sure that duplicate would get loaded only once.
 * @author Tadas
 *
 */
public class ImageManager 
{
	/**
	 * Basic constructor.
	 */
	public ImageManager()
	{
		imageStorage = new HashMap<String, BufferedImage>();
	}
	/**
	 * Loads the image and stores it internally for future use.
	 * @param image file name of the image. 
	 * @return Pixel data from the loaded file. Used by the renderer internally.
	 */
	public BufferedImage loadImage(String image)
	{
		BufferedImage im = (BufferedImage) imageStorage.get(image);
		if(im == null)
		{
			try {
			     im = ImageIO.read(new File(image));
			 } catch (IOException e) {
				 System.out.print(image + " file does not exist.");
			 }
			imageStorage.put(image, im);
		}
		return im;
	}
	private Map<String, BufferedImage> imageStorage;
}
