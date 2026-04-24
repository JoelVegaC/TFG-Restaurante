package Dao;

import VO.PlatosVo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PlatosDao {
    public List<PlatosVo> obtenerPlatos (Connection conexion) throws SQLException{
        //Creamos la lista donde guardaremos los datos
        List<PlatosVo> platos = new ArrayList<>();
        // Definimos la consulta
        String consulta = "SELECT id_categoria, nombre, descripcion, precio, activo FROM Plato";

        try(Statement stmt = conexion.createStatement();
            ResultSet resultado = stmt.executeQuery(consulta) ){
                while(resultado.next()){
                    //Recogemos los datos
                    int cat = resultado.getInt("id_categoria");
                    String nombre = resultado.getString("nombre");
                    String desc = resultado.getString("descripcion");
                    double precio = resultado.getDouble("precio");
                    int activo = resultado.getInt("activo");
                    
                    PlatosVo plato = new PlatosVo(desc, cat, nombre, precio);
                    platos.add(plato);
                }
            }catch(SQLException e) {
            e.printStackTrace();
        } 
        return platos;
    }

    public List<PlatosVo> obtenerPlatosPorIdCategoria(Connection conexion, int idCategoria) {
        List<PlatosVo> platos = new ArrayList<>();
        
        // Ya no hace falta el JOIN, buscamos directamente el ID en la tabla Plato
        String sql = "SELECT id_plato, id_categoria, nombre, descripcion, precio, activo " +
                    "FROM Plato " +
                    "WHERE id_categoria = ? AND activo = 1";

        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setInt(1, idCategoria); 
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    PlatosVo plato = new PlatosVo(
                        rs.getString("descripcion"),
                        rs.getInt("id_categoria"),
                        rs.getString("nombre"),
                        rs.getDouble("precio")
                    );
                    platos.add(plato);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener la carta: " + e.getMessage());
        }
        
        return platos;
    }



}
