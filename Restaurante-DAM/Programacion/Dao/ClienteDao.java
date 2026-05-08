package Dao;

import VO.ClienteVo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ClienteDao {

    public List<ClienteVo> obtenerClientes(Connection conexion) throws SQLException{
       // Definimos la consulta sobre la tabla Cliente
        String consulta = "SELECT  id_cliente, nombre, prApellido, sgApellido, dni, telefono, email FROM Cliente";

        // Creamos la lista donde almacenaremos los resultados del select
        List<ClienteVo> clientes = new ArrayList<>();

        try(Statement stmt = conexion.createStatement();
            ResultSet resultado = stmt.executeQuery(consulta)){
                while(resultado.next()){
                    int id = resultado.getInt("id_cliente");
                    String nombre = resultado.getString("nombre");
                    String apellido1 = resultado.getString("prApellido");
                    String apellido2 = resultado.getString("sgApellido");
                    String dni = resultado.getString("dni");
                    String tlf = resultado.getString("telefono");
                    String email = resultado.getString("email");

                    ClienteVo cliente = new ClienteVo(id, dni, email, nombre, apellido1, apellido2, tlf);
                    clientes.add(cliente);

                }

        } catch(SQLException e) {
            e.printStackTrace();
        } 
        return clientes;
    }

    public boolean existeDni(Connection conexion, String dni) throws SQLException {
        String consulta = "SELECT COUNT(*) FROM Cliente WHERE dni = ?";
        
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

    public boolean insertarCliente(Connection conexion, ClienteVo cliente) {
        // Definimos la consulta con los parametros necesarios
        String sql = "INSERT INTO Cliente (nombre, prApellido, sgApellido, dni, telefono, email) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            // Recogemos y sustituimos los valores por los ?
            pstmt.setString(1, cliente.getNombre());
            pstmt.setString(2, cliente.getPrApellido());
            pstmt.setString(3, cliente.getSgApellido());
            pstmt.setString(4, cliente.getDni());
            pstmt.setString(5, cliente.getTelefono());
            pstmt.setString(6, cliente.getEmail());

            int filas = pstmt.executeUpdate();
            return filas > 0;

        } catch (SQLException e) {
            System.out.println("Error al insertar cliente: " + e.getMessage());
            return false;
        }
    }

    public ClienteVo buscarPorDni(Connection conexion, String dni) throws SQLException {
        String consulta = "SELECT * FROM Cliente WHERE dni = ?";
        
        try (PreparedStatement pstmt = conexion.prepareStatement(consulta)) {
            pstmt.setString(1, dni);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new ClienteVo(
                        rs.getInt("id_cliente"),
                        rs.getString("dni"),         
                        rs.getString("email"),       
                        rs.getString("nombre"),      
                        rs.getString("prApellido"),  
                        rs.getString("sgApellido"),  
                        rs.getString("telefono")     
                    );
                }
            }
        }
        return null; // Devuelve null si no existe el cliente
    }
        
        
        
}
