package Modelo;

import com.google.gson.Gson;

import java.util.Date;

/**
 * Created by Michel on 11/5/2018.
 */

public class Historial {

    private Integer id;
    private Date fecha_viaje;
    private String desc;
    private String equipaje;


    public Historial(Date fecha_viaje, String desc, String equipaje, Integer id) {
        this.fecha_viaje = fecha_viaje;
        this.desc = desc;
        this.equipaje = equipaje;
        this.id = id;
    }

    public Date getFecha_viaje() {
        return fecha_viaje;
    }

    public void setFecha_viaje(Date fecha_viaje) {
        this.fecha_viaje = fecha_viaje;
    }

    public String getEquipaje() {
        return equipaje;
    }

    public void setEquipaje(String equipajeJSON) {
        this.equipaje = equipajeJSON;
    }


    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getId() {
        return id;
    }

    public AjusteEquipaje getAjusteEquip() {
        Gson gson = new Gson();
        return gson.fromJson(this.equipaje , AjusteEquipaje.class);
    }

    @Override
    public String toString() {
        return Integer.toString(this.id);
    }

    @Override
    public boolean equals(Object obj) {
        return  this.id.equals(((Historial) obj).getId());
    }
}
