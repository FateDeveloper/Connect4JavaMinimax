package application;


import application.viewcontroller.ICCGameMenu;
import javafx.fxml.FXMLLoader;

/**
 * <p>Estensione di ACScreen, schermata di selezione Nuova/Carica Partita</p>
 * @author Ciro Cozzolino
 */

public class CGameMenu extends ACScreen {
	
	
	public CGameMenu(MainApp oMainApp) 
	{
		super(oMainApp, "viewcontroller/VCGameMenu.fxml");
	}
	
	public String getUserName() 
	{
		return _oMainApp.getUserName();
	}
	
	@Override
	public void gotoNextMenu()
	{
		this.unload();
		super._oMainApp.gotoMenu(EScreens.GAME_MODE_MENU);
	}
	
	public void gotoNextMenu(CSaveDataGame oSavedata) 
	{
		this.unload();
		super._oMainApp.gotoGame(oSavedata);
	}
	
	@Override
	protected void unload() 
	{
		_oMainApp.getRootLayout().setCenter(null);
	}

	@Override
	protected void initController(FXMLLoader oLoader) {
		ICCGameMenu oController = oLoader.getController();
        oController.setMainClass(this);
	}
	
	public void initSaveData() 
	{
		_oMainApp.initSaveData(); //inizializza il salvataggio in base alle credenziali scelte
	}
	
}
