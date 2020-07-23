package ejercicio5;

import java.util.Vector;

/*
 * 5. Dada una coleccion de elementos que permite las operaciones insertar y eliminar
 *  un elemento, asi como permite chequear si un elemento pertenece o no a la coleccion, 
 *  resuelva: a) Defina una interfaz generica Coleccion que represente una coleccion de elementos 
 *  genericos mediante genericidad parametrica.
 *  
 *  b) Defina una clase Coleccion con vector que implemente la interfaz Coleccion definida en
	5a) mediante un vector.

	c) Defina una interfaz gen´erica Coleccion polimorfica que represente una colecci´on
 	de elementos gen´ericos mediante genericidad a trav´es de polimorfismo.

	d) Defina una clase Coleccion polimorfica con arreglo que implemente la interfaz
	Coleccion polimorfica definida en 5c) mediante un arreglo.
 */

public class ColeccionConVector<E> implements Coleccion<E> {
	
	protected Vector<E> v;
	
	public ColeccionConVector() {
		
		v = new Vector<E>();
	}

	public String toString() {
		
		return v.toString();
	}


	public void insertar(E elemento) {
		
		if (!v.contains(elemento)){
		    
		    v.add(elemento);
		}
		
	}


	@Override
	public void eliminar(E elemento) {
		// TODO Auto-generated method stub
		v.remove(elemento);
	}


	@Override
	public boolean pertenece(E elemento) {
		// TODO Auto-generated method stub
		return v.contains(elemento);
	}
}
