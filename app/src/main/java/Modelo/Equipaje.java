package Modelo;

public class Equipaje {
    private int cant;
    private int id_producto;

    public Equipaje(int id_producto, int cant) {
        this.id_producto = id_producto;
        this.cant = cant;
    }

    public int getId_producto() {
        return this.id_producto;
    }

    public int getCant() {
        return this.cant;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public void setCant(int cant) {
        this.cant = cant;
    }
}
