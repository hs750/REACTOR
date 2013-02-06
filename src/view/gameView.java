package view;

import java.awt.FontMetrics;
import java.util.ArrayList;

import simulator.OperatorSoftware;
import simulator.PlantController;
import simulator.ReactorUtils;

public class gameView 
{
	Renderer r;
	GUIThread g;
	public gameView(OperatorSoftware opSoft)
	{
		
		this.opSoft = opSoft;
		r = new Renderer();
		displayedInfo = new ArrayList<Text>();
		updateData();
		step = new StepButton("Step.png", 400, 300, 100, 50);
		increase = new IncrementButton("Step.png", 100, 100, 20, 20, 5);
		increase.disable();
		decrease = new IncrementButton("Step.png", 100, 200, 20, 20, -5);
		decrease.disable();
		switchButton = new OnOffButton("Step.png", 400, 400, 100, 50);
		switchButton.disable();
		valve1 = new ViewSwitchButton("Step.png", 100, 300, 100, 50, ComponentViewState.valve, 1);
		valve2 = new ViewSwitchButton("Step.png", 100, 400, 100, 50, ComponentViewState.valve, 2);
		pump3 = new ViewSwitchButton("Step.png", 300, 100, 100, 50, ComponentViewState.pump, 3);
		pump2 = new ViewSwitchButton("Step.png", 300, 200, 100, 50, ComponentViewState.pump, 2);
		pump1 = new ViewSwitchButton("Step.png", 300, 300, 100, 50, ComponentViewState.pump, 1);
		reactor =  new ViewSwitchButton("Step.png", 300, 400, 100, 50, ComponentViewState.reactor, 0);
		condenser = new ViewSwitchButton("Step.png", 300, 500, 100, 50, ComponentViewState.condenser, 2);
		turbine = new ViewSwitchButton("Step.png", 400, 100, 100, 50, ComponentViewState.turbine, 0);
		restartSoftware = new RestartButton("Step.png", 400, 400, 100, 50);
		
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
		//pumpPositions = ;
		pumpRPMs = opSoft.getPumpRpms();
	}
	
	public static void main(String[] args)
	{
		PlantController plant = new PlantController(new ReactorUtils());
		OperatorSoftware opSoft = new OperatorSoftware(plant);
		gameView view = new gameView(opSoft);
		view.run();
	}
	public void switchComponentView(ComponentViewState state, int id)
	{
		currentView = state;
		currentViewId = id;
		displayedInfo = new ArrayList<Text>();
		updateData();
		FontMetrics met;
		switch(currentView)
		{
		case condenser:
			decrease.disable();
			increase.disable();
			switchButton.disable();
			displayedInfo.add(new Text("Condenser", 500, 300, 15));
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
			decrease.enable();
			increase.enable();
			switchButton.enable();
			
			displayedInfo.add(new Text("Pump " + currentViewId, 500, 300, 15));
			met = r.getMetrics(displayedInfo.get(0));
			
			
			displayedInfo.add(new Text("RPM", 15));
			displayedInfo.get(0).nextLine(displayedInfo.get(1), met);
			
			displayedInfo.get(1).allign(decrease, met);
			decrease.allign(increase);
			displayedInfo.add(new Text(Integer.toString(pumpRPMs[currentViewId - 1]), 15));
			increase.allign(displayedInfo.get(2));
			

			break;
		case reactor:
			decrease.disable();
			increase.disable();
			switchButton.disable();
			displayedInfo.add(new Text("Reactor", 500, 300, 15));
			met = r.getMetrics(displayedInfo.get(0));
			
			displayedInfo.add(new Text("Health: " + opSoft.getReactorHealth(), 15));
			displayedInfo.get(0).nextLine(displayedInfo.get(1), met);
			
			displayedInfo.add(new Text("Temperature: " + opSoft.getReactorTemperature(), 15));
			displayedInfo.get(1).nextLine(displayedInfo.get(2), met);
			
			displayedInfo.add(new Text("Pressure: " + opSoft.getReactorPressure(), 15));
			displayedInfo.get(2).nextLine(displayedInfo.get(3), met);
			
			displayedInfo.add(new Text("Water Volume: " + opSoft.getReactorWaterVolume(), 15));
			displayedInfo.get(3).nextLine(displayedInfo.get(4), met);
			
			displayedInfo.add(new Text("Control rod position: " + opSoft.getControlRodsPercentage(), 15));
			displayedInfo.get(4).nextLine(displayedInfo.get(5), met);
			
			break;
		case turbine:
			decrease.disable();
			increase.disable();
			switchButton.disable();
			displayedInfo.add(new Text("Turbine", 500, 300, 15));
			met = r.getMetrics(displayedInfo.get(0));
			
			displayedInfo.add(new Text("RPM: " + opSoft.getTurbineRpm(), 15));
			displayedInfo.get(0).nextLine(displayedInfo.get(1), met);
			
			displayedInfo.add(new Text("Power output: " + opSoft.getPowerOutput(), 15));
			displayedInfo.get(1).nextLine(displayedInfo.get(2), met);
			
			break;
		case valve:
			decrease.disable();
			increase.disable();
			switchButton.enable();
			displayedInfo.add(new Text("Valve " + currentViewId, 500, 300, 15));
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
	
	class ViewSwitchButton extends Button
	{
		public ViewSwitchButton(String imageName, int posX, int posY, int width, int height, ComponentViewState view, int id) 
		{
			super(imageName, posX, posY, width, height);
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
				else opSoft.setControlRods(opSoft.getControlRodsPercentage() + change);
			} catch(java.lang.IllegalArgumentException e) { }
			
			updateData();
			
		}
		
		void setChange(int change)
		{
			this.change = change;
		}
		int change;
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
				opSoft.setValve(currentViewId, !valvePositions[currentViewId -1]);//!valvePositions[currentViewId]);

			else opSoft.setPumpOnOff(currentViewId, !pumpPositions[currentViewId - 1]);
			updateData();
		}
	}
	
	void updateGraphics()
	{
		switchComponentView(currentView, currentViewId);
		r.queueForRendering(increase.getRenderable());
		r.queueForRendering(decrease.getRenderable());
		
		r.queueForRendering(switchButton.getRenderable());
		
		r.queueForRendering(pump1.getRenderable());
		r.queueForRendering(pump2.getRenderable());
		r.queueForRendering(pump3.getRenderable());
		r.queueForRendering(valve1.getRenderable());
		r.queueForRendering(valve2.getRenderable());
		r.queueForRendering(reactor.getRenderable());
		r.queueForRendering(condenser.getRenderable());
		r.queueForRendering(turbine.getRenderable());
		r.queueForRendering(step.getRenderable());
		r.queueForRendering(restartSoftware.getRenderable());
	}
	
	public enum ComponentViewState { pump, valve, turbine, reactor, condenser }
	
	ArrayList<Text> displayedInfo;
	ComponentViewState currentView;
	int currentViewId;
	boolean [] valvePositions;
	boolean [] pumpPositions;
	int [] pumpRPMs;
	
	OperatorSoftware opSoft;
	
	IncrementButton increase;
	IncrementButton decrease;
	
	OnOffButton switchButton;
	
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
}
