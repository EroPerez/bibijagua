package Adaptadores;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.molanco.bibijagua.R;

import java.util.List;

import Modelo.Pregunta;

public class AdaptadorFaq extends Adapter<AdaptadorFaq.ViewHolder> implements OnClickListener {
    private int TYPE_DOWN = 2;
    private Context context;
    private List<Pregunta> datos;
    private OnClickListener listener;

    public class ViewHolder extends android.support.v7.widget.RecyclerView.ViewHolder {
        RelativeLayout acordion;
        ImageButton desp;
        LinearLayout pie;
        TextView resp;
        TextView titulo;

        public ViewHolder(View itemView) {
            super(itemView);
            this.desp = (ImageButton) itemView.findViewById(R.id.iconD);
            this.titulo = (TextView) itemView.findViewById(R.id.pregID);
            this.titulo.setTypeface(Typeface.createFromAsset(AdaptadorFaq.this.context.getAssets(), "fonts/Rubik-Bold.ttf"));
            this.resp = (TextView) itemView.findViewById(R.id.respID);
            this.resp.setTypeface(Typeface.createFromAsset(AdaptadorFaq.this.context.getAssets(), "fonts/Rubik-Bold.ttf"));
            this.acordion = (RelativeLayout) itemView.findViewById(R.id.press);
            this.pie = (LinearLayout) itemView.findViewById(R.id.pie);
        }
    }

    public AdaptadorFaq(Context context, List<Pregunta> datos) {
        this.context = context;
        this.datos = datos;
    }

    public int getTYPE_HIGH() {
        return 1;
    }

    public int getTYPE_DOWN() {
        return this.TYPE_DOWN;
    }

    public int getItemViewType(int position) {
        if (position == 0 || position % 3 == 0) {
            return 1;
        }
        return this.TYPE_DOWN;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(this.context).inflate(R.layout.item_fag, parent, false);
        v.setOnClickListener(this.listener);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.titulo.setText(this.datos.get(position).getTitulo());
        holder.resp.setText(this.datos.get(position).getRespuesta());
        holder.acordion.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (holder.resp.getLayoutParams().height < 100) {
                    holder.resp.getLayoutParams().height = 100;
                    holder.pie.setVisibility(View.VISIBLE);
                    holder.desp.setImageDrawable(AdaptadorFaq.this.context.getResources().getDrawable(R.drawable.flecha_abajo));
                } else {
                    holder.resp.getLayoutParams().height = -2;
                    holder.pie.setVisibility(View.INVISIBLE);
                    holder.desp.setImageDrawable(AdaptadorFaq.this.context.getResources().getDrawable(R.drawable.flecha_arriba));
                }
                holder.resp.setLayoutParams(holder.resp.getLayoutParams());
            }
        });
    }

    public int getItemCount() {
        return this.datos.size();
    }

    public List<Pregunta> getDatos() {
        return this.datos;
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
