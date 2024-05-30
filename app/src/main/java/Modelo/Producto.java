package Modelo;

public class Producto {
    private int cant;
    private int cant_equp;
    private String descripcion;
    private int id_cap;
    private int id_grupo;
    private int id_prod;
    private int isMisc = -1;
    private boolean ischeck;
    private float precio;

    public Producto(String descripcion, int id_prod, int id_cap, int cant, float precio, int id_grupo) {
        this.descripcion = descripcion;
        this.id_prod = id_prod;
        this.id_cap = id_cap;
        this.cant = cant;
        this.precio = precio;
        this.id_grupo = id_grupo;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public int getId_prod() {
        return this.id_prod;
    }

    public int getId_cap() {
        return this.id_cap;
    }

    public int getCant() {
        return this.cant;
    }

    public float getPrecio() {
        return this.precio;
    }

    public int getIsMisc() {
        return this.isMisc;
    }

    public void setIsMisc(int isMisc) {
        this.isMisc = isMisc;
    }

    public void setIscheck(boolean ischeck) {
        this.ischeck = ischeck;
    }

    public boolean ischeck() {
        return this.ischeck;
    }

    public int getId_grupo() {
        return this.id_grupo;
    }

    public int getCant_equp() {
        return this.cant_equp;
    }

    public void setCant_equp(int cant_equp) {
        this.cant_equp = cant_equp;
    }
}
