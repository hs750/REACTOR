package view;

import java.awt.FontMetrics;
import java.awt.AlphaComposite;
import java.awt.Canvas;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;
/**
 * 
 * @author Tadas
 *
 */

/**
 * Responsible for rendering images, text and particle emitters to a canvas. User has to queue objects he wants to render every frame and call the repaint method afterwards. 
 * @author Tadas
 *
 */
@SuppressWarnings("serial")
public class Renderer extends Canvas 
{
	/**
	 * Basic constructor.
	 */
	public Renderer()
	{
		
		renderables = new ArrayList<Renderable>();
		displayedTexts = new ArrayList<Text>();
		emitters = new ArrayList<ParticleEmitter>();
	}
	/**
	 * Initializes the renderer. Has to be called before renderer can be used. Has to be called after the window is visible,
	 * which is why this method is not part of the constructor.
	 */
	public void initialize()
	{
		createBufferStrategy(2);
		strategy = getBufferStrategy();
	}
	/**
	 * This has to be called every frame to make the window update. Uses double buffering internally.
	 */
	@Override 
	synchronized public void repaint()
	{
		Graphics graphics = (Graphics2D) strategy.getDrawGraphics();
		update(graphics);
		graphics.dispose();
		strategy.show();
		
	}
	/**
	 * Adds the object to the rendering queue to be rendered to screen on the next repaint call
	 * @param r
	 */
	synchronized public void queueForRendering(Renderable r)
	{
		if(r.getVisibility())
			renderables.add(r);
	}
	/**
	 * Adds the object to the rendering queue to be rendered to screen on the next repaint call
	 * @param t
	 */
	synchronized public void queueForRendering(Text t)
	{
		if(t.getVisibility())
			displayedTexts.add(t);
	}
	/**
	 * Adds the object to the rendering queue to be rendered to screen on the next repaint call
	 * @param b
	 */
	synchronized public void queueForRendering(TextButton b)
	{
		queueForRendering(b.getRenderable());
		queueForRendering(b.getText());
	}
	/**
	 * Adds the object to the rendering queue to be rendered to screen on the next repaint call
	 * @param b
	 */
	synchronized public void queueForRendering(Button b)
	{
		queueForRendering(b.getRenderable());
	}
	/**
	 * Adds the object to the rendering queue to be rendered to screen on the next repaint call
	 * @param e
	 */
	synchronized public void queueForRendering(ParticleEmitter e)
	{
		if(e.getActive())
			emitters.add(e);
	}
	/**
	 * Register a button so that it would receive messages from the canvas which this renderer renders to.
	 * @param b Button
	 */
	synchronized public void registerButton(Button b)
	{
		addMouseListener(b);
		addMouseMotionListener(b);

	}
	/**
	 * Renders all the queued objects every frame.
	 * Renderables are sorted based on their depth levels and then rendered.
	 * Text objects are rendered on top and finally the particle emitters get rendered.
	 */
	@Override
	synchronized public void paint(Graphics g)  
	{
		Graphics2D graphics = (Graphics2D) g;
		super.paint(graphics);
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
		
		Collections.sort(renderables, new RenderableComparator());
		for(Renderable r: renderables)
		{
			graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, r.getAlpha()));
			graphics.drawImage(r.getImage(), r.getPositionX(), r.getPositionY(), r.getPositionX() + r.getWidth(), r.getPositionY() + r.getHeight(),
					r.getSourceStartHorizontal(),r.getSourceStartVertical(), 
					r.getSourceStartHorizontal() + r.getSourceSizeHorizontal(), r.getSourceStartVertical() + r.getSourceSizeVertical(),null);
		}
		for(ParticleEmitter e: emitters)
		{
			for(int i = 0; i != e.getAlive(); ++i)
			{
				graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, e.getParticles()[i].alpha));
				graphics.drawImage(e.getRenderable().getImage(), null, e.getParticles()[i].x, e.getParticles()[i].y);
			}
		}
		for(Text t: displayedTexts)
		{
			String fontName; 
			if(t.getFontName() == null)
				fontName = defaultFontName;
			else fontName = t.getFontName();
	
			graphics.setFont(new Font(fontName, t.getStyle(), t.getSize()));
			graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, t.getAlpha()));
			graphics.setColor(t.getColor());
			graphics.drawString(t.getString(), t.getPosX(), t.getPosY());
		}
		renderables = new ArrayList<Renderable>();
		displayedTexts = new ArrayList<Text>();
		emitters = new ArrayList<ParticleEmitter>();
	}
	/**
	 * Returns the font metric of the supplied text object. Used for alligning the text objects.
	 * @param t Text object
	 * @return
	 */
	public FontMetrics getMetrics(Text t)
	{
		Graphics2D graphics = (Graphics2D) strategy.getDrawGraphics();
		Font f = new Font(t.getFontName(), t.getStyle(), t.getSize());
		FontMetrics m = graphics.getFontMetrics(f);
		graphics.dispose();
		return m;
	}
	/**
	 * Sets a default font name to be used on fonts that don't have any other font name specified.
	 * @param name Default font name
	 */
	public void setDefaultFontName(String name)
	{
		defaultFontName = name;
	}
	/**
	 * Comparator for sorting/comparing renderables based on their depth levels.
	 * @author Tadas
	 *
	 */
	class RenderableComparator implements Comparator<Renderable> {
	    @Override
	    public int compare(Renderable o1, Renderable o2) 
	    {
	        return o1.getDepthLevel() - o2.getDepthLevel();
	    }
	}
	String defaultFontName;
	BufferStrategy strategy;
	ArrayList<Renderable> renderables;
	ArrayList<Text> displayedTexts;
	ArrayList<ParticleEmitter> emitters;
}
