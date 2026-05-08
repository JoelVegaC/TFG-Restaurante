package Dao;

import VO.DetallePedidoVo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DetallePedidoDao {
    public List<DetallePedidoVo> obtenerDetallesUltimoPedido(Connection conexion) {
        List<DetallePedidoVo> listaDetalles = new ArrayList<>();
        
        // Creamos las consultas necesarias
        String sqlId = "SELECT MAX(id_pedido) AS ultimo_id FROM Pedido";
        String consulta = "SELECT id_pedido, id_plato, cantidad, precio_unitario, subtotal FROM DetallePedido WHERE id_pedido = ?";

        try {
            int idPedido = 0;

            // Obtenemos el último ID
            try (PreparedStatement pstmtId = conexion.prepareStatement(sqlId);
                ResultSet rsId = pstmtId.executeQuery()) {
                if (rsId.next()) {
                    idPedido = rsId.getInt("ultimo_id");
                }
            }

            // Si encontramos un ID válido, buscamos sus detalles
            if (idPedido > 0) {
                try (PreparedStatement pstmt = conexion.prepareStatement(consulta)) {
                    pstmt.setInt(1, idPedido); // Asignamos el ID obtenido

                    try (ResultSet rs = pstmt.executeQuery()) {
                        while (rs.next()) {
                            // Creamos el objeto con los datos de la fila actual
                            DetallePedidoVo detalle = new DetallePedidoVo(
                                rs.getInt("id_pedido"),
                                rs.getInt("id_plato"),
                                rs.getInt("cantidad"),
                                rs.getDouble("precio_unitario"),
                                rs.getDouble("subtotal")
                            );
                            listaDetalles.add(detalle);
                        }
                    }
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener los detalles del pedido: " + e.getMessage());
        }

        return listaDetalles;
    }
    
    public boolean añadirDetalle(Connection conexion, int platoId, int cantidad) {
        String precio = "SELECT precio FROM Plato WHERE id_plato = ?";
        String maxId = "SELECT MAX(id_pedido) AS ultimo_id FROM Pedido";
        String insert = "INSERT INTO DetallePedido (id_pedido, id_plato, cantidad, precio_unitario, subtotal) VALUES (?, ?, ?, ?, ?)";

        try {
            // Obtener el precio unitario del plato
            double precioUnitario = 0;
            try (PreparedStatement psPrecio = conexion.prepareStatement(precio)) {
                psPrecio.setInt(1, platoId);
                try (ResultSet rs = psPrecio.executeQuery()) {
                    if (rs.next()) {
                        precioUnitario = rs.getDouble("precio");
                    } else {
                        return false; 
                    }
                }
            }

            // Obtener el ID del pedido más reciente es decir el que acabamos de crear
            int idPedido = 0;
            try (PreparedStatement psMax = conexion.prepareStatement(maxId);
                 ResultSet rs = psMax.executeQuery()) {
                if (rs.next()) {
                    idPedido = rs.getInt("ultimo_id");
                }
            }

            // Calcular subtotal e Insertar el detalle
            double subtotal = precioUnitario * cantidad;

            try (PreparedStatement psInsert = conexion.prepareStatement(insert)) {
                psInsert.setInt(1, idPedido);
                psInsert.setInt(2, platoId);
                psInsert.setInt(3, cantidad);
                psInsert.setDouble(4, precioUnitario);
                psInsert.setDouble(5, subtotal);

                int filasAfectadas = psInsert.executeUpdate();
                return filasAfectadas > 0;
            }

        } catch (SQLException e) {
            System.err.println("Error al añadir detalle: " + e.getMessage());
            return false;
        }
    }
    
}