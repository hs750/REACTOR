package view;

import java.util.ArrayList;

public class RadioButton 
{
	public RadioButton()
	{
		buttons = new ArrayList<ToggleButton>();
	}
	public void addButton(ToggleButton b)
	{
		b.id = buttons.size();
		buttons.add(b);
	}
	public void disableButtons()
	{
		for(ToggleButton b : buttons)
		{
			b.disable();
		}
	}
	public void enableButtons()
	{
		for(ToggleButton b : buttons)
		{
			b.enable();
		}
	}
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
