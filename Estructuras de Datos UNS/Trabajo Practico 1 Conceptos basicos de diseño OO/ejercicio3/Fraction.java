package ejercicio3;

/*
 * Una clase llamada Fraction que representa una fracci´on como un par de n´umeros enteros. 
 * La clase Fraction debe tener un constructor con la siguiente signatura: 
 * Fraction(int numerador, int denominador). En el caso en que el denominador sea cero,
 *  el constructor debe lanzar una excepci´on de tipo FractionException.

 */

public class Fraction {
	
	protected int n1, n2;

	public Fraction (int numerador, int denominador) throws FractionException {
		
		if (denominador == 0) 
				throw new FractionException("El denominador no puede ser cero.");
		
		this.n1 = numerador;
		this.n2 = denominador;
	}
	
	public String getFraction() {
		
		if (n1 == 0) 
			return "0";
		
		else
			return n1 + "/" + n2;
	}
}
