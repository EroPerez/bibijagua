package Modelo;

public class Ajuste {
    private String categoria;
    private int importacion;
    private String metodo;
    private String moneda;
    private float peso;
    private float valor;

    public Ajuste(String categoria, int importacion, float peso, float valor, String moneda, String metodo) {
        this.categoria = categoria;
        this.importacion = importacion;
        this.peso = peso;
        this.valor = valor;
        this.moneda = moneda;
        this.metodo = metodo;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setImportacion(int importacion) {
        this.importacion = importacion;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public String getCategoria() {
        return this.categoria;
    }

    public int getImportacion() {
        return this.importacion;
    }

    public float getPeso() {
        return this.peso;
    }

    public float getValor() {
        return this.valor;
    }

    public String getMoneda() {
        return this.moneda;
    }

    public String getMetodo() {
        return this.metodo;
    }

    public void setMetodo(String metodo) {
        this.metodo = metodo;
    }
}
