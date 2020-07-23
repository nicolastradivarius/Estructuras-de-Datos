package ejercicio3;

import java.util.Scanner;

public class Tester {

	public static void main(String args[]) {
		
		Scanner entrada = new Scanner(System.in);
		
		try {
			
			System.out.println("Ingrese dos n�meros para calcular su cociente: ");
			int a = entrada.nextInt();
			int b = entrada.nextInt();
			
			Cociente c = new Cociente(a, b);
			
			System.out.println("La divisi�n entre " + a + " y " + b + " es " + c.division());
			entrada.close();
		}
		
		catch (ArithmeticException e) {
			
			System.out.println(e.getMessage());
			entrada.close();
		}
		
		finally {
			
			System.out.println("Programa ejecutado exitosamente.");
		}
	}
}
