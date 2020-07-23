package TDADiccionario;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;

import java.util.Random;
import java.util.Vector;


/**
 * Dictionary Test - Estructura de Datos (DCIC-UNS).
 * Define casos de pruebas a aplicar sobre el TDA Dictionary<K,V>.
 * @author Estructuras de Datos, DCIC-UNS.
 * @version 1.0 - María Lujan Ganuza (2013-2018) 
 * @version 2.0 - Federico Joaquín (2019-2020) 
 * @see Dictionary<K,V>
 */
public class DictionaryTest {

	private Dictionary<Integer, Integer> s;

	/*
	 * Retorna la clase tipo Dictionary a testear.
	 * Indique la clase que implementa Dictionary<K,V> que desea testear.
	 * Por ejemplo: DictionaryOpenHash<Integer,Integer> o DictionaryClosedHash<Integer,Integer>.
	 */
	private Dictionary<Integer, Integer> getDictionary() {
		return new Diccionario_con_listaOrdenada<Integer, Integer>();
	}

	/*
	 * Inicializa la lista antes de cada test individual
	 */
	@Before
	public void setUp() {
		s = getDictionary();
	}

	/* _______________________TESTS METODO size()_____________________________*/

	@Test
	public void size() {
		Entry<Integer, Integer> en;

		// Testeando si insert() actualiza el size correctamente.
		assertTrue("Tamaño de Diccionario justo después de ser creada != 0", s.size() == 0);

		try {

			s.insert(0, 0);
			assertTrue("Tamaño de Diccionario luego de insertar una vez != 1", s.size() == 1);

			s.insert(0, 1);
			assertTrue("Tamaño de Diccionario luego de insertar dos veces != 2", s.size() == 2);

			s.insert(1, 2);
			assertTrue("Tamaño de Diccionario luego de insertar tres veces != 3", s.size() == 3);

			// Testeando que el método remove() actualice el size, y que find(K key) no lo haga.
			s.findAll(0);
			assertTrue("Tamaño de Diccionario cambió al invocar get()", s.size() == 3);

			en=s.find(0);
			assertTrue("Tamaño de Diccionario cambió al invocar get()", s.size() == 3);

			s.remove(en);
			assertTrue("Tamaño de Diccionario luego de eliminar una entrada es != 2", s.size() == 2);

			en=s.find(0);
			assertTrue("Tamaño de Diccionario cambió al invocar get()", s.size() == 2);

			s.remove(en);
			assertTrue("Tamaño de Diccionario luego de eliminar es != 1", s.size() == 1);

			en=s.find(1);
			assertTrue("Tamaño de Diccionario cambió al invocar get()", s.size() == 1);

			s.remove(en);
			assertTrue("Tamaño de Diccionario luego de eliminar es != 0", s.size() == 0);

		} catch (InvalidKeyException e) {
			fail("Los métodos find(), findAll() e insert() no deberían lanzar la excepción InvalidKeyException para una clave válida.");

		} catch (InvalidEntryException e) {
			fail("El método remove() no debería lanzar la excepción InvalidEntryException para una entrada válida.");
		}
	}

	/* _______________________TESTS METODO isEmpty()_____________________________*/

	@Test
	public void isEmpty() {
		Entry<Integer,Integer> en;

		try {

			assertTrue("EL Diccionario no está vacío justo después de ser creado", s.isEmpty());

			s.insert(0, 0);
			assertTrue("El Diccionario está vacía después de insertar 1 elemento", !s.isEmpty());

			s.insert(0, 1);
			assertTrue("El Diccionario está vacía después de insertar 2 elementos", !s.isEmpty());

			s.insert(1, 0);
			assertTrue("El Diccionario está vacía después de insertar 3 elementos", !s.isEmpty());

		} catch (InvalidKeyException e) {
			fail("El método insert() no debería lanzar InvalidkeyException con una clave válida.");
		}

		try {

			s.findAll(0);
			assertTrue( "El Diccionario está vacío después de invocar findAll() teniendo 3 elementos", !s.isEmpty());

			en=s.find(1);
			assertTrue("El Diccionario está vacío después de invocar find() teniendo 3 elementos", !s.isEmpty());

			s.remove(en);
			assertTrue("El Diccionario está vacío después de eliminar 1 elemento, quedando 2.", !s.isEmpty());

			en=s.find(0);
			assertTrue( "El Diccionario está vacío después de invocar find() teniendo 2 elementos", !s.isEmpty());

			s.remove(en);
			assertTrue("El Diccionario está vacío después de eliminar 1 elemento, quedando 1.", !s.isEmpty());

			en=s.find(0);
			assertTrue("El Diccionario está vacío después de invocar get(i3) teniendo 1 elemento", !s.isEmpty());

			s.remove(en);
			assertTrue("EL Diccionario no está vacío después de eliminar todos los elementos que tenía.", s.isEmpty());

		} catch (InvalidKeyException e) {
			fail("El método find() o findAll() no debería lanzar InvalidKeyException con una clave válida.");

		} catch (InvalidEntryException e) {
			fail("El método remove() no debería lanzar InvalidEntryException con una entrada válida.");
		}
	}

	/* _______________________TESTS METODO find(K key)_____________________________*/

	@Test
	public void find() {
		java.util.Set<Integer> claves= new java.util.HashSet<Integer>();
		Vector<Vector<Integer>> V= new Vector<Vector<Integer>>(2);
		Integer valor, clave;
		Entry<Integer,Integer> en;
		boolean esta;

		Random r = new Random();

		// Clave inválida
		try {

			s.find(null);
			fail("El método find debería haber lanzado la excepción InvalidKeyException para una clave nula");

		} catch (InvalidKeyException e) {
			// Sería lo que debe pasar
		}

		// Diccionario vacío.
		try {

			en = s.find(1);
			assertTrue("El método find() debería devolver nulo cuando la clave no está en el Diccionario.", en == null);

		} catch (InvalidKeyException e) {
			fail("El método find() no debería lanzar InvalidkeyException con una clave válida.");
		}


		// Insertando 1000 elementos
		V.add(new Vector<Integer>(1000));
		V.add(new Vector<Integer>(1000));

		try {

			for (int i=0; i<1000;i++){
				clave = r.nextInt(10*(i+1));
				claves.add(clave);
				valor = r.nextInt(1000);
				V.get(0).add(clave);
				V.get(1).add(valor);
				s.insert(clave, valor);
			}

		} catch (InvalidKeyException e) {
			fail("El método insert() no debería lanzar InvalidkeyException con una clave válida.");
		}

		// Testeando find con 1000 elementos	
		try {

			for (Integer i : claves){
				en = s.find(i);

				while (en!=null){
					esta = buscoEnVectorYEliminar(en.getKey(), en.getValue(),V);
					assertTrue("El método find() no funciona correctamente", esta);

					s.remove(en);
					en = s.find(i);
				}
			}
			assertTrue("El método find() no funciona correctamente", ((V.get(0).size()==0) && (V.get(1).size()==0)));

		} catch (InvalidKeyException e) {
			fail("El método insert() no debería lanzar InvalidkeyException con una clave válida.");

		} catch (InvalidEntryException e) {
			fail("El método remove() no debería lanzar InvalidEntryException con una entrada válida.");
		}
	}

	private boolean buscoEnVectorYEliminar(int c, int v, Vector<Vector<Integer>> V){ 
		boolean encontro=true; 
		boolean es=false;
		int index=0;
		int pos=-1;

		while ((encontro)&& !es && (index<V.get(0).capacity())){
			pos=V.get(0).indexOf(c, index);
			if (pos==-1){ 
				encontro=false;
			}else{
				es= (V.get(1).get(pos)==v);
				index=pos+1;
			}     
		}
		if (es){
			V.get(0).remove(pos);
			V.get(1).remove(pos);
		}

		return es;
	}

	/* _______________________TESTS METODO findAll(K key)_____________________________*/

	@Test
	public void findAll() {
		java.util.Set<Integer> claves= new java.util.HashSet<Integer>();
		java.util.Iterator<Entry<Integer,Integer>> it;
		Vector<Vector<Integer>> V= new Vector<Vector<Integer>>(2);
		Integer valor, clave;

		Entry<Integer,Integer> en;
		boolean esta;

		Random r= new Random();

		// Clave inválida
		try {

			s.findAll(null);
			fail("El método findAll() debería haber lanzado la excepción InvalidKeyException para una clave nula");

		} catch (InvalidKeyException e) {
			// Sería lo que debe suceder
		}

		// Diccionario vacío.
		try {

			it = s.findAll(1).iterator();
			assertTrue("El método findAll() debería devolver un iterable sin elementos cuando la clave no está en el Diccionario.", !it.hasNext());

		} catch (InvalidKeyException e) {
			fail("El método findAll() no debería lanzar InvalidkeyException con una clave válida.");
		}

		// Insertando 1000 elementos
		V.add(new Vector<Integer>(1000));
		V.add(new Vector<Integer>(1000));

		try {

			for (int i=0; i<1000;i++){
				clave=r.nextInt(10*(i+1));
				claves.add(clave);
				valor = r.nextInt(1000);
				V.get(0).add(clave);
				V.get(1).add(valor);
				s.insert(clave, valor);
			}

		} catch (InvalidKeyException e) {
			fail("El método insert() no debería lanzar InvalidkeyException con una clave válida.");
		}

		// Testeando find con 1000 elementos	
		try {

			for (Integer i:claves){
				it=s.findAll(i).iterator();
				while (it.hasNext()){
					en=it.next();
					esta = buscoEnVectorYEliminar(en.getKey(), en.getValue(),V);
					assertTrue("El método findAll() no funciona correctamente", esta);
				}
			}
			assertTrue("El método findAll() no funciona correctamente",((V.get(0).size()==0) && (V.get(1).size()==0)));

		}catch (InvalidKeyException e) {
			fail("El método insert() no debería lanzar InvalidkeyException con una clave válida.");
		}
	}

	/* _______________________TESTS METODOS insert() y remove()_____________________________*/

	@Test
	public void Insert_Remove() {
		java.util.Set<Integer> claves= new java.util.HashSet<Integer>();
		Vector<Vector<Integer>> V= new Vector<Vector<Integer>>(2);
		Entry<Integer,Integer> en;
		Integer valor, clave;
		boolean esta;

		en = null;
		Random r = new Random();

		// Clave inválida
		try {

			s.insert(null,3); 
			fail("El método insert debería haber lanzado la excepción InvalidKeyException para una clave inválida");

		} catch (InvalidKeyException e) {
			// Sería lo que debe suceder
		}

		// Entrada inválida
		try {

			s.remove(null);
			fail("El método remove debería haber lanzado la excepción InvalidEntryException para una entry inválida");

		} catch (InvalidEntryException e) {
			// Sería lo que debe suceder
		}

		// Insertando 1 elemento.
		try {

			s.insert(0, 1);
			en=s.find(0);
			s.remove(en);

		} catch (InvalidKeyException e) {
			fail("El método insert() o find() no debería lanzar InvalidkeyException con una clave válida.");

		} catch (InvalidEntryException e) {
			fail("El método remove() no debería lanzar InvalidEntryException con una entrada válida.");
		}

		//Intentando eliminar una entrada que no está en el diccionario
		try {

			s.remove(en);
			fail("El método remove() debería lanzar InvalidEntryException con una entrada que no se encuentra en el diccionario.");

		} catch (InvalidEntryException e) {}

		// Insertando 1000 elementos
		V.add(new Vector<Integer>(1000));
		V.add(new Vector<Integer>(1000));

		try {
			for (int i=0; i<1000;i++){
				clave = r.nextInt(10*(i+1));
				claves.add(clave);
				valor=r.nextInt(1000);
				V.get(0).add(clave);
				V.get(1).add(valor);
				s.insert(clave, valor);
			}

		}catch (InvalidKeyException e) {
			fail("El método insert() no debería lanzar InvalidkeyException con una clave válida.");
		}

		// Testeando find con 1000 elementos	
		try {

			for (Integer i:claves){
				en=s.find(i);
				while (en!=null){
					esta = buscoEnVectorYEliminar(en.getKey(), en.getValue(),V);
					assertTrue("El método insert() no funciona correctamente", esta);
					s.remove(en);
					en = s.find(i);
				}
			}

			assertTrue("El método insert() no funciona correctamente",((V.get(0).size()==0)&&(V.get(1).size()==0)));

		} catch (InvalidKeyException e) {
			fail("El método insert() no debería lanzar InvalidkeyException con una clave válida.");

		} catch (InvalidEntryException e) {
			fail("El método remove() no debería lanzar InvalidEntryException con una entrada válida.");
		}

		assertTrue("El método remove() no funciona correctamente", s.isEmpty());
	}


	/* _______________________TESTS METOD entrie()_____________________________*/

	@Test
	public void entries() {
		java.util.Set<Integer> claves= new java.util.HashSet<Integer>();
		Vector<Vector<Integer>> V= new Vector<Vector<Integer>>(2);
		Integer valor, clave;
		boolean esta;

		Random r = new Random();

		// Diccionario vacío
		assertTrue("Entris no funciona correctamente para un diccionario vacío", ((s.entries()!=null)&&(!s.entries().iterator().hasNext())));

		// Insertando 1000 elementos
		V.add(new Vector<Integer>(1000));
		V.add(new Vector<Integer>(1000));

		try {

			for (int i=0; i<1000;i++){
				clave = r.nextInt(10*(i+1));
				claves.add(clave);
				valor = r.nextInt(1000);
				V.get(0).add(clave);
				V.get(1).add(valor);
				s.insert(clave, valor);
			}

		} catch (InvalidKeyException e) {
			fail("El método insert() no debería lanzar InvalidkeyException con una clave válida.");
		}

		// Testeando entries con 1000 elementos	
		for (Entry<Integer, Integer> entrada: s.entries()){
			esta = buscoEnVectorYEliminar(entrada.getKey(), entrada.getValue(),V);
			assertTrue("El método entries() no funciona correctamente", esta);
		}

		assertTrue("El método entries() no funciona correctamente",((V.get(0).size()==0)&&(V.get(1).size()==0)));	
	}
}