package application.viewcontroller;

import application.CGameModeMenu;
import application.EGameMode;
import javafx.fxml.FXML;

public class ICCGameModeMenu {
	
	/**
	 * <p>Classe Input Controller della schermata di selezione della modalità di gioco</p>
	 * @author Ciro Cozzolino
	 */
	
	private CGameModeMenu _oMainClass;
	
	public ICCGameModeMenu() 
	{
		
	}
	
	@FXML
	private void initialize() 
	{
		
	}
	
	@FXML
	private void onAttackModeButt() //Funzione per selezionare la modalità attacco cliccando il pulsante
	{
		_oMainClass.onSelectedMode(EGameMode.MODE_ATTACK);
	}
	
	@FXML
	private void onDefenseModeButt() //Funzione per selezionare la modalità difesa cliccando il pulsante
	{
		_oMainClass.onSelectedMode(EGameMode.MODE_DEFENSE);
	}
	
	@FXML
	private void onNeutralModeButt() 
	{
		_oMainClass.onSelectedMode(EGameMode.MODE_NEUTRAL); //Funzione per selezionare la modalità neutrale cliccando il pulsante
	}
	
	public void setMainClass(CGameModeMenu oMainClass) 
	{
		this._oMainClass = oMainClass;
	}
}
