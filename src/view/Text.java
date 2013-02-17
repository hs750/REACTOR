package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;

/**
 * Text rendering facility. Stores the string that will be rendered internally. Needs to be queued for rendering every frame. Can control transparency, color, size
 * position on the screen.
 * @author Tadas
 *
 */
public class Text 
{
	/**
	 * Basic constructor. Sets the position values to (0;0). Useful when the object will be alligned anyway
	 * @param storedText string that will be drawn onto the screen
	 * @param size Text size
	 */
	public Text(String storedText,  int size)
	{
		this.storedText = storedText;
		this.posX = 0;
		this.posY = 0;
		this.size = size;
		alpha = 1.0f;
		color = Color.black;
		visible = true;
	}
	/**
	 * Basic constructor
	 * @param storedText string that will be drawn onto the screen
	 * @param posX position of the first character
	 * @param posY position of the first character
	 * @param size Text size
	 */
	public Text(String storedText, int posX, int posY, int size)
	{
		this.storedText = storedText;
		this.posX = posX;
		this.posY = posY;
		this.size = size;
		alpha = 1.0f;
		color = Color.black;
		visible = true;
	}
	/**
	 * Returns a string that will be drawn onto the screen.
	 * @return String stored by the object
	 */
	public String getString()
	{
		return storedText;
	}
	/**
	 * Changes the string that will be drawn onto the screen.
	 * @param storedtext String to be stored by the object
	 */
	public void setString(String storedText)
	{
		this.storedText = storedText;
	}
	
	/**
	 * Returns horizontal position of the first character
	 * @return horizontal position of the first character
	 */
	public int getPosX()
	{
		return posX;
	}
	/**
	 * Sets horizontal position of the first character
	 * @param posX horizontal position of the first character
	 */
	public void setPosX(int posX)
	{
		this.posX = posX;
	}
	/**
	 * Returns vertical position of the first character
	 * @return vertical position of the first character
	 */
	public int getPosY() 
	{
		return posY;
	}
	/**
	 * Sets vertical position of the first character
	 * @param posX vertical position of the first character
	 */
	public void setPosY(int posY) 
	{
		this.posY = posY;
	}
	
	/**
	 * Returns the transparency of the text that will be drawn onto the screen
	 * @return the transparency of the text that will be drawn onto the screen
	 */
	public float getAlpha()
	{
		return alpha;
	}
	/**
	 * Sets the transparency of the text that will be drawn onto the screen
	 * @param alpha the transparency of the text that will be drawn onto the screen
	 */
	public void setAlpha(float alpha)
	{
		this.alpha = alpha;
	}
	/**
	 * Sets the size of the characters
	 * @param size size of the characters
	 */
	public void setSize(int size)
	{
		this.size = size;
	}
	/**
	 * Returns the size of the characters
	 * @return the size of the characters
	 */
	public int getSize()
	{
		return size;
	}
	/**
	 * Returns the color of the text. Black by default.
	 * @return the color of the text. Black by default.
	 */
	public Color getColor()
	{
		return color;
	}
	/**
	 * Sets the color of the text. Black by default.
	 * @param color the color of the text. Black by default.
	 */
	public void setColor(Color color)
	{
		this.color = color;
	}
	/**
	 * Positions a button to the right of where the text ends.
	 * @param b Button
	 * @param metrics information about the font. Can be created using the renderer
	 */
	public void allign(Button b, FontMetrics metrics)
	{
		int width = metrics.stringWidth(storedText);
		b.setPosX(posX + width + 5);
		b.setPosY(posY - metrics.getHeight());
	}
	/**
	 * Positions a Text object bellow this one.
	 * @param t Text
	 * @param metrics information about the font. Can be created using the renderer
	 */
	public void nextLine(Text t, FontMetrics metrics)
	{
		int height = metrics.getHeight();
		t.setPosX(posX);
		t.setPosY(posY + height + 5);
	}
	/**
	 * Positions a button bellow the text object
	 * @param b Button
	 * @param metrics information about the font. Can be created using the renderer
	 */
	public void nextLine(Button b, FontMetrics metrics)
	{
		int height = metrics.getHeight();
		b.setPosX(posX);
		b.setPosY(posY + height + 10);
	}
	/**
	 * Changes the text from visible into invisible (won't be rendered).
	 * @param v visibility
	 */
	public void setVisibility(boolean v)
	{
		visible = v;
	}
	/**
	 * Returns whether the text object will be visible accepted by the renderer or not
	 * @return visibility
	 */
	public boolean getVisibility()
	{
		return visible;
	}
	
	float alpha;
	
	boolean visible;
	
	Color color;
	int size;
	int posX;
	int posY;
	String storedText;

}
