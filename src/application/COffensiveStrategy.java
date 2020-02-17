package application;

import java.util.ArrayList;


/**
 * <p>Classe che rappresenta la strategia offensiva della CPU</p>
 * @author Ciro Cozzolino
 */

public class COffensiveStrategy implements ICPUStrategy{ //Strategia offensiva 

	CGame _oGameClass;
	int PLAYER_PIECE = 1;
	int AI_PIECE = 2;
	int EMPTY = 0;
	int _aPrimitiveBoard[][];
	int _iNextMoveLocation;
	int _iMaxDepth = 4;
	
	public COffensiveStrategy(CGame oGameClass) {
		_oGameClass = oGameClass;
	}
	
	
	@Override
	public int getBestColumn(int[][] aPrimitiveBoard) { //Funzione per ottenere la colonna migliore in base all algoritmo
		_iNextMoveLocation = -1; //Inizializza ad un valore null la cella migliore
		_aPrimitiveBoard = aPrimitiveBoard.clone(); //Clona la matrice primitiva data in input
		minimaxAlphaBetaPruning(_iMaxDepth, AI_PIECE, Integer.MIN_VALUE, Integer.MAX_VALUE); //Esegue l'algoritmo min-max con alpha beta pruning
		return _iNextMoveLocation;
	}
	
	/**
	 * <p>Funzione ricorsiva per il calcolo della mossa migliore, analizzando tutti i possibili scenari, ottimizzando i passaggi con l'alpha beta pruning</p>
	 * @param iDepth Livello di profondità di ricerca attuale
	 * @param iTurn Turno attuale di ricerca
	 * @param iAlpha Punteggio minimo che la CPU puo raggiungere
	 * @param iBeta Punteggio minimo che il Player puo raggiungere
	 * @return Int Valutazione della mossa
	 */
	
	private int minimaxAlphaBetaPruning(int iDepth, int iTurn, int iAlpha, int iBeta) 
	{
		//Casi Base
		
		if (iBeta <= iAlpha) //Se beta è maggiore di alpha vuol dire che non ha senso continuare a cercare attraverso quest albero 
		{
			if (iTurn == AI_PIECE)
				return Integer.MAX_VALUE; //Ritorna il valore massimo se il turno è della cpu
			else
				return Integer.MIN_VALUE; //Ritorna il valore minimo se il turno è del player
		}
		
		int iGameOver = _oGameClass.getGameOver(_aPrimitiveBoard,null); 
		if (iGameOver != 0) //Controllo se c'è una situazione di gameover
		{
			if (iGameOver == AI_PIECE) //In caso di vittoria della cpu, ritorna il valore massimo / 2
				return Integer.MAX_VALUE/2;
			else if (iGameOver == PLAYER_PIECE) //In caso di vittoria del player, ritorna il valore minimo / 2
				return Integer.MIN_VALUE/2;
			else
				return 0; //In caso di pareggio, ritorna 0
		}
		
		if (iDepth == 0) //Se la profondità massima è stata raggiunta, ritorna il valore secono la funzione di valore euristico
			return getScore(_aPrimitiveBoard, AI_PIECE);
		
		int iMaxScore = Integer.MIN_VALUE, iMinScore = Integer.MAX_VALUE; //Inizializza MaxScore al minimo e MinScore al massimo
		
		for (int i=0;i<6;i++) //Itera tutte le colonne della scacchiera
		{
			int iCurrentScore = 0; //Inizializza score a 0
			
			if (!isLegalMove(i)) continue; //se la mossa per questa colonna non è valida, passa alla colonna successiva
			
			if (iTurn == AI_PIECE) //Se è il turno della cpu, quindi massimizza
			{
				placeMove(i, AI_PIECE); //Inserisce la pedina della cpu nella colonna iterata
				iCurrentScore = minimaxAlphaBetaPruning(iDepth-1, PLAYER_PIECE, iAlpha, iBeta); // chiamata ricorsiva con profondità minore di 1 e secondo il turno del player
				
				if (iDepth == _iMaxDepth) //Se ci troviamo a profondità = iMaxDepth vuol dire che siamo risaliti al primo nodo e quindi: 
				{
					if (iCurrentScore > iMaxScore) _iNextMoveLocation = i; //Vedo se lo score di questo nodo è maggiore del valore massimo, e in tal caso salvo globalmente
																		   //l'indice della colonna migliore
					if (iCurrentScore == Integer.MAX_VALUE/2) //Se lo score attuale è lo score di vittoria
					{
						undoMove(i); //Rimuovo la pedina nella colonna i;
						break; //interrompo il ciclo;
					}
				}
				
				iMaxScore = Math.max(iCurrentScore, iMaxScore);
				iAlpha = Math.max(iCurrentScore, iAlpha);
				
			}else if(iTurn == PLAYER_PIECE) //Se è il turno del player, quindi minimizza
			{
				placeMove(i, PLAYER_PIECE); //Inserisco la pedina del player nella colonna iterata
				iCurrentScore = minimaxAlphaBetaPruning(iDepth-1, AI_PIECE, iAlpha, iBeta); //Chiamata ricorsiva con profondità minore di 1 secondo il turno della cpu
				iMinScore = Math.min(iCurrentScore, iMinScore); 
				iBeta = Math.min(iCurrentScore, iBeta);
			}
			
			undoMove(i); //Rimuovo la pedina nella colonna i;
			if (iCurrentScore == Integer.MAX_VALUE || iCurrentScore == Integer.MIN_VALUE) break;
		}
		return iTurn==AI_PIECE?iMaxScore:iMinScore; //Se è il turno della cpu ritorna max score, se è quello del player ritorna min score
		
	}
	
	/**
	 * <p>Funzione per controllare se la mossa è effettuabile in una data colonna</p>
	 * @param iCol Indice della colonna da controllare
	 * @return True se è possibile effettuare la mossa, altrimenti False
	 */
	
	private boolean isLegalMove(int iCol) //Controlla se la mossa in questa colonona è possibile
	{
		return _aPrimitiveBoard[iCol][0] == 0;
	}
	
	/**
	 * <p>Funzione per effettuare una mossa in una data colonna, all'interno della matrice primitiva di classe</p>
	 * @param iCol Indice della colonna in cui effettuare la mossa
	 * @param iPlayer pedina (primitiva) da inserire
	 * @return True se l'operazione va a buon fine, altrimenti False
	 */
	
	private boolean placeMove(int iCol, int iPlayer) //Inserisce la pedina nella colonna nella matrice primitiva
	{
		if (!isLegalMove(iCol))return false;
		for (int i = 6; i > -1; i--) 
		{
			if (_aPrimitiveBoard[iCol][i] == 0) 
			{
				_aPrimitiveBoard[iCol][i] = iPlayer;
				return true;
			}
		}
		return false;
	}
	
	/**
	 * <p>Funzione per togliere l'ultima pedina in una data colonna nella matricce primitiva di classe</p>
	 * @param iCol Indice della colonna in cui rimuovere la pedina
	 */
	
	private void undoMove(int iCol) //Rimuove l ultima pedina nella colonna della matrice primitiva
	{
		for (int i=0;i<7;i++) 
		{
			if (_aPrimitiveBoard[iCol][i] != 0) 
			{
				_aPrimitiveBoard[iCol][i] = 0;
				break;
			}
		}
	}
	
	/**
	 * <p>Funzione per ottenere il valore euristico di una data matrice primitiva</p>
	 * @param aBoard Matrice primitiva
	 * @param iPiece Player (primitivo) da valutare
	 * @return Punteggio della data matrice primitiva
	 */
	
	private int getScore(int[][] aBoard,int iPiece) //Funzione euristica della matrice
	{
		int iScore = 0; //inizializza lo score
		int aWindowArray[] = new int[4]; // crea una finestra di 4 elementi
		
		int aCenterColumn[] = new int[7]; //Array per contenere colonne centrali
		
		
		for (int i=0;i<7;i++) 
		{
			aCenterColumn[i] = aBoard[2][i]; //Colonna centrale 1
		}
		
		iScore+= _oGameClass.getOccurrencesInIntArray(aCenterColumn, iPiece) * 3; //Punteggio in colonna Centrale 1
		
		for (int i=0;i<7;i++) 
		{
			aCenterColumn[i] = aBoard[3][i]; //Colonna centrale 2
		}
		
		iScore+= _oGameClass.getOccurrencesInIntArray(aCenterColumn, iPiece) * 3; //Punteggio in colonna Centrale 2
		
		//Check Horizontal
		int aRowArray[] = new int[6]; //inizializzo array delle righe
		for (int j=0;j<7;j++) 
		{
			for (int i=0;i<6;i++) 
			{
				aRowArray[i] = aBoard[i][j]; //Inserisco una riga alla volta nell array delle righe
			}
			for (int i=0; i<3;i++) //creo ogni possibile finestra da 4 elementi per ogni riga
			{
				aWindowArray[0] = aBoard[i][j];
				aWindowArray[1] = aBoard[i+1][j];
				aWindowArray[2] = aBoard[i+2][j];
				aWindowArray[3] = aBoard[i+3][j];
				
				iScore += getValueFromWindow(aWindowArray, iPiece); //calcolo il punteggio per ogni riga
			}
		}
		
		//Check Vertical
		int aColumnArray[] = new int[7]; //inizializzo array delle colonne
		for (int i=0;i<6;i++) 
		{
			for (int j=0;j<7;j++) 
			{
				aColumnArray[j] = aBoard[i][j]; //Inserisco una colonna alla volta nell array delle colonne
			}
			for (int j=0;j<4;j++) //creo ogni possibile finestra da 4 elementi per ogni colonna
			{
				aWindowArray[0] = aBoard[i][j];
				aWindowArray[1] = aBoard[i][j+1];
				aWindowArray[2] = aBoard[i][j+2];
				aWindowArray[3] = aBoard[i][j+3];
				
				iScore += getValueFromWindow(aWindowArray, iPiece); //calcolo il punteggio per ogni colonna
			}
		}
		
		
		//Check Diagonal Up Right
		ArrayList<Integer> aDiagonalUpLeftArray = new ArrayList<>(); //inizializzo la lista per le diagonali alte a sinistra (lista perché la length è variabile)
		for (int k = 0; k<= (6+7-2); k++)
	    {
	      for (int j = 0; j<= k; j++)
	      {
	        int i= k-j;
	        if (i < 6 && j < 7)
	        {
	          	aDiagonalUpLeftArray.add(aBoard[i][j]); //aggiungo ogni diagonale alta sinistra nell array delle diagonali alte sinistra
	        }
	      }
	      if (aDiagonalUpLeftArray.size() >= 4) //se la diagonale è di length maggiore o uguale a 4 trova tutte le finestre disponibili
	      {
	    	  for (int j=0;j<3;j++) //Itera j come se fosse la più lunga delle diagonali
	    	  {
	    		  if (j+3 < aDiagonalUpLeftArray.size()) //se j+3 < del size della diagonale allora c è una finestra da 4
	    		  {
	    			  aWindowArray[0] = aDiagonalUpLeftArray.get(j);
	    			  aWindowArray[1] = aDiagonalUpLeftArray.get(j+1);
	    			  aWindowArray[2] = aDiagonalUpLeftArray.get(j+2);
	    			  aWindowArray[3] = aDiagonalUpLeftArray.get(j+3);
	    			  
	    			  iScore += getValueFromWindow(aWindowArray, iPiece); //calcola il punteggio di questra finestra da 4
	    		  }
	    	  }
	      }
	      aDiagonalUpLeftArray.clear(); //Libero la lista
	    }
		
		//Check Diagonal UpLeft
		
		ArrayList<Integer> aDiagonalUpRightArray = new ArrayList<>(); // inizializzo la lista per le diagonali alte a destra
		int aMatrixDiagonal2[][] = new int[7][6]; 
		
		for (int i = 0;i < 6; i++)
	    {
	      for (int j =0; j<7;j++)
	      {
	      	aMatrixDiagonal2[j][6-1-i] = aBoard[i][j];
	      }
	    }
		
		for (int k = 0; k<= (6+7-2); k++) //Applico la stessa formula delle diagonali di prima
	    {
	      for (int j = 0; j<= k; j++)
	      {
	        int i= k-j;
	        if (i < 7 && j < 6)
	        {
	        	aDiagonalUpRightArray.add(aMatrixDiagonal2[i][j]);
	        }
	      }
	      if (aDiagonalUpRightArray.size() >= 4) 
	      {
	    	  for (int j=0;j<3;j++) 
	    	  {
	    		  if (j+3 < aDiagonalUpRightArray.size()) 
	    		  {
	    			  aWindowArray[0] = aDiagonalUpRightArray.get(j);
	    			  aWindowArray[1] = aDiagonalUpRightArray.get(j+1);
	    			  aWindowArray[2] = aDiagonalUpRightArray.get(j+2);
	    			  aWindowArray[3] = aDiagonalUpRightArray.get(j+3);
	    			  
	    			  iScore += getValueFromWindow(aWindowArray, iPiece);
	    		  }
	    	  }
	      }
	      aDiagonalUpRightArray.clear();
	    }
		
		
		return iScore;
	}
	
	/**
	 * <p>Ottiene il punteggio effettivo dalla singola finestra (Combo possibile da 4)</p>
	 * @param aWindow Finestra da valutare
	 * @param iPiece Pedina (Primitiva) da controllare
	 * @return Punteggio in base alla combinazione
	 */
	private int getValueFromWindow(int[] aWindow, int iPiece) //Ricava lo score in base alla window inserita
	{
		int iScore = 0;
		int iOpponentPiece = PLAYER_PIECE;
		if (iPiece == PLAYER_PIECE)
			iOpponentPiece = AI_PIECE;
		
		if (_oGameClass.getOccurrencesInIntArray(aWindow, iPiece) == 3 && _oGameClass.getOccurrencesInIntArray(aWindow, EMPTY) == 1) //5 punti con 3 pedine in fila
			iScore += 5;
		else if (_oGameClass.getOccurrencesInIntArray(aWindow, iPiece) == 2 && _oGameClass.getOccurrencesInIntArray(aWindow, EMPTY) == 2) //2 punti con 2 pedine in fila
			iScore += 2;
		
		if (_oGameClass.getOccurrencesInIntArray(aWindow, iOpponentPiece) == 3 && _oGameClass.getOccurrencesInIntArray(aWindow, EMPTY) == 1) // -4 punti con 3 pedine avversarie in fila
			iScore -= 4;
		
		return iScore;
	}
	
	
	
}
