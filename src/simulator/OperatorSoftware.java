package simulator;

import java.util.Date;
import java.util.List;
import java.util.Random;

import pcomponents.Pump;
import pcomponents.Valve;

import model.HighScore;

public class OperatorSoftware {

	private PlantController controller;
	private UIData uidata;
	private boolean OSFailed;
	private static double RANDOM_OPERATOR_SOFTWARE_FAILURE_CHANCE = 1; //10% chance of failure
	
	//Random Generator used for creating random numbers when the Operator Software has failed.
	Date time;
	Random rand;
	
	public OperatorSoftware(PlantController controller){
		this.controller = controller;
		this.uidata = controller.getUIData();
		this.OSFailed = false;
		
		this.time = new Date();
		this.rand = new Random(time.getTime());
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
		Double randomNumber = randDoubleBetween0and100();
		if(randomNumber < RANDOM_OPERATOR_SOFTWARE_FAILURE_CHANCE)
			OSFailed = true;
	}
	/**
	 * Repairs the Operator Software or "Reboots" it - sets OSFailed to false.
	 */
	public void rebootOS(){
		OSFailed = false;
	}
	public boolean isOSFailed() {return OSFailed;}
	public void setOSFailed(boolean OSFailed) {this.OSFailed = OSFailed;}
	
	/* ------------------- Control Software Pass-though Methods  ------------------------
	 * These methods are intended to be called by the GUI.
	 * If the Operator Software is functioning they will pass though the given argument(s) normally
	 * If however the operator software has 'Failed' then the argument that is passed though will be 'Random'
	 */
	
	/**
	 * Advance the game by a number of time steps.
	 * If the game reaches a game over state before all steps are executed,
	 * the game stops stepping.
	 * @param numSteps number of timesteps to advance the game by.
	 */
	public void step(int numSteps) {
		calculateOSFailed();
		controller.step(numSteps);
	}
	
	/**
	 * Creates a new Game - used in UI to start a new game.
	 * @param operatorName the name of the player
	 */
	public void newGame(String operatorName) {
		controller.newGame(operatorName);
		uidata = controller.getUIData();
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
		boolean loadSuccess = controller.loadGame();
		if(loadSuccess)
			uidata = controller.getUIData();
		return loadSuccess;
	}
	
	/**
	 * Pauses/resumes the game on call.
	 * Currently not in used as the game is turn based. Gives the possibility
	 * to easily create a real-time game.
	 */
	public void togglePaused() {
		//TODO Implement if you want to pause (only applicable if using a continuous game loop)
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
	
	public String getOperatorName() {
		return uidata.getOperatorName();
	}
	public int getScore() {
		return uidata.getScore();
	}
	
	public boolean isGameOver() {
		return uidata.isGameOver();
	}
	
	/*
	 * ----------------- Methods affected by the Operator Software Failing (Setters)-----------------
	 */
	/**
	 * Returns true if command was successful, false if a valve with that ID was not found
	 * @return true if command was successful, false if a valve with that ID was not found
	 */
	public boolean setValve(int valveID, boolean open) {
		if(OSFailed)
			return setRandomComponent();
		return controller.setValve(valveID, open);
	}
	
	/**
	 * Returns true if command was successful, false if a pump with that ID was not found
	 * @return true if command was successful, false if a pump with that ID was not found
	 */
	public boolean setPumpOnOff(int pumpID, boolean on) {
		if(OSFailed)
			return setRandomComponent();
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
		if(OSFailed)
			return setRandomComponent();
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
		if(OSFailed)
			setRandomComponent();
		else controller.setControlRods(percentageLowered);
	}
	/**
	 * Sets a random component to a random value. Used when setting component values while the Operator Software is not functional.
	 * @return true if setting component was successful, otherwise false.
	 */
	private boolean setRandomComponent(){
		int componentType = rand.nextInt(3); // Which component to sent random value to: 0 = control rods, 1 = pumps, 2 = valves
		switch(componentType){
		case 0:
			controller.setControlRods(randIntBetween0and100()); 												// set control rods to random level
			return true;
		case 1:
			boolean attribute  = randBoolean();
			if(attribute)
				return controller.setPumpOnOff(1 + rand.nextInt(getPumpRpms().length), randBoolean());			//Set pumps randomly to on or off
			return controller.setPumpRpm(1 + rand.nextInt(getPumpRpms().length), randIntBetween0and100() * 10); //Set pumps to random RPM
		case 2:
			return controller.setValve(1+ rand.nextInt(getValvePositions().length), randBoolean());			//Set valve to random position
		default:
			return false;
		}
	}
	
	/*
	 * --------------- Methods affected by the Operator Software Failing (Getters)-----------------
	 */
	
	public int getReactorHealth() {
		uidata.updateUIData();
		if(OSFailed)
			return randIntBetween0and100();
		return uidata.getReactorHealth();
	}
	public int getReactorTemperature() {
		uidata.updateUIData();
		if(OSFailed)
			return randIntBetween0and100();
		return uidata.getReactorTemperature(); 
	}
	public int getReactorPressure() {
		uidata.updateUIData();
		if(OSFailed)
			return randIntBetween0and100();
		return uidata.getReactorPressure();
	}
	public int getReactorWaterVolume() {
		uidata.updateUIData();
		if(OSFailed)
			return randIntBetween0and100();
		return uidata.getReactorWaterVolume();
	}
	public int getCondenserHealth() {
		uidata.updateUIData();
		if(OSFailed)
			return randIntBetween0and100();
		return uidata.getCondenserHealth();
	}
	public int getCondenserTemperature() {
		uidata.updateUIData();
		if(OSFailed)
			return randIntBetween0and100();
		return uidata.getCondenserTemperature();
	}
	public int getCondenserPressure() {
		uidata.updateUIData();
		if(OSFailed)
			return randIntBetween0and100();
		return uidata.getCondenserPressure();
	}
	public int getCondenserWaterVolume() {
		uidata.updateUIData();
		if(OSFailed)
			return randIntBetween0and100();
		return uidata.getCondenserWaterVolume();
	}
	public int getControlRodsPercentage() {
		uidata.updateUIData();
		if(OSFailed)
			return randIntBetween0and100();
		return uidata.getControlRodsPercentage();
	}
	
	/**
	 * Gets the position of all of the valves within the power plant. 
	 * The index of the array indicates the ID-1 of the valve.
	 * @return Valve positions.
	 */
	public boolean[] getValvePositions() {
		uidata.updateUIData();
		List<Valve> valves = uidata.getValves();
		boolean[] positions = new boolean[valves.size()];
		int i = 0;
		for(Valve valve : valves){
			if(OSFailed){	
				positions[i] = randBoolean();
			}else{
				positions[i] = valve.isOpen();
				}
			i++;
		}
		return positions;
	}
	/**
	 * Gets the RPMs of all the pumps in the power plant.
	 * The index of the array indicates the ID-1 of the pump.
	 * @return Pump RPMs
	 */
	public int[] getPumpRpms() {
		uidata.updateUIData();
		List<Pump> pumps = uidata.getPumps();
		int[] Rpms = new int[pumps.size()];
		int i = 0;
		for(Pump pump : pumps){
			if(OSFailed){
				Rpms[i] = randIntBetween0and100() * 10; // RPM is between 0 and 1000
			}else{
				Rpms[i] = pump.getRpm();
			}
			i++;
		}
		return Rpms;
	}
	/**
	 * Gets the functionality of all the pumps in the power plant. true = operational. 
	 * The index of the array indicates the ID-1 of the pump.
	 * @return Pump functionalities
	 */
	public boolean[] arePumpsFunctional(){
		uidata.updateUIData();
		List<Pump> pumps = uidata.getPumps();
		boolean[] functional = new boolean[pumps.size()];
		int i = 0;
		for(Pump pump : pumps){
			if(OSFailed){
				functional[i] = randBoolean();
			}else{
				functional[i] = pump.isOperational();
			}
			i++;
		}
		return functional;
	}
	/**
	 * Gets whether pumps are on or off for all the pumps in the power plant. true = on.
	 * The index of the array indicates the ID-1 of the pump.
	 * @return Pump on/off
	 */
	public boolean[] arePumpsOn(){
		uidata.updateUIData();
		List<Pump> pumps = uidata.getPumps();
		boolean[] on = new boolean[pumps.size()];
		int i = 0;
		for(Pump pump : pumps){
			if(OSFailed){
				on[i] = randBoolean();
			}else{
				on[i] = pump.isOn();
			}
			i++;
		}
		return on;
	}
	
	public int getTurbineRpm() {
		uidata.updateUIData();
		if(OSFailed)
			return randIntBetween0and100();
		return uidata.getTurbineRpm();
	}
	public int getPowerOutput() {
		uidata.updateUIData();
		if(OSFailed)
			return randIntBetween0and100();
		return uidata.getPowerOutput();
	}
	public boolean isTurbineFunctional() {
		uidata.updateUIData();
		if(OSFailed)
			return randBoolean();
		return uidata.isTurbineFunctional();
	}

	/*
	 * ----------------------------- Repair Components ----------------------
	 * Excludes Operator software. See above section for methods about OS.
	 */
	/**
	 * Start the repair of the turbine if it has failed.
	 * @return true only if the turbine has failed and is not already being repaired.
	 */
	public boolean repairTurbine() {
		return controller.repairTurbine();
	}
	
	/**
	 * Start the repair of a particular pump.
	 * @param  pumpID the internal ID of the pump to be repaired.
	 * @return true only if the pump is found, has failed and is not already being repaired.
	 */
	public boolean repairPump(int pumpID) {
		return controller.repairPump(pumpID);
	}
	/*
	 * --------------- Max and Min values for Reactor and Condenser ------------
	 */
	public int getCondenserMaxPressure() {
		return uidata.getCondenserMaxPressure();
	}
	public int getReactorMaxTemperature() {
		return uidata.getReactorMaxTemperature();
	}
	public int getReactorMaxPressure() {
		return uidata.getReactorMaxPressure();
	}
	public int getCondenserMaxTemperature() {
		return uidata.getCondenserMaxTemperature();
	}
	public int getReactorMinSafeWaterVolume() {
		return uidata.getReactorMinSafeWaterVolume();
	}
	/*
	 * ----------------- Utility Functions -----------------------------------
	 */
	/**
	 * Generates a random double between 0 and 100
	 * @return The andom Number
	 */
	private double randDoubleBetween0and100(){
		return rand.nextDouble() * 100; 
	}
	/**
	 * Generates a random integer between 0 and 100
	 * @return The random number
	 */
	private int randIntBetween0and100(){
		return rand.nextInt(101);	
	}
	/**
	 * Generates a random boolean value
	 * @return The random value
	 */
	private boolean randBoolean(){
		return rand.nextBoolean();
	}
}
