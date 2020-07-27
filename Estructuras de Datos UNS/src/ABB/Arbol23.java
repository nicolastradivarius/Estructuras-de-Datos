package ABB;

import java.util.Comparator;

public class Arbol23<E> {

	protected Nodo23<E> raiz;
	protected Comparator<E> comp;

	public Arbol23(Comparator<E> comp) {
		this.comp = comp;
	}

	public boolean recuperar(E clave) {
		boolean esta = false;
		return recuperar_aux(raiz, clave, esta);
	}

	private boolean recuperar_aux(Nodo23<E> R, E clave, boolean esta) {
		if (R.getRotulo() == clave) {
			esta = true;
		}
		else if (R.getAltura() == 1)
			esta = false;

		else {
			if (R.getRotulo() != null) {
				E k = R.getRotulo();
				int comparacion = comp.compare(clave, k);

				if (comparacion == 0)
					esta = true;
				else if (comparacion < 0) {
					esta = recuperar_aux(R.getLeft(), clave, esta);
				}
				else {
					esta = recuperar_aux(R.getRight(), clave, esta);
				}
			}
			else {
				if (R.getK1() != null && R.getK2() != null) {
					E k1 = R.getK1();
					E k2 = R.getK2();

					int comparacion_k1 = (comp.compare(clave, k1));
					int comparacion_k2 = (comp.compare(clave, k2));

					if (comparacion_k1 < 0) {
						esta = recuperar_aux(R.getLeft(), clave, esta);
					}
					else {
						if (comparacion_k2 < 0) {
							esta = recuperar_aux(R.getMid(), clave, esta);
						}
						else {
							esta = recuperar_aux(R.getRight(), clave, esta);
						}
					}
				}
			}
		}
		
		return esta;
	}
}
