package TDAArbolBinario;

public class BTNodo<T> implements Position<T> {

	private T elemento;
	private BTNodo<T> hijoIzquierdo;
	private BTNodo<T> hijoDerecho;
	private BTNodo<T> padre;
	
	public BTNodo (T elemento){
		this.elemento = elemento;
		hijoIzquierdo = null;
		hijoDerecho = null;
		padre = null;
	}
	
	public BTNodo(T elemento, BTNodo<T> padre) {
		this.elemento = elemento;
		this.padre = padre;
		hijoIzquierdo = null;
		hijoDerecho = null;
	}
	
	public BTNodo(T elemento, BTNodo<T> padre, BTNodo<T> hijoIzq, BTNodo<T> hijoDer) {
		this.elemento = elemento;
		this.hijoIzquierdo = hijoIzq;
		this.hijoDerecho = hijoDer;
		this.padre = padre;
	}
	@Override
	public T element() {
		return elemento;
	}

	public BTNodo<T> getPadre() {
		return padre;
	}

	public BTNodo<T> getHijoIzquierdo() {
		return hijoIzquierdo;
	}

	public BTNodo<T> getHijoDerecho() {
		return hijoDerecho;
	}

	public void setPadre(BTNodo<T> padre) {
		this.padre = padre;
	}

	public void setHijoIzquierdo(BTNodo<T> hijoIzq) {
		this.hijoIzquierdo = hijoIzq;
	}

	public void setHijoDerecho(BTNodo<T> hijoDer) {
		this.hijoDerecho = hijoDer;
	}

	public void setElement(T element) {
		elemento = element;
	}
	
	public String toString() {
		return "" + elemento;
	}
	
	public BTNodo<T> clone(){
		return new BTNodo<T>(elemento, padre, hijoIzquierdo, hijoDerecho);
	}
}