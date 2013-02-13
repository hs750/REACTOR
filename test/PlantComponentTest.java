package test;

import simulator.*;
import view.GUI;
import model.*;
import pcomponents.*;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class PlantComponentTest {

	private GUI view;
	private PlantController presenter; 
	private ReactorUtils utils;
	private Plant plant;

	@Before
	public void setUp() {
		//GameInit game = new GameInit();
		utils = new ReactorUtils();
		presenter = new PlantController(utils);
		view = new GUI(presenter);
		plant = presenter.getPlant();
	}
	
	@Test
	public void testFailureRate() {
		
		
		
	}
	

}
