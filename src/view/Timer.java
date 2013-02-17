package view;
/**
 * Object responsible for tracking time that passes between the loop cycles.
 * Uses milliseconds.
 * @author Tadas
 *
 */
public class Timer 
{
	/**
	 * Stores the current system time. 
	 * If you want to measure how long a certain operation takes calls this function before the operation and then immediately after it.
	 */
	public void stamp()
	{
		previous = latest;
		latest = System.currentTimeMillis();
	}
	/**
	 * Returns the time that has passed between two stamp() calls in milliseconds.
	 * @return
	 */
	public long getDelta()
	{
		return latest - previous;
	}
	
	long previous;
	long latest;
}
