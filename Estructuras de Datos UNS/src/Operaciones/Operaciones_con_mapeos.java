package Operaciones;

import TDACola.EmptyQueueException;
import TDAMapeo.*;
import TDACola.*;

public class Operaciones_con_mapeos {

	/**
	 * Problema: Dada una cola Q de caracteres, determinar la frecuencia de apariciones de cada caractér.
	 * Nota: la frecuencia de apariciones de un caracter C en una secuencia de caracteres Q se define como la cantidad
	 * de apariciones de C dividido la cantidad total de caracteres de Q.
	 * Ejemplo: si 'R' apareció 4 veces y hay 200 caracteres en la secuencia Q, entonces la frecuencia de apariciones
	 * de 'R' en Q es: 			4/200=0.02=2%
	 */

	public static void main(String args[]) {

		Queue<Character> q1 = new Cola_con_enlaces<Character>();
		
		for (int i=0; i < 196; i++) {
			
			q1.enqueue('a');
		}
		
		q1.enqueue('R');
		q1.enqueue('R');
		q1.enqueue('R');
		q1.enqueue('R');
		
		System.out.println(calcularFrecuenciaApariciones(q1).toString());
	}
	
	public static Map<Character, Float> calcularFrecuenciaApariciones(Queue<Character> q) {
		
		Map<Character, Float> m = new Mapeo_con_lista<Character, Float>();
		Map<Character, Float> resultado = new Mapeo_con_lista<Character, Float>();
		int cantidadTotalCaracteres = q.size();
		Float cantApariciones = 0f;
		char c = ' ';
		
		try {

			while (!q.isEmpty()) {
				c = q.dequeue();
				cantApariciones = m.get(c);
				if (cantApariciones == null)
					m.put(c, 1f);
				else
					m.put(c, cantApariciones + 1);
			}

			for (Entry<Character, Float> e : m.entries()) {
				resultado.put(e.getKey(), e.getValue()/cantidadTotalCaracteres);
			}

		}
		catch (EmptyQueueException | InvalidKeyException e) {
			e.printStackTrace();
		}
		return resultado;
	}
}
