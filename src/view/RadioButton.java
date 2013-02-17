package view;

import java.util.ArrayList;
/**
 * Controls the states of the buttons that get assigned to it. Only one button controlled by the RadioButton can be pressed at a time. Clicking on button will make it pressed
 * and all the other ones idle.
 * @author Tadas
 *
 */
public class RadioButton 
{
	/**
	 * Basic constructor
	 */
	public RadioButton()
	{
		buttons = new ArrayList<ToggleButton>();
	}
	/**
	 * Adds a button the Radio Button and assigns a unique ID to it.
	 * @param b Button
	 */
	public void addButton(ToggleButton b)
	{
		b.id = buttons.size();
		buttons.add(b);
	}
	/**
	 * Disable all buttons under its control
	 */
	public void disableButtons()
	{
		for(ToggleButton b : buttons)
		{
			b.disable();
		}
	}
	/**
	 * Enable all buttons under its control.
	 */
	public void enableButtons()
	{
		for(ToggleButton b : buttons)
		{
			b.enable();
		}
	}
	/**
	 * Makes all the buttons idle except for button that has the same id as the one supplied.
	 * @param id Button id
	 */
	public void switchToggleOthers(int id)
	{
		for(ToggleButton b : buttons)
		{
			if(b.id != id)
				b.changeToggled(false);
		}
	}
	
	ArrayList<ToggleButton> buttons;
}
