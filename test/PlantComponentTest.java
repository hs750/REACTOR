package test;

import simulator.*;
import view.gameView;
import model.*;
import pcomponents.*;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class PlantComponentTest {

	private gameView view;
	private PlantController presenter; 
	private ReactorUtils utils;
	private Plant plant;

	@Before
	public void setUp() {
		//GameInit game = new GameInit();
		utils = new ReactorUtils();
		presenter = new PlantController(utils);
		view = new gameView(presenter);
		plant = presenter.getPlant();
	}
	
	@Test
	public void testFailureRate() {
		
		
		
	}
	

}
