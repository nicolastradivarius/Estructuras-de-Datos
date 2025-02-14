package TDAGrafo;

import TDAMapeo.Mapeo_hash_abierto;

public class ArcoParaMatriz<V, E> extends Mapeo_hash_abierto<Object, Object> implements Edge<E> {

	private TDALista.Position<Edge<E>> posicionEnArcos;
	private VerticeParaMatriz<V> v1, v2;
	private E rotulo;
	
	public ArcoParaMatriz(E rotulo) {
		this.rotulo = rotulo;
	}
	
	public ArcoParaMatriz(E rotulo, VerticeParaMatriz<V> v1, VerticeParaMatriz<V> v2) {
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

	public VerticeParaMatriz<V> getV1() {
		return v1;
	}

	public VerticeParaMatriz<V> getV2() {
		return v2;
	}

	public void setPosicionEnArcos(TDALista.Position<Edge<E>> posicionEnArcos) {
		this.posicionEnArcos = posicionEnArcos;
	}

	public void setV1(VerticeParaMatriz<V> v1) {
		this.v1 = v1;
	}

	public void setV2(VerticeParaMatriz<V> v2) {
		this.v2 = v2;
	}

	public void setRotulo(E rotulo) {
		this.rotulo = rotulo;
	}

}
