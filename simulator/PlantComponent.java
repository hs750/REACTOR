package simulator;

import java.util.Random;

abstract class PlantComponent {

	//The values of the constants below are for illustration.
	public final static int DEFAULT_FAILURE_RATE = 33;
	public final static int DEFAULT_REPAIR_TIME = 5;
	public final static boolean DEFAULT_OPERATIONAL = true;
	public final static boolean DEFAULT_PRESSURISED = false;

	private double failureRate;
	private int repairTime;
	private boolean operational; //Possibly unnecessary
	private boolean pressurised;
	private PlantComponent input;
	private PlantComponent output;
	private Flow flowOut;
	private Random random;
	
	/**
	 * Creates a new operational PlantComponent object
	 * with the default chance to fail and turns to repair.
	 */
	protected PlantComponent() {
		this.failureRate = DEFAULT_FAILURE_RATE;
		this.repairTime = DEFAULT_REPAIR_TIME;
		this.operational = DEFAULT_OPERATIONAL;
		this.pressurised = DEFAULT_PRESSURISED;
		this.flowOut = new Flow();
		random = new Random();
	}
	
	/**
	 * Creates a new operational PlantComponent with
	 * the specified chance to fail and turns to repair.
	 * 
	 * @param failureRate the chance of a component to fail randomly
	 * @param repairTime the number of steps needed to repair the component
	 */
	protected PlantComponent(double failureRate, int repairTime) {
		this.failureRate = failureRate;
		this.repairTime = repairTime;
		this.operational = DEFAULT_OPERATIONAL;
		this.pressurised = DEFAULT_PRESSURISED;
		this.flowOut = new Flow();
		random = new Random();
	}
	
	/**
	 * Creates a new PlantComponent with
	 * the specified chance to fail, turns to repair and working condition.
	 * 
	 * @param failureRate the chance of the component to fail randomly
	 * @param repairTime the number of steps needed to repair the component
	 * @param operational determines whether the component is initially
	 * 					  operational or not
	 */
	protected PlantComponent(double failureRate, int repairTime, boolean operational) {
		this.failureRate = failureRate;
		this.repairTime = repairTime;
		this.operational = operational;
		this.pressurised = DEFAULT_PRESSURISED;
		this.flowOut = new Flow();
		random = new Random();
	}
	
	/**
	 * Creates a new PlantComponent with
	 * the specified chance to fail, turns to repair and working condition.
	 * 
	 * @param failureRate the chance of the component to fail randomly
	 * @param repairTime the number of steps needed to repair the component
	 * @param operational determines whether the component is initially
	 * 					  operational or not
	 * @param pressurised determines whether or not the component is pressurised.
	 */
	protected PlantComponent(double failureRate, int repairTime, boolean operational, boolean pressurised) {
		this.failureRate = failureRate;
		this.repairTime = repairTime;
		this.operational = operational;
		this.pressurised = pressurised;
		this.flowOut = new Flow();
		random = new Random();
	}
	
	/**
	 * Creates a new PlantComponent with
	 * the specified chance to fail, turns to repair and working condition.
	 * 
	 * @param failureRate the chance of the component to fail randomly
	 * @param repairTime the number of steps needed to repair the component
	 * @param operational determines whether the component is initially
	 * 					  operational or not
	 * @param pressurised determines whether or not the component is pressurised.
	 */
	protected PlantComponent(double failureRate, int repairTime, boolean operational, boolean pressurised, Flow flow) {
		this.failureRate = failureRate;
		this.repairTime = repairTime;
		this.operational = operational;
		this.pressurised = pressurised;
		this.flowOut = flow;
		random = new Random();
	}

	/**
	 * Returns the current chance of the component to fail randomly.
	 * @return the current chance of the component to fail randomly
	 */
	protected double getFailureRate() {
		return failureRate;
	}
	
	/**
	 * Sets a new value for failureRate.
	 * @param failureRate the new value for failureRate
	 */
	protected void setFailureRate(double failureRate) {
		this.failureRate = failureRate;
	}
	
	/**
	 * Returns the number of turns that it takes for a component to repair.
	 * @return the number of turns that it takes for a component to repair
	 */	
	protected int getRepairTime() {
		return repairTime;
	}
	
	/**
	 * Changes the number of turns it take for a component to get repaired.
	 * @param repairTime the number of turns required for this component 
	 * 					 to be repaired.
	 */
	protected void setRepairTime(int repairTime) {
		this.repairTime = repairTime;
	}
	
	/**
	 * Returns the component connected to the input of this component.
	 * @return the component connected to the input of this component.
	 */
	public PlantComponent getInput() {
		return this.input;
	}

	/**
	 * Changes the component connected to the input of this component.
	 * @param input the component connected to the input of this component.
	 */
	public void setInput(PlantComponent input) {
		this.input = input;
	}

	/**
	 * Returns the component connected to the output of this component.
	 * @return the component connected to the output of this component.
	 */
	public PlantComponent getOutput() {
		return this.output;
	}

	/**
	 * Changes the component connected to the output of this component.
	 * @param output the component connected to the output of this component.
	 */
	public void setOutput(PlantComponent output) {
		this.output = output;
	}

	/**
	 * Returns the flow object that stores the rate of flow out of this component.
	 * @return the flow object that stores the rate of flow out of this component.
	 */
	public Flow getFlowOut() {
		return this.flowOut;
	}

	/**
	 * Returns true if the component is pressurised (e.g. Reactor or Condenser)
	 * @return true if the component is pressurised
	 */
	public boolean isPressurised() {
		return this.pressurised;
	}

	/**
	 * Returns true if the component is operational (working correctly).
	 * @return true if the component is operational (working correctly)
	 */
	protected boolean isOperational() {
		return operational;
	}
	
	/**
	 * Sets the condition of the component - true if it is
	 * working correctly, false if it has failed.
	 * @param operational the new state of the component
	 */
	protected void setOperational(boolean operational) {
		this.operational = operational;
	}
	
	/**
	 * Updates the information about the component.
	 */
	abstract public void updateState();
	
	/**
	 * Runs all checks for the component and changes it's operational state if needed. 
	 */
	public boolean checkFailure() {
		int checkFailure = random.nextInt(1000);
		if(failureRate >= checkFailure) {
			return true;
		}
		else {
			return false;
		}
	}
}
