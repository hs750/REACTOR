package view;

public class AnimatedRenderable extends Renderable
{

	public AnimatedRenderable(String ImageName, int PosX, int PosY, int Width,
			int Height, int DepthLevel, int numberOfFrames, int currentFrame) {
		super(ImageName, PosX, PosY, Width, Height, DepthLevel);
		this.numberOfFrames = numberOfFrames;
		frameSize = 1.0f / numberOfFrames;
		setCurrentFrame(currentFrame);
		
	}
	
	public void setCurrentFrame(int n)
	{
		if(n >= numberOfFrames)
			currentFrame = 0;
		else currentFrame = n;
		
		setSampleCoordinates(frameSize * currentFrame, frameSize, 0.0f, 1.0f);
	}
	
	public int getCurrentFrame()
	{
		return currentFrame;
	}
	
	public void animate(long d)
	{
		timePassed += d;
		if(timePassed >= frameTime)
		{
			setCurrentFrame(getCurrentFrame() + 1);
			timePassed -= frameTime;
		}
	}
	
	
	long timePassed;
	long frameTime;
	
	int numberOfFrames;
	int currentFrame;
	float frameSize;
}
