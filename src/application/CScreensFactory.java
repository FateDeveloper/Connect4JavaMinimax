package application;

/**
 * <p>Classe per il pattern Factory Method delle schermate (Non in gioco)</p>
 * @author Ciro Cozzolino
 */

public class CScreensFactory {
	public CScreensFactory() {}
	
	/**
	 * <p>Dato come parametro, la schermata da inizializzare, instanzia una delle classi figlie di ACScreen (Schermate)</p>
	 * @param eScreen Enum che rappresenta la schermata da inizializzare
	 * @param oMainApp Riferimento al main
	 * @return Figlio di ACScreen o null
	 */
	
	public ACScreen initMenu(EScreens eScreen, MainApp oMainApp) //Classe Factory Method per l'inizializzazione delle schermate
	{
		switch (eScreen) 
		{
			case AUTHENTICATION_MENU:
				return new CAuthenticationMenu(oMainApp);
			case GAME_MENU:
				return new CGameMenu(oMainApp);
			case GAME_MODE_MENU:
				return new CGameModeMenu(oMainApp);
			case RANKSMENU:
				return new CRankMenu(oMainApp);
			default:
				return null;
		}
	}
}
