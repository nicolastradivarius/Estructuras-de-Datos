package ABB;

import java.util.Comparator;

public class ABB<E extends Comparable<E>> {

	protected NodoABB<E> raiz;
	protected Comparator<E> comparador;

	public ABB() {
		raiz = new NodoABB<E>(null, null);
		comparador = new DefaultComparador<E>();
	}

	public ABB(Comparator<E> comp) {
		//Creo un nodo dummy como raiz del arbol para dejarlo preparado para la primera insercion
		raiz = new NodoABB<E> (null, null);
		comparador = comp;
	}

	/**
	 * Retorna el nodo del ABB donde se encuentra o deberia encontrarse el elemento e.
	 * @param x
	 * @return
	 */
	public NodoABB<E> buscar(E x){
		NodoABB<E> buscado = null; 
		return buscar_aux(x, raiz, buscado);
	}

	private NodoABB<E> buscar_aux(E x, NodoABB<E> p, NodoABB<E> buscado) {
		if (p.getRotulo() == null)
			buscado = p;
		else {
			int c = comparador.compare(x, p.getRotulo());
			if (c==0)
				buscado = p;
			else if (c < 0)
				buscado = buscar_aux(x, p.getLeft(), buscado);
			else 
				buscado = buscar_aux(x, p.getRight(), buscado);
		}

		return buscado;
	}

	/**
	 * Expande el nodo n del ABB, de forma luego de la ejecucion del metodo:
	 * 1) n contiene a e como elemento.
	 *	2) n tiene por hijos izquierdo y derechos dos nodos dummy.
	 * @param n
	 */
	public void expandir(NodoABB<E> n, E e) {
		n.setLeft(new NodoABB<E>(null, n));
		n.setRight(new NodoABB<E>(null, n));
		n.setRotulo(e);
	}

	/**
	 * Retorna el nodo raiz del ABB
	 * @return
	 */
	public NodoABB<E> raiz(){
		return raiz;
	}

	/*
	 	Paso 1: Buscar el nodo P con la clave x a eliminar
 		Paso 2: Cuatro casos:
			1. P es una hoja: Eliminarlo
			2. P tiene sólo hijo izquierdo: hacer el bypass del padre
			de p con el hijo izquierdo de p
			3. P tiene sólo hijo derecho: hacer el bypass del padre
			de p con el hijo derecho de p
			4. P tiene dos hijos: Reemplazar el rótulo de p con el
			de su sucesor inorder y eliminar el sucesor inorder
			de p.
	 */

	/**
	 * Elimina el nodo del ABB donde se encuentra el elemento e.
	 */
	public void eliminar(NodoABB<E> p) {
		//caso 1: p es hoja --> convertir el nodo en un dummy y soltar sus hijos dummy
		if (isExternal(p)) {
			p.setRotulo(null);
			p.setLeft(null);
			p.setRight(null);
		}
		else { //p no es hoja
			if (p == raiz) { 
				if (soloTieneHijoIzquierdo(p)) {//caso 2
					p.setRotulo(p.getLeft().getRotulo());
					p.getLeft().getLeft().setParent(p);
					p.getLeft().getRight().setParent(p);
				}
				else if (soloTieneHijoDerecho(p)) { //caso 3
					p.setRotulo(p.getRight().getRotulo());
					p.getRight().getLeft().setParent(p);
					p.getRight().getRight().setParent(p);
				}
				else { //ca
					
				}


			}
			//caso 2, si p no es la raíz, 
			else if (soloTieneHijoIzquierdo(p)) {
				if (p.getParent().getLeft() == p) { //si p es el hijo izquierdo de su padre
					p.getParent().setLeft(p.getLeft()); //ahora el hijo izquierdo del padre de p es el hijo de p (recordemos que p tenia un unico hijo)
				}
				else { //p es el hijo derecho de su padre
					p.getParent().setRight(p.getLeft()); //el hijo derecho del padre de p es ahora el hijo de p
				}

				p.getLeft().setParent(p.getParent()); //ahora el padre del hijo izquierdo de p es su abuelo (el padre de p)
			}
			else if (soloTieneHijoDerecho(p)) {
				//caso 3: enganchar al padre de p con el hijo derecho de p
				if (p.getParent().getLeft() == p) {
					p.getParent().setLeft(p.getRight());
				}
				else {
					p.getParent().setRight(p.getRight());
				}
				p.getRight().setParent(p.getParent());
			}
			else {
				//caso 4: p tiene dos hijos --> seteo como rotulo de p al rotulo del sucesor inorder de p
				p.setRotulo(eliminarMinimo(p.getRight(), null)); 
			}
		}
	}

	private E eliminarMinimo(NodoABB<E> p, E aRetornar) {
		if (p.getLeft().getRotulo() == null) { // El hijo izquierdo de p es un dummy
			aRetornar = p.getRotulo(); // salvo el rótulo a devolver

			if ( p.getRight().getRotulo() == null) { // p es hoja (pues sus hijos son dummy)
				p.setRotulo(null); // Convierto a p en dummy haciendo nulo su rótulo
				p.setLeft(null); // y desenganchando sus dos hijos dummy
				p.setRight(null) ;
			} 
			else { // p solo tiene hijo derecho (xq no tiene izquierdo)
				// Engancho al padre de p con el hijo derecho de p.
				// Seguro tiene que ser el hijo derecho de su padre.
				p.getParent().setLeft((p.getRight()));
				p.getRight().setParent(p.getParent() );
			}

		} 
		else { // Si p tiene hijo izquierdo, entonces p.getRotulo() no es el mínimo.
			// El mínimo tiene que estar en el subárbol izquierdo
			aRetornar = eliminarMinimo(p.getLeft(), aRetornar);
		}

		return aRetornar;
	}

	private boolean isExternal(NodoABB<E> p) {
		return p.getLeft().getRotulo() == null && p.getRight().getRotulo() == null;
	}

	private boolean soloTieneHijoIzquierdo(NodoABB<E> p) {
		return p.getLeft().getRotulo() != null && p.getRight().getRotulo() == null;
	}

	private boolean soloTieneHijoDerecho(NodoABB<E> p) {
		return p.getLeft().getRotulo() == null && p.getRight().getRotulo() != null;
	}

}
