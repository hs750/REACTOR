package view;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.*;
public class ImageManager 
{
	public ImageManager()
	{
		imageStorage = new HashMap<String, BufferedImage>();
	}
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
