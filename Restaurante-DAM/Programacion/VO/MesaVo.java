package VO;

public class MesaVo {
    // --- ATRIBUTOS
    private int num, capacidad;
    private String estado;
    
    // --- CONSTRUCTOR
    public MesaVo(int num, int capacidad, String estado) {
        this.num = num;
        this.capacidad = capacidad;
        this.estado = estado;
    }

    // --- GETTERS Y SETTERS
    public int getNum() {
        return num;
    }
    public void setNum(int num) {
        this.num = num;
    }
    public int getCapacidad() {
        return capacidad;
    }
    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }
    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }
    

}
