# Patrones de Diseño en Java

Proyecto educativo que demuestra la implementación de cuatro patrones de diseño fundamentales en Java: Singleton, Factory, Observer y Strategy, aplicados en un sistema de tienda online con gestión de productos, notificaciones y métodos de pago.

## Tabla de Contenidos

- [Descripción General](#descripción-general)
- [Patrones de Diseño Implementados](#patrones-de-diseño-implementados)
- [Estructura del Proyecto](#estructura-del-proyecto)
- [Requisitos](#requisitos)
- [Instalación y Ejecución](#instalación-y-ejecución)
- [Arquitectura y Diseño](#arquitectura-y-diseño)
- [Validaciones Implementadas](#validaciones-implementadas)
- [Ejemplos de Uso](#ejemplos-de-uso)
- [Diagramas](#diagramas)

## Descripción General

Este proyecto es una demostración práctica de patrones de diseño aplicados a un escenario real de e-commerce. Implementa un sistema de tienda online con las siguientes capacidades:

- **Gestión de conexión a base de datos única** (Singleton)
- **Creación flexible de productos** (Factory)
- **Sistema de notificaciones a clientes** (Observer)
- **Múltiples métodos de pago intercambiables** (Strategy)

El código está completamente documentado y diseñado con fines educativos para comprender cómo y cuándo aplicar estos patrones fundamentales.

## Patrones de Diseño Implementados

### 1. Singleton (DatabaseConnection)

**Problema que resuelve:** Garantiza que solo exista una única instancia de conexión a la base de datos en toda la aplicación, evitando la creación de múltiples conexiones innecesarias.

**Características:**
- Implementación thread-safe con Double-Checked Locking
- Uso de `volatile` para garantizar visibilidad entre hilos
- Constructor privado que previene instanciación externa
- Método `getInstance()` que retorna siempre la misma instancia

**Clase principal:** `DatabaseConnection.java`

```java
DatabaseConnection db1 = DatabaseConnection.getInstance();
DatabaseConnection db2 = DatabaseConnection.getInstance();
System.out.println(db1 == db2); // true - misma instancia
```

### 2. Factory (ProductoFactory)

**Problema que resuelve:** Centraliza y simplifica la creación de diferentes tipos de productos, delegando la lógica de instanciación a una clase especializada.

**Características:**
- Métodos estáticos para crear productos específicos
- `crearLibro()` - Crea instancias de Libro
- `crearElectronico()` - Crea instancias de Electronico
- `crearProducto()` - Factory genérico basado en tipo string
- Validación automática de parámetros
- Lanza `IllegalArgumentException` para tipos inválidos

**Clases principales:**
- `ProductoFactory.java` (Factory)
- `Producto.java` (Clase abstracta base)
- `Libro.java`, `Electronico.java` (Productos concretos)

```java
Producto libro = ProductoFactory.crearLibro("Don Quijote", 19.99, "Cervantes");
Producto laptop = ProductoFactory.crearElectronico("Laptop HP", 899.99, 24);
```

### 3. Observer (Tienda + Cliente)

**Problema que resuelve:** Permite que múltiples clientes reciban notificaciones automáticamente cuando la tienda publica nuevas ofertas, sin acoplamiento fuerte entre emisor y receptores.

**Características:**
- **Tienda** actúa como Subject (sujeto observable)
- **Cliente** actúa como Observer (observador concreto)
- Métodos de gestión: `suscribir()`, `desuscribir()`, `notificarClientes()`
- Notificación sincrónica a todos los observadores
- Soporte para múltiples suscriptores
- Validación de observadores no nulos

**Clases principales:**
- `Observer.java` (Interfaz Observer)
- `Tienda.java` (Subject)
- `Cliente.java` (ConcreteObserver)

```java
Tienda tienda = new Tienda("TechStore");
Cliente cliente1 = new Cliente("Juan Pérez");
tienda.suscribir(cliente1);
tienda.nuevaOferta("50% descuento en laptops"); // Notifica a todos
```

### 4. Strategy (CarritoCompra + EstrategiaPago)

**Problema que resuelve:** Permite cambiar dinámicamente el algoritmo de procesamiento de pagos sin modificar la clase CarritoCompra, facilitando la extensión con nuevos métodos de pago.

**Características:**
- **CarritoCompra** actúa como Context
- **EstrategiaPago** define la interfaz para todas las estrategias
- Tres estrategias concretas:
  - `PagoTarjeta` - Pago con tarjeta de crédito
  - `PagoEfectivo` - Pago en efectivo (5% descuento)
  - `PagoPayPal` - Pago a través de PayPal
- Cambio de estrategia en tiempo de ejecución con `setEstrategiaPago()`
- El método `pagar()` retorna el monto final procesado
- Validación de carrito no vacío y método de pago seleccionado

**Clases principales:**
- `EstrategiaPago.java` (Interfaz Strategy)
- `CarritoCompra.java` (Context)
- `PagoTarjeta.java`, `PagoEfectivo.java`, `PagoPayPal.java` (Estrategias concretas)

```java
CarritoCompra carrito = new CarritoCompra();
carrito.agregarProducto(laptop);
carrito.setEstrategiaPago(new PagoEfectivo()); // 5% descuento
carrito.checkout();
```

## Estructura del Proyecto

```
patrones-main/
│
├── src/
│   ├── Main.java                    # Clase principal con demo completa
│   │
│   ├── DatabaseConnection.java      # Singleton - Conexión BD
│   │
│   ├── ProductoFactory.java         # Factory - Creador de productos
│   ├── Producto.java                # Clase abstracta base
│   ├── Libro.java                   # Producto concreto
│   ├── Electronico.java             # Producto concreto
│   │
│   ├── Observer.java                # Interfaz Observer
│   ├── Tienda.java                  # Subject - Publicador de ofertas
│   ├── Cliente.java                 # ConcreteObserver - Suscriptor
│   │
│   ├── EstrategiaPago.java          # Interfaz Strategy
│   ├── CarritoCompra.java           # Context - Gestor de compras
│   ├── PagoTarjeta.java             # Estrategia concreta
│   ├── PagoEfectivo.java            # Estrategia concreta
│   └── PagoPayPal.java              # Estrategia concreta
│
├── HISTORIAS_USUARIOS.md            # Historias de usuario detalladas
├── diagrama_clases.puml             # Diagrama de clases UML
├── diagrama_secuencia.puml          # Diagrama de secuencia UML
├── CLAUDE.md                         # Guía para Claude Code
└── README.md                         # Este archivo
```

## Requisitos

- **Java Development Kit (JDK):** Versión 8 o superior
- **Sistema Operativo:** Windows, Linux, o macOS
- **Editor recomendado:** IntelliJ IDEA, Eclipse, VS Code, o cualquier editor de texto

## Instalación y Ejecución

### Opción 1: Desde el directorio raíz

```bash
# Compilar todos los archivos Java
javac src/*.java

# Ejecutar el programa principal
java -cp src Main
```

### Opción 2: Desde el directorio src

```bash
# Navegar al directorio src
cd src

# Compilar todos los archivos
javac *.java

# Ejecutar el programa principal
java Main
```

### Salida Esperada

El programa ejecutará una demostración completa de los 4 patrones:

```
=================================================
DEMOSTRACIÓN DE PATRONES DE DISEÑO EN JAVA
=================================================

>>> 1. PATRÓN SINGLETON <<<
Conexión a BD creada: jdbc:mysql://localhost:3306/midb
¿Son la misma instancia? true
Ejecutando query: SELECT * FROM usuarios

>>> 2. PATRÓN FACTORY <<<
Libro: Cien Años de Soledad - $25.99 - Autor: Gabriel García Márquez
Libro: Don Quijote - $19.99 - Autor: Miguel de Cervantes
Electrónico: Laptop HP - $899.99 - Garantía: 24 meses
Electrónico: iPhone 15 - $999.99 - Garantía: 12 meses

>>> 3. PATRÓN OBSERVER <<<
[Notificaciones a clientes suscritos...]

>>> 4. PATRÓN STRATEGY <<<
[Proceso de checkout con diferentes métodos de pago...]
```

## Arquitectura y Diseño

### Jerarquía de Productos

```
Producto (abstracto)
    ├── Libro
    │   └── atributo: autor
    └── Electronico
        └── atributo: garantiaMeses
```

### Flujo del Patrón Observer

1. Se crea la **Tienda** (Subject)
2. Los **Clientes** se suscriben usando `suscribir()`
3. Cuando hay una nueva oferta, la tienda llama a `nuevaOferta()`
4. Internamente se ejecuta `notificarClientes()` que itera sobre todos los observadores
5. Cada cliente recibe la notificación a través de su método `actualizar()`

### Flujo del Patrón Strategy

1. Se crea un **CarritoCompra**
2. Se agregan productos con `agregarProducto()`
3. Se establece la estrategia de pago con `setEstrategiaPago()`
4. Se ejecuta `checkout()` que:
   - Valida carrito no vacío
   - Valida método de pago seleccionado
   - Calcula el total
   - Delega el procesamiento a la estrategia actual
   - Muestra el monto final procesado

## Validaciones Implementadas

El proyecto implementa validaciones exhaustivas para garantizar integridad de datos:

### Producto (clase abstracta)
- ✅ Nombre no nulo ni vacío
- ✅ Precio mayor a 0

### Libro
- ✅ Autor no nulo ni vacío

### Electronico
- ✅ Garantía mayor o igual a 0 meses

### Cliente
- ✅ Nombre no nulo ni vacío

### Tienda
- ✅ Nombre de tienda no nulo ni vacío
- ✅ Cliente no nulo en suscribir/desuscribir

### CarritoCompra
- ✅ Producto no nulo al agregar
- ✅ Estrategia de pago no nula al establecer
- ✅ Carrito no vacío en checkout
- ✅ Método de pago seleccionado en checkout

### Estrategias de Pago

**PagoTarjeta:**
- ✅ Número de tarjeta con mínimo 4 caracteres
- ✅ Monto mayor a 0

**PagoPayPal:**
- ✅ Email no nulo ni vacío
- ✅ Email contiene "@"
- ✅ Monto mayor a 0

**PagoEfectivo:**
- ✅ Monto mayor a 0

Todas las validaciones lanzan `IllegalArgumentException` con mensajes descriptivos.

## Ejemplos de Uso

### Ejemplo 1: Singleton - Conexión Única

```java
// Obtener instancia de la conexión
DatabaseConnection db = DatabaseConnection.getInstance();
db.query("SELECT * FROM productos");

// Obtener otra referencia - será la misma instancia
DatabaseConnection otraDb = DatabaseConnection.getInstance();
System.out.println(db == otraDb); // true
```

### Ejemplo 2: Factory - Creación de Productos

```java
// Crear productos usando el factory
Producto libro = ProductoFactory.crearLibro(
    "1984",
    15.99,
    "George Orwell"
);

Producto tablet = ProductoFactory.crearElectronico(
    "iPad Pro",
    799.99,
    12
);

// Factory genérico basado en tipo
Producto producto = ProductoFactory.crearProducto(
    "libro",
    "El Principito",
    12.50
);
```

### Ejemplo 3: Observer - Sistema de Notificaciones

```java
// Crear tienda y clientes
Tienda tienda = new Tienda("MegaStore");
Cliente ana = new Cliente("Ana Martínez");
Cliente pedro = new Cliente("Pedro Ruiz");

// Suscribir clientes
tienda.suscribir(ana);
tienda.suscribir(pedro);

// Publicar oferta - todos los suscritos reciben notificación
tienda.nuevaOferta("Cyber Monday: 80% en toda la tienda");

// Desuscribir un cliente
tienda.desuscribir(pedro);

// Nueva oferta - solo Ana recibirá notificación
tienda.nuevaOferta("Envío gratis en compras mayores a $50");
```

### Ejemplo 4: Strategy - Métodos de Pago

```java
// Crear carrito y agregar productos
CarritoCompra carrito = new CarritoCompra();
carrito.agregarProducto(libro);
carrito.agregarProducto(tablet);

// Pagar con tarjeta
carrito.setEstrategiaPago(new PagoTarjeta("4532-1234-5678-9012"));
carrito.checkout();

// Crear otro carrito
CarritoCompra carrito2 = new CarritoCompra();
carrito2.agregarProducto(libro);

// Cambiar a pago en efectivo (5% descuento)
carrito2.setEstrategiaPago(new PagoEfectivo());
carrito2.checkout();

// Pagar con PayPal
carrito2.setEstrategiaPago(new PagoPayPal("usuario@correo.com"));
carrito2.checkout();
```

## Diagramas

El proyecto incluye dos diagramas UML en formato PlantUML:

### diagrama_clases.puml

Muestra la estructura completa de clases del sistema con:
- Todas las clases e interfaces
- Relaciones entre clases (herencia, implementación, asociación)
- Atributos y métodos principales
- Código de colores para identificar cada patrón

Para visualizar: Usar extensión PlantUML en VS Code o IntelliJ IDEA, o servicios online como [PlantText](https://www.planttext.com/)

### diagrama_secuencia.puml

Ilustra el flujo de ejecución completo del método `main()`:
- Secuencia de llamadas para cada patrón
- Interacciones entre objetos
- Orden de ejecución de operaciones

## Características Técnicas

### Thread Safety
- **Singleton:** Implementa Double-Checked Locking con `volatile` para garantizar seguridad en entornos multi-hilo

### Inmutabilidad y Encapsulación
- Todos los atributos son `private`
- Acceso controlado mediante getters públicos
- Validación en constructores

### Constantes
- Uso de `static final` para valores constantes (ej: `DESCUENTO = 0.05` en PagoEfectivo)
- Evita "magic numbers" en el código

### Formateo
- Precios formateados con `String.format("%.2f", amount)` para mostrar siempre 2 decimales

### Retorno de Valores
- `EstrategiaPago.pagar()` retorna `double` permitiendo que las estrategias modifiquen el monto (descuentos, recargos, etc.)

## Conceptos Clave de Patrones

### ¿Cuándo usar Singleton?
- Cuando se necesita exactamente una instancia de una clase
- Para recursos compartidos (conexiones DB, configuración, logger)
- Cuando múltiples instancias causarían problemas o desperdicio de recursos

### ¿Cuándo usar Factory?
- Cuando la creación de objetos es compleja
- Cuando se necesita centralizar la lógica de creación
- Para desacoplar el código cliente de las clases concretas

### ¿Cuándo usar Observer?
- Para implementar sistemas de eventos/notificaciones
- Cuando múltiples objetos deben reaccionar a cambios de estado
- Para reducir acoplamiento entre publicadores y suscriptores

### ¿Cuándo usar Strategy?
- Cuando hay múltiples algoritmos intercambiables para una tarea
- Para eliminar condicionales complejos (if/switch)
- Cuando se necesita cambiar comportamiento en tiempo de ejecución

## Extensibilidad

El proyecto está diseñado para ser fácilmente extensible:

### Agregar nuevo tipo de Producto
1. Crear clase que extienda `Producto`
2. Implementar `mostrarInfo()`
3. Agregar método factory en `ProductoFactory`

### Agregar nuevo método de pago
1. Crear clase que implemente `EstrategiaPago`
2. Implementar método `pagar(double monto)`
3. Usar con `carrito.setEstrategiaPago(new TuNuevaEstrategia())`

### Agregar nuevo tipo de Observer
1. Crear clase que implemente `Observer`
2. Implementar método `actualizar(String mensaje)`
3. Suscribir a la tienda con `tienda.suscribir()`

## Autor

Proyecto educativo para la enseñanza de patrones de diseño en Java.

## Licencia

Este proyecto es de código abierto y está disponible para fines educativos.