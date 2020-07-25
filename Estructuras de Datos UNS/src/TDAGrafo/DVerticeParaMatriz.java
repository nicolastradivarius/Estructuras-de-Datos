package TDAGrafo;

import TDAMapeo.Mapeo_hash_abierto;

public class DVerticeParaMatriz<V, E> extends Mapeo_hash_abierto<Object, Object> implements Vertex<V> {

	private TDALista.Position<Vertex<V>> posicionEnVertices;
	private V rotulo;
	private int indice;
	
	public DVerticeParaMatriz(V rotulo, int indice) {
		this.rotulo = rotulo;
		this.indice = indice;
	}
	
	public DVerticeParaMatriz(V rotulo) {
		this.rotulo = rotulo;
	}

	@Override
	public V element() {
		return rotulo;
	}

	public TDALista.Position<Vertex<V>> getPosicionEnVertices() {
		return posicionEnVertices;
	}

	public int getIndice() {
		return indice;
	}

	public void setPosicionEnVertices(TDALista.Position<Vertex<V>> posicionEnVertices) {
		this.posicionEnVertices = posicionEnVertices;
	}

	public void setRotulo(V rotulo) {
		this.rotulo = rotulo;
	}

	public void setIndice(int indice) {
		this.indice = indice;
	}

}
