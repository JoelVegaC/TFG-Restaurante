package VO;

import java.time.LocalDateTime;

public class PedidoVo {
    // --- ATRIBUTOS
    private int pedido, cliente, empleado, mesa;
    private LocalDateTime fecha;
    private String tipo, estado;
    private double total;

    // --- CONSTRUCTOR  
    public PedidoVo(int pedido, int cliente, int empleado, LocalDateTime fecha, int mesa, String tipo, String estado, double total) {
        this.pedido = pedido;
        this.cliente = cliente;
        this.empleado = empleado;
        this.fecha = fecha;
        this.mesa = mesa;
        this.tipo = tipo;
        this.estado = estado;
        this.total = total;
    }

    public PedidoVo(int cliente, int empleado, int mesa, String tipo, String estado, double total) {
        this.cliente = cliente;
        this.empleado = empleado;
        this.estado = estado;
        this.mesa = mesa;
        this.tipo = tipo;
        this.total = total;
    }
    

    // --- SETTERS Y GETTERS    
    public int getCliente() {
        return cliente;
    }

    public void setCliente(int cliente) {
        this.cliente = cliente;
    }

    public int getEmpleado() {
        return empleado;
    }

    public void setEmpleado(int empleado) {
        this.empleado = empleado;
    }

    public int getMesa() {
        return mesa;
    }

    public void setMesa(int mesa) {
        this.mesa = mesa;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
    public int getIdPedido(){
        return pedido;
    }

}
