package es.dit.upm.adsw;

public class Producto implements Comparable<Producto>{
	
	private String nombre;
	private int cantidad;
	
	public final static String NOMBRE = "HOMBRE";
	public final static String CANTIDAD = "CANTIDAD";
	
	public Producto(String nombre, int cantidad) {
		if (nombre == null) {
			nombre = "Sin nombre"; // O IllegalArgumentException
		}
		this.nombre = nombre;
		this.cantidad = cantidad;
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	
	@Override
	public String toString() {
		return nombre + " " + cantidad;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + cantidad;
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Producto other = (Producto) obj;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} 
		return nombre.equals(other.nombre);
	}

	@Override
	public int compareTo(Producto otro) {
		return this.nombre.compareTo(otro.nombre);
	}

}
