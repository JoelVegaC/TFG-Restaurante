-- ============================================================
-- TFG DAM 1º - Sistema de Gestión de Restaurante (Oskar,Iker,Joel)
-- ============================================================

USE restaurante_vacio_espiritual_db;

-- ------------------------------------------------------------
-- Roles del personal
-- ------------------------------------------------------------
INSERT INTO Rol (nombre, descripcion) VALUES
    ('Administrador', 'Acceso total al sistema y gestión general'),
    ('Camarero',      'Gestión de pedidos, mesas y atención al cliente'),
    ('Cocinero',      'Gestión de cocina, platos y stock');

-- ------------------------------------------------------------
-- Empleados
-- ------------------------------------------------------------
INSERT INTO Empleado (rol_id, nombre, prApellido, sgApellido, dni, telefono) VALUES
    (1, 'María',   'García',    'López',    '12345678A', '611000001'),
    (2, 'Carlos',  'Martínez',  'Ruiz',     '23456789B', '622000002'),
    (2, 'Lucía',   'Fernández', 'Sánchez',  '34567890C', '633000003'),
    (3, 'Alejandro','Pérez',    'Gómez',    '45678901D', '644000004'),
    (3, 'Sofía',   'Jiménez',   'Moreno',   '56789012E', '655000005');

-- ------------------------------------------------------------
-- Clientes
-- ------------------------------------------------------------
INSERT INTO Cliente (nombre, prApellido, sgApellido, dni, telefono, email) VALUES
    ('Juan',      'Rodríguez', 'Díaz',     '11111111F', '600100001', 'juan.rodriguez@email.com'),
    ('Ana',       'López',     'Torres',   '22222222G', '600100002', 'ana.lopez@email.com'),
    ('Pedro',     'González',  'Vargas',   '33333333H', '600100003', 'pedro.gonzalez@email.com'),
    ('Laura',     'Martín',    'Herrera',  '44444444J', '600100004', 'laura.martin@email.com'),
    ('Marcos',    'Sánchez',   NULL,       '55555555K', '600100005', 'marcos.sanchez@email.com'),
    ('Elena',     'Romero',    'Blanco',   '66666666L', '600100006', 'elena.romero@email.com');

-- ------------------------------------------------------------
-- Mesas
-- ------------------------------------------------------------
INSERT INTO Mesa (numero, capacidad, estado) VALUES
    (1,  2,  'libre'),
    (2,  2,  'libre'),
    (3,  4,  'ocupada'),
    (4,  4,  'libre'),
    (5,  6,  'reservada'),
    (6,  6,  'libre'),
    (7,  8,  'libre'),
    (8,  2,  'libre'),
    (9,  4,  'libre'),
    (10, 10, 'libre');

-- ------------------------------------------------------------
-- Categorías de platos
-- ------------------------------------------------------------
INSERT INTO CategoriaPlato (nombre, descripcion) VALUES
    ('Entrantes',   'Platos para compartir y aperitivos'),
    ('Primeros',    'Sopas, cremas, ensaladas y arroces'),
    ('Segundos',    'Carnes y pescados principales'),
    ('Postres',     'Dulces y helados'),
    ('Bebidas',     'Refrescos, agua, vinos y cervezas');

-- ------------------------------------------------------------
-- Platos del menú
-- ------------------------------------------------------------
INSERT INTO Plato (id_categoria, nombre, descripcion, precio, activo) VALUES
-- Entrantes
(1,'Croquetas infinitas','Croquetas caseras de jamón ibérico, crujientes por fuera y cremosas por dentro (6 uds)',8.50,1),
(1,'Patatas con carne mechada de Shibuya','Patatas fritas con carne mechada y salsa alioli-brava',6.00,1),
(1,'Tabla de quesitos','Selección de embutidos y quesos curados artesanales',14.00,1),
-- Primero
(2,'Makis con salsa de Yuta','Rollitos de arroz con atún, verduras frescas y salsa especial de la casa',7.50,1),
(2,'Inumaki Salmon Delux','Tartar de salmón marinado con cítricos y soja suave',5.50,1),
(2,'Nobara con clavos crujientes','Arroz salteado con setas de temporada y toque de ajo crujiente',12.00,1),
-- Segundo
(3,'Medio Sándwich de Goyo','Sándwich de pollo crujiente con queso fundido y salsa especiada',22.00,1),
(3,'Sukuna Finger Food','Filete de merluza al horno con guarnición de verduras asadas',18.50,1),
(3,'Hamburguesa de Geto','Hamburguesa de ternera con queso cheddar y patatas fritas',13.00,1),
-- Postre
(4,'Prision confinadora brownie','Brownie de chocolate negro con corazón fundido',5.50,1),
(4,'KitKat de Goyo','Brownie casero con helado de vainilla y sirope de chocolate',6.00,1),
(4,'Brazo de 35','Flan casero tradicional con caramelo',4.00,1),
-- Bebida
(5,'Té Zen´in','Té frío artesanal con limón',2.00,1),
(5,'Batido Panda Mode','Batido cremoso de chocolate o vainilla',2.50,1),
(5,'Sukuna Sour','Vino tinto, blanco o rosado servido en copa',2.50,1),
(5,'Black Flash Shot','Caña de cerveza o botellín frío',2.00,1);


-- ------------------------------------------------------------
-- Inventario (stock por plato)
-- ------------------------------------------------------------
INSERT INTO Inventario (id_plato, stock_actual, stock_minimo) VALUES
    (1,  30, 10),  -- Croquetas infinitas
    (2,  25, 10),  -- Patatas con carne mechada de Shibuya
    (3,  15,  5),  -- Tabla de quesitos
    (4,  40, 10),  -- Makis con salsa de Yuta
    (5,  20, 10),  -- Inumaki Salmon Delux
    (6,  18,  5),  -- Nobara con clavos crujientes
    (7,  12,  5),  -- Medio Sándwich de Goyo
    (8,  10,  5),  -- Sukuna Finger Food
    (9,  20,  5),  -- Hamburguesa de Geto
    (10, 15,  5),  -- Prision confinadora brownie
    (11, 12,  5),  -- KitKat de Goyo
    (12, 20,  8),  -- Brazo de 35
    (13, 50, 20),  -- Té Zen'in
    (14, 60, 20),  -- Batido Panda Mode
    (15, 40, 15),  -- Sukuna Sour
    (16, 80, 20);  -- Black Flash Shot


-- ------------------------------------------------------------
-- Reservas
-- ------------------------------------------------------------
INSERT INTO Reserva (id_cliente, id_mesa, fecha, num_personas, estado) VALUES
    (1, 5, '2025-04-22', 4, 'confirmada'),
    (2, 5, '2025-04-23', 2, 'pendiente'),
    (3, 7, '2025-04-25', 6, 'confirmada'),
    (4, 3, '2025-04-20', 3, 'completada'),
    (5, 4, '2025-04-21', 2, 'cancelada'),
    (6, 6, '2025-04-26', 5, 'pendiente');

-- ------------------------------------------------------------
-- Pedidos
-- ------------------------------------------------------------
INSERT INTO Pedido (id_cliente, id_empleado, id_mesa, fecha_hora, tipo, estado, total) VALUES
    (1, 2, 3, '2025-04-20 13:30:00', 'local',      'entregado', 52.50),
    (2, 2, 1, '2025-04-20 14:00:00', 'local',      'en_cocina', 27.00),
    (3, 3, 2, '2025-04-20 14:15:00', 'local',      'abierto',   18.50),
    (NULL, 3, NULL, '2025-04-20 13:00:00', 'para_llevar', 'entregado', 35.00),
    (4, 2, 4, '2025-04-20 14:30:00', 'local',      'listo',     40.00);

-- ------------------------------------------------------------
-- Detalle de pedidos
-- ------------------------------------------------------------
-- Pedido 1: entrecot + patatas + agua + flan
INSERT INTO DetallePedido (id_pedido, id_plato, cantidad, precio_unitario, subtotal) VALUES
    (1, 7,  1, 22.00, 22.00),   -- Entrecot
    (1, 2,  1,  6.00,  6.00),   -- Patatas bravas
    (1, 13, 1,  2.00,  2.00),   -- Agua
    (1, 16, 2,  2.00,  4.00),   -- Cerveza x2
    (1, 12, 2,  4.00,  8.00),   -- Flan x2
    (1, 15, 2,  2.50,  5.00);   -- Vino x2  → total: 47.00 (aprox 52.50 con IVA)

-- Pedido 2: ensalada + pollo + refresco
INSERT INTO DetallePedido (id_pedido, id_plato, cantidad, precio_unitario, subtotal) VALUES
    (2, 4,  1,  7.50,  7.50),   -- Ensalada
    (2, 9,  1, 13.00, 13.00),   -- Pollo asado
    (2, 14, 2,  2.50,  5.00);   -- Refresco x2

-- Pedido 3: gazpacho + merluza + agua
INSERT INTO DetallePedido (id_pedido, id_plato, cantidad, precio_unitario, subtotal) VALUES
    (3, 5,  1,  5.50,  5.50),   -- Gazpacho
    (3, 8,  1, 18.50, 18.50),   -- Merluza
    (3, 13, 1,  2.00,  2.00);   -- Agua

-- Pedido 4 (para llevar): croquetas + tabla ibéricos + tarta
INSERT INTO DetallePedido (id_pedido, id_plato, cantidad, precio_unitario, subtotal) VALUES
    (4, 1,  2,  8.50, 17.00),   -- Croquetas x2
    (4, 3,  1, 14.00, 14.00),   -- Tabla ibéricos
    (4, 10, 1,  5.50,  5.50);   -- Tarta de queso

-- Pedido 5: arroz + entrecot + vino + brownie
INSERT INTO DetallePedido (id_pedido, id_plato, cantidad, precio_unitario, subtotal) VALUES
    (5, 6,  1, 12.00, 12.00),   -- Arroz meloso
    (5, 7,  1, 22.00, 22.00),   -- Entrecot
    (5, 15, 2,  2.50,  5.00),   -- Vino x2
    (5, 11, 1,  6.00,  6.00);   -- Brownie  → 45.00 ≈ 40.00 (redondeado)

-- ------------------------------------------------------------
-- Facturas (para pedidos entregados)
-- ------------------------------------------------------------
INSERT INTO Factura (id_pedido, fecha, total, metodo_pago) VALUES
    (1, '2025-04-20 15:00:00', 52.50, 'tarjeta'),
    (4, '2025-04-20 13:45:00', 35.00, 'efectivo');

-- ------------------------------------------------------------
-- Detalle de facturas (histórico)
-- ------------------------------------------------------------
-- Factura 1 → copia de Pedido 1
INSERT INTO DetalleFactura (id_factura, id_plato, cantidad, precio_unitario, subtotal) VALUES
    (1, 7,  1, 22.00, 22.00),
    (1, 2,  1,  6.00,  6.00),
    (1, 13, 1,  2.00,  2.00),
    (1, 16, 2,  2.00,  4.00),
    (1, 12, 2,  4.00,  8.00),
    (1, 15, 2,  2.50,  5.00);

-- Factura 2 → copia de Pedido 4
INSERT INTO DetalleFactura (id_factura, id_plato, cantidad, precio_unitario, subtotal) VALUES
    (2, 1,  2,  8.50, 17.00),
    (2, 3,  1, 14.00, 14.00),
    (2, 10, 1,  5.50,  5.50);

-- ============================================================
-- FIN DEL SCRIPT 02
-- ============================================================
