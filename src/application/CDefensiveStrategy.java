package application;

import java.util.ArrayList;

/**
 * <p>Classe che rappresenta la strategia difensiva della CPU</p>
 * @author Ciro Cozzolino
 */

public class CDefensiveStrategy implements ICPUStrategy{ //Strategia difensiva (Facile)

	
	@Override
	public int getBestColumn(int[][] aBoard) {
		int aWeightedValues[] = new int[6];
		ArrayList<Integer> aBestIndexes = new ArrayList<>();
		int aVerticalPoints[] = new int[6];
		int aHorizontalPoints[] = new int[6];
		int aDiagonalRightPoints[] = new int[6];
		int aDiagonalLeftPoints[] = new int[6];
		int iMaxiestValue;
		int iBestIndex;
		
		for (int i=0;i<aBoard.length;i++) //Prende tutti i valori e li inserisce nei rispettivi array (tutti dello stesso size delle colonne della matrice)
		{
			if (aBoard[i][0] != 0) 
			{
				aVerticalPoints[i] = -1;
				aHorizontalPoints[i] = -1;
				aDiagonalRightPoints[i] = -1;
				aDiagonalLeftPoints[i] = -1;
			}else 
			{
				aVerticalPoints[i] = verticalCheck(aBoard[i]); //Controllo verticale del punteggio
				aHorizontalPoints[i] = horizontalCheck(aBoard,i); //Controllo orizzontale del punteggio
				aDiagonalRightPoints[i] = diagonalRightCheck(aBoard, i); //Controllo diagonale alta destra del punteggio
				aDiagonalLeftPoints[i] = diagonalLeftCheck(aBoard, i); //Controllo diagonale alta sinistra del punteggio
			}
		}
		
		iMaxiestValue = 0;
		
		for (int i=0;i<aBoard.length;i++) //Prende un solo valore, quello migliore di ogni combo per colonna e lo inserisce nell'array dei migliori valori
		{
			int iMaxValue = aVerticalPoints[i];
			if (aHorizontalPoints[i] > iMaxValue)
				iMaxValue = aHorizontalPoints[i];
			if (aDiagonalRightPoints[i] > iMaxValue)
				iMaxValue = aDiagonalRightPoints[i];
			if (aDiagonalLeftPoints[i] > iMaxValue) 
				iMaxValue = aDiagonalLeftPoints[i];
			
			aWeightedValues[i] = iMaxValue;
			
			if (iMaxValue > iMaxiestValue)
				iMaxiestValue = iMaxValue;
		}
		
		
		for (int i=0;i<aBoard.length;i++) //Prende tutti gli indici delle colonne col valore di punteggio più alto e li inserisce nell Arraylist dei migliori indici
		{
			if (aWeightedValues[i] == iMaxiestValue) 
				aBestIndexes.add(i);
		}
		
		iBestIndex = aBestIndexes.get(0);
		
		for (int i=1; i<aBestIndexes.size();i++) //Ricava il miglior indice con meno elementi nella colonna
		{
			if (aBoard[aBestIndexes.get(i)].length < aBoard[iBestIndex].length) 
			{
				iBestIndex = aBestIndexes.get(i);
			}else if (aBoard[aBestIndexes.get(i)].length == aBoard[iBestIndex].length) 
			{
				if (aBestIndexes.get(i) >= 2 && aBestIndexes.get(i) <=4) 
				{
					iBestIndex = aBestIndexes.get(i);
				}
			}
		}
		
		return iBestIndex; //ritorna il risultato
	}
	
	//pedina rossa = player, pedina gialla = avversario
	
	/**
	 * <p>Controllo verticale del punteggio</p>
	 * @param aBoardColumn Array che rappresenta la colonna da verificare.
	 * @return Ritorna lo score della colonna.
	 */
	
	private int verticalCheck(int[] aBoardColumn) 
	{
		int iPoints;
		int i=1;
		while (i < 6 && aBoardColumn[i] == 0) //trova la prima pedina di questa colonna
		{
			i++;
		}
		
		if (aBoardColumn[i] == 0 || aBoardColumn[i] == 2) 
		{
			//se non trova nessuna pedina o la prima pedina è gialla punteggio minimo
			return 0;
		}else
		{
			//se la prima è rossa continua a cercare finché non trova una pedina gialla o la scacchiera finisce
			//e incrementa di un punto per ogni pedina rossa trovata
			iPoints = 1;
			while(i < 6 && aBoardColumn[i] == 1) 
			{
				iPoints++;
				i++;
			}
			return iPoints;
		}
	}
	
	/**
	 * <p>Controllo del punteggio orizzontale</p>
	 * @param aBoard rappresenta la matrice della tavola da gioco.
	 * @param iStart rappresenta l'indice di colonna da cui partire.
	 * @return Ritorna il punteggio della riga orizzontale.
	 */
	
	private int horizontalCheck(int[][] aBoard, int iStart) 
	{
		int iStartingI = iStart;
		int iLeftI = iStartingI-1;
		int iRightI = iStartingI+1;
		int iPointsLeft = 0;
		int iPointsRight = 0;
		int i=1;
		boolean bClosedLeft = false;
		boolean bClosedRight = false;
		while (i<6 && aBoard[iStartingI][i] == 0) //trova la prima pedina
		{
			i++;
		}
		if (aBoard[iStartingI][i] != 0) //se esiste una prima pedina passa alla colonna libera precedente
		{
			i--;
		}
		
		while (iLeftI > -1 && aBoard[iLeftI][i] == 1) 
			//controllo se sulla sinistra ci sono altre pedine rosse e incrementa il punteggio di conseguenza,
			//finche non finisce la scacchiera o trova una pedina gialla o uno spazio vuoto
		{
			iPointsLeft++;
			iLeftI --;
		}
		if (iLeftI < 0 || aBoard[iLeftI][i] == 2) 
		{
			//se finisce la scacchiera o trova una pedina gialla tengo presente che la combo non puo essere continuata
			//a sinistra
			bClosedLeft = true;
		}
		
		while (iRightI < 6 && aBoard[iRightI][i] == 1) 
		{
			//controllo se sulla destra ci sono altre pedine rosse e incrementa il punteggio di conseguenza,
			//finche non finisce la scacchiera o trova una pedina gialla o uno spazio vuoto
			iPointsRight++;
			iRightI ++;
		}
		if (iRightI > 5 || aBoard[iRightI][i] == 2) 
		{
			//se finisce la scacchiera o trova una pedina gialla tengo presente che la combo non puo essere continuata
			//a destra
			bClosedRight = true;
		}
		
		if (bClosedRight && bClosedLeft) //Se entrambi i lati sono chiusi controlla che la somma dei punteggi
										//destra e sinistra non sia esattamente 3, in tal caso massima priorita
		{
			if (iPointsLeft+iPointsRight == 3) 
			{
				return 3;
			}
			return 0;	//se entrambi sono chiusi e non ci sono 3 pedine totali a destra e sinistra priorità minima
		}else 
		{
			return iPointsLeft+iPointsRight; //se almeno un estremità è aperta allora ritorna il numero di pedine rosse
			//a destra e a sinistra
		}
	}
	
	/**
	 * <p>Controllo diagonale, della diagonale alta destra del punteggio</p>
	 * @param aBoard rappresenta la matrice della tavola da gioco.
	 * @param iStart rappresenta la coordinata della colonna da cui partire.
	 * @return ritorna il punteggio della diagonale alta destra.
	 */
	
	private int diagonalRightCheck(int[][] aBoard, int iStart) 
	{
		int iStartingI = iStart;
		int i = 0;
		int iDiagonalUpRightColumn;
		int iDiagonalUpRightRow;
		int iDiagonalDownLeftColumn;
		int iDiagonalDownLeftRow;
		int iPointsDownLeft = 0;
		int iPointsUpRight = 0;
		boolean bClosedLeftDown = false;
		boolean bClosedRightUp = false;
		while (i<6 && aBoard[iStartingI][i] == 0) //trova la prima pedina
		{
			i++;
		}
		if (aBoard[iStartingI][i] != 0) //se esiste una prima pedina passa alla colonna libera precedente
		{
			i--;
		}
		
		iDiagonalDownLeftColumn = iStartingI-1;
		iDiagonalDownLeftRow = i + 1;
		iDiagonalUpRightColumn = iStartingI+1;
		iDiagonalUpRightRow = i - 1;
		
		while (iDiagonalDownLeftColumn > -1 && iDiagonalDownLeftRow < 7 && aBoard[iDiagonalDownLeftColumn][iDiagonalDownLeftRow] == 1) 
			//controllo se sulla diagonale sinistra verso il basso ci sono altre pedine rosse e incrementa il punteggio di conseguenza,
			//finche non finisce la scacchiera o trova una pedina gialla o uno spazio vuoto
		{
			iPointsDownLeft++;
			iDiagonalDownLeftColumn --;
			iDiagonalDownLeftRow++;
		}
		if (iDiagonalDownLeftColumn < 0 || iDiagonalDownLeftRow > 6 || aBoard[iDiagonalDownLeftColumn][iDiagonalDownLeftRow] == 2) 
		{
			//se finisce la scacchiera o trova una pedina gialla tengo presente che la combo non puo essere continuata
			//nella diagonale sinistra bassa
			bClosedLeftDown = true;
		}
		
		while (iDiagonalUpRightColumn < 6 && iDiagonalUpRightRow > -1 && aBoard[iDiagonalUpRightColumn][iDiagonalUpRightRow] == 1) 
		{
			//controllo se sulla diagonale destra alta ci sono altre pedine rosse e incrementa il punteggio di conseguenza,
			//finche non finisce la scacchiera o trova una pedina gialla o uno spazio vuoto
			iPointsUpRight++;
			iDiagonalUpRightColumn ++;
			iDiagonalUpRightRow --;
		}
		if (iDiagonalUpRightColumn > 5 || iDiagonalUpRightRow < 0 || aBoard[iDiagonalUpRightColumn][iDiagonalUpRightRow] == 2) 
		{
			//se finisce la scacchiera o trova una pedina gialla tengo presente che la combo non puo essere continuata
			//nella diagonale destra alta
			bClosedRightUp = true;
		}
		
		if (bClosedLeftDown && bClosedRightUp) //Se entrambe le diagonali sono chiuse controlla che la somma dei punteggi
										//destra e sinistra non sia esattamente 3, in tal caso massima priorita
		{
			if (iPointsDownLeft+iPointsUpRight >= 3) 
			{
				return iPointsDownLeft+iPointsUpRight;
			}
			return 0;	//se entrambi sono chiusi e non ci sono 3 pedine totali a destra e sinistra priorità minima
		}else 
		{
			return iPointsDownLeft+iPointsUpRight; //se almeno un estremità è aperta allora ritorna il numero di pedine rosse
			//a destra e a sinistra
		}
	}


	/**
	 * <p>Controllo diagonale, della diagonale alta sinistra del punteggio</p>
	 * @param aBoard rappresenta la matrice della tavola da gioco.
	 * @param iStart rappresenta la coordinata della colonna da cui partire.
	 * @return ritorna il punteggio della diagonale alta sinistra.
	 */
	
	private int diagonalLeftCheck(int[][] aBoard, int iStart) 
	{
		int iStartingI = iStart;
		int i = 0;
		int iDiagonalUpLeftColumn;
		int iDiagonalUpLeftRow;
		int iDiagonalDownRightColumn;
		int iDiagonalDownRightRow;
		int iPointsDownRight = 0;
		int iPointsUpLeft = 0;
		boolean bClosedRightDown = false;
		boolean bClosedLeftUp = false;
		
		while (i<6 && aBoard[iStartingI][i] == 0) //trova la prima pedina
		{
			i++;
		}
		if (aBoard[iStartingI][i] != 0) //se esiste una prima pedina passa alla colonna libera precedente
		{
			i--;
		}
		
		iDiagonalUpLeftColumn = iStartingI-1;
		iDiagonalUpLeftRow = i - 1;
		iDiagonalDownRightColumn = iStartingI+1;
		iDiagonalDownRightRow = i + 1;
		
		while (iDiagonalUpLeftColumn > -1 && iDiagonalUpLeftRow > -1 && aBoard[iDiagonalUpLeftColumn][iDiagonalUpLeftRow] == 1) 
			//controllo se sulla diagonale sinistra verso l'alto ci sono altre pedine rosse e incrementa il punteggio di conseguenza,
			//finche non finisce la scacchiera o trova una pedina gialla o uno spazio vuoto
		{
			iPointsUpLeft++;
			iDiagonalUpLeftColumn --;
			iDiagonalUpLeftRow--;
		}
		if (iDiagonalUpLeftColumn < 0 || iDiagonalUpLeftRow <0 || aBoard[iDiagonalUpLeftColumn][iDiagonalUpLeftRow] == 2) 
		{
			//se finisce la scacchiera o trova una pedina gialla tengo presente che la combo non puo essere continuata
			//nella diagonale sinistra alta
			bClosedLeftUp = true;
		}
		
		while (iDiagonalDownRightColumn < 6 && iDiagonalDownRightRow < 7 && aBoard[iDiagonalDownRightColumn][iDiagonalDownRightRow] == 1) 
		{
			//controllo se sulla diagonale destra bassa ci sono altre pedine rosse e incrementa il punteggio di conseguenza,
			//finche non finisce la scacchiera o trova una pedina gialla o uno spazio vuoto
			iPointsDownRight++;
			iDiagonalDownRightColumn ++;
			iDiagonalDownRightRow ++;
		}
		if (iDiagonalDownRightColumn > 5 || iDiagonalDownRightRow > 6 || aBoard[iDiagonalDownRightColumn][iDiagonalDownRightRow] == 2) 
		{
			//se finisce la scacchiera o trova una pedina gialla tengo presente che la combo non puo essere continuata
			//nella diagonale destra bassa
			bClosedRightDown = true;
		}
		
		if (bClosedLeftUp && bClosedRightDown) //Se entrambe le diagonali sono chiuse controlla che la somma dei punteggi
										//destra e sinistra non sia esattamente 3, in tal caso massima priorita
		{
			if (iPointsUpLeft+iPointsDownRight >= 3) 
			{
				return iPointsUpLeft+iPointsDownRight;
			}
			return 0;	//se entrambi sono chiusi e non ci sono 3 pedine totali a destra e sinistra priorità minima
		}else 
		{
			return iPointsUpLeft+iPointsDownRight; //se almeno un estremità è aperta allora ritorna il numero di pedine rosse
			//a destra e a sinistra
		}
	}
	
}
