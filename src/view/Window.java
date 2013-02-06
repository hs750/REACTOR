package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Canvas;
import java.awt.BorderLayout;

public class Window {

	private JFrame frame;
	private Canvas canvas;

	/**
	 * Launch the application.
	 */
	
	
	/*
	public static void main(String[] args) {
		
		Renderer r = new Renderer();
		
		GUIThread g = new GUIThread(r);
		g.run();
		Renderable rd = new Renderable("homer.gif", 100, 100, 200, 100, 0);
		Renderable rds = new Renderable("strawberry.png", 200, 150, 100, 100, 1);
		rd.setSampleCoordinates(0.5f, 0.5f, 0.5f, 0.5f);
		rds.setAlpha(0.4f);
		AnimatedRenderable a = new AnimatedRenderable("strawberry.png", 300, 400, 100, 100, 5, 2, 0);
		Text t = new Text("COCk munching\n heh", 400, 300, 25);
		Timer time = new Timer();
		time.stamp();
		long elapsed = 0;
		int x = 200;
		Button b = new Button("homer.gif", 200, 200, 100, 50);
		r.addMouseListener(b);
		r.addMouseMotionListener(b);
		while(true)
		{
			time.stamp();
			elapsed += time.getDelta();
			if(elapsed >= 34)
			{
				elapsed -= 34;
				rds.setPositionX(++x);
				r.queueForRendering(rd);
				r.queueForRendering(rds);
				r.queueForRendering(t);
				r.queueForRendering(a);
				r.queueForRendering(b.getRenderable());
				r.repaint();
			}
		}
	}
	 */
	/**
	 * Create the application.
	 */
	public Window(Canvas newCanvas) 
	{
		canvas = newCanvas;
		initialize();
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() 
	{
		frame = new JFrame();
		frame.setBounds(100, 100, 800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(canvas, null);
	}

}
