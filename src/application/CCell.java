package application;

import java.io.Serializable;

/**
 * <p>Classe per la singola cella della matrice tavola da gioco.</p>
 * @author Ciro Cozzolino
 *
 */

public class CCell implements Serializable { //Classe per gestire la singola cella, serializzabile per salvarla su un file binario
	private static final long serialVersionUID = 1L;
	EPieces _eFilledBy; //Variabile per vedere di che colore è la pedina che la riempie.
	float _fXCenter; //Coordinata X per la posizione centrale di questa cella;
	float _fYCenter; //Coordinata Y per la posizione centrale di questa cella;
	
	/**
	 * <p>Costruttore della classe</p>
	 * @param fXCenter Imposta la coordinata x della cella
	 * @param fYCenter Imposta la coordinata y della cella
	 */
	
	public CCell(float fXCenter,float fYCenter) 
	{
		_eFilledBy = null;
		_fXCenter = fXCenter;
		_fYCenter = fYCenter;
	}
	
	/**
	 * 
	 * @return Ritorna un enum che rappresenta il colore della pedina che la riempie.
	 */
	
	public EPieces getColorPiece() 
	{
		return _eFilledBy;
	}
	
	/**
	 * <p>Setta la cella riempita da una pedina del colore in base al parametro inserito</p>
	 * @param ePiece enum che rappresenta il colore della pedina.
	 */
	
	public void setColorPiece(EPieces ePiece) 
	{
		_eFilledBy = ePiece;
	}
	
	/**
	 * 
	 * @return ritorna la coordinata x della cella.
	 */
	
	public float getCenterX() 
	{
		return _fXCenter;
	}
	
	/**
	 * 
	 * @return ritorna la coordinata y della cella.
	 */
	
	public float getCenterY() 
	{
		return _fYCenter;
	}
}
