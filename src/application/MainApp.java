package application;

import java.io.IOException;

import javax.sound.sampled.Clip;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

/**
 * <p>Classe main di tutto il progetto</p>
 * @author Ciro Cozzolino
 */

public class MainApp extends Application {

	private Stage _oPrimaryStage;
    private BorderPane _oRootLayout;
    private String _szSelectedName;
    private String _szSelectedSurname;
    private EGameMode _eSelectedGameMode;
    private Scene _oRootScene;
    private int _iWinner;
    private CScreensFactory _oScreenFactory;
	
	@Override
	public void start(Stage primaryStage) {
		this._oPrimaryStage = primaryStage;
		this._oPrimaryStage.setTitle("Connect Four"); //Inserisco il titolo
		initRootLayout();
		_oScreenFactory = new CScreensFactory(); //inizializzo il Factory Method
		CPreloader.getIstance().init(); //Inizializzo il preloader
		CSoundHandler.getIstance().playSound("soundtrack", Clip.LOOP_CONTINUOUSLY); //Riproduco la soundtrack
		gotoMenu(EScreens.AUTHENTICATION_MENU); //Avvio la prima schermata
		_iWinner = 0;
	}

	/**
	 * <p>Inizializza il nodo radice, ossia una schermata BorderPane completamente vuota, da riempire con le varie schermate di gioco</p>
	 */
	
	public void initRootLayout() { //Inizializza la prima schermata con solo un BorderPane, al quale attaccherò al centro tutte le altre schermate
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("viewcontroller/RootLayout.fxml"));
            _oRootLayout = (BorderPane) loader.load();
            
            // Show the scene containing the root layout.
            _oRootScene = new Scene(_oRootLayout);
            _oPrimaryStage.setScene(_oRootScene);
            _oPrimaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	/**
	 * <p>Funzione per caricare una nuova schermata di gioco (Non in game)(Factory Method)</p>
	 * @param eMenu enum rappresentante il menu da caricare
	 */
	public void gotoMenu(EScreens eMenu) //Funzione per inizializzare un menu
	{
		_oScreenFactory.initMenu(eMenu, this);
	}
	
	/**
	 * <p>Funzione per salvare nel main le informazioni dell utente</p>
	 * @param szName Nome dell utente
	 * @param szSurname Cognome dell utente
	 */
	
	public void setUser(String szName, String szSurname) //Funzione per salvare nel main nome e cognome del giocatore
	{
		_szSelectedName = szName;
		_szSelectedSurname = szSurname;
	}
	
	/**
	 * <p>Definisce l'inizializzazione del nome del file in base alle credenziali dell utente</p>
	 */
	public void initSaveData() //Inizializzo il salvataggio
	{
		CSaveDataHandler.getIstance().init(getUserNameSD()+".save");
	}
	
	/**
	 * <p>Carica la schermata di gioco come nuova partita</p>
	 */
	public void gotoGame() //Inizializza la schermata di gioco in caso di Nuova Partita
	{
		new CGame(this);
	}
	
	/**
	 * <p>Funzione per salvare nel main le informazioni sul vincitore della partita</p>
	 * @param iWinner vincitore della partita
	 */
	public void setWinner(int iWinner) //Salva il vincitore nel main 
	{
		_iWinner = iWinner;
	}
	
	public int getWinner() //ottieni il vincitore
	{
		return _iWinner;
	}
	
	/**
	 * <p>Funzione per caricare la schermata di gioco come carica partita</p>
	 * @param oSavedata salvataggio da caricare
	 */
	
	public void gotoGame(CSaveDataGame oSavedata) //Inizializza la schermata di gioco in caso di Carica Partita
	{
		new CGame(this,oSavedata);
	}
	
	/**
	 * <p>Funzione per salvare nel main la modalità di gioco utilizzata</p>
	 * @param eSelectedMode enum che rappresenta la modalità di gioco scelta
	 */
	public void setEGameMode(EGameMode eSelectedMode) //Salvo nel main la modalità di gioco scelta 
	{
		_eSelectedGameMode = eSelectedMode;
	}
	
	public EGameMode getEGameMode() //Ottengo la modalità di gioco scelta
	{
		return _eSelectedGameMode;
	}
	
	public BorderPane getRootLayout() //Funzione per ottenere il nodo iniziale
	{
		return _oRootLayout;
	}
	
	public Scene getRootScene() //Funzione per ottenere la scena iniziale
	{
		return _oRootScene;
	}
	
	/**
	 * <p>Funzione per ottenere nome e cognome dell utente separati da uno spazioe</p>
	 * @return nome e cognome dell utente separate da uno spazio
	 */
	public String getUserName() //Funzione per ottenere nome e cognome in un unica stringa separate da uno spazio
	{
		return _szSelectedName+" "+_szSelectedSurname;
	}
	/**
	 * <p>Funzione per ottenere nome e cognome dell'utente atti alla creazione del file di salvataggio</p>
	 * @return nome e cognome dell utente separati da un _
	 */
	public String getUserNameSD()  // Funzione per ottenere nome e cognome in un unica stringa separate da un trattino basso
	{
		return _szSelectedName+"_"+_szSelectedSurname;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
