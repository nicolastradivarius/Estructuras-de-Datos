package TDAColaCP;

import java.util.Comparator;


public class Mi_Comparador implements Comparator<Integer> {
	
	@Override
	public int compare(Integer a, Integer b) {
		int comparacion = 0;
		
		if (a % 2 == 0) {
			if (b % 2 != 0)
				comparacion = -1;
			else
				if (a > b)
					comparacion = -1;
				else
					comparacion = 1;
		}
		else {
			if (b % 2 == 0)
				comparacion = 1;
			else
				if (a > b)
					comparacion = -1;
				else
					comparacion = 1;
		}
		
		return comparacion;
	}

}
