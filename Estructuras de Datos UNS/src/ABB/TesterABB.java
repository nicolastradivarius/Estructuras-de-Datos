package ABB;

public class TesterABB {
	protected static ABB<Integer> abb1 = new ABB<Integer>();
	
	public static void main(String args[]) {
		test_1();
		test_2();
	}

	public static void test_1() {
		abb1.expandir(abb1.buscar(7), 7);
		abb1.expandir(abb1.buscar(2), 2);
		abb1.expandir(abb1.buscar(9), 9);
		abb1.expandir(abb1.buscar(0), 0);
		abb1.expandir(abb1.buscar(5), 5);
		abb1.expandir(abb1.buscar(6), 6);
		abb1.expandir(abb1.buscar(8), 8);
		abb1.expandir(abb1.buscar(1), 1);
		
		
		System.out.println(inorder(abb1.raiz(), ""));
	}

	public static void test_2() {
		NodoABB<Integer> p0 = abb1.buscar(8);
		abb1.eliminar(p0);
		NodoABB<Integer> p1 = abb1.buscar(1);
		abb1.eliminar(p1);
		
		System.out.println(inorder(abb1.raiz(), ""));
		
	}
	private static String inorder(NodoABB<Integer> p, String salida) {
		if (p.getRotulo() != null) {
			salida += ("(" + inorder(p.getLeft(), salida) + p.getRotulo() + inorder(p.getRight(), salida)) + ")";
		}
		else
			salida += "";
		
		return salida;
	}
}
