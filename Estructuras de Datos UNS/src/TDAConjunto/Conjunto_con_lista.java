package TDAConjunto;

import java.util.Iterator;
import TDALista.PositionList;
import TDALista.ListaDoblementeEnlazada;
import TDALista.Position;

/**
 * @author Nicolas
 *
 */
public class Conjunto_con_lista<E> implements Conjunto<E> {

	protected PositionList<E> lista;

	public Conjunto_con_lista() {
		lista = new ListaDoblementeEnlazada<E>();
	}

	@Override
	public void agregar(E item) {
		boolean esta = false;
		for (E p : lista) {
			if (p.equals(item)) {
				esta = true;
				break;
			}
		}

		if (!esta)
			lista.addFirst(item);
	}

	@Override
	public void eliminar(E item) {
		Iterable<Position<E>> posiciones_lista = lista.positions();
		try {
			for (TDALista.Position<E> p : posiciones_lista) {
				if (p.element().equals(item)) {
					lista.remove(p);
					break;
				}
			}
		}
		catch (TDALista.InvalidPositionException err) {
			err.printStackTrace();
		}
	}

	@Override
	public boolean pertenece(E item) {
		boolean esta = false;
		for (E p : lista) {
			if (p.equals(item)) {
				esta = true;
				break;
			}
		}
		
		return esta;
	}

	@Override
	public void union(Conjunto<E> c) {
		for (E elem : lista) {
			c.agregar(elem);
		}
	}

	@Override
	public void interseccion(Conjunto<E> c) {
		for (E elem : lista) {
			if (!c.pertenece(elem))
				c.eliminar(elem);
		}
	}

	@Override
	public Iterator<E> iterator() {
		return lista.iterator();
	}

}
