// Clase base para el patrón Factory
public abstract class Producto {
    protected String nombre;
    protected double precio;

    public Producto(String nombre, double precio) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del producto no puede ser nulo o vacío");
        }
        if (precio <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor a 0");
        }
        this.nombre = nombre;
        this.precio = precio;
    }

    public abstract void mostrarInfo();

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }
}