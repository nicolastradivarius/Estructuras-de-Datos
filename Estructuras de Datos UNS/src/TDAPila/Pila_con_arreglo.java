/*
    Ac� deje una imagen mostrandote el error que me da StackTest al momento de querer testear Pila_con_arreglo: https://imgur.com/NcB6Js3
    (de paso se muestra c�mo est� organizado mi eclipse, capas tiene que ver con eso, no tengo idea)
*/

// Fj: Okey. Miro la foto.

//NG:  Al final, a qu� se debe el error de StackTest?
// FJ: Si vos miras la clase StackTest tal cual est� para descargar, viene configurada para funcionar dentro de un package
// FJ: llamado TDAPila. Habr�a que chequear varias cosas:
// FJ: 1) Cuando renombraste el package, se renombr� tambi�n la primera l�nea en el 

package TDAPila;

//FJ: Fijate Nicolas que ac� tenemos un primer error. Este import no corresponde. La clase EmptyStacjException figura como que 
// se debe importar del paquete TDAPila (ac� estar�a trayendo la clase del TDAPila.jar, ya que el paquete vos lo llamaste TDAPilaEnDesarrollo.)

//NG: entonces deber�a crear mi propia clase EmptyStackException? para as� poner 
//import TDAPilaEnDesarrollo.EmptyStackException

// FJ: S�. Todas las clases que utilices para implementar el TDA Pila las tenes que crear y agregar a tu paquete.
// FJ: Fijate que esto se corresponde con la consigna 2 del pr�ctico, es lo primero que se debe hacer.
// FJ: El �nico archivo que pueden copiar al pacjage TDAPila de ustedes, es la interfaz que les dejamos para descargar. 
// FJ: Todas las restantes clases e interfaces las deben implementar (no pueden importarlas desde el TDAPila.jar, y mucho
// FJ: menos desde Java)
//Ng: entendido.



/*
 * �qu� consideraciones especiales se deben tener al apilar un nuevo elemento?
 * 
 * -que la estructura usada para modelar la pila, en este caso un arreglo, no se encuentre vac�o
 * para prevalecer la idea de "conjunto infinito". Evidentemente no existen los arreglos infinitos,
 * por lo que cada cierta ocasi�n se debe efectuar una operaci�n que extienda su longitud.
 * 
 */
 
 //FJ: Excelente. A eso apunta la pregunta. Que al apilar uno tiene que estar seguro de que la implementaci�n cuente con espacio en 
 // el arreglo.

/**
 * Modela una pila en la forma de un arreglo
 * @author Nicol�s Gonz�lez
 */

public class Pila_con_arreglo<E> implements Stack<E> {

	protected int tamanyo;
	protected E[] arreglo;
	
	/**
	 * Constructor #1: crea un arreglo de tipo E de *max* slots.
	 * @param max Tama�o especificado de la pila. Debe ser un numero natural.
	 * @throws NegativeStackIndexException 
	 */
	
	
	/**
	 * Constructor #2: crea un arreglo de 10 slots por defecto.
	 * @throws NegativeStackIndexException Debido a que el tama�o especificado es una constante natural, esta excepci�n nunca ser� lanzada.
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
    
    //FJ: �Y si la pila est� vac�a? �Siempre hay que retornar el elemento en la posici�n tama�o?
    //FJ: Cuando la pila no tiene elementos, comienza a almacenar los mismos a partir de la posici�n 0; si se solicita top(),
    //FJ: �debe retornar el elemento en la posici�n 1?
    //Claro, confund� la cantidad total de los elementos de la pila con el tama�o de la misma
    // FJ: Excelente.
	public E top() throws EmptyStackException {
		if (tamanyo == 0) throw new EmptyStackException("La pila est� vac�a");
		
		return arreglo[tamanyo-1];
	} //corregido

	public void push(E element) {
		//FJ: �Es necesario definir una operaci�n extra para saber si el arreglo est� lleno?
		//FJ: �Qu� consulta simple se podr�a realizar para saber si la capacidad del arreglo es la misma que la cantidad 
		//FJ: de elementos almacenados en el arreglo?
		//FJ: Digo esto para evitar realizar una llamada a un m�todo que es m�s costosa que directamente realizar la comparaci�n.
		if (tamanyo == arreglo.length) { //corregido
		    // FJ: �Hace falta parametrizar el arreglo al m�todo resize()?
		    // FJ: �Arreglo no es un atributo de clase que puede accederse directamente en el m�todo resize()?
		  resize();
		        //Nicolas, te detengo dos segundos.
		        // No est� mal que utilices el m�todo resize() para que encapsule el comportamiento.
		        // Lo que estaba mal, digamos de esta forma, era parametrizar el arreglo.
		        // Ten� en cuenta que desde el m�todo resize() vos tenes acceso al atributo de clase arreglo, por eso no deb�as
		        // parametrizarlo. Pero haber implementado un m�todo a parte para realizar la operaci�n de redimensi�n es una 
		        // muy buena elecci�n.
		        //NG: no estar�a generando que el m�todo push tenga un mayor tiempo de ejecuci�n si dejo el resize(arreglo)
		        //en el m�todo resize(arreglo), copia la referencia de la variable arreglo a la variable arrayToResize, el tiempo all� es constante?
		        // FJ: No termino de comprender bien la pregunta. Pero insisto en el concepto; vos cuando sabes que debes realizar
		        // FJ: la redimensi�n, podes directamente llamar al m�todo resize().
		        // FJ: En el m�todo resize(), vos tenes acceso a todos los atributos de la clase Pila_con_arreglo; entre ellos,
		        // FJ: podes acceder al atributo arreglo. Teniendo acceso directo, podes modificarlo tal como lo hac�as.
		        //Entonces no ser�a necesario mandar a arreglo como parametro.
		        // FJ: Exactamente. Eso era lo que te intentaba indicar.
		     
	    }
		
		arreglo[tamanyo] = element;
		tamanyo++;
	}
		
		


	/**
	 * 
	 * @param arrayToResize
	 */
	
	// FJ: Eliminar el par�metro que no hace falta.
	// FJ: Al arreglo a redimensionar se puede acceder directamente ya que es un atributo de clase.
	@SuppressWarnings("unchecked")
	private void resize() {
		E[] auxArray = (E[]) new Object[arreglo.length*2];
		
		for (int i=0; i<arreglo.length; i++) {
			auxArray[i] = arreglo[i];
		}
		
		arreglo = auxArray;		
	}
    
    //FJ: Evitar�a el uso de estas operaciones.
    //FJ: Est� correctamente implementada pero es importante comenzar a tener en cuenta que las llamadas a funciones tienen un costo.
    //FJ: Como esta operaci�n computa una comparaci�n extremadamente simple, lo adecuado es que esta comparaci�n directamente 
    //FJ: se realice sobre un el que hace uso de isFull.
	/*private boolean isFull() {
		return tamanyo == arreglo.length;
	}*/ //metodo omitido

	public E pop() throws EmptyStackException {
		// FJ: �Es necesario hacer uso de la operaci�n isEmpty?
		// FJ: �No se puede estimar de forma simple si la pila est� vac�a mediante el atributo de clase tamanyo?
		// FJ: No es que est� mal, pero de nuevo, entendiendo que en general las llamadas a m�todos tienen un costo, en estos
		// FJ: escenarios es preferible consultar sobre el atributo tamanyo directamente y listo. 
		//corregido
		if (tamanyo == 0) throw new EmptyStackException("No se pudo desapilar. Pila vac�a.");
		
		E tope = arreglo[tamanyo-1];
		arreglo[tamanyo-1] = null;
		tamanyo--;
		return tope;
	}
	
	@SuppressWarnings("unchecked")
	public void invertir() throws EmptyStackException {
		if (tamanyo == 0) throw new EmptyStackException("ERROR: Pila vac�a");
		
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



