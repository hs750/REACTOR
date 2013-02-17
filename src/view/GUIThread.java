package view;
/**
 * Class that interacts with the OS messaging service in a different thread. 
 * @author Tadas
 *
 */
public class GUIThread implements Runnable
{
	/**
	 * Basic constructor
	 * @param r renderer will get initialized after the run() call
	 */
	GUIThread(Renderer r)
	{
		this.r = r;
	}
	Renderer r;
	public Window window;
	/**
	 * Starts the messaging service and displays a window with a canvas.
	 * Also initiliazes renderer so that it could be used properly.
	 */
	@Override
	public void run() {
		window = new Window(r);
		
		try {
			r.initialize();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
