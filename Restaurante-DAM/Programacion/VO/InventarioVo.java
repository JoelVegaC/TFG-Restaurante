package VO;

public class InventarioVo {
    private int idPlato;
    private String nombrePlato;
    private int stockActual;

    // Constructor actualizado con los 3 parámetros
    public InventarioVo(int idPlato, String nombrePlato, int stockActual) {
        this.idPlato = idPlato;
        this.nombrePlato = nombrePlato;
        this.stockActual = stockActual;
    }

    // Getters
    public int getIdPlato() { return idPlato; }
    public String getNombrePlato() { return nombrePlato; }
    public int getStockActual() { return stockActual; }

    // --- Metodos
    @Override
    public String toString() {
        return "ID: " + idPlato + " | Plato: " + nombrePlato + " | Stock: " + stockActual;
    }
}