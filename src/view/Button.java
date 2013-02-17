package view;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
/**
 * A basic button. It has 3 states, idle, mouseOver and pressed. Need to register the button using renderer to make sure it receives mouse messages.
 * Need queue for rendering every frame to make sure it appears on the screen.
 * Override doAction method to associate specific functionality with the button
 * @author Tadas
 *
 */

public class Button extends MouseAdapter
{
	/**
	 * Initializes the button.
	 * @param imageName file name of the image. It will be stored as an AnimatedRenderable with 3 frames internally and the image itself should reflect that.
	 * @param posX position of the top left corner of the image representing the button
	 * @param posY position of the top left corner of the image representing the button
	 * @param width width of the button
	 * @param height height of the button
	 */
	public Button(String imageName, int posX, int posY, int width, int height)
	{
		this.images = new AnimatedRenderable(imageName, posX, posY, width, height, 100, 3, 0);
		this.posX = posX;
		this.posY = posY;
		this.width = width;
		this.height = height;
		enabled = true;
		state = buttonState.idle;
	}
	/**
	 * Position another button to the right of this button
	 * @param b Button to be position
	 */
	public void allign(Button b)
	{
		b.setPosX(posX + width + 5);
		b.setPosY(posY);
	}
	/**
	 * Positions another button to the right of this button, leaving the specified space between them
	 * @param b the other button
	 * @param space space between the buttons
	 */
	public void allign(Button b, int space)
	{
		b.setPosX(posX + width + space);
		b.setPosY(posY);
	}
	/**
	 * Places a text object to the right of the button
	 * @param t Text to be placed after the button
	 */
	public void allign(Text t)
	{
		t.setPosX(posX + width + 5);
		t.setPosY(posY + height);
	}
	/**
	 * Places a text object to the right of the button.
	 * @param t Text object to be placed after the button
	 * @param space space between the button and the text object
	 */
	public void allign(Text t, int space)
	{
		t.setPosX(posX + width + space);
		t.setPosY(posY + height);
	}
	/**
	 * Places the text object bellow the button.
	 * @param t Text object to be placed bellow the button
	 */
	public void nextLine(Text t)
	{
		t.setPosX(posX);
		t.setPosY(posY);
	}
	/**
	 * Makes the button get rendered and makes it receive messages.
	 */
	public void enable()
	{
		images.setVisible(true);
		enabled = true;
	}
	/**
	 * Stops the button from rendering and receiving messages.
	 */
	public void disable()
	{
		images.setVisible(false);
		enabled = false;
	}
	/**
	 * Basic getter.
	 * @return position of the top left corner of the button.
	 */
	public int getPosX() {
		return posX;
	}
	/**
	 * Basic setter.
	 * @param posX  position of the top left corner of the button.
	 */
	public void setPosX(int posX) {
		this.posX = posX;
		images.setPositionX(posX);
	}
	/**
	 * Basic getter.
	 * @return position of the top left corner of the button.
	 */
	public int getPosY() {
		return posY;
	}
	/**
	 * Basic setter.
	 * @param posY  position of the top left corner of the button.
	 */
	public void setPosY(int posY) {
		this.posY = posY;
		images.setPositionY(posY);
	}
	/**
	 * Basic getter.
	 * @return width of the button
	 */
	public int getWidth() {
		return width;
	}
	/**
	 * Basic setter.
	 * @param width width of the button
	 */
	public void setWidth(int width) {
		this.width = width;
		images.setWidth(width);
	}
	/**
	 * Basic getter.
	 * @return height of the button
	 */
	public int getHeight() {
		return height;
	}
	/**
	 * Basic setter.
	 * @param height height of the button
	 */
	public void setHeight(int height) {
		this.height = height;
		images.setHeight(height);
	}

	/**
	 * Override this method to make the button do specific functionality based on your needs.
	 */
	public void doAction()
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
	public void mousePressed(MouseEvent e) 
	{
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
		super.mouseMoved(e);
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
	/**
	 * Checks if the specified coordinates are within the area of the button.
	 * @param x
	 * @param y
	 * @return returns true if the coordinates are within the button's bounding area.
	 */
	boolean checkIfIntercepting(int x, int y)
	{
		if(enabled)
			if(x >= posX && x <= posX + width)
				if(y >= posY && y <=posY + height)
					return true;
		return false;
	}
	/**
	 * Returns renderable that stores the images used by the button. Used internally by the renderer.
	 * @return Renderable storing the button images.
	 */
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
