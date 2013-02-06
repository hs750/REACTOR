package view;

import javax.imageio.*;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
/**
 * 
 * @author Tadas
 *
 */
public class Renderable 
{
 public Renderable(String ImageName, int PosX, int PosY, int Height, int Width, int DepthLevel)
 {
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
	 
	 try {
	     img = ImageIO.read(new File(ImageName));
	 } catch (IOException e) {
	 }
 }
 public int getPositionX()
 {
	return posX; 
 }
 public int getPositionY()
 {
	return posY; 
 }
 public void setPositionY(int y)
 {
	posY = y;
 }
 public void setPositionX(int x)
 {
	posX = x;
 }
 
 public BufferedImage getImage()
 {
	 return img;
 }
 
 public int getDepthLevel() 
 {
	return depthLevel;
 }
 
 public void setDepthLevel(int depthLevel) 
 {
	this.depthLevel = depthLevel;
 }
 
 public int getWidth() 
 {
	return width;
 }
 
 public void setWidth(int width)
 {
	 this.width = width;
 }
 
 public int getHeight()
 {
	 return height;
 }
 public void setHeight(int height)
 {
	 this.height = height;
 }
 
 public float getAlpha()
 {
	 return alpha;
 }
 public void setAlpha(float alpha)
 {
	 this.alpha = alpha;
 }
 
 //Horrible method name
 public void setSampleCoordinates(float startX, float sizeX, float startY, float sizeY)
 {
	 this.startX = startX;
	 this.sizeX = sizeX;
	 this.startY = startY;
	 this.sizeY = sizeY;
 }
 
 public int getSourceStartHorizontal()
 {
	 return (int)(startX * img.getWidth());
 }
 
 public int getSourceSizeHorizontal()
 {
	 return (int)(sizeX * img.getWidth());
 }
 
 public int getSourceStartVertical()
 {
	 return (int)(startY * img.getHeight());
 }
 
 public int getSourceSizeVertical()
 {
	 return (int)(sizeY * img.getHeight());
 }
 
 public void setVisible(boolean b)
 {
	 visible = b;
 }
 
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

}

