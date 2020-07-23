package Operaciones;

import java.util.Iterator;
import java.util.NoSuchElementException;

import TDALista.*;

public class Operaciones_con_listas {

	public static boolean contenido_e_invertido(PositionList<Character> l1, PositionList<Character> l2) {
		boolean valida = true;
		Position<Character> cursor1, cursor2;
		try {

			if (!l1.isEmpty() && !l2.isEmpty()) {
				if (l1.size() != l2.size()*2) {
					valida = false;
				}
				else {
					cursor1 = l1.first();
					cursor2 = l2.first();

					while (cursor2 != null && valida) {
						valida = cursor1.element() == cursor2.element();
						cursor1 = l1.next(cursor1);

						if (cursor2 == l2.last())
							cursor2 = null;
						else
							cursor2 = l2.next(cursor2);
					}

					valida = (valida) && (cursor2 == null);

					cursor2 = l2.last();
					while(valida && cursor1 != null && cursor2 != null) {

						valida = cursor1.element() == cursor2.element();

						if (cursor1 == l1.last() && cursor2 == l2.first())
							cursor1 = cursor2 = null;
						else {
							cursor1 = l1.next(cursor1);
							cursor2 = l2.prev(cursor2);
						}	

					}

					valida = (valida) && (cursor1==null) && (cursor2 == null);

				}
			}
			else {
				valida = false;
			}
		} catch (EmptyListException e) {
			System.out.println(e.getMessage());
		} catch (InvalidPositionException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		} catch (BoundaryViolationException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}

		return valida;
	}

	//recibe dos listas de enteros l1 y l2, crea y retorna una nueva lista l3 cuyos elementos son
	//la intercalacion sin repeticiones de los elementos de l1 y l2.
	public static PositionList<Integer> intercalar_sin_repetidos(PositionList<Integer> l1, PositionList<Integer> l2) {
		PositionList<Integer> l3 = new ListaDoblementeEnlazada<Integer>();
		Position<Integer> cursor1, cursor2, cursor3;
		boolean repetido = false;
		try {

			//si ninguna de las listas es vacía (tienen al menos un elemento)
			if (!(l1.isEmpty()) && (!(l2.isEmpty()))) {

				cursor1 = l1.first();
				cursor2 = l2.first();
				cursor3 = null; //pues l3 es vacía en este momento

				l3.addLast(cursor1.element()); //añado el primer elemento de l1 a l3 (total no necesita ser comparado con ningun otro)

				while (cursor1 != null && cursor2 != null) {
					repetido = false;
					cursor3 = l3.first(); //inicializo cursor3 con el primer elemento de l3
					while (cursor3 != null && !repetido) { //comienzo a comparar cada elemento de l2 con los de l3 para ver si ya está en l3 o no
						if (cursor2.element() == cursor3.element()) //a medida que vaya agregando elementos a l3, el recorrido va siendo más grande. Esto afecta en algo al cálculo del T(n)?
							repetido = true;
						else
							cursor3 = (cursor3 == l3.last()) ? null : l3.next((cursor3));
					}

					if (!repetido)
						l3.addLast(cursor2.element());
					cursor2 = (cursor2 == l2.last()) ? null : l2.next(cursor2);
					repetido = false;


					cursor3 = l3.first();
					while (cursor3 != null && !repetido) {
						if (cursor1.element() == cursor3.element())
							repetido = true;
						else
							cursor3 = (cursor3 == l3.last()) ? null : l3.next((cursor3));
					}
					if (!repetido)
						l3.addLast(cursor1.element());
					cursor1 = (cursor1 == l1.last()) ? null : l1.next(cursor1);
				}
				//si cursor2 es nulo es porque se me acabó l2 antes que l1.
				if (cursor2 == null) {
					while (cursor1 != null) {
						cursor3 = l3.first();
						while (cursor3 != null && !repetido) {
							if (cursor1.element() == cursor3.element())
								repetido = true;
							else
								cursor3 = (cursor3 == l3.last()) ? null : l3.next((cursor3));
						}
						if (!repetido)
							l3.addLast(cursor1.element());
						cursor1 = (cursor1 == l1.last()) ? null : l1.next(cursor1);
					}
				} //caso contrario, l1 se acabó antes que l2
				else {
					while (cursor2 != null) {
						cursor3 = l3.first();
						while (cursor3 != null && !repetido) {
							if (cursor2.element() == cursor3.element())
								repetido = true;
							else
								cursor3 = (cursor3 == l3.last()) ? null : l3.next((cursor3));
						}
						if (!repetido)
							l3.addLast(cursor2.element());
						cursor2 = (cursor2 == l2.last()) ? null : l2.next(cursor2);
					}
				}

			}
			else {
				//si l1 es vacía, añado todos los elementos de l2 a l3 teniendo en cuenta que no se deben repetir
				if (l1.isEmpty()) {
					cursor2 = l2.first();
					l3.addLast(cursor2.element());
					while (cursor2 != null) {
						cursor3 = l3.first();
						while (cursor3 != null && !repetido) {
							if (cursor2.element() == cursor3.element())
								repetido = true;
							else
								cursor3 = (cursor3 == l3.last()) ? null : l3.next((cursor3));
						}
						if (!repetido)
							l3.addLast(cursor2.element());
						cursor2 = (cursor2 == l2.last()) ? null : l2.next(cursor2);
						repetido = false;
					}
				}
				//lo mismo si l2 es vacía:
				else {
					cursor1 = l1.first();
					l3.addLast(cursor1.element());
					while (cursor1 != null) {
						cursor3 = l3.first();
						while (cursor3 != null && !repetido) {
							if (cursor1.element() == cursor3.element())
								repetido = true;
							else
								cursor3 = (cursor3 == l3.last()) ? null : l3.next((cursor3));
						}
						if (!repetido)
							l3.addLast(cursor1.element());
						cursor1 = (cursor1 == l1.last()) ? null : l1.next(cursor1);
						repetido = false;
					}
				}
			}
		}
		catch (EmptyListException e) {
			e.printStackTrace();
		} catch (InvalidPositionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BoundaryViolationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return l3;

	}

	public static PositionList<Integer> intercalar_ordenados_sin_repetidos(PositionList<Integer> l1, PositionList<Integer> l2) {
		//la unica operacion disponible para acceder a los elementos es iterator
		PositionList<Integer> l3 = new ListaDoblementeEnlazada<>();
		Iterator<Integer> it1 = l1.iterator();
		Iterator<Integer> it2 = l2.iterator();
		Integer p1 = (it1.hasNext()) ? it1.next() : null;
		Integer p2 = (it2.hasNext()) ? it2.next() : null;

		while (p1 != null && p2 != null) {
			if (p1 == p2) {
				l3.addLast(p1);
				p1 = buscar_diferente(p1, it1);
				p2 = buscar_diferente(p2, it2);
			}
			else {
				if (p1 < p2) {
					l3.addLast(p1);
					p1 = buscar_diferente(p1, it1);
				}
				else {
					l3.addLast(p2);
					p2 = buscar_diferente(p2, it2);
				}
			}
		}

		while (p1 != null) {
			l3.addLast(p1);
			p1 = buscar_diferente(p1, it1);
		}

		while (p2 != null) {
			l3.addLast(p2);
			p2 = buscar_diferente(p2, it2);
		}


		return l3;
	}


	private static Integer buscar_diferente(Integer p, Iterator<Integer> i) {
		Integer entero_original = p;
		try {
			while (p == entero_original) {
				p = i.next();
			}
		}
		catch (NoSuchElementException err) {
			p = null;
		}

		return p;
	}

	public static <E> void invertir (PositionList<E> l1) {
		try {
			if (!l1.isEmpty()) {
				Position<E> posPrimera = l1.first();
				Position<E> posUltima = l1.last();
				int contador = 0;
				
				while (contador < l1.size()/2 && posPrimera != null && posUltima != null) {
					E aux = posPrimera.element();
					l1.set(posPrimera, posUltima.element());
					l1.set(posUltima, aux);
					contador++;
					posPrimera = (posPrimera == l1.last())? null : l1.next(posPrimera);
					posUltima = (posUltima == l1.first()) ? null : l1.prev(posUltima);
				}
			}
		}
		catch (Exception err) {
			err.printStackTrace();
		}
	}

	public static void main(String args[]) {
		PositionList<Integer> l1 = new ListaSimplementeEnlazada<Integer>();
		PositionList<Integer> l2 = new ListaSimplementeEnlazada<Integer>();

		l1.addLast(5);
		l1.addLast(9);
		l1.addLast(10);
		l1.addLast(11);
		l1.addLast(12);
		l1.addLast(13);



		l2.addLast(0);
		l2.addLast(2);
		l2.addLast(3);
		l2.addLast(5);
		l2.addLast(9);
		l2.addLast(10);
		l2.addLast(11);


		System.out.println(l1);
		
		invertir(l1);
		
		System.out.println(l1);

	}
}

