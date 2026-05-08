package VO;

public class ClienteVo {
    // --- Atributos de Cliente
    private int idCliente;
    private String nombre;
    private String prApellido;
    private String sgApellido;
    private String dni;
    private String telefono;
    private String email;

    // --- Constructor del objeto Cliente

    public ClienteVo(int idCliente, String dni, String email, String nombre, String prApellido, String sgApellido, String telefono) {
        this.idCliente = idCliente;
        this.dni = dni;
        this.email = email;
        this.nombre = nombre;
        this.prApellido = prApellido;
        this.sgApellido = sgApellido;
        this.telefono = telefono;
    }

    public ClienteVo(String dni, String email, String nombre, String prApellido, String sgApellido, String telefono) {
        this.dni = dni;
        this.email = email;
        this.nombre = nombre;
        this.prApellido = prApellido;
        this.sgApellido = sgApellido;
        this.telefono = telefono;
    }


    // --- Getters del objeto Cliente
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
    public int getIdCliente(){
        return idCliente;
    }
    // --- Setters del objeto Cliente
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
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    // --- ToString
    @Override
    public String toString() {
        return "ClienteVo [nombre=" + nombre + ", prApellido=" + prApellido + ", sgApellido=" + sgApellido + ", dni="
                + dni + ", telefono=" + telefono + ", email=" + email + "]";
    }

    
    
    
}
