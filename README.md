# Clean Architecture Service

Este proyecto implementa una API RESTful siguiendo los principios de Clean Architecture con operaciones CRUD para gestión de usuarios.

## Estructura del Proyecto

```
src/main/java/com/juantapia/CleanArchitectureService/
├── domain/                          # Capa de Dominio
│   ├── model/                       # Modelos de dominio
│   │   └── User.java
│   └── port/                        # Interfaces/Ports
│       ├── UserRepository.java
│       └── UserService.java
├── application/                     # Capa de Aplicación
│   ├── dto/                         # Data Transfer Objects
│   │   ├── UserRequest.java
│   │   └── UserResponse.java
│   ├── command/                     # Commands (CQRS)
│   │   ├── CreateUserCommand.java
│   │   ├── UpdateUserCommand.java
│   │   └── DeleteUserCommand.java
│   ├── query/                       # Queries (CQRS)
│   │   ├── GetUserByIdQuery.java
│   │   └── GetAllUsersQuery.java
│   └── service/                     # Servicios de aplicación
│       └── UserApplicationService.java
├── infrastructure/                  # Capa de Infraestructura
│   ├── persistence/                 # Persistencia
│   │   ├── UserEntity.java
│   │   └── UserJpaRepository.java
│   └── adapter/                     # Adaptadores
│       └── UserRepositoryImpl.java
└── presentation/                    # Capa de Presentación
    └── controller/                  # Controllers REST
        ├── UserController.java
        └── GlobalExceptionHandler.java
```

## Tecnologías Utilizadas

- **Java 17**
- **Spring Boot 3.5.5**
- **Spring Data JPA**
- **H2 Database** (en memoria)
- **Lombok**
- **Maven**

## Cómo Ejecutar

1. Compilar el proyecto:
```bash
mvn clean compile
```

2. Ejecutar la aplicación:
```bash
mvn spring-boot:run
```

3. La aplicación estará disponible en: `http://localhost:8080`

4. Consola H2 disponible en: `http://localhost:8080/h2-console`
   - JDBC URL: `jdbc:h2:mem:testdb`
   - Usuario: `sa`
   - Contraseña: `password`

## API Endpoints

### Crear Usuario
```bash
POST /api/users
Content-Type: application/json

{
  "firstName": "Juan",
  "lastName": "Tapia",
  "email": "juan.tapia@example.com",
  "phoneNumber": "+1234567890"
}
```

### Obtener Todos los Usuarios
```bash
GET /api/users
```

### Obtener Usuario por ID
```bash
GET /api/users/{id}
```

### Actualizar Usuario
```bash
PUT /api/users/{id}
Content-Type: application/json

{
  "firstName": "Juan Carlos",
  "lastName": "Tapia Mendoza",
  "email": "juan.tapia@example.com",
  "phoneNumber": "+1234567891"
}
```

### Eliminar Usuario
```bash
DELETE /api/users/{id}
```

## Ejemplos de Respuesta

### Respuesta Exitosa (POST/GET/PUT):
```json
{
  "id": 1,
  "firstName": "Juan",
  "lastName": "Tapia",
  "fullName": "Juan Tapia",
  "email": "juan.tapia@example.com",
  "phoneNumber": "+1234567890",
  "createdAt": "2025-09-14T08:17:53.456211",
  "updatedAt": "2025-09-14T08:17:53.456226"
}
```

### Respuesta de Error de Validación:
```json
{
  "error": "Validation Failed",
  "errors": {
    "firstName": "First name must be between 2 and 50 characters",
    "email": "Email should be valid"
  },
  "timestamp": "2025-09-14T08:23:03.187361",
  "status": 400
}
```

## Principios de Clean Architecture Implementados

1. **Separación de Responsabilidades**: Cada capa tiene una responsabilidad específica
2. **Inversión de Dependencias**: La capa de dominio no depende de detalles externos
3. **Regla de Dependencias**: Las dependencias apuntan hacia el centro (dominio)
4. **CQRS**: Separación entre Commands (escritura) y Queries (lectura)
5. **Puertos y Adaptadores**: Interfaces en el dominio, implementaciones en infraestructura

## Validaciones Incluidas

- Nombre y apellido: entre 2 y 50 caracteres
- Email: formato válido
- Teléfono: máximo 20 caracteres
- Campos requeridos: firstName, lastName, email

## Base de Datos

- Se utiliza H2 en memoria para simplicidad
- La tabla se crea automáticamente al iniciar
- Los datos se pierden al reiniciar la aplicación