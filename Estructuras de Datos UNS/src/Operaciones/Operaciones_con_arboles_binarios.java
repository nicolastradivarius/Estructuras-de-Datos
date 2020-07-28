package Operaciones;

import TDAArbolBinario.*;
import TDACola.EmptyQueueException;
import TDACola.Queue;
import TDAColaCP.PriorityQueue;
import TDALista.PositionList;
import TDAMapeo.Entry;
import TDAMapeo.InvalidKeyException;
import TDAMapeo.Map;
import TDAMapeo.Mapeo_con_lista;
import TDALista.ListaDoblementeEnlazada;
import TDAPila.*;

public class Operaciones_con_arboles_binarios {

	//EJERCICIO (a)
	public static <E> void preorden (BinaryTree<E> t) {

		try {
			if (!t.isEmpty()) {
				Position<E> raiz = t.root();
				pre(t, raiz);
			}
		}
		catch (EmptyTreeException err) {
			err.printStackTrace();
		}
	}

	private static <E> void pre(BinaryTree<E> t, Position<E> v) {
		System.out.println(v);
		try {

			if (t.hasLeft(v)) 
				pre(t, t.left(v));

			if (t.hasRight(v))
				pre(t, t.right(v));
		}
		catch (InvalidPositionException | BoundaryViolationException err) {
			err.printStackTrace();
		}
	}

	//EJERCICIO (b)
	public static <E> void postorden(BinaryTree<E> t) {
		try {
			if (!t.isEmpty()) {
				Position<E> raiz = t.root();
				post(t, raiz);
			}
		}
		catch (EmptyTreeException err) {
			err.printStackTrace();
		}
	}

	private static <E> void post(BinaryTree<E> t, Position<E> v) {

		try {
			if (t.hasLeft(v))
				post(t, t.left(v));

			if (t.hasRight(v))
				post(t, t.right(v));

			System.out.println(v);
		}
		catch (InvalidPositionException | BoundaryViolationException err) {
			err.printStackTrace();
		}
	}

	//EJERCICIO (c)
	public static <E> void por_niveles(BinaryTree<E> t) {
		try {
			if (!t.isEmpty()) {
				int nivel = 0;
				Position<E> raiz = t.root();
				niveles(t, raiz, nivel);
			}
		}
		catch (EmptyTreeException err) {
			err.printStackTrace();
		}
	}

	private static <E> void niveles(BinaryTree<E> t, Position<E> v, int nivel) {

		int nivel_izq = nivel;
		int nivel_der = nivel;
		try {
			System.out.println(v + " " + "Nivel" + " " + nivel);

			if (t.hasLeft(v)) {
				nivel_izq++;
				niveles(t, t.left(v), nivel_izq);
			}

			if (t.hasRight(v)) {
				nivel_der++;
				niveles(t, t.right(v), nivel_der);
			}
		}
		catch (InvalidPositionException | BoundaryViolationException err) {
			err.printStackTrace();
		}
	}

	//Ejercicio (d)
	//Profundidad de un nodo p en un arbol t: longitud del camino de la raiz de T al nodo p:
	//cantidad de ancestros propios de p
	public static <E> int profundidad(BinaryTree<E> t, Position<E> p) {
		int prof = 0;
		return profundidad_rec(t, p, prof);
	}

	private static <E> int profundidad_rec(BinaryTree<E> t, Position<E> p, int prof) {
		try {
			if (t.isRoot(p))
				prof = 0;
			else
				prof = 1 + profundidad_rec(t, t.parent(p), prof);
		}
		catch (InvalidPositionException | BoundaryViolationException e) {
			e.printStackTrace();
		}

		return prof;
	}

	//ejercicio (e)
	//Altura de un nodo p en un árbol T: longitud del camino mas largo
	//a una hoja en el subarbol con raiz v
	public static <E> int altura (BinaryTree<E> t, Position<E> p) {
		int altura = 0;
		try {
			if (t.isExternal(p))
				altura = 0;
			else {
				int h = 0;
				for (Position<E> w: t.children(p)) {
					h = Math.max(h, altura(t, w));
				}
				altura = 1+h;
			}
		}
		catch (InvalidPositionException err) {
			err.printStackTrace();
		}

		return altura;
	}

	//EJercicio (f)
	public static <E> PositionList<E> camino(BinaryTree<E> t, Position<E> p1, Position<E> p2){
		Stack<E> pila1 = new Pila_con_enlaces<E>();
		Stack<E> pila2 = new Pila_con_enlaces<E>();
		PositionList<E> camino = new ListaDoblementeEnlazada<E>();

		try {
			while (p1 != null) {
				pila1.push(p1.element());
				p1 = (t.isRoot(p1)) ? null : t.parent(p1);
			}

			while (p2 != null) {
				pila2.push(p2.element());
				p2 = (t.isRoot(p2)) ? null : t.parent(p2);
			}

			E desapilado1 = pila1.pop(); 
			E desapilado2 = pila2.pop();
			E aux = desapilado1;

			while (!pila1.isEmpty() && !pila2.isEmpty() && pila1.top().equals(pila2.top())) {
				aux = pila1.pop();
				pila2.pop();
			}

			while (!pila1.isEmpty())
				camino.addFirst(pila1.pop());

			camino.addLast(aux);

			while(!pila2.isEmpty())
				camino.addLast(pila2.pop());
		}
		catch (InvalidPositionException | BoundaryViolationException | EmptyStackException err) {
			err.printStackTrace();
		}

		return camino;
	}

	//EJERCICIO (g)
	public static <E> BinaryTree<E> clonar(BinaryTree<E> t1) {
		BinaryTree<E> t2 = new ArbolBinarioEnlazado<E>();
		try {
			if (!t1.isEmpty()) {
				Position<E> raiz = t1.root();
				t2.createRoot(raiz.element());
				if (t1.isInternal(raiz)) {
					clonar_recursivo(raiz, t2.root(), t1, t2);
				}
			}
		}
		catch (EmptyTreeException | InvalidOperationException | InvalidPositionException err) {
			err.printStackTrace();
		}

		return t2;
	}

	private static <E> void clonar_recursivo(TDAArbolBinario.Position<E> padre_t1, TDAArbolBinario.Position<E> padre_t2, BinaryTree<E> t1, BinaryTree<E> t2) {
		Position<E> nuevoPadre_t2;
		try {

			if (t1.hasLeft(padre_t1)) {
				nuevoPadre_t2 = t2.addLeft(padre_t2, t1.left(padre_t1).element());
				clonar_recursivo(t1.left(padre_t1), nuevoPadre_t2, t1, t2);
			}
			if (t1.hasRight(padre_t1)) {
				nuevoPadre_t2 = t2.addRight(padre_t2, t1.right(padre_t1).element());
				clonar_recursivo(t1.right(padre_t1), nuevoPadre_t2, t1, t2);
			}
		}

		catch (InvalidPositionException | InvalidOperationException | BoundaryViolationException err) {
			err.printStackTrace();
		}

	}

	//EJERCICIO (h)
	public static <E> BinaryTree<E> clonar_espejado(BinaryTree<E> t1) {
		BinaryTree<E> t2 = new ArbolBinarioEnlazado<E>();
		try {
			if (!t1.isEmpty()) {
				Position<E> raiz = t1.root();
				t2.createRoot(raiz.element());
				if (t1.isInternal(raiz)) {
					clonar_espejado_recursivo(raiz, t2.root(), t1, t2);
				}
			}
		}
		catch (EmptyTreeException | InvalidOperationException | InvalidPositionException err) {
			err.printStackTrace();
		}

		return t2;
	}

	private static <E> void clonar_espejado_recursivo(Position<E> padre_t1, Position<E> padre_t2, BinaryTree<E> t1, BinaryTree<E> t2) {
		Position<E> nuevoPadre_t2;
		try {
			if (t1.hasLeft(padre_t1)) {
				nuevoPadre_t2 = t2.addRight(padre_t2, t1.left(padre_t1).element());
				clonar_espejado_recursivo(t1.left(padre_t1), nuevoPadre_t2, t1, t2);
			}
			if (t1.hasRight(padre_t1)) {
				nuevoPadre_t2 = t2.addLeft(padre_t2, t1.right(padre_t1).element());
				clonar_espejado_recursivo(t1.right(padre_t1), nuevoPadre_t2, t1, t2);
			}
		}

		catch (InvalidPositionException | InvalidOperationException | BoundaryViolationException err) {
			err.printStackTrace();
		}

	}

	//EJERCICIO (i)
	public static <E> boolean iguales(BinaryTree<E> t1, BinaryTree<E> t2) {
		boolean sonIguales = true;
		try {
			if (!t1.isEmpty() && !t2.isEmpty()) {
				if (t1.root().element().equals(t2.root().element())) {
					sonIguales = iguales_aux(t1.root(), t2.root(), t1, t2, sonIguales);
				}
				else
					sonIguales = false;
			}
		}
		catch (EmptyTreeException err) {
			err.printStackTrace();
		}

		return sonIguales;
	}

	private static <E> boolean iguales_aux(Position<E> p1, Position<E> p2, BinaryTree<E> t1, BinaryTree<E> t2, boolean sonIguales) {
		try {
			if (t1.hasLeft(p1)) {
				if (t2.hasLeft(p2)) {
					if (t1.left(p1).element().equals(t2.left(p2).element())) {
						sonIguales = iguales_aux(t1.left(p1), t2.left(p2), t1, t2, sonIguales);
					}
					else
						sonIguales = false;
				}
				else
					sonIguales = false;
			}
			else {
				if (t2.hasLeft(p2))
					sonIguales = false;
			}

			if (t1.hasRight(p1)) {
				if (t2.hasRight(p2)) {
					if(t1.right(p1).element().equals(t2.right(p2).element())) {
						sonIguales = iguales_aux(t1.right(p1), t2.right(p2), t1, t2, sonIguales);
					}
					else
						sonIguales = false;
				}
				else
					sonIguales = false;
			}
			else
				if (t2.hasRight(p2)){
					sonIguales = false;
				}
		}
		catch (InvalidPositionException | BoundaryViolationException err) {
			err.printStackTrace();
		}

		return sonIguales;
	}

	public static <E> boolean subarbol(BinaryTree<E> t1, BinaryTree<E> t2) {
		boolean es_subarbol = true;

		try {
			if (!t1.isEmpty()) {
				if (!t2.isEmpty()) {
					es_subarbol = subarbol_recursivo(t1, t2, t1.root(), t2.root(), es_subarbol);
				}
			}
			else {
				if (!t2.isEmpty()) {
					es_subarbol = false;
				}
			}
		}
		catch (EmptyTreeException err) {
			err.printStackTrace();
		}

		return es_subarbol;
	}

	private static <E> boolean subarbol_recursivo(BinaryTree<E> t1, BinaryTree<E> t2,
			Position<E> pos_1, Position<E> pos_2, boolean es_subarbol) {

		try {
			if (pos_1.element().equals(pos_2.element()))
				es_subarbol = iguales(t1, t2, pos_1, pos_2, es_subarbol);
			else
				es_subarbol = false;

			if (!es_subarbol) {
				for (Position<E> hijo : t1.children(pos_1)) {
					es_subarbol = subarbol_recursivo(t1, t2, hijo, pos_2, es_subarbol);
					if (es_subarbol) {
						break;
					}
				}
			}
		}
		catch (InvalidPositionException e) {
			e.printStackTrace();
		}

		return es_subarbol;
	}

	private static <E> boolean iguales(BinaryTree<E> t1, BinaryTree<E> t2, Position<E> pos_1, Position<E> pos_2, boolean sonIguales) {
		sonIguales = true;
		try {
			if (t1.hasLeft(pos_1)) {
				if (t2.hasLeft(pos_2)) {
					if (sonIguales && t1.left(pos_1).element().equals(t2.left(pos_2).element())) {
						sonIguales = iguales(t1, t2, t1.left(pos_1), t2.left(pos_2), sonIguales);
					}
					else
						sonIguales = false;
				}

			}
			else if (t2.hasLeft(pos_2))
				sonIguales = false;

			if (t1.hasRight(pos_1)) {
				if (t2.hasRight(pos_2)) {
					if (sonIguales && t1.right(pos_1).element().equals(t2.right(pos_2).element())) {
						sonIguales = iguales(t1, t2, t1.right(pos_1), t2.right(pos_2), sonIguales);
					}
					else
						sonIguales = false;
				}

			}
			else if (t2.hasRight(pos_2))
				sonIguales = false;
		}
		catch (InvalidPositionException | BoundaryViolationException e) {
			e.printStackTrace();
		}

		return sonIguales;
	}

	public static <E> BinaryTree<E> clonar_arbol_perfecto(int h, PositionList<E> l) {
		return null;
	}

	public <E> boolean es_AVL(BinaryTree<E> b) {
		boolean es_avl = true;
		try {
			TDAArbolBinario.Position<E> root = b.root();
			es_avl = es_AVL_aux(b, root);
		}
		catch (TDAArbolBinario.EmptyTreeException err) {
			err.printStackTrace();
		}

		return es_avl;
	}

	private <E> boolean es_AVL_aux(BinaryTree<E> b, Position<E> p) {
		boolean esavl = true;

		try {
			if (b.hasLeft(p)) {
				if (b.hasRight(p)) {
					if (Math.abs(altura(b, b.left(p)) - altura(b, b.right(p))) > 1) {
						esavl = false;
					}
				}
				else {
					if (Math.abs(altura(b, b.left(p)) - 0) > 1) {
						esavl = false;
					}
				}
			}
			else {
				if (b.hasRight(p)) {
					if (Math.abs(0 - altura(b, b.right(p))) > 1) {
						esavl = false;
					}
				}
			}
		}
		catch (TDAArbolBinario.InvalidPositionException | BoundaryViolationException err) {
			err.printStackTrace();
		}

		return esavl;
	}


	public static void main(String args[]) {
		ArbolBinarioEnlazado<Integer> b1 = new ArbolBinarioEnlazado<Integer>();
		try {
	
			Position<Integer> h0 = b1.createRoot(2);
			Position<Integer> h1 = b1.addLeft(h0, 66);
			Position<Integer> h2 = b1.addRight(h0, 62);
			Position<Integer> h3 = b1.addLeft(h2, 929);
			Position<Integer> h5 = b1.addRight(h3, 7532);
			Position<Integer> h8 = b1.addLeft(h3, 2452);
			Position<Integer> h10 = b1.addRight(h8, 555543);
			Position<Integer> h6 = b1.addLeft(h5, 42032);
			Position<Integer> h7 = b1.addLeft(h1, 1145);
	
			/*preorden(b1);
			System.out.println("-----------------------------");
			postorden(b1);
			System.out.println("-----------------------------");
			por_niveles(b1);
			System.out.println("-----------------------------");
			System.out.println("Profundidad del nodo" + " " + "h0" + " " + "en el árbol T: " + profundidad(b1, b1.root()));
			System.out.println("Altura del nodo" + " " + "h0" + " " + "en el árbol T: " + altura(b1, h0));
			System.out.println("----------------------------");
	
			System.out.println(camino(b1, h5, h1));
			System.out.println("----------------------------");
			 */
			
			/*
			BinaryTree<Integer> b2 = new ArbolBinarioEnlazado<Integer>();
	
			Position<Integer> j0 = b2.createRoot(62);
			Position<Integer> j1 = b2.addLeft(j0, 929);
			Position<Integer> j2 = b2.addLeft(j1, 2452);
			Position<Integer> j3 = b2.addRight(j1, 7532);
	
			System.out.println(b1.positions().toString());
			por_niveles(b1);
			System.out.println("----------------");
			System.out.println(b2.positions().toString());
			por_niveles(b2);
	
			System.out.println(subarbol(b1, b2));
			*/
			
			System.out.println(huffman("aaaaaabbbaaaaaccd").positions().toString());
	
		} catch (InvalidOperationException | InvalidPositionException e) {
			e.printStackTrace();
		}
	}

	public static Map<Character, Float> calcularFrecuenciaApariciones(Queue<Character> q) {

		Map<Character, Float> m = new Mapeo_con_lista<Character, Float>();
		Map<Character, Float> resultado = new Mapeo_con_lista<Character, Float>();
		int cantidadTotalCaracteres = q.size();
		Float cantApariciones = 0f;
		char c = ' ';

		try {

			while (!q.isEmpty()) {
				c = q.dequeue();
				cantApariciones = m.get(c);
				if (cantApariciones == null)
					m.put(c, 1f);
				else
					m.put(c, cantApariciones + 1);
			}

			for (Entry<Character, Float> e : m.entries()) {
				resultado.put(e.getKey(), e.getValue()/cantidadTotalCaracteres);
			}

		}
		catch (EmptyQueueException | InvalidKeyException e) {
			e.printStackTrace();
		}
		return resultado;
	}

	public static BinaryTree<Character> huffman(String X){
		Queue<Character> cola_X = new TDACola.Cola_con_enlaces<>();
		PriorityQueue<Float, BinaryTree<Character>> Q = new TDAColaCP.ColaCP_con_lista<>();
		BinaryTree<Character> a_retornar = null;
		try {
			for (int i=0; i < X.length(); i++) {
				cola_X.enqueue(X.charAt(i));
			}

			Map<Character, Float> frecAp = calcularFrecuenciaApariciones(cola_X);

			for (int i=0; i < X.length(); i++) {
				BinaryTree<Character> T = new ArbolBinarioEnlazado<>();
				T.createRoot(X.charAt(i));
				Q.insert(frecAp.get(X.charAt(i)), T);
			}

			while (Q.size() > 1) {
				Float f1 = Q.min().getKey();
				BinaryTree<Character> T1 = Q.removeMin().getValue();

				Float f2 = Q.min().getKey();
				BinaryTree<Character> T2 = Q.removeMin().getValue();

				BinaryTree<Character> T = new ArbolBinarioEnlazado<Character>();
				T.createRoot('|');
				T.attach(T.root(), T1, T2);
				Q.insert(f1+f2, T);
			}
			
			a_retornar = Q.removeMin().getValue();
		}
		catch (Exception err) {
			err.printStackTrace();
		}

		return a_retornar;
	}
}