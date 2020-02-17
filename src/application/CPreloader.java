package application;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import javafx.scene.image.Image;

/**
 * <p>Classe Singleton per il precaricamento degli elementi di gioco (Immagini, Musica)</p>
 * @author Ciro Cozzolino
 */

public class CPreloader { //Classe Singleton per la gestione delle immagini e degli audio all'interno del gioco, scritta per non creare infiniti elementi 
						  //occupando troppa memoria e caricarle tutte all'apertura dell applicativo
	private static CPreloader ISTANCE;
	private HashMap<String,Image> _mpSprites; //HashMap per il contenimento delle immagini
	private HashMap<String,Clip> _mpClips; //HashMap per il contenimento delle clip audio
	
	
	public static CPreloader getIstance() 
	{
		if (ISTANCE == null) 
		{
			ISTANCE = new CPreloader();
		}
		
		return ISTANCE;
	}
	/**
	 * <p>Funzione per inizializzazione della fase di caricamento degli elementi</p>
	 */
	public void init() 
	{
		_mpSprites = new HashMap<String,Image>(); //Inizializzo l'HashMap e instanzio le varie immagini e i vari audio
		_mpClips = new HashMap<String, Clip>();
		_mpSprites.put("bg_menu", new Image("file:sprites"+System.getProperty("file.separator")+"bg_menu.jpg"));
		_mpSprites.put("bg_game", new Image("file:sprites"+System.getProperty("file.separator")+"bg_game.jpg"));
		_mpSprites.put("board", new Image("file:sprites"+System.getProperty("file.separator")+"board.png"));
		_mpSprites.put("piece_red", new Image("file:sprites"+System.getProperty("file.separator")+"piece_red.png"));
		_mpSprites.put("piece_yellow", new Image("file:sprites"+System.getProperty("file.separator")+"piece_yellow.png"));
		try {
			_mpClips.put("soundtrack", AudioSystem.getClip());
			_mpClips.get("soundtrack").open(AudioSystem.getAudioInputStream(new File("music"+System.getProperty("file.separator")+"soundtrack.wav")));
			@SuppressWarnings("unused")
			FloatControl oFloatControl = (FloatControl) _mpClips.get("soundtrack").getControl(FloatControl.Type.MASTER_GAIN);
			oFloatControl.setValue((float)(Math.log(0.2D)/Math.log(10)*20));
			_mpClips.put("drop_piece", AudioSystem.getClip());
			_mpClips.get("drop_piece").open(AudioSystem.getAudioInputStream(new File("music"+System.getProperty("file.separator")+"drop_piece.wav")));
			_mpClips.put("game_win", AudioSystem.getClip());
			_mpClips.get("game_win").open(AudioSystem.getAudioInputStream(new File("music"+System.getProperty("file.separator")+"game_win.wav")));
			_mpClips.put("game_lose", AudioSystem.getClip());
			_mpClips.get("game_lose").open(AudioSystem.getAudioInputStream(new File("music"+System.getProperty("file.separator")+"game_lose.wav")));
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * <p>Funzione per ottenere una clip musicale dato il nome</p>
	 * @param szSound nome della traccia
	 * @return clip effettiva
	 */
	
	public Clip getClip(String szSound) //funzione per ottenere un audio dato il nome
	{
		return _mpClips.get(szSound);
	}
	
	/**
	 * <p>Funzione per ottenere un'immagine dato il nome</p>
	 * @param szImage nome dell'immagine
	 * @return immagine effettiva
	 */
	
	public Image getImage(String szImage) //funzione per ottenere un immagine dato il nome
	{
		return _mpSprites.get(szImage);
	}
	
}

