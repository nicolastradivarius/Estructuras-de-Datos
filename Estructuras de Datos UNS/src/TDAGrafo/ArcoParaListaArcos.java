package TDAGrafo;

public class ArcoParaListaArcos<V, E> implements Edge<E> {
	
	protected E rotulo;
	protected TDALista.Position<ArcoParaListaArcos<V,E>> posicionEnListaArco;
	protected VerticeParaListaArcos<V> v1, v2;

	public ArcoParaListaArcos(E elemento) {
		rotulo = elemento;
		posicionEnListaArco = null;
		v1 = v2 = null;
	}
	
	@Override
	public E element() {
		return rotulo;
	}
	
	public VerticeParaListaArcos<V> getV1() {
		return v1;
	}
	
	public VerticeParaListaArcos<V> getV2() {
		return v2;
	}

	public E getRotulo() {
		return rotulo;
	}

	public void setRotulo(E rotulo) {
		this.rotulo = rotulo;
	}

	public TDALista.Position<ArcoParaListaArcos<V, E>> getPosicionEnListaArco() {
		return posicionEnListaArco;
	}

	public void setPosicionEnListaArco(TDALista.Position<ArcoParaListaArcos<V, E>> posicionEnListaArco) {
		this.posicionEnListaArco = posicionEnListaArco;
	}

	public void setV1(VerticeParaListaArcos<V> v1) {
		this.v1 = v1;
	}
	
	public void setV2(VerticeParaListaArcos<V> v2) {
		this.v2 = v2;
	}

}
