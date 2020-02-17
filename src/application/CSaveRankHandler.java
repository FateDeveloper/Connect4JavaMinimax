package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

/**
 * <p>Classe per la gestione del salvataggio e del caricamento della classifica</p>
 * @author Ciro Cozzolino
 */
public class CSaveRankHandler { //Classe Singleton per gestire il salvataggio e il caricamento della classifica
		private static CSaveRankHandler ISTANCE;
		private  String _szFile;
		private ObjectInputStream _oObjectInputStream;
		private ObjectOutputStream _oObjectOutputStream;
		private   HashMap<String,Integer> _aRanks; //Hashmap per gestire la classifica
		
		public static CSaveRankHandler getIstance() 
		{
			if (ISTANCE == null) 
			{
				ISTANCE = new CSaveRankHandler();
				ISTANCE.init();
				
			}
			
			return ISTANCE;
		}
		
		/**
		 * <p>Funzione per l'inizializzazione dell'hashmap contenente la classifica (Lo carica se esiste)</p>
		 */
		
		@SuppressWarnings("unchecked")
		private void init() //Carica la classifica precedente se esiste, altrimenti la crea
		{
			_szFile = "."+System.getProperty("file.separator")+"savedatas"+System.getProperty("file.separator")+"ranks";
			if (ISTANCE.existAndIsNotEmpty()) 
			{
				try 
				{
					_oObjectInputStream = new ObjectInputStream(new FileInputStream(_szFile));
					_aRanks = (HashMap<String,Integer>)_oObjectInputStream.readObject();
					_oObjectInputStream.close();
				}catch(IOException e) 
				{
					e.printStackTrace();
				}catch(ClassNotFoundException e) 
				{
					e.printStackTrace();
				}
			}else 
				_aRanks = new HashMap<String,Integer>();
		}
		
		/**
		 * <p>Aggiunge alla classifica un nuovo elemento, o incrementa il valore di uno esistente e poi salva sul file della classifica</p>
		 * @param szNameSurname Nome e Cognome utente
		 */
		
		public void addNewScore(String szNameSurname) //Aggiunge un nuovo elemento all hashmap della classifica e salva sul file
		{
			if (_aRanks.containsKey(szNameSurname))
				_aRanks.put(szNameSurname,_aRanks.get(szNameSurname)+1);
			else
				_aRanks.put(szNameSurname,1);
			
			try 
			{
				_oObjectOutputStream = new ObjectOutputStream(new FileOutputStream(_szFile));
				_oObjectOutputStream.writeObject(_aRanks);
				_oObjectOutputStream.close();
			}catch(IOException e) 
			{
				e.printStackTrace();
			}
			
		}
		
		/**
		 * <p>Funzione per ricavare l'HashMap della classifica</p>
		 * @return HashMap(String,Integer) Nome e N.Partite Vinte
		 */
		public HashMap<String,Integer> getRanks() //Funzione per ottenere la classifica
		{
			return _aRanks;
		}
		
		public boolean existAndIsNotEmpty() //Controlla se il file esiste e non è vuoto
		{
			File oFile = new File(_szFile);
			if (oFile.exists() && oFile.length() > 0)
				return true;
			return false;
		}
}
