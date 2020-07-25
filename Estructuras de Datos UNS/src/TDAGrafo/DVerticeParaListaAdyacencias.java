package TDAGrafo;

import TDAMapeo.Mapeo_hash_abierto;

public class DVerticeParaListaAdyacencias<V, E> extends Mapeo_hash_abierto<Object, Object> implements Vertex<V> {

	protected V rotulo;
	protected TDALista.Position<DVerticeParaListaAdyacencias<V,E>> posicionEnListaVertices;
	protected TDALista.PositionList<DArcoParaListaAdyacencias<V,E>> emergentes, incidentes;
	
	@Override
	public V element() {
		return rotulo;
	}
	
	public void setRotulo(V rotulo) {
		this.rotulo = rotulo;
	}

	public TDALista.Position<DVerticeParaListaAdyacencias<V, E>> getPosicionEnListaVertices() {
		return posicionEnListaVertices;
	}

	public TDALista.PositionList<DArcoParaListaAdyacencias<V, E>> getEmergentes() {
		return emergentes;
	}

	public TDALista.PositionList<DArcoParaListaAdyacencias<V, E>> getIncidentes() {
		return incidentes;
	}

	public void setPosicionEnListaVertices(TDALista.Position<DVerticeParaListaAdyacencias<V, E>> posicionEnListaVertices) {
		this.posicionEnListaVertices = posicionEnListaVertices;
	}

}
