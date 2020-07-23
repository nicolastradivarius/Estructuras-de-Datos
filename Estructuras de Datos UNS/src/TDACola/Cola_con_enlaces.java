package TDACola;

public class Cola_con_enlaces<E> implements Queue<E>{

	class Nodo<T> {

		private T elemento;
		private Nodo<T> siguiente;
		
		// Constructores:
		public Nodo(T item, Nodo<T> sig){
			elemento=item; siguiente=sig; 
		}
		
		public Nodo(T item) { 
			this(item,null); 
		}
		
		// Setters:
		
		public void setElemento(T elemento) {
			this.elemento = elemento;
		}
			
		public void setSiguiente(Nodo<T> siguiente) {
			this.siguiente = siguiente;
		}
		
		// Getters:
		
		public T getElemento() { 
			return elemento;	
		}
		
		public Nodo<T> getSiguiente() { 
			return siguiente;
		}
		
	}
	protected Nodo<E> head, tail;
	protected int tamanyo;
	
	public Cola_con_enlaces() {
		head = tail = null;
		tamanyo = 0;
	}
	@Override
	public int size() {
		return tamanyo;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return tamanyo == 0;
	}

	@Override
	public E front() throws EmptyQueueException {
		if (tamanyo == 0) throw new EmptyQueueException("ERROR: lista vacía");
		return head.getElemento();
	}

	@Override
	public void enqueue(E element) {
		Nodo<E> nodo = new Nodo<E>(element);
		nodo.setSiguiente(null);
		
		if (tamanyo == 0)
			head = nodo;
		else
			tail.setSiguiente(nodo);
		tail = nodo;
		tamanyo++;
	}

	@Override
	public E dequeue() throws EmptyQueueException {
		if (tamanyo == 0)
			throw new EmptyQueueException("ERROR: Cola Vacía");
		
		E temporal = head.getElemento();
		head = head.getSiguiente();
		tamanyo--;
		if (tamanyo == 0)
			tail = null;
		return temporal;
	}

	
}
