package Adaptadores;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.molanco.bibijagua.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import Modelo.Historial;

public class AdaptadorDiario extends Adapter<AdaptadorDiario.ViewHolder> implements OnClickListener {

    private Context context;
    private List<Historial> datos;

    private OnClickListener listener;
    public List<Historial> items2Delete;


    public class ViewHolder extends android.support.v7.widget.RecyclerView.ViewHolder {

        TextView year;
        TextView month;
        TextView descripcion;
        TextView day;
        ImageView diario;
        CheckBox diario2Del;
        public int itemPos;

        public ViewHolder(View itemView) {
            super(itemView);
            this.descripcion = (TextView) itemView.findViewById(R.id.descID);
            this.descripcion.setTypeface(Typeface.createFromAsset(AdaptadorDiario.this.context.getAssets(), "fonts/Rubik-Bold.ttf"));
            this.month = (TextView) itemView.findViewById(R.id.textMonth);
            this.day = (TextView) itemView.findViewById(R.id.textDay);
            this.year = (TextView) itemView.findViewById(R.id.textYear);
            this.diario = (ImageView) itemView.findViewById(R.id.ivViajero);
            this.diario2Del = (CheckBox) itemView.findViewById(R.id.ckbSelViaje);

            this.diario.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {

                    ViewHolder.this.diario.setVisibility(View.GONE);
                    ViewHolder.this.diario2Del.setVisibility(View.VISIBLE);
                    ViewHolder.this.diario2Del.setChecked(true);
                    AdaptadorDiario.this.items2Delete.add(AdaptadorDiario.this.datos.get(itemPos));

                }
            });

            this.diario2Del.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {

                    ViewHolder.this.diario.setVisibility(View.VISIBLE);
                    ViewHolder.this.diario2Del.setVisibility(View.GONE);
                    ViewHolder.this.diario2Del.setChecked(false);
                    AdaptadorDiario.this.items2Delete.remove(AdaptadorDiario.this.datos.get(itemPos));

                }
            });

        }
    }

    public AdaptadorDiario(Context context, List<Historial> datos) {
        this.context = context;
        this.datos = datos;
        this.items2Delete = new ArrayList<>();
    }

    public List<Historial> getDatos() {
        return this.datos;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(this.context).inflate(R.layout.item_diario, parent, false);
        v.setOnClickListener(this.listener);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.descripcion.setText(this.datos.get(position).getDesc());
        holder.itemPos = position;

        SimpleDateFormat dayFormat = new SimpleDateFormat(
                "dd", Locale.getDefault());
        holder.day.setText(dayFormat.format(this.datos.get(position).getFecha_viaje()));

        SimpleDateFormat monthFormat = new SimpleDateFormat(
                "MMM", Locale.getDefault());
        holder.month.setText(monthFormat.format(this.datos.get(position).getFecha_viaje()));

        SimpleDateFormat yearFormat = new SimpleDateFormat(
                "yy", Locale.getDefault());
        holder.year.setText(yearFormat.format(this.datos.get(position).getFecha_viaje()));

    }

    @Override
    public int getItemCount() {
        return this.datos.size();
    }

    @Override
    public void onClick(View v) {
        if (this.listener != null) {
            this.listener.onClick(v);
        }
    }

    public void setOnClickListener(OnClickListener listener) {
        this.listener = listener;
    }
}
