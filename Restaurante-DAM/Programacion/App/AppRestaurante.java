package App;

import Dao.ClienteDao;
import Dao.DetallePedidoDao;
import Dao.EmpleadoDao;
import Dao.InventarioDao;
import Dao.MesaDao;
import Dao.PedidoDao;
import Dao.PlatosDao;
import Dao.conexion;
import VO.ClienteVo;
import VO.DetallePedidoVo;
import VO.EmpleadoVo;
import VO.InventarioVo;
import VO.MesaVo;
import VO.PlatosVo;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class AppRestaurante {
    // Scanner estatico para toda la app
    static Scanner sc = new Scanner(System.in);
    // - ========== MENUS DE LA APP ==========
    // Menu de inicio
    public static void menuLogIn(){
        System.out.println("""
                \n========== Login ==========\n
                -Introduce tu DNI para iniciar sesion
                -Introduce "crear usuario" para crear un nuevo usuario
                -Introduce "salir" para salir del programa\n

                """);
    }
    // Menu de creacion de Usuario
    public static void creacionUsu(){
        System.out.println("""
                \n========== Creacion de usuario ==========\n
                -Selecciona la opcion deseada
                1. Crear un Cliente
                2. Crear un Empleado\n
                """);
    }

    // Menu Empleado
    public static void menuEmp() {
        System.out.println("""
                \n========== Menu Empleado ==========\n
                - ¿Qué desea hacer?
                1. Crear un usuario Empleado
                2. Ver el Stock actual
                3. Rellenar el stock del Restaurante
                4. Salir
                """);
    }

    // Menu de eleccion del tipo de ReStock
    public static void menuStock(){
        System.out.println("""
                \n ========== Eleccion de ReStock ==========\n
                1. ReStockear un unico plato
                2. ReStock completo
                3. Salir
                """);
    }

    // Menu para el cliente
    public static void menuCliente(){
        System.out.println("""
                \n========== Menu Cliente ==========\n
                1. Ver la Carta
                2. Realizar pedido
                3. Salir
                """);
    }

    // - ========== INSERTS EN LA BASE DE DATOS ==========
    // Insert de nuevo Cliente
    public static boolean registrarNuevoCliente(Connection conex, ClienteDao dao) {
        System.out.println("\n--- Registro de Nuevo Cliente ---");
        
        System.out.print("Nombre: ");
        String nombre = sc.nextLine();
        if (!esSoloLetras(nombre)) {
            System.out.println("Error: El nombre no puede contener números.");
            return false;
        }

        System.out.print("Primer Apellido: ");
        String apellido1 = sc.nextLine();
        if (!esSoloLetras(apellido1)) {
            System.out.println("Error: El apellido no puede contener números.");
            return false;
        }

        System.out.print("Segundo Apellido: ");
        String apellido2 = sc.nextLine();
        // Validamos solo si no está vacío
        if (!apellido2.trim().isEmpty() && !esSoloLetras(apellido2)) {
            System.out.println("Error: El segundo apellido no es válido.");
            return false;
        }
        if (apellido2.trim().isEmpty()) apellido2 = null;

        System.out.print("DNI: ");
        String dni = sc.nextLine().toUpperCase();
        if (!validarDni(dni)) {
            System.out.println("Error: El DNI debe tener 8 números y una letra.");
            return false;
        }

        System.out.print("Teléfono: ");
        String tlf = sc.nextLine();
        if (!validarTelefono(tlf)) {
            System.out.println("Error: El teléfono debe tener exactamente 9 dígitos.");
            return false;
        }

        System.out.print("Email: ");
        String email = sc.nextLine();
        if (!email.contains("@")) { 
            System.out.println("Error: Email no válido.");
            return false;
        }

        
        ClienteVo nuevo = new ClienteVo(dni, email, nombre, apellido1, apellido2, tlf);
        if (dao.insertarCliente(conex, nuevo)) {
            System.out.println("Cliente registrado con éxito.");
            return true;
        }else{
            return false;
        }
    }
    // Insert de nuevo Empleado
    public static boolean registrarEmpleado(Connection conex, EmpleadoDao dao) {
        System.out.println("\n--- Nuevo Registro de Empleado ---");

        System.out.print("ID del Rol (1: Admin, 2: Camarero, 3: Cocina): ");
        sc.nextLine();
        String rolInput = sc.nextLine();
        if (!rolInput.matches("[1-3]")) { // Solo permite 1, 2 o 3
            System.out.println("Error: Rol no válido.");
            return false;
        }
        int rol = Integer.parseInt(rolInput);

        System.out.print("Nombre: ");
        String nom = sc.nextLine();
        if (!esSoloLetras(nom)) {
        System.out.println("Error: Nombre no válido.");
        return false;
        }

        System.out.print("Primer Apellido: ");
        String ape1 = sc.nextLine();
        if (!esSoloLetras(ape1)) {
            System.out.println("Error: El apellido no puede contener números.");
            return false;
        }


        System.out.print("Segundo Apellido: ");
        String ape2 = sc.nextLine();
        if (!ape2.trim().isEmpty() && !esSoloLetras(ape2)) {
            System.out.println("Error: El segundo apellido no es válido.");
            return false;
        }
        if (ape2.trim().isEmpty()) ape2 = null;

        System.out.print("DNI: ");
        String dni = sc.nextLine();
        if (!validarDni(dni)) {
        System.out.println("Error: DNI no válido.");
        return false;
        }

        System.out.print("Teléfono: ");
        String tlf = sc.nextLine();

        // Creamos el objeto con los datos 
        EmpleadoVo nuevo = new EmpleadoVo(dni, nom, ape1, rol, ape2, tlf);
        if (dao.insertarEmpleado(conex, nuevo)) {
            System.out.println("Empleado registrado con éxito.");
            return true;
        }else{
            return false;
        }
    }
    // Insert para crear el pedido base
    public static boolean creacionPedido(Connection conex, int cli, int empl, int mesa, String tipo, String estado, double total){
        PedidoDao datosPedido = new PedidoDao();
        boolean ret = false;
        try {
            ret = datosPedido.crearPedido(conex, cli, empl, mesa, tipo, estado, total);
        } catch (SQLException e) {
        }
        return ret;
    }

    // Insert para insertar en la base de datos los detalles de cada pedido
    public static boolean añadirProducto(Connection conex, int idPlato, int cantidad) {
        // Instanciamos los DAO necesarios
        DetallePedidoDao detDao = new DetallePedidoDao();
        InventarioDao invDao = new InventarioDao();

        // Reducimos el stock en el inventario
        // Pasamos -cantidad para que en el SQL (stock_actual + ?) se reste
        boolean stockActualizado = invDao.reponerStock(conex, idPlato, -cantidad);

        if (stockActualizado) {
            
            // Añadimos el plato al detalle del último pedido abierto
            boolean insertado = detDao.añadirDetalle(conex, idPlato, cantidad);
            if (insertado) {
                System.out.println("\nProducto registrado y stock actualizado.");
            } else {
                System.out.println("\nProducto registrado, pero hubo un error al actualizar stock.");
            }
            return true;
        } else {
            System.out.println("\nError al añadir el producto al pedido.");
            return false;
        }
    }

    // - ========== METODOS ENLAZADOS A LA BASE DE DATOS ==========
    // Metodo para ver el stock actual
    public static void mostrarInv(){
        try {
            Connection conex = conexion.getConnection();
            InventarioDao datosInv = new InventarioDao();
            System.out.println("\n========== Stock Actual ==========\n");
            List<InventarioVo> inventario = datosInv.ObtenInventarioVo(conex);
            for(InventarioVo i : inventario){ 
                System.out.println(i); 
            }
        } catch (Exception e) {
        }
    }

    // Metodo para ReStockear un plato
    public static void restockUnitario(Connection conex, InventarioDao invDao) {
        mostrarInv();
        System.out.println("\n========== Introduce la informacion ==========");
        
        System.out.println("Introduce el ID del plato a reponer: ");
        int plato_id = sc.nextInt();

        System.out.println("¿Cuantos platos desea restockear? ");
        int cantidad = sc.nextInt();

        invDao.reponerStock(conex, plato_id, cantidad);
        
    }

    // Metodo para ReStockear todo el inventario a los valores iniciales
    public static void reStockCompleto(Connection conex, InventarioDao invDao) {

        // Valores Iniciales del Stock
        int inicialPlato1 = 30; 
        int inicialPlato2 = 25;
        int inicialPlato3 = 15; 
        int inicialPlato4 = 40; 
        int inicialPlato5 = 20;
        int inicialPlato6 = 18;
        int inicialPlato7 = 12;
        int inicialPlato8 = 10;
        int inicialPlato9 = 20;
        int inicialPlato10 = 15;
        int inicialPlato11 = 12;
        int inicialPlato12 = 20;
        int inicialPlato13 = 50;
        int inicialPlato14 = 60;
        int inicialPlato15 = 40;
        int inicialPlato16 = 80;

        // Llamamos al DAO usando las variables
        boolean p1 = invDao.reStockCompleto(conex, 1, inicialPlato1);
        boolean p2 = invDao.reStockCompleto(conex, 2, inicialPlato2);
        boolean p3 = invDao.reStockCompleto(conex, 3, inicialPlato3);
        boolean p4 = invDao.reStockCompleto(conex, 4, inicialPlato4);
        boolean p5 = invDao.reStockCompleto(conex, 5, inicialPlato5);
        boolean p6 = invDao.reStockCompleto(conex, 6, inicialPlato6);
        boolean p7 = invDao.reStockCompleto(conex, 7, inicialPlato7);
        boolean p8 = invDao.reStockCompleto(conex, 8, inicialPlato8);
        boolean p9 = invDao.reStockCompleto(conex, 9, inicialPlato9);
        boolean p10 = invDao.reStockCompleto(conex, 10, inicialPlato10);
        boolean p11 = invDao.reStockCompleto(conex, 11, inicialPlato11);
        boolean p12 = invDao.reStockCompleto(conex, 12, inicialPlato12);
        boolean p13 = invDao.reStockCompleto(conex, 13, inicialPlato13);
        boolean p14 = invDao.reStockCompleto(conex, 14, inicialPlato14);
        boolean p15 = invDao.reStockCompleto(conex, 15, inicialPlato15);
        boolean p16 = invDao.reStockCompleto(conex, 16, inicialPlato16);

        // Comprobamos si todo ha ido bien
        if (p1 && p2 && p3 && p4 && p5 && p6 && p7 && p8 && p9 && p10 && p11 && p12 && p13 && p14 && p15 && p16) {
            System.out.println("Todos los platos se han restablecido a sus valores iniciales");
        } else {
            System.out.println("Algunos platos no se pudieron actualizar");
        }
    }

    // Metodo para obtener una mesa
    public static void buscarMesa(Connection conex, MesaDao mesaDao) {
        System.out.print("¿Para cuántas personas es la mesa?: ");
        int personas = Integer.parseInt(sc.nextLine());

        try {
            List<MesaVo> opciones = mesaDao.obtenerMesasAdecuadas(conex, personas);

            if (opciones.isEmpty()) {
                System.out.println("Lo sentimos, no hay mesas libres con esa capacidad ahora mismo.");
            } else {
                System.out.println("\n====== Mesas disponibles para " + personas + " personas ======");
                for (MesaVo m : opciones) {
                    System.out.println("Mesa Nº " + m.getNum() + " (Capacidad: " + m.getCapacidad() + ")");
                }
            }
        } catch (SQLException e) {
        }
    }

    // Metodo para mostrar la carta
    public static void categorias(Connection conex, PlatosDao dao, String titulo, int idCategoria) {
        List<PlatosVo> lista = dao.obtenerPlatosPorIdCategoria(conex, idCategoria);
        
        System.out.println("\n========== " + titulo.toUpperCase() + " ==========");
            for (PlatosVo plato : lista) {
                // Imprime ID - Nombre - Precio
                System.out.println("ID: "+plato.getidPlato()+" | " +plato.getNombre() +" | "+ plato.getPrecio()+" Eur");
                // Imprime la descripción justo debajo, con un poco de sangría
                System.out.println("   -> " + plato.getDescripcion());
            }
    }

    // Menu completo
    public static void menuCompleto (){
        try {
            Connection conex = conexion.getConnection();
            PlatosDao datosPlatos = new PlatosDao();
            categorias(conex, datosPlatos, "Entrantes", 1);
            categorias(conex, datosPlatos, "Primeros", 2);
            categorias(conex, datosPlatos, "Segundos", 3);
            categorias(conex, datosPlatos, "Postres", 4);
            categorias(conex, datosPlatos, "Bebidas", 5);
        } catch (Exception e) {
        }
        
    }
    // Factura
    public static void factura(Connection conex){
        try {
            DetallePedidoDao datosPedido = new DetallePedidoDao();
            List<DetallePedidoVo> ped = datosPedido.obtenerDetallesUltimoPedido(conex);

            System.out.println("\n ===== Factura =====");

            for (DetallePedidoVo det : ped) {
                System.out.println(det);
            }
        } catch (Exception e) {
        }
        
    }
    
    // - ========== VALIDACIONES ==========
    // Valida que el texto solo contenga letras y espacios
    private static boolean esSoloLetras(String texto) {
        // Regex: ^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$ permite letras con tildes y espacios
        return texto != null && texto.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$");
    }

    // Valida DNI (8 números + 1 letra)
    private static boolean validarDni(String dni) {
        return dni != null && dni.matches("^[0-9]{8}[A-Z]$");
    }

    // Valida teléfono (Exactamente 9 números)
    private static boolean validarTelefono(String tlf) {
        return tlf != null && tlf.matches("^[0-9]{9}$");
    }
    // Valida que el valor introducido sea un numero
    private static int leerEntero() {
        while (true) {
            try {
                return Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Error: ¡Debes introducir un numero! Intentalo de nuevo: ");
            }
        }
    }
    // Valida que ek valor introducido sea "si" o "no"
    private static String leerSiNo(String mensaje) {
        String respuesta;
        while (true) {
            System.out.print(mensaje + " (si/no) =>  ");
            respuesta = sc.nextLine().trim().toLowerCase(); // Limpiamos espacios y pasamos a minúsculas
            
            if (respuesta.equals("si") || respuesta.equals("no")) {
                return respuesta;
            } else {
                System.out.println("Error: Por favor, responde solo con 'si' o 'no'.");
            }
        }
    }
    // Valida que el id introducido coincida con el rango adecuado
    private static boolean validarRangoPlato(int id, int categoriaId) {
        return switch (categoriaId) {
            case 1 -> (id >= 1 && id <= 3);   // Entrantes
            case 2 -> (id >= 4 && id <= 6);   // Primeros
            case 3 -> (id >= 7 && id <= 9);   // Segundos
            case 4 -> (id >= 10 && id <= 12); // Postres
            case 5 -> (id >= 13 && id <= 16); // Bebidas
            default -> false;
        };
    }

    // - ========== METODO MAIN (PRINCIPAL) DE LA APP ==========
    public static void main(String[] args) {
        try {
            // Conexion a la base de datos para toda la app
            Connection conex = conexion.getConnection();
            System.out.println("Conexión realizada con exito");

            String tUsuario = "";
            EmpleadoDao datosEmp = new EmpleadoDao();
            ClienteDao datosCli = new ClienteDao();
            InventarioDao datosInv = new InventarioDao();
            MesaDao datosMesa = new MesaDao();
            PlatosDao datosPlatos = new PlatosDao();

            //El bucle sigue mientras NO escriba "salir"
            while (!tUsuario.equalsIgnoreCase("salir")) { 
                menuLogIn();
                System.out.print("Introduce aqui tu respuesta: ");
                tUsuario = sc.nextLine();

                // Si el usuario quiere salir, rompemos el bucle antes de validar
                if (tUsuario.equalsIgnoreCase("salir")) {
                    break;
                }else if (tUsuario.equalsIgnoreCase("crear usuario")) {
                    if(registrarNuevoCliente(conex, datosCli)){
                        continue;
                    }
                }

                
                try {
                    // Validamos si lo que ha escrito es un DNI que existe
                    String tipoUsuario = "";
                    if (datosEmp.existeDni(conex, tUsuario)) tipoUsuario = "EMPLEADO";
                    else if (datosCli.existeDni(conex, tUsuario)) tipoUsuario = "CLIENTE";

                    // Condicionamos la secuencia en base a la opcion elegida
                    switch (tipoUsuario) {
                        case "EMPLEADO" -> {
                            // Guardamos en la variable emp, los datos en base al dni del usuario
                            EmpleadoVo emp = datosEmp.buscarPorDni(conex, tUsuario);

                            // Pintamos un mesaje de bienvenidda
                            System.out.println("\nLogin correcto. Bienvenido, " + emp.getNombre());
                            System.out.println("Has entrado como empleado");
                            int opcionEmpleado = 0;
                            // Bucle sobre las diferentes opciones del menu empleado
                            while (opcionEmpleado != 4) { 
                                // Pintamos el menu de Empleados
                                menuEmp();
                                System.out.print("Introduce aqui tu respuesta: ");
                                opcionEmpleado = leerEntero();
                                // switch para direccionar el flujo del codigo en base a la opcion del empleado
                                switch (opcionEmpleado) {
                                    case 1:// Crear usuario
                                        // condicionamos si en los datos del empleado aparece como administrador
                                        if (emp.getRolId() != 1) {
                                            System.out.println("Error: No tienes el rol Administrador, no puedes realizar esta operacion");
                                            break;
                                        }
                                        registrarEmpleado(conex, datosEmp); // En caso de tener los "permisos" necesarios procede a crear el empleado
                                        break;
                                    case 2:// Mostrar el Stock/inventario actual
                                        mostrarInv();
                                        break;
                                    case 3: // Re stockear el inventario
                                        int opcionStock = 0;
                                        // Bucle sobre las diferentes formas ed hacer el re-stock
                                        while(opcionStock != 3){
                                            menuStock();
                                            System.out.print("Introduce aqui tu respuesta: ");
                                            opcionStock = leerEntero();
                                            // Switch para direccionar el flujo del codigo hacia la opcion elejida por el usuario
                                            switch (opcionStock) {
                                                case 1:
                                                    restockUnitario(conex, datosInv); // re-stock de un unico plato
                                                    break;
                                                case 2:
                                                    reStockCompleto(conex, datosInv);// re-stock de todo el inventario
                                                    break;
                                            }
                                        }
                                        break;
                                    default:
                                        throw new AssertionError();
                                }
                            }
                        }

                        case "CLIENTE" -> {
                            // Creamos las variables de los diferentes Vo
                            // Para asi poder llamar y usar sus metodos
                            ClienteVo cli = datosCli.buscarPorDni(conex, tUsuario);
                            EmpleadoVo camarero = datosEmp.obtenerCamareroAleatorio(conex);
                            int elecMesa = -1;
                            System.out.println("\nLogin correcto. Has entrado como Cliente ");
                            // Mensaje de Bienvenida personalizado por cliente
                            System.out.println("\nBuenos dias, bienvenido " + cli.getNombre() );
                            // Usamos el rol de camarero para elegir uno al azar para cada ejecucion
                            System.out.println("Me llamo, " + camarero.getNombre() + " y voy a ser su camarero.");
                            while(true){
                                buscarMesa(conex, datosMesa); // Llamamos al metodo de los datos de la mesa
                                System.out.print("Introduce aqui tu respuesta: ");
                                elecMesa = leerEntero();
                                // Condicionamos que el metodo cambiarEstadoMesa sea true para poder continuar con la ejecucion del codigo
                                if(!datosMesa.cambiarEstadoMesa(conex, elecMesa, "ocupada")) {
                                    System.out.println("Error: Elije una mesa valida");    
                                    continue;
                                }

                                System.out.println("\nPor aquí, les llevo a su mesa \nA continuacion podrán ver el menu de clientes");
                                // Creamos el pedido para la mesa seleccionada
                                if(!creacionPedido(conex, cli.getIdCliente(), camarero.getIdEmpleado(), elecMesa, "Local", "en_cocina", 0)){
                                    datosMesa.cambiarEstadoMesa(conex, elecMesa, "libre"); // Unicamente si se crea el pedido correctamente, cambiamos el estado de la mesa
                                }else{break;}

                            }
                            int opcionCliente = 0;
                            
                            // Bucle de opciones de los clientes
                            while(opcionCliente != 3){
                                menuCliente();
                                opcionCliente = leerEntero();
                                // En base a la eleccion del cliente direccionamos el codigo hacia el caso concreto
                                switch (opcionCliente) {
                                    case 1 -> { // Mostrar la carta completa
                                        menuCompleto();
                                    }

                                    case 2 -> { // Realizar el pedido

                                        // Variables para el correcto funcionamiento del pedido
                                        Double cuenta = 0.0;
                                        String seguir = "si";

                                        // Repetimos esta secuencia dependiendo de lo que intrduzca el usuario
                                        while(!seguir.equalsIgnoreCase("no")){
                                            System.out.println("¿Que quieren ustedes pedir de beber?: ");
                                            categorias(conex, datosPlatos, "Bebidas", 5); // Pintamos la seccion de la carta correspondiente a la pregunta
                                            int eleccionBebida;
                                            // Bucle para validar
                                            while (true) {
                                                System.out.print("\nIntroduce el ID de la bebida: ");
                                                eleccionBebida = leerEntero();// Validamos que el numero introducido sea un entero
                                                if (validarRangoPlato(eleccionBebida, 5)) break; // Validamos que no haya inconvenientes con el plato elegido
                                                System.out.println("ID incorrecto. Debe ser una bebida (13-16).");
                                            }

                                            System.out.print("Introduce la cantidad: "); // Cantidad del plato elegido
                                            int bebidacant = leerEntero();

                                            // Si el producto se añade sin errores en el pedido sumamos al precio total
                                            if(añadirProducto(conex, eleccionBebida, bebidacant)){
                                                cuenta += datosPlatos.obtenerPrecioPlato(conex, eleccionBebida) * bebidacant;
                                            }


                                            seguir = leerSiNo("¿Desea pedir otra bebida?");

                                        }
                                        seguir = "si";

                                        System.out.print("¿Quieren pedir entrante?(si o no) => ");
                                        String opEntrante = sc.nextLine();
                                        if (opEntrante.equalsIgnoreCase("si")) { // Si el usuario quiere entrante ejecutamos
                                            while(!seguir.equalsIgnoreCase("no")){
                                                System.out.print("¿Que quiere pedir usted como entrante?: ");
                                                categorias(conex, datosPlatos, "Entrantes", 1); // Pintamos la seccion de platos correspondientes
                                                int eleccionEntrante;
                                                // Bucle para validar
                                                while (true) {
                                                    System.out.print("\nIntroduce el ID del entrante: ");
                                                    eleccionEntrante = leerEntero();// Validamos que el numero introducido sea un entero
                                                    if (validarRangoPlato(eleccionEntrante, 1)) break; // Validamos que el plato elegido sea correcto
                                                    System.out.println("ID incorrecto. Debe ser un entrante (1 - 3).");
                                                }
                                                
                                                System.out.print("Introduce la cantidad: ");
                                                int entranteacant = leerEntero();
                                                // Validamos que se sume el total del pedido si el producto se añade correctamente
                                                if(añadirProducto(conex, eleccionEntrante, entranteacant)){
                                                    cuenta += datosPlatos.obtenerPrecioPlato(conex, eleccionEntrante) * entranteacant;
                                                }

                                                seguir = leerSiNo("¿Desea pedir mas entrantes?");
                                            }
                                            
                                        }

                                        seguir = "si";
                                        while (!seguir.equalsIgnoreCase("no")){
                                            System.out.println("¿Que quieren ustedes pedir de primer plato?");
                                            categorias(conex, datosPlatos, "Primero", 2);// Pintamos la seccion correspondiente al plato pedido
                                            int eleccionPrimero;

                                            while (true) { 
                                                System.out.print("Introduce el ID del Primero: ");
                                                eleccionPrimero = leerEntero();// Validamos que el numero introducido sea un entero
                                                if(validarRangoPlato(eleccionPrimero, 2)) break; // Validamos que el plato elegido sea correcto
                                                System.out.println("ID incorrecto, Debe ser un Primero. (4 - 6)");
                                            }

                                            System.out.print("\nIntroduce la cantidad: ");// Pedimos la cantidad del plato elegido
                                            int primerocant = leerEntero();
                                            // Validamos que el producto se añada al pedido sin errores antes de sumar el precio total a la cuenta
                                            if(añadirProducto(conex, eleccionPrimero, primerocant)){
                                                cuenta += datosPlatos.obtenerPrecioPlato(conex, eleccionPrimero) * primerocant;
                                            }

                                            seguir = leerSiNo("¿Desea pedir mas primeros?");
                                        }
                                        
                                        seguir = "si";

                                        while (!seguir.equalsIgnoreCase("no")){
                                            System.out.println("¿Que quieren ustedes pedir de segundo plato?");
                                            categorias(conex, datosPlatos, "Segundo", 3);
                                            int eleccionSegundo;

                                            while (true) { 
                                                System.out.print("Introduce el ID del segundo: ");
                                                eleccionSegundo = leerEntero();// Validamos que el numero introducido sea un entero
                                                if(validarRangoPlato(eleccionSegundo, 3)) break;// Validamos que el rango del plato sea valido
                                                System.out.println("ID incorrecto. Debe ser un segundo. (7 - 9)");
                                            }
                                            System.out.print("Introduce la cantidad: ");
                                            int segundocant = leerEntero();
                                             // Validamos que el plato se añada al pedido sin errores antes de aumentar el precio total de la cuenta
                                            if(añadirProducto(conex, eleccionSegundo, segundocant)){
                                                cuenta += datosPlatos.obtenerPrecioPlato(conex, eleccionSegundo) * segundocant;
                                            }

                                            seguir = leerSiNo("¿Desea pedir mas segundos?");

                                        }
                                        
                                        seguir = "si";

                                        String opPostre = leerSiNo("¿Quieren pedir postre?");
                                        if (opPostre.equalsIgnoreCase("si")) {
                                            while(!seguir.equalsIgnoreCase("no")){
                                                System.out.println("¿Que quieren ustedes pedir de postre?");
                                                categorias(conex, datosPlatos, "Postre", 4);
                                                int eleccionPostre;

                                                while (true) { 
                                                    System.out.print("Introduce el ID del postre: ");
                                                    eleccionPostre = leerEntero();// Validamos que el numero introducido sea un entero
                                                    if(validarRangoPlato(eleccionPostre, 4)) break; // Validamos que el plato sea valido
                                                    System.out.println("ID incorrecto. Debe ser un postre (10 - 12)");
                                                }

                                                System.out.print("Introduce la cantidad: ");
                                                int postrecant = leerEntero();
                                                // Validamos que el plato se añada al pedido antes de aumentar el precio total de la cuenta
                                                if(añadirProducto(conex, eleccionPostre, postrecant)){
                                                    cuenta += datosPlatos.obtenerPrecioPlato(conex, eleccionPostre) * postrecant;
                                                }

                                                seguir = leerSiNo("¿Desea pedir mas postres?");
                                            }
                                            
                                        }
                                        // Actualizamos el estado del pedido
                                        PedidoDao.actualizarPedido(conex, "entregado", cuenta);

                                        System.out.println("\nEl pedido ya esta en camino");
                                        System.out.println("\nA continuacion les muestro todo su pedido y el total de la cuenta");
                                        // Llamamos la funcion que pinta toda la factura del pedido en cuestion
                                        factura(conex);
                                        System.out.println("- Total a pagar =>" + cuenta + " eur");// Pintamos el total a pagar
                                        datosMesa.cambiarEstadoMesa(conex, elecMesa, "libre"); // Cambiamos el estado de la mesa despues de que el usuario pague


                                    }
                                    default -> throw new AssertionError();
                                }
                            }
                        }

                        default -> System.out.println("El DNI no existe o es incorrecto.");
                    }
                } catch (SQLException e) {
                    System.out.println("Error al validar datos: " + e.getMessage());
                }
            }

            System.out.println("Programa finalizado. ¡Adiós!");
        } catch (Exception e) {
        }
        
        
    }
}
