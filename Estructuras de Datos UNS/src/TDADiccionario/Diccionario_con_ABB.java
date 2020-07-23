package TDADiccionario;

import ABB.*;
import TDALista.ListaDoblementeEnlazada;
import TDALista.PositionList;

public class Diccionario_con_ABB<K extends Comparable<K>, V> implements Dictionary<K,V> {

	protected ABB<EntradaComparable<K, PositionList<Entry<K,V>>>> entradasABB;
	//En el abb, cada nodo tiene una EntradaComparable con una clave y una lista de Entradas con esa clave y valores asignados
	protected int tamanyo;

	public Diccionario_con_ABB() {
		entradasABB = new ABB<EntradaComparable<K, PositionList<Entry<K,V>>>>();
		tamanyo = 0;
	}

	@Override
	public int size() {
		return tamanyo;
	}
	@Override
	public boolean isEmpty() {
		return tamanyo == 0;
	}
	@Override
	public Entry<K, V> find(K key) throws InvalidKeyException {
		EntradaComparable<K, PositionList<Entry<K, V>>> entrada_buscada;
		Entry<K, PositionList<Entry<K,V>>> entrada_asociada = null;
		NodoABB<EntradaComparable<K, PositionList<Entry<K, V>>>> nodo_buscado;
		PositionList<Entry<K,V>> entrada_lista_valores;
		Entry<K, V> a_retornar = null;

		try {
			check_key(key);
			//creo una entrada con la clave parametrizada y una lista nula con el fin de buscarla en el arbol
			entrada_buscada = new EntradaComparable<K, PositionList<Entry<K, V>>>(key, null);
			//recupero el nodo donde se almacena esa entrada buscada
			nodo_buscado = entradasABB.buscar(entrada_buscada);

			//si en el nodo hay algo válido (No es dummy)
			if (nodo_buscado.getRotulo() != null) {
				//obtengo la entrada asociada al nodo, con la clave buscada y la lista de entradas con esa clave y valores asociados
				entrada_asociada = nodo_buscado.getRotulo();
				//recupero la lista de entradas con dicha clave y valores asociados
				entrada_lista_valores = entrada_asociada.getValue();
				//guardo en la variable de retorno la primera entrada de la lista de entradas
				a_retornar = (entrada_lista_valores.isEmpty()) ? null : entrada_lista_valores.first().element();

			}
		}
		catch (TDALista.EmptyListException err) {
			err.printStackTrace();
		}

		return a_retornar;
	}

	@Override
	public Iterable<Entry<K, V>> findAll(K key) throws InvalidKeyException {

		check_key(key);

		PositionList<Entry<K,V>> lista_claves_comunes = new ListaDoblementeEnlazada<Entry<K,V>>();
		EntradaComparable<K, PositionList<Entry<K, V>>> entrada_buscada;
		NodoABB<EntradaComparable<K, PositionList<Entry<K, V>>>> nodo_buscado;

		//da igual con que value se mande, las entradas se comparan por key, por ende se manda un nulo y listo
		entrada_buscada = new EntradaComparable<K, PositionList<Entry<K,V>>>(key, null);
		nodo_buscado = entradasABB.buscar(entrada_buscada);

		if (nodo_buscado.getRotulo() != null) {
			lista_claves_comunes = nodo_buscado.getRotulo().getValue();
		}

		return lista_claves_comunes;
	}

	@Override
	public Entry<K, V> insert(K key, V value) throws InvalidKeyException {
		EntradaComparable<K, PositionList<Entry<K, V>>> entrada_buscada;
		NodoABB<EntradaComparable<K, PositionList<Entry<K, V>>>> nodo_buscado;
		EntradaComparable<K, V>  a_retornar = null;

		check_key(key);
		entrada_buscada = new EntradaComparable<K, PositionList<Entry<K,V>>>(key, null);
		nodo_buscado = entradasABB.buscar(entrada_buscada);
		a_retornar = new EntradaComparable<K,V>(key, value);

		//Si el nodo no es dummy, entonces existe en el ABB una entrada cuya clave es K, entonces va a existir una positionList de entradas para ese nodo que contiene aquellas entradas con claves y valores asociadas a K 
		if (nodo_buscado.getRotulo() != null) {
			nodo_buscado.getRotulo().getValue().addLast(a_retornar);
		}
		else {
			entradasABB.expandir(nodo_buscado, entrada_buscada);
			entrada_buscada.setValue(new ListaDoblementeEnlazada<Entry<K,V>>());
			entrada_buscada.getValue().addLast(a_retornar);
		}

		tamanyo++;
		return a_retornar;
	}

	@Override
	public Entry<K, V> remove(Entry<K, V> e) throws InvalidEntryException {

		check_entry(e);

		boolean elimine = false;
		EntradaComparable<K, PositionList<Entry<K, V>>> entrada_buscada;
		NodoABB<EntradaComparable<K, PositionList<Entry<K, V>>>> nodo_buscado;
		PositionList<Entry<K, V>> lista_entradas_comunes = null;
		TDALista.Position<Entry<K,V>> a_eliminar = null;
		Entry<K,V> entry_borrada = null;

		try {
			//da igual con que value se mande, las entradas se comparan por key, por ende se manda un nulo y listo
			entrada_buscada = new EntradaComparable<K, PositionList<Entry<K,V>>>(e.getKey(), null);
			nodo_buscado = entradasABB.buscar(entrada_buscada);

			if (nodo_buscado.getRotulo() != null) {
				lista_entradas_comunes = nodo_buscado.getRotulo().getValue();
				a_eliminar = lista_entradas_comunes.isEmpty() ? null : lista_entradas_comunes.first();

				while (!elimine && a_eliminar != null) {
					if (a_eliminar.element() == e) {
						entry_borrada = a_eliminar.element();
						lista_entradas_comunes.remove(a_eliminar);
						tamanyo--;
						elimine = true;
					}
					else 
						a_eliminar = (a_eliminar == lista_entradas_comunes.last()) ? null : lista_entradas_comunes.next(a_eliminar);
				}
			}
			else 
				throw new InvalidEntryException("La entrada no está en el diccionario");

		}
		catch (TDALista.EmptyListException | TDALista.InvalidPositionException | TDALista.BoundaryViolationException err) {
			err.printStackTrace();
		}
		
		if (!elimine)
			throw new InvalidEntryException("La entrada no está en el diccionario");

		return entry_borrada;

	}

	@Override
	public Iterable<Entry<K, V>> entries() {
		PositionList<Entry<K,V>> lista_entries = new ListaDoblementeEnlazada<Entry<K,V>>();
		if (entradasABB.raiz().getRotulo() != null) {
			recorrido_inorder(entradasABB.raiz(), lista_entries);
		}

		return lista_entries;
	}

	private void recorrido_inorder(NodoABB<EntradaComparable<K, PositionList<Entry<K, V>>>> p,
			PositionList<Entry<K, V>> lista_entries) {

		TDALista.Position<Entry<K,V>> a_insertar = null;

		try {
			if (p.getLeft().getRotulo() != null) {
				recorrido_inorder(p.getLeft(), lista_entries);
			}

			a_insertar = (p.getRotulo().getValue().isEmpty()) ? null : p.getRotulo().getValue().first();
			while (a_insertar != null) {
				lista_entries.addLast(a_insertar.element());
				a_insertar = (a_insertar == p.getRotulo().getValue().last()) ? null : p.getRotulo().getValue().next(a_insertar);
			}

			if (p.getRight().getRotulo() != null) {
				recorrido_inorder(p.getRight(), lista_entries);
			}
		}
		catch (Exception er) {
			er.printStackTrace();
		}
	}

	private void check_entry(Entry<K, V> e) throws InvalidEntryException {
		if (e == null)
			throw new InvalidEntryException("La entrada parametrizada es inválida");
		if (e.getKey() == null)
				throw new InvalidEntryException("La entrada parametrizada es inválida");
	}

	private void check_key(K key) throws InvalidKeyException{
		if (key == null)
			throw new InvalidKeyException();
	}
}
