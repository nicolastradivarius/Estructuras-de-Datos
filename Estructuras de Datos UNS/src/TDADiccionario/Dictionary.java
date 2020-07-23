package TDADiccionario;

/**
 * Interface Dictionary - Estructura de Datos (DCIC-UNS).
 * Define los datos y operaciones aplicables sobre un diccionario.
 * @author Estructuras de Datos, DCIC-UNS.
 * @version 1.0 - María Lujan Ganuza (2013-2018)
 * @version 2.0 - Federico Joaquín (2019-2020) 
 * @param <K> Tipo de dato de las claves a almacenar en el diccionario.
 * @param <V> Tipo de dato de los valores a almacenar en el diccionario.
 */
public interface Dictionary<K,V>{
	
	/**
	 * Consulta el número de entradas del diccionario.
	 * @return Número de entradas del diccionario.
	 */
	public int size();
	
	/**
	 * Consulta si el diccionario está vacío.
	 * @return Verdadero si el diccionario está vacío, falso en caso contrario.
	 */
	public boolean isEmpty();
	
	/**
	 * Busca una entrada con clave igual a una clave dada y la devuelve, si no existe retorna nulo.
	 * @param key Clave a buscar.
	 * @return Entrada encontrada.
	 * @throws InvalidKeyException si la clave pasada por parámetro es inválida.
	 */
	public Entry<K,V> find(K key) throws InvalidKeyException;
	
	/**
	 * Retorna una colección iterable que contiene todas las entradas con clave igual a una clave dada.
	 * @param key Clave de las entradas a buscar.
	 * @return Colección iterable de las entradas encontradas.
	 * @throws InvalidKeyException si la clave pasada por parámetro es inválida.
	 */
	public Iterable<Entry<K,V>> findAll(K key) throws InvalidKeyException;
	
	/**
	 * Inserta una entrada con una clave y un valor dado en el diccionario y retorna la entrada creada.
	 * @param key Clave de la entrada a crear.
	 * @param value Valor de la entrada a crear.
	 * @return Entrada creada.
	 * @throws InvalidKeyException si la clave pasada por parámetro es inválida.
	 */
	public Entry<K,V> insert(K key, V value) throws InvalidKeyException;
	
	/**
	 * Remueve una entrada dada en el diccionario y devuelve la entrada removida.
	 * @param e Entrada a remover.
	 * @return Entrada removida.
	 * @throws InvalidEntryException si la entrada no está en el diccionario o es inválida.
	 */
	public Entry<K,V> remove(Entry<K,V> e) throws InvalidEntryException;
	
	/**
	 * Retorna una colección iterable con todas las entradas en el diccionario.
	 * @return Colección iterable de todas las entradas.
	 */
	public Iterable<Entry<K,V>> entries();
	
}
