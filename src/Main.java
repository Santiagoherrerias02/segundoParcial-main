public class Main {
    public static void main(String[] args) {
        System.out.println("=================================================");
        System.out.println("DEMOSTRACIÓN DE PATRONES DE DISEÑO EN JAVA");
        System.out.println("=================================================\n");

        // ============================================
        // 1. PATRÓN SINGLETON - Conexión a BD
        // ============================================
        System.out.println(">>> 1. PATRÓN SINGLETON <<<");
        DatabaseConnection db1 = DatabaseConnection.getInstance();
        DatabaseConnection db2 = DatabaseConnection.getInstance();

        System.out.println("¿Son la misma instancia? " + (db1 == db2));
        db1.query("SELECT * FROM usuarios");
        System.out.println();

        // ============================================
        // 2. PATRÓN FACTORY - Crear productos
        // ============================================
        System.out.println(">>> 2. PATRÓN FACTORY <<<");
        Producto libro1 = ProductoFactory.crearLibro("Cien Años de Soledad", 25.99, "Gabriel García Márquez");
        Producto libro2 = ProductoFactory.crearLibro("Don Quijote", 19.99, "Miguel de Cervantes");
        Producto laptop = ProductoFactory.crearElectronico("Laptop HP", 899.99, 24);
        Producto telefono = ProductoFactory.crearElectronico("iPhone 15", 999.99, 12);

        libro1.mostrarInfo();
        libro2.mostrarInfo();
        laptop.mostrarInfo();
        telefono.mostrarInfo();
        System.out.println();

        // ============================================
        // 3. PATRÓN OBSERVER - Sistema de notificaciones
        // ============================================
        System.out.println(">>> 3. PATRÓN OBSERVER <<<");
        Tienda tienda = new Tienda("TechStore");

        Cliente cliente1 = new Cliente("Juan Pérez");
        Cliente cliente2 = new Cliente("María López");
        Cliente cliente3 = new Cliente("Carlos Gómez");

        tienda.suscribir(cliente1);
        tienda.suscribir(cliente2);
        tienda.suscribir(cliente3);

        tienda.nuevaOferta("50% descuento en laptops");

        System.out.println();
        tienda.desuscribir(cliente2);
        System.out.println();

        tienda.nuevaOferta("Black Friday: Todo al 70% de descuento");
        System.out.println();

        // ============================================
        // 4. PATRÓN STRATEGY - Diferentes formas de pago
        // ============================================
        System.out.println(">>> 4. PATRÓN STRATEGY <<<");

        // Carrito 1 - Pago con tarjeta
        CarritoCompra carrito1 = new CarritoCompra();
        carrito1.agregarProducto(libro1);
        carrito1.agregarProducto(laptop);
        carrito1.setEstrategiaPago(new PagoTarjeta("4532-1234-5678-9012"));
        carrito1.checkout();

        // Carrito 2 - Pago en efectivo
        CarritoCompra carrito2 = new CarritoCompra();
        carrito2.agregarProducto(telefono);
        carrito2.agregarProducto(libro2);
        carrito2.setEstrategiaPago(new PagoEfectivo());
        carrito2.checkout();

        // Carrito 3 - Pago con PayPal
        CarritoCompra carrito3 = new CarritoCompra();
        carrito3.agregarProducto(laptop);
        carrito3.agregarProducto(telefono);
        carrito3.setEstrategiaPago(new PagoPayPal("usuario@email.com"));
        carrito3.checkout();

        System.out.println("=================================================");
        System.out.println("FIN DE LA DEMOSTRACIÓN");
        System.out.println("=================================================");
    }
}
