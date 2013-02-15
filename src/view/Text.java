package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;

/**
 * 
 * @author Tadas
 *
 */
public class Text 
{
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
	
	public String getString()
	{
		return storedText;
	}
	
	public void setString(String storedText)
	{
		this.storedText = storedText;
	}
	
	
	public int getPosX()
	{
		return posX;
	}

	public void setPosX(int posX)
	{
		this.posX = posX;
	}

	public int getPosY() 
	{
		return posY;
	}

	public void setPosY(int posY) 
	{
		this.posY = posY;
	}
	
	public float getAlpha()
	{
		return alpha;
	}
	
	public void setAlpha(float alpha)
	{
		this.alpha = alpha;
	}
	public void setSize(int size)
	{
		this.size = size;
	}
	public int getSize()
	{
		return size;
	}
	
	public Color getColor()
	{
		return color;
	}
	
	public void setColor(Color color)
	{
		this.color = color;
	}
	
	public void allign(Button b, FontMetrics metrics)
	{
		int width = metrics.stringWidth(storedText);
		b.setPosX(posX + width + 5);
		b.setPosY(posY - metrics.getHeight());
	}
	
	public void nextLine(Text t, FontMetrics metrics)
	{
		int height = metrics.getHeight();
		t.setPosX(posX);
		t.setPosY(posY + height + 5);
	}
	public void nextLine(Button b, FontMetrics metrics)
	{
		int height = metrics.getHeight();
		b.setPosX(posX);
		b.setPosY(posY + height + 10);
	}
	
	public void setVisibility(boolean v)
	{
		visible = v;
	}
	
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
