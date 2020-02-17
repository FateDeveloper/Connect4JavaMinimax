package application.viewcontroller;

import java.util.ArrayList;

import application.CCell;
import application.CGame;
import application.CPiece;
import application.CPreloader;
import application.ESelectionColumns;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.transform.Rotate;

/**
 * <p>Classe View della schermata di gioco</p>
 * @author Ciro Cozzolino
 */

public class VCGame { //Classe per l'interfaccia grafica di gioco

	CGame _oMainClass; //Riferimento alla classe Model
	Rectangle _oRectangleEndGame; //Rettangolo di fine partita
	boolean _bRectangle; //Booleana per definire se il rettangolo è spawnato
	
	public VCGame(CGame oMainClass) 
	{
		_oMainClass = oMainClass;
		_bRectangle = false;
	}
	
	/**
	 * <p>Funzione di aggiornamento grafico, eseguita 1 volta per frame</p>
	 * @param t Tempo trascorso dallo scorso frame
	 * @param oGC Graphics Context su cui disegnare gli elementi
	 * @param bPlayerTurn booleana che definisce di chi è il turno
	 * @param ePreSelectedColumn enum che definisce quale colonna l'utente sta pre-selezionando
	 * @param aPieces lista delle pedine in gioco
	 * @param iGameOver stato della partita
	 * @param bGameOver booleana per sapere se la partita è finita
	 * @param bEndGameDelay booleana per elementi post end-game
	 */
	
	public void update(double t,GraphicsContext oGC,boolean bPlayerTurn,ESelectionColumns ePreSelectedColumn,ArrayList<CPiece> aPieces, int iGameOver, boolean bGameOver, boolean bEndGameDelay) 
	{
		oGC.drawImage(CPreloader.getIstance().getImage("bg_game"), 0, 0); //disegna il background
		
		if (bPlayerTurn && !bGameOver) //Se è il turno del player e la partita non è finita:
		{
			switch (ePreSelectedColumn) //Disegna la pre-selezione della pedina
			{
				case FIRST_COLUMN:
					oGC.drawImage(CPreloader.getIstance().getImage("piece_red"), 214, 80);
					break;
				case SECOND_COLUMN:
					oGC.drawImage(CPreloader.getIstance().getImage("piece_red"), 294, 80);
					break;
				case THIRD_COLUMN:
					oGC.drawImage(CPreloader.getIstance().getImage("piece_red"), 374, 80);
					break;
				case FOURTH_COLUMN:
					oGC.drawImage(CPreloader.getIstance().getImage("piece_red"), 454, 80);
					break;
				case FIFTH_COLUMN:
					oGC.drawImage(CPreloader.getIstance().getImage("piece_red"), 534, 80);
					break;
				case SIXTH_COLUMN:
					oGC.drawImage(CPreloader.getIstance().getImage("piece_red"), 614, 80);
					break;
				case NULL:
					break;
			}
			
		}
		for (int i=0; i<aPieces.size();i++) //Disegna tutte le pedine
		{
			oGC.drawImage(CPreloader.getIstance().getImage(aPieces.get(i).getImageSz()), aPieces.get(i).getX(), aPieces.get(i).getY());
		}
		
		oGC.drawImage(CPreloader.getIstance().getImage("board"), 205, 165); //Disegna la tavola da gioco
		
		if (bEndGameDelay) //Dopo un delay, se la partita è finita disegna gli elementi di fine gioco 
		{
			drawEndGameGraphics(oGC, _oMainClass.getEndText(), _oMainClass.getBoard(), _oMainClass.getWinningPieces(), iGameOver);
		}
	}
	
	/**
	 * Funzione per disegnare gli elementi di end game
	 * @param oGC Graphics Context su cui disegnare gli elementi
	 * @param szEndText Testo di fine partita
	 * @param aBoard Tavola da gioco
	 * @param aWinningPieces Array[int][int] 2x4 contenenti gli indici delle pedine vincenti
	 * @param iGameOver esito finale della partita
	 */
	
	public void drawEndGameGraphics(GraphicsContext oGC, String szEndText, CCell[][] aBoard, int[][] aWinningPieces,int iGameOver) 
	{
		drawTextEndGame(oGC,szEndText); //Disenga il testo di fine gioco
		if (iGameOver != 3 && !_bRectangle) //Se non è pareggio disegna il rettangolo
    		drawRectEndGame(oGC,aBoard,aWinningPieces);
	}
	
	private void drawTextEndGame(GraphicsContext oGC, String szEndText) 
	{
		oGC.setFill(Color.BLUEVIOLET);
		oGC.setStroke(Color.BLACK);
		oGC.setLineWidth(2);
		Font theFont = Font.font("Comic Sans MS", FontWeight.BOLD,45);
		oGC.setFont(theFont);
		oGC.fillText(szEndText, 200, 90);
		oGC.strokeText(szEndText, 200, 90);
		oGC.fillText("Clicca per andare avanti", 190, 830);
		oGC.strokeText("Clicca per andare avanti", 190, 830);
	}
	
	private void drawRectEndGame(GraphicsContext oGC,CCell[][] aBoard,int[][] aWinningPieces) 
	{
		float iX = aBoard[aWinningPieces[0][0]][aWinningPieces[0][1]].getCenterX();
		float iY = aBoard[aWinningPieces[0][0]][aWinningPieces[0][1]].getCenterY();
		float iWidth = 0;
		float iHeight = 0;
		
		Rotate oRotate = new Rotate();
		
		if (aWinningPieces[0][0] == aWinningPieces[3][0]) 
		{
			iWidth = 260;
			iHeight = 20;
			oRotate.setPivotX(iX+iHeight/2);
			oRotate.setPivotY(iY);
			oRotate.setAngle(90);
		}
		else if (aWinningPieces[0][1] == aWinningPieces[3][1]) 
		{
			iWidth = 260;
			iHeight = 20;
			iX = iX-8;
			iY = iY-10;
			oRotate.setPivotX(iX);
			oRotate.setPivotY(iY);
			oRotate.setAngle(0);
		}else if (aWinningPieces[0][0] > aWinningPieces[3][0] && aWinningPieces[0][1] < aWinningPieces[3][1]) 
		{
			iWidth = 350;
			iHeight = 20;
			oRotate.setPivotX(iX+5);
			oRotate.setPivotY(iY+5);
			oRotate.setAngle(135);
		}else 
		{
			iWidth = 350;
			iHeight = 20;
			oRotate.setPivotX(iX);
			oRotate.setPivotY(iY+5);
			oRotate.setAngle(-135);
		}
		
		
		
		_oRectangleEndGame = new Rectangle(iX,iY,iWidth,iHeight);
		_oRectangleEndGame.setFill(Color.LIMEGREEN);
		_oRectangleEndGame.setStroke(Color.BLACK);
		_oRectangleEndGame.getTransforms().add(oRotate);
		_oMainClass.getMainApp().getRootLayout().getChildren().add(_oRectangleEndGame);
		_bRectangle = true;
	}
	
	public void unload() 
	{
		_oMainClass.getMainApp().getRootLayout().getChildren().remove(_oRectangleEndGame);
	}
}
