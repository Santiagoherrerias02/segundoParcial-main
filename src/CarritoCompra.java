import java.util.ArrayList;
import java.util.List;

// Patrón Strategy - Contexto que usa diferentes estrategias de pago
public class CarritoCompra {
    private List<Producto> productos;
    private EstrategiaPago estrategiaPago;

    public CarritoCompra() {
        this.productos = new ArrayList<>();
    }

    public void agregarProducto(Producto producto) {
        if (producto == null) {
            throw new IllegalArgumentException("El producto no puede ser nulo");
        }
        productos.add(producto);
        System.out.println("Producto agregado al carrito: " + producto.getNombre());
    }

    public void setEstrategiaPago(EstrategiaPago estrategiaPago) {
        if (estrategiaPago == null) {
            throw new IllegalArgumentException("La estrategia de pago no puede ser nula");
        }
        this.estrategiaPago = estrategiaPago;
    }

    public double calcularTotal() {
        double total = 0;
        for (Producto producto : productos) {
            total += producto.getPrecio();
        }
        return total;
    }

    public void checkout() {
        if (productos.isEmpty()) {
            System.out.println("El carrito está vacío");
            return;
        }

        if (estrategiaPago == null) {
            System.out.println("Debe seleccionar un método de pago");
            return;
        }

        double total = calcularTotal();
        System.out.println("\n=== CHECKOUT ===");
        System.out.println("Productos en el carrito:");
        for (Producto producto : productos) {
            producto.mostrarInfo();
        }
        System.out.println("Total: $" + total);
        double montoFinal = estrategiaPago.pagar(total);
        System.out.println("Monto final procesado: $" + String.format("%.2f", montoFinal));
        System.out.println("¡Compra completada!\n");
    }
}
