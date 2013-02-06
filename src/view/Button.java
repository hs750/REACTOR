package view;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
/**
 * 
 * @author Tadas
 *
 */
public class Button extends MouseAdapter
{
	
	public Button(String imageName, int posX, int posY, int width, int height)
	{
		this.images = new AnimatedRenderable(imageName, posX, posY, height, width, 100, 3, 0);
		this.posX = posX;
		this.posY = posY;
		this.width = width;
		this.height = height;
		enabled = true;
		state = buttonState.idle;
	}
	
	public void allign(Button b)
	{
		b.setPosX(posX + width + 5);
		b.setPosY(posY);
	}
	public void allign(Text t)
	{
		t.setPosX(posX + width + 5);
		t.setPosY(posY + height);
	}
	
	public void enable()
	{
		images.setVisible(true);
		enabled = true;
	}
	public void disable()
	{
		images.setVisible(false);
		enabled = false;
	}
	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
		images.setPositionX(posX);
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
		images.setPositionY(posY);
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
		images.setWidth(width);
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
		images.setHeight(height);
	}

	
	public  void doAction()
	{
		System.out.println("CLICK");
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		
		super.mouseClicked(e);
		if(checkIfIntercepting(e.getX(), e.getY()))
		{
			doAction();
		}
	}


	@Override
	public void mousePressed(MouseEvent e) {
		super.mousePressed(e);
		if(checkIfIntercepting(e.getX(), e.getY()))
		{
			state = buttonState.pressed;
			images.setCurrentFrame(2);
			
		}
	}
	@Override
	public void mouseMoved(MouseEvent e) 
	{
		super.mousePressed(e);
		boolean intersecting = checkIfIntercepting(e.getX(), e.getY());
		if (state == buttonState.pressed || state == buttonState.mouseOver && !intersecting)
		{
			state = buttonState.idle;
			images.setCurrentFrame(0);
			
		}
		else if(state == buttonState.idle && intersecting)
		{
			images.setCurrentFrame(1);
			state = buttonState.mouseOver;
		}
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		super.mouseReleased(e);
		if(state == buttonState.pressed && checkIfIntercepting(e.getX(), e.getY()))
		{
			state = buttonState.mouseOver;
			images.setCurrentFrame(1);
			
		}
	}
	boolean checkIfIntercepting(int x, int y)
	{
		if(enabled)
			if(x >= posX && x <= posX + width)
				if(y >= posY && y <=posY + height)
					return true;
		return false;
	}
	
	public AnimatedRenderable getRenderable()
	{
		return images;
	}
	int posX;
	int posY;
	buttonState state;
	int width;
	int height;
	boolean enabled;
	enum buttonState { idle, mouseOver, pressed }
	AnimatedRenderable images;
}
