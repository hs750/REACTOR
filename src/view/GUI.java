package view;

import java.awt.FontMetrics;
import java.util.ArrayList;

import simulator.OperatorSoftware;
import simulator.PlantController;
import simulator.ReactorUtils;

public class GUI 
{
	Renderer r;
	GUIThread g;
	public GUI(PlantController controller)
	{
		
		this.opSoft = new OperatorSoftware(controller);
		r = new Renderer();
		displayedInfo = new ArrayList<Text>();
		updateData();
		step = new StepButton("Step.png", 1030, 300, 100, 100);
		increase = new IncrementButton("graphics/increase.png", 100, 100, 20, 20, 5);
		increase.disable();
		decrease = new IncrementButton("graphics/decrease.png", 100, 200, 20, 20, -5);
		decrease.disable();
		switchButton = new OnOffButton("Step.png", 400, 400, 60, 40);
		repair = new RepairButton("graphics/repair.png", 400, 400, 60, 40);
		repair.disable();
		switchButton.disable();
		
		newGame = new NewGame(new Text("New game", 0, 0, 18), "Step.png", 200, 50, 150, 50);
		radio = new RadioButton();
		
		restartSoftware = new RestartButton("graphics/restart.png", 1030, 400, 100, 100);
		
		background = new Renderable("graphics/background.png", 0, 0, 1024, 683, 0);
		turbinePic = new Renderable("graphics/turbine.png", 486, 380, 113, 102, 102);
		reactorPic = new Renderable("graphics/reactor.png", 30, 200, 242, 300, 102);
		valve1Pic = new Renderable("graphics/turbine.png", 0, 0, 102, 113, 102);
		valve2Pic = new Renderable("graphics/turbine.png", 0, 0, 102, 113, 102);
		condenserPic = new Renderable("graphics/condenser.png", 607, 99, 202, 226, 102);
		pump1Pic = new Renderable("graphics/pump.png", 581, 195, 54, 46, 103);
		pump2Pic = new Renderable("graphics/pump.png", 586, 291, 54, 46, 103);
		pump3Pic = new Renderable("graphics/pump.png", 400, 100, 54, 46, 103);
		
		valve1 = new ViewSwitchButton("graphics/Selector.png", radio, valve1Pic.getPositionX(), valve1Pic.getPositionY(), valve1Pic.getWidth(), valve1Pic.getHeight(), ComponentViewState.valve, 1);
		valve2 = new ViewSwitchButton("graphics/Selector.png", radio, valve2Pic.getPositionX(), valve2Pic.getPositionY(), valve2Pic.getWidth(), valve2Pic.getHeight(), ComponentViewState.valve, 2);
		pump3 = new ViewSwitchButton("graphics/Selector.png", radio, pump3Pic.getPositionX(), pump3Pic.getPositionY(), pump3Pic.getWidth(), pump3Pic.getHeight(), ComponentViewState.pump, 3);
		pump2 = new ViewSwitchButton("graphics/Selector.png", radio, pump2Pic.getPositionX(), pump2Pic.getPositionY(), pump2Pic.getWidth(), pump2Pic.getHeight(), ComponentViewState.pump, 2);
		pump1 = new ViewSwitchButton("graphics/Selector.png", radio, pump1Pic.getPositionX(), pump1Pic.getPositionY(), pump1Pic.getWidth(), pump1Pic.getHeight(), ComponentViewState.pump, 1);
		reactor =  new ViewSwitchButton("graphics/Selector.png", radio, reactorPic.getPositionX(), reactorPic.getPositionY(), reactorPic.getWidth(), reactorPic.getHeight(), ComponentViewState.reactor, 0);
		condenser = new ViewSwitchButton("graphics/Selector.png", radio, condenserPic.getPositionX(), condenserPic.getPositionY(), condenserPic.getWidth(), condenserPic.getHeight(), ComponentViewState.condenser, 2);
		turbine = new ViewSwitchButton("graphics/Selector.png", radio, turbinePic.getPositionX(), turbinePic.getPositionY(), turbinePic.getWidth(), turbinePic.getHeight(), ComponentViewState.turbine, 0);
		
		g = new GUIThread(r);
		r.registerButton(step);
		r.registerButton(valve1);
		r.registerButton(valve2);
		r.registerButton(pump1);
		r.registerButton(pump2);
		r.registerButton(pump3);
		r.registerButton(restartSoftware);
		r.registerButton(increase);
		r.registerButton(decrease);
		
		r.registerButton(switchButton);
		
		r.registerButton(reactor);
		r.registerButton(condenser);
		r.registerButton(turbine);
		r.registerButton(step);
		r.registerButton(repair);
		r.registerButton(newGame);
		currentView = ComponentViewState.reactor;
		currentViewId = 0;
	}
		
	
	public void run()
	{
		g.run();
		
		Timer time = new Timer();
		time.stamp();
		long elapsed = 0;

		while(true)
		{
			time.stamp();
			elapsed += time.getDelta();
			if(elapsed >= 34)
			{
				elapsed -= 34;
				updateGraphics();
				for(Text t : displayedInfo)
				{
					r.queueForRendering(t);
				}
				
				r.repaint();
			}
		}
	}
	
	public Renderer getRenderer()
	{
		return r;
	}
	
	public void updateData()
	{
		valvePositions = opSoft.getValvePositions();
		pumpPositions = opSoft.arePumpsOn();
		pumpRPMs = opSoft.getPumpRpms();
		pumpFunctional = opSoft.arePumpsFunctional();
	}
	
	public static void main(String[] args)
	{
		PlantController plant = new PlantController(new ReactorUtils());
		GUI view = new GUI(plant);
		view.run();
	}
	public void switchComponentView(ComponentViewState state, int id)
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
		case condenser:
			decrease.disable();
			increase.disable();
			repair.disable();
			switchButton.disable();
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
	
	class StepButton extends Button
	{

		public StepButton(String imageName, int posX, int posY, int width, int height) 
		{
			super(imageName, posX, posY, width, height);
			
		}
		@Override
		public void doAction()
		{
			opSoft.step(1);
			updateData();
		}
	}
	
	class RestartButton extends Button
	{

		public RestartButton(String imageName, int posX, int posY, int width, int height) 
		{
			super(imageName, posX, posY, width, height);
			
		}
		@Override
		public void doAction()
		{
			opSoft.rebootOS();
		}
	} 
	
	class ViewSwitchButton extends ToggleButton
	{
		public ViewSwitchButton(String imageName, RadioButton radio, int posX, int posY, int width, int height, ComponentViewState view, int id) 
		{
			super(imageName, posX, posY, width, height, radio);
			this.view = view;
			this.id = id;
		}
		@Override
		public void doAction()
		{
			switchComponentView(view, id);
		}
		int id;
		ComponentViewState view;
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
				if(currentView == ComponentViewState.pump)
					opSoft.setPumpRpm(currentViewId, pumpRPMs[currentViewId - 1] + change);
				else 
					opSoft.setControlRods(opSoft.getControlRodsPercentage() + change);
			} catch(java.lang.IllegalArgumentException e) { }
			
			updateData();
			System.out.println(opSoft.getControlRodsPercentage());
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
			if(currentView ==  ComponentViewState.turbine)
				opSoft.repairTurbine();
			else
			opSoft.repairPump(currentViewId);
			updateData();

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
			if(currentView == ComponentViewState.valve)
				opSoft.setValve(currentViewId, !valvePositions[currentViewId - 1]);
			else opSoft.setPumpOnOff(currentViewId, !pumpPositions[currentViewId - 1]);
			updateData();
		}
	}
	
	class NewGame extends TextButton
	{

		public NewGame(Text t, String imageName, int posX, int posY, int width,
				int height) {
			super(t, imageName, posX, posY, width, height);
		}
		@Override
		public void doAction()
		{
			PlantController plant = new PlantController(new ReactorUtils());
			opSoft = new OperatorSoftware(plant);
		}
	}
	
	void updateGraphics()
	{
		switchComponentView(currentView, currentViewId);
		
		r.queueForRendering(increase.getRenderable());
		r.queueForRendering(decrease.getRenderable());
		
		r.queueForRendering(switchButton.getRenderable());
		
		r.queueForRendering(pump1);
		r.queueForRendering(pump2);
		r.queueForRendering(pump3);
		r.queueForRendering(valve1);
		r.queueForRendering(valve2);
		r.queueForRendering(reactor);
		r.queueForRendering(condenser);
		r.queueForRendering(turbine);
		r.queueForRendering(step);
		r.queueForRendering(restartSoftware);
		r.queueForRendering(repair);
		
		r.queueForRendering(turbinePic);
		r.queueForRendering(reactorPic);
		r.queueForRendering(turbinePic);
		r.queueForRendering(pump1Pic);
		r.queueForRendering(pump2Pic);
		r.queueForRendering(pump3Pic);
		
		r.queueForRendering(valve1Pic);
		r.queueForRendering(valve2Pic);
		
		r.queueForRendering(condenserPic);
		r.queueForRendering(background);
		r.queueForRendering(newGame);
	}
	
	public enum ComponentViewState { pump, valve, turbine, reactor, condenser }
	
	ArrayList<Text> displayedInfo;
	ComponentViewState currentView;
	int currentViewId;
	boolean [] valvePositions;
	boolean [] pumpPositions;
	boolean [] pumpFunctional;
	int [] pumpRPMs;
	
	NewGame newGame;
	
	OperatorSoftware opSoft;
	
	IncrementButton increase;
	IncrementButton decrease;
	
	OnOffButton switchButton;
	
	
	Renderable background;
	
	Renderable reactorPic;
	Renderable turbinePic;
	Renderable pump1Pic;
	Renderable pump2Pic;
	Renderable pump3Pic;
	
	Renderable valve1Pic;
	Renderable valve2Pic;
	
	Renderable condenserPic;
	
	RadioButton radio;
	ViewSwitchButton pump1;
	ViewSwitchButton pump2;
	ViewSwitchButton pump3;
	ViewSwitchButton valve1;
	ViewSwitchButton valve2;
	ViewSwitchButton reactor;
	ViewSwitchButton condenser;
	ViewSwitchButton turbine;
	StepButton step;
	RestartButton restartSoftware;
	RepairButton repair;
}
