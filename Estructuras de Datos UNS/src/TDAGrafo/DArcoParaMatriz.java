package TDAGrafo;

import TDAMapeo.Mapeo_hash_abierto;

public class DArcoParaMatriz<V,E> extends Mapeo_hash_abierto<Object, Object> implements Edge<E> {

	private TDALista.Position<Edge<E>> posicionEnArcos;
	private Vertex<V> v1, v2;
	private E rotulo;
	
	public DArcoParaMatriz(E rotulo) {
		this.rotulo = rotulo;
	}
	
	public DArcoParaMatriz(E rotulo, Vertex<V> v1, Vertex<V> v2) {
		this.rotulo = rotulo;
		this.v1 = v1;
		this.v2 = v2;
	}
	
	@Override
	public E element() {
		return rotulo;
	}

	public TDALista.Position<Edge<E>> getPosicionEnArcos() {
		return posicionEnArcos;
	}

	public Vertex<V> getV1() {
		return v1;
	}

	public Vertex<V> getV2() {
		return v2;
	}

	public void setPosicionEnArcos(TDALista.Position<Edge<E>> posicionEnArcos) {
		this.posicionEnArcos = posicionEnArcos;
	}

	public void setV1(Vertex<V> v1) {
		this.v1 = v1;
	}

	public void setV2(Vertex<V> v2) {
		this.v2 = v2;
	}

	public void setRotulo(E rotulo) {
		this.rotulo = rotulo;
	}

}
