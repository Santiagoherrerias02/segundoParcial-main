# Historias de Usuario - Sistema de Patrones de Diseño

Este documento contiene las historias de usuario completas para el sistema de tienda online que demuestra la implementación de cuatro patrones de diseño: Singleton, Factory, Observer y Strategy.

## Tabla de Contenidos

- [Épica 1: Gestión de Conexión a Base de Datos (Singleton)](#épica-1-gestión-de-conexión-a-base-de-datos-singleton)
- [Épica 2: Creación de Productos (Factory)](#épica-2-creación-de-productos-factory)
- [Épica 3: Sistema de Notificaciones (Observer)](#épica-3-sistema-de-notificaciones-observer)
- [Épica 4: Procesamiento de Pagos (Strategy)](#épica-4-procesamiento-de-pagos-strategy)

---

## Épica 1: Gestión de Conexión a Base de Datos (Singleton)

### Historia de Usuario 1.1: Conexión Única a Base de Datos

**Como** desarrollador del sistema
**Quiero** garantizar que solo exista una única instancia de conexión a la base de datos
**Para** evitar la creación de múltiples conexiones innecesarias que desperdicien recursos del sistema

#### Criterios de Aceptación

- ✅ Debe existir solo una instancia de `DatabaseConnection` en toda la aplicación
- ✅ Múltiples llamadas a `getInstance()` deben retornar la misma instancia
- ✅ El constructor debe ser privado para prevenir instanciación directa
- ✅ La implementación debe ser thread-safe (segura para entornos multi-hilo)
- ✅ La primera llamada a `getInstance()` debe crear la instancia
- ✅ Llamadas subsecuentes deben retornar la instancia ya creada sin crear una nueva

#### Reglas de Negocio

- La cadena de conexión debe ser: `jdbc:mysql://localhost:3306/midb`
- Se debe usar Double-Checked Locking para garantizar thread-safety
- La variable de instancia debe ser `volatile` para garantizar visibilidad entre hilos
- Al crear la conexión, debe mostrar un mensaje indicando la cadena de conexión

#### Casos de Prueba

**Caso 1: Verificar instancia única**
```java
DatabaseConnection db1 = DatabaseConnection.getInstance();
DatabaseConnection db2 = DatabaseConnection.getInstance();
assert db1 == db2; // Deben ser la misma instancia
```

**Caso 2: Constructor privado**
```java
// Esto no debe compilar:
// DatabaseConnection db = new DatabaseConnection(); // Error de compilación
```

**Caso 3: Ejecutar query**
```java
DatabaseConnection db = DatabaseConnection.getInstance();
db.query("SELECT * FROM usuarios");
// Debe mostrar: "Ejecutando query: SELECT * FROM usuarios"
```

#### Definición de Hecho (DoD)

- ✅ Clase `DatabaseConnection` implementada con patrón Singleton
- ✅ Implementación thread-safe con Double-Checked Locking
- ✅ Método `getInstance()` retorna siempre la misma instancia
- ✅ Método `query(String sql)` implementado para demostración
- ✅ Código probado y funcionando en `Main.java`
- ✅ Documentación con comentarios explicativos

---

## Épica 2: Creación de Productos (Factory)

### Historia de Usuario 2.1: Crear Productos de Tipo Libro

**Como** administrador del sistema
**Quiero** crear productos de tipo Libro de manera centralizada
**Para** simplificar la creación y garantizar validación consistente de datos

#### Criterios de Aceptación

- ✅ Debe existir un método `crearLibro(nombre, precio, autor)` en `ProductoFactory`
- ✅ El método debe retornar una instancia de `Libro`
- ✅ El libro debe heredar de la clase abstracta `Producto`
- ✅ Debe validar que el nombre no sea nulo ni vacío
- ✅ Debe validar que el precio sea mayor a 0
- ✅ Debe validar que el autor no sea nulo ni vacío
- ✅ Debe lanzar `IllegalArgumentException` con mensaje descriptivo si alguna validación falla

#### Reglas de Negocio

- El precio debe ser un número decimal positivo
- El nombre y autor deben ser strings no vacíos
- El libro debe poder mostrar su información con el método `mostrarInfo()`
- El formato de salida debe ser: `"Libro: [nombre] - $[precio] - Autor: [autor]"`

#### Casos de Prueba

**Caso 1: Crear libro válido**
```java
Producto libro = ProductoFactory.crearLibro("Don Quijote", 19.99, "Miguel de Cervantes");
assert libro != null;
assert libro instanceof Libro;
assert libro.getNombre().equals("Don Quijote");
assert libro.getPrecio() == 19.99;
```

**Caso 2: Validación de nombre vacío**
```java
try {
    ProductoFactory.crearLibro("", 19.99, "Cervantes");
    fail("Debería lanzar IllegalArgumentException");
} catch (IllegalArgumentException e) {
    // Esperado
}
```

**Caso 3: Validación de precio inválido**
```java
try {
    ProductoFactory.crearLibro("Don Quijote", -10.0, "Cervantes");
    fail("Debería lanzar IllegalArgumentException");
} catch (IllegalArgumentException e) {
    // Esperado
}
```

**Caso 4: Validación de autor vacío**
```java
try {
    ProductoFactory.crearLibro("Don Quijote", 19.99, "");
    fail("Debería lanzar IllegalArgumentException");
} catch (IllegalArgumentException e) {
    // Esperado
}
```

#### Definición de Hecho (DoD)

- ✅ Clase abstracta `Producto` implementada
- ✅ Clase `Libro` implementada extendiendo `Producto`
- ✅ Método `crearLibro()` en `ProductoFactory` implementado
- ✅ Todas las validaciones funcionando correctamente
- ✅ Método `mostrarInfo()` implementado con formato correcto
- ✅ Casos de prueba ejecutados exitosamente

---

### Historia de Usuario 2.2: Crear Productos de Tipo Electrónico

**Como** administrador del sistema
**Quiero** crear productos de tipo Electrónico de manera centralizada
**Para** gestionar productos con garantía de forma consistente

#### Criterios de Aceptación

- ✅ Debe existir un método `crearElectronico(nombre, precio, garantiaMeses)` en `ProductoFactory`
- ✅ El método debe retornar una instancia de `Electronico`
- ✅ El electrónico debe heredar de la clase abstracta `Producto`
- ✅ Debe validar que el nombre no sea nulo ni vacío
- ✅ Debe validar que el precio sea mayor a 0
- ✅ Debe validar que la garantía sea mayor o igual a 0 meses
- ✅ Debe lanzar `IllegalArgumentException` si alguna validación falla

#### Reglas de Negocio

- La garantía debe expresarse en meses (número entero)
- Una garantía de 0 meses es válida (sin garantía)
- El formato de salida debe ser: `"Electrónico: [nombre] - $[precio] - Garantía: [meses] meses"`

#### Casos de Prueba

**Caso 1: Crear electrónico válido**
```java
Producto laptop = ProductoFactory.crearElectronico("Laptop HP", 899.99, 24);
assert laptop != null;
assert laptop instanceof Electronico;
assert laptop.getNombre().equals("Laptop HP");
assert laptop.getPrecio() == 899.99;
```

**Caso 2: Electrónico sin garantía**
```java
Producto producto = ProductoFactory.crearElectronico("Cable USB", 5.99, 0);
assert producto != null; // 0 meses es válido
```

**Caso 3: Validación de garantía negativa**
```java
try {
    ProductoFactory.crearElectronico("Laptop", 899.99, -12);
    fail("Debería lanzar IllegalArgumentException");
} catch (IllegalArgumentException e) {
    // Esperado
}
```

#### Definición de Hecho (DoD)

- ✅ Clase `Electronico` implementada extendiendo `Producto`
- ✅ Método `crearElectronico()` en `ProductoFactory` implementado
- ✅ Todas las validaciones funcionando correctamente
- ✅ Método `mostrarInfo()` implementado con formato correcto
- ✅ Casos de prueba ejecutados exitosamente

---

### Historia de Usuario 2.3: Factory Genérico con Selección por Tipo

**Como** desarrollador del sistema
**Quiero** crear productos usando un factory genérico basado en tipo string
**Para** facilitar la creación dinámica de productos cuando el tipo se determina en tiempo de ejecución

#### Criterios de Aceptación

- ✅ Debe existir un método `crearProducto(tipo, nombre, precio)` en `ProductoFactory`
- ✅ Si `tipo` es "libro", debe crear un `Libro` con autor por defecto "Autor Desconocido"
- ✅ Si `tipo` es "electronico", debe crear un `Electronico` con garantía por defecto de 12 meses
- ✅ El método debe ser case-insensitive (aceptar "LIBRO", "libro", "Libro", etc.)
- ✅ Debe lanzar `IllegalArgumentException` para tipos no reconocidos
- ✅ El mensaje de error debe indicar el tipo inválido recibido

#### Reglas de Negocio

- Tipos válidos: "libro" y "electronico" (case-insensitive)
- Valores por defecto:
  - Libros: autor = "Autor Desconocido"
  - Electrónicos: garantía = 12 meses

#### Casos de Prueba

**Caso 1: Crear libro genérico**
```java
Producto producto = ProductoFactory.crearProducto("libro", "El Principito", 12.50);
assert producto instanceof Libro;
```

**Caso 2: Crear electrónico genérico**
```java
Producto producto = ProductoFactory.crearProducto("electronico", "Mouse", 15.99);
assert producto instanceof Electronico;
```

**Caso 3: Case-insensitive**
```java
Producto p1 = ProductoFactory.crearProducto("LIBRO", "Test", 10.0);
Producto p2 = ProductoFactory.crearProducto("Libro", "Test", 10.0);
Producto p3 = ProductoFactory.crearProducto("libro", "Test", 10.0);
assert p1 instanceof Libro && p2 instanceof Libro && p3 instanceof Libro;
```

**Caso 4: Tipo inválido**
```java
try {
    ProductoFactory.crearProducto("ropa", "Camisa", 25.0);
    fail("Debería lanzar IllegalArgumentException");
} catch (IllegalArgumentException e) {
    assert e.getMessage().contains("ropa");
}
```

#### Definición de Hecho (DoD)

- ✅ Método `crearProducto()` implementado con switch/case
- ✅ Conversión a lowercase para case-insensitive
- ✅ Valores por defecto correctamente asignados
- ✅ Manejo de excepciones para tipos inválidos
- ✅ Todos los casos de prueba pasando

---

## Épica 3: Sistema de Notificaciones (Observer)

### Historia de Usuario 3.1: Suscripción de Clientes a Notificaciones

**Como** cliente de la tienda
**Quiero** suscribirme a las notificaciones de la tienda
**Para** recibir alertas sobre nuevas ofertas y promociones

#### Criterios de Aceptación

- ✅ Debe existir una clase `Tienda` que actúe como Subject (sujeto observable)
- ✅ Debe existir una interfaz `Observer` con método `actualizar(String mensaje)`
- ✅ Debe existir una clase `Cliente` que implemente `Observer`
- ✅ La tienda debe tener un método `suscribir(Observer cliente)`
- ✅ El método debe agregar el cliente a la lista de observadores
- ✅ Debe validar que el cliente no sea nulo
- ✅ Debe mostrar mensaje de confirmación al suscribir

#### Reglas de Negocio

- Un cliente puede suscribirse múltiples veces (no hay validación de duplicados)
- La tienda debe tener un nombre no nulo ni vacío
- El cliente debe tener un nombre no nulo ni vacío
- La lista de observadores se inicializa vacía al crear la tienda

#### Casos de Prueba

**Caso 1: Suscripción exitosa**
```java
Tienda tienda = new Tienda("TechStore");
Cliente cliente = new Cliente("Juan Pérez");
tienda.suscribir(cliente);
// Debe mostrar: "Cliente suscrito a la tienda TechStore"
```

**Caso 2: Suscribir múltiples clientes**
```java
Tienda tienda = new Tienda("TechStore");
Cliente c1 = new Cliente("Ana");
Cliente c2 = new Cliente("Pedro");
Cliente c3 = new Cliente("María");
tienda.suscribir(c1);
tienda.suscribir(c2);
tienda.suscribir(c3);
// Los 3 clientes deben estar suscritos
```

**Caso 3: Validación de cliente nulo**
```java
Tienda tienda = new Tienda("TechStore");
try {
    tienda.suscribir(null);
    fail("Debería lanzar IllegalArgumentException");
} catch (IllegalArgumentException e) {
    // Esperado
}
```

#### Definición de Hecho (DoD)

- ✅ Interfaz `Observer` creada con método `actualizar()`
- ✅ Clase `Tienda` implementada como Subject
- ✅ Clase `Cliente` implementada como Observer
- ✅ Método `suscribir()` funcionando correctamente
- ✅ Validaciones implementadas
- ✅ Mensajes de confirmación mostrándose

---

### Historia de Usuario 3.2: Desuscripción de Clientes

**Como** cliente de la tienda
**Quiero** poder cancelar mi suscripción a las notificaciones
**Para** dejar de recibir alertas cuando ya no me interesen

#### Criterios de Aceptación

- ✅ La tienda debe tener un método `desuscribir(Observer cliente)`
- ✅ El método debe remover el cliente de la lista de observadores
- ✅ Debe validar que el cliente no sea nulo
- ✅ Debe mostrar mensaje de confirmación al desuscribir
- ✅ Si el cliente no estaba suscrito, no debe lanzar error
- ✅ Después de desuscribirse, el cliente no debe recibir más notificaciones

#### Reglas de Negocio

- Desuscribir un cliente no suscrito es una operación válida (no hace nada)
- El método usa `List.remove()` que elimina la primera ocurrencia

#### Casos de Prueba

**Caso 1: Desuscripción exitosa**
```java
Tienda tienda = new Tienda("TechStore");
Cliente cliente = new Cliente("Juan");
tienda.suscribir(cliente);
tienda.desuscribir(cliente);
// Debe mostrar: "Cliente desuscrito de la tienda TechStore"
```

**Caso 2: Desuscribir cliente no suscrito**
```java
Tienda tienda = new Tienda("TechStore");
Cliente cliente = new Cliente("Ana");
tienda.desuscribir(cliente); // No debe lanzar error
```

**Caso 3: Validación de cliente nulo**
```java
Tienda tienda = new Tienda("TechStore");
try {
    tienda.desuscribir(null);
    fail("Debería lanzar IllegalArgumentException");
} catch (IllegalArgumentException e) {
    // Esperado
}
```

#### Definición de Hecho (DoD)

- ✅ Método `desuscribir()` implementado en `Tienda`
- ✅ Validaciones funcionando correctamente
- ✅ Mensaje de confirmación mostrándose
- ✅ Casos de prueba ejecutados exitosamente

---

### Historia de Usuario 3.3: Notificación Automática a Clientes Suscritos

**Como** administrador de la tienda
**Quiero** que todos los clientes suscritos reciban notificaciones automáticamente cuando publico una oferta
**Para** mantenerlos informados sin tener que notificar manualmente a cada uno

#### Criterios de Aceptación

- ✅ La tienda debe tener un método `nuevaOferta(String descripcion)`
- ✅ El método debe invocar `notificarClientes()` internamente
- ✅ `notificarClientes()` debe iterar sobre todos los observadores suscritos
- ✅ Cada observador debe recibir la notificación mediante su método `actualizar()`
- ✅ El mensaje debe incluir la descripción de la oferta
- ✅ Debe mostrar cuántos clientes fueron notificados
- ✅ Si no hay clientes suscritos, debe notificar a 0 clientes sin error

#### Reglas de Negocio

- Las notificaciones se envían de forma sincrónica (una tras otra)
- Todos los observadores suscritos reciben la misma notificación
- El formato del mensaje es: "Nueva oferta: [descripción]"
- El cliente muestra: "Cliente [nombre] recibió notificación: [mensaje]"

#### Casos de Prueba

**Caso 1: Notificar a múltiples clientes**
```java
Tienda tienda = new Tienda("TechStore");
Cliente c1 = new Cliente("Juan");
Cliente c2 = new Cliente("Ana");
tienda.suscribir(c1);
tienda.suscribir(c2);
tienda.nuevaOferta("50% descuento en laptops");
// Ambos clientes deben recibir la notificación
// Debe mostrar: "--- Notificando a 2 clientes ---"
```

**Caso 2: Notificar sin clientes suscritos**
```java
Tienda tienda = new Tienda("TechStore");
tienda.nuevaOferta("Black Friday");
// Debe mostrar: "--- Notificando a 0 clientes ---"
```

**Caso 3: Cliente desuscrito no recibe notificación**
```java
Tienda tienda = new Tienda("TechStore");
Cliente c1 = new Cliente("Juan");
Cliente c2 = new Cliente("Ana");
tienda.suscribir(c1);
tienda.suscribir(c2);
tienda.desuscribir(c2);
tienda.nuevaOferta("Promoción especial");
// Solo Juan debe recibir la notificación
// Debe mostrar: "--- Notificando a 1 clientes ---"
```

#### Definición de Hecho (DoD)

- ✅ Método `nuevaOferta()` implementado
- ✅ Método `notificarClientes()` implementado
- ✅ Método `actualizar()` implementado en `Cliente`
- ✅ Todos los observadores siendo notificados correctamente
- ✅ Contadores y mensajes mostrándose correctamente
- ✅ Casos de prueba pasando

---

## Épica 4: Procesamiento de Pagos (Strategy)

### Historia de Usuario 4.1: Gestión de Carrito de Compras

**Como** cliente de la tienda
**Quiero** agregar productos a un carrito de compras
**Para** acumular mis selecciones antes de proceder al pago

#### Criterios de Aceptación

- ✅ Debe existir una clase `CarritoCompra` que actúe como Context
- ✅ El carrito debe tener un método `agregarProducto(Producto producto)`
- ✅ El método debe agregar el producto a una lista interna
- ✅ Debe validar que el producto no sea nulo
- ✅ Debe mostrar mensaje de confirmación al agregar cada producto
- ✅ El carrito debe poder calcular el total de todos los productos agregados
- ✅ Debe existir un método `calcularTotal()` que retorne la suma de precios

#### Reglas de Negocio

- El carrito se inicializa vacío
- Se pueden agregar múltiples productos
- El mismo producto puede agregarse varias veces
- El total es la suma de los precios de todos los productos

#### Casos de Prueba

**Caso 1: Agregar productos al carrito**
```java
CarritoCompra carrito = new CarritoCompra();
Producto libro = ProductoFactory.crearLibro("Don Quijote", 19.99, "Cervantes");
Producto laptop = ProductoFactory.crearElectronico("Laptop", 899.99, 24);
carrito.agregarProducto(libro);
carrito.agregarProducto(laptop);
// Debe mostrar mensajes de confirmación para ambos
```

**Caso 2: Calcular total**
```java
CarritoCompra carrito = new CarritoCompra();
carrito.agregarProducto(ProductoFactory.crearLibro("Libro1", 10.0, "Autor"));
carrito.agregarProducto(ProductoFactory.crearLibro("Libro2", 15.0, "Autor"));
double total = carrito.calcularTotal();
assert total == 25.0;
```

**Caso 3: Validación de producto nulo**
```java
CarritoCompra carrito = new CarritoCompra();
try {
    carrito.agregarProducto(null);
    fail("Debería lanzar IllegalArgumentException");
} catch (IllegalArgumentException e) {
    // Esperado
}
```

#### Definición de Hecho (DoD)

- ✅ Clase `CarritoCompra` implementada
- ✅ Método `agregarProducto()` funcionando con validaciones
- ✅ Método `calcularTotal()` calculando correctamente
- ✅ Lista interna de productos manejándose correctamente
- ✅ Casos de prueba pasando

---

### Historia de Usuario 4.2: Pago con Tarjeta de Crédito

**Como** cliente de la tienda
**Quiero** poder pagar mis compras con tarjeta de crédito
**Para** completar transacciones de forma segura y conveniente

#### Criterios de Aceptación

- ✅ Debe existir una interfaz `EstrategiaPago` con método `pagar(double monto)`
- ✅ Debe existir una clase `PagoTarjeta` que implemente `EstrategiaPago`
- ✅ `PagoTarjeta` debe recibir número de tarjeta en el constructor
- ✅ Debe validar que el número de tarjeta tenga al menos 4 caracteres
- ✅ Debe validar que el monto sea mayor a 0
- ✅ El método `pagar()` debe retornar el monto exacto (sin modificaciones)
- ✅ Debe mostrar mensaje indicando pago con tarjeta

#### Reglas de Negocio

- El número de tarjeta se almacena como String
- No se aplican descuentos ni recargos al pago con tarjeta
- El monto retornado es igual al monto recibido

#### Casos de Prueba

**Caso 1: Pago exitoso con tarjeta**
```java
EstrategiaPago estrategia = new PagoTarjeta("4532-1234-5678-9012");
double montoFinal = estrategia.pagar(100.0);
assert montoFinal == 100.0;
```

**Caso 2: Validación de número de tarjeta corto**
```java
try {
    new PagoTarjeta("123");
    fail("Debería lanzar IllegalArgumentException");
} catch (IllegalArgumentException e) {
    // Esperado
}
```

**Caso 3: Validación de monto inválido**
```java
EstrategiaPago estrategia = new PagoTarjeta("4532-1234-5678-9012");
try {
    estrategia.pagar(-50.0);
    fail("Debería lanzar IllegalArgumentException");
} catch (IllegalArgumentException e) {
    // Esperado
}
```

#### Definición de Hecho (DoD)

- ✅ Interfaz `EstrategiaPago` creada
- ✅ Clase `PagoTarjeta` implementada
- ✅ Todas las validaciones funcionando
- ✅ Método `pagar()` retornando monto correcto
- ✅ Casos de prueba pasando

---

### Historia de Usuario 4.3: Pago en Efectivo con Descuento

**Como** cliente de la tienda
**Quiero** pagar en efectivo y recibir un descuento del 5%
**Para** obtener un beneficio por usar este método de pago

#### Criterios de Aceptación

- ✅ Debe existir una clase `PagoEfectivo` que implemente `EstrategiaPago`
- ✅ Debe aplicar un descuento del 5% al monto recibido
- ✅ El descuento debe definirse como constante `DESCUENTO = 0.05`
- ✅ Debe validar que el monto sea mayor a 0
- ✅ El método `pagar()` debe retornar el monto con descuento aplicado
- ✅ Debe mostrar el monto original, el porcentaje de descuento, y el total a pagar

#### Reglas de Negocio

- Descuento fijo del 5% en todos los pagos en efectivo
- Cálculo: `montoFinal = monto * (1 - DESCUENTO)` o `monto * 0.95`
- El monto retornado es el monto después del descuento

#### Casos de Prueba

**Caso 1: Pago en efectivo con descuento**
```java
EstrategiaPago estrategia = new PagoEfectivo();
double montoFinal = estrategia.pagar(100.0);
assert montoFinal == 95.0; // 100 * 0.95
```

**Caso 2: Verificar porcentaje de descuento**
```java
EstrategiaPago estrategia = new PagoEfectivo();
double montoFinal = estrategia.pagar(200.0);
double descuentoAplicado = 200.0 - montoFinal;
assert descuentoAplicado == 10.0; // 5% de 200
```

**Caso 3: Validación de monto inválido**
```java
EstrategiaPago estrategia = new PagoEfectivo();
try {
    estrategia.pagar(0);
    fail("Debería lanzar IllegalArgumentException");
} catch (IllegalArgumentException e) {
    // Esperado
}
```

#### Definición de Hecho (DoD)

- ✅ Clase `PagoEfectivo` implementada
- ✅ Constante `DESCUENTO` definida
- ✅ Cálculo de descuento correcto
- ✅ Validaciones funcionando
- ✅ Mensajes informativos mostrándose
- ✅ Casos de prueba pasando

---

### Historia de Usuario 4.4: Pago con PayPal

**Como** cliente de la tienda
**Quiero** poder pagar mis compras usando PayPal
**Para** utilizar mi cuenta de PayPal como método de pago alternativo

#### Criterios de Aceptación

- ✅ Debe existir una clase `PagoPayPal` que implemente `EstrategiaPago`
- ✅ `PagoPayPal` debe recibir email en el constructor
- ✅ Debe validar que el email no sea nulo ni vacío
- ✅ Debe validar que el email contenga el carácter "@"
- ✅ Debe validar que el monto sea mayor a 0
- ✅ El método `pagar()` debe retornar el monto exacto (sin modificaciones)
- ✅ Debe mostrar mensaje indicando pago con PayPal y el email usado

#### Reglas de Negocio

- El email debe ser un string válido con formato básico (contener @)
- No se aplican descuentos ni recargos al pago con PayPal
- El monto retornado es igual al monto recibido

#### Casos de Prueba

**Caso 1: Pago exitoso con PayPal**
```java
EstrategiaPago estrategia = new PagoPayPal("usuario@email.com");
double montoFinal = estrategia.pagar(150.0);
assert montoFinal == 150.0;
```

**Caso 2: Validación de email sin @**
```java
try {
    new PagoPayPal("usuarioemail.com");
    fail("Debería lanzar IllegalArgumentException");
} catch (IllegalArgumentException e) {
    // Esperado
}
```

**Caso 3: Validación de email vacío**
```java
try {
    new PagoPayPal("");
    fail("Debería lanzar IllegalArgumentException");
} catch (IllegalArgumentException e) {
    // Esperado
}
```

**Caso 4: Validación de monto inválido**
```java
EstrategiaPago estrategia = new PagoPayPal("user@email.com");
try {
    estrategia.pagar(-100.0);
    fail("Debería lanzar IllegalArgumentException");
} catch (IllegalArgumentException e) {
    // Esperado
}
```

#### Definición de Hecho (DoD)

- ✅ Clase `PagoPayPal` implementada
- ✅ Todas las validaciones de email funcionando
- ✅ Validación de monto funcionando
- ✅ Método `pagar()` retornando monto correcto
- ✅ Mensajes informativos mostrándose
- ✅ Casos de prueba pasando

---

### Historia de Usuario 4.5: Proceso de Checkout con Estrategia Seleccionada

**Como** cliente de la tienda
**Quiero** completar mi compra usando el método de pago de mi elección
**Para** finalizar la transacción de manera conveniente

#### Criterios de Aceptación

- ✅ El carrito debe tener un método `setEstrategiaPago(EstrategiaPago estrategia)`
- ✅ El método debe validar que la estrategia no sea nula
- ✅ El carrito debe tener un método `checkout()` para procesar la compra
- ✅ `checkout()` debe validar que el carrito no esté vacío
- ✅ `checkout()` debe validar que se haya seleccionado un método de pago
- ✅ Debe mostrar todos los productos del carrito
- ✅ Debe mostrar el total calculado
- ✅ Debe invocar `pagar()` de la estrategia seleccionada
- ✅ Debe mostrar el monto final procesado (que puede diferir del total si hay descuento)
- ✅ Debe mostrar mensaje de confirmación de compra completada

#### Reglas de Negocio

- No se puede hacer checkout con carrito vacío
- No se puede hacer checkout sin método de pago seleccionado
- La estrategia de pago puede cambiarse antes del checkout
- El formato de precios debe mostrar 2 decimales

#### Casos de Prueba

**Caso 1: Checkout exitoso con tarjeta**
```java
CarritoCompra carrito = new CarritoCompra();
carrito.agregarProducto(ProductoFactory.crearLibro("Libro", 20.0, "Autor"));
carrito.setEstrategiaPago(new PagoTarjeta("4532-1234-5678-9012"));
carrito.checkout();
// Debe completar sin errores y mostrar monto final: $20.00
```

**Caso 2: Checkout con efectivo (descuento)**
```java
CarritoCompra carrito = new CarritoCompra();
carrito.agregarProducto(ProductoFactory.crearElectronico("Laptop", 1000.0, 12));
carrito.setEstrategiaPago(new PagoEfectivo());
carrito.checkout();
// Debe mostrar total: $1000.0 y monto final procesado: $950.00
```

**Caso 3: Cambiar estrategia de pago**
```java
CarritoCompra carrito = new CarritoCompra();
carrito.agregarProducto(ProductoFactory.crearLibro("Libro", 50.0, "Autor"));
carrito.setEstrategiaPago(new PagoTarjeta("1234-5678"));
carrito.setEstrategiaPago(new PagoPayPal("user@email.com")); // Cambio válido
carrito.checkout();
// Debe usar PayPal para procesar el pago
```

**Caso 4: Checkout con carrito vacío**
```java
CarritoCompra carrito = new CarritoCompra();
carrito.setEstrategiaPago(new PagoTarjeta("1234-5678"));
carrito.checkout();
// Debe mostrar: "El carrito está vacío" y terminar sin procesar
```

**Caso 5: Checkout sin método de pago**
```java
CarritoCompra carrito = new CarritoCompra();
carrito.agregarProducto(ProductoFactory.crearLibro("Libro", 20.0, "Autor"));
carrito.checkout();
// Debe mostrar: "Debe seleccionar un método de pago" y terminar
```

#### Definición de Hecho (DoD)

- ✅ Método `setEstrategiaPago()` implementado con validación
- ✅ Método `checkout()` implementado con todas las validaciones
- ✅ Integración con todas las estrategias de pago funcionando
- ✅ Formato de precios con 2 decimales
- ✅ Todos los mensajes mostrándose correctamente
- ✅ Casos de prueba pasando
- ✅ Demostración completa en `Main.java`

---

## Historia de Usuario Transversal: Demostración Completa del Sistema

**Como** instructor o estudiante
**Quiero** ejecutar una demostración completa de los 4 patrones de diseño
**Para** ver en funcionamiento cada patrón con ejemplos prácticos

#### Criterios de Aceptación

- ✅ Debe existir una clase `Main` con método `main()`
- ✅ El programa debe ejecutar ejemplos de los 4 patrones en orden:
  1. Singleton (DatabaseConnection)
  2. Factory (creación de productos)
  3. Observer (notificaciones)
  4. Strategy (métodos de pago)
- ✅ Cada sección debe estar claramente identificada con encabezados
- ✅ Debe mostrar salida formateada y legible en consola
- ✅ Debe demostrar casos exitosos y variaciones de cada patrón
- ✅ No debe contener errores de ejecución

#### Reglas de Negocio

- La demostración debe ser autocontenida (no requiere input del usuario)
- Debe mostrar mensajes descriptivos en español
- Cada patrón debe tener su propia sección claramente delimitada

#### Casos de Demostración

**Singleton:**
- Crear dos referencias y verificar que son la misma instancia
- Ejecutar una query de ejemplo

**Factory:**
- Crear 2 libros con diferentes datos
- Crear 2 electrónicos con diferentes garantías
- Mostrar información de todos los productos

**Observer:**
- Crear tienda y 3 clientes
- Suscribir los 3 clientes
- Publicar primera oferta (3 clientes notificados)
- Desuscribir un cliente
- Publicar segunda oferta (2 clientes notificados)

**Strategy:**
- Crear carrito 1 con libros y laptop, pagar con tarjeta
- Crear carrito 2 con productos, pagar en efectivo (mostrar descuento)
- Crear carrito 3 con productos, pagar con PayPal

#### Definición de Hecho (DoD)

- ✅ Clase `Main.java` implementada con todos los ejemplos
- ✅ Salida formateada con líneas separadoras
- ✅ Todos los patrones funcionando correctamente
- ✅ Código compila sin errores
- ✅ Código ejecuta sin excepciones
- ✅ Salida es clara y educativa

---

## Resumen de Implementación

### Clases por Patrón

**Singleton:**
- `DatabaseConnection.java`

**Factory:**
- `ProductoFactory.java`
- `Producto.java` (abstracta)
- `Libro.java`
- `Electronico.java`

**Observer:**
- `Observer.java` (interfaz)
- `Tienda.java`
- `Cliente.java`

**Strategy:**
- `EstrategiaPago.java` (interfaz)
- `CarritoCompra.java`
- `PagoTarjeta.java`
- `PagoEfectivo.java`
- `PagoPayPal.java`

**Demostración:**
- `Main.java`

### Métricas del Proyecto

- **Total de clases:** 14
- **Patrones implementados:** 4
- **Interfaces:** 2
- **Clases abstractas:** 1
- **Historias de usuario:** 12
- **Validaciones:** 15+

### Validaciones por Tipo

**Validaciones de Null/Vacío:**
- Nombre de producto
- Nombre de cliente
- Nombre de tienda
- Autor de libro
- Email de PayPal
- Número de tarjeta

**Validaciones Numéricas:**
- Precio > 0
- Garantía >= 0
- Monto a pagar > 0

**Validaciones de Formato:**
- Email contiene "@"
- Número de tarjeta >= 4 caracteres

**Validaciones de Estado:**
- Carrito no vacío al checkout
- Método de pago seleccionado al checkout

---

## Glosario

- **Subject:** Objeto observable que mantiene lista de observadores y los notifica
- **Observer:** Objeto que se suscribe para recibir notificaciones
- **Strategy:** Algoritmo intercambiable encapsulado en una clase
- **Context:** Clase que usa una estrategia y puede cambiarla dinámicamente
- **Factory:** Clase responsable de crear instancias de otras clases
- **Singleton:** Patrón que garantiza una única instancia de una clase
- **Thread-safe:** Seguro para uso en ambientes con múltiples hilos
- **Double-Checked Locking:** Técnica para lazy initialization thread-safe