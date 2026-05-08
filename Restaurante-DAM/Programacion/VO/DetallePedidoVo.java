package VO;

public class DetallePedidoVo {
    // --- ATRIBUTOS
    private int id_pedido, id_plato, cantidad;
    private double precio_unitario, subtotal;

    // --- CONSTRUCTOR
    public DetallePedidoVo(int id_pedido, int id_plato, int cantidad, double precio_unitario, double subtotal) {
        this.id_pedido = id_pedido;
        this.id_plato = id_plato;
        this.cantidad = cantidad;
        this.precio_unitario = precio_unitario;
        this.subtotal = subtotal;
    }

    // --- GETTERS Y SETTERS
    public int getId_pedido() {
        return id_pedido;
    }
    public void setId_pedido(int id_pedido) {
        this.id_pedido = id_pedido;
    }
    public int getId_plato() {
        return id_plato;
    }
    public void setId_plato(int id_plato) {
        this.id_plato = id_plato;
    }
    public int getCantidad() {
        return cantidad;
    }
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    public double getPrecio_unitario() {
        return precio_unitario;
    }
    public void setPrecio_unitario(double precio_unitario) {
        this.precio_unitario = precio_unitario;
    }
    public double getSubtotal() {
        return subtotal;
    }
    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    // --- METODOS
    // metodo para obtener el nombre del plato a traves del id
    public String getNombrePlato(int idBuscado) {
        switch (idBuscado) {
            case 1:  return "Croquetas infinitas";
            case 2:  return "Patatas con carne mechada de Shibuya";
            case 3:  return "Tabla de quesitos";
            case 4:  return "Makis con salsa de Yuta";
            case 5:  return "Inumaki Salmon Delux";
            case 6:  return "Nobara con clavos crujientes";
            case 7:  return "Medio Sándwich de Goyo";
            case 8:  return "Sukuna Finger Food";
            case 9:  return "Hamburguesa de Geto";
            case 10: return "Prision confinadora brownie";
            case 11: return "KitKat de Goyo";
            case 12: return "Brazo de 35";
            case 13: return "Té Zen´in";
            case 14: return "Batido Panda Mode";
            case 15: return "Sukuna Sour";
            case 16: return "Black Flash Shot";
            default: return "Plato desconocido";
        }
    }
    
    @Override
    public String toString() {
        return "ID: " + id_pedido + " | Plato: " + getNombrePlato(id_plato) + " | Cantidad: " + cantidad + " | Precio/Unidad: " + precio_unitario + " | Subtotal: " + subtotal;
    }

    
    
    

}
