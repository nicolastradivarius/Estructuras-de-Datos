package TDAGrafo;

import TDAMapeo.Mapeo_hash_abierto;

public class DArcoParaListaAdyacencias<V, E> extends Mapeo_hash_abierto<Object, Object> implements Edge<E> {

	protected E rotulo;
	protected DVerticeParaListaAdyacencias<V,E> v1, v2;
	protected TDALista.Position<DArcoParaListaAdyacencias<V,E>> posicionEnEmergentes, posicionEnIncidentes, posicionEnArcos;
	
	public DVerticeParaListaAdyacencias<V, E> getV1() {
		return v1;
	}

	public DVerticeParaListaAdyacencias<V, E> getV2() {
		return v2;
	}

	public TDALista.Position<DArcoParaListaAdyacencias<V, E>> getPosicionEnEmergentes() {
		return posicionEnEmergentes;
	}

	public TDALista.Position<DArcoParaListaAdyacencias<V, E>> getPosicionEnIncidentes() {
		return posicionEnIncidentes;
	}

	public TDALista.Position<DArcoParaListaAdyacencias<V, E>> getPosicionEnArcos() {
		return posicionEnArcos;
	}

	public void setRotulo(E rotulo) {
		this.rotulo = rotulo;
	}

	public void setV1(DVerticeParaListaAdyacencias<V, E> v1) {
		this.v1 = v1;
	}

	public void setV2(DVerticeParaListaAdyacencias<V, E> v2) {
		this.v2 = v2;
	}

	public void setPosicionEnEmergentes(TDALista.Position<DArcoParaListaAdyacencias<V, E>> posicionEnEmergentes) {
		this.posicionEnEmergentes = posicionEnEmergentes;
	}

	public void setPosicionEnIncidentes(TDALista.Position<DArcoParaListaAdyacencias<V, E>> posicionEnIncidentes) {
		this.posicionEnIncidentes = posicionEnIncidentes;
	}

	public void setPosicionEnArcos(TDALista.Position<DArcoParaListaAdyacencias<V, E>> posicionEnArcos) {
		this.posicionEnArcos = posicionEnArcos;
	}

	@Override
	public E element() {
		return rotulo;
	}

}
