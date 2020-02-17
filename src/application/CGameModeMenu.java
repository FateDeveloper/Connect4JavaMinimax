package application;


import application.viewcontroller.ICCGameModeMenu;
import javafx.fxml.FXMLLoader;

/**
 * <p>Estensione di ACScreen, schermata di selezione della modalità di gioco</p>
 * @author Ciro Cozzolino
 */

public class CGameModeMenu extends ACScreen{
	
	public CGameModeMenu(MainApp oMainApp)
	{
		super(oMainApp, "viewcontroller/VCGameModeMenu.fxml");
	}
	
	/**
	 * <p>Funzione per salvare nel main la modalità di gioco scelta</p>
	 * @param eGameMode enum che rappressenta la modalità di gioco scelta
	 */
	
	public void onSelectedMode(EGameMode eGameMode) 
	{
		this.unload();
		_oMainApp.setEGameMode(eGameMode); //Setta la modalità di gioco direttamente nel main
		gotoNextMenu();
	}
	
	@Override
	public void gotoNextMenu() 
	{
		_oMainApp.gotoGame();
	}
	
	@Override
	protected void unload() 
	{
		super._oMainApp.getRootLayout().setCenter(null);
	}

	@Override
	protected void initController(FXMLLoader oLoader) {
		ICCGameModeMenu oController = oLoader.getController();
        oController.setMainClass(this);
	}
	
}
