package view;

public class GUIThread implements Runnable
{
	
	GUIThread(Renderer r)
	{
		this.r = r;
	}
	Renderer r;
	public Window window;
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
