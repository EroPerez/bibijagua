package Adaptadores;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.molanco.bibijagua.R;

import java.util.List;

import Modelo.Opcion;

public class AdaptadorMenu extends Adapter<AdaptadorMenu.ViewHolder> implements OnClickListener {
    private Context context;
    private List<Opcion> datos;
    private OnClickListener listener;

    public class ViewHolder extends android.support.v7.widget.RecyclerView.ViewHolder {
        RelativeLayout fondo;
        ImageView foto;
        TextView titulo;

        public ViewHolder(View itemView) {
            super(itemView);
            this.foto = (ImageView) itemView.findViewById(R.id.iconOpc);
            this.titulo = (TextView) itemView.findViewById(R.id.titulOpc);
            this.titulo.setTypeface(Typeface.createFromAsset(AdaptadorMenu.this.context.getAssets(), "fonts/Rubik-Bold.ttf"));
            this.fondo = (RelativeLayout) itemView.findViewById(R.id.fondo);
        }
    }

    public AdaptadorMenu(Context context, List<Opcion> datos) {
        this.context = context;
        this.datos = datos;
    }



    public void setTitulo(TextView tv_titul) {
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(this.context).inflate(R.layout.item_menu, parent, false);
        v.setOnClickListener(this.listener);
        return new ViewHolder(v);
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.titulo.setText(this.datos.get(position).getOpcion());
        holder.foto.setImageDrawable(this.context.getResources().getDrawable(this.datos.get(position).getId_foto()));

    }

    public int getItemCount() {
        return this.datos.size();
    }

    public List<Opcion> getDatos() {
        return this.datos;
    }

    public void setActv(Activity actv) {
    }

    public void onClick(View v) {
        if (this.listener != null) {
            this.listener.onClick(v);
        }
    }

    public void setOnClickListener(OnClickListener listener) {
        this.listener = listener;
    }
}
