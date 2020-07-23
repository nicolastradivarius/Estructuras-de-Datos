package TDAArbol;

import TDALista.*;

/**
 * Modela la implementación de la interface {@link Position} a través de un nodo de árbol.
 * @author Nicolás González
 * @param <E> Tipo de elemento de la interface Position
 */
public class TNodo<E> implements Position<E> {

	private E elemento;
	private TNodo<E> padre;
	private PositionList<TNodo<E>> hijos;

	/**
	 * Crea un nodo de árbol con elemento y padre pasados por parámetro.
	 * @param elemento Elemento que va a almacenar el nodo.
	 * @param padre Padre del nodo.
	 */
	public TNodo(E elemento, TNodo<E> padre) {
		this.elemento = elemento;
		this.padre = padre;
		hijos = new ListaDoblementeEnlazada<TNodo<E>>();
	}

	/**
	 * Crea un nodo de árbol con elemento pasado por parámetro y sin padre.
	 * @param elemento Elemento que va a almacenar el nodo.
	 */
	public TNodo(E elemento) {
		this (elemento, null);
	}

	@Override
	public E element() {
		return elemento;
	}

	/**
	 * Devuelve la lista de nodos hijos del nodo.
	 * @return Lista que contiene a los nodos hijos del nodo.
	 */
	public PositionList<TNodo<E>> getHijos() {
		return hijos;
	}

	/**
	 * Reemplaza el elemento contenido por el nodo, por el elemento parametrizado.
	 * @param elemento Elemento que se va a almacenar en el nodo.
	 */
	public void setElemento(E elemento) {
		this.elemento = elemento;
	}

	/**
	 * Retorna el nodo padre del nodo.
	 * @return El nodo padre del nodo.
	 */
	public TNodo<E> getPadre() {
		return padre;
	}

	/**
	 * Modifica el nodo padre del nodo por el parametrizado.
	 * @param padre Nuevo padre del nodo.
	 */
	public void setPadre(TNodo<E> padre) {
		this.padre = padre;
	}
	
	public String toString() {
		return "(" + this.elemento + ")";
	}
}