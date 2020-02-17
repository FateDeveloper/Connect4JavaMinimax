package application;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * <p>Classe con attributi pubblici (Struct) per la gestione dei dati del salvataggio</p>
 * @author Ciro Cozzolino
 */

public class CSaveDataGame implements Serializable{ //Classe con solo campi pubblici (Struct) per contenere le informazioni necessarie per il salvataggio dello stato della partita

	private static final long serialVersionUID = 5593431958349233796L;
	public boolean _bPlayerTurn;
	public CCell[][] _aBoard;
	public ArrayList<CPiece> _aPieces;
	public EGameMode _eGameMode;
	public boolean _bGameOver;
	
}
