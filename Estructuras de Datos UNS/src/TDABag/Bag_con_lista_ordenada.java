package TDABag;

import TDALista.PositionList;
import TDALista.ListaDoblementeEnlazada;
import TDALista.Position;

public class Bag_con_lista_ordenada<E> implements Bag<E> {

	protected PositionList<E> lista_ordenada;
	protected int tamanyo;
	
	@Override
	public void agregar(E item) {

	}

	@Override
	public boolean esVacio() {
		return false;
	}

	@Override
	public int cantElementos() {
		return 0;
	}

	@Override
	public void eliminar(E item) {

	}

	@Override
	public int cantidad(E item) {
		return 0;
	}

}
