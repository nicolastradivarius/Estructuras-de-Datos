package ABB;

import java.util.Comparator;

public class DefaultComparador<E extends Comparable<E>> implements Comparator<E> {

	@Override
	public int compare(E o1, E o2) {
		return o1.compareTo(o2);
	}
	
}
