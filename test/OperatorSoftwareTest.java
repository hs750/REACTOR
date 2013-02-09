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
	 * ----------------Test Component Setter Methods when OS is operational -----------------------------
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
	 * ----------------Test Component Setter Methods when OS is not operational -----------------------------
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
	
	/*
	 * ----------------------Test Component Getter Methods when OS is functional ---------------------------
	 */
	@Test
	public void testGetReactorHealth(){
		int actualHealth = controller.getPlant().getReactor().getHealth();
		int OSHealth = OS.getReactorHealth();
		assertEquals(actualHealth, OSHealth);
	}
	@Test
	public void testGetReactorTemperature(){
		int actualTemp = controller.getPlant().getReactor().getTemperature();
		int OSTemp = OS.getReactorTemperature();
		assertEquals(actualTemp, OSTemp);
	}
	@Test
	public void testGetReactorPressure(){
		int actualPressure = controller.getPlant().getReactor().getPressure();
		int OSPressure = OS.getReactorPressure();
		assertEquals(actualPressure, OSPressure);
	}
	@Test
	public void testGetReactorWaterVolume(){
		int actualVol = controller.getPlant().getReactor().getWaterVolume();
		int OSVol = OS.getReactorWaterVolume();
		assertEquals(actualVol, OSVol);
	}
	@Test
	public void testGetCondenserHealth(){
		int actualHealth = controller.getPlant().getCondenser().getHealth();
		int OSHealth = OS.getCondenserHealth();
		assertEquals(actualHealth, OSHealth);
	}
	@Test
	public void testGetCondenserTemperature(){
		int actualTemp = controller.getPlant().getCondenser().getTemperature();
		int OSTemp = OS.getCondenserTemperature();
		assertEquals(actualTemp, OSTemp);
	}
	@Test
	public void testGetCondenserPressure(){
		int actualPressure = controller.getPlant().getCondenser().getPressure();
		int OSPressure = OS.getCondenserPressure();
		assertEquals(actualPressure, OSPressure);
	}
	@Test
	public void testGetCondenserWaterVolume(){
		int actualVol = controller.getPlant().getCondenser().getWaterVolume();
		int OSVol = OS.getCondenserWaterVolume();
		assertEquals(actualVol, OSVol);
	}
	@Test
	public void testGetControlRodPercentage(){
		controller.getPlant().getReactor().setPercentageLowered(50);
		int OS_CR_Level = OS.getControlRodsPercentage();
		assertEquals(50, OS_CR_Level);
	}
	@Test
	public void testGetValvePositions(){
		int numValves = controller.getPlant().getValves().size();
		for(int i = 0; i < numValves; i++){
			controller.getPlant().getValves().get(i).setOpen(false);
			assertEquals("Valve " + (i+1),false, OS.getValvePositions()[i]);
		}
	}
	@Test 
	public void testGetPumpRpms(){
		int numPumps = controller.getPlant().getPumps().size();
		for(int i = 0; i < numPumps; i++){
			controller.getPlant().getPumps().get(i).setRpm(123);
			assertEquals("Pump " + (i+1), 123, OS.getPumpRpms()[i]);
		}
	}
	@Test
	public void testArePumpsOn(){
		int numPumps = controller.getPlant().getPumps().size();
		for(int i = 0; i < numPumps; i++){
			controller.getPlant().getPumps().get(i).setOn(false);
			assertEquals("Pump " + (i+1), false, OS.arePumpsOn()[i]);
		}
	}
	@Test
	public void testGetTurbineRpm(){
		int actualRPM = controller.getPlant().getTurbine().getRpm();
		int OS_RPM = OS.getTurbineRpm();
		assertEquals(actualRPM, OS_RPM);
	}
	@Test
	public void testGetPowerOutput(){
		int actualOutput = controller.getPlant().getGenerator().getPowerOutput();
		int OSOutput = OS.getPowerOutput();
		assertEquals(actualOutput, OSOutput);
	}
	/*
	 * ----------------- Test Component Getter Methods when OS is not functional ---------------------
	 */
	@Test
	public void testGetReactorHealth_OSF(){
		int correct = 0;
		for(int i = 0; i < 100; i++){
			OS.setOSFailed(true);
			int actualHealth = controller.getPlant().getReactor().getHealth();
			int OSHealth = OS.getReactorHealth();
			if(actualHealth == OSHealth)
				correct++;
		}
		assertTrue(""+ correct, correct < 5); //Rarely returns the correct value
	}
	@Test
	public void testGetReactorTemperature_OSF(){
		int correct = 0;
		for(int i = 0; i < 100; i++){
			OS.setOSFailed(true);
			int actualTemp = controller.getPlant().getReactor().getTemperature();
			int OSTemp = OS.getReactorTemperature();
			if(actualTemp == OSTemp)
				correct++;
		}
		assertTrue(""+ correct, correct < 5); //Rarely returns the correct value
	}
	@Test
	public void testGetReactorPressure_OSF(){
		int correct = 0;
		for(int i = 0; i < 100; i++){
			OS.setOSFailed(true);
			int actualPressure = controller.getPlant().getReactor().getPressure();
			int OSPressure = OS.getReactorPressure();
			if(actualPressure == OSPressure)
				correct++;
		}
		assertTrue(""+ correct, correct < 5); //Rarely returns the correct value
	}
	@Test
	public void testGetReactorWaterVolume_OSF(){
		int correct = 0;
		for(int i = 0; i < 100; i++){
			OS.setOSFailed(true);
			int actualVol = controller.getPlant().getReactor().getWaterVolume();
			int OSVol = OS.getReactorWaterVolume();
			if(actualVol == OSVol)
				correct++;
		}
		assertTrue(""+ correct, correct < 5); //Rarely returns the correct value
	
	}
	@Test
	public void testGetCondenserHealth_OSF(){
		int correct = 0;
		for(int i = 0; i < 100; i++){
			OS.setOSFailed(true);
			int actualHealth = controller.getPlant().getCondenser().getHealth();
			int OSHealth = OS.getCondenserHealth();
			if(actualHealth == OSHealth)
				correct++;
		}
		assertTrue(""+ correct, correct < 5); //Rarely returns the correct value
	}
	@Test
	public void testGetCondenserTemperature_OSF(){
		int correct = 0;
		for(int i = 0; i < 100; i++){
			OS.setOSFailed(true);
			int actualTemp = controller.getPlant().getCondenser().getTemperature();
			int OSTemp = OS.getCondenserTemperature();
			if(actualTemp == OSTemp)
				correct++;
		}
		assertTrue(""+ correct, correct < 5); //Rarely returns the correct value
	}
	@Test
	public void testGetCondenserPressure_OSF(){
		int correct = 0;
		for(int i = 0; i < 100; i++){
			OS.setOSFailed(true);
			int actualPressure = controller.getPlant().getCondenser().getPressure();
			int OSPressure = OS.getCondenserPressure();
			if(actualPressure == OSPressure)
				correct++;
		}
		assertTrue(""+ correct, correct < 5); //Rarely returns the correct value
	}
	@Test
	public void testGetCondenserWaterVolume_OSF(){
		int correct = 0;
		for(int i = 0; i < 100; i++){
			OS.setOSFailed(true);
			int actualVol = controller.getPlant().getCondenser().getWaterVolume();
			int OSVol = OS.getCondenserWaterVolume();
			if(actualVol == OSVol)
				correct++;
		}
		assertTrue(""+ correct, correct < 5); //Rarely returns the correct value
	}
	@Test
	public void testGetControlRodPercentage_OSF(){
		int correct = 0;
		for(int i = 0; i < 100; i++){
			OS.setOSFailed(true);
			controller.getPlant().getReactor().setPercentageLowered(50);
			int OS_CR_Level = OS.getControlRodsPercentage();
			if(50 == OS_CR_Level)
				correct++;
		}
		assertTrue(""+ correct, correct < 5); //Rarely returns the correct value
	}
	@Test
	public void testGetValvePositions_OSF(){
		int numValves = controller.getPlant().getValves().size();
		for(int i = 0; i < numValves; i++){
			int correct = 0;
			for(int j = 0; j < 100; j++){
				OS.setOSFailed(true);
				controller.getPlant().getValves().get(i).setOpen(false);
				if(!OS.getValvePositions()[i])
					correct++;
			}
			assertTrue("Valve:" + i + " " + correct, correct < 70); //Rarely returns the correct value
		}
	}
	@Test 
	public void testGetPumpRpms_OSF(){
		int numPumps = controller.getPlant().getPumps().size();
		for(int i = 0; i < numPumps; i++){
			int correct = 0;
			for(int j = 0; j < 100; j++){
				OS.setOSFailed(true);
				controller.getPlant().getPumps().get(i).setRpm(123);
				if(OS.getPumpRpms()[i] == 123)
					correct++;
			}
			assertTrue("Pump: "+ i + " " + correct, correct < 5); //Rarely returns the correct value
		}
	}
	@Test
	public void testArePumpsOn_OSF(){
		int numPumps = controller.getPlant().getPumps().size();
		for(int i = 0; i < numPumps; i++){
			int correct = 0;
			for(int j = 0; j < 100; j++){
				OS.setOSFailed(true);
				controller.getPlant().getPumps().get(i).setOn(false);
				if(!OS.arePumpsOn()[i])
					correct++;
			}
			assertTrue("Pump: "+ i + " " + correct, correct < 70); //Rarely returns the correct value
		}
	}
	@Test
	public void testGetTurbineRpm_OSF(){
		int correct = 0;
		for(int i = 0; i < 100; i++){
			OS.setOSFailed(true);
			int actualRPM = controller.getPlant().getTurbine().getRpm();
			int OS_RPM = OS.getTurbineRpm();
			if(actualRPM == OS_RPM)
				correct++;
		}
		assertTrue(""+ correct, correct < 5); //Rarely returns the correct value
	}
	@Test
	public void testGetPowerOutput_OSF(){
		int correct = 0;
		for(int i = 0; i < 100; i++){
			OS.setOSFailed(true);	
			int actualOutput = controller.getPlant().getGenerator().getPowerOutput();
			int OSOutput = OS.getPowerOutput();
			if(actualOutput == OSOutput)
				correct++;
		}
		assertTrue(""+ correct, correct < 5); //Rarely returns the correct value
	}
	/*
	 *------------------- Component Functionality and Repair Tests ---------------------- 
	 */
}
