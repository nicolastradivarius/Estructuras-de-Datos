package ejercicio4;

/* Implemente un programa Java que cree una clase llamada Pair que pueda almacenar dos objetos 
 * declarados como tipos gen´ericos. Demuestre el uso de la clase Pair creando 
 * e imprimiendo distintos objetos conteniendo cinco tipos diferentes de pares, 
 * como por ejemplo <Integer, String> y <Float, Long>, entre otros.
 *  Cree un objeto conteniendo (23,"Juan") y otro conteniendo (23.2f, 21345).
*/

public class Pair <A, B> {
	
	protected A firstValue;
	protected B secondValue;
	
	public Pair(A first, B second) {
		
		this.firstValue = first;
		this.secondValue = second;
	}
	
	public A getFirstValue() {
		
		return firstValue;
	}
	
	public B getSecondValue() {
		
		return secondValue;
	}
	
	public String toString() {
		
		return "[" + firstValue + ", " + secondValue + "]";
	}

}
