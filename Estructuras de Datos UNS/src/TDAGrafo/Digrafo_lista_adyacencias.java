package TDAGrafo;

import TDALista.PositionList;
import TDALista.ListaDoblementeEnlazada;

public class Digrafo_lista_adyacencias<V, E> implements GraphD<V, E> {

	protected PositionList<DVerticeParaListaAdyacencias<V,E>> nodos;
	protected PositionList<DArcoParaListaAdyacencias<V,E>> arcos;

	@Override
	public Iterable<Vertex<V>> vertices() {
		PositionList<Vertex<V>> lista_vertices = new ListaDoblementeEnlazada<Vertex<V>>();

		for (Vertex<V> caminante : this.nodos) {
			lista_vertices.addLast(caminante);
		}

		return lista_vertices;
	}

	@Override
	public Iterable<Edge<E>> edges() {
		PositionList<Edge<E>> lista_arcos = new ListaDoblementeEnlazada<Edge<E>>();

		for (DArcoParaListaAdyacencias<V, E> caminante : this.arcos) {
			lista_arcos.addLast(caminante);
		}

		return lista_arcos;
	}

	@Override
	public Iterable<Edge<E>> incidentEdges(Vertex<V> v) throws InvalidVertexException {
		DVerticeParaListaAdyacencias<V,E> vertice = checkVertex(v);
		PositionList<Edge<E>> lista_arcos_incidentes = new ListaDoblementeEnlazada<>();

		for (DArcoParaListaAdyacencias<V,E> caminante : this.arcos) {
			if (caminante.getV2() == vertice)
				lista_arcos_incidentes.addLast(caminante);
		}

		return lista_arcos_incidentes;
	}

	@Override
	public Iterable<Edge<E>> succesorEdges(Vertex<V> v) throws InvalidVertexException {
		DVerticeParaListaAdyacencias<V,E> vertice = checkVertex(v);
		PositionList<Edge<E>> lista_arcos_emergentes = new ListaDoblementeEnlazada<>();

		for (DArcoParaListaAdyacencias<V,E> caminante : this.arcos) {
			if (caminante.getV1() == vertice)
				lista_arcos_emergentes.addLast(caminante);
		}

		return lista_arcos_emergentes;
	}

	@Override
	public Vertex<V> opposite(Vertex<V> v, Edge<E> e) throws InvalidVertexException, InvalidEdgeException {
		DVerticeParaListaAdyacencias<V,E> vertice = checkVertex(v);
		DArcoParaListaAdyacencias<V,E> arco = checkEdge(e);
		Vertex<V> opuesto = null;

		if (arco.getV1() == vertice) { 
			opuesto = arco.getV2();
		}
		else
			throw new InvalidVertexException("El vértice parametrizado no es el origen del arco parametrizado");

		return opuesto;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Vertex<V>[] endvertices(Edge<E> e) throws InvalidEdgeException {
		DArcoParaListaAdyacencias<V,E> arco = checkEdge(e);
		Vertex<V>[] arreglo_vertices_del_arco = (Vertex<V>[]) new DVerticeParaListaAdyacencias[2];

		arreglo_vertices_del_arco[0] = arco.getV1();
		arreglo_vertices_del_arco[1] = arco.getV2();

		return arreglo_vertices_del_arco;
	}

	@Override
	public boolean areAdjacent(Vertex<V> v, Vertex<V> w) throws InvalidVertexException {
		DVerticeParaListaAdyacencias<V,E> v1 = checkVertex(v);
		DVerticeParaListaAdyacencias<V,E> v2 = checkVertex(w);
		boolean sonAdyacentes = false;

		for (DArcoParaListaAdyacencias<V,E> caminante : v1.getEmergentes()) {
			if (caminante.getV2() == v2)
				sonAdyacentes = true;
		}

		return sonAdyacentes;
	}

	@Override
	public V replace(Vertex<V> v, V x) throws InvalidVertexException {
		if (x == null)
			throw new InvalidVertexException("El rótulo que se va a emplazar en el vértice es inválido");

		V valorViejo = null;
		DVerticeParaListaAdyacencias<V,E> vertice = checkVertex(v);

		valorViejo = vertice.element();
		vertice.setRotulo(x);

		return valorViejo;
	}

	@Override
	public Vertex<V> insertVertex(V x) {
		DVerticeParaListaAdyacencias<V,E> nuevo = null;
		try {
			if (x != null)  {
				nuevo = new DVerticeParaListaAdyacencias<>();
				nuevo.setRotulo(x);
				this.nodos.addLast(nuevo);
				nuevo.setPosicionEnListaVertices(nodos.last());
			}
		}
		catch (TDALista.EmptyListException err) {
			err.printStackTrace();
		}

		return nuevo;
	}

	@Override
	public Edge<E> insertEdge(Vertex<V> v, Vertex<V> w, E e) throws InvalidVertexException {
		DVerticeParaListaAdyacencias<V,E> v1 = checkVertex(v);
		DVerticeParaListaAdyacencias<V,E> v2 = checkVertex(v);
		DArcoParaListaAdyacencias<V,E> nuevo = new DArcoParaListaAdyacencias<>();

		try {
			if (e != null) {
				nuevo.setRotulo(e);
				this.arcos.addLast(nuevo);
				nuevo.setPosicionEnArcos(arcos.last());

				nuevo.setV1(v1);
				v1.getEmergentes().addLast(nuevo);

				nuevo.setV2(v2);
				v2.getIncidentes().addLast(nuevo);
			}
		}
		catch (TDALista.EmptyListException err) {
			err.printStackTrace();
		}

		return nuevo;
	}

	@Override
	public V removeVertex(Vertex<V> v) throws InvalidVertexException {
		DVerticeParaListaAdyacencias<V,E> vertice = checkVertex(v);
		V valorViejo = null;

		try {
			for (DArcoParaListaAdyacencias<V,E> caminante1 : vertice.getIncidentes()) {
				removeEdge(caminante1);
			}
			for (DArcoParaListaAdyacencias<V,E> caminante2 : vertice.getEmergentes()) {
				removeEdge(caminante2);
			}

			valorViejo = vertice.element();
			vertice.setRotulo(null);
			this.nodos.remove(vertice.getPosicionEnListaVertices());
		}
		catch (TDALista.InvalidPositionException | InvalidEdgeException err) {
			err.printStackTrace();
		}

		return valorViejo;
	}

	@Override
	public E removeEdge(Edge<E> e) throws InvalidEdgeException {
		DArcoParaListaAdyacencias<V,E> arco = checkEdge(e);
		E valorViejo = null;

		DVerticeParaListaAdyacencias<V,E> extremo1 = arco.getV1();
		DVerticeParaListaAdyacencias<V,E> extremo2 = arco.getV2();

		try {

			extremo1.getEmergentes().remove(arco.getPosicionEnEmergentes());
			extremo2.getIncidentes().remove(arco.getPosicionEnIncidentes());
			
			valorViejo = arco.element();
			arco.setRotulo(null);
			arco.setV1(null);
			arco.setV2(null);
			this.arcos.remove(arco.getPosicionEnArcos());
		}
		catch (TDALista.InvalidPositionException err) {
			err.printStackTrace();
		}
		
		return valorViejo;
	}

	@SuppressWarnings("unchecked")
	private DVerticeParaListaAdyacencias<V, E> checkVertex(Vertex<V> v) throws InvalidVertexException {
		try {
			if (v == null | v.element() == null)
				throw new InvalidVertexException("Vértice inválido");

			return (DVerticeParaListaAdyacencias<V,E>) v;
		}
		catch (ClassCastException err) {
			throw new InvalidVertexException("El vértice no es de tipo DVerticeParaListaAdyacencias");
		}
	}

	@SuppressWarnings("unchecked")
	private DArcoParaListaAdyacencias<V, E> checkEdge(Edge<E> e) throws InvalidEdgeException {
		try {
			if (e == null | e.element() == null)
				throw new InvalidEdgeException("El arco es inválido");

			return (DArcoParaListaAdyacencias<V,E>) e;
		}
		catch (ClassCastException err) {
			throw new InvalidEdgeException("El arco no es de tipo DArcoParaListaAdyacencias");
		}
	}

}
