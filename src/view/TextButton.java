package view;
/**
 * Acts like a regular Button, however it is also tied to a Text object. Used to make a lot of similar buttons that just have a different label.
 * @author Tadas
 *
 */
public abstract class TextButton extends Button
{
	/**
	 * Initializes the button.
	 * @param t Text object to be tied with this button.
	 * @param imageName file name of the image. It will be stored as an AnimatedRenderable with 3 frames internally and the image itself should reflect that.
	 * @param posX position of the top left corner of the image representing the button
	 * @param posY position of the top left corner of the image representing the button
	 * @param width width of the button
	 * @param height height of the button
	 */
	public TextButton(Text t, String imageName, int posX, int posY, int width,
			int height) 
	{
		super(imageName, posX, posY, width, height);
		text = t;
		t.setPosY(posY + height/3*2);
		t.setPosX(posX + width/4);
	}
	
	/**
	 * Makes the button get rendered and makes it receive messages.
	 */
	@Override
	public void enable() 
	{
		super.enable();
		text.setVisibility(true);
	}
	/**
	 * Stops the button from rendering and receiving messages.
	 */
	@Override
	public void disable() 
	{
		super.disable();
		text.setVisibility(false);
	}
	/**
	 * Basic setter.
	 * @param posX  position of the top left corner of the button.
	 */
	@Override
	public void setPosX(int posX) 
	{
		super.setPosX(posX);
		text.setPosX(posX + width/4);
	}
	/**
	 * Basic setter.
	 * @param posY  position of the top left corner of the button.
	 */
	@Override
	public void setPosY(int posY) 
	{
		super.setPosY(posY);
		text.setPosY(posY + height/3*2);
	}
	/**
	 * Returns the Text object used by this button.
	 * @return Text object rendered over this button.
	 */
	public Text getText()
	{
		return text;
	}
	Text text;
}
