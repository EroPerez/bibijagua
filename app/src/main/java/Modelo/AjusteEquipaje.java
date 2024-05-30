package Modelo;

import java.util.ArrayList;

public class AjusteEquipaje {
    private Ajuste ajuste;
    private ArrayList<Equipaje> list_prod_equip;

    public AjusteEquipaje(Ajuste ajuste, ArrayList<Equipaje> list_prod_equip) {
        this.ajuste = ajuste;
        this.list_prod_equip = list_prod_equip;
    }

    public Ajuste getAjuste() {
        return this.ajuste;
    }

    public ArrayList<Equipaje> getList_prod_equip() {
        return this.list_prod_equip;
    }

    public void setAjuste(Ajuste ajuste) {
        this.ajuste = ajuste;
    }

    public void setList_prod_equip(ArrayList<Equipaje> list_prod_equip) {
        this.list_prod_equip = list_prod_equip;
    }
}
