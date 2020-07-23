package ABB;

public class NodoABB<E> {

	protected E rotulo;
	protected NodoABB<E> padre, hi, hd;
	
	public NodoABB(E r, NodoABB<E> p) {
		rotulo = r;
		padre = p;
	}
	
	public void setRotulo(E r) {
		rotulo = r;
	}
	
	public void setParent(NodoABB<E> p) {
		padre = p; 
	}
	
	public void setLeft(NodoABB<E> i) {
		hi = i;
	}
	
	public void setRight(NodoABB<E> d) {
		hd = d;
	}
	
	public E getRotulo() {
		return rotulo;
	}
	
	public NodoABB<E> getParent(){
		return padre;
	}
	
	public NodoABB<E> getLeft(){
		return hi;
	}
	
	public NodoABB<E> getRight(){
		return hd;
	}
}
