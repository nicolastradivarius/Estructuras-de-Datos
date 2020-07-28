package TDAMapeo;

import TDALista.ListaDoblementeEnlazada;
import TDALista.PositionList;

public class MapeoConTrie<E> implements Map<String, E> {

	private class NodoTrie<T> {

		private NodoTrie<T> padre;
		private NodoTrie<T> [] hijos;
		private T imagen;

		@SuppressWarnings("unchecked")
		public NodoTrie(NodoTrie<T> padre) {
			hijos = new NodoTrie[26];
			imagen = null;
			this.padre = padre;
		}

		public NodoTrie<T> getPadre() {
			return padre;
		}

		public NodoTrie<T> getHijo(int i) {
			return hijos[i];
		}

		public T getImagen() {
			return imagen;
		}

		public void setPadre(NodoTrie<T> padre) {
			this.padre = padre;
		}

		public void setHijo(int i, NodoTrie<T> hijo) {
			this.hijos[i] = hijo;
		}

		public void setImagen(T imagen) {
			this.imagen = imagen;
		}
	}

	protected NodoTrie<E> raiz;

	public MapeoConTrie() {
		raiz = new NodoTrie<E>(null);
	}

	@Override
	public int size() {
		return 0;
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public E get(String key) throws InvalidKeyException {
		//recorro la clave desde el indice 0 y el arbol desde la raiz
		E buscado = null;
		return get_aux(key, 0, key.length(), raiz, buscado);
	}

	private E get_aux(String clave, int i, int n, NodoTrie<E> p, E buscado) {
		if (i == n) {
			buscado = p.getImagen();
		}
		else {
			//no estoy al final de la clave, sigo buscando
			int indice = (int) clave.charAt(i) - (int) 'a';

			if (p.getHijo(indice) != null)
				buscado = get_aux(clave, i+1, n, p.getHijo(indice), buscado);
		}

		return buscado;
	}

	@Override
	public E put(String key, E value) throws InvalidKeyException {
		//le mando a un metodo auxiliar: el string, el valor, la posicion del 
		//caracter que estoy leyendo, la longitud del string, y la posicion
		//a partir de la cual comienzo el recorrido
		E valorAnterior = null;
		return put_aux(key, value, 0, key.length(), raiz, valorAnterior);
	}

	private E put_aux(String clave, E valor, int i, int n, MapeoConTrie<E>.NodoTrie<E> p, E valorAnterior) {
		if (i < n) { //todavía queda clave por recorrer (string)
			int indice = ((int) clave.charAt(i)) - ((int) 'a');
			if (p.getHijo(indice) == null) {
				//si no hay hijo, lo creo antes de continuar
				p.setHijo(indice, new NodoTrie<E>(p));
			}
			valorAnterior = put_aux(clave, valor, i+1, n, p.getHijo(indice), valorAnterior);
		}
		else { //i == n, se termino la clave, acá va el valor
			E imagenVieja = p.getImagen();
			p.setImagen(valor);
			valorAnterior = imagenVieja;
		}

		return valorAnterior;
	}

	@Override
	public E remove(String key) throws InvalidKeyException {
		E a_retormar = null;
		//busco la clave a partir del caracter 0 y el arbol a partir del nodo raiz
		return remove_aux(key, 0, key.length(), raiz, 0, a_retormar);
	}

	private E remove_aux(String clave, int i, int n, MapeoConTrie<E>.NodoTrie<E> p, int indiceP, E valorRemovido) throws InvalidKeyException {
		//indiceP es la posicion del nodo P en el arreglo de hijos de su padre (que en el caso de la raíz es 0)
		if (i == n) {
			if (p.getImagen() == null)
				throw new InvalidKeyException();

			valorRemovido = p.getImagen();
			p.setImagen(null);
		}
		else {
			//no estoy en el final de la clave
			int indice = (int) clave.charAt(i) - (int) 'a';

			if (p.getHijo(indice) == null)
				throw new InvalidKeyException();

			//borro en el hijo indice
			valorRemovido = remove_aux(clave, i+1, n, p.getHijo(indice), indice, valorRemovido);

		}

		if (todoNulo(p)) { //si el arreglo de hijos de p son todos nulos y el value también, procedo a borrar definitivamente el nodo
			if (p != raiz) {
				p.getPadre().setHijo(indiceP, null);
				p.setPadre(null);
			}
		}

		return valorRemovido;

	}

	private boolean todoNulo(MapeoConTrie<E>.NodoTrie<E> p) {
		boolean todonulo = true;

		if (p.getImagen() != null)
			todonulo = false;

		for (int i = 0; todonulo && i < 26; i++) {
			if (p.getHijo(i) != null)
				todonulo = false;
		}

		return todonulo;
	}

	@Override
	public Iterable<String> keys() {
		PositionList<String> lista_claves = new ListaDoblementeEnlazada<>();
		String elem = "";
		recorrer_claves(lista_claves, elem, raiz);

		return lista_claves;
	}

	private void recorrer_claves(PositionList<String> lista_claves, String elem, MapeoConTrie<E>.NodoTrie<E> p) {

		if (p.getImagen() != null) {
			lista_claves.addLast(elem);
		}

		for (int i = 0; i < 26; i++) {
			if (p.getHijo(i) != null) {
				elem += (char)(i + (int)'a');
				recorrer_claves(lista_claves, elem, p.getHijo(i));
				elem = elem.substring(0, elem.length() - 1);
			}
		}
	}

	@Override
	public Iterable<E> values() {
		PositionList<E> lista_valores = new ListaDoblementeEnlazada<>();
		recorrer_valores(lista_valores, raiz);
		
		return lista_valores;
	}

	private void recorrer_valores(PositionList<E> lista_valores, NodoTrie<E> p) {
		if (p.getImagen() != null) {
			lista_valores.addLast(p.getImagen());
		}
		
		for (int i = 0; i < 26; i++) {
			if (p.getHijo(i) != null) {
				recorrer_valores(lista_valores, p.getHijo(i));
			}
		}
	}

	@Override
	public Iterable<Entry<String, E>> entries() {
		PositionList<Entry<String,E>> lista_entradas = new ListaDoblementeEnlazada<>();
		String elem = "";
		recorrer_entradas(lista_entradas, raiz, elem);
		
		return lista_entradas;
	}
	
	private void recorrer_entradas(PositionList<Entry<String, E>> lista_entradas, MapeoConTrie<E>.NodoTrie<E> p, String elem) {

		if (p.getImagen() != null) {
			lista_entradas.addLast(new Entrada<>(elem, p.getImagen()));
		}
		
		for (int i=0; i < 26; i++) {
			if (p.getHijo(i) != null) {
				elem += (char)(i + (int)'a');
				recorrer_entradas(lista_entradas, p.getHijo(i), elem);
				elem = elem.substring(0, elem.length() - 1);
			}
		}
	}

	public static void main(String args[]) {
		MapeoConTrie<Integer> m1 = new MapeoConTrie<>();

		try {
			m1.put("casa", 9);
			m1.put("caca", 3);
			m1.put("camion", 1);
			m1.put("aaa", 12);
			m1.put("sass", 133);
			m1.put("asado", 14);
			m1.put("odiado", 1221);
			m1.put("odin", 12131);
			m1.put("carla", 4132);

			System.out.println(m1.get("carla"));
			System.out.println(m1.keys().toString());
			System.out.println(m1.values().toString());
			System.out.println(m1.entries().toString());
		}
		catch (Exception err) {
			err.printStackTrace();
		}
	}

}
