package VO;

public class EmpleadoVo {
    // --- Atributos
    private String rol, nombre, prApellido, sgApellido, dni, telefono;

    // --- Constructores del objeo Empleado

    public EmpleadoVo(String dni, String nombre, String prApellido, int rol, String sgApellido, String telefono) {
        this.dni = dni;
        this.nombre = nombre;
        this.prApellido = prApellido;
        String puesto = (rol == 1) ? "Administrados" : (rol == 2)? "Camarero": "Cocinero";
        this.rol = puesto;
        this.sgApellido = sgApellido;
        this.telefono = telefono;
    }
    // Constructor sobrecargado para obtener unicamente el dni
    public EmpleadoVo(String dni){
         this.dni = dni;
    }

    // --- Getters y Setters del objeto Empleado
    public String getRol() {
        return rol;
    }
    public int getRolId() {
        if (this.rol == null) return 0;
        
        switch (this.rol) {
            case "Administrados": return 1;
            case "Camarero":      return 2;
            case "Cocinero":      return 3;
            default:              return 0;
        }
    }
    public void setRol(String rol) {
        this.rol = rol;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getPrApellido() {
        return prApellido;
    }
    public void setPrApellido(String prApellido) {
        this.prApellido = prApellido;
    }
    public String getSgApellido() {
        return sgApellido;
    }
    public void setSgApellido(String sgApellido) {
        this.sgApellido = sgApellido;
    }
    public String getDni() {
        return dni;
    }
    public void setDni(String dni) {
        this.dni = dni;
    }
    public String getTelefono() {
        return telefono;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
    // --- Metodos del Objeto

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("EmpleadoVo{");
        sb.append("rol=").append(rol);
        sb.append(", nombre=").append(nombre);
        sb.append(", prApellido=").append(prApellido);
        sb.append(", sgApellido=").append(sgApellido);
        sb.append(", dni=").append(dni);
        sb.append(", telefono=").append(telefono);
        sb.append('}');
        return sb.toString();
    }


}
