package VO;

public class PlatosVo {
    // --- Atributos
    private int id_plato,id_categoria;
    private double precio;
    private String nombre, descripcion;

    // --- Constructor 
    public PlatosVo(int plato, String descripcion, int id_categoria, String nombre, double precio) {
        this.id_plato = plato;
        this.descripcion = descripcion;
        this.id_categoria = id_categoria;
        this.nombre = nombre;
        this.precio = precio;
    }

    // --- Getters y Setters
    public int getId_categoria() {
        return id_categoria;
    }
    public void setId_categoria(int id_categoria) {
        this.id_categoria = id_categoria;
    }
    public int getidPlato(){
        return id_plato;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }
    

    // --- Metodos 
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("PlatosVo{");
        sb.append("id_categoria=").append(id_categoria);
        sb.append(", nombre=").append(nombre);
        sb.append(", descripcion=").append(descripcion);
        sb.append(", precio=").append(precio);
        sb.append('}');
        return sb.toString();
    }
    

}
