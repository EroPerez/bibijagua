package Modelo;

public class Foto {
    private boolean f0f = false;
    private int id;
    private String nombre;

    public Foto(String nombre, int id) {
        this.nombre = nombre;
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public int getId() {
        return this.id;
    }

    public void setF(boolean f) {
        this.f0f = f;
    }

    public boolean isF() {
        return this.f0f;
    }
}
