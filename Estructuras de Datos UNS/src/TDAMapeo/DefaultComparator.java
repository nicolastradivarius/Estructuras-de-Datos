package TDAMapeo;

public class DefaultComparator<E extends Comparable<E>> implements java.util.Comparator<E> {
	
	public int compare( E a, E b ) {
		return a.compareTo( b );
		}
}
