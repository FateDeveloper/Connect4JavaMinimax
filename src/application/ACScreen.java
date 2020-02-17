package application;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

/**
 * <p>Classe Astratta per le interfacce grafiche (Non in game)</p>
 * @author Ciro Cozzolino
 */

public abstract class ACScreen { //Classe astratta per le interfacce grafiche non in game.

	protected MainApp _oMainApp; //Riferimento al main
	
	/**
	 * 
	 * @param oMainApp Riferimento al main.
	 * @param szLoaderPath Path del file fxml.
	 */
	
	public ACScreen(MainApp oMainApp, String szLoaderPath) 
	{
		_oMainApp = oMainApp;
		try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource(szLoaderPath)); //Cerca la schermata nel file fxml
            AnchorPane oMenu = (AnchorPane) loader.load(); //La carica
            _oMainApp.getRootLayout().setCenter(oMenu); //La seleziona come pannello centrale del Root Layout
            this.initController(loader); //funzione astratta per inizializzare la classe input controller della stessa;
            
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	protected abstract void initController(FXMLLoader oLoader); //funzione per inizializzare la classe controller
	
	public abstract void gotoNextMenu(); //funzione astratta per passare alla prossima schermata
	
	protected abstract void unload(); //funzione astratta per la chiusura della schermata
	
}
