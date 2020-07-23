package ejercicio1;

public class Empleado {
	
	protected String nombre;
	protected int sueldo;
	
	public Empleado(String nombre, int sueldo) {
		
		this.nombre = nombre;
		this.sueldo = sueldo;
	}
	
	public Empleado(int sueldo) {
		
		this.sueldo = sueldo;
	}
	
	public String getNombre() {
		
		return nombre;
	}
	
	public int getSueldo() {
		
		return sueldo;
	}

}