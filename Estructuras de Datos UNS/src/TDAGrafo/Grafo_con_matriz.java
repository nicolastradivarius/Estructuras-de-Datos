package TDAGrafo;

import TDALista.PositionList;

public class Grafo_con_matriz<V, E> implements Graph<V, E> {
	
	protected PositionList<Vertex<V>> vertices;
	protected PositionList<Edge<E>> arcos;
	protected Edge<E> [][] matriz;
	protected int cantidadVertices;

	@Override
	public Iterable<Vertex<V>> vertices() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<Edge<E>> edges() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<Edge<E>> incidentEdges(Vertex<V> v) throws InvalidVertexException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vertex<V> opposite(Vertex<V> v, Edge<E> e) throws InvalidVertexException, InvalidEdgeException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vertex<V>[] endvertices(Edge<E> e) throws InvalidEdgeException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean areAdjacent(Vertex<V> v, Vertex<V> w) throws InvalidVertexException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public V replace(Vertex<V> v, V x) throws InvalidVertexException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vertex<V> insertVertex(V x) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Edge<E> insertEdge(Vertex<V> v, Vertex<V> w, E e) throws InvalidVertexException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public V removeVertex(Vertex<V> v) throws InvalidVertexException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public E removeEdge(Edge<E> e) throws InvalidEdgeException {
		// TODO Auto-generated method stub
		return null;
	}

}
