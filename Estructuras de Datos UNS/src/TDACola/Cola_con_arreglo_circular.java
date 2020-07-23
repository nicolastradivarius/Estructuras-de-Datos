package TDACola;

/**
 * Modela la implementación de la interfaz {@link Queue} a través de un arreglo con la característica de que posee <b>circularidad</b>,
 * esto es, los elementos se insertan al principio (si hay un lugar libre) en el caso que se llegue al final del mismo. <br>
 * Garantiza la posibilidad de insertar cualquier cantidad de elementos pues el arreglo se redimensiona antes de llenarse.
 * @author Nicolás González
 * @see Queue
 * @param <E> Tipo de elemento de los componentes del arreglo.
 */
public class Cola_con_arreglo_circular<E> implements Queue<E> {

	protected int frente;
	protected int rabo;
	protected E[] arregloCircular;

	/**
	 * Crea un arreglo con 10 celdas libres y sus dos índices <b>frente</b> y <b>rabo</b> en la posición 0.
	 */
	@SuppressWarnings("unchecked")
	public Cola_con_arreglo_circular(){
		frente=rabo=0;
		arregloCircular = (E[]) new Object[10];
	}

	@Override
	public int size() {
		return (arregloCircular.length - frente + rabo) % arregloCircular.length;
	}

	@Override
	public boolean isEmpty() {
		return frente == rabo;
	}

	@Override
	public E front() throws EmptyQueueException {
		if (frente==rabo) throw new EmptyQueueException("ERROR: Cola vacía");

		return arregloCircular[frente];
	}

	@Override
	public void enqueue(E element) {
		if (size() == (arregloCircular.length-1))
			resize();

		arregloCircular[rabo] = element;
		rabo = (rabo+1) % arregloCircular.length;
	}

	/**
	 * Redimensiona el arreglo circular duplicando su cantidad de celdas. Requiere que la cantidad de celdas del arreglo a redimensionar menos uno es igual a la cantidad de elementos.
	 * Reordena los elementos existentes de manera tal que los elementos ordenados a partir de <b>frente</b> hasta <B>rabo</B>
	 * pasan a estar ordenados a partir de 0. 
	 */
	@SuppressWarnings("unchecked")
	private void resize() {
		E[] resizedArray = (E[]) new Object[size()*2];
		//Rindex será el índice a partir del cual se insertarán los elementos del arreglo a redimensionar
		int Rindex = 0;

		//Comienza a insertar en el arreglo redimensionado los elementos a partir del frente (rabo+1) hasta el final del arreglo original.
		for (int i=rabo+1; i<arregloCircular.length; i++) {
			resizedArray[Rindex] = arregloCircular[i];
			Rindex++;
		}

		//Comienza a insertar en el arreglo redimensionado los elementos a partir del inicio del arreglo hasta el rabo.
		for(int j=0; j<rabo; j++) {
			resizedArray[Rindex] = arregloCircular[j];
			Rindex++;
		}

		arregloCircular = resizedArray;
		frente=0;
		rabo=Rindex;
	}

	@Override
	public E dequeue() throws EmptyQueueException {
		if (isEmpty()) throw new EmptyQueueException("ERROR: Cola vacía");

		E temporal = arregloCircular[frente];
		arregloCircular[frente] = null;
		frente = (frente+1) % arregloCircular.length;
		return temporal;
	}
}