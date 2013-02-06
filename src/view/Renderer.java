package view;

import java.awt.FontMetrics;
import java.awt.AlphaComposite;
import java.awt.Canvas;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;
/**
 * 
 * @author Tadas
 *
 */
@SuppressWarnings("serial")
public class Renderer extends Canvas 
{
	public Renderer()
	{
		
		renderables = new ArrayList<Renderable>();
		displayedTexts = new ArrayList<Text>();
	}
	public void initialize()
	{
		createBufferStrategy(2);
		strategy = getBufferStrategy();
	}
	@Override 
	synchronized public void repaint()
	{
		Graphics graphics = (Graphics2D) strategy.getDrawGraphics();
		update(graphics);
		graphics.dispose();
		strategy.show();
		
	}
	synchronized public void queueForRendering(Renderable r)
	{
		if(r.getVisibility())
			renderables.add(r);
	}
	
	synchronized public void queueForRendering(Text t)
	{
		displayedTexts.add(t);
	}
	
	synchronized public void registerButton(Button b)
	{
		addMouseListener(b);
		addMouseMotionListener(b);

	}
	@Override
	synchronized public void paint(Graphics g)  
	{
		Graphics2D graphics = (Graphics2D) g;
		super.paint(graphics);
		
		//graphics.drawRect(100, 100, 100 ,100);
		Collections.sort(renderables, new RenderableComparator());
		for(Renderable r: renderables)
		{
			graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, r.getAlpha()));
			graphics.drawImage(r.getImage(), r.getPositionX(), r.getPositionY(), r.getPositionX() + r.getWidth(), r.getPositionY() + r.getHeight(),
					r.getSourceStartHorizontal(),r.getSourceStartVertical(), 
					r.getSourceStartHorizontal() + r.getSourceSizeHorizontal(), r.getSourceStartVertical() + r.getSourceSizeVertical(),null);
		}
		for(Text t: displayedTexts)
		{
			graphics.setFont(new Font(null, Font.PLAIN, t.getSize()));
			graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, t.getAlpha()));
			graphics.setColor(t.getColor());
			graphics.drawString(t.getString(), t.getPosX(), t.getPosY());
		}
		renderables = new ArrayList<Renderable>();
		displayedTexts = new ArrayList<Text>();
	}
	
	public FontMetrics getMetrics(Text t)
	{
		Graphics2D graphics = (Graphics2D) strategy.getDrawGraphics();
		Font f = new Font(null, Font.PLAIN, t.getSize());
		FontMetrics m = graphics.getFontMetrics(f);
		graphics.dispose();
		return m;
	}
	
	class RenderableComparator implements Comparator<Renderable> {
	    @Override
	    public int compare(Renderable o1, Renderable o2) 
	    {
	        return o1.getDepthLevel() - o2.getDepthLevel();
	    }
	}
	BufferStrategy strategy;
	ArrayList<Renderable> renderables;
	ArrayList<Text> displayedTexts;
}
