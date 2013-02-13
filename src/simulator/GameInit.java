package simulator;

import javax.swing.text.View;

import view.GUI;

/**
 * GameInit class bootstraps the entire game.
 * 
 * It first instantiates a ReactorUtils object that can create 
 * new plant objects.
 * The controller takes a reference to the ReactorUtils object 
 * so that it can get a new Plant if and when they're needed.
 * It also instantiates the UI and gives it a reference to
 * the controller for routing user commands. 
 *
 */
public class GameInit {
	
	private GUI view;
	private PlantController controller;
	private ReactorUtils utils;
	
	public GameInit() {
		utils = new ReactorUtils();
		controller = new PlantController(utils);
		view = new GUI(controller);
		view.run();
	}
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		GameInit game = new GameInit();
	}
	
}