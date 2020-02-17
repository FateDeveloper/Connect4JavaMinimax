package application;

/**
 * <p>Interfaccia per l'utilizzo del Prototype Pattern per le pedine</p>
 * @author Ciro Cozzolino
 */

public interface IPiece extends Cloneable{ //Interfaccia per il prototype pattern
	/**
	 * @return un nuovo elemento IPiece, uguale a quest'ultimo ma con un area di memoria differente
	 */
	public IPiece getClone();
}
