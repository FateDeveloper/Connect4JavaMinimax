package application;



import application.viewcontroller.ICCAuthentication;
import javafx.fxml.FXMLLoader;

/**
 * <p>Estensione di ACScreen, Schermata di Autenticazione</p>
 * @author Ciro Cozzolino
 */

public class CAuthenticationMenu extends ACScreen{
	
	/**
	 * 
	 * @param oMainApp Riferimento al main.
	 */
	
	public CAuthenticationMenu(MainApp oMainApp) //Schermata di autenticazione
	{
		super(oMainApp, "viewcontroller/VCAuthenticationMenu.fxml");
	}
	
	@Override
	public void gotoNextMenu() 
	{
		this.unload();
		super._oMainApp.gotoMenu(EScreens.GAME_MENU);
	}
	
	@Override
	protected void unload() 
	{
		super._oMainApp.getRootLayout().setCenter(null);
	}
	
	/**
	 * <p>Funzione atta a settare il nome e il cognome dell utente nel main.</p>
	 * @param szName Stringa contentente il nome.
	 * @param szSurname Stringa contenente il cognome.
	 */
	
	public void setUser (String szName, String szSurname) //funzione per salvare nel main il nome e il cognome utilizzato
	{
		super._oMainApp.setUser(szName,szSurname);
	}


	@Override
	protected void initController(FXMLLoader oLoader) {
		ICCAuthentication oController = oLoader.getController();
        oController.setMainClass(this);
		
	}
}
