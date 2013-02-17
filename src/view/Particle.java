package view;
/**
 * A simple class that stores all the information about a particle.
 * @author Tadas
 *
 */
public class Particle 
{
	/**
	 * Applies simple newtonian physics to the particle and ages it.
	 * @param deltaTime time that has passed since the last frame.
	 */
	public void integrate(long deltaTime)
	{
		++age;
		vx +=  ax;
		vy += ay;
		x +=  vx;
		y += vy;
				
	}
	//Position
	public int x;
	int y;
	//Velocity
	public int vx;
	int vy;
	//Acceleration
	public int ax;
	public int ay;
	
	public int age;
	//transparency
	public float alpha;
}
