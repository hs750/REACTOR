/**
 * 
 */
package test;

import static org.junit.Assert.*;

import javax.swing.event.AncestorEvent;

import model.HighScore;

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
	
	/**
	 * Create the instances of Reactor Utilities, Plant Controller, and Operator Software needed for the tests on the Operator Software.
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		utils = new ReactorUtils();
		controller = new PlantController(utils);
		OS = new OperatorSoftware(controller);
		
	}

	/*
	 * ----------------Test Component Setter Methods when OS is operational -----------------------------
	 */
	/**
	 * Tests whether calling setValve() in operator software, when the operator software is functional, successfully sets the specified valve to the specified position. All valves are individually tested within this test.
	 * Pass Criteria: Every valve in the plant is changed from being open to being closed after.
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
	/**
	 * Tests whether calling setPumpOnOff() in operator software, when the operator software is functional, successfully sets the specified pump. All pumps are individually tested within this test.
	 * Pass Criteria: Every pump in the power plant will be set to off. 
	 */
	@Test
	public void testSetPumpOnOff(){
		int numPumps = controller.getPlant().getPumps().size();
		for(int i = 0; i < numPumps; i++){
			assertEquals("Before: Pump " + (i + 1), true, controller.getPlant().getPumps().get(i).isOn());		//pumps are initially on
			OS.setPumpOnOff(i + 1, false);
			assertEquals("After: Pump " + (i + 1), false, controller.getPlant().getPumps().get(i).isOn());		//check pumps are off
		}
	}
	/**
	 * Tests whether calling setPumpRpm() in operator software, when the operator software is functional, successfully sets the specified pump's RPM. All pumps are individually tested within this test.
	 * Pass Criteria: 
	 * 		Every pump will not be set below 0 RPM
	 * 		Every pump will be set to a legal RPM value (the mid value between 0 and a pumps max rpm
	 * 		Every pump will not be set to a value above the max RPM of the pump.
	 */
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
	/**
	 * Tests whether calling setControlRods() in operator software, when the operator software is functional, successfully sets the control rods to a specified level.
	 * Pass Criteria: 
	 * 		Control rods will not be set to a level below 0
	 * 		Control rods will be set to a legal level (50)
	 * 		Control rods will not be set to a level above 100
	 */
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
	/**
	 * Tests whether calling setValve() in operator software, when the operator software is not functional, does not set the specified valve to the specified position. All valves are individually tested within this test.
	 * Pass Criteria: Less than all the valve should be set to closed.
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
	/**
	 * Tests whether calling setPumpOnOff() in operator software, when the operator software is not functional, does not set the specified pump. All pumps are individually tested within this test.
	 * Pass Criteria: Less than all the pumps should be set to off. 
	 */
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
	/**
	 * Tests whether calling setPumpRPM() in operator software, when the operator software is not functional, does not set the specified pump to the specified RPM. All pumps are individually tested within this test.
	 * Pass Criteria: Less than all the pumps should be set to 50 RPM.
	 */
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
	/**
	 * Tests whether calling setPumpOnOff() in operator software, when the operator software is not functional, does not set the control rods to the specifed level.
	 * Pass Criteria: In 100 attempts at setting the control rods, not all will set the control rods to 50. 
	 */
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
	/**
	 * Tests getReactorHealth() in Operator Software, when Operator Software is functional.
	 * Pass Criteria: The health value from the reactor matches that returned by getReactorHealth()
	 */
	@Test
	public void testGetReactorHealth(){
		int actualHealth = controller.getPlant().getReactor().getHealth();
		int OSHealth = OS.getReactorHealth();
		assertEquals(actualHealth, OSHealth);
	}
	/**
	 * Tests getReactorTemperature() in Operator Software, when Operator Software is functional.
	 * Pass Criteria: The temperature value from the reactor matches that returned by getReactorTemperature()
	 */
	@Test
	public void testGetReactorTemperature(){
		int actualTemp = controller.getPlant().getReactor().getTemperature();
		int OSTemp = OS.getReactorTemperature();
		assertEquals(actualTemp, OSTemp);
	}
	/**
	 * Tests getReactorPressure() in Operator Software, when Operator Software is functional.
	 * Pass Criteria: The pressure value from the reactor matches that returned by getReactorPressure()
	 */
	@Test
	public void testGetReactorPressure(){
		int actualPressure = controller.getPlant().getReactor().getPressure();
		int OSPressure = OS.getReactorPressure();
		assertEquals(actualPressure, OSPressure);
	}
	/**
	 * Tests getReactorWaterLevel() in Operator Software, when Operator Software is functional.
	 * Pass Criteria: The water level value from the reactor matches that returned by getReactorWaterLevel()
	 */
	@Test
	public void testGetReactorWaterVolume(){
		int actualVol = controller.getPlant().getReactor().getWaterVolume();
		int OSVol = OS.getReactorWaterVolume();
		assertEquals(actualVol, OSVol);
	}
	/**
	 * Tests getCondenserHealth() in Operator Software, when Operator Software is functional.
	 * Pass Criteria: The health value from the condenser matches that returned by getCondenserHealth()
	 */
	@Test
	public void testGetCondenserHealth(){
		int actualHealth = controller.getPlant().getCondenser().getHealth();
		int OSHealth = OS.getCondenserHealth();
		assertEquals(actualHealth, OSHealth);
	}
	/**
	 * Tests getCondenserTemperature() in Operator Software, when Operator Software is functional.
	 * Pass Criteria: The temperature value from the condenser matches that returned by getCondenserTemperature()
	 */
	@Test
	public void testGetCondenserTemperature(){
		int actualTemp = controller.getPlant().getCondenser().getTemperature();
		int OSTemp = OS.getCondenserTemperature();
		assertEquals(actualTemp, OSTemp);
	}
	/**
	 * Tests getCondenserPressure() in Operator Software, when Operator Software is functional.
	 * Pass Criteria: The pressure value from the condenser matches that returned by getCondenserPressure()
	 */
	@Test
	public void testGetCondenserPressure(){
		int actualPressure = controller.getPlant().getCondenser().getPressure();
		int OSPressure = OS.getCondenserPressure();
		assertEquals(actualPressure, OSPressure);
	}
	/**
	 * Tests getCondenserWaterLevel() in Operator Software, when Operator Software is functional.
	 * Pass Criteria: The water level value from the condenser matches that returned by getCondenserWaterLevel()
	 */
	@Test
	public void testGetCondenserWaterVolume(){
		int actualVol = controller.getPlant().getCondenser().getWaterVolume();
		int OSVol = OS.getCondenserWaterVolume();
		assertEquals(actualVol, OSVol);
	}
	/**
	 * Tests getControlRodPercentage() in Operator Software, when Operator Software is functional.
	 * Pass Criteria: value returned by getControlRodPercentage() matches the value that the control rods level was manually set to (50).
	 */
	@Test
	public void testGetControlRodPercentage(){
		controller.getPlant().getReactor().setPercentageLowered(50);
		int OS_CR_Level = OS.getControlRodsPercentage();
		assertEquals(50, OS_CR_Level);
	}
	/**
	 * Tests getValvePositions() in Operator Software, when Operator Software is functional.
	 * Pass Criteria: Every valve will be set to closed and then getValvePosition() will return false
	 */
	@Test
	public void testGetValvePositions(){
		int numValves = controller.getPlant().getValves().size();
		for(int i = 0; i < numValves; i++){
			controller.getPlant().getValves().get(i).setOpen(false);
			assertEquals("Valve " + (i+1),false, OS.getValvePositions()[i]);
		}
	}
	/**
	 * Tests getPumpRpms() in Operator Software, when Operator Software is functional.
	 * Pass Criteria: Every pump's RPM will be set to 123 and then getPumpRpms() will return 123
	 */
	@Test 
	public void testGetPumpRpms(){
		int numPumps = controller.getPlant().getPumps().size();
		for(int i = 0; i < numPumps; i++){
			controller.getPlant().getPumps().get(i).setRpm(123);
			assertEquals("Pump " + (i+1), 123, OS.getPumpRpms()[i]);
		}
	}
	/**
	 * Tests arePumpsOn() in Operator Software, when Operator Software is functional.
	 * Pass Criteria: Every pump is set to off and then arePumpsOn() will return false
	 */
	@Test
	public void testArePumpsOn(){
		int numPumps = controller.getPlant().getPumps().size();
		for(int i = 0; i < numPumps; i++){
			controller.getPlant().getPumps().get(i).setOn(false);
			assertEquals("Pump " + (i+1), false, OS.arePumpsOn()[i]);
		}
	}
	/**
	 * Tests getTurbineRpm() in Operator Software, when Operator Software is functional.
	 * Pass Criteria: The RPM value from the Turbine in the power plant will be the same as that returned by getTurbineRpm()
	 */
	@Test
	public void testGetTurbineRpm(){
		int actualRPM = controller.getPlant().getTurbine().getRpm();
		int OS_RPM = OS.getTurbineRpm();
		assertEquals(actualRPM, OS_RPM);
	}
	/**
	 * Tests getPowerOutput() in Operator Software, when Operator Software is functional.
	 * Pass Criteria: The power output value from the Generator in the power plant will be the same as that returned by getPowerOutput()
	 */
	@Test
	public void testGetPowerOutput(){
		int actualOutput = controller.getPlant().getGenerator().getPowerOutput();
		int OSOutput = OS.getPowerOutput();
		assertEquals(actualOutput, OSOutput);
	}
	/*
	 * ----------------- Test Component Getter Methods when OS is not functional ---------------------
	 */
	/**
	 * Tests getReactorHealth() in Operator Software, when Operator Software is not functional.
	 * Pass Criteria: The health value from the reactor doesnt match that returned by getReactorHealth() in 95 out of 100 get operations. 
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
	/**
	 * Tests getReactorTemperature() in Operator Software, when Operator Software is not functional.
	 * Pass Criteria: The temperature value from the reactor doesnt match that returned by getReactorTemperature() in 95 out of 100 get operations. 
	 */
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
	/**
	 * Tests getReactorPressure() in Operator Software, when Operator Software is not functional.
	 * Pass Criteria: The pressure value from the reactor doesnt match that returned by getReactorPressure() in 95 out of 100 get operations. 
	 */
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
	/**
	 * Tests getReactorWaterLevel() in Operator Software, when Operator Software is not functional.
	 * Pass Criteria: The water level value from the reactor doesnt match that returned by getReactorLevel() in 95 out of 100 get operations. 
	 */
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
	/**
	 * Tests getCondenserHealth() in Operator Software, when Operator Software is not functional.
	 * Pass Criteria: The health value from the condenser doesn't match that returned by getCondenserHealth() in 95 out of 100 get operations. 
	 */
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
	/**
	 * Tests getCondenserTemperature() in Operator Software, when Operator Software is not functional.
	 * Pass Criteria: The temperature value from the condenser doesn't match that returned by getCondenserTemperature() in 95 out of 100 get operations. 
	 */
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
	/**
	 * Tests getCondenserPressure() in Operator Software, when Operator Software is not functional.
	 * Pass Criteria: The pressure value from the condenser doesn't match that returned by getCondenserPressure() in 95 out of 100 get operations. 
	 */
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
	/**
	 * Tests getCondenserWaterLevel() in Operator Software, when Operator Software is not functional.
	 * Pass Criteria: The water level value from the condenser doesn't match that returned by getCondenserWaterLevel() in 95 out of 100 get operations. 
	 */
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
	/**
	 * Tests getControlRodPercentage() in Operator Software, when Operator Software is not functional.
	 * Pass Criteria: value returned by getControlRodPercentage() does not match the value that the control rods level was manually set to (50), in 95 out of 100 get operations.
	 */
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
	/**
	 * Tests getValvePositions() in Operator Software, when Operator Software is not functional.
	 * Pass Criteria: Every valve will be set to closed and then getValvePosition() will return false only 70% of the time.
	 */
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
	/**
	 * Tests getPUmpsRpms() in Operator Software, when Operator Software is not functional.
	 * Pass Criteria: Every pump's RPM will be set to 123 and then getValvePosition() will return 123 only 5% of the time.
	 */
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
	/**
	 * Tests arePUmpsOn() in Operator Software, when Operator Software is not functional.
	 * Pass Criteria: Every pump will be set to off and then arePumpsOn() will return false only 70% of the time.
	 */
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
	/**
	 * Tests getTurbineRpm() in Operator Software, when Operator Software is not functional.
	 * Pass Criteria: The RPM value from the Turbine in the power plant will not be the same as that returned by getTurbineRpm() in 95% of method calls.
	 */
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
	/**
	 * Tests getPowerOuput() in Operator Software, when Operator Software is functional.
	 * Pass Criteria: The power output value from the Generator in the power plant will not be the same as that returned by getPowerOutput() in 95% of method calls.
	 */
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
	@Test
	public void testArePumpsFunctional(){
		int numPumps = controller.getPlant().getPumps().size();
		for(int i = 0; i < numPumps; i++){
			boolean actualFunc = controller.getPlant().getPumps().get(i).isOperational();
			boolean OSFunc = OS.arePumpsFunctional()[i];
			assertEquals("Pump " + (i + 1), actualFunc, OSFunc);
		}
	}
	@Test
	public void testIsTurbineFunctional(){
		boolean actualFunc = controller.getPlant().getTurbine().isOperational();
		boolean OSFunc = OS.isTurbineFunctional();
		assertEquals(actualFunc, OSFunc);
	}
	@Test
	public void testRepairTurbine(){
		while(OS.isTurbineFunctional()) //Keep stepping through game until Turbine randomly breaks.
			controller.step(1);
		OS.repairTurbine();
		controller.step(controller.getPlant().getTurbine().getRepairTime());
		assertEquals(true, controller.getPlant().getTurbine().isOperational());
	}
	@Test
	public void testRepairPumps(){
		int numPumps = controller.getPlant().getPumps().size();
		for(int i = 0; i < numPumps; i++){
			try {setUp();} catch (Exception e) {e.printStackTrace();}
			while(OS.arePumpsFunctional()[i])			//Keep stepping through game until Pump randomly breaks.
				controller.step(1);	
			OS.repairPump(i+1);
			controller.step(controller.getPlant().getPumps().get(i).getRepairTime());
			assertEquals(true, controller.getPlant().getPumps().get(i).isOperational());
		}
	}
	@Test
	public void testRebootOS(){
		int numOSFailedAttempts = 0;
		while(!OS.isOSFailed()){
			OS.calculateOSFailed();
			numOSFailedAttempts++;
		}
		assertEquals(true, OS.isOSFailed());
		assertTrue(""+ numOSFailedAttempts, numOSFailedAttempts < 200); //1 in 100 calcs should fail OS (at time of making test)
		OS.rebootOS();
		assertEquals(false, OS.isOSFailed());
	}
	/*
	 * -------------- Get Min/Max component values -----------------------
	 */
	@Test
	public void testGetCondenserMaxPressure(){
		int actualMax = controller.getPlant().getCondenser().getMaxPressure();
		int OSMax = OS.getCondenserMaxPressure();
		assertEquals(actualMax, OSMax);
	}
	@Test
	public void testGetReactorMaxPresssure(){
		int actualMax = controller.getPlant().getReactor().getMaxPressure();
		int OSMax = OS.getReactorMaxPressure();
		assertEquals(actualMax, OSMax);
	}
	@Test
	public void testGetReactorMaxTemperature(){
		int actualMax = controller.getPlant().getReactor().getMaxTemperature();
		int OSMax = OS.getReactorMaxTemperature();
		assertEquals(actualMax, OSMax);
	}
	@Test 
	public void testGetCondenserMaxTemperature(){
		int actualMax = controller.getPlant().getCondenser().getMaxTemperature();
		int OSMax = OS.getCondenserMaxTemperature();
		assertEquals(actualMax, OSMax);
	}
	@Test
	public void testGetReactorMinSafeWaterVolume(){
		int actualMin = controller.getPlant().getReactor().getMinSafeWaterVolume();
		int OSMin = OS.getReactorMinSafeWaterVolume();
		assertEquals(actualMin, OSMin);
	}
	/*
	 * -------------- Other Operator Software Functionality Tests -----------------------
	 */
	@Test
	public void testStep(){
		for(int i = 1; i <= 10; i++){
			int initialNumSteps = controller.getPlant().getTimeStepsUsed();
			OS.step(i);
			int newNumSteps = controller.getPlant().getTimeStepsUsed();
			assertEquals(initialNumSteps + i , newNumSteps);
		}
	}
	@Test
	public void testNewGame_GetOperatorName(){
		String name = "Anchovy";
		OS.newGame(name);
		assertEquals(name, OS.getOperatorName());
	}
	@Test
	public void testSaveGame_LoadGame(){
		String name = "Anchovy";
		OS.newGame(name);
		OS.saveGame();
		try{setUp();}catch(Exception e){};
		assertEquals(null, OS.getOperatorName());
		OS.loadGame();
		assertEquals(name, OS.getOperatorName());
	}
	@Test
	public void testGetHighScores(){
		assertSame(controller.getHighScores(), OS.getHighScores());
	}
	@Test
	public void testAddHighScore(){
		HighScore highScore = new HighScore("Anchovy", 9001);
		OS.addHighScore(highScore);
		assertTrue(controller.getHighScores().contains(highScore));
	}
	@Test
	public void testScore(){
		int actualScore = controller.getPlant().getScore();
		int OSScore = OS.getScore();
		assertEquals(actualScore, OSScore);
	}
	@Test
	public void testIsGameOver(){
		boolean actualStatus = controller.getPlant().isGameOver();
		boolean OSStatus = OS.isGameOver();
		assertEquals(actualStatus, OSStatus);
	}
}
