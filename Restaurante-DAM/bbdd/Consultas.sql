-- ============================================================
-- TFG DAM 1º - Sistema de Gestión de Restaurante (Oskar,Iker,Joel)
-- ============================================================

USE restaurante_vacio_espiritual_db;



-- ------------------------------------------------------------
-- 1.1 Ver todos los platos activos con su categoría
-- ------------------------------------------------------------
SELECT cp.nombre AS categoria, p.nombre AS plato, p.precio
FROM Plato p
JOIN CategoriaPlato cp ON p.id_categoria = cp.id_categoria
WHERE p.activo = 1
ORDER BY cp.nombre, p.precio;


-- ------------------------------------------------------------
-- 1.2 Ver el estado de todas las mesas
-- ------------------------------------------------------------
SELECT numero, capacidad, estado
FROM Mesa
ORDER BY numero;


-- ------------------------------------------------------------
-- 1.3 Ver pedidos abiertos con el camarero y la mesa asignada
-- ------------------------------------------------------------
SELECT p.id_pedido, m.numero AS mesa, CONCAT(e.nombre, ' ', e.prApellido) AS camarero,
       p.fecha_hora, p.estado
FROM Pedido p
JOIN Empleado e ON p.id_empleado = e.id_empleado
LEFT JOIN Mesa m ON p.id_mesa = m.id_mesa
WHERE p.estado NOT IN ('entregado', 'cancelado')
ORDER BY p.fecha_hora;


-- ------------------------------------------------------------
-- 1.4 Ver el detalle completo de un pedido (ej: pedido 1)
-- ------------------------------------------------------------
SELECT pl.nombre AS plato, dp.cantidad, dp.subtotal
FROM DetallePedido dp
JOIN Plato pl ON dp.id_plato = pl.id_plato
WHERE dp.id_pedido = 1;


-- ------------------------------------------------------------
-- 1.5 Total facturado por método de pago
-- ------------------------------------------------------------
SELECT metodo_pago, SUM(total) AS total
FROM Factura
GROUP BY metodo_pago
ORDER BY total DESC;


-- ------------------------------------------------------------
-- 1.6 Reservas confirmadas para los próximos 7 días
-- ------------------------------------------------------------
SELECT r.id_reserva, CONCAT(c.nombre, ' ', c.prApellido) AS cliente,
       m.numero AS mesa, r.fecha
FROM Reserva r
JOIN Cliente c ON r.id_cliente = c.id_cliente
JOIN Mesa m ON r.id_mesa = m.id_mesa
WHERE r.fecha BETWEEN CURDATE() AND DATE_ADD(CURDATE(), INTERVAL 7 DAY)
  AND r.estado = 'confirmada'
ORDER BY r.fecha;


-- ------------------------------------------------------------
-- 1.7 Platos más pedidos
-- ------------------------------------------------------------
SELECT pl.nombre AS plato, SUM(dp.cantidad) AS vendidos
FROM DetallePedido dp
JOIN Plato pl ON dp.id_plato = pl.id_plato
GROUP BY pl.id_plato
ORDER BY vendidos DESC;

-- ------------------------------------------------------------
-- 1.8 Stock bajo mínimo
-- ------------------------------------------------------------
SELECT pl.nombre AS plato, i.stock_actual, i.stock_minimo
FROM Inventario i
JOIN Plato pl ON i.id_plato = pl.id_plato
WHERE i.stock_actual <= i.stock_minimo;

-- ------------------------------------------------------------
-- 1.9 Clientes con más pedidos realizados
-- ------------------------------------------------------------
SELECT CONCAT(c.nombre, ' ', c.prApellido) AS cliente,
       COUNT(*) AS pedidos
FROM Cliente c
JOIN Pedido p ON c.id_cliente = p.id_cliente
GROUP BY c.id_cliente
ORDER BY pedidos DESC;


-- ------------------------------------------------------------
-- 1.10 Empleados y sus pedidos gestionados hoy
-- ------------------------------------------------------------
SELECT CONCAT(e.nombre, ' ', e.prApellido) AS empleado,
       COUNT(p.id_pedido) AS pedidos
FROM Empleado e
LEFT JOIN Pedido p ON e.id_empleado = p.id_empleado
  AND DATE(p.fecha_hora) = CURDATE()
GROUP BY e.id_empleado
ORDER BY pedidos DESC;



-- ============================================================
-- SECCIÓN 2 – VISTAS
-- ============================================================

-- ------------------------------------------------------------
-- Vista: Menú activo completo
-- ------------------------------------------------------------
CREATE  VIEW v_menu_activo AS
SELECT cp.nombre AS categoria, p.nombre AS plato, p.precio
FROM Plato p
JOIN CategoriaPlato cp ON p.id_categoria = cp.id_categoria
WHERE p.activo = 1;


-- ------------------------------------------------------------
-- Vista: Estado actual de las mesas
-- ------------------------------------------------------------
CREATE VIEW v_estado_mesas AS
SELECT m.numero AS mesa, m.capacidad, m.estado,
       p.id_pedido, p.estado AS estado_pedido
FROM Mesa m
LEFT JOIN Pedido p ON m.id_mesa = p.id_mesa
  AND p.estado NOT IN ('entregado', 'cancelado');


-- ------------------------------------------------------------
-- Vista: Resumen de ventas por día
-- ------------------------------------------------------------
CREATE VIEW v_ventas_diarias AS
SELECT DATE(fecha) AS dia, SUM(total) AS total
FROM Factura
GROUP BY DATE(fecha)
ORDER BY dia DESC;


-- ------------------------------------------------------------
-- Vista: Inventario con alerta de stock
-- ------------------------------------------------------------
CREATE VIEW v_inventario_alertas AS
SELECT pl.nombre AS plato, i.stock_actual, i.stock_minimo,
       CASE
           WHEN i.stock_actual = 0 THEN 'SIN STOCK'
           WHEN i.stock_actual <= i.stock_minimo THEN 'STOCK BAJO'
           ELSE 'OK'
       END AS estado
FROM Inventario i
JOIN Plato pl ON i.id_plato = pl.id_plato;




-- ============================================================
-- SECCIÓN 3 – CONSULTAS DML (INSERT, UPDATE, DELETE)
-- ============================================================

-- ------------------------------------------------------------
-- 3.1 Cambiar el estado de una mesa a 'ocupada'
-- ------------------------------------------------------------
UPDATE Mesa
SET estado = 'ocupada'
WHERE id_mesa = 4;

-- ------------------------------------------------------------
-- 3.2 Cancelar una reserva
-- ------------------------------------------------------------
UPDATE Reserva
SET estado = 'cancelada'
WHERE id_reserva = 2;

-- ------------------------------------------------------------
-- 3.3 Marcar un pedido como entregado
-- ------------------------------------------------------------
UPDATE Pedido
SET estado = 'entregado'
WHERE id_pedido = 2;

-- ------------------------------------------------------------
-- 3.4 Desactivar un plato del menú (baja lógica)
-- ------------------------------------------------------------
UPDATE Plato
SET activo = 0
WHERE id_plato = 12; -- Flan de huevo

-- ------------------------------------------------------------
-- 3.5 Actualizar el stock después de servir un pedido
-- ------------------------------------------------------------
UPDATE Inventario
SET stock_actual = stock_actual - 1
WHERE id_plato = 7; -- Entrecot

-- ------------------------------------------------------------
-- 3.6 Añadir un nuevo plato al menú
-- ------------------------------------------------------------
INSERT INTO Plato (id_categoria, nombre, descripcion, precio, activo)
VALUES (3, 'Lubina a la sal', 'Lubina entera cocinada a la sal', 24.00, 1);


-- ------------------------------------------------------------
-- 3.7 Eliminar un cliente sin pedidos asociados
-- ------------------------------------------------------------
DELETE FROM Cliente
WHERE id_cliente = 6
  AND id_cliente NOT IN (SELECT DISTINCT id_cliente FROM Pedido WHERE id_cliente IS NOT NULL);

-- ============================================================
-- FIN DEL SCRIPT 03
-- ============================================================
