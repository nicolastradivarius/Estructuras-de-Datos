package Operaciones;

import java.util.Comparator;

import TDAColaCP.*;

public class Operaciones_con_colasCP {

	public static void main(String args[]) {
		try {

			int i;
			Entry<Integer, Integer> e;
			PriorityQueue<Integer, Integer> ccp = new ColaCP_con_arreglo<Integer, Integer>( 9, new Mi_Comparador() );
			for(i = 1; i<9; i++){
				ccp.insert(i, null);
			}

			while(! ccp.isEmpty() ){
				e = ccp.removeMin();
				System.out.print(e.getKey() + " ");
			}
			
			
		}
		catch (Exception err) {
			err.printStackTrace();
		}
	}

	public static void mostrar_secuencia_ascendente() {
		try {
			PriorityQueue<Integer, Integer> pq1 = new TDAColaCP.ColaCP_con_arreglo<Integer, Integer>(7);
			pq1.insert(4, null);
			pq1.insert(5, null);
			pq1.insert(1, null);
			pq1.insert(3, null);
			pq1.insert(2, null);
			pq1.insert(6, null);
			pq1.insert(7, null);

			while (!pq1.isEmpty()) {
				System.out.print(pq1.removeMin() + " ");
			}
		}
		catch (Exception err) {
			err.printStackTrace();
		}
	}

	public static void mostrar_secuencia_descendente() {
		Comparator<Integer> DefaultComparator_inv = new DefaultComparator_inv<Integer>();
		PriorityQueue<Integer, Integer> pq1 = new TDAColaCP.ColaCP_con_arreglo<Integer, Integer>(7, DefaultComparator_inv);

		try {
			pq1.insert(4, null);
			pq1.insert(5, null);
			pq1.insert(1, null);
			pq1.insert(3, null);
			pq1.insert(2, null);
			pq1.insert(6, null);
			pq1.insert(7, null);

			while (!pq1.isEmpty()) {
				System.out.println(pq1.removeMin());
			}
		}
		catch (Exception err) {
			err.printStackTrace();
		}
	}

	public static void mostrar_secuencia_especial() {
		Comparator<Integer> comparador_especial = new Comparador_especial<Integer>();
		PriorityQueue<Integer, Integer> pq1 = new TDAColaCP.ColaCP_con_arreglo<Integer, Integer>(7, comparador_especial);

		try {
			pq1.insert(4, null);
			pq1.insert(5, null);
			pq1.insert(1, null);
			pq1.insert(3, null);
			pq1.insert(2, null);
			pq1.insert(6, null);
			pq1.insert(7, null);

			while (!pq1.isEmpty()) {
				System.out.println(pq1.removeMin());
			}
		}
		catch (Exception err) {
			err.printStackTrace();
		}
	}
}

class DefaultComparator_inv<K extends Comparable<K>> implements Comparator<K>{

	@Override
	public int compare(K o1, K o2) {
		return (-1)*o1.compareTo(o2);
	}
}

class Comparador_especial<K extends Comparable<K>> implements Comparator<Integer>{

	@Override
	public int compare(Integer a, Integer b) {
		int comparacion = 0;

		/*
		 * Recordemos:
		 * si a < b retorno un negativo ==> a tiene mas prioridad que b
		 * si a = b tienen misma prioridad retorno 0
		 * si a > b retorno un positivo ==> b tiene mas prioridad que a
		 */
		//retorno -1 si a tiene mayor prioridad que b
		//retorno 1 si b tiene mayor prioridad que b
		if (a % 2 == 0) { //si a es par
			if (b % 2 != 0) //si b es impar
				comparacion = -1; //a tiene mayor prioridad, b tiene menor, retorno -1
			else { //a es par y b es par
				if ( a < b) //si a es más chico que b
					comparacion = -1; //a tiene mayor prioridad, b tiene menor, return -1
				else //a es más grande que b --> b es mas chico que a
					comparacion = 1; //a tiene menor prioridad, b mayor, return 1
			}
		}
		else { //si a es impar
			if (b % 2 ==0) //si b es par
				comparacion = 1; //a tiene menor prioridad, b mayor, retorno 1
			else { //b es impar tambien
				if (a>b) //si a es mas grande que b --> b es mas chico que a
					comparacion = -1; //a tiene mayor prioridad que b
				else
					comparacion = 1; //b tiene mayor prioridad que a
			}
		}
		return comparacion;
	}

}