package ejercicio5;

public class TesterColeccionConArreglo {

	public static void main(String args[]) {
		
		Coleccion<String> c1 = new ColeccionConArreglo<String>();
		
		
		c1.insertar("Nicolas");
		c1.insertar("luciano");
		c1.insertar("joaquin");
		c1.insertar("boca");
		
		System.out.println(c1.toString());
		
		c1.eliminar("luciano");
		
		System.out.println(c1.toString());
		
		System.out.println(c1.pertenece("boca"));
		
		c1.insertar("haha");
		c1.insertar("hehe");

		c1.insertar("hoho");

		c1.insertar("hihi");
		c1.insertar("kkkk");
		c1.insertar("asjksa");
		c1.insertar("F");
		
		System.out.println(c1.toString());


	}
}
