// Producto concreto - Electrónico
public class Electronico extends Producto {
    private int garantiaMeses;

    public Electronico(String nombre, double precio, int garantiaMeses) {
        super(nombre, precio);
        if (garantiaMeses < 0) {
            throw new IllegalArgumentException("La garantía no puede ser negativa");
        }
        this.garantiaMeses = garantiaMeses;
    }

    @Override
    public void mostrarInfo() {
        System.out.println("Electrónico: " + nombre + " | Garantía: " + garantiaMeses + " meses | Precio: $" + precio);
    }
}
