package application.viewcontroller;


import java.util.HashMap;
import java.util.Map.Entry;

import application.CRankMenu;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * <p>Classe Input Controller della schermata di classifica degli score</p>
 * @author Ciro Cozzolino
 */

public class ICCRankMenu {
	
	CRankMenu _oMainClass;
	
	Label[] _aLabelsName;
	Label[] _aLabelsScore;
	
	@FXML
	Label _oLabelName0;
	
	@FXML
	Label _oLabelName1;

	@FXML
	Label _oLabelName2;

	@FXML
	Label _oLabelName3;
	
	@FXML
	Label _oLabelName4;
	
	@FXML
	Label _oLabelName5;
	
	@FXML
	Label _oLabelName6;
	
	@FXML
	Label _oLabelName7;
	
	@FXML
	Label _oLabelName8;
	
	@FXML
	Label _oLabelName9;
	
	@FXML
	Label _oLabelScore0;
	
	@FXML
	Label _oLabelScore1;
	
	@FXML
	Label _oLabelScore2;
	
	@FXML
	Label _oLabelScore3;
	
	@FXML
	Label _oLabelScore4;
	
	@FXML
	Label _oLabelScore5;
	
	@FXML
	Label _oLabelScore6;
	
	@FXML
	Label _oLabelScore7;
	
	@FXML
	Label _oLabelScore8;
	
	@FXML
	Label _oLabelScore9;
	
	@FXML
	private void initialize() 
	{
		_aLabelsName = new Label[10]; //Array lables dei nomi nella classifica
		_aLabelsScore = new Label[10]; //Array lables dei punteggi nella classifica
		_aLabelsName[0] = _oLabelName0;
		_aLabelsScore[0] = _oLabelScore0;
		_aLabelsName[1] = _oLabelName1;
		_aLabelsScore[1] = _oLabelScore1;
		_aLabelsName[2] = _oLabelName2;
		_aLabelsScore[2] = _oLabelScore2;
		_aLabelsName[3] = _oLabelName3;
		_aLabelsScore[3] = _oLabelScore3;
		_aLabelsName[4] = _oLabelName4;
		_aLabelsScore[4] = _oLabelScore4;
		_aLabelsName[5] = _oLabelName5;
		_aLabelsScore[5] = _oLabelScore5;
		_aLabelsName[6] = _oLabelName6;
		_aLabelsScore[6] = _oLabelScore6;
		_aLabelsName[7] = _oLabelName7;
		_aLabelsScore[7] = _oLabelScore7;
		_aLabelsName[8] = _oLabelName8;
		_aLabelsScore[8] = _oLabelScore8;
		_aLabelsName[9] = _oLabelName9;
		_aLabelsScore[9] = _oLabelScore9;
	}
	
	public void setMainClass(CRankMenu oMainClass) 
	{
		this._oMainClass = oMainClass;
	}
	
	public void setContent(HashMap<String,Integer> aValues) 
	{
		int i = 0;
		for (Entry<String,Integer> oEntry : aValues.entrySet()) 
		{
			_aLabelsName[i].setText(oEntry.getKey());
			_aLabelsScore[i].setText(oEntry.getValue().toString());
			i++;
			if (i>9)
				break;
		}
	}
	
	@FXML
	private void onMenuButton() //Funzione per il tasto torna al menu principale
	{
		_oMainClass.gotoNextMenu();
	}
	
	@FXML
	private void onRestartButton() //Funzione per il tasto riavvia partita
	{
		_oMainClass.restartGame();
	}
	
}
