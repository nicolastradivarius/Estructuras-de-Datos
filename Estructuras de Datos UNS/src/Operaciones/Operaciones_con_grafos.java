package Operaciones;

import TDAGrafo.*;

public class Operaciones_con_grafos {

	//objetos para las busquedas
	private static final Object VISITADO = new Object();
	private static final Object ESTADO = new Object();

	public static void main(String[] args) {

		Grafo_lista_adyacencias<Character, Integer> g1 = new Grafo_lista_adyacencias<>();

		Vertex<Character> h1 = g1.insertVertex('u');
		Vertex<Character> h2 = g1.insertVertex('v');
		Vertex<Character> h3 = g1.insertVertex('w');
		Vertex<Character> h4 = g1.insertVertex('w');
		Vertex<Character> h5 = g1.insertVertex('w');
		Vertex<Character> h6 = g1.insertVertex('y');

		try {
			Edge<Integer> a1 = g1.insertEdge(h1, h2, 5);
			Edge<Integer> a2 = g1.insertEdge(h2, h3, 7);
			Edge<Integer> a3 = g1.insertEdge(h3, h1, 13);
			Edge<Integer> a4 = g1.insertEdge(h2, h4, 42);
			Edge<Integer> a5 = g1.insertEdge(h2, h5, 32);
			Edge<Integer> a7 = g1.insertEdge(h5, h3, 12);
			Edge<Integer> a8 = g1.insertEdge(h2, h6, 42);
		}
		catch (InvalidVertexException err) {
			err.printStackTrace();
		}

	}


	public static <V,E> void dfs(Graph<V,E> g) {
		try {
			for (Vertex<V> w : g.vertices())
				w.put(ESTADO, null);

			for (Vertex<V> v : g.vertices()) {
				if (v.get(ESTADO) == null)
					dfs_aux(g, v);
			}
		}
		catch (TDAMapeo.InvalidKeyException err) {
			err.printStackTrace();
		}
	}


	private static <V,E> void dfs_aux(Graph<V, E> g, Vertex<V> v) {
		System.out.print(v.element() + " ");

		try {
			v.put(ESTADO, VISITADO);
			Iterable<Edge<E>> adyacentes = g.incidentEdges(v);

			for (Edge<E> e : adyacentes) {
				Vertex<V> w = g.opposite(v, e);
				if (w.get(ESTADO) == null)
					dfs_aux(g,w);
			}
		}
		catch (TDAMapeo.InvalidKeyException | InvalidVertexException | InvalidEdgeException err) {
			err.printStackTrace();
		}
	}

	public static <V,E> void bfs (Graph<V,E> g) {
		try {
			for (Vertex<V> w : g.vertices()) {
				w.put(ESTADO, null);
			}

			for (Vertex<V> v : g.vertices()) {
				if (v.get(ESTADO) == null) {
					bfs_aux(g, v);
				}
			}
		}
		catch (TDAMapeo.InvalidKeyException err) {
			err.printStackTrace();
		}
	}


	private static <V,E> void bfs_aux(Graph<V, E> g, Vertex<V> v) {
		TDACola.Queue<Vertex<V>> cola = new TDACola.Cola_con_enlaces<>();

		try {
			cola.enqueue(v);
			v.put(ESTADO, VISITADO);
			while (!cola.isEmpty()) {
				Vertex<V> u = cola.dequeue();
				System.out.print(u.element() + " ");
				for (Edge<E> arco : g.incidentEdges(u)) {
					Vertex<V> x = g.opposite(u, arco);
					if (x.get(ESTADO) == null) {
						x.put(ESTADO, VISITADO);
						cola.enqueue(x);
					}
				}
			}
		}
		catch (TDAMapeo.InvalidKeyException | InvalidVertexException | InvalidEdgeException | TDACola.EmptyQueueException err) {
			err.printStackTrace();
		}
	}
}
