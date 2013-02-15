package view;

import java.awt.FontMetrics;
import java.util.ArrayList;
import java.util.List;

import model.HighScore;
import simulator.OperatorSoftware;

public class SideBar 
{
	
	public SideBar(Renderer r, OperatorSoftware soft)
	{
		this.r = r;
		opSoft = soft;
		displayedInfo = new ArrayList<Text>();
		
		increase = new IncrementButton("graphics/increase.png", 100, 100, 20, 20, 5);
		increase.disable();
		decrease = new IncrementButton("graphics/decrease.png", 100, 200, 20, 20, -5);
		decrease.disable();
		switchButton = new OnOffButton("graphics/onoff.png", 400, 400, 60, 35);
		repair = new RepairButton("graphics/repair.png", 400, 400, 60, 40);
		repair.disable();
		switchButton.disable();
		r.registerButton(repair);
		r.registerButton(increase);
		r.registerButton(decrease);
		
		r.registerButton(switchButton);
		
		currentView = ViewState.reactor;
		currentViewId = 0;
	}
	
	public void switchComponentView(ViewState state, int id)
	{
		currentView = state;
		currentViewId = id;
		displayedInfo = new ArrayList<Text>();
		int startingPosX = 1030;
		int startingPosY = 100;
		updateData();
		FontMetrics met;
		switch(currentView)
		{
		case none:
			break;
		case scores:
			repair.disable();
			decrease.disable();
			increase.disable();
			switchButton.disable();
			List<HighScore> scores = opSoft.getHighScores();
			displayedInfo.add(new Text("High scores", startingPosX, startingPosY, 15));
			met = r.getMetrics(displayedInfo.get(0));
			if(scores.size() == 0)
			{
				displayedInfo.add(new Text("No high scores stored yet.", 15));
				displayedInfo.get(0).nextLine(displayedInfo.get(1), met);
			}
			else
			{
				for(int i = 0; i != scores.size() && i < 10; ++i)
				{
					displayedInfo.add(new Text(scores.get(i).getName() + ' ' + scores.get(i).getHighScore(), 15));
					displayedInfo.get(i).nextLine(displayedInfo.get(i + 1), met);
				}
			}
			break;
		case condenser:
			repair.disable();
			decrease.enable();
			increase.enable();
			switchButton.enable();
			displayedInfo.add(new Text("Condenser", startingPosX, startingPosY, 15));
			met = r.getMetrics(displayedInfo.get(0));
			
			displayedInfo.add(new Text("Pressure: " + opSoft.getCondenserPressure(), 15));
			displayedInfo.get(0).nextLine(displayedInfo.get(1), met);
			
			displayedInfo.add(new Text("Temperature: " + opSoft.getCondenserTemperature(), 15));
			displayedInfo.get(1).nextLine(displayedInfo.get(2), met);
			
			displayedInfo.add(new Text("Health: " + opSoft.getCondenserHealth(), 15));
			displayedInfo.get(2).nextLine(displayedInfo.get(3), met);
			
			displayedInfo.add(new Text("Water Volume: " + opSoft.getCondenserWaterVolume(), 15));
			displayedInfo.get(3).nextLine(displayedInfo.get(4), met);

			
			displayedInfo.add(new Text("Condenser pump", 15));
			displayedInfo.get(4).nextLine(displayedInfo.get(5), met);
			displayedInfo.add(new Text("RPM", 15));
			displayedInfo.get(5).nextLine(displayedInfo.get(6), met);
			
			displayedInfo.get(6).allign(decrease, met);
			decrease.allign(increase);
			
			displayedInfo.add(new Text(Integer.toString(pumpRPMs[2]), 15));
			increase.allign(displayedInfo.get(7));
			
			if(pumpPositions[2])
				displayedInfo.add(new Text("Running", 15));
			else displayedInfo.add(new Text("Turned off", 15));
			
			displayedInfo.get(6).nextLine(displayedInfo.get(8), met);
			displayedInfo.get(8).allign(switchButton, met);
			
			if(!pumpFunctional[2])
			{
				displayedInfo.add(new Text("Condenser pump is broken.", 15));
				displayedInfo.get(8).nextLine(displayedInfo.get(9), met);
				displayedInfo.get(9).allign(repair, met);
				repair.enable();
			}
			 
			break;
		case pump:
			repair.disable();
			decrease.enable();
			increase.enable();
			switchButton.enable();
			
			displayedInfo.add(new Text("Pump " + currentViewId, startingPosX, startingPosY, 15));
			met = r.getMetrics(displayedInfo.get(0));
			
			
			displayedInfo.add(new Text("RPM", 15));
			displayedInfo.get(0).nextLine(displayedInfo.get(1), met);
			
			displayedInfo.get(1).allign(decrease, met);
			decrease.allign(increase);
			displayedInfo.add(new Text(Integer.toString(pumpRPMs[currentViewId - 1]), 15));
			increase.allign(displayedInfo.get(2));
			
			if(pumpPositions[currentViewId - 1])
				displayedInfo.add(new Text("Running", 15));
			else displayedInfo.add(new Text("Turned off", 15));
			
			displayedInfo.get(1).nextLine(displayedInfo.get(3), met);
			displayedInfo.get(3).allign(switchButton, met);
			if(!pumpFunctional[currentViewId - 1])
			{
				displayedInfo.add(new Text("Pump is broken.", 15));
				displayedInfo.get(3).nextLine(displayedInfo.get(4), met);
				displayedInfo.get(4).allign(repair, met);
				repair.enable();
			}

			break;
		case reactor:
			repair.disable();
			decrease.enable();
			increase.enable();
			switchButton.disable();
			displayedInfo.add(new Text("Reactor", startingPosX, startingPosY, 15));
			met = r.getMetrics(displayedInfo.get(0));
			
			displayedInfo.add(new Text("Health: " + opSoft.getReactorHealth(), 15));
			displayedInfo.get(0).nextLine(displayedInfo.get(1), met);
			
			displayedInfo.add(new Text("Temperature: " + opSoft.getReactorTemperature(), 15));
			displayedInfo.get(1).nextLine(displayedInfo.get(2), met);
			
			displayedInfo.add(new Text("Pressure: " + opSoft.getReactorPressure(), 15));
			displayedInfo.get(2).nextLine(displayedInfo.get(3), met);
			
			displayedInfo.add(new Text("Water Volume: " + opSoft.getReactorWaterVolume(), 15));
			displayedInfo.get(3).nextLine(displayedInfo.get(4), met);
			
			displayedInfo.add(new Text("Control rod position: ", 15));
			displayedInfo.get(4).nextLine(displayedInfo.get(5), met);
			displayedInfo.get(5).allign(decrease, met);
			displayedInfo.add(new Text(Integer.toString(opSoft.getControlRodsPercentage()), 15));
			decrease.allign(increase);
			increase.allign(displayedInfo.get(6));
			
			break;
		case turbine:
			repair.disable();
			decrease.disable();
			increase.disable();
			switchButton.disable();
			displayedInfo.add(new Text("Turbine", startingPosX, startingPosY, 15));
			met = r.getMetrics(displayedInfo.get(0));
			
			displayedInfo.add(new Text("RPM: " + opSoft.getTurbineRpm(), 15));
			displayedInfo.get(0).nextLine(displayedInfo.get(1), met);
			
			displayedInfo.add(new Text("Power output: " + opSoft.getPowerOutput(), 15));
			displayedInfo.get(1).nextLine(displayedInfo.get(2), met);
			if(!opSoft.isTurbineFunctional())
			{
				displayedInfo.add(new Text("Turbine is broken.", 15));
				displayedInfo.get(2).nextLine(displayedInfo.get(3), met);
				displayedInfo.get(3).allign(repair, met);
				repair.enable();
			}
			
			break;
		case valve:
			decrease.disable();
			increase.disable();
			switchButton.enable();
			repair.disable();
			displayedInfo.add(new Text("Valve " + currentViewId, startingPosX, startingPosY, 15));
			met = r.getMetrics(displayedInfo.get(0));
			
			if(valvePositions[currentViewId - 1])
				displayedInfo.add(new Text("State: Open", 15));
			else  displayedInfo.add(new Text("State: Closed", 15));
			displayedInfo.get(0).nextLine(displayedInfo.get(1), met);
			
			displayedInfo.get(0).nextLine(switchButton, met);
			break;
		default:
			break;
		}
	}
	
	
	public void updateData()
	{
		valvePositions = opSoft.getValvePositions();
		pumpPositions = opSoft.arePumpsOn();
		pumpRPMs = opSoft.getPumpRpms();
		pumpFunctional = opSoft.arePumpsFunctional();
	}
	
	public void updateGraphics()
	{
		switchComponentView(currentView, currentViewId);
		r.queueForRendering(increase);
		r.queueForRendering(decrease);
		r.queueForRendering(repair);
		r.queueForRendering(switchButton);
		for(Text t : displayedInfo)
		{
			r.queueForRendering(t);
		}
	}
	
	class OnOffButton extends Button
	{

		public OnOffButton(String imageName, int posX, int posY, int width, int height) 
		{
			super(imageName, posX, posY, width, height);
			
		}
		
		@Override
		public void doAction()
		{
			switch(currentView)
			{
			case valve:
				opSoft.setValve(currentViewId, !valvePositions[currentViewId - 1]);
			case pump:
				opSoft.setPumpOnOff(currentViewId, !pumpPositions[currentViewId - 1]);
			case condenser:
				opSoft.setPumpOnOff(3, !pumpPositions[2]);
			default:
				break;
			}
			updateData();
		}
	}
	class IncrementButton extends Button
	{

		public IncrementButton(String imageName, int posX, int posY, int width, int height, int change) 
		{
			super(imageName, posX, posY, width, height);
			this.change = change;
		}
		
		@Override
		public void doAction()
		{
			try{
				switch(currentView)
				{
				case pump:
					opSoft.setPumpRpm(currentViewId, pumpRPMs[currentViewId - 1] + change);
					break;
				case reactor:
					opSoft.setControlRods(opSoft.getControlRodsPercentage() + change);
					break;
				case condenser:
					opSoft.setPumpRpm(3, pumpRPMs[2] + change);
					break;
				default:
					break;
				}
			} catch(java.lang.IllegalArgumentException e) { }
			
			updateData();
		}
		
		void setChange(int change)
		{
			this.change = change;
		}
		int change;
	}
	class RepairButton extends Button
	{

		public RepairButton(String imageName, int posX, int posY, int width, int height) 
		{
			super(imageName, posX, posY, width, height);
		}
		
		@Override
		public void doAction()
		{
			switch(currentView)
			{
			case turbine:
				opSoft.repairTurbine();
				break;
			case pump:
				opSoft.repairPump(currentViewId);
				break;
			case condenser:
				opSoft.repairPump(3);
				break;
			default:
				break;
			}
			updateData();
		}
	}
	
	public enum ViewState { pump, valve, turbine, reactor, condenser, scores, none }
	
	boolean [] valvePositions;
	boolean [] pumpPositions;
	boolean [] pumpFunctional;
	int [] pumpRPMs;
	
	IncrementButton increase;
	IncrementButton decrease;
	
	OnOffButton switchButton;
	RepairButton repair;
	
	OperatorSoftware opSoft;
	Renderer r;
	
	ArrayList<Text> displayedInfo;
	ViewState currentView;
	int currentViewId;
}
