package view;
import java.util.Random;
import java.util.Date;
/**
 * Object that creates and tracks particles for smoke/fog/spark effects in the game. Needs to be queued for rendering every frame to get displayed.
 * Should call update() method every frame to animate the particles.
 * @author Tadas
 *
 */
public class ParticleEmitter 
{
	/**
	 * Updates the particles using basic newtonian physics. Kills the dead particles.
	 * @param deltaTime time that has passed between the frames.
	 */
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
	/**
	 * Makes the particle more transparent based on its age. Could be overloaded to achieve variety of effects.
	 * @param p particle that's to be modified
	 */
	public void applyParticleModifications(Particle p)
	{
		p.alpha = (float)p.age /  (maxAge * 5);
	}
	/**
	 * Initializes a new particle emitter.
	 * @param imageName Name of the image that will be used by every particle.
	 * @param maxAge Maximum age of a particle
	 * @param spawnTimer How much time should pass between creation of new particles
	 * @param maxParticles Maximum number of particles
	 * @param x initial particle position
	 * @param y initial particle position
	 * @param startVelocityX initial particle velocity
	 * @param startVelocityY initial particle velocity
	 * @param startAccelerationX initial particle acceleration
	 * @param startAccelerationY initial particle acceleration
	 * @param variation Used to introduce randomness/variation in particle movement speeds.
	 */
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
	/**
	 * Moves a dead particle to the end of the alive particles list. Used internally.
	 * @param index Index of the particle that needs to be killed.
	 */
	void killParticle(int index)
	{
		--aliveParticles;
		particles[index] = particles[aliveParticles];
		
	}
	/**
	 * Spawns a new particle. Used internally
	 */
	void addParticle()
	{
		if(aliveParticles + 1 <= maxParticles) 
		{
			Particle p = new Particle();
			p.age = 0;
			if(variedXSpawn >= 1)
				p.x = x + random.nextInt(variedXSpawn) - variedXSpawn / 2;
			else p.x = x;
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
	/**
	 * Renderable that gets used by every single particle. Most of the values stored in this object are ignored by the renderer,
	 * therefore it should only be used internally
	 * 
	 * @return renderable used by all the particles.
	 */
	public Renderable getRenderable()
	{
		return renderable;
	}
	/**
	 * Enable emitter so that it would render/update its particles every frame.
	 */
	public void enable()
	{
		active = true;
	}
	/**
	 * Prevent system from rendering/updating every frame.
	 */
	public void disable()
	{
		active = false;
	}
	/**
	 * Is the system being updated/renderer every frame.
	 * @return If true then the emitter is active
	 */
	public boolean getActive()
	{
		return active;
	}
	/**
	 * Returns the array that has info about all the particles. Ideally only used by the renderer.
	 * @return All the particles (possibly dead particles too) stored by the system.
	 */
	public Particle [] getParticles() 
	{
		return particles;
	}
	/**
	 * Returns the number of active particles
	 * @return number of particle that have not reached the max age yet.
	 */
	public int getAlive() 
	{
		return aliveParticles;
	}
	
	
	/**
	 * Returns the horizontal particle spawning variation
	 * @return Horizontal particle spawning variation
	 */
	public int getVariedXSpawn() 
	{
		return variedXSpawn;
	}
	/**
	 * Sets the horizontal particle spawning variation
	 * @param variedXSpawn Horizontal particle spawning variation
	 */
	public void setVariedXSpawn(int variedXSpawn) 
	{
		this.variedXSpawn = variedXSpawn;
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
	
	int variedXSpawn;
	
	boolean active;
	Random random;
	
	long timeElapsed;

	

	
}
