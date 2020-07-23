/*
    Acá deje una imagen mostrandote el error que me da StackTest al momento de querer testear Pila_con_arreglo: https://imgur.com/NcB6Js3
    (de paso se muestra cómo está organizado mi eclipse, capas tiene que ver con eso, no tengo idea)
*/

// Fj: Okey. Miro la foto.

//NG:  Al final, a qué se debe el error de StackTest?
// FJ: Si vos miras la clase StackTest tal cual está para descargar, viene configurada para funcionar dentro de un package
// FJ: llamado TDAPila. Habría que chequear varias cosas:
// FJ: 1) Cuando renombraste el package, se renombró también la primera línea en el 

package TDAPila;

//FJ: Fijate Nicolas que acá tenemos un primer error. Este import no corresponde. La clase EmptyStacjException figura como que 
// se debe importar del paquete TDAPila (acá estaría trayendo la clase del TDAPila.jar, ya que el paquete vos lo llamaste TDAPilaEnDesarrollo.)

//NG: entonces debería crear mi propia clase EmptyStackException? para así poner 
//import TDAPilaEnDesarrollo.EmptyStackException

// FJ: Sí. Todas las clases que utilices para implementar el TDA Pila las tenes que crear y agregar a tu paquete.
// FJ: Fijate que esto se corresponde con la consigna 2 del práctico, es lo primero que se debe hacer.
// FJ: El único archivo que pueden copiar al pacjage TDAPila de ustedes, es la interfaz que les dejamos para descargar. 
// FJ: Todas las restantes clases e interfaces las deben implementar (no pueden importarlas desde el TDAPila.jar, y mucho
// FJ: menos desde Java)
//Ng: entendido.



/*
 * ¿qué consideraciones especiales se deben tener al apilar un nuevo elemento?
 * 
 * -que la estructura usada para modelar la pila, en este caso un arreglo, no se encuentre vacío
 * para prevalecer la idea de "conjunto infinito". Evidentemente no existen los arreglos infinitos,
 * por lo que cada cierta ocasión se debe efectuar una operación que extienda su longitud.
 * 
 */
 
 //FJ: Excelente. A eso apunta la pregunta. Que al apilar uno tiene que estar seguro de que la implementación cuente con espacio en 
 // el arreglo.

/**
 * Modela una pila en la forma de un arreglo
 * @author Nicolás González
 */

public class Pila_con_arreglo<E> implements Stack<E> {

	protected int tamanyo;
	protected E[] arreglo;
	
	/**
	 * Constructor #1: crea un arreglo de tipo E de *max* slots.
	 * @param max Tamaño especificado de la pila. Debe ser un numero natural.
	 * @throws NegativeStackIndexException 
	 */
	
	
	/**
	 * Constructor #2: crea un arreglo de 10 slots por defecto.
	 * @throws NegativeStackIndexException Debido a que el tamaño especificado es una constante natural, esta excepción nunca será lanzada.
	 */
	@SuppressWarnings("unchecked")
	public Pila_con_arreglo() {
		
		arreglo = (E[]) new Object[10];
	}
	

	public int size() {
		return tamanyo;
	}

	public boolean isEmpty() {
		return tamanyo == 0;
	}
    
    //FJ: ¿Y si la pila está vacía? ¿Siempre hay que retornar el elemento en la posición tamaño?
    //FJ: Cuando la pila no tiene elementos, comienza a almacenar los mismos a partir de la posición 0; si se solicita top(),
    //FJ: ¿debe retornar el elemento en la posición 1?
    //Claro, confundí la cantidad total de los elementos de la pila con el tamaño de la misma
    // FJ: Excelente.
	public E top() throws EmptyStackException {
		if (tamanyo == 0) throw new EmptyStackException("La pila está vacía");
		
		return arreglo[tamanyo-1];
	} //corregido

	public void push(E element) {
		//FJ: ¿Es necesario definir una operación extra para saber si el arreglo está lleno?
		//FJ: ¿Qué consulta simple se podría realizar para saber si la capacidad del arreglo es la misma que la cantidad 
		//FJ: de elementos almacenados en el arreglo?
		//FJ: Digo esto para evitar realizar una llamada a un método que es más costosa que directamente realizar la comparación.
		if (tamanyo == arreglo.length) { //corregido
		    // FJ: ¿Hace falta parametrizar el arreglo al método resize()?
		    // FJ: ¿Arreglo no es un atributo de clase que puede accederse directamente en el método resize()?
		  resize();
		        //Nicolas, te detengo dos segundos.
		        // No está mal que utilices el método resize() para que encapsule el comportamiento.
		        // Lo que estaba mal, digamos de esta forma, era parametrizar el arreglo.
		        // Tené en cuenta que desde el método resize() vos tenes acceso al atributo de clase arreglo, por eso no debías
		        // parametrizarlo. Pero haber implementado un método a parte para realizar la operación de redimensión es una 
		        // muy buena elección.
		        //NG: no estaría generando que el método push tenga un mayor tiempo de ejecución si dejo el resize(arreglo)
		        //en el método resize(arreglo), copia la referencia de la variable arreglo a la variable arrayToResize, el tiempo allí es constante?
		        // FJ: No termino de comprender bien la pregunta. Pero insisto en el concepto; vos cuando sabes que debes realizar
		        // FJ: la redimensión, podes directamente llamar al método resize().
		        // FJ: En el método resize(), vos tenes acceso a todos los atributos de la clase Pila_con_arreglo; entre ellos,
		        // FJ: podes acceder al atributo arreglo. Teniendo acceso directo, podes modificarlo tal como lo hacías.
		        //Entonces no sería necesario mandar a arreglo como parametro.
		        // FJ: Exactamente. Eso era lo que te intentaba indicar.
		     
	    }
		
		arreglo[tamanyo] = element;
		tamanyo++;
	}
		
		


	/**
	 * 
	 * @param arrayToResize
	 */
	
	// FJ: Eliminar el parámetro que no hace falta.
	// FJ: Al arreglo a redimensionar se puede acceder directamente ya que es un atributo de clase.
	@SuppressWarnings("unchecked")
	private void resize() {
		E[] auxArray = (E[]) new Object[arreglo.length*2];
		
		for (int i=0; i<arreglo.length; i++) {
			auxArray[i] = arreglo[i];
		}
		
		arreglo = auxArray;		
	}
    
    //FJ: Evitaría el uso de estas operaciones.
    //FJ: Está correctamente implementada pero es importante comenzar a tener en cuenta que las llamadas a funciones tienen un costo.
    //FJ: Como esta operación computa una comparación extremadamente simple, lo adecuado es que esta comparación directamente 
    //FJ: se realice sobre un el que hace uso de isFull.
	/*private boolean isFull() {
		return tamanyo == arreglo.length;
	}*/ //metodo omitido

	public E pop() throws EmptyStackException {
		// FJ: ¿Es necesario hacer uso de la operación isEmpty?
		// FJ: ¿No se puede estimar de forma simple si la pila está vacía mediante el atributo de clase tamanyo?
		// FJ: No es que esté mal, pero de nuevo, entendiendo que en general las llamadas a métodos tienen un costo, en estos
		// FJ: escenarios es preferible consultar sobre el atributo tamanyo directamente y listo. 
		//corregido
		if (tamanyo == 0) throw new EmptyStackException("No se pudo desapilar. Pila vacía.");
		
		E tope = arreglo[tamanyo-1];
		arreglo[tamanyo-1] = null;
		tamanyo--;
		return tope;
	}
	
	@SuppressWarnings("unchecked")
	public void invertir() throws EmptyStackException {
		if (tamanyo == 0) throw new EmptyStackException("ERROR: Pila vacía");
		
		if (tamanyo > 1) {
			E[] arregloInv = (E[]) new Object[tamanyo];
			int posicion = 0;
			
			for (int t=tamanyo-1; t>=0; t--) {
				arregloInv[posicion++] = arreglo[t];
			}
			arreglo = arregloInv;
		}
	}
	
	public static void main(String args[]) {
		
		Stack<Integer> s = new Pila_con_arreglo<Integer>();
		s.push(3);
		s.push(4);
		System.out.println(s.toString());
		
		try {
			((Pila_con_arreglo<Integer>) s).invertir();
			System.out.println(s.toString());
		} catch (EmptyStackException e) {
			
			System.out.println(e.getMessage());
		}
	}
}



