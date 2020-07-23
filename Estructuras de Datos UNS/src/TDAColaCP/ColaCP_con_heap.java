package TDAColaCP;

import java.util.Comparator;

public class ColaCP_con_heap<K extends Comparable<K>,V> implements PriorityQueue<K,V> {

	public static void main(String args[]) {
		ColaCP_con_heap<Integer, String> cp1 = new ColaCP_con_heap<>();
		try {
			cp1.insert(1, "nico");
			cp1.insert(6, "klsdkkdsk");
			cp1.insert(8, "lallala");
			cp1.insert(9, "sdsdds");
			cp1.insert(24, "assnd");
			cp1.insert(3, "aaaa");
			System.out.println(cp1.toString());

			cp1.removeMin();
			System.out.println(cp1.toString());

			cp1.removeMin();
			System.out.println(cp1.toString());

			cp1.removeMin();
			cp1.removeMin();
			
			
			System.out.println(cp1.toString());

		}
		catch (InvalidKeyException | EmptyPriorityQueueException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public String toString() {
		String salida = "[";
		for (int i=0; i<this.arreglo_entradas.length; i++) {
			salida += arreglo_entradas[i] + "|";
		}
		
		salida += "]";
		return salida;
	}
	//la componente 0 del arreglo NO será utilizada
	protected Entrada<K,V> [] arreglo_entradas;
	protected Comparator<K> keyComp;
	protected int size;

	@SuppressWarnings("unchecked")
	public ColaCP_con_heap(int maxElems, Comparator<K> comp) {
		this.arreglo_entradas = (Entrada<K,V> []) new Entrada[maxElems];
		this.keyComp = comp;
		size = 0;
	}

	@SuppressWarnings("unchecked")
	public ColaCP_con_heap(int maxElems) {
		this.arreglo_entradas = (Entrada<K,V> []) new Entrada[maxElems];
		this.keyComp = new DefaultComparator<K>();
		size = 0;
	}

	@SuppressWarnings("unchecked")
	public ColaCP_con_heap() {
		this.arreglo_entradas = (Entrada<K,V> []) new Entrada[10];
		this.keyComp = new DefaultComparator<K>();
		size = 0;
	}


	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public Entry<K, V> min() throws EmptyPriorityQueueException {
		if (size == 0)
			throw new EmptyPriorityQueueException();

		return arreglo_entradas[1];
	}

	@Override
	public Entry<K, V> insert(K key, V value) throws InvalidKeyException {
		checkKey(key);

		if ((size/(this.arreglo_entradas.length-1)) > 0.8f) {
			redimensionar();
		}

		Entrada<K,V> elemActual = null;
		Entrada<K,V> elemPadre = null;
		Entrada<K,V> aux = null;

		Entrada<K,V> entrada = new Entrada<>(key, value);
		arreglo_entradas[++size] = entrada;

		//comienzo a burbujear para arriba
		int i = size;
		boolean seguir = true;

		while (i>1 && seguir) {
			elemActual = arreglo_entradas[i];
			elemPadre = arreglo_entradas[i/2];

			if (this.keyComp.compare(elemActual.getKey(), elemPadre.getKey()) < 0) { //intercambio entradas si están desordenadas
				aux = arreglo_entradas[i];
				this.arreglo_entradas[i] = this.arreglo_entradas[i/2];
				this.arreglo_entradas[i/2] = aux;
				i /= 2; // reinicializo i con el indice de su padre
			}
			else { //si no pude intercambiar ==> la entrada ya estaba ordenada
				seguir = false; 
			}
		}

		return entrada;
	}

	@SuppressWarnings("unchecked")
	private void redimensionar() {
		Entrada<K,V> [] arreglo_entradas_original = arreglo_entradas;

		arreglo_entradas = (Entrada<K,V> []) new Entrada[arreglo_entradas.length + 20];

		for (int i=1; i<=size; i++) {
			arreglo_entradas[i] = arreglo_entradas_original[i];
		}

		arreglo_entradas_original = null;

	}

	@Override
	public Entry<K, V> removeMin() throws EmptyPriorityQueueException {
		Entry<K, V> entrada_retorno = min(); //salvo valor a retornar;
		int pos_minimo_hijos_de_i = 0; //aqui voy a computar la posición del mínimo de los hijos de i:
		int hi;
		int hd;
		boolean tieneHijoIzquierdo;
		boolean tieneHijoDerecho;

		if (size == 1) {
			this.arreglo_entradas[1] = null;
			size = 0;
		}
		else {
			//paso la última entrada a la raiz y la borro del final del arreglo, decremento size
			this.arreglo_entradas[1] = this.arreglo_entradas[size];
			this.arreglo_entradas[size] = null;
			size--;

			//burbujeo la nueva raíz hacia abajo buscando su unicacion correcta
			int i=1; //i es la ubicacion corriente (me ubico en la raiz)
			boolean seguir = true; 

			while (seguir) {
				//calculo la posicion de los hijos izq y derecho de i, y veo si existen
				hi = i*2;
				hd = i*2 + 1;
				tieneHijoIzquierdo = (hi <= size);
				tieneHijoDerecho = (hd <= size);
				pos_minimo_hijos_de_i = hi;

				if (!tieneHijoIzquierdo)
					seguir = false; //si no tiene hijo izq, llegué a una hoja
				else {
					if (tieneHijoDerecho) {
						//calculo cual es el menor de los hijos
						if (this.keyComp.compare(this.arreglo_entradas[hi].getKey(), this.arreglo_entradas[hd].getKey()) < 0 )
							pos_minimo_hijos_de_i = hi;
						else
							pos_minimo_hijos_de_i = hd;
					}
					else
						pos_minimo_hijos_de_i = hi; // Si hay hijo izquierdo y no hay hijo derecho, el mínimo es el izq
				}
				// Me fijo si hay que intercambiar el actual con el menor de sus hijos:
				if (seguir && this.keyComp.compare(this.arreglo_entradas[i].getKey(), this.arreglo_entradas[pos_minimo_hijos_de_i].getKey()) > 0) {
					Entrada<K, V> aux = this.arreglo_entradas[i]; // Intercambio la entrada i con la m
					this.arreglo_entradas[i] = this.arreglo_entradas[pos_minimo_hijos_de_i];
					this.arreglo_entradas[pos_minimo_hijos_de_i] = aux;
					i = pos_minimo_hijos_de_i; // Reinicializo i para en la siguiente iteración actualizar a partir de posición m
				}
				else {
					seguir = false;
				}
			}
		}

		return entrada_retorno;
	}

	private void checkKey(K key) throws InvalidKeyException {
		if (key == null)
			throw new InvalidKeyException();
	}
}
