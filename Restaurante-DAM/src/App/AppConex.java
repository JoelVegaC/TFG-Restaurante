package App;

import Dao.ClienteDao;
import Dao.EmpleadoDao;
import Dao.InventarioDao;
import Dao.PlatosDao;
import Dao.conexion;
import VO.ClienteVo;
import VO.EmpleadoVo;
import VO.InventarioVo;
import VO.PlatosVo;
import java.sql.Connection;
import java.util.List;

public class AppConex {
    public static void main(String[] args) {
        
        //creamos el DAO para ejecutar las operaciones disponibles
        ClienteDao a1DAO = new ClienteDao();
        EmpleadoDao a2DAO = new EmpleadoDao();
        PlatosDao a3DAO = new PlatosDao();
        EmpleadoDao prue = new EmpleadoDao();
        InventarioDao inv = new InventarioDao();
        try {
            //establecer la conexión
            Connection conex = conexion.getConnection();
            System.out.println("Conexión realizada con exito");

            //consutar los alumnos
            List<ClienteVo> clientes = a1DAO.obtenerClientes(conex);

            for(ClienteVo cli:clientes) {
                System.out.println(cli);
            }
            System.out.println("\n=================================");
            System.out.println("\n========== Empleados ==========");
            List<EmpleadoVo> empleados = a2DAO.ObtenerEmpleado(conex);
            for(EmpleadoVo emp:empleados){
                System.out.println(emp.getDni());
            }
            System.out.println("\n=================================");
            System.out.println("\n========== Menu ==========");
            List<PlatosVo> platos = a3DAO.obtenerPlatos(conex);
            for(PlatosVo pla:platos){
                System.out.println(pla);
            }
            System.out.println("\n========== Prueba ==========");
            List<InventarioVo> inventario = inv.ObtenInventarioVo(conex);

            // 1. Cambiamos 'empleados' por 'prue1'
            for(InventarioVo emp : inventario){ 
                // 2. Usamos el getter para imprimir solo el texto del DNI
                System.out.println(emp); 
            }


        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
