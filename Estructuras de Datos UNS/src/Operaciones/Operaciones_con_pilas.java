package Operaciones;

import TDAPila.*;

public class Operaciones_con_pilas {

	public static void invertir_arreglo(int [] a) {
		
		Stack<Integer> s = new Pila_con_enlaces<Integer>();
		
		for (int i=0; i<a.length; i++) {
			s.push(a[i]);
		}
		int i=0;
		while(!s.isEmpty()) {
			try {
				a[i] = s.pop();
				i++;
			} catch (EmptyStackException e) {
				System.out.println(e.getMessage());
			}
			
		}
	}
	
	public static String toString(int[] a) {
		
		String salida = "";
		
		for (int i=0; i < a.length; i++) {
			
			salida += a[i] + ", ";
		}
		
		return salida;
	}
	
	public static <E> Stack<E> invertir(Stack<E> p) throws EmptyStackException {
		if (p.size() == 0) throw new EmptyStackException("ERROR: Pila vac�a");
		
		Stack<E> pilaInv = new Pila_con_enlaces<E>();
		
		while(!p.isEmpty()) {
			
			pilaInv.push(p.pop());
		}
		
		return pilaInv;
		
	}
	
	@SuppressWarnings("unused")
	public static void main(String args[]) {
		int incremento = 2;
		int num = 0;
		/*int [] arreglo = new int[15];
		
		for (int i=0; i < arreglo.length; i++) {
			
			arreglo[i] = (num + incremento);
			num++; incremento++;
		}
		
		System.out.println(toString(arreglo));
		
		invertir_arreglo(arreglo);
		
		System.out.println(toString(arreglo));*/
		
		Stack<Integer> s = new Pila_con_enlaces<Integer>();
		
		s.push(2);
		s.push(1);
		s.push(6);
		s.push(9);
		
		System.out.println(s.toString());
		
		try {
			
			s = invertir(s);
			System.out.println(s.toString());
		} catch (EmptyStackException e) {
			System.out.println(e.getMessage());
		}
	}
}
 