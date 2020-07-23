package ejercicio1;

public class Medico extends Empleado {
	
	protected String especiabilidad;
	
	public Medico(String nombre, int sueldo, String especiabilidad) {
		
		super(sueldo);
		this.nombre = "Dr. " + nombre;
		this.especiabilidad = especiabilidad;
		this.sueldo = this.sueldo * 8;
	}
	
	public String getEspeciabilidad() {
		
		return especiabilidad;
	}

}
