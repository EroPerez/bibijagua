package Modelo;

public class Opcion {
    private int id_foto;
    private String opcion;

    public Opcion(String opcion, int id_foto) {
        this.id_foto = id_foto;
        this.opcion = opcion;
    }

    public String getOpcion() {
        return this.opcion;
    }

    public int getId_foto() {
        return this.id_foto;
    }

    public void setOpcion(String opcion) {
        this.opcion = opcion;
    }

    public void setId_foto(int id_foto) {
        this.id_foto = id_foto;
    }
}
