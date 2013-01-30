package simulator;

import java.util.Date;
import java.util.List;
import java.util.Random;

import model.HighScore;

public class OperatorSoftware {

	private PlantController controller;
	protected UIData uidata;
	private boolean OSFailed;
	private static double RANDOM_OPERATOR_SOFTWARE_FAILURE_CHANCE = 10; //10% chance of failure
	
	public OperatorSoftware(PlantController controller){
		this.controller = controller;
		this.uidata = controller.getUIData();
		this.OSFailed = false;
	}
	
	/*
	 * ---------------- Methods about the operator software ------------------------
	 */
	/**
	 * Determine whether the Operator Software has failed.
	 * Calculate a random number between 0 and 100 (percent) 
	 * If the random number generator is less that the failure chance constant, set the OS to failed.
	 */
	public void calculateOSFailed(){
		Date time = new Date();
		Random rand = new Random(time.getTime());
		Double randomNumber = rand.nextDouble() * 100; //Make it a percentage
		if(randomNumber < RANDOM_OPERATOR_SOFTWARE_FAILURE_CHANCE)
			OSFailed = true;
	}
	
	public void rebootOS(){
		OSFailed = false;
	}
	
	
	/* ------------------- Control Software Pass-though Methods ------------------------
	 * These methods are intended to be called by the GUI.
	 * If the Operator Software is functioning they will pass though the given argument(s) normally
	 * If however the operator software has 'Failed' then the argument that is passed though will be 'Random'
	 */
	
	/**
	 * Creates a new Game - used in UI to start a new game.
	 * @param operatorName the name of the player
	 */
	public void newGame(String operatorName) {
		controller.newGame(operatorName);
	}
	
	/**
	 * Saves the state of the current game (plant) into a file called "save.ser" inside the current folder.
	 * @return true if saving a game was successful, false otherwise.
	 */
	public boolean saveGame(){
		return controller.saveGame();
	}
	
	/**
	 * Loads the state of the current game (plant) from a file called "save.ser" inside the current folder.
	 * If the file does not exist, the load will not be successful (nothing will happen).
	 * @return true if loading a game was successful, false otherwise.
	 */
	public boolean loadGame() {
		return controller.loadGame();
	}
	
	/**
	 * Pauses/resumes the game on call.
	 * Currently not in used as the game is turn based. Gives the possibility
	 * to easily create a real-time game.
	 */
	public void togglePaused() {
		// TODO passthrough command to controller
	}
	
	/**
	 * Returns the highscores list.
	 * @return list of highscores.
	 */
	public List<HighScore> getHighScores() {
		return controller.getHighScores();
	}
	
	/**
	 * Adds a new score to high scores if the new score is in the top 10 of all scores.
	 * @return true if adding was successful and the new score is in top 10, false otherwise.
	 */
	public boolean addHighScore(HighScore newHighScore) {
		return controller.addHighScore(newHighScore);
	}
	
	/**
	 * Returns true if command was successful, false if a valve with that ID was not found
	 * @return true if command was successful, false if a valve with that ID was not found
	 */
	public boolean setValve(int valveID, boolean open) {
		// TODO Implement failure
		return controller.setValve(valveID, open);
	}
	
	/**
	 * Returns true if command was successful, false if a pump with that ID was not found
	 * @return true if command was successful, false if a pump with that ID was not found
	 */
	public boolean setPumpOnOff(int pumpID, boolean on) {
		// TODO Implement failure
		return controller.setPumpOnOff(pumpID, on);
	}
	
	/**
	 * Sets the RPM of a particular pump.
	 * @param  pumpID the internal ID of the pump.
	 * @param  rpm the new value of the RPM, needs to be in range (0 to MAX_RPM).
	 * @return true if setting the RPM was successful, false otherwise.
	 * @throws IllegalArgumentException if RPM is out of the allowed range (rpm < 0 || rpm > MAX_RPM).
	 */
	public boolean setPumpRpm(int pumpID, int rpm) throws IllegalArgumentException {
		// TODO Implement failure
		return controller.setPumpRpm(pumpID, rpm);
	}
	
	/**
	 * Sets a new percentage for the control rods.
	 * Setting the percentage to 100 means that the control rods are fully inside
	 * the reactor and the reaction stops. Setting the percentage to 0 means that
	 * the control rods are outside of the reactor.
	 * @param percentageLowered the new value of percentageLowered
	 */
	public void setControlRods(int percentageLowered) {
		// TODO Implement failure
		controller.setControlRods(percentageLowered);
	}
	
	/**
	 * Start the repair of the turbine if it has failed.
	 * @return true only if the turbine has failed and is not already being repaired.
	 */
	public boolean repairTurbine() {
		// TODO Implement some sort of check perhaps
		return controller.repairTurbine();
	}
	
	/**
	 * Start the repair of a particular pump.
	 * @param  pumpID the internal ID of the pump to be repaired.
	 * @return true only if the pump is found, has failed and is not already being repaired.
	 */
	public boolean repairPump(int pumpID) {
		// TODO Implement some sort of check
		return controller.repairPump(pumpID);
	}
	
	/**
	 * Advance the game by a number of time steps.
	 * If the game reaches a game over state before all steps are executed,
	 * the game stops stepping.
	 * @param numSteps number of timesteps to advance the game by.
	 */
	public void step(int numSteps) {
		// TODO may want to do something other than just pass through
		calculateOSFailed();
		controller.step(numSteps);
	}
	
	
	
	
	
	 
}
