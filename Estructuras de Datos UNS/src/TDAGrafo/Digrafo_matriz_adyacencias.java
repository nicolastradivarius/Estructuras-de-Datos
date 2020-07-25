package TDAGrafo;

import TDALista.ListaDoblementeEnlazada;
import TDALista.PositionList;

public class Digrafo_matriz_adyacencias<V,E> implements GraphD <V,E>{

	protected PositionList<Vertex<V>> vertices;
	protected PositionList<Edge<E>> arcos;
	protected DArcoParaMatriz<V,E> [][] matriz;
	protected int cantidadVertices;

	@SuppressWarnings("unchecked")
	public Digrafo_matriz_adyacencias() {
		vertices = new ListaDoblementeEnlazada<Vertex<V>>();
		arcos = new ListaDoblementeEnlazada<Edge<E>>();
		cantidadVertices = 0;
		matriz = (DArcoParaMatriz<V, E>[][]) new DArcoParaMatriz[3][3];
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
		DVerticeParaMatriz<V,E> vertice = checkVertex(v);
		PositionList<Edge<E>> arcos_incidentes = new ListaDoblementeEnlazada<>();

		int col = vertice.getIndice();

		for (int fila = 0; fila < matriz.length; fila++) {
			if (matriz[fila][col] != null)
				arcos_incidentes.addLast(matriz[fila][col]);
		}

		return arcos_incidentes;
	}

	@Override
	public Iterable<Edge<E>> succesorEdges(Vertex<V> v) throws InvalidVertexException {
		DVerticeParaMatriz<V,E> vertice = checkVertex(v);
		PositionList<Edge<E>> arcos_emergentes = new ListaDoblementeEnlazada<>();

		int fila = vertice.getIndice();

		for (int col = 0; col < matriz[0].length; col++) {
			if (matriz[fila][col] != null)
				arcos_emergentes.addLast(matriz[fila][col]);
		}

		return arcos_emergentes;
	}

	@Override
	public Vertex<V> opposite(Vertex<V> v, Edge<E> e) throws InvalidVertexException, InvalidEdgeException {
		DVerticeParaMatriz<V,E> vertice = checkVertex(v);
		DArcoParaMatriz<V,E> arco = checkEdge(e);
		Vertex<V> opuesto = null;

		if (arco.getV1() == vertice)
			opuesto = arco.getV2();
		else
			throw new InvalidVertexException("El vértice parametrizado no es el origen del arco parametrizado");

		return opuesto;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Vertex<V>[] endvertices(Edge<E> e) throws InvalidEdgeException {
		DArcoParaMatriz<V,E> arco = checkEdge(e);
		Vertex<V> [] extremos = (Vertex<V> []) new DVerticeParaMatriz[2];

		extremos[0] = arco.getV1();
		extremos[1] = arco.getV2();

		return extremos;
	}

	@Override
	public boolean areAdjacent(Vertex<V> v, Vertex<V> w) throws InvalidVertexException {
		DVerticeParaMatriz<V,E> v1 = checkVertex(v);
		DVerticeParaMatriz<V,E> v2 = checkVertex(w);
		boolean sonAdyacentes = false;

		int fila = v1.getIndice();
		int col = v2.getIndice();

		if (matriz[fila][col] != null)
			sonAdyacentes = true;

		return sonAdyacentes;
	}

	@Override
	public V replace(Vertex<V> v, V x) throws InvalidVertexException {
		DVerticeParaMatriz<V,E> vertice = checkVertex(v);
		V viejoValor = null;

		viejoValor = vertice.element();
		vertice.setRotulo(x);

		return viejoValor;
	}

	@Override
	public Vertex<V> insertVertex(V x) {

		if (cantidadVertices >= matriz.length)
			redimensionar_matriz();

		DVerticeParaMatriz<V,E> nuevo_vertice = new DVerticeParaMatriz<V,E>(x);

		try {
			this.vertices.addLast(nuevo_vertice);
			nuevo_vertice.setPosicionEnVertices(vertices.last());
			cantidadVertices++;
			nuevo_vertice.setIndice(cantidadVertices);
		}
		catch (TDALista.EmptyListException err) {
			err.printStackTrace();
		}

		return nuevo_vertice;
	}

	@SuppressWarnings("unchecked")
	private void redimensionar_matriz() {
		DArcoParaMatriz<V,E> [][] matriz_original = matriz;
		matriz = (DArcoParaMatriz<V,E> [][]) new DArcoParaMatriz[matriz.length*2] [matriz.length*2];


		for (int i=0; i < matriz_original.length;i++) {
			for (int j=0; j < matriz_original[0].length; j++) {
				matriz[i][j] = matriz_original[i][j];
			}
		}

	}

	@Override
	public Edge<E> insertEdge(Vertex<V> v, Vertex<V> w, E e) throws InvalidVertexException {
		DVerticeParaMatriz<V,E> v1 = checkVertex(v);
		DVerticeParaMatriz<V,E> v2 = checkVertex(w);

		DArcoParaMatriz<V,E> nuevo_arco = new DArcoParaMatriz<>(e);

		int fila = v1.getIndice();
		int col = v2.getIndice();

		if (matriz[fila][col] != null)
			matriz[fila][col].setRotulo(e);
		else
			matriz[fila][col] = nuevo_arco;

		return matriz[fila][col];
	}

	@Override
	public V removeVertex(Vertex<V> v) throws InvalidVertexException {
		DVerticeParaMatriz<V,E> vertice = checkVertex(v);
		V valorViejo = null;
		int fila = vertice.getIndice();
		int col = fila;

		try {

			for (int i = 0; i < matriz[0].length; i++) {
				this.arcos.remove(matriz[fila][i].getPosicionEnArcos());
				matriz[fila][i] = null;
			}

			for (int j=0; j < matriz.length; j++) {
				this.arcos.remove(matriz[j][col].getPosicionEnArcos());
				matriz[j][col] = null;
			}

			valorViejo = vertice.element();
			vertice.setRotulo(null);
			this.vertices.remove(vertice.getPosicionEnVertices());
			cantidadVertices--;
		}
		catch (TDALista.InvalidPositionException err) {
			err.printStackTrace();
		}

		return valorViejo;
	}

	@SuppressWarnings("unchecked")
	@Override
	public E removeEdge(Edge<E> e) throws InvalidEdgeException {
		DArcoParaMatriz<V,E> arco = checkEdge(e);
		E valorViejo = null;
		
		int indice = ((DVerticeParaMatriz<V, E>) arco.getV1()).getIndice();

		try {
			valorViejo = arco.element();
			this.arcos.remove(matriz[indice][indice].getPosicionEnArcos());
			matriz[indice][indice] = null;
		}
		catch (TDALista.InvalidPositionException er) {
			er.printStackTrace();
		}
		
		return valorViejo;
	}

	@SuppressWarnings("unchecked")
	private DVerticeParaMatriz<V, E> checkVertex(Vertex<V> v) throws InvalidVertexException {
		try {
			if (v == null | v.element() == null)
				throw new InvalidVertexException("Vértice inválido");

			return (DVerticeParaMatriz<V,E>) v;
		}
		catch (ClassCastException err) {
			throw new InvalidVertexException("El vértice parametrizado no es de tipo DVerticeParaMatriz");
		}
	}

	@SuppressWarnings("unchecked")
	private DArcoParaMatriz<V, E> checkEdge(Edge<E> e) throws InvalidEdgeException{
		try {
			if (e == null | e.element() == null)
				throw new InvalidEdgeException("Arco inválido");

			return (DArcoParaMatriz<V,E>) e;
		}
		catch (ClassCastException err) {
			throw new InvalidEdgeException("El arco parametrizado no es de tipo DArcoParaMatriz");
		}
	}

}
