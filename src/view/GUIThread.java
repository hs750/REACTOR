package view;

public class GUIThread implements Runnable
{
	
	GUIThread(Renderer r)
	{
		this.r = r;
	}
	Renderer r;
	@Override
	public void run() {
		Window window = new Window(r);
		//window.frame.setVisible(true);
		try {
			r.initialize();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
