package view;

public class ParticleEmitter 
{
	public ParticleEmitter()
	{
		
	}
	public void update(long deltaTime)
	{
		
	}
	
	public Renderable getRenderable()
	{
		return renderable;
	}
	Renderable renderable;
	Particle [] particles;
	
	int maxAge;
	
	int maxParticles;
	int aliveParticles;
	
	float x;
	float y;
	
	float startVelocityX;
	float startVelocityY;
	
	float startAccelerationX;
	float startAccelerationY;
	
	
	public void applyParticleModifications(Particle p)
	{
		
	}
	
	void killParticle(int index)
	{
		
	}
	void addParticle()
	{
		if(aliveParticles < maxParticles)
		{
			
		}
	}
}
