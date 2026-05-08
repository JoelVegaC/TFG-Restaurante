package Dao;

import VO.MesaVo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MesaDao {
    public List<MesaVo> obtenerMesasAdecuadas(Connection conexion, int numPersonas) throws SQLException {
        List<MesaVo> mesas = new ArrayList<>();
        
        // El primer ? es el mínimo (lo que pide el usuario)
        // El segundo ? es para calcular el límite superior (+3)
        String sql = "SELECT * FROM Mesa WHERE estado = 'libre' AND capacidad BETWEEN ? AND (? + 3) ORDER BY capacidad ASC";

        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setInt(1, numPersonas);
            pstmt.setInt(2, numPersonas);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    mesas.add(new MesaVo(
                        rs.getInt("numero"),
                        rs.getInt("capacidad"),
                        rs.getString("estado")
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar mesas: " + e.getMessage());
        }
        return mesas;
    }

    public boolean cambiarEstadoMesa(Connection conexion, int mesa, String estado) {
        // Definimos la consulta
        String consulta = "UPDATE Mesa SET estado = ? WHERE numero = ?";
        
        
        try (PreparedStatement pstmt = conexion.prepareStatement(consulta)) {
            
            pstmt.setString(1, estado);
            pstmt.setInt(2, mesa);
            
            int filasAfectadas = pstmt.executeUpdate();
            
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            System.err.println("Error al actualizar el estado de la mesa: " + e.getMessage());
            return false;
        }
    }
}
