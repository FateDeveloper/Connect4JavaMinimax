package application;

import java.util.ArrayList;
import java.util.Random;

import application.viewcontroller.ICCGame;
import application.viewcontroller.VCGame;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;

/**
 * <p>Classe per la gestione della schermata di gioco (Model)</p>
 * @author Ciro Cozzolino
 */

public class CGame {
	
	ICCGame _oGameController; //Variabile per l oggetto della gestione dell Input Utente
	VCGame _oGameView; //Variabile per l oggetto della gestione grafica
	int TIMEPERTURN = 500; //Tempo di delay per turno
	int TIME_DELAY_END_GAME = 300; //Tempo di delay per animazione di fine game
	MainApp _oMainApp; //Riferimento al main
	Pane _oCanvasPane; //Pannello per il canvas
	GraphicsContext _oGC; //Graphics Context del canvas
	CCell _aBoard[][]; //Tavola da gioco
	ArrayList<CPiece> _aPieces; //Arraylist contenenti le pedine in gioco
	boolean _bPlayerTurn; //Boolean per la gestione del turno
	double _fXMouse; //Variabile per tenere la coordinata X del mouse
	double _fYMouse; //Variabile per tenere la coordinata y del mouse
	boolean _bMouseClicked; //Variabile per tenere il click dell utente
	boolean _bFirstClick;
	CPiece _oRedPiecePrototype; //Pedina rossa prototype
	CPiece _oYellowPiecePrototype; //Pedina gialla prototype
	ESelectionColumns _ePreSelectedColumn; //Colonna pre-selezionata
	ICPUStrategy _oCpuStrategy; //Strategia della cpu
	AnimationTimer _oAnimationTimer; //Animation timer per i cicli
	int _iTimeTurn; 
	boolean _bGameOver; //Indica se la partita è finita
	boolean _bEndGameDelay; 
	int _iTimeDelayEndGame;
	int _aWinningPieces[][]; //Array destino a salvare le coordinate delle pedine vincenti
	int _iGameOver; //Int per gestire l esito della partita 0: Partita in Corso | 1: Vince il Player | 2: vince la Cpu | 3: Pareggio
	EGameMode _eGameMode; //variabile per tenere conto della Modalità di gioco
	String _szEndText; //Testo di fine gioco
	/**
	 * <p>Costruttore per l'inizializzazione della nuova partita</p>
	 * @param oMainApp riferimento al main
	 */
	public CGame(MainApp oMainApp) //Costruttore in caso di Nuova Partita
	{
		defaultInit(oMainApp);
        newGameInit(_oMainApp.getEGameMode());
        defaultInit2();
	}
	
	/**
	 * <p>Costruttore per l'inizializzazione della partita caricata</p>
	 * @param oMainApp riferimento al main
	 * @param oSavedata oggetto salvataggio
	 */
	
	public CGame(MainApp oMainApp, CSaveDataGame oSavedata) //Costruttore in caso di Carica Partita
	{
		defaultInit(oMainApp);
		loadGameInit(oSavedata);
		defaultInit2();
	}
	
	private void defaultInit(MainApp oMainApp) 
	{
		_oMainApp = oMainApp;
		_oCanvasPane = new Pane(); //Creo il Pannello
        _oMainApp.getRootLayout().setCenter(_oCanvasPane); //Lo applico alla schermata
        Canvas oCanvas = new Canvas(900,900); //Creo il canvas
        _oCanvasPane.getChildren().add(oCanvas); //Aggiungo il canvas al pannello
        _oGC = oCanvas.getGraphicsContext2D(); //Ricavo il Graphics Context
        _bGameOver = false;
        _bEndGameDelay = false;
        
        _aWinningPieces = new int[4][2];
        _iGameOver = 0;
        _oRedPiecePrototype = new CPiece(EPieces.PIECE_RED,0,80); //Inizializzo la pedina rossa prototype
        _oYellowPiecePrototype = new CPiece(EPieces.PIECE_YELLOW,0,80); //Inizializzo la pedina gialla prototype
        
        _ePreSelectedColumn = ESelectionColumns.NULL;
        
        _bFirstClick = true;
        _bMouseClicked = false;
        _fXMouse = 0;
        _fYMouse = 0;
        _iTimeTurn = TIMEPERTURN;
        _iTimeDelayEndGame = TIME_DELAY_END_GAME;
	}
	
	private void newGameInit(EGameMode eGameMode) 
	{
		_aBoard = new CCell[6][7]; //Inizializzo la board vuota
		_aPieces = new ArrayList<CPiece>();
        
        float fFirstCellCenterX = 205+45;
        float fFirstCellCenterY = 165+45;
        for (int i=0;i<6;i++) 
        {
        	for (int j=0;j<7;j++) 
        	{
        		_aBoard[i][j] = new CCell(fFirstCellCenterX + (80 * i), fFirstCellCenterY + (80 * j));
        	}
        }
        
        _eGameMode = eGameMode;
        _bPlayerTurn = (new Random().nextInt((2-1)+1) + 1 == 1)?true:false;
	}
	
	@SuppressWarnings("unchecked")
	private void loadGameInit(CSaveDataGame oSavedata) //Carico i dati del salvataggio
	{
		_aPieces = (ArrayList<CPiece>)oSavedata._aPieces.clone();
		_aBoard = oSavedata._aBoard.clone();
		_eGameMode = oSavedata._eGameMode;
		_bPlayerTurn = oSavedata._bPlayerTurn;
	}
	
	private void defaultInit2() 
	{
		switch (_eGameMode) //Inizializzo la strategia
        {
        	case MODE_ATTACK:
        		_oCpuStrategy = new COffensiveStrategy(this);
        		break;
        	case MODE_DEFENSE:
        		_oCpuStrategy = new CDefensiveStrategy();
        		break;
        	case MODE_NEUTRAL:
        		break;
		}
		
		_oGameController = new ICCGame(this); //Inizializzo l'input controller
		_oGameView = new VCGame(this); //inizializzo il view controller
		
		final long startNanoTime = System.nanoTime();
        _oAnimationTimer = new AnimationTimer() { //Inizializzo il ciclo di update
        	public void handle(long currentNanoTime) 
        	{
        		double t = (currentNanoTime - startNanoTime) / 1000000000.0;
        		update(t); //Funzione per aggiornare constantemente i valori variabili
        		_oGameView.update(t, _oGC, _bPlayerTurn, _ePreSelectedColumn, _aPieces, _iGameOver, _bGameOver,_bEndGameDelay); //Funzione per l aggiornamento grafico
        		
        	}
        };
        _oAnimationTimer.start(); //Avvio il ciclo
	}
	
	public MainApp getMainApp() 
	{
		return _oMainApp;
	}
	
	/**
	 * <p>Funzione per gestire il singolo frame</p>
	 * @param t tempo trascorso dal frame precedente
	 */
	
	private void update(double t) 
	{
		if (!_bGameOver) //Se la partita non è finita
		{
			if (isBeetween2Doubles(_fXMouse, 205, 290)) //Preselezione della casella in base alla posizione del mouse
			{
				_ePreSelectedColumn = ESelectionColumns.FIRST_COLUMN;
			}else if (isBeetween2Doubles(_fXMouse, 290, 370)) 
			{
				_ePreSelectedColumn = ESelectionColumns.SECOND_COLUMN;
			}else if (isBeetween2Doubles(_fXMouse, 370, 450)) 
			{
				_ePreSelectedColumn = ESelectionColumns.THIRD_COLUMN;
			}else if (isBeetween2Doubles(_fXMouse, 450, 530)) 
			{
				_ePreSelectedColumn = ESelectionColumns.FOURTH_COLUMN;
			}else if (isBeetween2Doubles(_fXMouse, 530, 610)) 
			{
				_ePreSelectedColumn = ESelectionColumns.FIFTH_COLUMN;
			}else if (isBeetween2Doubles(_fXMouse, 610, 695)) 
			{
				_ePreSelectedColumn = ESelectionColumns.SIXTH_COLUMN;
			}else 
			{
				_ePreSelectedColumn = ESelectionColumns.NULL;
			}
			
			int iSpawnRow;
			
			if (_bMouseClicked && !_bFirstClick && _bPlayerTurn) //Se c'è stato un click ed è il turno del giocatore
		{
				int iX = 0,iIndex = 0;
				switch (_ePreSelectedColumn) //Setta la cordinata di x in base alla colonna pre-selezionata
				{
					
					case FIRST_COLUMN:
						iX = 214;
						iIndex = 0;
						break;
					case SECOND_COLUMN:
						iX = 294;
						iIndex = 1;
						break;
					case THIRD_COLUMN:
						iX = 374;
						iIndex = 2;
						break;
					case FOURTH_COLUMN:
						iX = 454;
						iIndex = 3;
						break;
					case FIFTH_COLUMN:
						iX = 534;
						iIndex = 4;
						break;
					case SIXTH_COLUMN:
						iX = 614;
						iIndex = 5;
						break;
					case NULL:
						break;
				}
				
				if (_ePreSelectedColumn != ESelectionColumns.NULL)  //Se una colonna è stata pre-selezionata allora:
				{
					
					iSpawnRow = getFirstFreeCellInAColumn(getPrimitiveMatrix(),iIndex); //Ottiene la riga in base alla colonna selezionata
					if (iSpawnRow != -1) //Se la colonna non è piena
					{
						_aPieces.add((CPiece)_oRedPiecePrototype.getClone()); //Clona la pedina e la aggiunge alla lista delle pedine in gioco
						_aPieces.get(_aPieces.size()-1).setX(iX); //Setta la x della pedina
						_aPieces.get(_aPieces.size()-1).initSpawn(_aBoard[iIndex][iSpawnRow].getCenterY()-36); //Setta la y finale ed avvia lo spostamento
						_aBoard[iIndex][iSpawnRow].setColorPiece(EPieces.PIECE_RED); //Setta la cella corrispondente piena
						CSoundHandler.getIstance().playSound("drop_piece", 0); //Riproduce il suono di pedina che cade
						_bPlayerTurn = false; //Cambia turno
						handleGameOver(); //Controlla e gestisce il gameOver
						CSaveDataHandler.getIstance().saveData(_eGameMode, _aBoard, _aPieces, _bPlayerTurn,_bGameOver); //Salva la partita
					}
				}
				
			}
			if (!_bPlayerTurn) //Se è il turno dell avversario
			{
				if (_iTimeTurn > 0) //gestione del delay del turno
				{
					_iTimeTurn -= t;
				}else 
				{
					if (_eGameMode == EGameMode.MODE_NEUTRAL) //Se la modalità di gioco è neutrale sceglie casualmente una delle due strategie
					{
						if (new Random().nextInt((2-1) + 1) + 1 == 1)
						{
							_oCpuStrategy = new COffensiveStrategy(this);
						}else 
						{
							_oCpuStrategy = new CDefensiveStrategy();
						}
					}
					int iEnemySelection = _oCpuStrategy.getBestColumn(getPrimitiveMatrix()); //Ottiene la colonna migliore in base alla strategia utilizzata
					iSpawnRow = getFirstFreeCellInAColumn(getPrimitiveMatrix(),iEnemySelection); //Ottiene la riga in base alla colonna
					_aPieces.add((CPiece)_oYellowPiecePrototype.getClone()); //Clona la pedina gialla e la inserisce nella lista delle pedine in gioco (prototype)
					_aPieces.get(_aPieces.size()-1).setX(_aBoard[iEnemySelection][iSpawnRow].getCenterX()-36); //Setta la coordinata x della pedina
					_aPieces.get(_aPieces.size()-1).initSpawn(_aBoard[iEnemySelection][iSpawnRow].getCenterY()-36); //Setta la coordinata y finale della pedina ed avvia lo spostamento
					_aBoard[iEnemySelection][iSpawnRow].setColorPiece(EPieces.PIECE_YELLOW); //Setta la cella corrispondente piena
					handleGameOver(); //Controlla e gestisce il gameOver
					_bPlayerTurn = true; //cambia turno
					CSoundHandler.getIstance().playSound("drop_piece", 0); //Riproduce il suono della pedina che cade
					CSaveDataHandler.getIstance().saveData(_eGameMode, _aBoard, _aPieces, _bPlayerTurn,_bGameOver); //Salva la partita
					_iTimeTurn = TIMEPERTURN; //Resetta il tempo di delay
				}
			}
		}else if (!_bEndGameDelay) //Gestisce il piccolo delay quando finisce il gioco
		{
			_iTimeDelayEndGame-= t;
			
			if (_iTimeDelayEndGame <= 0) 
			{
				_bEndGameDelay = true;
			}
		}else 
		{
			if (_bMouseClicked == true) //Se si clicca col mouse dopo che la partita è finita
			{
				this.unload(); //Chiude questa schermata
				if (_iGameOver == 1) //Se il player ha vinto
				{
					CSaveRankHandler.getIstance().addNewScore(_oMainApp.getUserName()); //Aggiungi in classifica il player
				}
				_oMainApp.setWinner(_iGameOver); //Salva nel main il vincitore
				_oMainApp.gotoMenu(EScreens.RANKSMENU); //Vai alla schermata della classifica
			}
		} 
		
		_bMouseClicked = false;
		_bFirstClick = false;
		
		for (int i=0;i<_aPieces.size();i++) 
		{
			_aPieces.get(i).update(); //aggiorna le coordinate delle pedine;
		}
	}
	
	/**
	 * <p>Funzione per la ricerca e la gestione del game over</p>
	 */
	
	private void handleGameOver() //Gestione del gameover
	{
		_iGameOver = getGameOver(getPrimitiveMatrix(),_aWinningPieces); //Controllo se la partita è finita e se c è una vittoria, una sconfitta o un pareggio
		if (_iGameOver != 0) //Se la partita è finita:
		{	
			_bGameOver = true;
			switch (_iGameOver) 
			{
				case 1: //Vince il Player
					_szEndText = "Complimenti!  Hai vinto";
					CSoundHandler.getIstance().playSound("game_win", 0); //Riproduco il suono di vittoria
					break;
				case 2: //Vince la Cpu
					_szEndText = "Mi dispiace.. Hai perso";
					CSoundHandler.getIstance().playSound("game_lose", 0); //Riproduco il suono di sconfitta
					break;
				case 3: //Pareggio
					_szEndText = "Incredibile, Pareggio!";
					CSoundHandler.getIstance().playSound("game_lose", 0); //Riproduco il suono di sconfitta
			}
		}
	}
	
	public String getEndText() //Ottengo la stringa di fine partita
	{
		return _szEndText;
	}
	
	public int[][] getWinningPieces() //ottengo l'array winning pieces
	{
		return _aWinningPieces;
	}
	
	public CCell[][] getBoard() //Ottengo la board
	{
		return _aBoard;
	}
	
	/**
	 * <p>Ottiene l'indice della prima riga disponibile in una data colonna, di un array[int][int] (Matrice primitiva)</p>
	 * @param aPrimitiveBoard Matrice primitiva.
	 * @param iColumn Indice della colonna.
	 * @return Indice di riga
	 */
	
	private int getFirstFreeCellInAColumn(int[][] aPrimitiveBoard,int iColumn) //Funzione per ottenere il numero di riga data una colonna 
	{
		int iCell = 6;
		while (iCell != -1) 
		{
			if (aPrimitiveBoard[iColumn][iCell] == 0) 
				return iCell;
			iCell--;
		}
		return -1;
	}
	
	/**
	 * <p>Funzione per ricavare lo stato della partita (0: In corso, 1: Vince il Player, 2: Vince la CPU, 3: Pareggio) e Ricavare le pedine vincenti</p>
	 * @param aPrimitiveBoard Matrice primitiva della tavola da gioco
	 * @param aWinningPositions Matrice 2x4 per ricavare gli indici delle pedine
	 * @return Stato della partita
	 */
	
	public int getGameOver(int[][] aPrimitiveBoard, int[][] aWinningPositions) //Check GameOver 0 = Not Ended / 1 = Player Win / 2 = CpuWin / 3 = Tie.
	{	
		boolean bTie = true; //Controlla il pareggio
		
		for (int i=0;i<6;i++) 
		{
			for (int j=0;j<7;j++) 
			{
				if (aPrimitiveBoard[i][j] == 0) 
				{
					bTie = false;
				}
			}
		}
		
		if (bTie)
			return 3;
		
		int aWindowArray[] = new int[4]; // crea una finestra di 4 elementi
		int aTmpWinningPositions[][] = new int[7][2];
		int iTmpWinningPosition;
		
		//Check Horizontal
		int aRowArray[] = new int[6]; //inizializzo array delle righe
		for (int j=0;j<7;j++) 
		{
			for (int i=0;i<6;i++) 
			{
				aRowArray[i] = aPrimitiveBoard[i][j]; //Inserisco una riga alla volta nell array delle righe
			}
			for (int i=0; i<3;i++) //creo ogni possibile finestra da 4 elementi per ogni riga
			{
				aWindowArray[0] = aPrimitiveBoard[i][j];
				aWindowArray[1] = aPrimitiveBoard[i+1][j];
				aWindowArray[2] = aPrimitiveBoard[i+2][j];
				aWindowArray[3] = aPrimitiveBoard[i+3][j];
				
				if (aWindowArray[0] != 0 && getOccurrencesInIntArray(aWindowArray, aWindowArray[0]) == 4) 
				{
					if (aWinningPositions != null) //Inserisco nell array aWinningPosition le pedine vincenti
					{
						aWinningPositions[0][0] = i;
						aWinningPositions[0][1] = j;
						aWinningPositions[1][0] = i+1;
						aWinningPositions[1][1] = j;
						aWinningPositions[2][0] = i+2;
						aWinningPositions[2][1] = j;
						aWinningPositions[3][0] = i+3;
						aWinningPositions[3][1] = j;
					}
					return aWindowArray[0];
				}
			}
		}
		
		//Check Vertical
		int aColumnArray[] = new int[7]; //inizializzo array delle colonne
		for (int i=0;i<6;i++) 
		{
			for (int j=0;j<7;j++) 
			{
				aColumnArray[j] = aPrimitiveBoard[i][j]; //Inserisco una colonna alla volta nell array delle colonne
			}
			for (int j=0;j<4;j++) //creo ogni possibile finestra da 4 elementi per ogni colonna
			{
				aWindowArray[0] = aPrimitiveBoard[i][j];
				aWindowArray[1] = aPrimitiveBoard[i][j+1];
				aWindowArray[2] = aPrimitiveBoard[i][j+2];
				aWindowArray[3] = aPrimitiveBoard[i][j+3];
				
				if (aWindowArray[0] != 0 && getOccurrencesInIntArray(aWindowArray, aWindowArray[0]) == 4) 
				{
					if (aWinningPositions != null) //Inserisco nell array aWinningPosition le pedine vincenti
					{
						aWinningPositions[0][0] = i;
						aWinningPositions[0][1] = j;
						aWinningPositions[1][0] = i;
						aWinningPositions[1][1] = j+1;
						aWinningPositions[2][0] = i;
						aWinningPositions[2][1] = j+2;
						aWinningPositions[3][0] = i;
						aWinningPositions[3][1] = j+3;
					}
					
					return aWindowArray[0];
				}
			}
		}
		
		
		//Check Diagonal Up Right
		ArrayList<Integer> aDiagonalUpLeftArray = new ArrayList<>(); //inizializzo la lista per le diagonali alte a sinistra (lista perché la length è variabile)
		for (int k = 0; k<= (6+7-2); k++)
	    {
		  iTmpWinningPosition = 0;
	      for (int j = 0; j<= k; j++)
	      {
	        int i= k-j;
	        if (i < 6 && j < 7)
	        {
	          	aDiagonalUpLeftArray.add(aPrimitiveBoard[i][j]); //aggiungo ogni diagonale alta sinistra nell array delle diagonali alte sinistra
	          	aTmpWinningPositions[iTmpWinningPosition][0] = i;
	          	aTmpWinningPositions[iTmpWinningPosition][1] = j;
	          	iTmpWinningPosition++;
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
	    			  
	    			  if (aWindowArray[0] != 0 && getOccurrencesInIntArray(aWindowArray, aWindowArray[0]) == 4) 
	  				  {
	    				  if (aWinningPositions != null) //Inserisco nell array aWinningPosition le pedine vincenti
	    				  {
	    					  aWinningPositions[0][0] = aTmpWinningPositions[j][0];
	    					  aWinningPositions[0][1] = aTmpWinningPositions[j][1];
	    					  aWinningPositions[1][0] = aTmpWinningPositions[j+1][0];
	    					  aWinningPositions[1][1] = aTmpWinningPositions[j+1][1];
	    					  aWinningPositions[2][0] = aTmpWinningPositions[j+2][0];
	    					  aWinningPositions[2][1] = aTmpWinningPositions[j+2][1];
	    					  aWinningPositions[3][0] = aTmpWinningPositions[j+3][0];
	    					  aWinningPositions[3][1] = aTmpWinningPositions[j+3][1];
	    				  }
	  					return aWindowArray[0];
	  				  }
	    		  }
	    	  }
	      }
	      aDiagonalUpLeftArray.clear(); //Libero la lista
	    }
		
		//Check Diagonal UpLeft
		
		ArrayList<Integer> aDiagonalUpRightArray = new ArrayList<>(); // inizializzo la lista per le diagonali alte a destra
		int aMatrixDiagonal2[][] = new int[7][6]; //Cosa un po sporca, siccome non sapevo come calcolare le diagonali destre ho creato un altra matrice
												  //Che è uguale alla prima matrice ma ruotata di 90 gradi a destra, in modo da poter calcolare le diagonali 
												  //con la stessa formula
		for (int i = 0;i < 6; i++)
	    {
	      for (int j =0; j<7;j++)
	      {
	      	aMatrixDiagonal2[j][6-1-i] = aPrimitiveBoard[i][j];
	      }
	    }
		
		for (int k = 0; k<= (6+7-2); k++) //Applico la stessa formula delle diagonali di prima
	    {
		  iTmpWinningPosition = 0;
	      for (int j = 0; j<= k; j++)
	      {
	        int i= k-j;
	        if (i < 7 && j < 6)
	        {
	        	aDiagonalUpRightArray.add(aMatrixDiagonal2[i][j]);
	        	aTmpWinningPositions[iTmpWinningPosition][0] = i;
	          	aTmpWinningPositions[iTmpWinningPosition][1] = j;
	          	iTmpWinningPosition++;
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
	    			  
	    			if (aWindowArray[0] != 0 && getOccurrencesInIntArray(aWindowArray, aWindowArray[0]) == 4) 
	  				{
	    				if (aWinningPositions != null) //Inserisco nell array aWinningPosition le pedine vincenti
	    				  {
	    					  aWinningPositions[0][1] = aTmpWinningPositions[j][0];
	    					  aWinningPositions[0][0] = Math.abs(aTmpWinningPositions[j][1]-5);
	    					  aWinningPositions[1][1] = aTmpWinningPositions[j+1][0];
	    					  aWinningPositions[1][0] = Math.abs(aTmpWinningPositions[j+1][1]-5);
	    					  aWinningPositions[2][1] = aTmpWinningPositions[j+2][0];
	    					  aWinningPositions[2][0] = Math.abs(aTmpWinningPositions[j+2][1]-5);
	    					  aWinningPositions[3][1] = aTmpWinningPositions[j+3][0];
	    					  aWinningPositions[3][0] = Math.abs(aTmpWinningPositions[j+3][1]-5);
	    				  }
	    				
	  					return aWindowArray[0];
	  				}
	    		  }
	    	  }
	      }
	      aDiagonalUpRightArray.clear();
	    }
		
		
		return 0;
	}
	
	
	private boolean isBeetween2Doubles(double N, double N1, double N2) 
	{
		if (N > N1 && N <N2)
			return true;
		return false;
	}
	
	/**
	 * <p>Funzione per ottenere la matrice primitiva [int][int] dalla matrice principale [CCell][CCell]</p>
	 * @return matrice primitiva [int][int]
	 */
	
	private int[][] getPrimitiveMatrix() //Ottiene la matrice primitiva di _aBoard, ossia una semplice matrice int[][] i cui valori valgono 0: Vuoto, 1:Player, 2: CPU
	{
		int aPrimitiveMatrix[][] = new int[6][7];
		
		for (int i=0;i<6;i++) 
		{
			for (int j=0;j<7;j++) 
			{
				if (_aBoard[i][j].getColorPiece() != null) 
				{
					switch (_aBoard[i][j].getColorPiece()) 
					{
						case PIECE_RED:
							aPrimitiveMatrix[i][j] = 1;
							break;
						case PIECE_YELLOW:
							aPrimitiveMatrix[i][j] = 2;
							break;
					}
				}else 
				{
					aPrimitiveMatrix[i][j] = 0;
				}
			}
		}
		
		return aPrimitiveMatrix;
	}
	
	/**
	 * <p>Controlla quanti elementi int ci sono in un array[int]</p>
	 * @param aArray Array da controllare
	 * @param iKey Chiave da cercare
	 * @return Numero di occorrenze
	 */
	
	public int getOccurrencesInIntArray(int[] aArray, int iKey) //Controlla quanti elementi iKey ci sono in un array di Int
	{
		int iOccurrences = 0;
		
		for (int i=0;i<aArray.length;i++) 
		{
			if (aArray[i] == iKey)
				iOccurrences++;
		}
		
		return iOccurrences;
	}
	
	private void unload() //Funzione per chiudere la pagina
	{
		_oGameController.unload(); //Chiude il controller e rimuove gli input
		_oGameView.unload();	//Chiude l oggetto view della classe
		_oMainApp.getRootLayout().setCenter(null); //Rimuove CGame dalla schermata
	}
	
	public void setMouseMove(double iX, double iY) //Funzione per aggiornare i valori di posizione del mouse
	{
		_fXMouse = iX;
		_fYMouse = iY;
	}
	
	public void setMouseClick() //Funzione per settare il click del mouse
	{
		_bMouseClicked = true;
	}
}
