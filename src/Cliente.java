// Observador concreto - Cliente que recibe notificaciones
public class Cliente implements Observer {
    private String nombre;

    public Cliente(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del cliente no puede ser nulo o vacío");
        }
        this.nombre = nombre;
    }

    @Override
    public void actualizar(String mensaje) {
        System.out.println("Cliente " + nombre + " recibió notificación: " + mensaje);
    }

    public String getNombre() {
        return nombre;
    }
}
