package TDALista;

import java.util.Iterator;

public class Lista_DE<E> implements PositionList<E> {
	
	DNodo<E> primero, ultimo;
	int tamanyo;
	
	public Lista_DE() {
		tamanyo = 0;
		primero = new DNodo<E>(null, null, null);
		ultimo = new DNodo<E>(primero, null, null);
		
		primero.setSiguiente(ultimo);
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Position<E> first() throws EmptyListException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Position<E> last() throws EmptyListException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Position<E> next(Position<E> p) throws InvalidPositionException, BoundaryViolationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Position<E> prev(Position<E> p) throws InvalidPositionException, BoundaryViolationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addFirst(E element) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addLast(E element) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addAfter(Position<E> p, E element) throws InvalidPositionException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addBefore(Position<E> p, E element) throws InvalidPositionException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public E remove(Position<E> p) throws InvalidPositionException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public E set(Position<E> p, E element) throws InvalidPositionException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterator<E> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<Position<E>> positions() {
		// TODO Auto-generated method stub
		return null;
	}

}
