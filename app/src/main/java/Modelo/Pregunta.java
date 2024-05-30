package Modelo;

public class Pregunta {
    private boolean ischeck = false;
    private String respuesta;
    private String titulo;

    public Pregunta(String titulo, String respuesta) {
        this.titulo = titulo;
        this.respuesta = respuesta;
    }

    public String getTitulo() {
        return this.titulo;
    }

    public String getRespuesta() {
        return this.respuesta;
    }

    public boolean ischeck() {
        return this.ischeck;
    }

    public void setIscheck(boolean ischeck) {
        this.ischeck = ischeck;
    }
}
