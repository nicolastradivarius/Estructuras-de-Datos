package Logica;

import TDAMapeo.*;
import TDADiccionario.*;
import TDAArbol.*;
import TDALista.*;
import TDAPila.*;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.io.BufferedReader;
import TDAGrafo.*;


/**
 * Clase que contiene los métodos solicitados por la sección 3 del Proyecto N°2 de la cátedra Estructuras de Datos, DCIC-UNS.
 * Año 2020, primer cuatrimestre.
 * @author Nicolás González
 */
public class Logica {

	/**
	 * Procesa un archivo de texto compuesto únicamente por palabras (preferiblemente en minúsculas)
	 * separadas por un espacio, computa en un mapeo la cantidad de apariciones de cada palabra en el archivo y asocia
	 * en un diccionario la inicial de cada palabra con todas las palabras que comienzan con ella.
	 * @param path La ruta absoluta a un archivo de texto.
	 * @return Un <b>Mapeo &ltString, Integer&gt</b> que contabiliza la cantidad total de veces que una palabra aparece
	 * en el archivo, y un <b>Diccionario&ltCharacter, String&gt</b> que asocia a cada inicial con las palabras que comienzan con esa inicial,
	 * en forma de par ordenado (Mapeo, Diccionario).
	 */
	public static Pair<Map<String, Integer>, Dictionary<Character, String>> estadisticas(String path) {

		Pair<Map<String, Integer>, Dictionary<Character, String>> stats = 
				new Pair<Map<String, Integer>, Dictionary<Character, String>>(new Mapeo_hash_abierto<String, Integer>(), new Diccionario_hash_cerrado<Character, String>());

		BufferedReader a;
		String [] arreglo_linea = null;
		Integer cant_apariciones = null;
		String linea = "";
		String palabra = "";
		Character inicial = ' ';

		try {
			a = new BufferedReader (new FileReader(path));

			//Lee cada linea del archivo hasta llegar al final (no se toman en cuenta líneas "blancas").
			while ((linea = a.readLine()) != null) {

				if (!linea.isBlank()) {
					arreglo_linea = linea.split(" ");

					for (int i = 0; i < arreglo_linea.length; i++) {
						palabra = arreglo_linea[i];
						inicial = arreglo_linea[i].charAt(0);

						//Inserta la palabra leída en el mapeo e incrementa la cantidad de veces que aparece
						cant_apariciones = stats.getA().get(palabra);
						if (cant_apariciones == null) {
							stats.getA().put(palabra, 1);
							cant_apariciones = 1;
						}
						else {
							cant_apariciones++;
							stats.getA().put(palabra, cant_apariciones);
						}

						//Inserta la inicial leída con la palabra asociada en el Diccionario. Para evitar repeticiones, se pregunta si la palabra en cuestión apareció solo una vez hasta el momento.
						if (cant_apariciones == 1)
							stats.getB().insert(inicial, palabra);
					}
				}

			}

			a.close();
		}
		catch (IOException | TDAMapeo.InvalidKeyException | TDADiccionario.InvalidKeyException err) {
			err.printStackTrace();
		}

		return stats;
	}

	/**
	 * Calcula el camino entre dos posiciones de un mismo árbol.
	 * @param <E> Tipo de elemento de la lista de posiciones de árbol resultante.
	 * @param t1 el árbol donde se quiere calcular el camino
	 * @param p1 La posición inicial
	 * @param p2 La posición final
	 * @return Una lista de posiciones de árbol que representa el camino entre p1 y p2 en t1.
	 */
	public static <E> PositionList<TDAArbol.Position<E>> camino(Tree<E> t1, TDAArbol.Position<E> p1, TDAArbol.Position<E> p2){

		Stack<TDAArbol.Position<E>> pila1 = new Pila_con_enlaces<TDAArbol.Position<E>>();
		Stack<TDAArbol.Position<E>> pila2 = new Pila_con_enlaces<TDAArbol.Position<E>>();
		PositionList<TDAArbol.Position<E>> camino = new ListaDoblementeEnlazada<TDAArbol.Position<E>>();
		TDAArbol.Position<E> aux = null;
		TDAArbol.Position<E> desapilado_pila1 = null;
		TDAArbol.Position<E> desapilado_pila2 = null;

		try {

			if (!t1.isEmpty()) {
				if (sonPosicionesValidas(t1, p1, p2)) {
					if (!p1.equals(p2)) {
						//Apila las posiciones del árbol a partir de p1 hasta llegar a la raíz
						while (p1 != null) {
							pila1.push(p1);
							p1 = (p1 == t1.root()) ? null : t1.parent(p1);
						}

						//Apila las posiciones del árbol a partir de p2 hasta llegar a la raíz
						while (p2 != null) {
							pila2.push(p2);
							p2 = (p2 == t1.root()) ? null : t1.parent(p2);
						}

						//Como en ambas pilas se apiló hasta la raíz, se desapila una vez para comenzar a procesar el camino
						desapilado_pila1 = pila1.pop();
						desapilado_pila2 = pila2.pop();
						aux = desapilado_pila1;

						//Desapila en ambas pilas hasta que sus topes sean diferentes, pues eso indica que allí se bifurcó el camino.
						while (!pila1.isEmpty() && !pila2.isEmpty() && pila1.top().equals(pila2.top())) {
							desapilado_pila1 = pila1.pop();
							desapilado_pila2 = pila2.pop();
							aux = desapilado_pila1;
						}

						//Como el camino es de la posición p1 a la p2, una manera correcta de insertar las posiciones-recorrido
						//en el camino es empezar por insertarle a lo último el contenido de la pila2 (posiciones desde p2 hasta donde cortó el bucle anterior)
						while (!pila2.isEmpty())
							camino.addLast(pila2.pop());

						//se inserta la posición en común que tienen ambos recorridos antes de bifurcarse
						camino.addFirst(aux);

						//se superponen el resto de las posiciones de la pila1 en el camino
						while (!pila1.isEmpty())
							camino.addFirst(pila1.pop());
					}

				}
			}
		}
		catch (EmptyTreeException | TDAArbol.InvalidPositionException | TDAArbol.BoundaryViolationException | EmptyStackException err) {
			err.printStackTrace();
		}

		return camino;
	}

	/**
	 * Clona de manera espejada un árbol t1 parametrizado en otro árbol. Con espejado se refiere a que las posiciones del nuevo árbol están invertidas respecto al original.
	 * Por ejemplo, dada una posición P con hijos h1 y h2 izquierdo y derecho respectivamente, el árbol clonado contendrá a la posición P con los hijos h2 y h1 izquierdo y derecho respectivamente. 
	 * @param <E> Tipo de elementos del árbol
	 * @param t1 el árbol que se va a clonar
	 * @return Árbol que es un clon espejado de t1. 
	 */
	public static <E> Tree<E> clonar_espejado(Tree<E> t1) {
		Tree<E> t2 = new Arbol_enlazado<E>();
		TDAArbol.Position<E> raiz_t1;
		TDAArbol.Position<E> padre_t2;

		try {
			if (!t1.isEmpty()) {
				//Clonamos la raíz
				raiz_t1 = t1.root();
				t2.createRoot(raiz_t1.element());

				padre_t2 = t2.root();
				//Clonamos el resto de las posiciones de t1
				clonar_recursivamente(raiz_t1, padre_t2, t1, t2);
			}
		}
		catch (EmptyTreeException | TDAArbol.InvalidOperationException err) {
			err.printStackTrace();
		}

		return t2;
	}

	/**
	 * Determina si dos posiciones pasadas por parámetro pertenecen al mismo árbol.
	 * @param <E> Tipo de elemento del árbol y posiciones
	 * @param t1 el árbol que se va a usar para la comprobación
	 * @param p1 Una posición que se va a chequear
	 * @param p2 La otra posición que se quiere chequear
	 * @return Verdadero si ambas posiciones están en el árbol t1, falso en caso contrario.
	 */
	private static <E> boolean sonPosicionesValidas(Tree<E> t1, TDAArbol.Position<E> p1, TDAArbol.Position<E> p2) {
		boolean esValidap1 = false;
		boolean esValidap2 = false;
		Iterable<TDAArbol.Position<E>> it = t1.positions();

		if (p1 != null && p2 != null) {
			for (TDAArbol.Position<E> p : it) {
				if (p == p1)
					esValidap1 = true;

				if (p == p2)
					esValidap2 = true;

				if (esValidap1 && esValidap2)
					break;
			}
		}

		return esValidap1 && esValidap2;
	}

	/**
	 * Clona espejadamente de manera recursiva las posiciones de un árbol t1 en el árbol t2
	 * @param <E> Tipo de elemento de las posiciones y los árboles.
	 * @param padre_t1 Una posición "padre" del árbol t1 a partir de la cual se van a tomar sus hijos para clonarlos 
	 * @param padre_t2 Una posición "padre" del árbol t2 a partir de la cual se van a insertar los hijos clonados de padre_t1
	 * @param t1 Árbol del cual se toman las posiciones para ser clonadas
	 * @param t2 Árbol en donde se van a clonar las posiciones de t1.
	 */
	private static <E> void clonar_recursivamente(TDAArbol.Position<E> padre_t1, TDAArbol.Position<E> padre_t2, Tree<E> t1, Tree<E> t2) {
		try {
			TDAArbol.Position<E> h_padre_t1;
			TDAArbol.Position<E> h_padre_t2;
			Iterator<TDAArbol.Position<E>> it_t1 = t1.children(padre_t1).iterator();

			//Clona el nodo en el cual se encuentra posicionado y llama a clonar recursivamente a sus hijos.
			while (it_t1.hasNext()) {
				h_padre_t1 = it_t1.next();
				h_padre_t2 = t2.addFirstChild(padre_t2, h_padre_t1.element());
				clonar_recursivamente(h_padre_t1, h_padre_t2, t1,t2);
			}
		}

		catch (TDAArbol.InvalidPositionException err) {
			err.printStackTrace();
		}
	}
}