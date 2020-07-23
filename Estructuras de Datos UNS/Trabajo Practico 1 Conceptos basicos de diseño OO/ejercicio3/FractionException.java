package ejercicio3;

/*
 * Una clase llamada FractionException derivada de la clase Exception. En su constructor,
esta clase debe recibir una cadena explicando la naturaleza del error producido.
 * 
 */

public class FractionException extends Exception {

	public FractionException(String msg) {
		
		super(msg);
	}
}
