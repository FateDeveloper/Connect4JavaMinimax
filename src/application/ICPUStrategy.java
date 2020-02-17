package application;

/**
 * <p>Interfaccia per lo Strategy Pattern</p>
 * @author Ciro Cozzolino
 */

public interface ICPUStrategy { //Interfaccia per lo Strategy Pattern
	/**
	 * <p>Funzione per ottenere la migliore mossa secondo l'attuale strategia</p>
	 * @param aPrimitiveBoard Matrice primitiva della tavola da gioco
	 * @return Int (colonna migliore)
	 */
	public int getBestColumn(int[][] aPrimitiveBoard);
}
