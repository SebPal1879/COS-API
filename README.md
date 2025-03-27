# 📚 Sistema de Biblioteca

Este proyecto es un sistema de gestión de biblioteca desarrollado con **Spring Boot** para el backend y **Angular 19** para el frontend. Permite gestionar usuarios, libros y préstamos de una biblioteca de manera eficiente.

## 🚀 Tecnologías Utilizadas

### Backend:

- **Spring Boot** (Framework principal)
- **Spring Data JPA** (Manejo de la base de datos)
- **PostgreSQL** (Base de datos relacional)
- **JUnit** (Pruebas unitarias)
- Java 17

### Frontend:

- **Angular 19** (Framework principal)
- **Angular Material** (Interfaz gráfica moderna)
- **RxJS** (Manejo de estados y observables)
- **TypeScript** (Lenguaje de programación)

## 📂 Estructura del Proyecto

```
📦 sistema-biblioteca
├── 📁 biblioteca        # Backend (Spring Boot)
├── 📁 biblioteca-app    # Frontend (Angular 19)
└── README.md           # Documentación del proyecto
```

---

## 🎯 Características Principales

- **Gestión de Usuarios**: Crear, editar, eliminar y listar usuarios.
- **Gestión de Libros**: Agregar, editar, eliminar y consultar libros.
- **Gestión de Préstamos**: Registrar préstamos, devolver libros y ver el historial de préstamos.
- **Indicadores de Préstamos**: Resumen de préstamos activos y finalizados.
- **Exportar información a excel**: Exportar a excel Resumen de préstamos activos y finalizados.
- **Interfaz Amigable**: Diseño atractivo y responsive con Angular Material.

## 🛠️ Instalación y Configuración

### 🔹 1. Clonar el repositorio

```bash
git clone https://github.com/EdTabares/biblioteca
```

### 🔹 2. Configurar el Backend (Spring Boot)

1. Acceder a la carpeta del backend:
   ```bash
   cd sistema-biblioteca/biblioteca
   ```
2. Configurar la base de datos en `application.properties` o `application.yml`:
   ```properties
spring.datasource.url=jdbc:mysql://localhost:3306/biblioteca
spring.datasource.username=root
spring.datasource.password=contraseña
   ```
3. Construir y ejecutar el backend:
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

### 🔹 3. Configurar el Frontend (Angular 19)

1. Acceder a la carpeta del frontend:
   ```bash
   cd ../biblioteca-app
   ```
2. Instalar dependencias:
   ```bash
   npm install
   ```
3. Ejecutar el servidor de Angular:
   ```bash
   ng serve
   ```
4. Acceder a la aplicación en el navegador:
   ```
   http://localhost:4200
   ```
   
## ✅ Pruebas

Para ejecutar las pruebas unitarias en el backend:

```bash
mvn test
```



