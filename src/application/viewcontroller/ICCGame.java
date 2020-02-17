package application.viewcontroller;

import application.CGame;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

/**
 * <p>Classe Input Controller della schermata di gioco</p>
 * @author Ciro Cozzolino
 */

public class ICCGame { //Classe per gestire l'input control della schermata di gioco
	CGame _oMainClass;
	
	public ICCGame(CGame oMainClass) {
		_oMainClass = oMainClass;
		initInput();
		
	}
	/**
	 * <p>Funzione per inizializzare l'input</p>
	 */
	private void initInput() 
	{
		EventHandler<MouseEvent> oMouseMove = new EventHandler<MouseEvent>() //Event handler della gestione del movimento del mouse
		{

			@Override
			public void handle(MouseEvent arg0) {
				_oMainClass.setMouseMove(arg0.getX(), arg0.getY());
				
			}
			
		};
		
		EventHandler<MouseEvent> oMouseClick = new EventHandler<MouseEvent>() //Event handler della gestione del click del mouse
		{

			@Override
			public void handle(MouseEvent arg0) {
				_oMainClass.setMouseClick();
				
			}
			
		};
		
		_oMainClass.getMainApp().getRootScene().setOnMouseMoved(oMouseMove); //Applica Event Handler
        
		_oMainClass.getMainApp().getRootScene().setOnMouseClicked(oMouseClick); //Applica Event Handler
	}
	
	public void unload() //Rimuovi event handler
	{
		_oMainClass.getMainApp().getRootScene().setOnMouseMoved(null);
		_oMainClass.getMainApp().getRootScene().setOnMouseClicked(null);
	}
}
