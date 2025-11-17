import java.util.ArrayList;
import java.util.List;

// Patrón Observer - Sujeto observable (Tienda)
public class Tienda {
    private List<Observer> clientes;
    private String nombre;

    public Tienda(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la tienda no puede ser nulo o vacío");
        }
        this.nombre = nombre;
        this.clientes = new ArrayList<>();
    }

    public void suscribir(Observer cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException("El cliente no puede ser nulo");
        }
        clientes.add(cliente);
        System.out.println("Cliente suscrito a la tienda " + nombre);
    }

    public void desuscribir(Observer cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException("El cliente no puede ser nulo");
        }
        clientes.remove(cliente);
        System.out.println("Cliente desuscrito de la tienda " + nombre);
    }

    public void notificarClientes(String mensaje) {
        System.out.println("\n--- Notificando a " + clientes.size() + " clientes ---");
        for (Observer cliente : clientes) {
            cliente.actualizar(mensaje);
        }
    }

    public void nuevaOferta(String descripcion) {
        System.out.println("\n¡Nueva oferta en " + nombre + "!");
        notificarClientes("Nueva oferta: " + descripcion);
    }
}
