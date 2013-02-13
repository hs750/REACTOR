package view;

public class Timer 
{
	public void stamp()
	{
		previous = latest;
		latest = System.currentTimeMillis();
	}
	public long getDelta()
	{
		return latest - previous;
	}
	
	long previous;
	long latest;
}
