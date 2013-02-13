package test;

import simulator.*;
import view.GUI;
import model.*;
import pcomponents.*;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ValveTest {

	private GUI view;
	private PlantController presenter; 
	private ReactorUtils utils;
	private Plant plant;
	
	private Valve valve;
	private int valveID = 4;
	private FlowType valveFlowType = FlowType.Water;
	private boolean valveOpen = true;

	@Before
	public void setUp() {
		//GameInit game = new GameInit();
		utils = new ReactorUtils();
		presenter = new PlantController(utils);
		view = new GUI(presenter);
		plant = presenter.getPlant();
		
		this.valve = new Valve(this.valveID, this.valveFlowType, this.valveOpen);
	}
	
	@Test
	public void testGetID() {
		
		assertEquals("Result", this.valveID, valve.getID());
		
	}
	
	@Test
	public void testIsOpen() {
		
		assertEquals("Result", valveOpen, valve.isOpen());
		
		valve.setOpen(false);
		
		assertEquals("Result", false, valve.isOpen());
		
	}

}
