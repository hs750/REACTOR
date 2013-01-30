package simulator;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

public class GUI extends JFrame implements KeyListener {
	private enum State {Uninitialised, NewGame, Normal, AreYouSure, GameOver;}
	private enum AreYouSureCaller {Newgame, Savegame, Loadgame, Exit, NoAction;}

	 private State state = State.Uninitialised;
	 private AreYouSureCaller caller = AreYouSureCaller.NoAction;
	 private OperatorSoftware ctrlSoft;

	public GUI(PlantController control)
    {
    	super("REACTOR");
    	this.ctrlSoft = new OperatorSoftware(control);
        initWindow();
    }
	
	private void initWindow(){
		//TODO initialise the window to however it should look
		
		//Example of how an element of the GUI would get the data that it needs from the rest of the game.
		ctrlSoft.getPowerOutput();
		
		//Example of seting a pumps RPM via the operator software.
		ctrlSoft.setPumpRpm(1, 1000);
		
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
