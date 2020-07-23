package TDAGrafo;

import TDALista.EmptyListException;
import TDALista.InvalidPositionException;
import TDALista.ListaDoblementeEnlazada;
import TDALista.PositionList;

/**
 * Implementación de la interfaz Graph (grafo NO dirigido) a través de una lista de adyacencias.
 * @author Nicolas
 *
 * @param <V>
 * @param <E>
 */

//el grafo conoce:
/*
 * 1. una lista de vértices
 * 2. una lista de ArcoParaListaAdyacencias
 * 3. cada VerticeParaListaAdyacencias conoce su rótulo y los ArcoParaListaAdyacencias que inciden a él.
 * 4. los ArcoParaListaAdyacencias conocen su peso y los VerticeParaListaAdyacenciass que unen. 
 */
public class Grafo_lista_adyacencias<V, E> implements Graph<V, E> {

	protected PositionList<VerticeParaListaAdyacencias<V,E>> nodos;
	protected PositionList<ArcoParaListaAdyacencias<V, E>> arcos;

	public Grafo_lista_adyacencias() {
		nodos = new ListaDoblementeEnlazada<VerticeParaListaAdyacencias<V,E>>();
		arcos = new ListaDoblementeEnlazada<ArcoParaListaAdyacencias<V,E>>();
	}

	@Override
	public Iterable<Vertex<V>> vertices() {
		PositionList<Vertex<V>> lista = new ListaDoblementeEnlazada<>();
		for (Vertex<V> v : nodos) {
			lista.addLast(v);
		}

		return lista;
	}

	@Override
	public Iterable<Edge<E>> edges() {
		PositionList<Edge<E>> lista = new ListaDoblementeEnlazada<>();
		for (Edge<E> e : arcos) {
			lista.addLast(e);
		}

		return lista;
	}

	@Override
	public Iterable<Edge<E>> incidentEdges(Vertex<V> v) throws InvalidVertexException {
		PositionList<Edge<E>> lista = new ListaDoblementeEnlazada<>();		
		VerticeParaListaAdyacencias<V,E> vert = checkVertex(v);

		for (Edge<E> e : vert.getAdyacentes()) {
			lista.addLast(e);
		}

		return lista;
	}

	@Override
	public Vertex<V> opposite(Vertex<V> v, Edge<E> e) throws InvalidVertexException, InvalidEdgeException {
		VerticeParaListaAdyacencias<V,E> vertice = checkVertex(v);
		ArcoParaListaAdyacencias<V,E> arco = checkEdge(e);
		Vertex<V> toReturn = null;
		
		if (arco.getV1() == vertice) {
			toReturn = arco.getV2();
		}
		else if (arco.getV2() == vertice) {
			toReturn = arco.getV1();
		}
		else 
			throw new InvalidVertexException("El vértice parametrizado no es extremo del arco parametrizado");
		
		return toReturn;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Vertex<V>[] endvertices(Edge<E> e) throws InvalidEdgeException {
		ArcoParaListaAdyacencias<V,E> arco = checkEdge(e);
		Vertex<V>[] arreglo_extremos = (Vertex<V>[]) new VerticeParaListaAdyacencias[2];
		
		arreglo_extremos[0] = arco.getV1();
		arreglo_extremos[1] = arco.getV2();
		
		return arreglo_extremos;
	}

	@Override
	public boolean areAdjacent(Vertex<V> v, Vertex<V> w) throws InvalidVertexException {
		VerticeParaListaAdyacencias<V,E> v1 = checkVertex(v);
		VerticeParaListaAdyacencias<V,E> v2 = checkVertex(w);
		boolean sonAdyacentes = false;
		
		for (ArcoParaListaAdyacencias<V,E> caminante : this.arcos) {
			if (caminante.getV1() == v1 && caminante.getV2() == v2) {
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
		
		VerticeParaListaAdyacencias<V,E> vertice = checkVertex(v);
		V valorViejo = vertice.element();
		
		vertice.setRotulo(x);
		
		return valorViejo;
	}

	@Override
	public Vertex<V> insertVertex(V x) {
		VerticeParaListaAdyacencias<V, E> v = new VerticeParaListaAdyacencias<>(x);
		try {
			nodos.addLast(v);
			v.setPositionEnNodos(nodos.last());
		}
		catch (TDALista.EmptyListException err) {
			err.printStackTrace();
		}

		return v;
	}

	@Override
	public Edge<E> insertEdge(Vertex<V> v, Vertex<V> w, E e) throws InvalidVertexException {
		VerticeParaListaAdyacencias<V,E> vv = checkVertex(v);
		VerticeParaListaAdyacencias<V,E> ww = checkVertex(w);
		ArcoParaListaAdyacencias<V,E> arco = new ArcoParaListaAdyacencias<>(e, vv, ww); //Creo el nuevo ArcoParaListaAdyacencias

		try {
			//agrego el ArcoParaListaAdyacencias al final de la lista de adyacentes de v y anoto su posicion
			vv.getAdyacentes().addLast(arco);
			arco.setPosicionEnlv1(vv.getAdyacentes().last());

			//agrego el ArcoParaListaAdyacencias al final de la lista de adyacentes de w y anoto su posicion
			ww.getAdyacentes().addLast(arco);
			arco.setPosicionEnlv2(ww.getAdyacentes().last());

			//agrego el ArcoParaListaAdyacencias al final de lista de arcos y le seteo su posicion
			arcos.addLast(arco);
			arco.setPosicionEnArcos(arcos.last());

		}
		catch (EmptyListException err) {
			err.printStackTrace();
		}

		return arco;
	}

	@Override
	public V removeVertex(Vertex<V> v) throws InvalidVertexException {
		VerticeParaListaAdyacencias<V,E> a_remover = checkVertex(v);
		V valorViejo = null;

		try {
			for (ArcoParaListaAdyacencias<V,E> caminante : a_remover.getAdyacentes()) {
				removeEdge(caminante);
			}
			
			valorViejo = a_remover.element();
			a_remover.setRotulo(null);
			nodos.remove(a_remover.getPosicionEnNodos());
			
		}
		catch (InvalidEdgeException | InvalidPositionException err) {
			err.printStackTrace();
		}
		
		return valorViejo;
	}

	@Override
	public E removeEdge(Edge<E> e) throws InvalidEdgeException {

		E valorViejo = null;
		ArcoParaListaAdyacencias<V,E> a_remover = checkEdge(e);
		VerticeParaListaAdyacencias<V,E> extremo1, extremo2;

		try {
			extremo1 = a_remover.getV1();
			extremo2 = a_remover.getV2();

			extremo1.getAdyacentes().remove(a_remover.getPosicionEnlv1());
			extremo2.getAdyacentes().remove(a_remover.getPosicionEnlv2());

			valorViejo = a_remover.element();
			a_remover.setV1(null);
			a_remover.setV2(null);
			a_remover.setPosicionEnlv1(null);
			a_remover.setPosicionEnlv2(null);
			this.arcos.remove(a_remover.getPosicionEnArcos());
		}
		catch (InvalidPositionException err) {
			err.printStackTrace();
		}

		return valorViejo;

	}

	@SuppressWarnings("unchecked")
	private VerticeParaListaAdyacencias<V, E> checkVertex(Vertex<V> v) throws InvalidVertexException {
		try {
			if (v == null || v.element() == null)
				throw new InvalidVertexException("Vértice invalido");

			return (VerticeParaListaAdyacencias<V, E>) v;
		}
		catch (ClassCastException err) {
			throw new InvalidVertexException("El vértice es de otra clase");
		}
	}

	@SuppressWarnings("unchecked")
	private ArcoParaListaAdyacencias<V, E> checkEdge(Edge<E> e) throws InvalidEdgeException {
		try {
			if (e == null || e.element() == null)
				throw new InvalidEdgeException("Arco inválido");

			return (ArcoParaListaAdyacencias<V, E>) e;
		}
		catch (ClassCastException err) {
			throw new InvalidEdgeException("El arco no es de tipo ArcoParaListaAdyacencias");
		}
	}
	
	public String toString() {
		String salida = "";
		for (VerticeParaListaAdyacencias<V,E> v : nodos) {
			salida += v.rotulo + "----";
		}
		
		return salida;
	}

	public static void main(String[] args) {

		Graph<Character, Character> g1 = new Grafo_lista_adyacencias<>();
		
		Vertex<Character> h1 = g1.insertVertex('u');
		Vertex<Character> h2 = g1.insertVertex('v');
		Vertex<Character> h3 = g1.insertVertex('w');
		
		try {
			g1.removeVertex(h2);
		} catch (InvalidVertexException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(g1.toString());
	}

}
