/**
 * 
 */
package test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import simulator.*;

/**
 * @author Harrison
 *
 */
public class OperatorSoftwareTest {
	
	private OperatorSoftware OS;
	private PlantController controller;
	private ReactorUtils utils;
	
	@Before
	public void setUp() throws Exception {
		utils = new ReactorUtils();
		controller = new PlantController(utils);
		OS = new OperatorSoftware(controller);
		
	}

	/*
	 * ----------------Test Setter Methods when OS is operational -----------------------------
	 */
	
	@Test
	public void testSetValve() {
		int numValves = controller.getPlant().getValves().size();
		for(int i = 0; i < numValves; i++){
			assertEquals("Before: valve " + (i + 1), true, controller.getPlant().getValves().get(i).isOpen());		//valves are intially open
			OS.setValve(i + 1, false);
			assertEquals("After: valve " + (i + 1), false, controller.getPlant().getValves().get(i).isOpen());		//check they are not cloesd
		}
	}
	@Test
	public void testSetPumpOnOff(){
		int numPumps = controller.getPlant().getPumps().size();
		for(int i = 0; i < numPumps; i++){
			assertEquals("Before: Pump " + (i + 1), true, controller.getPlant().getPumps().get(i).isOn());		//pumps are initially on
			OS.setPumpOnOff(i + 1, false);
			assertEquals("After: Pump " + (i + 1), false, controller.getPlant().getPumps().get(i).isOn());		//check pumps are off
		}
	}
	
	@Test
	public void testSetPumpRpm(){
		int numPumps = controller.getPlant().getPumps().size();
		for(int i = 0; i < numPumps; i++){
			assertEquals("Before: Pump " + (i + 1), 0, controller.getPlant().getPumps().get(i).getRpm());		//pumps are initially at 0 rpm
			
			try{
				OS.setPumpRpm(i+1, -1);
				fail("Setting pump to -1 should have caused an exception");
			}catch(IllegalArgumentException e){
				assertEquals("Bellow 0: Pump " + (i + 1), 0, controller.getPlant().getPumps().get(i).getRpm());		//pumps are initially at 0 rpm
			}
			
			int pumpMaxRPM = controller.getPlant().getPumps().get(i).getMaxRpm();
			OS.setPumpRpm(i+1, pumpMaxRPM/2);
			assertEquals("In rage RPM: Pump " + (i + 1), pumpMaxRPM/2, controller.getPlant().getPumps().get(i).getRpm());		//pumps are initially at 0 rpm

			try{
				OS.setPumpRpm(i+1, pumpMaxRPM+1);
				fail("Setting pump above max pump rpm should have caused an exception");
			}catch(IllegalArgumentException e){
				assertEquals("Above Max: Pump " + (i + 1), pumpMaxRPM/2, controller.getPlant().getPumps().get(i).getRpm());		//pumps are initially at 0 rpm
			}
		}
	}
	
	@Test
	public void testSetControllRods(){
		OS.setControlRods(-1);
		controller.getUIData().updateUIData();
		assertEquals("Below min, should have no change", 100, controller.getUIData().getControlRodsPercentage());
		
		OS.setControlRods(50);
		controller.getUIData().updateUIData();
		assertEquals("In range", 50, controller.getUIData().getControlRodsPercentage());
		
		OS.setControlRods(101);
		controller.getUIData().updateUIData();
		assertEquals("Above Max, should have no change", 50, controller.getUIData().getControlRodsPercentage());
		
	}
	
	/*
	 * ----------------Test Setter Methods when OS is not operational -----------------------------
	 */
	
	@Test
	public void testSetValve_OSF() {
		
		OS.setOSFailed(true);
		int numValves = controller.getPlant().getValves().size();
		for(int i = 0; i < numValves; i++){
			int closed = 0;
			for(int j = 0; j < 100; j++){
				OS.setValve(i + 1, false);
				
				if(!controller.getPlant().getValves().get(i).isOpen())
					closed++;
			}
			System.out.print(closed + " ");
			assertTrue("" + closed, closed < 100); //valve should not always be set correctly.
		}
	}
	@Test
	public void testSetPumpOnOff_OSF(){
		OS.setOSFailed(true);
		int numPumps = controller.getPlant().getPumps().size();
		for(int i = 0; i < numPumps; i++){
			int off = 0;
			for(int j = 0; j < 100; j++){
				OS.setPumpOnOff(i + 1, false);
				if(!controller.getPlant().getPumps().get(i).isOn())
					off++;
			}
			System.out.print(off + " ");
			assertTrue("" + off, off < 100); //pump should not always be set correctly.
		}
	}
	
	@Test
	public void testSetPumpRpm_OSF(){
		OS.setOSFailed(true);
		int numPumps = controller.getPlant().getPumps().size();
		for(int i = 0; i < numPumps; i++){
			int correct = 0;
			for(int j = 0; j < 1000; j++){
				OS.setPumpRpm(i+1, 500);
				if(controller.getPlant().getPumps().get(i).getRpm() == 50)
					correct++;
				
			}
			System.out.print(correct + " ");
			assertTrue("" + correct, correct < 100); //pump should not always be set correctly - Incredibly unlikely. 
		}
	}
	
	@Test
	public void testSetControllRods_OSF(){
		OS.setOSFailed(true);
		int correct = 0;
		for(int j = 0; j < 1000; j++){
			OS.setControlRods(50);
			controller.getUIData().updateUIData();
			if(controller.getUIData().getControlRodsPercentage() == 50)
				correct++;
		}
		System.out.print(correct + " ");
		assertTrue("" + correct, correct < 100); //Control rods should be very rarely set to the correct position. 
	}
	
}
