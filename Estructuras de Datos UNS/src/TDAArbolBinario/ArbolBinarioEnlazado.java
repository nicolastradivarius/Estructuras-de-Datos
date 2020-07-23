package TDAArbolBinario;

import java.util.Iterator;

import TDALista.ListaDoblementeEnlazada;
import TDALista.PositionList;
import TDAMapeo.Map;
import TDAMapeo.Mapeo_hash_abierto;

public class ArbolBinarioEnlazado<E> implements BinaryTree<E> {

	protected int tamanyo;
	protected BTNodo<E> raiz;

	public ArbolBinarioEnlazado() {
		raiz = null;
		tamanyo = 0;
	}

	private ArbolBinarioEnlazado(BTNodo<E> raiz) {
		this.raiz = raiz;
		tamanyo = 1; 
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
	public Iterator<E> iterator() {
		PositionList<E> l = new ListaDoblementeEnlazada<E>();
		for (Position<E> p : positions()) {
			l.addLast(p.element());
		}

		return l.iterator();
	}

	@Override
	//Se crea en preorden
	public Iterable<Position<E>> positions() {
		PositionList<Position<E>> l = new ListaDoblementeEnlazada<Position<E>>();

		if (tamanyo != 0)
			pre(raiz, l);

		return l;
	}

	@Override
	public E replace(Position<E> v, E e) throws InvalidPositionException {
		BTNodo<E> nodo = checkPosition(v);
		E elementoAnterior = nodo.element();

		nodo.setElement(e);

		return elementoAnterior;
	}

	@Override
	public Position<E> root() throws EmptyTreeException {
		if (raiz == null)
			throw new EmptyTreeException();
		return raiz;
	}

	@Override
	public Position<E> parent(Position<E> v) throws InvalidPositionException, BoundaryViolationException {
		BTNodo<E> nodo = checkPosition(v);

		if (nodo == raiz)
			throw new BoundaryViolationException("La posición parametrizada corresponde a la raíz. Imposible calcular parent()");
		return nodo.getPadre(); 
	}

	@Override
	public Iterable<Position<E>> children(Position<E> v) throws InvalidPositionException {
		PositionList<Position<E>> hijos = new ListaDoblementeEnlazada<Position<E>>();
		BTNodo<E> nodo = checkPosition(v);

		if (nodo.getHijoIzquierdo() != null)
			hijos.addLast(nodo.getHijoIzquierdo());

		if (nodo.getHijoDerecho() != null)
			hijos.addLast(nodo.getHijoDerecho());

		return hijos;
	}

	@Override
	public boolean isInternal(Position<E> v) throws InvalidPositionException {
		BTNodo<E> nodo = checkPosition(v);

		return nodo.getHijoIzquierdo() != null || nodo.getHijoDerecho() != null;
	}

	@Override
	public boolean isExternal(Position<E> v) throws InvalidPositionException {
		BTNodo<E> nodo = checkPosition(v);

		return nodo.getHijoIzquierdo() == null && nodo.getHijoDerecho() == null;
	}

	@Override
	public boolean isRoot(Position<E> v) throws InvalidPositionException {
		BTNodo<E> nodo = checkPosition(v);

		return nodo == raiz;
	}

	@Override
	public Position<E> left(Position<E> v) throws InvalidPositionException, BoundaryViolationException {
		BTNodo<E> nodo = checkPosition(v);

		if (nodo.getHijoIzquierdo() == null)
			throw new BoundaryViolationException("El nodo corresponde a una hoja");

		return nodo.getHijoIzquierdo();
	}

	@Override
	public Position<E> right(Position<E> v) throws InvalidPositionException, BoundaryViolationException {
		BTNodo<E> padre = checkPosition(v);

		if (padre.getHijoDerecho() == null)
			throw new BoundaryViolationException("El nodo parametrizado no tiene hijo derecho");

		return padre.getHijoDerecho();
	}

	@Override
	public boolean hasLeft(Position<E> v) throws InvalidPositionException {
		BTNodo<E> nodo = checkPosition(v);

		return nodo.getHijoIzquierdo() != null;
	}

	@Override
	public boolean hasRight(Position<E> v) throws InvalidPositionException {
		BTNodo<E> nodo = checkPosition(v);

		return nodo.getHijoDerecho() != null;
	}

	@Override
	public Position<E> createRoot(E r) throws InvalidOperationException {
		if (raiz != null)
			throw new InvalidOperationException("El árbol ya tiene una raíz");

		raiz = new BTNodo<E>(r);
		tamanyo++;

		return raiz;
	}

	@Override
	public Position<E> addLeft(Position<E> v, E r) throws InvalidOperationException, InvalidPositionException {
		if (raiz == null)
			throw new InvalidPositionException("El árbol está vacío");

		BTNodo<E> padre = checkPosition(v);
		BTNodo<E> hijo = new BTNodo<E>(r, padre);

		if (padre.getHijoIzquierdo() != null)
			throw new InvalidOperationException("El nodo asociado ya tiene hijo izquierdo");

		padre.setHijoIzquierdo(hijo);
		tamanyo++;

		return hijo;
	}

	@Override
	public Position<E> addRight(Position<E> v, E r) throws InvalidOperationException, InvalidPositionException {
		if (raiz == null)
			throw new InvalidPositionException("El árbol está vacío");

		BTNodo<E> padre = checkPosition(v);
		BTNodo<E> hijo = new BTNodo<E>(r, padre);

		if (padre.getHijoDerecho() != null)
			throw new InvalidOperationException("El nodo asociado ya tiene hijo derecho");

		padre.setHijoDerecho(hijo);
		tamanyo++;

		return hijo;
	}

	@Override
	public E remove(Position<E> v) throws InvalidOperationException, InvalidPositionException {
		BTNodo<E> nodoAsoc = checkPosition(v);

		if (nodoAsoc.getHijoIzquierdo() != null && nodoAsoc.getHijoDerecho() != null)
			throw new InvalidOperationException("El nodo a eliminar tiene más de un hijo");

		BTNodo<E> hijo = (nodoAsoc.getHijoIzquierdo() != null) ? nodoAsoc.getHijoIzquierdo() : nodoAsoc.getHijoDerecho();

		if (hijo != null) {
			hijo.setPadre(nodoAsoc.getPadre());
		}

		if (nodoAsoc == raiz) {
			raiz = hijo;
		}
		else {
			BTNodo<E> padre = nodoAsoc.getPadre();
			if (nodoAsoc == padre.getHijoIzquierdo())
				padre.setHijoIzquierdo(hijo);
			else 
				padre.setHijoDerecho(hijo);
		}

		tamanyo--;
		E temporal = nodoAsoc.element();
		nodoAsoc.setElement(null);
		nodoAsoc.setHijoIzquierdo(null);
		nodoAsoc.setHijoDerecho(null);
		nodoAsoc.setPadre(null);

		return temporal;
	}

	@Override
	public void attach(Position<E> p, BinaryTree<E> t1, BinaryTree<E> t2) throws InvalidPositionException {
		BTNodo<E> raiz_local = checkPosition(p);
		BTNodo<E> hi_raiz_local;
		BTNodo<E> hd_raiz_local;
		Position<E> raiz_t1 = null;
		Position<E> raiz_t2 = null;

		if (raiz_local.getHijoIzquierdo() != null || raiz_local.getHijoDerecho() != null)
			throw new InvalidPositionException("La posición no corresponde a un nodo hoja");

		try {
			if (!t1.isEmpty()) {
				raiz_t1 = t1.root();
				hi_raiz_local = new BTNodo<E>(raiz_t1.element(), raiz_local);
				raiz_local.setHijoIzquierdo(hi_raiz_local);
				clonar(hi_raiz_local, raiz_t1, t1);
			}

			if (!t2.isEmpty()) {
				raiz_t2 = t2.root();
				hd_raiz_local = new BTNodo<E>(raiz_t2.element(), raiz_local);
				raiz_local.setHijoDerecho(hd_raiz_local);
				clonar(hd_raiz_local, raiz_t2, t2);
			}
		}
		catch (EmptyTreeException err) {
			err.printStackTrace();
		}
	}

	private void clonar(BTNodo<E> padre_local, Position<E> padre_t, BinaryTree<E> t) {
		BTNodo<E> hi_padre_local, hd_padre_local;
		Position<E> hi_padre_t, hd_padre_t;

		try {
			if (t.hasLeft(padre_t)) {
				hi_padre_t = t.left(padre_t);
				hi_padre_local = new BTNodo<E>(hi_padre_t.element(), padre_local);
				padre_local.setHijoIzquierdo(hi_padre_local);
				clonar(hi_padre_local, hi_padre_t, t);
			}

			if (t.hasRight(padre_t)) {
				hd_padre_t = t.right(padre_t);
				hd_padre_local = new BTNodo<E>(hd_padre_t.element(), padre_local);
				padre_local.setHijoDerecho(hd_padre_local);
				clonar(hd_padre_local, hd_padre_t, t);
			}

		}
		catch (Exception err) {
			padre_local.setHijoIzquierdo(null); 
			padre_local.setHijoDerecho(null);
		}
	}

	private void pre(BTNodo<E> v, PositionList<Position<E>> l) {
		l.addLast(v);

		if (v.getHijoIzquierdo() != null) {
			pre(v.getHijoIzquierdo(), l);
		}
		if (v.getHijoDerecho() != null) {
			pre(v.getHijoDerecho(), l);
		}
	}

	/**
	 * Controla que una posición p pasada por parámetro sea válida (no nula ni con elemento nulo, y que sea un nodo de árbol binario).
	 * @param p Posición a ser chequeada
	 * @return El nodo de árbol binario correspondiente a la posición p
	 * @throws InvalidPositionException si la posición no cumple con las condiciones impuestas.
	 */
	private BTNodo<E> checkPosition(Position<E> p) throws InvalidPositionException {
		try {
			if (p == null)
				throw new InvalidPositionException("ERROR: Posición nula");
			if (p.element() == null) 
				throw new InvalidPositionException("ERROR: La posición fue eliminada previamente");
			return (BTNodo<E>)p;
		}
		catch(ClassCastException e) {
			throw new InvalidPositionException("ERROR: La posición no apunta a un nodo del árbol binario.");
		}
	}

	//// METODOS SOLICITADOS POR EL TRABAJO PRACTICO N°7 - EDD

	//Ejercicio (a)
	public void espejar() {
		if (raiz != null) {
			invertir(raiz.getHijoIzquierdo(), raiz.getHijoDerecho(), raiz);
		}
	}

	private void invertir(BTNodo<E> nodo1, BTNodo<E> nodo2, BTNodo<E> padre) {
		if (nodo1 != null | nodo2 != null) {
			BTNodo<E> p = padre.getHijoIzquierdo();
			padre.setHijoIzquierdo(padre.getHijoDerecho());
			padre.setHijoDerecho(p);

			if (nodo1 != null)
				invertir(nodo1.getHijoIzquierdo(), nodo1.getHijoDerecho(), nodo1);
			if (nodo2 != null)
				invertir(nodo2.getHijoIzquierdo(), nodo2.getHijoDerecho(), nodo2);
		}
	}

	//Ejercicio (b)
	public void padre_de_hojas() {
		if (raiz != null) {

			int nivel = 0;
			padres_de_hojas_aux(raiz, nivel);
		}
	}

	private boolean padres_de_hojas_aux(Position<E> n, int nivel) {
		/*
		 * Lo que hace es ir pasando por cada nodo, recorrer su conjnto de hijos
		 * con el for(), si existen hijos entonces va a entrar en el for(), aumenta 
		 * cant_hijos y ahí vuelve a realizar la misma operatoria.
		 * es_hojas será verdadero cuando en un proceso recursivo no entre en el for()
		 */
		boolean imprimi = false;
		boolean es_hoja;
		int cant_hijos = 0;

		try {
			//Por cada hijo de n
			for (Position<E> p : children(n)) {
				cant_hijos++;
				es_hoja = padres_de_hojas_aux(p, nivel++);

				//Si el hijo es hoja y todavia no imprimí...
				if (es_hoja && !imprimi) {
					imprimi = true;
					System.out.println(n.element() + " " + "Nivel: " + nivel);
				}
			}

		}
		catch (Exception err) {
			err.printStackTrace();
		}

		return cant_hijos == 0;
	}

	//clonado en profundidad, deberá clonar tanto el objeto instancia como los nodos
	public BinaryTree<E> clone(){
		BinaryTree<E> arbol_clon = new ArbolBinarioEnlazado<E>(raiz);
		try {
			arbol_clon.createRoot(raiz.clone().element());
			clonar_rec(arbol_clon, arbol_clon.root(), raiz);
		}
		catch (Exception err) {
			err.printStackTrace();
		}
		return arbol_clon;
	}

	private void clonar_rec(BinaryTree<E> t, Position<E> p, BTNodo<E> raiz) {

	}

	public static <E> boolean subarbol(BinaryTree<E> t1, BinaryTree<E> t2) {
		try {
			Position<E> raiz_t1 = t1.root();
			Position<E> raiz_t2 = t2.root();

			Position<E> punto_en_comun = buscar_punto_en_comun(t1, raiz_t1, raiz_t2);
			
		}
		catch (Exception err) {
			err.printStackTrace();
		}
		return false;
	}

	private static <E> Position<E> buscar_punto_en_comun(BinaryTree<E> t, Position<E> raiz_t1, Position<E> raiz_t2) {
		Position<E> posible_punto_comun = raiz_t1;
		
		try {
			
			if (t.hasLeft(raiz_t1) && !(posible_punto_comun.element().equals(raiz_t2.element()))) {
				posible_punto_comun = buscar_punto_en_comun(t, t.left(raiz_t1), raiz_t2);
			}
			if (t.hasRight(raiz_t1) && !(posible_punto_comun.element().equals(raiz_t2.element()))) {
				posible_punto_comun = buscar_punto_en_comun(t, t.right(raiz_t1), raiz_t2);
			}
		}
		catch (Exception err) {
			err.printStackTrace();
		}
		
		return posible_punto_comun;
	}

	public static void main(String args[]) {
		ArbolBinarioEnlazado<Integer> b1 = new ArbolBinarioEnlazado<Integer>();
		ArbolBinarioEnlazado<Integer> b2 = new ArbolBinarioEnlazado<Integer>();
		try {

			Position<Integer> h0 = b1.createRoot(2);
			Position<Integer> h1 = b1.addLeft(h0, 22);
			Position<Integer> h2 = b1.addLeft(h1, 222);
			Position<Integer> h3 = b1.addRight(h1, 333);
			Position<Integer> h4 = b1.addLeft(h2, 2222);
			Position<Integer> h5 = b1.addRight(h2, 3333);
			Position<Integer> h6 = b1.addLeft(h4, 22222);
			Position<Integer> h7 = b1.addRight(h5, 33333);
			Position<Integer> h8 = b1.addLeft(h6, 222222);

			Position<Integer> j0 = b2.createRoot(1111);
			Position<Integer> j1 = b2.addLeft(j0, 2222);
			Position<Integer> j2 = b2.addRight(j0, 3333);
			Position<Integer> j3 = b2.addLeft(j1, 22222);
			Position<Integer> j4 = b2.addRight(j2, 33333);
			Position<Integer> j5 = b2.addLeft(j3, 222222);
			
			System.out.println(buscar_punto_en_comun(b1, h0, j0).element());

			System.out.println(b1.positions().toString());
			/*
			 * b1.espejar();
			System.out.println(b1.positions().toString());
			 */
			b1.padre_de_hojas();
		} catch (InvalidOperationException | InvalidPositionException e) {
			e.printStackTrace();
		}
	}
}
