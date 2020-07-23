package TDALista;

/**
 * Modela la implementación de la interfaz Position a través de un nodo compuesto 
 * por un elemento, una referencia al nodo siguiente y una referencia al nodo anterior dentro 
 * de una estructura implementada a través de este tipo de nodos.
 * 
 * @author Nicolás González
 * @param <T> El tipo de elemento del nodo
 * @see Position
 */
public class DNodo<T> implements Position<T>{

	DNodo<T> anterior, siguiente;
	T elemento;

	/**
	 * Crea un nodo con elemento pasado por parámetro y sin referencias a otros nodos. 
	 * @param elemento Elemento a ser contenido por el nuevo nodo creado.
	 */
	public DNodo(T elemento) {
		this.elemento = elemento;
		anterior = siguiente = null;
	}

	/**
	 * Crea un nodo con elemento y referencias a nodo anterior y siguiente pasados por parámetro.
	 * @param anterior Nodo que va a ser considerado como &quotel anterior&quot al nuevo nodo creado.
	 * @param elemento Elemento a ser contenido por el nuevo nodo creado.
	 * @param siguiente Nodo que va a ser considerado como &quotel siguiente&quot al nuevo nodo creado.
	 */
	public DNodo( DNodo<T> anterior, T elemento, DNodo<T> siguiente) {
		this.anterior = anterior;
		this.elemento=elemento;
		this.siguiente = siguiente;
	}

	/**
	 * Reemplaza el elemento contenido por el nodo al cual se llamó la instrucción, por el pasado por parámetro.
	 * @param elemento Elemento a ser insertado en el nodo que recibió la instrucción.
	 */
	public void setElemento(T elemento) {
		this.elemento = elemento;
	}

	/**
	 * Establece el nodo anterior en el nodo que recibió la instrucción.
	 * @param nodo Nodo que será &quotanterior&quot al nodo que recibió la instrucción. 
	 */
	public void setAnterior(DNodo<T> nodo) {
		this.anterior = nodo;
	}

	/**
	 * Establece la referencia al nodo siguiente en el nodo que recibió la instrucción.
	 * @param nodo Nodo que será &quotsiguiente&quot al nodo que recibió la instrucción. 
	 */
	public void setSiguiente(DNodo<T> nodo) {
		this.siguiente = nodo;
	}

	/**
	 * Devuelve el nodo anterior al nodo que recibió la instrucción.
	 * @return El nodo anterior al nodo que recibió la instrucción.
	 */
	public DNodo<T> getAnterior() {
		return this.anterior;
	}

	/**
	 * Devuelve el nodo siguiente al nodo que recibió la instrucción.
	 * @return El nodo siguiente al nodo que recibió la instrucción.
	 */
	public DNodo<T> getSiguiente() {
		return this.siguiente;
	}

	/**
	 * Devuelve el elemento contenido en el nodo.
	 * @return El elemento que contiene el nodo que recibió la instrucción.
	 */
	public T element() {
		return elemento;
	}
}