package TDAGrafo;

public class VerticeParaListaArcos<V> implements Vertex<V> {

	protected TDALista.Position<VerticeParaListaArcos<V>> posicionEnListaVertices;
	protected V rotulo;
	
	@Override
	public V element() {
		return rotulo;
	}
	
	public VerticeParaListaArcos(V rotulo) {
		this.rotulo = rotulo;
	}
	
	public void setRotulo(V nuevoRotulo) {
		rotulo = nuevoRotulo;
	}
	
	public void setPositionEnNodos(TDALista.Position<VerticeParaListaArcos<V>> p) {
		this.posicionEnListaVertices = p;
	}
	
	public TDALista.Position<VerticeParaListaArcos<V>> getPosicionEnNodos(){
		return this.posicionEnListaVertices;
	}

}
