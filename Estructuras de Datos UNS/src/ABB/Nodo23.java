package ABB;

public class Nodo23<E> {

	private Nodo23<E> padre;
	private E k1;
	private E k2;
	private int altura;
	private boolean eliminado;
	private Nodo23<E> left, mid, right;
	
	public Nodo23(E k1, E k2) {
		this.k1 = k1;
		this.k2 = k2;
		eliminado = false;
		altura = 0;
		padre = left = mid = right = null;
	}
	
	public E getK1() {
		return k1;
	}
	
	public E getRotulo() {
		E retorno = null;
		
		if (k1 == null && k2 != null)
			retorno = k2;
		else if (k2 == null && k1 != null)
			retorno = k1;
		
		return retorno;
		
	}

	public E getK2() {
		return k2;
	}

	public void setK1(E k1) {
		this.k1 = k1;
	}

	public void setK2(E k2) {
		this.k2 = k2;
	}

	public Nodo23<E> getPadre() {
		return padre;
	}
	public int getAltura() {
		return altura;
	}
	public boolean isEliminado() {
		return eliminado;
	}
	public Nodo23<E> getLeft() {
		return left;
	}
	public Nodo23<E> getMid() {
		return mid;
	}
	public Nodo23<E> getRight() {
		return right;
	}
	public void setPadre(Nodo23<E> padre) {
		this.padre = padre;
	}
	public void setAltura(int altura) {
		this.altura = altura;
	}
	public void setEliminado(boolean eliminado) {
		this.eliminado = eliminado;
	}
	public void setLeft(Nodo23<E> left) {
		this.left = left;
	}
	public void setMid(Nodo23<E> mid) {
		this.mid = mid;
	}
	public void setRight(Nodo23<E> right) {
		this.right = right;
	}
}
