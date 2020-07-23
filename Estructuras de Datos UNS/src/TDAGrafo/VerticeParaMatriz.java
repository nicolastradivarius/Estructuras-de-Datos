package TDAGrafo;

public class VerticeParaMatriz<V> implements Vertex<V> {

	private TDALista.Position<Vertex<V>> posicionEnVertices;
	private V rotulo;
	private int indice;
	
	public VerticeParaMatriz(V rotulo, int indice) {
		this.rotulo = rotulo;
		this.indice = indice;
	}
	
	@Override
	public V element() {
		// TODO Auto-generated method stub
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
