package application;

import java.io.Serializable;
/**
 * <p>Classe per gestire la singola pedina da gioco</p>
 * @author Ciro Cozzolino
 */
public class CPiece implements IPiece, Serializable{ //Classe che rappresenta le pedine
	private static final long serialVersionUID = 1L;
	EPieces _eColor; //Definisce il colore della pedina
	boolean _bSpawned; //Booleana per verificare se la pedina è lasciata cadere
	float _fX; //Coordinata X
	float _fY; //Coordinata Y
	float _fYToSpawn; //Coordinata Y finale
	
	/**
	 * <p>Costruttore della pedina</p>
	 * @param ePieceColor enum rappresentante il colore della pedina
	 * @param fX posizione x iniziale della pedina
	 * @param fY posizione y iniziale della pedina
	 */
	
	public CPiece(EPieces ePieceColor, float fX, float fY) 
	{
		_bSpawned = false;
		_eColor = ePieceColor;
		_fX = fX;
		_fY = fY;
	}
	
	/**
	 * <p>Funzione per ottenere la stringa di immagine della pedina, in base al colore della pedina</p>
	 * @return stringa dell immagine della pedina
	 */
	public String getImageSz() 
	{
		switch (_eColor) 
		{
			case PIECE_RED:
				return "piece_red";
			case PIECE_YELLOW:
				return "piece_yellow";
		}
		
		return null;
	}
	
	/**
	 * <p>Funzione per inizializzare il movimento della pedina per l inserimento della tavola da gioco</p>
	 * @param fYToSpawn coordinata y di arrivo della pedina
	 */
	public void initSpawn(float fYToSpawn) 
	{
		_fYToSpawn = fYToSpawn;
		_bSpawned = true;
	}
	
	
	/**
	 * <p>Funzione di aggiornamento delle coordinate della pedina, singolo frame</p>
	 */
	public void update()
	{
		if (_bSpawned) 
		{
			if (_fY < _fYToSpawn)
				_fY += 15;
			else 
			{
				_fY = _fYToSpawn;
				_bSpawned = false;
			}
		}
	}
	
	public void setColor(EPieces eColor) 
	{
		_eColor = eColor;
	}
	
	public void setPosition(float fX, float fY) 
	{
		_fX = fX;
		_fY = fY;
	}
	
	public void setX(float fX) 
	{
		_fX = fX;
	}
	
	public void setY(float fY) 
	{
		_fX = fY;
	}
	
	public float getX() 
	{
		return _fX;
	}
	
	public float getY() 
	{
		return _fY;
	}
	
	@Override
	public IPiece getClone() { //Utilizzata per il prototype pattern
		CPiece oPieceObj = null;
		
		try 
		{
			oPieceObj = (CPiece) super.clone();
		}catch(CloneNotSupportedException e) 
		{
			System.out.println("Oggetto non clonabile");
			e.printStackTrace();
		}
		
		return oPieceObj;
		
	}
	
}
