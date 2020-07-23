package TDAPila;

/**
 * Modela la implementación de un nodo compuesto por el elemento que contiene, 
 * y la referencia al nodo siguiente en caso de formar parte de una estructura enlazada.
 * @author Nicolás González
 * @param <T> Tipo de dato del nodo.
 */
public class Nodo<T> {

	private T elemento;
	private Nodo<T> siguiente;

	/**
	 * Crea un nodo con el elemento y referencia al nodo siguiente pasados por parámetro.
	 * @param item El elemento que el nodo va a contener.
	 * @param sig El nodo siguiente al nodo creado.
	 */
	public Nodo(T item, Nodo<T> sig){
		this.elemento=item; 
		this.siguiente=sig; 
	}

	/**
	 * Crea un nodo sólamente con el elemento que va a contener, pasado por parámetro.
	 * @param item El elemento que el nodo va a contener.
	 */
	public Nodo(T item) { 
		this(item,null); 
	}

	/**
	 * Reemplaza el elemento contenido por el nodo al cual se llamó la instrucción, por el pasado por parámetro.
	 * @param elemento Elemento a ser insertado en el nodo que recibió la instrucción.
	 */
	public void setElemento(T elemento) {
		this.elemento = elemento;
	}

	/**
	 * Establece la referencia al nodo siguiente en el nodo que recibió la instrucción.
	 * @param siguiente Nodo que será &quotsiguiente&quot al nodo que recibió la instrucción. 
	 */
	public void setSiguiente(Nodo<T> siguiente) {
		this.siguiente = siguiente;
	}

	/**
	 * Devuelve el elemento contenido en el nodo.
	 * @return El elemento que contiene el nodo que recibió la instrucción.
	 */
	public T getElemento() { 
		return this.elemento;	
	}

	/**
	 * Devuelve el nodo siguiente al nodo que recibió la instrucción.
	 * @return El nodo siguiente al nodo que recibió la instrucción.
	 */
	public Nodo<T> getSiguiente() { 
		return this.siguiente;
	}
}	