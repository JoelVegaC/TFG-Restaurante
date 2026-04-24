package Dao;

import VO.InventarioVo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class InventarioDao {
    public List<InventarioVo> ObtenInventarioVo(Connection conexion) throws SQLException {
        List<InventarioVo> inventario = new ArrayList<>();

        // Consultamos id y stock, asi como un join para obtener el nombre de cada id
        String consulta = "SELECT i.id_plato, p.nombre, i.stock_actual " +
                        "FROM Inventario i " +
                        "INNER JOIN Plato p ON i.id_plato = p.id_plato";

        try (Statement stmt = conexion.createStatement();
            ResultSet resultado = stmt.executeQuery(consulta)) {
            
            while (resultado.next()) {
                // Extraemos los datos
                int idPlato = resultado.getInt("id_plato");
                String nombre = resultado.getString("nombre");
                int actual = resultado.getInt("stock_actual");

                // Usamos el constructor para crear el objeto
                InventarioVo stock = new InventarioVo(idPlato, nombre, actual);
                // Añadimos el objeto a la lista
                inventario.add(stock);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        } 

        return inventario;
    }
    public boolean reponerStock(Connection conexion, int idPlato, int cantidadASumar) {
        // Usamos el ID del plato para localizar la fila exacta y sumar stock al actual
        String sql = "UPDATE Inventario SET stock_actual = stock_actual + ? WHERE id_plato = ?";

        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setInt(1, cantidadASumar);
            pstmt.setInt(2, idPlato);

            int filasAfectadas = pstmt.executeUpdate();
            
            // Si la variable es mayor a 0, significa que el ID existia y se actualizo
            if(filasAfectadas > 0){
                System.out.println("Reestock realizado correctamente");
                return true;
            }else{return false;}

        } catch (SQLException e) {
            System.err.println("Error al actualizar el inventario: " + e.getMessage());
            return false;
        }
    }

    public boolean reStockCompleto(Connection conexion, int idPlato, int stockExacto) {
        // Consulta donde cambiamos el valor actual por otro
        String sql = "UPDATE Inventario SET stock_actual = ? WHERE id_plato = ?";

        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setInt(1, stockExacto); // Valor inicial
            pstmt.setInt(2, idPlato);     // El ID del plato

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al establecer stock exacto: " + e.getMessage());
            return false;
        }
    }

}
