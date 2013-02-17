package view;

import java.awt.event.MouseEvent;

import view.Button.buttonState;

/**
 * Acts like a regular Button, except that if it gets clicked it will stay in a pressed mode rather than idle or mouseOver. 
 * Can add to a RadioButton to make it 'unpressed' again, which is the main reason for this class to exist. If this button is pressed and user clicks on another button 
 * that is added to the same radio button this button will become idle.
 * @author Tadas
 *
 */
public abstract class ToggleButton extends Button
{
	/**
	 * Initializes the button.
	 * @param imageName file name of the image. It will be stored as an AnimatedRenderable with 3 frames internally and the image itself should reflect that.
	 * @param posX position of the top left corner of the image representing the button
	 * @param posY position of the top left corner of the image representing the button
	 * @param width width of the button
	 * @param height height of the button
	 */
	public ToggleButton(String imageName, int posX, int posY, int width,
			int height) {
		super(imageName, posX, posY, width, height);
		parent = null;
		id = 0;
	}
	/**
	 * Initializes the button.
	 * @param imageName file name of the image. It will be stored as an AnimatedRenderable with 3 frames internally and the image itself should reflect that.
	 * @param posX position of the top left corner of the image representing the button
	 * @param posY position of the top left corner of the image representing the button
	 * @param width width of the button
	 * @param height height of the button
	 * @param parent RadioButton that will handle this button's state based on what the user clicks on.
	 */
	public ToggleButton(String imageName, int posX, int posY, int width,
			int height, RadioButton parent) {
		super(imageName, posX, posY, width, height);
		this.parent = parent;
		parent.addButton(this);
	}
	@Override
	public void mouseMoved(MouseEvent e) 
	{
		
		boolean intersecting = checkIfIntercepting(e.getX(), e.getY());
		if (state == buttonState.mouseOver && !intersecting)
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
	public void mouseClicked(MouseEvent e)
	{
		if(checkIfIntercepting(e.getX(), e.getY()))
		{
				doAction();
		
			state = buttonState.pressed;
			images.setCurrentFrame(2);
			if(parent != null)
				parent.switchToggleOthers(id);
		}
	}
	
	@Override
	public void mouseReleased(MouseEvent e) 
	{

	}
	/**
	 * Toggles between button being pressed and idle. Used by radio button.
	 * @param t If true buttons becomes pressed, if false button becomes idle.
	 */
	public void changeToggled(boolean t)
	{
		if(t)
		{
			state = buttonState.pressed;
			images.setCurrentFrame(2);
		}
		else
		{
			state = buttonState.idle;
			images.setCurrentFrame(0);
		}
	}
	/**
	 * Adds RadioButton as a parent to this object.
	 * @param b Radio button will now handle pressed/idle states for this object
	 */
	public void changeParentRadioButton(RadioButton b)
	{
		parent = b;
	}
	public int id; //Used by radio button
	RadioButton parent;
}
