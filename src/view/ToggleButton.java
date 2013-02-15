package view;

import java.awt.event.MouseEvent;

import view.Button.buttonState;

public abstract class ToggleButton extends Button
{

	public ToggleButton(String imageName, int posX, int posY, int width,
			int height) {
		super(imageName, posX, posY, width, height);
		parent = null;
		id = 0;
	}
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
	
	public void changeParentRadioButton(RadioButton b)
	{
		parent = b;
	}
	public int id; //Used by radio button
	RadioButton parent;
}
