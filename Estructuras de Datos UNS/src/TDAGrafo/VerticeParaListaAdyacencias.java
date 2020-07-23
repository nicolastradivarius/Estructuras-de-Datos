package TDAGrafo;

import TDALista.ListaDoblementeEnlazada;
import TDALista.PositionList;

public class VerticeParaListaAdyacencias<V, E> implements Vertex<V> {
	
	protected TDALista.Position<VerticeParaListaAdyacencias<V, E>> posicionEnListaVertices;
	protected V rotulo;
	protected PositionList<ArcoParaListaAdyacencias<V,E>> adyacentes;

	@Override
	public V element() {
		return rotulo;
	}
	
	public VerticeParaListaAdyacencias(V rotulo) {
		this.rotulo = rotulo;
		adyacentes = new ListaDoblementeEnlazada<ArcoParaListaAdyacencias<V,E>>();
	}
	
	public void setRotulo(V nuevoRotulo) {
		rotulo = nuevoRotulo;
	}
	
	public PositionList<ArcoParaListaAdyacencias<V, E>> getAdyacentes() {
		return adyacentes;
	}
	
	public void setPositionEnNodos(TDALista.Position<VerticeParaListaAdyacencias<V, E>> p) {
		this.posicionEnListaVertices = p;
	}
	
	public TDALista.Position<VerticeParaListaAdyacencias<V,E>> getPosicionEnNodos(){
		return this.posicionEnListaVertices;
	}

}
