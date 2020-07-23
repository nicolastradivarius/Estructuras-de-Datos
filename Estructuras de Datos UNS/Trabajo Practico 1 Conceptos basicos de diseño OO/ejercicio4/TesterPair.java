package ejercicio4;

public class TesterPair {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		try {
			
			Pair<Integer, Integer> p1 = new Pair<Integer, Integer>(4, 2);
			Pair<Float, String> p2 = new Pair<Float, String>(3.14f, "Nicolas");
			Pair<Boolean, Long> p3 = new Pair<Boolean, Long>(true, 9L);
			Pair<Double, Integer> p4 = new Pair<Double, Integer>(5.99382, 3);
			Pair<String, Character> p5 = new Pair<String, Character>("Juan", 'k');
			Pair<Integer, String> p6 = new Pair<Integer, String>(23, "Juan");
			Pair<Float, Integer> p7 = new Pair<Float, Integer>(23.2f, 21345);
			
			System.out.println(p1.toString());
			System.out.println(p2.toString());
			System.out.println(p3.toString());
			System.out.println(p4.toString());
			System.out.println(p5.toString());
			System.out.println(p6.toString());
			System.out.println(p7.toString());

		}
		
		finally {
			
			System.out.println("Programa ejecutado exitosamente.");
		}
	}

}
