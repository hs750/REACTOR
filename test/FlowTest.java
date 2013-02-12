package test;

import simulator.*;
import view.gameView;
import model.*;
import pcomponents.*;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class FlowTest {

	private gameView view;
	private PlantController presenter; 
	private ReactorUtils utils;
	private Plant plant;
	
	private Flow flow;

	@Before
	public void setUp() {
		//GameInit game = new GameInit();
		utils = new ReactorUtils();
		presenter = new PlantController(utils);
		view = new gameView(presenter);
		plant = presenter.getPlant();
		
		flow = new Flow();
	}
	
	@Test
	public void testSetGetRate() {
		
		flow.setRate(5);
		
		assertEquals("Result", 5, flow.getRate());
		
	}
	
	@Test
	public void testSetGetTemperature() {
		
		flow.setTemperature(45);
		
		assertEquals("Result", 45, flow.getTemperature());
		
	}
	
	@Test
	public void testSetGetType() {
		
		FlowType flowType = FlowType.Water;
		
		flow.setType(flowType);
		
		assertEquals("Result", FlowType.Water, flow.getType());
		
	}

}
