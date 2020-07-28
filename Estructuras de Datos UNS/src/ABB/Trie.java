package ABB;

import TDACola.EmptyQueueException;
import TDACola.Queue;
import TDALista.ListaDoblementeEnlazada;
import TDALista.PositionList;
import TDAMapeo.Entry;
import TDAMapeo.InvalidKeyException;
import TDAMapeo.Map;
import TDAMapeo.Mapeo_con_lista;

public class Trie {

	private class NodoTrie<E> {

		private NodoTrie<E> padre;
		private NodoTrie<E> [] hijos;
		private boolean terminador;

		@SuppressWarnings("unchecked")
		public NodoTrie(NodoTrie<E> padre) {
			hijos = new NodoTrie[26];
			this.padre = padre;
			terminador = false;
		}

		public boolean esTerminador() {
			return terminador;
		}

		public NodoTrie<E> getPadre() {
			return padre;
		}

		public NodoTrie<E> getHijo(int i) {
			return hijos[i];
		}

		public void setPadre(NodoTrie<E> padre) {
			this.padre = padre;
		}

		public void setTerminador(boolean t) {
			terminador = t;
		}

		public void setHijo(int i, NodoTrie<E> hijo) {
			this.hijos[i] = hijo;
		}

	}

	protected NodoTrie<String> raiz;

	public Trie() {
		raiz = new NodoTrie<>(null);
	}

	public boolean get(String clave) {
		boolean esta = false;
		return get_aux(clave, 0, clave.length(), raiz, esta);
	}

	private boolean get_aux(String clave, int i, int n, NodoTrie<String> p, boolean esta) {
		if (i == n) {
			if ( p.esTerminador())
				esta = true;
			else
				esta = false;
		}
		else {
			int indice = (int) clave.charAt(i) - (int)'a';

			if (p.getHijo(indice) == null)
				esta = false;
			else
				esta = get_aux(clave, i+1, n, p.getHijo(indice), esta);
		}

		return esta;
	}

	public void put(String clave) {
		put_aux(clave, 0, clave.length(), raiz);
	}

	private void put_aux(String clave, int i, int n, NodoTrie<String> p) {
		if (i < n) {
			int indice = ((int)clave.charAt(i)) - ((int)'a');
			if (p.getHijo(indice) == null) {
				p.setHijo(indice, new NodoTrie<>(p));
			}

			put_aux(clave, i+1, n, p.getHijo(indice));
		}
		else {
			p.setTerminador(true);
		}
	}

	public String remove(String clave) {
		String cadena = "";

		cadena = remove_aux(clave, 0, clave.length(), raiz, 0, cadena);

		return cadena;
	}

	private String remove_aux(String clave, int i, int n, NodoTrie<String> p, int indiceP, String cadena) {
		if (i == n) {
			if (!p.esTerminador())
				cadena = null;
			else {
				p.setTerminador(false);
			}
		}
		else {
			int indice = (int) clave.charAt(i) - (int)'a';
			if (p.getHijo(indice) == null) {
				cadena = null;
			}
			else {
				cadena += (char) (indice + (int)'a');
				cadena = remove_aux(clave, i+1, n, p.getHijo(indice), indice, cadena);
			}
		}

		if (todoNulo(p)) {
			if (p!=raiz) {
				p.getPadre().setHijo(indiceP, null);
				p.setPadre(null);

			}
		}
		return cadena;
	}

	private boolean todoNulo(NodoTrie<String> p) {
		boolean todonulo = true;
		for (int i = 0; todonulo && i < 26; i++) {
			if (p.getHijo(i) != null)
				todonulo = false;
		}

		return todonulo;
	}

	public PositionList<String> conjunto_strings(){
		PositionList<String> lista_cadenas = new ListaDoblementeEnlazada<>();
		String cadena = "";

		recorrer_trie(lista_cadenas, raiz, cadena);

		return lista_cadenas;

	}

	private void recorrer_trie(PositionList<String> lista_cadenas, NodoTrie<String> p, String cadena) {
		if (p.esTerminador()) {
			lista_cadenas.addLast(cadena);
		}
		for (int i=0; i < 26; i++) {
			if (p.getHijo(i) != null) {
				cadena += (char)(i + (int)'a');
				recorrer_trie(lista_cadenas, p.getHijo(i), cadena);
				cadena = cadena.substring(0, cadena.length() - 1);
			}
		}
	}

	public static void main(String args[]) {
		Trie t = new Trie();


		t.put("nico");
		t.put("nicolas");
		t.put("jsjdj");
		t.put("ana");
		System.out.println(t.get("nicolas"));
		System.out.println(t.get("nicoo"));


		System.out.println(t.conjunto_strings().toString());
	}
	
}
