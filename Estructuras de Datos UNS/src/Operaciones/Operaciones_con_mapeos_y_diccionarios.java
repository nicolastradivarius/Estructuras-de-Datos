package Operaciones;

import TDALista.*;
import TDAMapeo.*;

import java.util.Iterator;

import TDADiccionario.Dictionary;

public class Operaciones_con_mapeos_y_diccionarios {

	public  static void main(String args[]) {

		Map<Integer, String> m1 = new Mapeo_con_lista<Integer, String>();
		Map<Integer, String> m2 = new Mapeo_hash_abierto<Integer, String>();

		try {

			m1.put(1, "v2");
			m1.put(2, "v1");
			m1.put(3, "v1");
			m1.put(4, "v2");
			m1.put(5, "v1");
			m1.put(6, "v1");
			m1.put(7, "v1");
			m1.put(8, "v1");

			System.out.println(m1.toString());
			acomodar(m1);

			//PositionList<Entry<Integer, String>> lista = contiene_claves_y_valores_diferentes(m1,m2);

			//System.out.println(lista.toString());
			//acomodar(m1);
			System.out.println(m1.toString());
		}
		catch (TDAMapeo.InvalidKeyException err) {
			err.printStackTrace();
		}

	}

	/**
	 * Un m´etodo contiene_claves(...) que recibiendo dos mapeos m1 y m2 con claves y valores
	de tipo gen´erico K y V respectivamente, determine si todas las claves de m1 est´an contenidas
	en m2. Calcule el orden del tiempo de ejecuci´on de su soluci´on
	 */
	public static <K, V> boolean contiene_claves(Map<K,V> m1, Map<K,V> m2) {

		boolean contenida = true;

		try {

			if (m1 != null && m2 != null) {
				Iterable<K> it1 = m1.keys();
				for (K clave1 : it1) {

					if (m2.get(clave1) == null) {
						contenida = false;
						break;
					}
				}
			}
		}
		catch (TDAMapeo.InvalidKeyException err) {
			err.printStackTrace();
		}

		return contenida;
	}

	/*
	 * Un m´etodo contiene claves y valores diferentes(...) que recibiendo dos mapeos
m1 y m2 con claves y valores de tipo gen´erico K y V respectivamente, cree y retorne una
PositionList con aquellas entradas de m1 y m2 que tienen igual clave pero diferente valor.
Por ejemplo, asumiendo dos mapeos m1 y m2 tal que la entrada e1 = (29303; 8) pertenece a
m1 y la entrada e2 = (29303; 7) pertenece a m2, entonces e1 y e2 formar´an parte de la lista
retornada por el m´etodo. Calcule el orden del tiempo de ejecuci´on de su soluci´on.
	 */
	public static <K,V> PositionList<Entry<K,V>> contiene_claves_y_valores_diferentes(Map<K,V> m1, Map<K,V> m2){

		PositionList<Entry<K,V>> listaRetornante = new ListaDoblementeEnlazada<Entry<K,V>>();

		if (m1 != null && m2 != null) {

			Iterable<Entry<K, V>> it1 = m1.entries();
			Iterable<Entry<K, V>> it2 = m2.entries();

			for (Entry<K,V> e1 : it1) {
				for (Entry<K,V> e2 : it2) {

					if (e1.getKey() == e2.getKey() && e1.getValue() != e2.getValue()) {
						listaRetornante.addLast(e1);
						listaRetornante.addLast(e2);
					}
				}
			}
		}

		return listaRetornante;
	}

	/*
	 * Un m´etodo acomodar(...) que recibiendo un mapeo m1 con claves y valores de tipo
gen´erico K y V respectivamente, modifique m1 de forma tal que luego de la ejecuci´on del
m´etodo cada entrada de m1 tenga un valor asociado diferente al de las dem´as entradas.
Tenga en cuenta que es posible que ciertas entradas de m1 deban ser eliminadas. Analice
c´omo haciendo uso del TDA Diccionario este problema podr´ıa resolverse. Calcule el tiempo
de ejecuci´on de su soluci´on.
	 */
	public static <K,V> void acomodar(Map<K,V> m1) {
		Integer cant_veces = 0;
		Dictionary<V, Integer> dic = new TDADiccionario.Diccionario_con_lista<V, Integer>();
		try {
			if (!m1.isEmpty()) {
				for (Entry<K,V> e : m1.entries()) {
					cant_veces = (dic.find(e.getValue()) == null) ? 0 : dic.find(e.getValue()).getValue();
					cant_veces++;
					dic.insert(e.getValue(), cant_veces);
					
					if (cant_veces > 1) {
						m1.remove(e.getKey());
					}
				}
			}
		}
		catch (Exception err) {
			err.printStackTrace();
		}
	}

	/*
	 * Un m´etodo crear mapeo inverso(...) que recibiendo un mapeo m1 con claves y valores
de tipo gen´erico K y V respectivamente, cree y retorne un nuevo mapeo m2 de forma tal que
por cada entrada (ki; vi) en m1 exista una entrada (vi; ki) en m2. Analice y especifique qu´e
estrategia utilizar´a en caso de que en m1 existan dos o m´as entradas con igual valor asociado.
Calcule el orden del tiempo de ejecuci´on de su soluci´on.
	 */
	public static <V,K> Map<V,K> crear_mapeo_inverso (Map<K,V> m1) {
		Map<V,K> m2 = new Mapeo_con_lista<V,K>();
		Iterable<Entry<K, V>> entries1 = m1.entries();

		try {
			if (m1 != null) {
				for (Entry<K,V> e1 : entries1) {
					if (e1.getValue() != null)
						//en caso de haber entradas con valores repetidos para distintas claves, se opta por dejar en m2
						//las entradas de la forma (Vi, Ki) donde Ki es la última clave asociada a ese valor en m1.
						m2.put( e1.getValue(), e1.getKey());
				}
			}
		}
		catch (InvalidKeyException err) {
			err.printStackTrace();
		}

		return m2;
	}

	/*
	 * Un m´etodo convertir a diccionario(...) que recibiendo un mapeo Map<String,
Integer> m1 donde las claves representan palabras y los valores representan la cantidad de
veces que una palabra aparece en un texto dado, cree y retorne un Dictionary<Integer,String>
d1 en el que las claves son la cantidad de apariciones y los valores son las palabras. Por ejemplo, considerando 
m1 = {(Hola; 1); (que; 2); (lindo; 1); (examen; 1); (tuvimos; 1); (hoy; 1)} obtenido a partir de un archivo con la 
frase "Hola, que lindo examen que tuvimos hoy", el m´etodo debe retornar un diccionario 
d1 = {(1; {Hola; lindo; examen; tuvimos; hoy}); (2; {que})}.
	 */
}
