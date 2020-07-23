package TDABag;

public interface Bag<E> {

	/**
	 *  Inserta un item en el bag.
	 * @param item
	 */
	public void agregar(E item);
	
	/**
	 * Retorna verdadadero si el bag esta vacio y falso en caso contrario .
	 * @return
	 */
	public boolean esVacio();
	
	/**
	 *  Retorna la cantidad de elementos almacenados en el bag.
	 * @return
	 */
	public int cantElementos();
	
	/**
	 * Elimina una aparicion del elemento especificado del bag.
	 * @param item
	 */
	public void eliminar(E item);
	
	/**
	 * Retorna la cantidad de apariciones del elemento en el bag
	 * @param item
	 * @return
	 */
	public int cantidad(E item);
}
