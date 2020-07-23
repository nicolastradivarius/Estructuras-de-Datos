package TDAGrafo;

public class ArcoParaListaAdyacencias<V, E> implements Edge<E> {
	
	private E rotulo;
	private VerticeParaListaAdyacencias<V, E> v1, v2;
	private TDALista.Position<ArcoParaListaAdyacencias<V, E>> posicionEnArcos;
	private TDALista.Position<ArcoParaListaAdyacencias<V, E>> posicionEnlv1, posicionEnlv2;
	
	public ArcoParaListaAdyacencias(E rotulo, VerticeParaListaAdyacencias<V, E> v1, VerticeParaListaAdyacencias<V, E> v2) {
		this.rotulo = rotulo;
		this.setV1(v1);
		this.setV2(v2);
	}

	@Override
	public E element() {
		return rotulo;
	}
	
	public void setRotulo(E rotulo) {
		this.rotulo = rotulo;
	}

	public TDALista.Position<ArcoParaListaAdyacencias<V, E>> getPosicionEnlv1() {
		return posicionEnlv1;
	}

	public void setPosicionEnlv1(TDALista.Position<ArcoParaListaAdyacencias<V, E>> position) {
		this.posicionEnlv1 = position;
	}

	public TDALista.Position<ArcoParaListaAdyacencias<V, E>> getPosicionEnArcos() {
		return posicionEnArcos;
	}

	public void setPosicionEnArcos(TDALista.Position<ArcoParaListaAdyacencias<V, E>> posicionEnArcos) {
		this.posicionEnArcos = posicionEnArcos;
	}

	public TDALista.Position<ArcoParaListaAdyacencias<V, E>> getPosicionEnlv2() {
		return posicionEnlv2;
	}

	public void setPosicionEnlv2(TDALista.Position<ArcoParaListaAdyacencias<V, E>> posicionEnlv2) {
		this.posicionEnlv2 = posicionEnlv2;
	}

	public VerticeParaListaAdyacencias<V, E> getV1() {
		return v1;
	}

	public void setV1(VerticeParaListaAdyacencias<V, E> v1) {
		this.v1 = v1;
	}

	public VerticeParaListaAdyacencias<V, E> getV2() {
		return v2;
	}

	public void setV2(VerticeParaListaAdyacencias<V, E> v2) {
		this.v2 = v2;
	}
	
}
