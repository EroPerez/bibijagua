package Modelo;

public class Resultado {
    private float v_aduana;
    private float v_derecho;
    private float v_servicio;
    private float v_total;

    public Resultado(float v_aduana, float v_derecho, float v_servicio, float v_total) {
        this.v_aduana = v_aduana;
        this.v_derecho = v_derecho;
        this.v_servicio = v_servicio;
        this.v_total = v_total;
    }

    public void setV_aduana(float v_aduana) {
        this.v_aduana = v_aduana;
    }

    public void setV_derecho(float v_derecho) {
        this.v_derecho = v_derecho;
    }

    public void setV_servicio(float v_servicio) {
        this.v_servicio = v_servicio;
    }

    public void setV_total(float v_total) {
        this.v_total = v_total;
    }

    public float getV_aduana() {
        return this.v_aduana;
    }

    public float getV_derecho() {
        return this.v_derecho;
    }

    public float getV_servicio() {
        return this.v_servicio;
    }

    public float getV_total() {
        return this.v_total;
    }
}
