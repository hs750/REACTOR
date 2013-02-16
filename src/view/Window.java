package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Canvas;
import java.awt.BorderLayout;
import java.awt.Dimension;

public class Window {

	public JFrame frame;
	private Canvas canvas;

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
		frame.setResizable(false);
		frame.setBounds(100, 100, 1276, 683);
		frame.setTitle("REACTOR++ by Team Anchovy");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(canvas, null);
	}

}
