FACTURACION

Descripción del Proyecto
Este proyecto es una aplicación Java que permite administrar las ventas de un comercio. Facilita la gestión de clientes, productos y facturas, además de ofrecer manejo automático de stock e integración con un servicio REST externo para obtener la fecha de los comprobantes.

Requisitos del Sistema
• Java: 17.0.12
• IDE: Puedes utilizar IntelliJ IDEA Community (gratuito) como entorno de desarrollo
• MySQL: 5.5.5
• XAMPP: Para el servidor MySQL local
• Swagger: Para la documentación y pruebas de la API
• Postman: Para pruebas de endpoints y validación de funcionalidades

Instalación y Configuración Desde Cero
1. Instalar IntelliJ IDEA
Descarga e instala IntelliJ IDEA desde aquí. Abre IntelliJ y crea un nuevo proyecto desde cero o importando el archivo descargado en el siguiente paso.

2. Configurar el Proyecto con Spring Initializr
Visita Spring Initializr y configura tu proyecto:

• Tipo de Proyecto: Maven
• Lenguaje: Java
• Spring Boot: La versión más reciente compatible con Java 17
• Dependencias:
• Spring Web
• Spring Data JPA
• MySQL Driver
• Lombok (opcional, para reducir el código repetitivo)
• Otros necesarios para la conexión a MySQL, según las especificaciones de tu aplicación.

Descarga el archivo del proyecto y descomprímelo.

3. Abrir el Proyecto en IntelliJ
Abre IntelliJ, selecciona Open y localiza la carpeta del proyecto descargado. Una vez abierto, revisa el archivo pom.xml y, si necesitas, agrega las dependencias adicionales.

4. Configurar el Archivo pom.xml
En el archivo pom.xml, añade o verifica las siguientes dependencias necesarias:

<dependencies> • Spring Boot Starter Web <dependency> <groupId>org.springframework.boot</groupId> <artifactId>spring-boot-starter-web</artifactId> </dependency> • Spring Data JPA <dependency> <groupId>org.springframework.boot</groupId> <artifactId>spring-boot-starter-data-jpa</artifactId> </dependency> • MySQL Driver <dependency> <groupId>mysql</groupId> <artifactId>mysql-connector-java</artifactId> </dependency> • Lombok <dependency> <groupId>org.projectlombok</groupId> <artifactId>lombok</artifactId> <version>1.18.24</version> <scope>provided</scope> </dependency> </dependencies>
Guarda el archivo y permite que IntelliJ descargue e instale las dependencias.

5. Configurar la Base de Datos en XAMPP
Inicia el servidor MySQL en XAMPP y realiza los siguientes pasos:

• Crea una base de datos llamada ventas_db.
• Configura las credenciales en el archivo application.properties de Spring Boot:

spring.datasource.url=jdbc:mysql://localhost:3306/ventas_db
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

6. Ejecutar la Aplicación
En IntelliJ, selecciona el archivo principal de la aplicación y ejecútalo. La aplicación estará disponible en http://localhost:8080.

Uso de la API
Gestión de Clientes
Crear Cliente
Endpoint: /api/clientes
Método: POST
Ejemplo de Solicitud:

{
"nombre": "Maximiliano Sastre",
"dni": "11111111"
}

Ejemplo de Error

{
"timestamp": "2024-10-30T10:22:50",
"status": 400,
"error": "Bad Request",
"message": "El cliente ya existe",
"path": "/api/clientes"
}

Obtener Cliente
Endpoint: /api/clientes/{id}
Método: GET
Ejemplo de Error

{
"timestamp": "2024-10-30T10:30:00",
"status": 404,
"error": "Not Found",
"message": "Cliente no encontrado",
"path": "/api/clientes/1"
}

Actualizar Cliente
Endpoint: /api/clientes/{id}
Método: PUT
Ejemplo de Solicitud:

{
"nombre": "Maximiliano Sastre",
"dni": "22222222"
}

Ejemplo de Error

{
"timestamp": "2024-10-30T11:00:00",
"status": 400,
"error": "Bad Request",
"message": "Datos inválidos para actualizar el cliente",
"path": "/api/clientes/1"
}

Eliminar Cliente
Endpoint: /api/clientes/{id}
Método: DELETE
Ejemplo de Respuesta Exitosa

{
"status": 200,
"message": "Cliente eliminado correctamente"
}

Ejemplo de Error

{
"timestamp": "2024-10-30T11:05:00",
"status": 404,
"error": "Not Found",
"message": "Cliente no encontrado",
"path": "/api/clientes/1"
}

Gestión de Productos
Crear Producto
Endpoint: /api/productos
Método: POST
Ejemplo de Solicitud:

{
"nombre": "Producto X",
"precio": 50.0,
"stock": 100
}

Ejemplo de Error

{
"timestamp": "2024-10-30T10:40:00",
"status": 400,
"error": "Bad Request",
"message": "El producto ya existe",
"path": "/api/productos"
}

Actualizar Producto
Endpoint: /api/productos/{id}
Método: PUT
Ejemplo de Solicitud:

{
"nombre": "Producto X Actualizado",
"precio": 60.0,
"stock": 90
}

Ejemplo de Error

{
"timestamp": "2024-10-30T11:15:00",
"status": 400,
"error": "Bad Request",
"message": "Error al actualizar el producto",
"path": "/api/productos/1"
}

Eliminar Producto
Endpoint: /api/productos/{id}
Método: DELETE
Ejemplo de Respuesta Exitosa

{
"status": 200,
"message": "Producto eliminado correctamente"
}

Ejemplo de Error

{
"timestamp": "2024-10-30T11:20:00",
"status": 404,
"error": "Not Found",
"message": "Producto no encontrado",
"path": "/api/productos/1"
}

Manejo de Excepciones y Códigos de Error
El sistema maneja diferentes tipos de errores para proporcionar respuestas claras y consistentes. Los errores se gestionan con códigos HTTP y mensajes detallados. Aquí están los posibles códigos de error:

• 400 Bad Request: Cuando los datos enviados son inválidos o incompletos. Este error es común para datos de entrada incorrectos en los métodos POST y PUT.
• 404 Not Found: Devuelto cuando el recurso solicitado no existe, como cuando se intenta acceder a un cliente o producto inexistente con un ID específico.
• 500 Internal Server Error: Ocurre por problemas internos, como errores en la conexión a la base de datos o excepciones no controladas en la lógica de negocio.

Cada excepción en el sistema está capturada en el controlador correspondiente o en clases globales de manejo de errores, lo cual asegura que se envíe un mensaje claro y un código de estado adecuado.

Servicio REST para Fecha del Comprobante
El sistema obtiene la fecha del comprobante utilizando el servicio Time API. La dirección http://timeapi.io/api/Time/current/zone?timeZone=America/Argentina/Buenos_Aires se configura en un archivo de configuración para permitir obtener la fecha en el formato local de Argentina y se llama desde el servicio de comprobantes cuando se necesita generar la fecha para un nuevo comprobante.

Pruebas con Postman
Crea una colección en Postman con el nombre del proyecto.
Configura los endpoints de cliente, producto y factura con los ejemplos anteriores.
Envía peticiones con los datos proporcionados y verifica las respuestas.
Uso de Swagger
Para ver la documentación de la API y probar los endpoints, accede a http://localhost:8080/swagger-ui.html. Swagger ofrece una interfaz interactiva donde puedes ver y probar cada endpoint de la API. Swagger documenta automáticamente todos los endpoints de la aplicación y permite probar solicitudes directamente en el navegador sin necesidad de herramientas adicionales. Esto es útil para validar rápidamente los diferentes métodos y verificar las respuestas que genera el sistema, incluyendo ejemplos de datos de prueba, parámetros de entrada y respuestas de error.

Explicación Detallada de Componentes
Controladores (Controllers)
• Los controladores actúan como el punto de entrada para todas las solicitudes HTTP que la aplicación recibe, con métodos específicos para cada tipo de solicitud (GET, POST, PUT, DELETE).
• Por ejemplo, el controlador de ClienteController recibe solicitudes para crear, buscar, actualizar o eliminar clientes y asegura que los datos entrantes cumplen con las validaciones antes de enviarlos a los servicios.
• También formatea las respuestas de la aplicación en JSON, permitiendo comunicación con herramientas externas.
• Gestionan errores y devuelven códigos HTTP apropiados (como 400 para solicitudes incorrectas o 404 para elementos no encontrados).

Servicios (Services)
• Gestionan la lógica de negocio de la aplicación y las validaciones complejas. En FacturaService, por ejemplo, se verifica el stock disponible antes de crear una factura.
• Los servicios son intermediarios entre los controladores y los repositorios, asegurando que se apliquen reglas de negocio antes de que los datos se almacenen en la base de datos.
• Separan la lógica de negocio, permitiendo que los controladores gestionen únicamente la interacción con los usuarios y los repositorios se enfoquen en la interacción directa con la base de datos.

Repositorios (Repositories)
• Los repositorios interactúan directamente con la base de datos mediante JPA. Cada repositorio contiene métodos para crear, leer, actualizar y eliminar entidades.
• Pueden incluir consultas personalizadas, como un método para buscar productos por nombre o clientes por DNI.
• Mantienen la eficiencia de las consultas, optimizando el acceso a los datos y asegurando que solo la información necesaria se traiga a la aplicación.

Entidades (Entities)
• Las entidades reflejan las tablas de la base de datos en el código. Por ejemplo, la entidad Producto tiene atributos nombre, precio, y stock, que corresponden a columnas en la base de datos.
• Las relaciones, como OneToMany entre Factura y Producto, se gestionan con anotaciones para reflejar las relaciones entre tablas y asegurar la integridad referencial.
• Las entidades mantienen la estructura de los datos y limitan la entrada de datos incorrectos, asegurando que los registros sean consistentes con el diseño de la base de datos.

Configuraciones (Configuration)
• Las configuraciones de la aplicación están definidas en application.properties, donde se especifican las credenciales de la base de datos y otros parámetros esenciales.
• Configuran Swagger para documentar automáticamente los endpoints y permiten probar la API sin herramientas adicionales.
• Incluyen otras opciones de personalización que adaptan la aplicación a distintos entornos, facilitando ajustes rápidos según el entorno de desarrollo o producción.

Maximiliano Sastre.
