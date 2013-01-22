package simulator;

import java.io.Serializable;

/**
 * Repair class keeps track of the components that are being repaired.
 * When a component's timeStepsRemaining reach 0, the component needs to be
 * removed from the list of beingRepaired inside Plant.
 */
public class Repair implements Serializable {
	private static final long serialVersionUID = 1819944421888642516L;
	
	private PlantComponent plantComponent;
	private int timeStepsRemaining;
	
	Repair (PlantComponent componentToRepair) {
		this.plantComponent     = componentToRepair;
		this.timeStepsRemaining = componentToRepair.getRepairTime();
	}
	
	/**
	 * Decrements the time remaining until the component is repaired 
	 */
	public void decTimeStepsRemaining() {
		timeStepsRemaining--;
	}
	
	/**
	 * Returns timeStepsRemaining
	 */
	public int getTimeStepsRemaining() {
		return timeStepsRemaining;
	}
	
	/**
	 * Returns plantComponent
	 */
	public PlantComponent getPlantComponent() {
		return plantComponent;
	}
	
}