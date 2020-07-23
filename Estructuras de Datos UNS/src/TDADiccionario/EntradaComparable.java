package TDADiccionario;

public class EntradaComparable<K extends Comparable<K>, V> implements Entry<K,V>, Comparable<EntradaComparable<K,V>>{

	private K key;
	private V value;

	public EntradaComparable(K k, V v) {
		key = k;
		value = v;
	}
	
	@Override
	public int compareTo(EntradaComparable<K, V> e) {
		return key.compareTo(e.getKey());
	}

	@Override
	public K getKey() {
		return key;
	}

	@Override
	public V getValue() {
		return value;
	}

	@Override
	public void setValue(V value) {
		this.value = value;
	}
	
	public String toString() {
		return "(" + key + ", " + value + ")";
	}
}
