package view;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JOptionPane;

import simulator.OperatorSoftware;
import simulator.PlantController;
import simulator.ReactorUtils;
import view.SideBar.ViewState;

/**
 * Responsible for initializing the game look and feel, hooking up buttons to OS messages and running the main game loop.
 * @author Tadas
 *
 */
public class GUI 
{
	Renderer r;
	GUIThread g;
	
	/**
	 * Initializes all the graphics and UI side of things
	 * @param controller reference to the plant controller that will be represented by the GUI.
	 */
	public GUI(PlantController controller)
	{
		
		this.opSoft = new OperatorSoftware(controller);
		r = new Renderer();
		sideBar = new SideBar(r, opSoft);
		
		gameOver = false;
		
		turbineSmoke = new ParticleEmitter("graphics/particle.png", 13, 4,600, 500, 400, 2, -1, 0, -1, 4);
		turbineSmoke.disable();
		pump2Smoke = new ParticleEmitter("graphics/particle.png", 10, 4, 800, 592, 200, 2, -1, 0, -1, 4);
		turbineSmoke.disable();
		pump1Smoke = new ParticleEmitter("graphics/particle.png", 10, 4, 700, 592, 300, 2, -1, 0, -1, 4);
		turbineSmoke.disable();
		steam = new ParticleEmitter("graphics/particle.png", 15, 1, 2000, 690, 100, 2, -1, 0, -1, 5);
		steam.setVariedXSpawn(100);
		step = new StepButton("graphics/step.png", 1030, 400, 100, 100);
		
		newGame = new NewGame(new Text("New game", 0, 0, 17), "graphics/genericButton.png", 25, 10, 140, 35);
		newGame.getRenderable().setDepthLevel(105);
		newGame.getText().setColor(Color.white);
		saveGame = new SaveGame(new Text("Save game", 0, 0, 17), "graphics/genericButton.png", 170, 10, 140, 35);
		saveGame.getRenderable().setDepthLevel(105);
		saveGame.getText().setColor(Color.white);
		loadGame = new LoadGame(new Text("Load game", 0, 0, 17), "graphics/genericButton.png", 315, 10, 140, 35);
		loadGame.getRenderable().setDepthLevel(105);
		loadGame.getText().setColor(Color.white);
		scoresButton = new ScoresButton(new Text("High scores", 0, 0, 17), "graphics/genericButton.png", 460, 10, 145, 35);
		scoresButton.getText().setColor(Color.white);
		
		scoresButton.getRenderable().setDepthLevel(105);
		r.setDefaultFontName("Impact");
		radio = new RadioButton();
		
		restartSoftware = new RestartButton("graphics/restart.png", 1030, 500, 100, 100);
		
		sidebarPic = new Renderable("graphics/sidebar.png", 1024, 0, 246, 655, 0);
		background = new Renderable("graphics/background.png", 0, 0, 1024, 683, 0);
		gameOverSplash = new Renderable("graphics/gameover.png", 0, 0, 1024, 683, 103);
		gameOverSplash.setVisible(false);
		
		turbinePic = new Renderable("graphics/turbine.png", 486, 380, 113, 102, 102);
		reactorPic = new Renderable("graphics/reactor.png", 30, 200, 242, 300, 102);
		valve1Pic = new Renderable("graphics/valveUpper.png", 459, 234, 58, 43, 102);
		valve2Pic = new Renderable("graphics/valveLower.png", 302, 385, 69, 50, 102);
		condenserPic = new Renderable("graphics/condenser.png", 607, 99, 202, 226, 102);
		pump1Pic = new Renderable("graphics/pump.png", 570, 200, 54, 46, 103);
		pump2Pic = new Renderable("graphics/pump.png", 586, 291, 54, 46, 103);
		
		
		valve1 = new ViewSwitchButton("graphics/Selector.png", radio, valve1Pic.getPositionX(), valve1Pic.getPositionY(), valve1Pic.getWidth(), valve1Pic.getHeight(), ViewState.valve, 1);
		valve1.getRenderable().setAlpha(0.3f);
		valve2 = new ViewSwitchButton("graphics/Selector.png", radio, valve2Pic.getPositionX(), valve2Pic.getPositionY(), valve2Pic.getWidth(), valve2Pic.getHeight(), ViewState.valve, 2);
		valve2.getRenderable().setAlpha(0.3f);
		
		pump2 = new ViewSwitchButton("graphics/Selector.png", radio, pump2Pic.getPositionX(), pump2Pic.getPositionY(), pump2Pic.getWidth(), pump2Pic.getHeight(), ViewState.pump, 2);
		pump2.getRenderable().setAlpha(0.3f);
		pump1 = new ViewSwitchButton("graphics/Selector.png", radio, pump1Pic.getPositionX(), pump1Pic.getPositionY(), pump1Pic.getWidth(), pump1Pic.getHeight(), ViewState.pump, 1);
		pump1.getRenderable().setAlpha(0.3f);
		reactor =  new ViewSwitchButton("graphics/Selector.png", radio, reactorPic.getPositionX(), reactorPic.getPositionY(), reactorPic.getWidth(), reactorPic.getHeight(), ViewState.reactor, 0);
		reactor.getRenderable().setAlpha(0.3f);
		condenser = new ViewSwitchButton("graphics/Selector.png", radio, condenserPic.getPositionX(), condenserPic.getPositionY(), condenserPic.getWidth(), condenserPic.getHeight(), ViewState.condenser, 2);
		condenser.getRenderable().setAlpha(0.3f);
		turbine = new ViewSwitchButton("graphics/Selector.png", radio, turbinePic.getPositionX(), turbinePic.getPositionY(), turbinePic.getWidth(), turbinePic.getHeight(), ViewState.turbine, 0);
		turbine.getRenderable().setAlpha(0.3f);
		
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
		
	/**
	 * Main game loop. Tracks the time that has passed between each cycle. Asks the user to enter a new operator name first.
	 * This function must be run to actually start rendering and updating the window
	 */
	public void run()
	{
		g.run();
		
		Timer time = new Timer();
		
		long elapsed = 0;
		operatorName = (String)JOptionPane.showInputDialog(
                g.window.frame,
                "Please enter new plant operator name.",
                "New game",
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                "Operator name");
		opSoft.newGame(operatorName);
		time.stamp();
		while(true)
		{
			time.stamp();
			elapsed += time.getDelta();
			if(elapsed >= 34) //In milliseconds, which means ~30 fps
			{
				steam.update(elapsed); //Condenser always smokes
				//Smoke effects work only if the corresponding components are broken
				if(!opSoft.isTurbineFunctional() )
				{
					turbineSmoke.enable();
					turbineSmoke.update(elapsed);
				}
				else turbineSmoke.disable();
				if(!opSoft.arePumpsFunctional()[1])
				{
					pump1Smoke.enable();
					pump1Smoke.update(elapsed);
				}
				else pump1Smoke.disable();
					
				if(!opSoft.arePumpsFunctional()[0])
				{
					pump2Smoke.enable();
					pump2Smoke.update(elapsed);
				}
				
				else pump2Smoke.disable();
				
				elapsed -= 34;
				updateGraphics();
				
				r.repaint();
			}
		}
	}
	
	/**
	 *  Returns a reference to the renderer, ideally does not need to be used at all.
	 * @return reference to the main renderer
	 */
	public Renderer getRenderer()
	{
		return r;
	}
	

	/**
	 * Resets a bunch of internal values that get set when the game over screen is reached. 
	 * Gets called whenever a new game is started or loaded. 
	 */
	void reset()
	{
		radio.enableButtons();
		step.enable();
		restartSoftware.enable();
		steam.enable();
		gameOverSplash.setVisible(false);
		gameOver = false;
		
	}
	
	/**
	 * Implements Step button functionality. Whenever the button is clicked a call is made to the simulation code.
	 * After every step checks are made to make sure that the game is not over.
	 * @author Tadas
	 *
	 */
	class StepButton extends Button
	{
		/**
		 * Initiliazes the button
		 * @param imageName name of the image associated with the button
		 * @param posX horizontal coordinates of the upper left corner of the button.
		 * @param posY vertical coordinates of the upper left corner of the button.
		 * @param width button's width. will automaticaly resize the supplied picture based on this value.
		 * @param height button's height. will automatically resize the supplied picture based on this value.
		 */
		public StepButton(String imageName, int posX, int posY, int width, int height) 
		{
			super(imageName, posX, posY, width, height);
			
		}
		
		/**
		 * Gets called when the button is clicked. 'Steps' the simulation, update the interface with the new data and checks if the game is over.
		 */
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
				steam.disable();
			}
		}
	}
	
	/**
	 * Button for restarting the operator software.
	 * @author Tadas
	 *
	 */
	class RestartButton extends Button
	{
		/**
		 * Initiliazes the button
		 * @param imageName name of the image associated with the button
		 * @param posX horizontal coordinates of the upper left corner of the button.
		 * @param posY vertical coordinates of the upper left corner of the button.
		 * @param width button's width. will automaticaly resize the supplied picture based on this value.
		 * @param height button's height. will automatically resize the supplied picture based on this value.
		 */
		public RestartButton(String imageName, int posX, int posY, int width, int height) 
		{
			super(imageName, posX, posY, width, height);
			
		}
		@Override
		/**
		 * Restarts the operator software. Called whenever the button is clicked.
		 */
		public void doAction()
		{
			opSoft.rebootOS();
		}
	} 
	/**
	 * These buttons are used for changing the content to be displayed in the sidebar.
	 * Normally tied to certain components.
	 * @author Tadas
	 *
	 */
	class ViewSwitchButton extends ToggleButton
	{
		/**
		 * Initiliazes the button
		 * @param imageName name of the image associated with the button
		 * @param posX horizontal coordinates of the upper left corner of the button.
		 * @param posY vertical coordinates of the upper left corner of the button.
		 * @param width button's width. will automaticaly resize the supplied picture based on this value.
		 * @param height button's height. will automatically resize the supplied picture based on this value.
		 * @param view the type of new view
		 * @param id used for types like pumps and valves to recognise which component is being interacted with
		 */
		public ViewSwitchButton(String imageName, RadioButton radio, int posX, int posY, int width, int height, ViewState view, int id) 
		{
			super(imageName, posX, posY, width, height, radio);
			this.view = view;
			this.id = id;
		}
		/**
		 * Called whenever the buttons is clicked.
		 * Informs the sidebar that it needs to change its data.
		 */
		@Override
		public void doAction()
		{
			sideBar.switchComponentView(view, id);
		}
		int id;
		ViewState view;
	}
	/**
	 * Button makes the operator software create a new power plant.
	 * @author Tadas
	 *
	 */
	class NewGame extends TextButton
	{
		/**
		 * Initiliazes the button
		 * @param t Text object associated with the button.
		 * @param imageName name of the image associated with the button
		 * @param posX horizontal coordinates of the upper left corner of the button.
		 * @param posY vertical coordinates of the upper left corner of the button.
		 * @param width button's width. will automaticaly resize the supplied picture based on this value.
		 * @param height button's height. will automatically resize the supplied picture based on this value.
		 */
		public NewGame(Text t, String imageName, int posX, int posY, int width,
				int height) {
			super(t, imageName, posX, posY, width, height);
		}
		@Override
		public void doAction()
		{
			
			reset();
			operatorName = (String)JOptionPane.showInputDialog(
                    g.window.frame,
                    "Please enter new plant operator name.",
                    "New game",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    null,
                    "Operator name");
			
			opSoft.newGame(operatorName);
			sideBar.updateData();
			
		}
	}
	
	/**
	 * Button object. When clicked makes the operator software load a previously saved instance of the game.
	 * @author Tadas
	 *
	 */
	class LoadGame extends TextButton
	{
		/**
		 * Initiliazes the button
		 * @param t Text object associated with the button.
		 * @param imageName name of the image associated with the button
		 * @param posX horizontal coordinates of the upper left corner of the button.
		 * @param posY vertical coordinates of the upper left corner of the button.
		 * @param width button's width. will automaticaly resize the supplied picture based on this value.
		 * @param height button's height. will automatically resize the supplied picture based on this value.
		 */
		public LoadGame(Text t, String imageName, int posX, int posY, int width,
				int height) {
			super(t, imageName, posX, posY, width, height);
		}
		/**
		 * Called whenever the button is clicked.
		 * Loads a new instance of the game.
		 */
		@Override
		public void doAction()
		{
			reset();
			operatorName = opSoft.getOperatorName();
			opSoft.loadGame();
		}
	}
	/**
	 * Button object. Whenever the button is clicked it makes the operator software save the current state of the game to a save file.
	 * @author Tadas
	 *
	 */
	class SaveGame extends TextButton
	{
		/**
		 * Initiliazes the button
		 * @param t Text object associated with the button.
		 * @param imageName name of the image associated with the button
		 * @param posX horizontal coordinates of the upper left corner of the button.
		 * @param posY vertical coordinates of the upper left corner of the button.
		 * @param width button's width. will automaticaly resize the supplied picture based on this value.
		 * @param height button's height. will automatically resize the supplied picture based on this value.
		 */
		public SaveGame(Text t, String imageName, int posX, int posY, int width,
				int height) {
			super(t, imageName, posX, posY, width, height);
		}
		/**
		 * Called whenever the button is clicked.
		 * Saves the game state to a file.
		 */
		@Override
		public void doAction()
		{
			opSoft.saveGame();
		}
	}
	/**
	 * Button object. Switches the sidebar view to the highscore list.
	 * @author Tadas
	 *
	 */
	class ScoresButton extends TextButton
	{
		/**
		 * Initiliazes the button
		 * @param t Text object associated with the button.
		 * @param imageName name of the image associated with the button
		 * @param posX horizontal coordinates of the upper left corner of the button.
		 * @param posY vertical coordinates of the upper left corner of the button.
		 * @param width button's width. will automaticaly resize the supplied picture based on this value.
		 * @param height button's height. will automatically resize the supplied picture based on this value.
		 */
		public ScoresButton(Text t, String imageName, int posX, int posY, int width,
				int height) {
			super(t, imageName, posX, posY, width, height);
		}
		/**
		 * Called whenever the button is clicked. Switches the sidebar view to highscores.
		 */
		@Override
		public void doAction()
		{
			sideBar.switchComponentView(ViewState.scores, 0);
			radio.switchToggleOthers(-1); //Deselects all highlighted components
		}
	}
	/**
	 * Adds all the objects that need to be rendered to the rendering queue.
	 * Automatically called in the run() function every frame.
	 */
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
		r.queueForRendering(sidebarPic);
		if(!gameOver)
		{
			r.queueForRendering(turbineSmoke);
			r.queueForRendering(pump1Smoke);
			r.queueForRendering(pump2Smoke);
			r.queueForRendering(steam);
		}
		if(operatorName != null && operatorName.length() != 0)
			r.queueForRendering(new Text ("Welcome, " +  operatorName + '!', 1030, 15, 15));
	}
	
	boolean gameOver;
	String operatorName;
	ParticleEmitter turbineSmoke;
	ParticleEmitter pump1Smoke;
	ParticleEmitter pump2Smoke;
	ParticleEmitter steam;
	
	SideBar sideBar;
	NewGame newGame;
	LoadGame loadGame;
	SaveGame saveGame;
	ScoresButton scoresButton;
	
	OperatorSoftware opSoft;
	
	Renderable gameOverSplash;
	Renderable background;
	Renderable sidebarPic;
	
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
