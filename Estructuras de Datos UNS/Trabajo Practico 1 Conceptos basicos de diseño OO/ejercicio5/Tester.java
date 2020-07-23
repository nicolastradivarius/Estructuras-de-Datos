package ejercicio5;

public class Tester {

	public static void main(String args[]) {
		
		ColeccionConVector<String> c1 = new ColeccionConVector<String>();
		
		c1.insertar("79.2");
		c1.insertar("9.2");
		c1.insertar("0.1");
		c1.insertar("9.2");
		
		System.out.println(c1.toString());
	}
}
