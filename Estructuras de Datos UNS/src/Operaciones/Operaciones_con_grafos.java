package Operaciones;

import TDAGrafo.*;

public class Operaciones_con_grafos {

	//objetos para las busquedas
	private static final Object VISITADO = new Object();
	private static final Object ESTADO = new Object();

	public static void main(String[] args) {

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
	}
}
