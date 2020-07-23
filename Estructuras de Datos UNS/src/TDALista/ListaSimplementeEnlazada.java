package TDALista;

import java.util.Iterator;

public class ListaSimplementeEnlazada<E> implements PositionList<E> {

	protected Nodo<E> head;
	
	private class Nodo<T> implements Position<T>{

		protected T elemento;
		protected Nodo<T> siguiente;
		
		// Constructores:
		public Nodo(T item, Nodo<T> sig){
			elemento=item; siguiente=sig; 
		}
		
		public Nodo(T item) { 
			this(item,null); 
		}
		
		// Setters:
		
		public void setElemento(T elemento) {
			this.elemento = elemento;
		}
			
		public void setSiguiente(Nodo<T> siguiente) {
			this.siguiente = siguiente;
		}
		
		// Getters:

		public Nodo<T> getSiguiente() { 
			return siguiente;
		}

		
		public T element() {
			return elemento;
		}
		
		public Nodo<T> clone(){
			return new Nodo<T>(elemento, siguiente);
		}
	}

	
	public ListaSimplementeEnlazada(){
		this.head = null;
	}
	
	public int size() {
		int tamanyo = 0;
		Nodo<E> aux = head;
		while (aux != null) {
			tamanyo++;
			aux = aux.getSiguiente();
		}
		
		return tamanyo;
	}

	public boolean isEmpty() {
		return head == null;
	}

	public Position<E> first() throws EmptyListException {
		if (head == null) throw new EmptyListException();
		return head;
	}

	public Position<E> last() throws EmptyListException {
		if (head == null) throw new EmptyListException();
		
		Nodo<E> buscado = head;
		while (buscado.getSiguiente() != null) {
			buscado = buscado.getSiguiente();
		}
		
		return buscado;
	}

	public Position<E> next(Position<E> p) throws InvalidPositionException, BoundaryViolationException {
		Nodo<E> n = checkPosition(p);
		if(n.getSiguiente()==null) throw new BoundaryViolationException("ERROR: La posici�n apunta al �ltimo elemento de la lista");
		return n.getSiguiente();
	}
	
	/**
	 * Chequea que la posici�n no sea nula ni apunte a un nodo nulo. En caso que la posici�n p apunte a un nodo de otro tipo, se lanza la excepci�n ClassCastException
	 * @param p Posición a ser chequeada.
	 * @return El nodo al cual apunta la posici�n p.
	 * @throws InvalidPositionException Si la posición no cumple con las condiciones.
	 */

	private Nodo<E> checkPosition(Position<E> p) throws InvalidPositionException {
		try {
			
			if (p==null) throw new InvalidPositionException("ERROR: Posici�n nula");
			if (p.element() == null) throw new InvalidPositionException("ERROR: La posici�n fue eliminada previamente");
			return (Nodo<E>)p;
		}
		catch(ClassCastException e) {
			
			throw new InvalidPositionException("ERROR: La posici�n no apunta a un nodo de la lista.");
		}
	}
	
	public Position<E> prev(Position<E> p) throws InvalidPositionException, BoundaryViolationException {
		
		Nodo<E> apuntado = checkPosition(p);
		if (p == head) throw new BoundaryViolationException("ERROR: Se intent� devolver el previo de una lista con un elemento.");
		
		Nodo<E> recorriendo = head;
		while (recorriendo.siguiente != apuntado && recorriendo.siguiente  != null) {
			recorriendo = recorriendo.siguiente;
		}
		
		if (recorriendo.siguiente == null) throw new InvalidPositionException("ERROR: El previo a la posici�n indicada es NULO.");
		return recorriendo; 

	}

	public void addFirst(E element) {
		head = new Nodo<E>(element, head);
	}

	public void addLast(E element) {
		if (head == null) 
			head = new Nodo<E>(element, head);
		else {
			Nodo<E> ultimo = head;
			while (ultimo.getSiguiente() != null) {
				ultimo = ultimo.getSiguiente();
			}
		
			ultimo.setSiguiente(new Nodo<E>(element));
			
		}		
	}

	public void addAfter(Position<E> p, E element) throws InvalidPositionException {
		Nodo<E> apuntado = checkPosition(p);
		Nodo<E> agregado = new Nodo<E>(element, apuntado.siguiente);
		
		apuntado.setSiguiente(agregado);
	}

	public void addBefore(Position<E> p, E element) throws InvalidPositionException {
		Nodo<E> apuntado = checkPosition(p);
		if (apuntado == head)
			head = new Nodo<E>(element, head);
		
		else {
			try {
				addAfter(prev(apuntado), element);
			}
			catch (BoundaryViolationException b) {
				b.getMessage();
			}
		}
	}

	public E remove(Position<E> p) throws InvalidPositionException {
		Nodo<E> apuntado = checkPosition(p);
		if (apuntado == head) {
			
			head = head.getSiguiente();
		}
		else {
			try {
				checkPosition(prev(p)).setSiguiente(apuntado.getSiguiente());
			}
			catch (BoundaryViolationException b) {
				b.getMessage();
			}
		}	
				
		E aDevolver = p.element();
		apuntado.setElemento(null);
		apuntado.setSiguiente(null);
		
		return (E) aDevolver;

	}

	public E set(Position<E> p, E element) throws InvalidPositionException {
		Nodo<E> aSetear = checkPosition(p);
		E aux = aSetear.element();
		aSetear.setElemento(element);
		return aux;
	}

	public Iterator<E> iterator() {
		return new IteradorListaSobreEdOriginal<E>(this);
	}

	public Iterable<Position<E>> positions() {
		PositionList<Position<E>> iterable = new ListaSimplementeEnlazada<Position<E>>();
		Nodo<E> actual = head;
		while(actual != null) {
			iterable.addLast(actual);
			actual = actual.getSiguiente();
		}
	
		return iterable;
	}
	
	public String toString() {
		String salida = "[";
		Iterator<E> it = iterator();
		while(it.hasNext()) {
			salida += it.next();
			if (it.hasNext()) 
				salida+=", ";
		}
		salida += "]";
		return salida;
	}

	public ListaSimplementeEnlazada<E> clone() {
		ListaSimplementeEnlazada<E> listaClon = new ListaSimplementeEnlazada<E>();
		
		Nodo<E> nodo = this.head;
		
		while (nodo != null) {
			listaClon.addLast(nodo.clone().element());
			
			nodo = nodo.getSiguiente();
		}
		
		return listaClon;
	}
	
	public void eliminar_consecutivos(E e1, E e2) {
		Nodo<E> puntero = head;
		
		
		
	}
	
	public static void main(String args[]) {
		ListaSimplementeEnlazada<Integer> l1 = new ListaSimplementeEnlazada<>();
		
		l1.addLast(2);
		l1.addLast(4);
		l1.addLast(8);
		l1.addLast(66);
		l1.addLast(66);
		
		System.out.println(l1.toString());
		
		l1.eliminar_consecutivos(4, 8);
		
		System.out.println(l1.toString());
		
		
	}
}
