FACTURACION

Este proyecto es una aplicación para la gestión de ventas de un comercio. Permite administrar clientes, productos y generar comprobantes con validaciones específicas, además de consumir un servicio externo para obtener la fecha de los comprobantes.

Tecnologías utilizadas:

Java 17.0.12
Spring Boot 3.3.5
Spring Data JPA
MySQL 5.5.5 (utilizando XAMPP como servidor)
Lombok
Maven
Swagger/OpenAPI para documentación y pruebas de la API
Requisitos previos:

JDK 17 o superior
Maven 3.6.3 o superior
XAMPP (para gestionar MySQL)
Postman o Swagger para realizar pruebas sobre los endpoints
Configuración de la Base de Datos: Primero, crea una base de datos MySQL llamada "facturacion". Asegúrate de configurar el nombre de usuario y la contraseña de la base de datos en el archivo de configuración de la aplicación para que pueda conectarse correctamente a MySQL.

Compilación y ejecución del proyecto:

Clona el repositorio en tu máquina local.
Usa Maven para compilar el proyecto y prepararlo para su ejecución.
Ejecuta la aplicación con Maven. Una vez iniciada, el servidor estará disponible en http://localhost:8080.
Uso de la API:

Productos:

Obtener un producto por ID.
Actualizar un producto existente.
Eliminar un producto por ID.
Obtener todos los productos.
Crear un nuevo producto.
Facturas:

Obtener una factura por ID.
Actualizar una factura.
Eliminar una factura.
Obtener todas las facturas.
Crear una nueva factura.
Clientes:

Obtener un cliente por ID.
Actualizar un cliente existente.
Eliminar un cliente por ID.
Obtener todos los clientes.
Crear un nuevo cliente.
Buscar cliente por número de documento.
Tiempo:

Obtener la fecha actual.
Documentación de la API: Puedes consultar y probar los diferentes endpoints de la API accediendo a la interfaz de Swagger en http://localhost:8080/swagger-ui.html. También puedes utilizar Postman para realizar pruebas de los endpoints mencionados.

Manejo de errores: El sistema maneja varias situaciones y responde con códigos específicos según el resultado de las operaciones:

Respuesta 201: Indica que una entidad se creó exitosamente.
Respuesta 400: Los datos proporcionados en la solicitud son inválidos o incompletos.
Respuesta 409: Ocurre un conflicto, como cuando el cliente o producto no existen o si no hay suficiente stock para completar una venta.
Respuesta 500: Error interno del servidor.
Para cada endpoint se detallan las posibles respuestas y errores que pueden surgir durante la interacción con el sistema.

Servicio de Fecha Externo: La aplicación consume un servicio REST externo para obtener la fecha de los comprobantes, accesible en https://timeapi.io/swagger/index.html. Si este servicio no está disponible, el sistema utilizará una lógica de respaldo que calcula la fecha utilizando la clase Date de Java.

Estructura del Proyecto: El proyecto está organizado de la siguiente manera:

Controladores: Gestionan las operaciones para clientes, productos y facturas.
Servicios: Contienen la lógica de negocio y las validaciones necesarias.
Repositorios: Interactúan con la base de datos a través de JPA.
Entidades: Definen la estructura de los objetos manejados por el sistema, como Cliente, Producto y Factura.
Configuración: Los archivos de recursos gestionan la conexión a la base de datos y otras configuraciones importantes.

Maximiliano Sastre.
