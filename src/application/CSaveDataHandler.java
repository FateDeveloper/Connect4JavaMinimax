package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
/**
 * 
 * @author Ciro Cozzolino
 *<p>Classe Singleton per la gestione del salvataggio e del caricamento</p>
 */
public class CSaveDataHandler { //Classe Singleton per gestire il salvataggio e il caricamento dei dati in game
	private static volatile CSaveDataHandler ISTANCE;
	private String _szFile;
	private ObjectInputStream _oObjectInputStream;
	private ObjectOutputStream _oObjectOutputStream;
	
	public static CSaveDataHandler getIstance() 
	{
		if (ISTANCE == null) 
		{
			ISTANCE = new CSaveDataHandler();
		}
		
		return ISTANCE;
	}
	
	/**
	 * <p>Funzione per l'inizializzazione del percorso del file di salvataggio</p>
	 * @param szFile nome del file
	 */
	public void init(String szFile) //Funzione per inizializzare il nome del file di salvataggio
	{
		_szFile = "."+System.getProperty("file.separator")+"savedatas"+System.getProperty("file.separator")+szFile;
	}
	
	/**
	 * Funzione per il caricamento del file
	 * @return Ritorna l'oggetto {@link CSaveDataGame} contenente i dati del salvataggio
	 */
	public CSaveDataGame loadGame() //Funzione di carica partita
	{
		CSaveDataGame oSavedata = new CSaveDataGame();
		try 
		{
			_oObjectInputStream = new ObjectInputStream(new FileInputStream(_szFile));
			oSavedata = (CSaveDataGame)_oObjectInputStream.readObject(); //Legge l oggetto dal file
			_oObjectInputStream.close();
		}catch(IOException e) 
		{
			e.printStackTrace();
		}catch(ClassNotFoundException e) 
		{
			e.printStackTrace();
		}
		
		return oSavedata; //Ritorna l oggetto letto
	}
	
	/**
	 * Funzione per salvare i dati in un oggetto {@link CSaveDataGame} e successivamente scriverlo sul file
	 * @param eGameMode enum contentente la modalità scelta
	 * @param aBoard tavola da gioco
	 * @param aPieces lista delle pedine in gioco
	 * @param bPlayerTurn booleana che definisce il turno del giocatore
	 * @param bGameOver booleana che definisce lo stato della partita (
	 */
	
	public void saveData(EGameMode eGameMode, CCell[][] aBoard,ArrayList<CPiece> aPieces, boolean bPlayerTurn, boolean bGameOver) //Funzione per salvare lo stato della partita in un file 
	{
		new File(_szFile);
		CSaveDataGame oSavedata = new CSaveDataGame(); //crea l'oggetto
		//Lo riempio
		oSavedata._eGameMode = eGameMode;
		oSavedata._aBoard = aBoard;
		oSavedata._aPieces = aPieces;
		oSavedata._bPlayerTurn = bPlayerTurn;
		oSavedata._bGameOver = bGameOver;
		
		try 
		{
			_oObjectOutputStream = new ObjectOutputStream(new FileOutputStream(_szFile));
			_oObjectOutputStream.writeObject(oSavedata); //Lo Scrivo sul file
			_oObjectOutputStream.close();
		}catch(IOException e) 
		{
			e.printStackTrace();
		}
		
	}
	
	/**
	 * <p>Funzione per controllare se la partita esiste e se non è game over</p>
	 * @return True se la partita esiste e non è game over, False se la partita non esiste o la partita è game over
	 */
	
	public boolean existAndIsNotGameOver() //Verifica se lo stato della partita non è uno stato di game over 
	{
		CSaveDataGame oSavedata = new CSaveDataGame();
		try 
		{
			_oObjectInputStream = new ObjectInputStream(new FileInputStream(_szFile));
			oSavedata = (CSaveDataGame)_oObjectInputStream.readObject();
			_oObjectInputStream.close();
		}catch(IOException e) 
		{
			e.printStackTrace();
		}catch(ClassNotFoundException e) 
		{
			e.printStackTrace();
		}
		
		
		if (oSavedata != null && !oSavedata._bGameOver)
			return true;
		return false;
		
	}
}
