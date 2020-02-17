package application;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import application.viewcontroller.ICCRankMenu;
import javafx.fxml.FXMLLoader;
/**
 * <p>Estensione di ACScreen, schermata di classifica</p>
 * @author Ciro Cozzolino
 */
public class CRankMenu extends ACScreen{ //Schermata della classifica

	HashMap<String,Integer> _aRank;
	
	public CRankMenu(MainApp oMainApp) 
	{
		super(oMainApp,"viewcontroller/VCRankMenu.fxml");
	}
	
	/**
	 * <p>Funzione per ordinare l'hashmap in base al secondo valore, score (Integer)</p>
	 * @param aValues HashMap Stringa/Valore
	 * @return Hashmap Stringa/Valore ordinato per Valore Maggiore
	 */
	
	private HashMap<String,Integer> sortByValue(HashMap<String,Integer> aValues) //Ordino l'hashmap dei giocatori con il loro punteggio in base al punteggio più alto
	{
		List<Map.Entry<String,Integer>> oList = new LinkedList<Map.Entry<String,Integer>>(aValues.entrySet());
		
		Collections.sort(oList,new Comparator<Map.Entry<String,Integer>>(){

			@Override
			public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
				return (o2.getValue().compareTo(o1.getValue()));
			}});
		
		
		HashMap<String,Integer> aTemp = new LinkedHashMap<String,Integer>();
		for (Map.Entry<String,Integer> aa: oList) 
		{
			aTemp.put(aa.getKey(),aa.getValue());
		}
		return aTemp;
	}
	
	@Override
	public void gotoNextMenu() //Funzione per tornare al menu principale
	{
		this.unload();
		super._oMainApp.gotoMenu(EScreens.AUTHENTICATION_MENU);
	}
	
	public void restartGame() //funzione per riavviare la partita
	{
		this.unload();
		super._oMainApp.initSaveData();
		super._oMainApp.gotoGame();
	}
	
	@Override
	protected void unload() 
	{
		super._oMainApp.getRootLayout().setCenter(null);
	}

	@Override
	protected void initController(FXMLLoader oLoader) {
		ICCRankMenu oController = oLoader.getController();
        oController.setMainClass(this);
        _aRank = CSaveRankHandler.getIstance().getRanks();
        _aRank = sortByValue(_aRank);
        oController.setContent(_aRank);
		
	}
}
