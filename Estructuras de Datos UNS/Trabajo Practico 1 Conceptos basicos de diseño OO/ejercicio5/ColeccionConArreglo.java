package ejercicio5;
// FJ: Trabajen un rato, y los leo en unos minutos.

public class ColeccionConArreglo<E> implements Coleccion<E> {

    protected E array[];
    protected int cant=0;
    
    
    public ColeccionConArreglo(){
        //LA:Arreglo de 10 elementos cuando se llama a este constructor
        array=(E[])new Object[10];
    } //he aqui mi duda, no debería enviar ningún "index" para definir el tamaño?
    
    public ColeccionConArreglo(int indice) throws NegativeArraySizeException{
        //LA:Arreglo de "indice" elementos cuando se llama a este constructor con un parametro de tipo entero
        if (indice < 0) throw new NegativeArraySizeException("Índice negativo: no se pueden crear arreglos con tamaño negativo");
        array=(E[])new Object[indice];
        // FJ: ¿Qué sucede si indice es negativo?
        //LA: tira excepcion de Array negativo NegativeArraySizeException
    }
    //LA: en el arreglo si o si hay que especificar un tamaño, eso creo. O sino de ultima establecer uno desde inicio
    // FJ: no necesariamente el cliente de la clase Coleccion_con_arreglo debe definir su capacidad inicial. Ustedes como programadores de la clase podrían decidir una capacidad inicial, sin la necesidad de darle esa responsabilidad al cliente, que en definitiva, desea usar una implementación de Coleccion.
    
    
    // FJ. OJO AQUÍ! 
    // FJ: La interfaz colección, en su operación insertar(), ¿indica que la operación puede lanzar una excepcion?
    //LA:Claro, habria que ver la Coleccion si tiene o no la operacion, si no tiene la operacion no deberiamos lanzar la excepcion
    // FJ: Exactamente! La interfaz NO deberia considerar lanzar excepciones referentes a la capacidad, dado que lo que esperamos es que, las coleccione sean consideradas conjuntos (por definición son infinitos y no tienen elementos repetidos)
    public void insertar(E elemento){
    	    //creo que es mejor implementar el pertenece primero -_-
    	    //el primer elemento se agrega en 0, ir aumentando a partir de cada insertar
    	    //que pasa si el arreglo ya tiene elementos?
    	    //que pasa si se quiere insertar un elemento cuando el arreglo esta lleno? // FJ: para garantizar que la colección sigue siendo una colección, deberán realizar un resize(), esto es, agrandar la capacidad del arreglo.
    	  
      if(cant==array.length-1){
    	  
          E[]nuevoarreglo=(E[]) new Object[array.length+10];
    	      //los anteriores elementos de array los tengo que insertar en el nuevoarreglo
    	      //igual despues me voy a referir a array y no a nuevoarreglo 
    	      //siempre se me da por el codigo "spagetthi" o como se escriba :C, se me hace mucho agregar nuevos metodos :^(
    	      
    	      //NG: me cuesta asimilar todo eso porque parece que es muy rebuscado y consume muchos recursos
    	     //el tema es que necesitas agregar todos los elementos del arreglo anterior, y la unica manera es con un for
    	     //a lo que me refiero es que si o si tenes que recorrer todo el arreglo, tambien podes hacer un while, pero de igual manera tenes que recorrer todo el arreglo para "agarrar" cada elemento
    	     //creo que no existe un metodo para "agrandar" el arreglo mismo
    	     
    	     //capas aca hay algo que nos ayude: https://stackoverflow.com/questions/13197702/resize-an-array-while-keeping-current-elements-in-java
    	     //justo en el primer punto dice "Create a new array of the desired size, and copy the contents from the original array to the new array, using.." crear un nuevo arreglo del tamaño deseado (en este caso indice +10) y copiar el contenido del arreglo original al nuevo arreglo usando tal metodo, pero creo que el metodo seria casi lo mismo que esto.
          for(int i=0;i<array.length;i++){
              nuevoarreglo[i]=array[i];
          }
    	      array=nuevoarreglo;
    	  }
    	    if(!pertenece(elemento)){
    	        array[cant]=elemento;
    	        cant++;
    	    }
    	    //se podría lanzar una excepcion cuando el arreglo esta llenO? (como para usar algo de lo nuevo xd)
    	    //habria que agregarlo a la interface Coleccion<E> en el metodo insertar que lanze una excepcion de "ArregloLlenoException"
    	    //ah genial
    	}
    	public void eliminar(E elemento){
    	    
    	    boolean encontre = false;
    	    
    	    for (int i=0; i < array.length && !encontre; i++){
    	        
    	        if (array[i].equals(elemento)) {
    	        	
    	        	encontre = true;
    	        	array[i] = null;
    	        	
    	        	for (int j=i; j < array.length-1; j++) {
    	        		
    	        		array[j] = array[j+1];
    	        		array[j+1] = null;
    	        	}
    	        } //supongo que así está bien
    	    }
    	        
    	}
    	
    	// FJ: La operación está correctamente implementada. Una pregunta interesante sería: ¿se debería usar == o equals? ¿Por qué? 
    	// FJ: Analicenlo un rato a ver qué les parece.
        //NG: tendría que ser equals porque el arreglo está compuesto por objetos, y el parametro es un objeto, y la idea es corroborar que el objeto parámetro con su estado interno esté o no allí
        
        // En realidad podrían usar cualquiera de ambas opciones; obviamente que la política utilizada luego hará que el cliente use su implementación o no dependiendo el comportamiento que desee. 
        // por ejemplo, dos Personas p1 y p2 tiene como atributo de instancia DNI con el mismo valor, por ejemplo, 15151515. 
        // (P1 == P2)? no; (P1.equals(p2))? Posiblemente SI, si se considera la política que dos personas son iguales si tienen el mismo DNI.
        // De aquí la importancia a la hora de definir la implementación: de ustedes depende el comportamiento que deseen, porque las dos posibles souciones son válidas, pero tiene que quedar definidas.
        // Yo utilizaría la versión de equals, que le permite al cliente definir cómo quiere que los elementos sean comparados.
        //en el caso de usar equals, habría que hacer el casting?
        // FJ: No. Tené en cuenta que por definición, el arreglo es de E (uno lo inicializa con Objects pero sigue siendo de E).
        //NG: al realizar el equals, sí o sí el parametro a comparar debe ser de tipo Object, de ser de tipo E eclipse me lanza error.
        
    	public boolean pertenece(E elemento){

    	    int i=0;
    	    while(i < array.length){
    	        if(array[i]==elemento){
    	           	return true;
    	        }
    	        i++;
    	    }
    	    return false;
    	}
    	
    	public boolean equals(Object elemento){
    	       	   
    	   for (int i=0; i < cant; i++){
    	       
    	       if (array[i].equals(elemento))
    	            return true;
    	   }
    	   
    	   return false;
    	   
    	}
    	
    	public String toString() {
    		
    		String salida = "(";
    		
    		for (int i=0; i < array.length; i++) {
    			
    			salida += array[i]+" ";
    		}
    		
    		salida += ")";
    		return salida;
    	}
}