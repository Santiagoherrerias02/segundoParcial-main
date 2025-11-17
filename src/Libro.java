// Producto concreto - Libro
public class Libro extends Producto {
    private String autor;

    public Libro(String nombre, double precio, String autor) {
        super(nombre, precio);
        if (autor == null || autor.trim().isEmpty()) {
            throw new IllegalArgumentException("El autor no puede ser nulo o vacío");
        }
        this.autor = autor;
    }

    @Override
    public void mostrarInfo() {
        System.out.println("Libro: " + nombre + " | Autor: " + autor + " | Precio: $" + precio);
    }
}
