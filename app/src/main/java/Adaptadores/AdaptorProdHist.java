package Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.molanco.bibijagua.R;

import java.util.List;

import Modelo.Producto;
import SingletonBD.Singleton;

public class AdaptorProdHist extends AdaptadorProd {


    public AdaptorProdHist(Context context, List<Producto> datos, boolean is_equipaje) {
        super(context, datos, is_equipaje);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(this.context).inflate(R.layout.item_productos, parent, false);
        v.setOnClickListener(this.listener);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Producto prod = getDatos().get(position);
        String descripcion = prod.getDescripcion();
        float precio = prod.getPrecio();
        if (prod.getIsMisc() == -1) {
            prod.setIsMisc(Singleton.getInstance().getBd().isMisc(prod.getId_prod()));
        }
        if (this.is_equipaje) {
            holder.cant_prod.setText(Integer.toString(prod.getCant_equp()));
            holder.precio.setText("$" + (((float) prod.getCant_equp()) * precio) + "0");
        } else {
            holder.cant_prod.setText(Integer.toString(prod.getCant()));
            holder.precio.setText("$" + precio + "0");
        }

        holder.descripcion.setText(descripcion);

        this.datos.get(position).setIscheck(true);

        holder.agregar_prod.setVisibility(View.INVISIBLE);
        holder.carrito.setVisibility(View.VISIBLE);
        holder.cant_carr.setVisibility(View.VISIBLE);
        holder.cant_carr.setText(Integer.toString(prod.getCant_equp()));
        holder.cant_prod.setVisibility(View.INVISIBLE);
        if (!this.is_equipaje) {
            if (this.ajuste.getMetodo().equals("Valor") || (this.ajuste.getMetodo().equals("Valor/Peso") && prod.getIsMisc() == 0)) {
                final int i = position;
            } else
                holder.agregar_prod.setVisibility(View.INVISIBLE);
        }
    }


}
