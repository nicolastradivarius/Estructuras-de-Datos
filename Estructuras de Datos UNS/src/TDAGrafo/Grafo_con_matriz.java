package TDAGrafo;

import TDALista.EmptyListException;
import TDALista.InvalidPositionException;
import TDALista.ListaDoblementeEnlazada;
import TDALista.PositionList;

public class Grafo_con_matriz<V, E> implements Graph<V, E> {

	protected PositionList<Vertex<V>> vertices;
	protected PositionList<Edge<E>> arcos;
	protected ArcoParaMatriz<V,E> [][] matriz;
	protected int cantidadVertices;

	@SuppressWarnings("unchecked")
	public Grafo_con_matriz(int n) {
		vertices = new ListaDoblementeEnlazada<>();
		arcos = new ListaDoblementeEnlazada<>();
		matriz = new ArcoParaMatriz[n][n];
		cantidadVertices = 0;

		//innecesario en java
		for (int i=0; i < matriz.length; i++) {
			for (int j=0; j < matriz[0].length; j++) {
				matriz[i][j] = null;
			}
		}
	}

	@Override
	public Iterable<Vertex<V>> vertices() {
		PositionList<Vertex<V>> lista_vertices = new ListaDoblementeEnlazada<>();

		for (Vertex<V> caminante : this.vertices) {
			lista_vertices.addLast(caminante);
		}

		return lista_vertices;
	}

	@Override
	public Iterable<Edge<E>> edges() {
		PositionList<Edge<E>> lista_arcos = new ListaDoblementeEnlazada<>();

		for (Edge<E> caminante : this.arcos) {
			lista_arcos.addLast(caminante);
		}

		return lista_arcos;
	}

	@Override
	public Iterable<Edge<E>> incidentEdges(Vertex<V> v) throws InvalidVertexException {
		VerticeParaMatriz<V> vertice = checkVertex(v);
		int fila = vertice.getIndice();
		PositionList<Edge<E>> lista_arcos_incidentes = new ListaDoblementeEnlazada<>();

		for (int i = 0; i < cantidadVertices; i++) {
			if (matriz[fila][i] != null)
				lista_arcos_incidentes.addLast(matriz[fila][i]);
		}

		return lista_arcos_incidentes;
	}

	@Override
	public Vertex<V> opposite(Vertex<V> v, Edge<E> e) throws InvalidVertexException, InvalidEdgeException {
		ArcoParaMatriz<V,E> arco = checkEdge(e);
		VerticeParaMatriz<V> vertice = checkVertex(v);
		Vertex<V> opuesto = null;

		if (arco.getV1() == vertice)
			opuesto = arco.getV2();
		else if (arco.getV2() == vertice)
			opuesto = arco.getV1();
		else
			throw new InvalidVertexException("El vértice parametrizado no es un extremo del arco parametrizado");

		return opuesto;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Vertex<V>[] endvertices(Edge<E> e) throws InvalidEdgeException {
		ArcoParaMatriz<V,E> arco = checkEdge(e);
		Vertex<V>[] extremos = (Vertex<V> []) new VerticeParaMatriz[2];

		extremos[0] = arco.getV1();
		extremos[1] = arco.getV2();

		return extremos;
	}

	@Override
	public boolean areAdjacent(Vertex<V> v, Vertex<V> w) throws InvalidVertexException {
		VerticeParaMatriz<V> v1 = checkVertex(v);
		VerticeParaMatriz<V> v2 = checkVertex(w);
		boolean sonAdyacentes = false;
		int fila = v1.getIndice();
		int col = v2.getIndice();

		sonAdyacentes = matriz[fila][col] != null;

		return sonAdyacentes;
	}

	@Override
	public V replace(Vertex<V> v, V x) throws InvalidVertexException {
		VerticeParaMatriz<V> vertice = checkVertex(v);
		V viejoRotulo = vertice.element();
		
		vertice.setRotulo(x);
		
		return viejoRotulo;
	}

	@Override
	public Vertex<V> insertVertex(V x) {
		VerticeParaMatriz<V> vertice = new VerticeParaMatriz<V>(x, cantidadVertices++);

		if (cantidadVertices >= matriz.length)
			redimensionar_matriz();

		try {
			this.vertices.addLast(vertice);
			vertice.setPosicionEnVertices(vertices.last());
			cantidadVertices++;
			vertice.setIndice(cantidadVertices);
		}
		catch (TDALista.EmptyListException err) {
			err.printStackTrace();
		}

		return vertice;
	}

	@SuppressWarnings("unchecked")
	private void redimensionar_matriz() {
		ArcoParaMatriz<V,E>[][] matriz_orig = matriz;

		matriz = new ArcoParaMatriz[matriz_orig.length*2][matriz_orig.length*2];

		for (int i = 0; i < matriz_orig.length; i++) {
			for (int j = 0; j < matriz_orig[0].length; j++) {
				matriz[i][j] = matriz_orig[i][j];
			}
		}
	}

	@Override
	public Edge<E> insertEdge(Vertex<V> v, Vertex<V> w, E e) throws InvalidVertexException {
		VerticeParaMatriz<V> v1 = checkVertex(v);
		VerticeParaMatriz<V> v2 = checkVertex(w);
		int fila = v1.getIndice();
		int columna = v2.getIndice();
		ArcoParaMatriz<V,E> arco = new ArcoParaMatriz<>(e);

		arco.setV1(v1);
		arco.setV2(v2);
		//grafo no dirigido => matriz simetrica
		matriz[fila][columna] = arco;
		matriz[columna][fila] = arco;

		try {
			//cargo arco en lista de arcos
			arcos.addLast(arco);
			arco.setPosicionEnArcos(arcos.last());
		} 
		catch (EmptyListException e1) {
			e1.printStackTrace();
		}

		return arco;
	}

	@Override
	public V removeVertex(Vertex<V> v) throws InvalidVertexException {
		VerticeParaMatriz<V> a_eliminar = checkVertex(v);
		V valorViejo = null;

		int fila = a_eliminar.getIndice();
		int col = a_eliminar.getIndice();

		try {

			for (int i=0; i < matriz.length; i++) {
				arcos.remove(matriz[i][col].getPosicionEnArcos());
				matriz[i][col] = null;
			}

			for (int j=0; j < matriz[0].length; j++) {
				arcos.remove(matriz[fila][j].getPosicionEnArcos());
				matriz[fila][j] = null;
			}
			
			valorViejo = a_eliminar.element();
			vertices.remove(a_eliminar.getPosicionEnVertices());
			
		}
		catch (TDALista.InvalidPositionException err) {
			err.printStackTrace();
		}

		return valorViejo;
	}

	@Override
	public E removeEdge(Edge<E> e) throws InvalidEdgeException {
		ArcoParaMatriz<V,E> arco = checkEdge(e);

		try {
			int fila = arco.getV1().getIndice();
			int col = arco.getV2().getIndice();
			matriz[fila][col] = null;
			matriz[col][fila] = null;
			arcos.remove(arco.getPosicionEnArcos());
		}
		catch (InvalidPositionException err) {
			err.printStackTrace();
		}

		return arco.element();
	}

	@SuppressWarnings("unchecked")
	private ArcoParaMatriz<V, E> checkEdge(Edge<E> e) throws InvalidEdgeException {
		try {
			if (e == null | e.element() == null)
				throw new InvalidEdgeException("Arco inválido");
	
			return (ArcoParaMatriz<V,E>) e;
		}
		catch (ClassCastException err) {
			throw new InvalidEdgeException("El arco no es de tipo ArcoParaMatriz");
		}
	}

	private VerticeParaMatriz<V> checkVertex(Vertex<V> v) throws InvalidVertexException {
		try {
			if (v == null | v.element() == null)
				throw new InvalidVertexException("Vértice inválido");
	
			return (VerticeParaMatriz<V>) v;
		}
		catch (ClassCastException e) {
			throw new InvalidVertexException("El vértice parametrizado no es del tipo VerticeParaMatriz");
		}
	}

}
