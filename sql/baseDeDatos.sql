CREATE TABLE Usuarios (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    correo VARCHAR(100) UNIQUE NOT NULL,
    telefono INT UNIQUE,
    contraseña VARCHAR(255) NOT NULL,
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabla de Organizadores
CREATE TABLE Organizadores (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    correo VARCHAR(100) UNIQUE NOT NULL,
    telefono INT UNIQUE,
    contraseña VARCHAR(255) NOT NULL,
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabla de Ubicaciones (como entidad independiente)
CREATE TABLE Ubicaciones (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    tipo_ubicacion ENUM('ONLINE', 'PRESENCIAL') NOT NULL,
    direccion VARCHAR(255),
    ciudad VARCHAR(100),
    codigo_postal VARCHAR(10)
);

-- Tabla de Categorías
CREATE TABLE Categorias (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(50) UNIQUE NOT NULL
);

-- Tabla de Eventos 
CREATE TABLE Eventos (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    categoria_id BIGINT NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    fecha DATE NOT NULL,
    duracion DOUBLE,
    ubicacion_id BIGINT,
    organizador_id BIGINT NOT NULL,
    estado ENUM('ACTIVO', 'CANCELADO', 'FINALIZADO') DEFAULT 'ACTIVO',
    FOREIGN KEY (categoria_id) REFERENCES Categorias(id),
    FOREIGN KEY (ubicacion_id) REFERENCES Ubicaciones(id),
    FOREIGN KEY (organizador_id) REFERENCES Organizadores(id)
);

-- Tabla de Inscripciones (tabla intermedia entre Eventos y Usuarios)
CREATE TABLE Inscripciones (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    evento_id BIGINT NOT NULL,
    usuario_id BIGINT NOT NULL,
    fecha_inscripcion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (evento_id) REFERENCES Eventos(id) ON DELETE CASCADE,
    FOREIGN KEY (usuario_id) REFERENCES Usuarios(id) ON DELETE CASCADE,
    UNIQUE KEY unique_inscripcion (evento_id, usuario_id)
);