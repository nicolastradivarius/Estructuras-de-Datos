package TDALista;

import java.util.Iterator;

/**
 * Modela la implementación de la interface PositionList a través de una Lista doblemente enlazada, esto es, con dos celdas 
 * con referencia al primer y último nodo respectivamente. Estos dos nodos no contienen elementos y son inválidos a la hora de operar con ellos.
 * @author Nicolás González
 *	@see PositionList
 * @param <E> Tipo de elemento de los nodos de la lista.
 */
public class ListaDoblementeEnlazada<E>  implements PositionList<E>{

	protected DNodo<E> header, trailer;
	protected int tamanyo = 0;

	/**
	 * Crea una Lista doblemente enlazada vacía, esto es, con referencias a los nodos primero y último, pero
	 * sin ningún otro nodo con elementos entre medio.
	 */
	public ListaDoblementeEnlazada() {
		header = new DNodo<E>(null, null, null);
		trailer = new DNodo<E>(header, null, null);
		header.setSiguiente(trailer);
	}

	@Override
	public int size() {
		return tamanyo;
	}

	@Override
	public boolean isEmpty() {
		return tamanyo == 0;
	}

	@Override
	public Position<E> first() throws EmptyListException {
		if (tamanyo == 0) throw new EmptyListException();
		return header.getSiguiente();
	}

	@Override
	public Position<E> last() throws EmptyListException {
		if (tamanyo == 0) throw new EmptyListException();
		return trailer.getAnterior();
	}

	@Override
	public Position<E> next(Position<E> p) throws InvalidPositionException, BoundaryViolationException {
		DNodo<E> nodo = checkPosition(p);
		if (nodo.getSiguiente() == trailer) throw new BoundaryViolationException("ERROR: no hay siguiente del �ltimo");

		return nodo.getSiguiente();
	}

	@Override
	public Position<E> prev(Position<E> p) throws InvalidPositionException, BoundaryViolationException {
		DNodo<E> nodo = checkPosition(p);
		if (nodo.getAnterior() == header) throw new BoundaryViolationException("ERROR: no hay anterior del primero");

		return nodo.getAnterior();
	}

	@Override
	public void addFirst(E element) {
		addBetween(element, header, header.getSiguiente());
	}

	@Override
	public void addLast(E element) {
		addBetween(element, trailer.getAnterior(), trailer);
	}

	@Override
	public void addBefore(Position<E> p, E element) throws InvalidPositionException {
		DNodo<E> nodo = checkPosition(p);
		DNodo<E> anterior = nodo.getAnterior();
		DNodo<E> nuevo = new DNodo<E>(element);

		nuevo.setSiguiente(nodo);
		nodo.setAnterior(nuevo);
		anterior.setSiguiente(nuevo);
		nuevo.setAnterior(anterior);
		tamanyo++;

	}

	@Override
	public void addAfter(Position<E> p, E element) throws InvalidPositionException {
		DNodo<E> nodo = checkPosition(p);
		DNodo<E> siguiente = nodo.getSiguiente();
		DNodo<E> nuevo = new DNodo<E>(element);

		nuevo.setAnterior(nodo);
		nodo.setSiguiente(nuevo);
		siguiente.setAnterior(nuevo);
		nuevo.setSiguiente(siguiente);
		tamanyo++;
	}

	@Override
	public E remove(Position<E> p) throws InvalidPositionException {
		if (tamanyo == 0) throw new InvalidPositionException("ERROR: lista vacía");
		DNodo<E> nodo = checkPosition(p);
		DNodo<E> predecesor = nodo.getAnterior();
		DNodo<E> sucesor = nodo.getSiguiente();

		E aux = nodo.element();
		nodo.setElemento(null);
		predecesor.setSiguiente(sucesor);
		sucesor.setAnterior(predecesor);
		tamanyo--;
		return aux;
	}

	@Override
	public E set(Position<E> p, E element) throws InvalidPositionException {
		if (tamanyo == 0) throw new InvalidPositionException("ERROR: Lista vacía");
		DNodo<E> nodo = checkPosition(p);
		E aux = nodo.element();
		nodo.setElemento(element);
		return aux;
	}

	@Override
	public Iterator<E> iterator() {
		return new IteradorListaSobreEdOriginal<E>(this);
	}

	@Override
	public Iterable<Position<E>> positions() {
		PositionList<Position<E>> lista_de_posiciones_Iterable = new ListaDoblementeEnlazada<Position<E>>();
		DNodo<E> actual = header.getSiguiente();
		while (actual != trailer) {
			lista_de_posiciones_Iterable.addLast(actual);
			actual = actual.getSiguiente();
		}
		return lista_de_posiciones_Iterable;
	}

	/**
	 * Inserta un nodo con el elemento pasado por parámetro entre medio de dos nodos (predecesor y sucesor),
	 * también pasados por parámetro. <br>
	 * Es utilizado por los métodos <code>addFirst(E)</code> y <code>addLast(E)</code>. 
	 * @param elemento El elemento que va a contener el nuevo nodo insertado.
	 * @param predecesor El nodo anterior al nuevo nodo que será insertado.
	 * @param sucesor El nodo siguiente al nuevo nodo que será insertado.
	 */
	private void addBetween(E elemento, DNodo<E> predecesor, DNodo<E> sucesor) {
		DNodo<E> nuevo = new DNodo<E>(predecesor, elemento, sucesor);
		predecesor.setSiguiente(nuevo);
		sucesor.setAnterior(nuevo);
		tamanyo++;
	}

	/**
	 * Verifica que la posición pasada por parámetro corresponda a una posición válida, es decir, no sea nula, o contenga
	 * elementos nulos/borrados con anterioridad, y retorna el nodo al cual corresponde la posición.
	 * @param p La posición a ser chequeada.
	 * @return El nodo que corresponde a la posición pasada por parámetro (si es válida).
	 * @throws InvalidPositionException Si la posición es nula, o contiene un elemento nulo.
	 */
	private DNodo<E> checkPosition(Position<E> p) throws InvalidPositionException {
		try {
			if (p==null) throw new InvalidPositionException("ERROR: Posición nula");
			if (p.element()== null) throw new InvalidPositionException("ERROR: La posición fue eliminada previamente");
			return (DNodo<E>)p;
		}
		catch(ClassCastException e) {
			throw new InvalidPositionException("ERROR: La posición no apunta a un nodo de la lista.");
		}
	}

	public String toString() {
		String salida = "[";

		for (Position<E> p : positions()) {
			salida += p.element().toString() + ", ";
		}

		salida += "]";
		return salida;
	}

	public void invertir(){

		int contador = 0;
		DNodo<E> nodoUltimo = trailer.getAnterior();
		DNodo<E> nodoPrimero = header.getSiguiente();

		while (contador < this.tamanyo/2) {
			E aux = nodoPrimero.element();
			nodoPrimero.setElemento(nodoUltimo.element());
			nodoUltimo.setElemento(aux);
			
			contador++;
			
			nodoPrimero = nodoPrimero.getSiguiente();
			nodoUltimo = nodoUltimo.getAnterior();
		}
	}
	
	
	public static void main (String args[]) {
		try {
			ListaDoblementeEnlazada<Integer> l1 = new ListaDoblementeEnlazada<>();
			
			l1.addLast(2);
			l1.addLast(5);
			l1.addLast(7);
			l1.addLast(9);
			l1.addLast(9938);
			
			System.out.println(l1.toString());
			
			l1.invertir();
			
			System.out.println(l1.toString());
		}
		catch (Exception err) {
			err.printStackTrace();
		}
	}
}
