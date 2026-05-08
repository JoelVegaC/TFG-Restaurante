package Dao;

import VO.EmpleadoVo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoDao {
    public List<EmpleadoVo> ObtenerEmpleado(Connection conexion) throws SQLException{
        // Definimos la consulta sobre la tabla Cliente
        String consulta = "SELECT rol_id, nombre, prApellido, sgApellido, dni, telefono FROM Empleado";

        // Creamos la lista donde almacenaremos los resultados del select
        List<EmpleadoVo> empleados = new ArrayList<>();
        
        try(Statement stmt = conexion.createStatement();
            ResultSet resultado = stmt.executeQuery(consulta)){
                while(resultado.next()){
                    int rol = resultado.getInt("rol_id");
                    String nombre = resultado.getString("nombre");
                    String apellido1 = resultado.getString("prApellido");
                    String apellido2 = resultado.getString("sgApellido");
                    String dni = resultado.getString("dni");
                    String tlf = resultado.getString("telefono");

                    EmpleadoVo empleado = new EmpleadoVo(dni, nombre, apellido1, rol, apellido2, tlf);
                    empleados.add(empleado);

                }

        } catch(SQLException e) {
            e.printStackTrace();
        } 
        return empleados;
    }

    public boolean existeDni(Connection conexion, String dni) throws SQLException {
        String consulta = "SELECT COUNT(*) FROM Empleado WHERE dni = ?";
        
        try (PreparedStatement pstmt = conexion.prepareStatement(consulta)) {
            pstmt.setString(1, dni); // Sustituye el ? por el DNI
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    // Si el conteo es mayor a 0, es que existe
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    public boolean insertarEmpleado(Connection conexion, EmpleadoVo empleado) {
        // Definimos la consulta 
        String sql = "INSERT INTO Empleado (rol_id, nombre, prApellido, sgApellido, dni, telefono) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            // Colocamos los datos siguiendo el orden de los ?
            pstmt.setInt(1, empleado.getRolId());         
            pstmt.setString(2, empleado.getNombre());     
            pstmt.setString(3, empleado.getPrApellido()); 
            pstmt.setString(4, empleado.getSgApellido()); 
            pstmt.setString(5, empleado.getDni());        
            pstmt.setString(6, empleado.getTelefono());   

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al insertar: " + e.getMessage());
            return false;
        }
    }

    public EmpleadoVo buscarPorDni(Connection conexion, String dni) throws SQLException {
        String consulta = "SELECT * FROM Empleado WHERE dni = ?";
        try (PreparedStatement pstmt = conexion.prepareStatement(consulta)) {
            pstmt.setString(1, dni);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    // Creamos el objeto con los datos de la fila
                    return new EmpleadoVo(
                        rs.getString("dni"),
                        rs.getString("nombre"),
                        rs.getString("prApellido"),
                        rs.getInt("rol_id"),
                        rs.getString("sgApellido"),
                        rs.getString("telefono")
                    );
                }
            }
        }
        return null; // Si no lo encuentra
    }

    // Metodo para obtener un camarero aleatorio
    public EmpleadoVo obtenerCamareroAleatorio(Connection conexion) throws SQLException {
        // Definimos la consulta condicionando que rol_id sea 2(empleado)
        String sql = "SELECT * FROM Empleado WHERE rol_id = 2 ORDER BY RAND() LIMIT 1";
        
        try (Statement stmt = conexion.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {
            
            if (rs.next()) {
                // Creamos y devolvemos el objeto con los datos del camarero aleatorio
                return new EmpleadoVo(
                    rs.getInt("id_empleado"),
                    rs.getString("dni"),
                    rs.getString("nombre"),
                    rs.getString("prApellido"),
                    rs.getInt("rol_id"),
                    rs.getString("sgApellido"),
                    rs.getString("telefono"));
            }
        }
        return null; 
    }

}
