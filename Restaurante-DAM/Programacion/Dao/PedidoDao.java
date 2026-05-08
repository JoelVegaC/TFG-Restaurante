package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PedidoDao {
    public boolean crearPedido (Connection conex, int cliente, int empleado, int mesa, String tipo, String estado, double total ) throws SQLException{
        String consulta = "INSERT INTO  Pedido (id_cliente, id_empleado, id_mesa, tipo, estado, total)Values (?, ?, ? ,? , ?, ?)";
        try (PreparedStatement pstmt = conex.prepareStatement(consulta)) {
            // Recogemos y sustituimos los valores por los ?
            pstmt.setInt(1, cliente);
            pstmt.setInt(2, empleado);
            pstmt.setInt(3, mesa);
            pstmt.setString(4, tipo);
            pstmt.setString(5, estado);
            pstmt.setDouble(6, total);

            

            int filas = pstmt.executeUpdate();
            return filas > 0;

        } catch (SQLException e) {
            System.out.println("Error al crear el pedido: " + e.getMessage());
            return false;
        }
    }
    public static boolean actualizarPedido(Connection conexion, String estado, Double total) {
        // Buscamos el ID del último pedido creado
        String sqlId = "SELECT MAX(id_pedido) AS ultimo_id FROM Pedido";
        String consulta = "UPDATE Pedido SET estado = ?, total = ? WHERE id_pedido = ?";

        try {
            int idPedido = 0;
            try (PreparedStatement pstmtId = conexion.prepareStatement(sqlId);
                ResultSet rs = pstmtId.executeQuery()) {
                if (rs.next()) {
                    idPedido = rs.getInt("ultimo_id");
                }
            }

            // Si encontramos el pedido, lo actualizamos
            try (PreparedStatement pstmt = conexion.prepareStatement(consulta)) {
                pstmt.setString(1, estado); 
                pstmt.setDouble(2, total);  
                pstmt.setInt(3, idPedido);  
                
                int filasAfectadas = pstmt.executeUpdate();
                return filasAfectadas > 0;
            }

        } catch (SQLException e) {
            System.out.println("Error al actualizar pedido: " + e.getMessage());
            return false;
        }
    }
    
}
