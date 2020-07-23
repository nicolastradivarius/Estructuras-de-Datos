package Logica;

/**
 * Modela un par ordenado (A, B).
 * @author Nicolás González
 * @param <A> Primer elemento del par ordenado
 * @param <B> Segundo elemento del par ordenado
 */
public class Pair<A, B> {

	private A a;
	private B b;

	/**
	 * Crea un par con elementos 'A' y 'B' pasados por parámetro
	 * @param a Elemento que va a ser almacenado en la primera parte del par.
	 * @param b Elemento que va a ser almacenado en la segunda parte del par.
	 */
	public Pair(A a, B b) {
		this.a = a;
		this.b = b;
	}

	/**
	 * Devuelve el primer elemento del par.
	 * @return el elemento 'A' del par
	 */
	public A getA() {
		return a;
	}

	/**
	 * Devuelve el segundo elemento del par.
	 * @return el elemento 'B' del par.
	 */
	public B getB() {
		return b;
	}
	
	/**
	 * Reemplaza el valor del primer elemento del par por el elemento parametrizado.
	 * @param a El elemento que se va a almacenar en la primera parte del par.
	 */
	public void setA(A a) {
		this.a = a;
	}
	
	/**
	 * Reemplaza el valor del segundo elemento del par por el elemento parametrizado.
	 * @param b El elemento que se va a almacenar en la segunda parte del par.
	 */
	public void setB(B b) {
		this.b = b;
	}
}
