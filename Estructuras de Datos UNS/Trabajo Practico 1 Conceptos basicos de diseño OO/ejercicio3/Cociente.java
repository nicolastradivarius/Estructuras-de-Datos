package ejercicio3;

public class Cociente {

	private int n1, n2;
	
	public Cociente(int n1, int n2) {
		
		this.n1 = n1;
		this.n2 = n2;
	}
	
	public double division() throws ArithmeticException {
		
		if (n2 == 0)
			throw new ArithmeticException("No se puede dividir por cero");
		
		return (double) n1/n2;
	}
}
