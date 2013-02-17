package view;


import java.awt.image.BufferedImage;

/**
 * An object that stores all the info for the renderer to correctly display an image on the screen.
 * Needs to be queued for rendering every single frame.
 * @author Tadas
 *
 */
public class Renderable 
{
	/**
	 * Constructor initializes a renderable. Some values get set to default values and need to be accessed using getters/setters.
	 * @param ImageName name of the file that stores the image. 
	 * @param PosX Horizontal coordinates of the top left corner of the image.
	 * @param PosY Vertical coordiantes of the top left corner of the image.
	 * @param Width Width of the image on the screen. Image will get resized if the value supplied doesnt match the real size.
	 * @param Height Height of the image on the screen. Image will get resized if the value supplied doesnt match the real size.
	 * @param DepthLevel Also known as Z level, images get sorted based on this value in the rendering queue. Images with lower Z will get rendered first
	 * and images with higher Z will be rendered last and possibly on top of already drawn images.
	 */
 public Renderable(String ImageName, int PosX, int PosY, int Width, int Height, int DepthLevel)
 {
	 if(images == null)
	 {
		 images = new ImageManager();
	 }
	 posX = PosX;
	 posY = PosY;
	 width = Width;
	 height = Height;
	 alpha = 1.0f;
	 depthLevel = DepthLevel;
	 visible = true;
	 startX = 0.0f;
	 startY = 0.0f;
	 sizeX = 1.0f;
	 sizeY = 1.0f;
	 img = images.loadImage(ImageName);
 }
 /**
  * Basic getter.
  * @return horizontal position of the top left corner of the renderable.
  */
 public int getPositionX()
 {
	return posX; 
 }
 /**
  * Basic getter.
  * @return vertical position of the top left corner of the renderable.
  */
 public int getPositionY()
 {
	return posY; 
 }
 /**
  * Basic setter.
  * @param y vertical position of the top left corner
  */
 public void setPositionY(int y)
 {
	posY = y;
 }
 /**
  * Basic setter.
  * @param x horizontal position of the top left corner.
  */
 public void setPositionX(int x)
 {
	posX = x;
 }
 
 /**
  *	Returns the pixel data. Ideally used only by the renderer.
  * @return Pixel data.
  */
 public BufferedImage getImage()
 {
	 return img;
 }
 
 /**
  * Basic getter.
  * @return Also known as Z level, images get sorted based on this value in the rendering queue. Images with lower Z will get rendered first
  * and images with higher Z will be rendered last and possibly on top of already drawn images.
  */
 public int getDepthLevel() 
 {
	return depthLevel;
 }
 /**
 * Basic setter.
 * @param depthLevel Also known as Z level, images get sorted based on this value in the rendering queue. Images with lower Z will get rendered first
 * and images with higher Z will be rendered last and possibly on top of already drawn images.
 */
 public void setDepthLevel(int depthLevel) 
 {
	this.depthLevel = depthLevel;
 }
 /**
  * Basic getter.
  * @return width of the image as it's displayed on the screen (not the true width).
  */
 public int getWidth() 
 {
	return width;
 }
 /**
  * Basic setter.
  * @param width width of the image as it's displayed on the screen (not the true width).
  */
 public void setWidth(int width)
 {
	 this.width = width;
 }
 /**
  * Basic getter.
  * @return height of the image as it's displayed on the screen (not the true width).
  */
 public int getHeight()
 {
	 return height;
 }
 /**
  * Basic setter.
  * @param height height of the image as it's displayed on the screen (not the true width).
  */
 public void setHeight(int height)
 {
	 this.height = height;
 }
 /**
  * Basic getter for transparency.
  * @return 0.0 for completely invisible 1.0 for solid.
  */
 public float getAlpha()
 {
	 return alpha;
 }
 /**
  * Basic setter for transparency.
  * @param alpha 0.0 for completely invisible 1.0 for solid.
  */
 public void setAlpha(float alpha)
 {
	 this.alpha = alpha;
 }
 
 /**
  * Sets which part of the image is to be used by the renderer using relative coordinates. Can render only half of the image for example.
  * @param startX Range:[0.0;1.0] Point where the renderer starts sampling
  * @param sizeX Range:[0.0;1.0] Size of the portion of the image that's to be sampled.
  * @param startY Range:[0.0;1.0] Point where the renderer starts sampling
  * @param sizeY Range:[0.0;1.0] Size of the portion of the image that's to be sampled.
  */
 public void setSampleCoordinates(float startX, float sizeX, float startY, float sizeY)
 {
	 this.startX = startX;
	 this.sizeX = sizeX;
	 this.startY = startY;
	 this.sizeY = sizeY;
 }
 /**
  * Used internally by the renderer. Using real coordinates rather than relative.
  * @return Point where 
  */
 public int getSourceStartHorizontal()
 {
	 return (int)(startX * img.getWidth());
 }
 /**
  * Used internally by the renderer. Using real coordinates rather than relative.
  * @return Size of the sample in that axis
  */
 public int getSourceSizeHorizontal()
 {
	 return (int)(sizeX * img.getWidth());
 }
 /**
  * Used internally by the renderer. Using real coordinates rather than relative.
  * @return Point where renderer starts sampling
  */
 public int getSourceStartVertical()
 {
	 return (int)(startY * img.getHeight());
 }
 /**
  * Used internally by the renderer. Using real coordinates rather than relative.
  * @return Size of the sample in that axis.
  */
 public int getSourceSizeVertical()
 {
	 return (int)(sizeY * img.getHeight());
 }
 /**
  * Sets renderables visibility. If set to false renderable won't appear on the screen.
  * True by default
  * @param b visibility
  */
 public void setVisible(boolean b)
 {
	 visible = b;
 }
 /**
  * Sets renderables visibility. If set to false renderable won't appear on the screen.
  * True by default
  * @return visibility
  */
 public boolean getVisibility()
 {
	 return visible;
 }
 
 float startX;
 float sizeX;
 float startY;
 float sizeY;
 
 int posX;
 int posY;
 
 int width;
 int height;
 
 boolean visible;
 
 int depthLevel;
 float alpha;
 BufferedImage img;
 static ImageManager images;
}

