package ejercicio3;

import java.util.Scanner;

/*
 * Una clase ProgramaPrincipal que lea dos n´umeros por teclado y cree una fracci´on utilizando
la clase Fraction implementada en el inciso 3c, tomando el primer n´umero le´ido como
numerador y el segundo como denominador. El programa debe ser capaz de capturar una
excepci´on FractionException en el caso en que esta sea lanzada al crear la fracci´on.
 */

public class ProgramaPrincipal {
	
	public static void main(String args[]) {
	
		Scanner entrada = new Scanner(System.in);
		
		try {
			
			System.out.println("Ingrese dos números por teclado para formar una fraccion: ");
			
			int num1 = entrada.nextInt();
			int num2 = entrada.nextInt();
			entrada.close();
			
			Fraction f1 = new Fraction(num1, num2);
			System.out.println("La fraccion es: " + f1.getFraction());
		}
		
		catch (FractionException e) {
			
			System.out.println(e.getMessage());
			entrada.close();
		}
	
	}
}
