package TDAGrafo;

public class DVerticeParaListaAdyacencias<V, E> implements Vertex<V> {

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
