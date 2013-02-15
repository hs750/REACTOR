package view;

public abstract class TextButton extends Button
{

	public TextButton(Text t, String imageName, int posX, int posY, int width,
			int height) 
	{
		super(imageName, posX, posY, width, height);
		text = t;
		t.setPosY(posY + height/3*2);
		t.setPosX(posX + width/4);
	}
	
	@Override
	public void enable() 
	{
		super.enable();
		text.setVisibility(true);
	}
	@Override
	public void disable() 
	{
		super.disable();
		text.setVisibility(false);
	}
	@Override
	public void setPosX(int posX) 
	{
		super.setPosX(posX);
		text.setPosX(posX);
	}
	@Override
	public void setPosY(int posY) 
	{
		super.setPosY(posY);
		text.setPosY(posY);
	}
	public Text getText()
	{
		return text;
	}
	Text text;
}
