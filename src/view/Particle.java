package view;

public class Particle 
{
	public void integrate(long deltaTime)
	{
		++age;
		vx += deltaTime * ax;
		x += deltaTime * vx;
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
	
	public float alpha;
}
