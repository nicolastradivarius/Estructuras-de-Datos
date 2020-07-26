package ABB;

public class NodoAVL<E> {
	
	private NodoAVL<E> padre;
	private E rotulo;
	private int altura;
	private boolean eliminado;
	private NodoAVL<E> hijoizq, hijoder;
	
	public NodoAVL(E rotulo) {
		altura = 0;
		this.rotulo = rotulo;
		eliminado = false;
		padre = hijoizq = hijoder = null;
	}

	public NodoAVL<E> getPadre() {
		return padre;
	}

	public E getRotulo() {
		return rotulo;
	}

	public int getAltura() {
		return altura;
	}

	public boolean isEliminado() {
		return eliminado;
	}

	public NodoAVL<E> getHijoizq() {
		return hijoizq;
	}

	public NodoAVL<E> getHijoder() {
		return hijoder;
	}

	public void setPadre(NodoAVL<E> padre) {
		this.padre = padre;
	}

	public void setRotulo(E rotulo) {
		this.rotulo = rotulo;
	}

	public void setAltura(int altura) {
		this.altura = altura;
	}

	public void setEliminado(boolean eliminado) {
		this.eliminado = eliminado;
	}

	public void setHijoizq(NodoAVL<E> hijoizq) {
		this.hijoizq = hijoizq;
	}

	public void setHijoder(NodoAVL<E> hijoder) {
		this.hijoder = hijoder;
	}

}
