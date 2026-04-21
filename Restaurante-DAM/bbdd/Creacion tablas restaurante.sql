-- ============================================================
-- TFG DAM 1º - Sistema de Gestión de Restaurante (Oskar,Iker,Joel)
-- ============================================================

DROP DATABASE IF EXISTS restaurante_vacio_espiritual_db;
CREATE DATABASE restaurante_vacio_espiritual_db;
USE restaurante_vacio_espiritual_db;

CREATE TABLE Rol (
    id_rol INT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(50) NOT NULL UNIQUE,
    descripcion VARCHAR(200),
    PRIMARY KEY (id_rol)
);

CREATE TABLE Empleado (
    id_empleado INT NOT NULL AUTO_INCREMENT,
    rol_id INT NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    prApellido VARCHAR(100) NOT NULL,
    sgApellido VARCHAR(100),
    dni CHAR(9) NOT NULL UNIQUE,
    telefono VARCHAR(15),
    PRIMARY KEY (id_empleado),
    CONSTRAINT fk_empleado_rol FOREIGN KEY (rol_id)
        REFERENCES Rol(id_rol)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
);

CREATE TABLE Cliente (
    id_cliente INT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    prApellido VARCHAR(100) NOT NULL,
    sgApellido VARCHAR(100),
    dni CHAR(9) UNIQUE,
    telefono VARCHAR(15),
    email VARCHAR(150) UNIQUE,
    PRIMARY KEY (id_cliente)
);

CREATE TABLE Mesa (
    id_mesa INT NOT NULL AUTO_INCREMENT,
    numero INT NOT NULL UNIQUE,
    capacidad INT NOT NULL CHECK (capacidad > 0),
    estado ENUM('libre', 'ocupada', 'reservada') NOT NULL DEFAULT 'libre',
    PRIMARY KEY (id_mesa)
);

CREATE TABLE Reserva (
    id_reserva INT NOT NULL AUTO_INCREMENT,
    id_cliente INT NOT NULL,
    id_mesa INT NOT NULL,
    fecha DATE NOT NULL,
    num_personas INT NOT NULL CHECK (num_personas > 0),
    estado ENUM('pendiente', 'confirmada', 'cancelada', 'completada') NOT NULL DEFAULT 'pendiente',
    PRIMARY KEY (id_reserva),
    CONSTRAINT fk_reserva_cliente FOREIGN KEY (id_cliente)
        REFERENCES Cliente(id_cliente)
        ON UPDATE CASCADE
        ON DELETE RESTRICT,
    CONSTRAINT fk_reserva_mesa FOREIGN KEY (id_mesa)
        REFERENCES Mesa(id_mesa)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
);

CREATE TABLE Pedido (
    id_pedido INT NOT NULL AUTO_INCREMENT,
    id_cliente INT,
    id_empleado INT NOT NULL,
    id_mesa INT,
    fecha_hora DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    tipo ENUM('local', 'para_llevar', 'delivery') NOT NULL DEFAULT 'local',
    estado ENUM('abierto', 'en_cocina', 'listo', 'entregado', 'cancelado') NOT NULL DEFAULT 'abierto',
    total DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    PRIMARY KEY (id_pedido),
    CONSTRAINT fk_pedido_cliente FOREIGN KEY (id_cliente)
        REFERENCES Cliente(id_cliente)
        ON UPDATE CASCADE
        ON DELETE SET NULL,
    CONSTRAINT fk_pedido_empleado FOREIGN KEY (id_empleado)
        REFERENCES Empleado(id_empleado)
        ON UPDATE CASCADE
        ON DELETE RESTRICT,
    CONSTRAINT fk_pedido_mesa FOREIGN KEY (id_mesa)
        REFERENCES Mesa(id_mesa)
        ON UPDATE CASCADE
        ON DELETE SET NULL
);

CREATE TABLE CategoriaPlato (
    id_categoria INT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL UNIQUE,
    descripcion VARCHAR(200),
    PRIMARY KEY (id_categoria)
);

CREATE TABLE Plato (
    id_plato INT NOT NULL AUTO_INCREMENT,
    id_categoria INT NOT NULL,
    nombre VARCHAR(150) NOT NULL,
    descripcion VARCHAR(300),
    precio DECIMAL(8,2) NOT NULL CHECK (precio >= 0),
    activo TINYINT(1) NOT NULL DEFAULT 1,
    PRIMARY KEY (id_plato),
    CONSTRAINT fk_plato_categoria FOREIGN KEY (id_categoria)
        REFERENCES CategoriaPlato(id_categoria)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
);

CREATE TABLE DetallePedido (
    id_detalle INT NOT NULL AUTO_INCREMENT,
    id_pedido INT NOT NULL,
    id_plato INT NOT NULL,
    cantidad INT NOT NULL CHECK (cantidad > 0),
    precio_unitario DECIMAL(8,2) NOT NULL,
    subtotal DECIMAL(10,2) NOT NULL,
    PRIMARY KEY (id_detalle),
    CONSTRAINT fk_detalle_pedido FOREIGN KEY (id_pedido)
        REFERENCES Pedido(id_pedido)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT fk_detalle_plato FOREIGN KEY (id_plato)
        REFERENCES Plato(id_plato)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
);

CREATE TABLE Factura (
    id_factura INT NOT NULL AUTO_INCREMENT,
    id_pedido INT NOT NULL UNIQUE,
    fecha DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    total DECIMAL(10,2) NOT NULL,
    metodo_pago ENUM('efectivo','tarjeta','bizum','transferencia') NOT NULL DEFAULT 'efectivo',
    PRIMARY KEY (id_factura),
    CONSTRAINT fk_factura_pedido FOREIGN KEY (id_pedido)
        REFERENCES Pedido(id_pedido)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
);

CREATE TABLE DetalleFactura (
    id_detalle_factura INT NOT NULL AUTO_INCREMENT,
    id_factura INT NOT NULL,
    id_plato INT NOT NULL,
    cantidad INT NOT NULL CHECK (cantidad > 0),
    precio_unitario DECIMAL(8,2) NOT NULL,
    subtotal DECIMAL(10,2) NOT NULL,
    PRIMARY KEY (id_detalle_factura),
    CONSTRAINT fk_det_factura_factura FOREIGN KEY (id_factura)
        REFERENCES Factura(id_factura)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT fk_det_factura_plato FOREIGN KEY (id_plato)
        REFERENCES Plato(id_plato)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
);

CREATE TABLE Inventario (
    id_inventario INT NOT NULL AUTO_INCREMENT,
    id_plato INT NOT NULL UNIQUE,
    stock_actual INT NOT NULL DEFAULT 0 CHECK (stock_actual >= 0),
    stock_minimo INT NOT NULL DEFAULT 5 CHECK (stock_minimo >= 0),
    PRIMARY KEY (id_inventario),
    CONSTRAINT fk_inventario_plato FOREIGN KEY (id_plato)
        REFERENCES Plato(id_plato)
        ON UPDATE CASCADE
        ON DELETE CASCADE
);
