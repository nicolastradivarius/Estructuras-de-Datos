package TDADiccionario;

import java.util.Comparator;

public class DefaultComparator<K> implements Comparator<K> {

	public int compare(K o1, K o2) {
		return (-1)*compare(o1, o2);
	}

}
