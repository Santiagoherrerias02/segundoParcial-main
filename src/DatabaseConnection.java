// Patrón Singleton - Conexión única a la base de datos
public class DatabaseConnection {
    // volatile garantiza visibilidad entre hilos
    private static volatile DatabaseConnection instance;
    private String connectionString;

    private DatabaseConnection() {
        this.connectionString = "jdbc:mysql://localhost:3306/midb";
        System.out.println("Conexión a BD creada: " + connectionString);
    }

    // Double-Checked Locking para thread-safety
    public static DatabaseConnection getInstance() {
        if (instance == null) {
            synchronized (DatabaseConnection.class) {
                if (instance == null) {
                    instance = new DatabaseConnection();
                }
            }
        }
        return instance;
    }

    public void query(String sql) {
        System.out.println("Ejecutando query: " + sql);
    }
}