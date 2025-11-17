// Patrón Factory - Crea productos según el tipo
public class ProductoFactory {

    public static Producto crearProducto(String tipo, String nombre, double precio) {
        switch (tipo.toLowerCase()) {
            case "libro":
                return new Libro(nombre, precio, "Autor Desconocido");
            case "electronico":
                return new Electronico(nombre, precio, 12);
            default:
                throw new IllegalArgumentException("Tipo de producto no válido: " + tipo);
        }
    }

    public static Libro crearLibro(String nombre, double precio, String autor) {
        return new Libro(nombre, precio, autor);
    }

    public static Electronico crearElectronico(String nombre, double precio, int garantia) {
        return new Electronico(nombre, precio, garantia);
    }
}
