package ejercicio5;

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

public interface Coleccion<E> {

	public void insertar(E elemento);
	
	public void eliminar(E elemento);
	
	public boolean pertenece(E elemento);
}
