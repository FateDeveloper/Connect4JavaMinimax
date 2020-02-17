package application.viewcontroller;

import java.io.File;

import application.CGameMenu;
import application.CSaveDataHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * <p>Classe Input Controller della schermata Nuova/Carica Partita</p>
 * @author Ciro Cozzolino
 */

public class ICCGameMenu { //Classe per la gestione dell'input control della schermata di selezione nuova partita e carica partita

	private CGameMenu _oMainClass;
	
	@FXML
	private Label _oLabelError1;
	
	@FXML
	private Label _oLabelError2;
	
	File _oFile;
	
	public ICCGameMenu() 
	{
		
	}
	
	@FXML
	private void initialize() 
	{
		_oLabelError1.setText("");
		_oLabelError2.setText("");
	}
	
	@FXML
	private void onNewGameButt()
	{
		_oMainClass.gotoNextMenu();
	}
	
	@FXML
	private void onLoadGameButt() //Funzione assegnata al click di carica partita
	{
		if (!CSaveDataHandler.getIstance().existAndIsNotGameOver()) //Se il salvataggio è game over allora stampa a schermo che non ci sono salvataggi disponibili per l utente
		{
			_oLabelError1.setText("Non ci sono salvataggi disponibili per");
			_oLabelError2.setText(_oMainClass.getUserName());
		}else 
		{
			_oMainClass.gotoNextMenu(CSaveDataHandler.getIstance().loadGame()); //Altrimenti carica la partita
		}
	}
	
	public void setMainClass(CGameMenu oMainClass) 
	{
		this._oMainClass = oMainClass;
		initFile();
	}
	
	private void initFile() 
	{
		this._oMainClass.initSaveData(); //Inizializza il salvataggio
	}
}
