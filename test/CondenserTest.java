package test;

import simulator.*;
import view.GUI;
import model.*;
import pcomponents.*;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class CondenserTest {

	private GUI view;
	private PlantController presenter; 
	private ReactorUtils utils;
	private Plant plant;
	private Condenser condenser;

	@Before
	public void setUp() {
		//GameInit game = new GameInit();
		utils = new ReactorUtils();
		presenter = new PlantController(utils);
		view = new GUI(presenter);
		plant = presenter.getPlant();
		
		condenser = plant.getCondenser();
	}
	
	@Test
	public void testUpdateWaterVolume() {
		
		int currentWaterVolume = condenser.getWaterVolume();
		
		condenser.updateWaterVolume(300);
		
		assertEquals("Result", (currentWaterVolume+300), condenser.getWaterVolume());
		
	}
	
	@Test
	public void testUpdateSteamVolume() {
		
		int currentSteamVolume = condenser.getSteamVolume();
		
		condenser.updateSteamVolume(300);
		
		assertEquals("Result", (currentSteamVolume+300), condenser.getSteamVolume());
		
	}

}
