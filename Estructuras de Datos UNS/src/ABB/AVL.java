package ABB;

import java.util.Comparator;

public class AVL<E> {

	private NodoAVL<E> raiz;
	private Comparator<E> comp;

	public AVL(Comparator<E> comp) {
		raiz = new NodoAVL<E>(null);
		this.comp = comp;
	}

	//recorre el arbol recursivamente desde la raiz con un metodo auxiliar
	public void insert(E x) {
		insert_aux(raiz, x);
	}

	private void insert_aux(NodoAVL<E> p, E item) {
		//llegue a un dummy
		if (p.getRotulo() == null) {
			//seteo la altura y le hago dos hijos
			p.setRotulo(item);
			p.setAltura(1);
			p.setHijoizq(new NodoAVL<E>(null));
			p.setHijoder(new NodoAVL<E>(null));
			p.getHijoizq().setPadre(p);
			p.getHijoder().setPadre(p);
		}
		else {
			//comparo el item con el rotulo de la raiz del subarbol que estoy mirando
			int comparacion = comp.compare(item, p.getRotulo());
			//estoy insertando un nodo que ya elimine antes --> lo deselimino
			if (comparacion == 0) {
				p.setEliminado(false);
			}
			else if (comparacion < 0) {
				//inserto recursivamente en el hijo izquierdo y a la vuelta rebalanceo
				insert_aux(p.getHijoizq(), item);

				//veo si el árbol se desbalanceó
				if (Math.abs(p.getHijoizq().getAltura() - p.getHijoder().getAltura()) > 1) {
					//sí se desbalanceó. Entonces tengo dos casos posibles: caso I o caso II
					//como llegué acá, sé que item < x (raiz del arbol)
					//debo testear si (item < y) o (item > y) siendo y el hijoizq de x
					//si (item < y) ==> caso I (rotacion I)
					//sino ==> rotacion II

					E y = p.getHijoizq().getRotulo();

					//obtengo el resultado de comparar el item con Y (hijoIzq de x)
					int comp_item_y = comp.compare(item, y);

					if (comp_item_y < 0) //(item < y)
						rotacion_I(p);
					else
						rotacion_II(p); //(item > y)
				}
			} else { //comparacion > 0
				//Caso simetrico pero insertando hacia la derecha
				//luego testeo por rotaciones (III) e (IV) para ver cual se hace
				insert_aux(p.getHijoder(), item);
				
				if (Math.abs(p.getHijoizq().getAltura() - p.getHijoder().getAltura()) > 1 ) {
					//rebalancear mediante rotaciones (III) o (IV)
					//si estoy acá ==> item > x, debo testear si (item <y) o (item > y)
					//si (item < y) ==> z es hijo izq de y ==> rotacion(IV)
					//si (item > y) ==> z es hijo der de y ==> rotacion(III)
					
					E y = p.getHijoder().getRotulo();
					
					int comp_item_y = comp.compare(item, y);
					
					if (comp_item_y < 0)
						rotacion_IV(p); //(item < y)
					else
						rotacion_III(p); //(item > y)
				}
				
			}
			
			//actualizo la altura de p
			p.setAltura(max(p.getHijoizq().getAltura(), p.getHijoder().getAltura()) +1);;
		}
	}

	private void rotacion_I (NodoAVL<E> p) {
		NodoAVL<E> x = p;
		NodoAVL<E> y = p.getHijoizq();
		NodoAVL<E> z = y.getHijoizq();
		NodoAVL<E> raiz_t1 = z.getHijoizq();
		NodoAVL<E> raiz_t2 = z.getHijoder();
		NodoAVL<E> raiz_t3 = y.getHijoder();
		NodoAVL<E> raiz_t4 = x.getHijoder();
		
		x.setHijoizq(raiz_t3);
		y.setHijoder(x);
		
		if (x.getPadre() != null) {
			if (x.getPadre().getHijoizq() == x)
				x.getPadre().setHijoizq(y);
			else
				x.getPadre().setHijoder(y);
		}
		
		y.setPadre(x.getPadre());
		z.setPadre(y);
		x.setPadre(y);
	}
	
	private void rotacion_II (NodoAVL<E> p) {
		NodoAVL<E> x = p;
		NodoAVL<E> y = x.getHijoizq();
		NodoAVL<E> z = y.getHijoder();
		NodoAVL<E> raiz_t1 = y.getHijoizq();
		NodoAVL<E> raiz_t2 = z.getHijoizq();
		NodoAVL<E> raiz_t3 = z.getHijoder();
		NodoAVL<E> raiz_t4 = x.getHijoder();
		
		y.setHijoder(raiz_t2);
		x.setHijoizq(raiz_t3);
		z.setHijoizq(y);
		z.setHijoder(x);
		
		if (x.getPadre() != null) {
			if (x.getPadre().getHijoizq() == x)
				x.getPadre().setHijoizq(z);
			else
				x.getPadre().setHijoder(z);
		}
		
		z.setPadre(x.getPadre());
		x.setPadre(z);
		y.setPadre(z);
		
	}
	
	private void rotacion_III (NodoAVL<E> p) {
		NodoAVL<E> x = p;
		NodoAVL<E> y = p.getHijoder();
		NodoAVL<E> z = y.getHijoder();
		NodoAVL<E> raiz_t1 = x.getHijoizq();
		NodoAVL<E> raiz_t2 = y.getHijoizq();
		NodoAVL<E> raiz_t3 = z.getHijoizq();
		NodoAVL<E> raiz_t4 = z.getHijoder();
		
		
		x.setHijoder(raiz_t2);
		y.setHijoizq(x);
		
		if (x.getPadre() != null) {
			if (x.getPadre().getHijoizq() == x)
				x.getPadre().setHijoizq(y);
			else
				x.getPadre().setHijoder(y);
		}
		
		y.setPadre(x.getPadre());
		z.setPadre(y);
		x.setPadre(y);
	}
	
	private void rotacion_IV (NodoAVL<E> p) {
		NodoAVL<E> x = p;
		NodoAVL<E> y = x.getHijoder();
		NodoAVL<E> z = y.getHijoizq();
		NodoAVL<E> raiz_t1 = x.getHijoizq();
		NodoAVL<E> raiz_t2 = z.getHijoizq();
		NodoAVL<E> raiz_t3 = z.getHijoder();
		NodoAVL<E> raiz_t4 = y.getHijoder();
		
		x.setHijoder(raiz_t2);
		y.setHijoizq(raiz_t3);
		
		z.setHijoizq(x);
		z.setHijoder(y);
		
		if (x.getPadre() != null) {
			if (x.getPadre().getHijoizq() == x)
				x.getPadre().setHijoizq(z);
			else
				x.getPadre().setHijoder(z);
		}
		
		z.setPadre(x.getPadre());
		x.setPadre(z);
		y.setPadre(z);
	}

	public int max(int i, int j) {
		return i>j ? i : j;
	}
}
