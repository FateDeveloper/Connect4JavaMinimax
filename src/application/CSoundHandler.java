package application;

import javax.sound.sampled.Clip;

/**
 * <p>Classe singleton per gestire la riproduzione dei suoni</p>
 * @author Ciro Cozzolino
 */

public  class CSoundHandler { //Classe per gestire la riproduzione dei suoni 
	private static CSoundHandler ISTANCE;
	
	public static CSoundHandler getIstance() 
	{
			if (ISTANCE == null)
				ISTANCE = new CSoundHandler();
			return ISTANCE;
	}
	
	/**
	 * <p>Funzione che come input chiede, il nome della traccia e il numero di volte che deve essere ripetuta</p>
	 * @param szSound Stringa che rappresenta il nome della traccia
	 * @param iLoop Integer che rappresenta il numero di volte che deve essere ripetuta
	 */
	
	public void playSound(String szSound, int iLoop) //Riproduce il suono in base al nome e a quante volte deve ripetersi
	{
		Clip oClip;
		oClip = CPreloader.getIstance().getClip(szSound);
		oClip.setFramePosition(0);
		oClip.loop(iLoop);
		oClip.start();
	}
}
