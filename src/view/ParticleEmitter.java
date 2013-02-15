package view;
import java.util.Random;
import java.util.Date;
public class ParticleEmitter 
{
	
	public void update(long deltaTime)
	{
		timeElapsed += deltaTime;
		while(timeElapsed >= spawnTimer)
		{
			timeElapsed -= spawnTimer;
			addParticle();
		}
		for(int i = 0; i < aliveParticles && i != maxParticles; ++i)
		{
			if(particles[i].age >= maxAge)
				killParticle(i);
			particles[i].integrate(deltaTime);
			applyParticleModifications(particles[i]);
		}
	}
	
	public void applyParticleModifications(Particle p)
	{
		p.alpha = (float)p.age /  (maxAge * 5);
	}
	
	public ParticleEmitter(String imageName, int maxAge,
			long spawnTimer, int maxParticles, int x,
			int y, int startVelocityX, int startVelocityY,
			int startAccelerationX, int startAccelerationY, int variation) 
	{
		this.renderable = new Renderable(imageName, 10, 10, 0, 0, 0);
		this.maxAge = maxAge;
		this.spawnTimer = spawnTimer;
		this.maxParticles = maxParticles;
		this.aliveParticles = 0;
		this.x = x;
		this.y = y;
		this.startVelocityX = startVelocityX;
		this.startVelocityY = startVelocityY;
		this.startAccelerationX = startAccelerationX;
		this.startAccelerationY = startAccelerationY;
		this.variation = variation;
		timeElapsed = 0;
		particles = new Particle[maxParticles];
		random = new Random(new Date().getTime());
		active = true;
	}

	void killParticle(int index)
	{
		--aliveParticles;
		particles[index] = particles[aliveParticles];
		
	}
	void addParticle()
	{
		if(aliveParticles + 1 <= maxParticles) 
		{
			Particle p = new Particle();
			p.age = 0;
			p.x = x;
			p.y = y;
			p.vx = startVelocityX + random.nextInt(variation) - variation / 2;
			p.vy = startVelocityY + random.nextInt(variation) - variation / 2;
			p.ax = startAccelerationX;
			p.ay = startAccelerationY;
			p.alpha = 0.5f;
			particles[aliveParticles] = p;
			
			++aliveParticles;
		}
	}
	public Renderable getRenderable()
	{
		return renderable;
	}
	public void enable()
	{
		active = true;
	}
	
	public void disable()
	{
		active = false;
	}
	
	public boolean getActive()
	{
		return active;
	}
	public Particle [] getParticles() 
	{
		return particles;
	}
	public int getAlive() 
	{
		return aliveParticles;
	}
	
	Renderable renderable;
	Particle [] particles;
	
	int maxAge;
	long spawnTimer;
	
	int maxParticles;
	int aliveParticles;
	
	int x;
	int y;
	
	int startVelocityX;
	int startVelocityY;
	
	int startAccelerationX;
	int startAccelerationY;
	int variation;
	
	boolean active;
	Random random;
	
	long timeElapsed;

	

	
}
