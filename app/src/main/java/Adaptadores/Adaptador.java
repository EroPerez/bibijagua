package Adaptadores;

import Modelo.Capitulo;
import Modelo.Foto;
import SingletonBD.Singleton;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.molanco.bibijagua.R;
import java.util.List;

public class Adaptador extends Adapter<Adaptador.ViewHolder> implements OnClickListener {
    private int TYPE_DOWN = 2;
    private final int TYPE_HIGH = 1;
    private Context context;
    private List<Capitulo> datos;
    private OnClickListener listener;

    public class ViewHolder extends android.support.v7.widget.RecyclerView.ViewHolder {
        ImageView foto;
        TextView titulo;

        public ViewHolder(View itemView) {
            super(itemView);
            //this.foto = (ImageView) itemView.findViewById(R.id.fotoID);
            this.titulo = (TextView) itemView.findViewById(R.id.tituloID);
            this.titulo.setTypeface(Typeface.createFromAsset(Adaptador.this.context.getAssets(), "fonts/Rubik-Bold.ttf"));
        }
    }

    public Adaptador(Context context, List<Capitulo> datos) {
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
        View v = LayoutInflater.from(this.context).inflate(R.layout.item_capitulo, parent, false);
        v.setOnClickListener(this.listener);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.titulo.setText(this.datos.get(position).getDescripcion());
        /*Foto f = Singleton.getInstance().buscarFoto(this.datos.get(position).getImagen());
        if (f != null) {
            holder.foto.setImageBitmap(decode(this.context.getResources(), f.getId(), 80, 80));
        }*/
    }

    public Bitmap decode(Resources resc, int id, int h, int w) {
        Options options = new Options();
        options.inSampleSize = 3;
        return BitmapFactory.decodeResource(resc, id, options);
    }

    private int calculateSimpleSize(Options options, int w, int h) {
        int h_aux = options.outHeight;
        int w_aux = options.outWidth;
        int sampleSize = 4;
        if (h_aux > h || w_aux > w) {
            int mitad_h = h_aux / 2;
            int mitad_w = w_aux / 2;
            while (mitad_h / sampleSize > h && mitad_w / sampleSize > w) {
                sampleSize *= 2;
            }
        }
        return sampleSize;
    }

    public int getItemCount() {
        return this.datos.size();
    }

    public List<Capitulo> getDatos() {
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
