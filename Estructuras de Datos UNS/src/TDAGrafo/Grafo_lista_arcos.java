package TDAGrafo;

import TDALista.InvalidPositionException;
import TDALista.ListaDoblementeEnlazada;
import TDALista.PositionList;

public class Grafo_lista_arcos<V,E> implements Graph<V,E> {

	PositionList<VerticeParaListaArcos<V>> nodos;
	PositionList<ArcoParaListaArcos<V, E>> arcos;

	@Override
	public Iterable<Vertex<V>> vertices() {
		PositionList<Vertex<V>> lista_vertices = new ListaDoblementeEnlazada<>();

		for (VerticeParaListaArcos<V> v : nodos) {
			lista_vertices.addLast(v);
		}

		return lista_vertices;
	}

	@Override
	public Iterable<Edge<E>> edges() {
		PositionList<Edge<E>> lista_arcos = new ListaDoblementeEnlazada<>();

		for (ArcoParaListaArcos<V, E> a : arcos) {
			lista_arcos.addLast(a);
		}

		return lista_arcos;
	}

	@Override
	public Iterable<Edge<E>> incidentEdges(Vertex<V> v) throws InvalidVertexException {
		VerticeParaListaArcos<V> vertice = checkVertex(v);
		PositionList<Edge<E>> lista_arcos_incidentes = new ListaDoblementeEnlazada<>();

		for (ArcoParaListaArcos<V,E> arco : arcos) {
			if (arco.getV1() == vertice)
				lista_arcos_incidentes.addLast(arco);
			else if (arco.getV2() == vertice)
				lista_arcos_incidentes.addLast(arco);
		}
		return lista_arcos_incidentes;
	}

	@Override
	public Vertex<V> opposite(Vertex<V> v, Edge<E> e) throws InvalidVertexException, InvalidEdgeException {
		VerticeParaListaArcos<V> vertice = checkVertex(v);
		ArcoParaListaArcos<V, E> arco = checkEdge(e);
		Vertex<V> opuesto = null;

		if (arco.getV1() == vertice)
			opuesto = arco.getV2();
		else if (arco.getV2() == vertice)
			opuesto = arco.getV1();
		else
			throw new InvalidVertexException("El vértice parametrizado no es extremo del arco parametrizado");

		return opuesto;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Vertex<V>[] endvertices(Edge<E> e) throws InvalidEdgeException {
		ArcoParaListaArcos<V, E> arco = checkEdge(e);
		Vertex<V>[] arreglo_vertices_del_arco = (Vertex<V>[]) new VerticeParaListaArcos[2];

		arreglo_vertices_del_arco[0] = arco.getV1();
		arreglo_vertices_del_arco[1] = arco.getV2();

		return arreglo_vertices_del_arco;
	}

	@Override
	public boolean areAdjacent(Vertex<V> v, Vertex<V> w) throws InvalidVertexException {
		VerticeParaListaArcos<V> v1 = checkVertex(v);
		VerticeParaListaArcos<V> v2 = checkVertex(w);
		boolean sonAdyacentes = false;

		for (ArcoParaListaArcos<V, E> caminante : this.arcos) {
			if (caminante.getV1() == v1 && caminante.getV2() == v2) {
				sonAdyacentes = true;
				break;
			}
			else if (caminante.getV1() == v2 && caminante.getV2() == v1) {
				sonAdyacentes = true;
				break;
			}
		}

		return sonAdyacentes;
	}

	@Override
	public V replace(Vertex<V> v, V x) throws InvalidVertexException {
		if (x == null)
			throw new InvalidVertexException("El rótulo que se quiere emplazar en el vértice es nulo");

		V viejoRotulo = null;

		VerticeParaListaArcos<V> a_reemplazar = checkVertex(v);
		viejoRotulo = a_reemplazar.element();
		a_reemplazar.setRotulo(x);

		return viejoRotulo;
	}

	@Override
	public Vertex<V> insertVertex(V x) {
		VerticeParaListaArcos<V> nuevo = null;

		try {
			if (x != null) {
				nuevo = new VerticeParaListaArcos<V>(x);
				nodos.addLast(nuevo);
				nuevo.setPositionEnNodos(nodos.last());
			}
		}
		catch (TDALista.EmptyListException err) {
			err.printStackTrace();
		}

		return nuevo;
	}

	@Override
	public Edge<E> insertEdge(Vertex<V> v, Vertex<V> w, E e) throws InvalidVertexException {
		if (e == null)
			throw new InvalidVertexException("El rótulo del nuevo arco es nulo");
		VerticeParaListaArcos<V> vv = checkVertex(v);
		VerticeParaListaArcos<V> ww = checkVertex(w);
		ArcoParaListaArcos<V,E> nuevo = new ArcoParaListaArcos<V,E>(e);

		try {
			arcos.addLast(nuevo);
			nuevo.setPosicionEnListaArco(arcos.last());
			nuevo.setV1(vv);
			nuevo.setV2(ww);
		}
		catch (TDALista.EmptyListException err) {
			err.printStackTrace();
		}

		return nuevo;
	}

	@Override
	public V removeVertex(Vertex<V> v) throws InvalidVertexException {
		VerticeParaListaArcos<V> a_eliminar = checkVertex(v);
		V valorViejo = null;

		try {
			for (ArcoParaListaArcos<V,E> arco : arcos) {
				if (arco.getV1() == a_eliminar | arco.getV2() == a_eliminar) {
					arco.setRotulo(null);
					arco.setV1(null);
					arco.setV2(null);
					arcos.remove(arco.getPosicionEnListaArco());
				}
			}

			valorViejo = a_eliminar.element();
			a_eliminar.setRotulo(null);
			nodos.remove(a_eliminar.getPosicionEnNodos());
		}
		catch (InvalidPositionException err) {
			err.printStackTrace();
		}

		return valorViejo;
	}

	@Override
	public E removeEdge(Edge<E> e) throws InvalidEdgeException {
		ArcoParaListaArcos<V,E> a_remover = checkEdge(e);
		E viejoRotulo = null;

		try {
			viejoRotulo = a_remover.getRotulo();
			a_remover.setRotulo(null);
			a_remover.setV1(null);
			a_remover.setV2(null);
			arcos.remove(a_remover.getPosicionEnListaArco());
		}
		catch (InvalidPositionException err) {
			err.printStackTrace();
		}

		return viejoRotulo;
	}

	private VerticeParaListaArcos<V> checkVertex(Vertex<V> v) throws InvalidVertexException {
		try {
			if (v == null || v.element() == null)
				throw new InvalidVertexException("Vértice inválido");
	
			return (VerticeParaListaArcos<V>) v;
		}
		catch (ClassCastException err) {
			throw new InvalidVertexException("El vértice no es un vértice específico para el grafo con lista de arcos");
		}
	
	}

	@SuppressWarnings("unchecked")
	private ArcoParaListaArcos<V, E> checkEdge(Edge<E> e) throws InvalidEdgeException {
		try {
			if (e == null || e.element() == null)
				throw new InvalidEdgeException("Arco inválido");
	
			return (ArcoParaListaArcos<V, E>) e;
		}
		catch (ClassCastException err) {
			throw new InvalidEdgeException("El arco es de otra clase");
		}
	}

}
