package TDAConjunto;

import java.util.Iterator;

public interface Conjunto<E> {

	/**
	 * Inserta un item en el conjunto
	 * @param item
	 */
	public void agregar(E item);
	
	/**
	 * Elimina el elemento especificado del conjunto
	 * @param item
	 */
	public void eliminar(E item);
	
	/**
	 * Retorna si un elemento pertenece al conjunto
	 * @param item
	 * @return
	 */
	public boolean pertenece(E item);
	
	/**
	 * Realiza la union con otro conjunto actualizando el conjunto receptor del mensaje
	 * @param c
	 */
	public void union(Conjunto<E> c);
	
	/**
	 *  Realiza la interseccion con otro conjunto actualizando el conjunto receptor del mensaje.
	 * @param c
	 */
	public void interseccion(Conjunto<E> c);
	
	/**
	 * Retorna un iterador con los elementos del conjunto
	 * @return
	 */
	public Iterator<E> iterator();
}