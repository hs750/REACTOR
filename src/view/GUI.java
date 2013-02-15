package view;

import model.HighScore;
import simulator.OperatorSoftware;
import simulator.PlantController;
import simulator.ReactorUtils;
import view.SideBar.ViewState;

public class GUI 
{
	Renderer r;
	GUIThread g;
	public GUI(PlantController controller)
	{
		
		this.opSoft = new OperatorSoftware(controller);
		r = new Renderer();
		sideBar = new SideBar(r, opSoft);
		
		gameOver = false;
		
		test = new ParticleEmitter("graphics/particle.png", 13, 4,600, 500, 400, 2, -1, 0, -1, 4);
		
		step = new StepButton("graphics/step.png", 1030, 400, 100, 100);
		
		newGame = new NewGame(new Text("New game", 0, 0, 17), "graphics/genericButton.png", 25, 10, 140, 35);
		newGame.getRenderable().setDepthLevel(105);
		saveGame = new SaveGame(new Text("Save game", 0, 0, 17), "graphics/genericButton.png", 170, 10, 140, 35);
		saveGame.getRenderable().setDepthLevel(105);
		loadGame = new LoadGame(new Text("Load game", 0, 0, 17), "graphics/genericButton.png", 315, 10, 140, 35);
		loadGame.getRenderable().setDepthLevel(105);
		scoresButton = new ScoresButton(new Text("High scores", 0, 0, 17), "graphics/genericButton.png", 460, 10, 145, 35);
		scoresButton.getRenderable().setDepthLevel(105);
		
		radio = new RadioButton();
		
		restartSoftware = new RestartButton("graphics/restart.png", 1030, 500, 100, 100);
		
		background = new Renderable("graphics/background.png", 0, 0, 1024, 683, 0);
		gameOverSplash = new Renderable("graphics/gameover.png", 0, 0, 1024, 683, 103);
		gameOverSplash.setVisible(false);
		
		turbinePic = new Renderable("graphics/turbine.png", 486, 380, 113, 102, 102);
		reactorPic = new Renderable("graphics/reactor.png", 30, 200, 242, 300, 102);
		valve1Pic = new Renderable("graphics/valveUpper.png", 459, 234, 58, 43, 102);
		valve2Pic = new Renderable("graphics/valveLower.png", 302, 385, 69, 50, 102);
		condenserPic = new Renderable("graphics/condenser.png", 607, 99, 202, 226, 102);
		pump1Pic = new Renderable("graphics/pump.png", 581, 195, 54, 46, 103);
		pump2Pic = new Renderable("graphics/pump.png", 586, 291, 54, 46, 103);
		
		
		valve1 = new ViewSwitchButton("graphics/Selector.png", radio, valve1Pic.getPositionX(), valve1Pic.getPositionY(), valve1Pic.getWidth(), valve1Pic.getHeight(), ViewState.valve, 1);
		valve2 = new ViewSwitchButton("graphics/Selector.png", radio, valve2Pic.getPositionX(), valve2Pic.getPositionY(), valve2Pic.getWidth(), valve2Pic.getHeight(), ViewState.valve, 2);
		
		pump2 = new ViewSwitchButton("graphics/Selector.png", radio, pump2Pic.getPositionX(), pump2Pic.getPositionY(), pump2Pic.getWidth(), pump2Pic.getHeight(), ViewState.pump, 2);
		pump1 = new ViewSwitchButton("graphics/Selector.png", radio, pump1Pic.getPositionX(), pump1Pic.getPositionY(), pump1Pic.getWidth(), pump1Pic.getHeight(), ViewState.pump, 1);
		reactor =  new ViewSwitchButton("graphics/Selector.png", radio, reactorPic.getPositionX(), reactorPic.getPositionY(), reactorPic.getWidth(), reactorPic.getHeight(), ViewState.reactor, 0);
		condenser = new ViewSwitchButton("graphics/Selector.png", radio, condenserPic.getPositionX(), condenserPic.getPositionY(), condenserPic.getWidth(), condenserPic.getHeight(), ViewState.condenser, 2);
		turbine = new ViewSwitchButton("graphics/Selector.png", radio, turbinePic.getPositionX(), turbinePic.getPositionY(), turbinePic.getWidth(), turbinePic.getHeight(), ViewState.turbine, 0);
		
		radio.addButton(turbine);
		radio.addButton(valve1);
		radio.addButton(valve2);
		radio.addButton(pump1);
		radio.addButton(pump2);
		radio.addButton(reactor);
		radio.addButton(condenser);
		
		
		g = new GUIThread(r);
		r.registerButton(step);
		r.registerButton(valve1);
		r.registerButton(valve2);
		r.registerButton(pump1);
		r.registerButton(pump2);
		r.registerButton(restartSoftware);
		
		
		r.registerButton(reactor);
		r.registerButton(condenser);
		r.registerButton(turbine);
		r.registerButton(step);
		
		r.registerButton(newGame);
		r.registerButton(saveGame);
		r.registerButton(loadGame);
		r.registerButton(scoresButton);
		
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
				test.update(elapsed);
				elapsed -= 34;
				updateGraphics();
				
				r.repaint();
			}
		}
	}
	
	public Renderer getRenderer()
	{
		return r;
	}
	
	public static void main(String[] args)
	{
		PlantController plant = new PlantController(new ReactorUtils());
		GUI view = new GUI(plant);
		view.run();
	}
	
	public void reset()
	{
		radio.enableButtons();
		step.enable();
		restartSoftware.enable();
		gameOverSplash.setVisible(false);
		
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
			sideBar.updateData();
			if(opSoft.isGameOver())
			{
				gameOver = true;
				radio.disableButtons();
				step.disable();
				restartSoftware.disable();
				gameOverSplash.setVisible(true);
			}
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
		public ViewSwitchButton(String imageName, RadioButton radio, int posX, int posY, int width, int height, ViewState view, int id) 
		{
			super(imageName, posX, posY, width, height, radio);
			this.view = view;
			this.id = id;
		}
		@Override
		public void doAction()
		{
			sideBar.switchComponentView(view, id);
		}
		int id;
		ViewState view;
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
			reset();
		}
	}
	
	class LoadGame extends TextButton
	{
		public LoadGame(Text t, String imageName, int posX, int posY, int width,
				int height) {
			super(t, imageName, posX, posY, width, height);
		}
		@Override
		public void doAction()
		{
			reset();
			opSoft.loadGame();
		}
	}
	class SaveGame extends TextButton
	{
		public SaveGame(Text t, String imageName, int posX, int posY, int width,
				int height) {
			super(t, imageName, posX, posY, width, height);
		}
		@Override
		public void doAction()
		{
			opSoft.saveGame();
		}
	}
	
	class ScoresButton extends TextButton
	{
		public ScoresButton(Text t, String imageName, int posX, int posY, int width,
				int height) {
			super(t, imageName, posX, posY, width, height);
		}
		@Override
		public void doAction()
		{
			sideBar.switchComponentView(ViewState.scores, 0);
			radio.switchToggleOthers(-1);
		}
	}
	void updateGraphics()
	{
		
		sideBar.updateGraphics();
		r.queueForRendering(pump1);
		r.queueForRendering(pump2);
		r.queueForRendering(valve1);
		r.queueForRendering(valve2);
		r.queueForRendering(reactor);
		r.queueForRendering(condenser);
		r.queueForRendering(turbine);
		r.queueForRendering(step);
		r.queueForRendering(restartSoftware);
		
		
		r.queueForRendering(turbinePic);
		r.queueForRendering(reactorPic);
		r.queueForRendering(turbinePic);
		r.queueForRendering(pump1Pic);
		r.queueForRendering(pump2Pic);		
		r.queueForRendering(valve1Pic);
		r.queueForRendering(valve2Pic);
		
		r.queueForRendering(condenserPic);
		r.queueForRendering(background);
		r.queueForRendering(gameOverSplash);
		r.queueForRendering(newGame);
		r.queueForRendering(saveGame);
		r.queueForRendering(loadGame);
		r.queueForRendering(scoresButton);
		r.queueForRendering(test);
	}
	
	boolean gameOver;
	
	ParticleEmitter test;
	SideBar sideBar;
	NewGame newGame;
	LoadGame loadGame;
	SaveGame saveGame;
	ScoresButton scoresButton;
	
	OperatorSoftware opSoft;
	
	Renderable gameOverSplash;
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
	ViewSwitchButton valve1;
	ViewSwitchButton valve2;
	ViewSwitchButton reactor;
	ViewSwitchButton condenser;
	ViewSwitchButton turbine;
	StepButton step;
	RestartButton restartSoftware;
	
}
