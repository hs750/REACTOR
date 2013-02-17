package view;
/**
 * Renderable that supports animations. All the animation frames have to be stored in the same file and and alligned horizontally. All the frames have to be the same size.
 * Object has to be added to the rendering queue every frame to appear on the screen. To achieve the animation effect call the animate method every frame.
 * @author Tadas
 *
 */
public class AnimatedRenderable extends Renderable
{

	/**
	 * Constructor initializes a renderable. Some values get set to default values and need to be accessed using getters/setters.
	 * @param ImageName name of the file that stores the image. 
	 * @param PosX Horizontal coordinates of the top left corner of the image.
	 * @param PosY Vertical coordinates of the top left corner of the image.
	 * @param Width Width of the image on the screen. Image will get resized if the value supplied doesnt match the real size.
	 * @param Height Height of the image on the screen. Image will get resized if the value supplied doesnt match the real size.
	 * @param DepthLevel Also known as Z level, images get sorted based on this value in the rendering queue. Images with lower Z will get rendered first
	 * and images with higher Z will be rendered last and possibly on top of already drawn images.
	 * @param numberOfFrames number of frames that the image stores.
	 * @param currentFrame the frame that the renderable displays first. 
	 */
	public AnimatedRenderable(String ImageName, int PosX, int PosY, int Width,
			int Height, int DepthLevel, int numberOfFrames, int currentFrame) {
		super(ImageName, PosX, PosY, Width, Height, DepthLevel);
		this.numberOfFrames = numberOfFrames;
		frameSize = 1.0f / numberOfFrames;
		setCurrentFrame(currentFrame);
		
	}
	/**
	 * Flips to the frame that should be displayed. If n exceeds the maximum number of frames the first (0) frame will get displayed instead.
	 * @param n number of the frame. Frame numbering starts with 0 rather than 1
	 */
	public void setCurrentFrame(int n)
	{
		if(n >= numberOfFrames)
			currentFrame = 0;
		else currentFrame = n;
		
		setSampleCoordinates(frameSize * currentFrame, frameSize, 0.0f, 1.0f);
	}
	/**
	 * Returns the index of the frame that is currently being displayed.
	 * @return frame index
	 */
	public int getCurrentFrame()
	{
		return currentFrame;
	}
	/**
	 * Animates the renderable and should be called  every loop cycle. 
	 * @param d time that has passed since the last call
	 */
	public void animate(long d)
	{
		timePassed += d;
		if(timePassed >= frameTime)
		{
			setCurrentFrame(getCurrentFrame() + 1);
			timePassed -= frameTime;
		}
	}
	/**
	 * Sets how fast the object should flip through the frames.
	 * @param t duration of a single frame
	 */
	public void setFrameTime(long t)
	{
		frameTime = t;
	}
	/**
	 * Returns how fast the object flips through the frames
	 * @return duration of a single frame
	 */
	public long getFrameTime()
	{
		return frameTime;
	}
	long timePassed;
	long frameTime;
	
	int numberOfFrames;
	int currentFrame;
	float frameSize;
}
