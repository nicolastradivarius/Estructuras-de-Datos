package TDAPila;

/**
 * Modela la implementación de la interface {@link Stack} a través de una estructura enlazada compuesta
 * por <i>nodos simples</i>, esto es, nodos que además de contener un elemento contienen una referencia al
 * nodo siguiente dentro de la respectiva estructura. <br>
 * @see Nodo 
 * @author Nicolás González
 * @param <E> Tipo de elemento de los nodos de la pila.
 */
public class Pila_con_enlaces<E> implements Stack<E>{

	protected Nodo<E> head;
	protected int tamanyo;

	/**
	 * Crea una Pila con enlaces vacía, esto es, sin ningún nodo.
	 */
	public Pila_con_enlaces() {
		head = null;
		tamanyo = 0;
	}

	@Override
	public int size() {
		return tamanyo;
	}

	@Override
	public boolean isEmpty() {
		return tamanyo==0;
	}

	@Override
	public E top() throws EmptyStackException {
		if (tamanyo == 0) throw new EmptyStackException("Pila vacía");

		return head.getElemento();
	}

	@Override
	public void push(E element) {
		head = new Nodo<E>(element, head);
		tamanyo++;
	}

	@Override
	public E pop() throws EmptyStackException {
		if (tamanyo == 0) throw new EmptyStackException("Pila vacía");

		E tope = head.getElemento();
		head = head.getSiguiente();
		tamanyo--;
		return tope;
	}
}