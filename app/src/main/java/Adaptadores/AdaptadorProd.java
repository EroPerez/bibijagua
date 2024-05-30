package Adaptadores;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.molanco.bibijagua.R;

import java.util.List;

import Modelo.Ajuste;
import Modelo.Capitulo;
import Modelo.Producto;
import SingletonBD.Singleton;

public class AdaptadorProd extends Adapter<AdaptadorProd.ViewHolder> implements OnClickListener {

    protected Ajuste ajuste;
    protected Capitulo capitulo;
    protected Context context;
    protected List<Producto> datos;
    protected View icon_pas;
    protected boolean is_equipaje;
    protected OnClickListener listener;
    protected View pb;
    protected View tip_moneda;
    protected View val_aduana;
    protected View val_derechos;
    protected View val_servicio;
    protected View val_total;
    protected View direccion;

    public class ViewHolder extends android.support.v7.widget.RecyclerView.ViewHolder {
        ImageButton agregar_prod;
        TextView cant_carr;
        TextView cant_prod;
        ImageView carrito;
        TextView descripcion;
        TextView precio;

        public ViewHolder(View itemView) {
            super(itemView);
            this.descripcion = (TextView) itemView.findViewById(R.id.descID);
            this.descripcion.setTypeface(Typeface.createFromAsset(AdaptadorProd.this.context.getAssets(), "fonts/Rubik-Bold.ttf"));
            this.cant_prod = (TextView) itemView.findViewById(R.id.cantID);
            this.precio = (TextView) itemView.findViewById(R.id.precioID);
            this.agregar_prod = (ImageButton) itemView.findViewById(R.id.botonAgregID);
            this.carrito = (ImageView) itemView.findViewById(R.id.carroID);
            this.cant_carr = (TextView) itemView.findViewById(R.id.cantEID);
            if (AdaptadorProd.this.is_equipaje) {
                this.agregar_prod.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        if (ViewHolder.this.getAdapterPosition() != -1) {
                            TextView tv_val = (TextView) AdaptadorProd.this.val_aduana;
                            Singleton.actualizValAduan(tv_val, -1, AdaptadorProd.this.datos.get(ViewHolder.this.getAdapterPosition()), (ProgressBar) AdaptadorProd.this.pb, AdaptadorProd.this.icon_pas, AdaptadorProd.this.val_total, AdaptadorProd.this.tip_moneda, AdaptadorProd.this.context);
                            TextView tv_der = (TextView) AdaptadorProd.this.val_derechos;
                            float val_der = Singleton.calcularValDerech(Float.valueOf(tv_val.getText().toString()).floatValue(), ajuste);
                            tv_der.setText(Math.abs(1000.0f - Float.valueOf(tv_val.getText().toString()).floatValue()) + "0");
                            TextView tv_ser = (TextView) AdaptadorProd.this.val_servicio;
                            Singleton.calcularValServ(tv_ser, val_der);
                            TextView tv_tot = (TextView) AdaptadorProd.this.val_total;
                            Singleton.calcularValTotal(tv_tot, (TextView) AdaptadorProd.this.tip_moneda, val_der, Float.valueOf(tv_ser.getText().toString()).floatValue());
                            Singleton.getInstance().getBd().deleteEquipaje(AdaptadorProd.this.datos.get(ViewHolder.this.getAdapterPosition()).getId_prod());
                            AdaptadorProd.this.datos.remove(ViewHolder.this.getAdapterPosition());
                            AdaptadorProd.this.notifyItemRemoved(ViewHolder.this.getAdapterPosition());
                            if (tv_tot.length() >= 7) {
                                tv_tot.setTextSize(16.0f);
                            } else {
                                tv_tot.setTextSize(19.0f);
                            }
                        }
                    }
                });
            }
        }
    }

    public AdaptadorProd(Context context, List<Producto> datos, boolean is_equipaje) {
        this.context = context;
        this.datos = datos;
        this.is_equipaje = is_equipaje;

    }

    public void setCapitulo(Capitulo capitulo) {
        this.capitulo = capitulo;
    }

    public Capitulo getCapitulo() {
        return this.capitulo;
    }

    public void setAjuste(Ajuste ajuste) {
        this.ajuste = ajuste;
    }

    public Ajuste getAjuste() {
        return this.ajuste;
    }

    public void setVal_aduana(View val_aduana) {
        this.val_aduana = val_aduana;
    }

    public void setVal_derechos(View val_derechos) {
        this.val_derechos = val_derechos;
    }

    public void setVal_servicio(View val_servicio) {
        this.val_servicio = val_servicio;
    }

    public void setVal_total(View val_total) {
        this.val_total = val_total;
    }

    public void setDireccion(View dir) {
        this.direccion = dir;
    }

    public void setPb(View pb) {
        this.pb = pb;
    }

    public void setTip_moneda(View tip_moneda) {
        this.tip_moneda = tip_moneda;
    }

    public void setIcon_pas(View icon_pas) {
        this.icon_pas = icon_pas;
    }

    public List<Producto> getDatos() {
        return this.datos;
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
        final int id_prod = prod.getId_prod();
        boolean check = prod.ischeck();
        holder.descripcion.setText(descripcion);
        if (Singleton.getInstance().getBd().existIdProdEquipaje(id_prod)) {
            this.datos.get(position).setIscheck(true);
            holder.agregar_prod.setImageDrawable(this.context.getResources().getDrawable(R.drawable.edit_remove));
            holder.carrito.setVisibility(View.VISIBLE);
            holder.cant_carr.setVisibility(View.VISIBLE);
            holder.cant_carr.setText(Integer.toString(Singleton.getInstance().getBd().consultCantProdEquip(id_prod)));
            holder.cant_prod.setVisibility(View.INVISIBLE);
            holder.carrito.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    Singleton.mostrarAgregarProd(prod, AdaptadorProd.this.context, Singleton.getInstance().getBd().consultCantProdEquip(id_prod), holder.agregar_prod, holder.cant_carr, holder.carrito, holder.cant_prod, holder.precio, AdaptadorProd.this.val_aduana, AdaptadorProd.this.icon_pas, AdaptadorProd.this.tip_moneda, AdaptadorProd.this.val_derechos, AdaptadorProd.this.val_servicio, AdaptadorProd.this.val_total, AdaptadorProd.this.pb);
                }
            });
        } else {
            holder.agregar_prod.setImageDrawable(this.context.getResources().getDrawable(R.drawable.edit_add));
            holder.carrito.setVisibility(View.INVISIBLE);
            holder.cant_carr.setVisibility(View.INVISIBLE);
            holder.cant_prod.setVisibility(View.VISIBLE);
        }

        if (!this.is_equipaje) {
            if (this.ajuste.getMetodo().equals("Valor") ||
                    (this.ajuste.getMetodo().equals("Valor/Peso") && prod.getIsMisc() == 0)) {
                final int i = position;
                final ViewHolder viewHolder = holder;
                holder.agregar_prod.setOnClickListener(new OnClickListener() {


                    /* class C00021 implements OnClickListener {
                        C00021() {
                        }

                        public void onClick(View v) {
                            Singleton.mostrarAgregarProd(prod, AdaptadorProd.this.context, Singleton.getInstance().getBd().consultCantProdEquip(id_prod), viewHolder.agregar_prod, viewHolder.cant_carr, viewHolder.carrito, viewHolder.cant_prod, viewHolder.precio, AdaptadorProd.this.val_aduana, AdaptadorProd.this.icon_pas, AdaptadorProd.this.tip_moneda, AdaptadorProd.this.val_derechos, AdaptadorProd.this.val_servicio, AdaptadorProd.this.val_total, AdaptadorProd.this.pb);

                        }
                    }*/

                    public void onClick(View v) {
                        if (AdaptadorProd.this.datos.get(i).ischeck()) {
                            Singleton.getInstance().getBd().deleteEquipaje(AdaptadorProd.this.datos.get(i).getId_prod());
                            AdaptadorProd.this.datos.get(i).setIscheck(false);
                            viewHolder.agregar_prod.setImageDrawable(AdaptadorProd.this.context.getResources().getDrawable(R.drawable.edit_add));
                            viewHolder.carrito.setVisibility(View.INVISIBLE);
                            viewHolder.cant_carr.setVisibility(View.INVISIBLE);
                            viewHolder.cant_prod.setVisibility(View.VISIBLE);

                            //Nottifcando cambios en al calculadora
                            Singleton.updateCalc();

                            //return;
                        } else {
                            AdaptadorProd.this.datos.get(i).setIscheck(Singleton.mostrarAgregarProd(AdaptadorProd.this.datos.get(i), AdaptadorProd.this.context, 1, viewHolder.agregar_prod, viewHolder.cant_carr, viewHolder.carrito, viewHolder.cant_prod, viewHolder.precio, AdaptadorProd.this.val_aduana, AdaptadorProd.this.icon_pas, AdaptadorProd.this.tip_moneda, AdaptadorProd.this.val_derechos, AdaptadorProd.this.val_servicio, AdaptadorProd.this.val_total, AdaptadorProd.this.pb).ischeck());
                            viewHolder.carrito.setOnClickListener(new OnClickListener() {

                                public void onClick(View v) {
                                    Singleton.mostrarAgregarProd(prod, AdaptadorProd.this.context, Singleton.getInstance().getBd().consultCantProdEquip(id_prod), viewHolder.agregar_prod, viewHolder.cant_carr, viewHolder.carrito, viewHolder.cant_prod, viewHolder.precio, AdaptadorProd.this.val_aduana, AdaptadorProd.this.icon_pas, AdaptadorProd.this.tip_moneda, AdaptadorProd.this.val_derechos, AdaptadorProd.this.val_servicio, AdaptadorProd.this.val_total, AdaptadorProd.this.pb);

                                }
                            });
                        }


                    }
                });
                return;
            }
            holder.agregar_prod.setVisibility(View.INVISIBLE);
        }
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


}
