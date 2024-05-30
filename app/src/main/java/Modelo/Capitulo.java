package Modelo;

public class Capitulo {
    private int codigo;
    private String created_at;
    private String deleted_at;
    private String descripcion;
    private String fecha_fin;
    private String fecha_inicio;
    private int id_tipo_producto;
    private String imagen;
    private boolean miscelanea;
    private int prohibido;

    public Capitulo(int codigo, int id_tipo_producto, String descripcion, String fecha_inicio, String fecha_fin, String created_at, String deleted_at, String imagen, int prohibido, boolean miscelanea) {
        this.codigo = codigo;
        this.id_tipo_producto = id_tipo_producto;
        this.descripcion = descripcion;
        this.fecha_inicio = fecha_inicio;
        this.fecha_fin = fecha_fin;
        this.created_at = created_at;
        this.deleted_at = deleted_at;
        this.imagen = imagen;
        this.prohibido = prohibido;
        this.miscelanea = miscelanea;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public int getId_tipo_producto() {
        return this.id_tipo_producto;
    }

    public int getCodigo() {
        return this.codigo;
    }

    public String getFecha_inicio() {
        return this.fecha_inicio;
    }

    public String getFecha_fin() {
        return this.fecha_fin;
    }

    public String getCreated_at() {
        return this.created_at;
    }

    public String getDeleted_at() {
        return this.deleted_at;
    }

    public String getImagen() {
        return this.imagen;
    }

    public int getProhibido() {
        return this.prohibido;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public void setId_tipo_producto(int id_tipo_producto) {
        this.id_tipo_producto = id_tipo_producto;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public void setFecha_inicio(String fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public void setFecha_fin(String fecha_fin) {
        this.fecha_fin = fecha_fin;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public void setDeleted_at(String deleted_at) {
        this.deleted_at = deleted_at;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isMiscelanea() {
        return this.miscelanea;
    }

    public void setMiscelanea(boolean miscelanea) {
        this.miscelanea = miscelanea;
    }
}
