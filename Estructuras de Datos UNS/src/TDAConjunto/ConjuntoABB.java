package TDAConjunto;

import java.util.Comparator;
import java.util.Iterator;
import ABB.*;

public class ConjuntoABB<E extends Comparable<E>> implements Conjunto<E> {

	protected ABB<E> elementos;
	protected int tamanyo;
	
	public ConjuntoABB(Comparator<E> comp){
		elementos = new ABB<E>(comp);
		tamanyo = 0;
	}
	
	public ConjuntoABB() {
		this(new DefaultComparador<E>());
	}
	@Override
	public void agregar(E item) {
		//Busco si está el elemento ya en el conjunto. Si no está, buscar(E x) del abb me retorna un nodo dummy con rotulo nulo
		NodoABB<E> p = elementos.buscar(item);
		//Si es un nodo dummy, quiere decir que el elemento no estaba ya en el conjunto
		if (p.getRotulo() == null) {
			elementos.expandir(p, item);
			//COMPORTAMIENTO ESPECIFICO DEL CONJUNTO: al no estar item en el conjunto, expando el nodo, permitiendo asi poder almacenar en su rotulo el item (y tengo dos nodos dummy más como corresponde)
			p.setRotulo(item);
			tamanyo++;
		}
		
		//si p no es nulo, es porque item ya estaba en el conjunto, entonces no hay que hacer nada ya que los conjuntos no almacenan elementos repetidos.
	}

	@Override
	public void eliminar(E item) {
		NodoABB<E> p = elementos.buscar(item);
		
		if (p.getRotulo() != null) {
			elementos.eliminar(p);
			tamanyo--;
		}
		else {
			System.out.println("No se pudo eliminar. Elemento no encontrado");
		}
	}

	@Override
	public boolean pertenece(E item) {
		return elementos.buscar(item).getRotulo() != null;
		//Si encontre a item, el rotulo del nodo no es null
		//si no lo encontró, devuelve un nodo dummy con rotulo nulo.
	}

	@Override
	public void union(Conjunto<E> c) {

	}

	@Override
	public void interseccion(Conjunto<E> c) {

	}
	
	@Override
	public Iterator<E> iterator() {
		return null;
	}
	
	public String toString() {
		return inorder(elementos.raiz(), "");
	}

	private String inorder(NodoABB<E> p, String salida) {
		if (p.getRotulo() != null) {
			salida += "(" + inorder(p.getLeft(), salida) + p.getRotulo() + inorder(p.getRight(), salida) + ")";
		}
		else {
			salida+= "";
		}
		
		return salida;
	}
}
