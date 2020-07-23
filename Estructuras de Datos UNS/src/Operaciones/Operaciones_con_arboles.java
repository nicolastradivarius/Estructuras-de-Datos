package Operaciones;


import java.util.Iterator;

import TDAArbol.*;
import TDALista.ListaDoblementeEnlazada;
import TDALista.PositionList;

public class Operaciones_con_arboles {

	public static void main(String args[]) {
		try {
			Arbol_enlazado<Integer> t1 = new Arbol_enlazado<>();

			t1.createRoot(1);
			Position<Integer> h1 = t1.root();
			Position<Integer> h2 = t1.addFirstChild(h1, 2);
			Position<Integer> h3 = t1.addAfter(h1, h2, 3);
			Position<Integer> h4 = t1.addAfter(h1, h3, 4);
			Position<Integer> h5 = t1.addLastChild(h2, 5);
			Position<Integer> h6 = t1.addLastChild(h2, 6);
			Position<Integer> h7 = t1.addLastChild(h3, 9);
			Position<Integer> h8 = t1.addLastChild(h4, 7);
			Position<Integer> h9 = t1.addLastChild(h4, 9);
			Position<Integer> h10 = t1.addLastChild(h4, 2);
			Position<Integer> h11 = t1.addLastChild(h8, 8);


			preorden(t1);

			eliminarPrimos(t1, h10);
System.out.println();
			preorden(t1);
		}
		catch (Exception err) {
			err.printStackTrace();
		}
	}

	public static void eliminarPrimos(Tree<Integer> t, Position<Integer> p) {
		PositionList<Position<Integer>> nodosAEliminar = new ListaDoblementeEnlazada<>();
		try {
			if (!t.isEmpty()) {
				if (perteneceAArbol(t, p)) {
					if (p != t.root() && t.parent(p) != t.root() && t.parent(p) != null && t.parent(t.parent(p)) != null) {
						eliminarPrimos_aux(t, p, t.root(), nodosAEliminar);
					}
				}
			}

			for (Position<Integer> pos : nodosAEliminar) {
				t.removeNode(pos);
			}
		}
		catch (EmptyTreeException | InvalidPositionException | BoundaryViolationException err) {
			err.printStackTrace();
		}
	}

	private static boolean perteneceAArbol(Tree<Integer> t, Position<Integer> p) {
		Iterable<Position<Integer>> it = t.positions();
		boolean pertenece = false;
		
		for (Position<Integer> i : it) {
			if (i == p)
				pertenece = true;
		}
		
		return pertenece;
	}

	public static  <E> void preorden(Tree<E> t) {
		try {
			preorden_aux(t, t.root());
		}
		catch (EmptyTreeException err) {
			err.printStackTrace();
		}
	}

	private static <E> void preorden_aux(Tree<E> t, Position<E> v) {
		System.out.print(v.element() + "-");
		try {
			for (Position<E> p : t.children(v)) {
				preorden_aux(t, p);
			}
		}
		catch (Exception err) {
			err.printStackTrace();
		}
	}

	private static void eliminarPrimos_aux(Tree<Integer> t, Position<Integer> p, Position<Integer> nodo, PositionList<Position<Integer>> nodosAEliminar) {
		try {

			Iterator<Position<Integer>> it = t.children(nodo).iterator();

			if (nodo != t.root() && t.parent(nodo) != t.root() && t.parent(nodo) != null && t.parent(t.parent(nodo)) != null) {
				if (t.parent(t.parent(nodo)) == t.parent(t.parent(p)) && nodo.element() > p.element()) {
					nodosAEliminar.addLast(nodo);
				}
			}
			else {
				while (it.hasNext()) {
					nodo = it.next();
					eliminarPrimos_aux(t, p, nodo, nodosAEliminar);
				}
			}

		}
		catch (InvalidPositionException | BoundaryViolationException | EmptyTreeException err) {
			err.printStackTrace();
		}


	}

}
