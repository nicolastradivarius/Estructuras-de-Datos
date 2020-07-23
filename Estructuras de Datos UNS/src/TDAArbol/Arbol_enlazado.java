package TDAArbol;

import java.util.Iterator;
import TDALista.*;

/**
 * Modela la implementación de la interface {@link Tree} a través de una estructura enlazada.
 * @author Nicolás González
 * @see ListaDoblementeEnlazada
 * @param <E> Tipo de elemento del árbol
 */
public class Arbol_enlazado<E> implements Tree<E> {

	protected TNodo<E> raiz;
	protected int tamanyo;

	/**
	 * Crea un árbol vacío
	 */
	public Arbol_enlazado() {
		raiz = null;
		tamanyo = 0;
	}

	/**
	 * Crea un árbol con raíz parametrizada
	 * @param rot Raíz del nuevo árbol creado.
	 */
	public Arbol_enlazado (E rot) {
		tamanyo = 1;
		raiz = new TNodo<E>(rot, null);
	}

	@Override
	public int size() {
		return tamanyo;
	}

	@Override
	public boolean isEmpty() {
		return raiz == null;
	}

	@Override
	public Iterator<E> iterator() {
		PositionList<E> l = new ListaDoblementeEnlazada<E>();
		Iterable<Position<E>> positions = positions();

		for (Position<E> p : positions) {
			l.addLast(p.element());
		}

		return l.iterator();
	}

	@Override
	//Se crea en preorden.
	public Iterable<Position<E>> positions() {
		PositionList<Position<E>> l = new ListaDoblementeEnlazada<Position<E>>();

		if (tamanyo != 0)
			pre(raiz, l);

		return l;
	}

	@Override
	public E replace(Position<E> v, E e) throws InvalidPositionException {
		TNodo<E> nodo = checkPosition(v);
		E aux = nodo.element();

		nodo.setElemento(e);

		return aux;
	}

	@Override
	public Position<E> root() throws EmptyTreeException {
		if (raiz == null)
			throw new EmptyTreeException("Arbol vacío");

		return raiz;
	}

	@Override
	public Position<E> parent(Position<E> v) throws InvalidPositionException, BoundaryViolationException {
		TNodo<E> nodo = checkPosition(v);

		if (nodo == raiz)
			throw new BoundaryViolationException("El nodo parametrizado corresponde a la raíz del árbol");

		return nodo.getPadre(); 
	}

	@Override
	public Iterable<Position<E>> children(Position<E> v) throws InvalidPositionException {
		TNodo<E> p = checkPosition(v);
		PositionList<Position<E>> listaDeHijos = new ListaDoblementeEnlazada<Position<E>>();
		PositionList<TNodo<E>> it = p.getHijos();
		TDALista.Position<TNodo<E>> n = null;

		try {
			if (!it.isEmpty())
				n = it.first();

			//Añade todos los hijos de la posición v a la lista retornante.
			while (n != null) {
				listaDeHijos.addLast(n.element());
				n = (n == it.last()) ? null : it.next(n);
			}
		}
		catch (Exception err) {
			err.printStackTrace();
		}

		return listaDeHijos;
	}

	@Override
	public boolean isInternal(Position<E> v) throws InvalidPositionException {
		TNodo<E> nodo = checkPosition(v);

		return !(nodo.getHijos().isEmpty());
	}

	@Override
	public boolean isExternal(Position<E> v) throws InvalidPositionException {
		TNodo<E> nodo = checkPosition(v);

		return nodo.getHijos().isEmpty();
	}

	@Override
	public boolean isRoot(Position<E> v) throws InvalidPositionException {
		TNodo<E> nodo = checkPosition(v);

		return nodo.getPadre() == null;
	}

	@Override
	public void createRoot(E e) throws InvalidOperationException {
		if (tamanyo != 0)
			throw new InvalidOperationException("No se puede crear un nodo raíz en un árbol que ya tiene raíz");

		raiz = new TNodo<E>(e, null);
		tamanyo++;
	}

	@Override
	public Position<E> addFirstChild(Position<E> p, E e) throws InvalidPositionException {
		if (tamanyo == 0)
			throw new InvalidPositionException("El árbol está vacío");

		TNodo<E> nodoPadre = checkPosition(p);
		TNodo<E> nuevoNodo = new TNodo<E>(e, nodoPadre);
		nodoPadre.getHijos().addFirst(nuevoNodo);
		tamanyo++;

		return nuevoNodo;
	}

	@Override
	public Position<E> addLastChild(Position<E> p, E e) throws InvalidPositionException {
		if (tamanyo == 0)
			throw new InvalidPositionException("El árbol está vacío");

		TNodo<E> nodoPadre = checkPosition(p);
		TNodo<E> nuevo = new TNodo<E>(e, nodoPadre);
		nodoPadre.getHijos().addLast(nuevo);
		tamanyo++;

		return nuevo;
	}

	@Override
	public Position<E> addBefore(Position<E> p, Position<E> rb, E e) throws InvalidPositionException {
		if (tamanyo == 0)
			throw new InvalidPositionException("El árbol está vacío");
		if (p == null | rb == null)
			throw new InvalidPositionException("Posiciones inválidas");

		TDALista.Position<TNodo<E>> pp;
		TNodo<E> padre = checkPosition(p);
		TNodo<E> rightBro = checkPosition(rb);
		TNodo<E> nuevo = new TNodo<E>(e, padre);
		PositionList<TNodo<E>> hijos = padre.getHijos();
		boolean encontre = false;

		//Controla que el padre del hermano derecho al nuevo nodo que se va a insertar sea efectivamente hijo del nodo padre parametrizado.
		if (rightBro.getPadre() != padre)
			throw new InvalidPositionException("El nodo hermano pasado por parámetro no es hijo del nodo padre pasado por parámetro");

		try { 
			pp = hijos.first();
			//Busca la posición del hermano derecho al futuro nodo en la lista de hijos del padre.
			while (pp != null && !encontre) {
				if (rightBro == pp.element()) {
					encontre = true;
					hijos.addBefore(pp, nuevo);
					tamanyo++;
				}
				else
					pp = (pp != hijos.last()) ? hijos.next(pp) : null;
			}

			if (!encontre)
				throw new InvalidPositionException("P no es padre de rb");

		}
		catch (TDALista.InvalidPositionException | EmptyListException | TDALista.BoundaryViolationException err) {
			err.printStackTrace();
		}
		return nuevo;
	}

	@Override
	public Position<E> addAfter(Position<E> p, Position<E> lb, E e) throws InvalidPositionException {
		if (tamanyo == 0)
			throw new InvalidPositionException("El árbol está vacío");
		if (p == null | lb == null)
			throw new InvalidPositionException("Posiciones inválidas");

		TNodo<E> padre = checkPosition(p);
		TNodo<E> leftBro = checkPosition(lb);

		//Chequea que el hermano izquierdo parametrizado sea efectivamente hijo del padre nodo parametrizado
		if (leftBro.getPadre() != padre)
			throw new InvalidPositionException("El nodo hermano pasado por parámetro no es hijo del nodo padre pasado por parámetro");

		PositionList<TNodo<E>> listaDeHijos = padre.getHijos();
		TDALista.Position<TNodo<E>> pos = null;
		TNodo<E> nodoNuevo = new TNodo<E>(e, padre);
		boolean encontre = false;

		try {
			if (! (listaDeHijos.isEmpty()))
				pos = listaDeHijos.first();

			//Busca al hermano izquierdo en la lista de hijos del padre
			while (pos != null && !encontre) {
				if (pos.element() == leftBro) {
					encontre = true;
					listaDeHijos.addAfter(pos, nodoNuevo);
					tamanyo++;
				}

				pos = (pos == listaDeHijos.last()) ? null : listaDeHijos.next(pos);
			}

			if (!encontre)
				throw new InvalidPositionException("Posición inválida: no se encuentra al nodo hermano");
		}
		catch (Exception err) {
			err.printStackTrace();
		}

		return pos.element();
	}

	@Override
	public void removeExternalNode(Position<E> p) throws InvalidPositionException {
		if (tamanyo == 0)
			throw new InvalidPositionException("El árbol está vacío");

		TNodo<E> n = checkPosition(p);

		if (!(n.getHijos().isEmpty()))
			throw new InvalidPositionException("La posición no representa una hoja del árbol");

		removeNode(n);
	}

	@Override
	public void removeInternalNode(Position<E> p) throws InvalidPositionException {
		if (tamanyo == 0)
			throw new InvalidPositionException("El árbol está vacío");

		TNodo<E> n = checkPosition(p);

		if (n.getHijos().isEmpty())
			throw new InvalidPositionException("La posición no representa una nodo interno del árbol");

		removeNode(n);
	}

	@Override
	public void removeNode(Position<E> p) throws InvalidPositionException {

		TNodo<E> nEliminar = checkPosition(p);
		TNodo<E> padreDelEliminar = nEliminar.getPadre();
		PositionList<TNodo<E>> hijos = nEliminar.getHijos();
		TNodo<E> hijo = null;
		PositionList<TNodo<E>> hermanos = null;
		TDALista.Position<TNodo<E>> posListaHermanos  = null;

		try {
			//Si el nodo a eliminar es la raiz y ésta no tiene hijos directamente se setea en nulo
			if (nEliminar == raiz) {
				if (hijos.size() == 0) {
					raiz = null;
				}
				else {
					//Si tiene uno y sólo un hijo, éste se convierte en la nueva raíz del árbol
					if (hijos.size() == 1) {
						hijo = hijos.remove(hijos.first());
						hijo.setPadre(null);
						raiz = hijo;
					}
					else {
						throw new InvalidPositionException("No se puede eliminar raíz con más de 1 hijo");
					}
				}
			}
			else {
				if (padreDelEliminar == null)
					throw new InvalidPositionException("ERROR: El padre del nodo que no es la raíz es un nulo");

				hermanos = padreDelEliminar.getHijos();
				posListaHermanos = hermanos.isEmpty() ? null : hermanos.first();

				//Recorre la lista de hijos del padre del nodo a eliminar (que no es la raíz) hasta encontrar dicho nodo
				while (posListaHermanos != null && posListaHermanos.element() != nEliminar ) {
					posListaHermanos = (hermanos.last() == posListaHermanos) ? null : hermanos.next(posListaHermanos);
				}

				if (posListaHermanos == null)
					throw new InvalidPositionException("La posicion p no se encuentra en la lista del padre");

				//Para cada hijo del nodo a eliminar, se setea a su padre con el padre del nodo a eliminar y se lo añade a la colección de hijos de dicho padre 
				while (!hijos.isEmpty()) {
					hijo = hijos.remove(hijos.first());
					hijo.setPadre(padreDelEliminar);
					hermanos.addBefore(posListaHermanos, hijo);
				}
				hermanos.remove(posListaHermanos);
			}

			//Finaliza eliminando efectivamente el nodo.
			nEliminar.setPadre(null);
			nEliminar.setElemento(null);
			tamanyo--;
		}
		catch (EmptyListException | TDALista.BoundaryViolationException | TDALista.InvalidPositionException err) {
			err.printStackTrace();
		}
	}


	/**
	 * Recorrido preorden del árbol: el nodo v se visita primero y luego se visitan recursivamente cada uno de los subárboles de v. Al mismo tiempo se añaden en la lista l.
	 * @param v Nodo donde empieza el recorrido
	 * @param l Lista donde se añaden los nodos en preorden.
	 */
	private void pre(TNodo<E> v, PositionList<Position<E>> l) {
		l.addLast(v);

		for (TNodo<E> h : v.getHijos())
			pre (h, l);
	}

	/**
	 * Controla que una posición p pasada por parámetro sea válida (no nula ni con elemento nulo, y que sea un nodo de árbol).
	 * @param p Posición a ser chequeada
	 * @return El nodo de árbol correspondiente a la posición p
	 * @throws InvalidPositionException si la posición no cumple con las condiciones impuestas.
	 */
	private TNodo<E> checkPosition(Position<E> p) throws InvalidPositionException {
		try {
			if (p == null)
				throw new InvalidPositionException("ERROR: Posición nula");
			if (p.element() == null) 
				throw new InvalidPositionException("ERROR: La posición fue eliminada previamente");
			return (TNodo<E>)p;
		}
		catch(ClassCastException e) {
			throw new InvalidPositionException("ERROR: La posición no apunta a un nodo del árbol.");
		}
	}

	public void listar(boolean p) {
		if (this.tamanyo != 0)
			listar_aux(raiz, p);
		else
			System.out.println("Árbol vacío");
	}

	private static <E> void listar_aux(TNodo<E> n, boolean p) {
		if (p) { //realizar listado en preorden
			System.out.print(n.element() + "-");
			for (TNodo<E> hijo : n.getHijos()) {
				listar_aux(hijo, p);
			}
		}
		else { //realizar listado en postorden
			for (TNodo<E> hijo : n.getHijos()) {
				listar_aux(hijo, p);
			}
			System.out.print(n.element() + "-");
		}
	}

	private static void inorder_aux(Tree<Integer> t, TNodo<Integer> v){
		Iterator<TNodo<Integer>> hijos = v.getHijos().iterator();
		TNodo<Integer> w = null;
		try {
			if (t.isExternal(v))
				System.out.print(v.element() + " ");
			else {
				w = hijos.next();
				inorder_aux(t, w);
				System.out.print(v.element() + " ");

				while (hijos.hasNext()){
					w = hijos.next();
					inorder_aux(t, w);
				}
			}
		}
		catch (InvalidPositionException err) {
			err.printStackTrace();
		}

	}

	/*
	 * retorna lista de posiciones con las posiciones que representan hijos
	 * extremos izquierdos y que no son hojas
	 */
	public PositionList<Position<E>> extremos_izquierdos_no_hojas(){
		PositionList<Position<E>> lista_posiciones = new ListaDoblementeEnlazada<Position<E>>();
		try {
			if (this.tamanyo > 1)
				extremos_izquierdos_no_hojas_aux(raiz.getHijos().first().element(), lista_posiciones);
		}
		catch (Exception err) {
			err.printStackTrace();
		}
		return lista_posiciones;
	}

	private void extremos_izquierdos_no_hojas_aux(TNodo<E> n, PositionList<Position<E>> lista_posiciones) {
		try {
			if (!n.getHijos().isEmpty()) {
				lista_posiciones.addLast(n);
				PositionList<TNodo<E>> hijos = n.getHijos();
				extremos_izquierdos_no_hojas_aux(hijos.first().element(), lista_posiciones);
			}
		}
		catch (Exception err) {
			err.printStackTrace();
		}
	}

	public void eliminar_hojas() {
		try {
			if (tamanyo > 1) {
				eliminar_hojas_aux(raiz);
			}
			else {
				raiz = null;
				tamanyo = 0;
			}
		}
		catch (Exception err) {
			err.printStackTrace();
		}
	}

	private void eliminar_hojas_aux(TNodo<E> n) {
		Iterable<TDALista.Position<TNodo<E>>> hijos = n.getHijos().positions();
		try {
			for (TDALista.Position<TNodo<E>> p : hijos) {
				if (p.element().getHijos().isEmpty()) {
					p.element().getPadre().getHijos().remove(p);
					tamanyo--; 
				}
				else
					eliminar_hojas_aux(p.element());
			}
		}
		catch (Exception err) {
			err.printStackTrace();
		}
	}

	public void eliminar_rotulo(E r) {
		if (tamanyo != 0)
			eliminar_rotulo_aux(raiz, r);
	}

	private void eliminar_rotulo_aux(TNodo<E> n, E r) {
		Iterable<TDALista.Position<TNodo<E>>> hijos = n.getHijos().positions();
		try {
			for (TDALista.Position<TNodo<E>> p : hijos) {
				if (p.element().element().equals(r)) {
					if (!p.element().getHijos().isEmpty()) {
						for (TNodo<E> hijo : p.element().getHijos()) {
							hijo.setPadre(p.element().getPadre());
						}
					}
					p.element().getPadre().getHijos().remove(p);
					tamanyo--;
				}
				else {
					eliminar_rotulo_aux(p.element(), r);
				}
			}
		}
		catch (Exception err) {
			err.printStackTrace();
		}
	}

	public static void main(String args[]) {
		Arbol_enlazado<Character> t1 = new Arbol_enlazado<>();

		try {
			t1.createRoot('A');
			Position<Character> h0 = t1.root();
			Position<Character> h1 = t1.addLastChild(h0, 'B');
			Position<Character> h2 = t1.addLastChild(h0, 'C');
			Position<Character> h3 = t1.addLastChild(h0, 'D');
			Position<Character> h4 = t1.addLastChild(h1, 'E');
			Position<Character> h5 = t1.addLastChild(h1, 'F');
			Position<Character> h6 = t1.addLastChild(h1, 'G');
			Position<Character> h7 = t1.addLastChild(h2, 'H');
			Position<Character> h8 = t1.addLastChild(h7, 'I');
			Position<Character> h9 = t1.addLastChild(h7, 'J');
			Position<Character> h10 = t1.addLastChild(h9, 'K');
			Position<Character> h11 = t1.addLastChild(h4, 'L');
			Position<Character> h12 = t1.addLastChild(h11, 'M');
			Position<Character> h13 = t1.addLastChild(h11, 'N');
			t1.eliminar_rotulo('J');

			t1.listar(true);

		}
		catch (Exception err) {
			err.printStackTrace();
		}
	}
}

