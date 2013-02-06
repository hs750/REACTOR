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
		valve1 = new ViewSwitchButton("Step.png", 100, 300, 100, 50, ComponentViewState.valve, 0);
		valve2 = new ViewSwitchButton("Step.png", 100, 400, 100, 50, ComponentViewState.valve, 1);
		pump3 = new ViewSwitchButton("Step.png", 300, 100, 100, 50, ComponentViewState.pump, 2);
		pump2 = new ViewSwitchButton("Step.png", 300, 200, 100, 50, ComponentViewState.pump, 1);
		pump1 = new ViewSwitchButton("Step.png", 300, 300, 100, 50, ComponentViewState.pump, 0);
		reactor =  new ViewSwitchButton("Step.png", 300, 400, 100, 50, ComponentViewState.reactor, 0);
		condenser = new ViewSwitchButton("Step.png", 300, 500, 100, 50, ComponentViewState.condenser, 2);
		turbine = new ViewSwitchButton("Step.png", 300, 600, 100, 50, ComponentViewState.turbine, 0);
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
		//switchComponentView(ComponentViewState.reactor, 0);
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
		Text temp;
		FontMetrics met;
		switch(currentView)
		{
		case condenser:
			decrease.disable();
			increase.disable();
			switchButton.disable();
			temp = new Text("Condenser", 500, 300, 15);
			met = r.getMetrics(temp);
			displayedInfo.add(temp);
			temp = new Text("Pressure: " + opSoft.getCondenserPressure(), 15);
			met = r.getMetrics(temp);
			displayedInfo.get(0).nextLine(temp, met);
			displayedInfo.add(temp);
			temp = new Text("Temperature: " + opSoft.getCondenserTemperature(), 15);
			displayedInfo.get(1).nextLine(temp, met);
			displayedInfo.add(temp);
			temp =new Text("Health: " + opSoft.getCondenserHealth(), 15);
			displayedInfo.get(2).nextLine(temp, met);
			displayedInfo.add(temp);
			temp = new Text("Water Volume: " + opSoft.getCondenserWaterVolume(), 15);
			displayedInfo.get(3).nextLine(temp, met);
			displayedInfo.add(temp);
			 
			break;
		case pump:
			decrease.enable();
			increase.enable();
			switchButton.enable();
			temp = new Text("Pump " + currentViewId, 500, 300, 15);
			met = r.getMetrics(temp);
			
			displayedInfo.add(temp);
			temp = new Text("RPM", 15);
			displayedInfo.get(0).nextLine(temp, met);
			displayedInfo.add(temp);
			temp.allign(decrease, met);
			decrease.allign(increase);
			increase.allign(temp);
			temp = new Text((String)(" " + pumpRPMs[currentViewId]), 15);
			displayedInfo.add(temp);
			break;
		case reactor:
			decrease.disable();
			increase.disable();
			switchButton.disable();
			temp = new Text("Reactor", 500, 300, 15);
			met = r.getMetrics(temp);
			displayedInfo.add(temp);
			temp = new Text("Health: " + opSoft.getReactorHealth(), 15);
			displayedInfo.get(0).nextLine(temp, met);
			displayedInfo.add(temp);
			temp = new Text("Temperature: " + opSoft.getReactorTemperature(), 15);
			displayedInfo.get(1).nextLine(temp, met);
			displayedInfo.add(temp);
			temp = new Text("Pressure: " + opSoft.getReactorPressure(), 15);
			displayedInfo.get(2).nextLine(temp, met);
			displayedInfo.add(temp);
			temp = new Text("Water Volume: " + opSoft.getReactorWaterVolume(), 15);
			displayedInfo.get(3).nextLine(temp, met);
			displayedInfo.add(temp);
			temp = new Text("Control rod position: " + opSoft.getControlRodsPercentage(), 15);
			displayedInfo.get(4).nextLine(temp, met);
			displayedInfo.add(temp);
		case turbine:
			decrease.disable();
			increase.disable();
			switchButton.disable();
			temp = new Text("Turbine", 500, 300, 15);
			met = r.getMetrics(temp);
			displayedInfo.add(temp);
			temp = new Text("RPM: " + opSoft.getTurbineRpm(), 15);
			displayedInfo.get(0).nextLine(temp, met);
			displayedInfo.add(temp);
			temp = new Text("Power output: " + opSoft.getPowerOutput(), 15);
			displayedInfo.get(1).nextLine(temp, met);
			displayedInfo.add(temp);
			break;
		case valve:
			decrease.disable();
			increase.disable();
			switchButton.enable();
			temp = new Text("Valve " + currentViewId, 500, 300, 15);
			met = r.getMetrics(temp);
			displayedInfo.add(temp);
			if(valvePositions[currentViewId])
				temp = new Text("State: Open", 15);
			else  temp = new Text("State: Closed", 15);
			displayedInfo.get(0).nextLine(temp, met);
			displayedInfo.add(temp);
			temp.nextLine(switchButton, met);
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
			if(currentView == ComponentViewState.pump)
				opSoft.setPumpRpm(currentViewId, pumpRPMs[currentViewId] + change);
			else opSoft.setControlRods(opSoft.getControlRodsPercentage() + change);
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
				opSoft.setValve(currentViewId, !valvePositions[currentViewId]);
			else opSoft.setPumpOnOff(currentViewId, !pumpPositions[currentViewId]);
			updateData();
		}
	}
	
	void updateGraphics()
	{
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
