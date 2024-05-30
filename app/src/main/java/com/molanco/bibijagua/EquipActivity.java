package com.molanco.bibijagua;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.Toolbar.OnMenuItemClickListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;

import Adaptadores.AdaptadorProd;
import Adaptadores.AdaptorProdHist;
import Modelo.Ajuste;
import Modelo.AjusteEquipaje;
import Modelo.Equipaje;
import Modelo.Historial;
import Modelo.Producto;
import SingletonBD.Singleton;

public class EquipActivity extends AppCompatActivity {
    AsyncTaskCargaDatos ATCargaDatos = null;
    AlertDialog ad_aux = null;
    LinearLayout clic;
    ProgressBar pb;
    ProgressBar pb_qr;
    TextView peso_aj;
    ImageButton qr;
    RadioGroup radioG;
    RadioGroup radioGC;
    RelativeLayout rell;
    RelativeLayout rell2;
    Spinner spin;
    String string_peso = "0.0 kg";
    String string_valor = "$ 0.00 ";
    TextView tv_QR;
    TextView tv_qr;
    TextView valor_aj;
    private AdaptadorProd adaptador;
    private Ajuste ajuste = null;
    private String categoria = "PV";
    private ImageView icon_pas;
    private int importacion = 1;
    private ArrayList<Producto> list_productos = new ArrayList();
    private String metodo = "Valor/Peso";
    private String moneda = "cup";
    private TextView peso;
    private RecyclerView recv;
    private TextView tip_moneda;
    private float v_peso = 0.00f;
    private float v_valor = 0.00f;
    private TextView val_aduana;
    private TextView val_derecho;
    private TextView val_servicio;
    private TextView val_total;
    private float value = 0.00f;
    private DatePicker fecha_viaje;
    private EditText desc_viaje;
    int id_historial = -1;

    private TextView direccion;
    private EditText txtNombrePDF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_equip);
        this.clic = (LinearLayout) findViewById(R.id.clicpeso);
        this.tv_QR = (TextView) findViewById(R.id.tvQR);
        this.tv_qr = (TextView) findViewById(R.id.tvqr);
        this.tv_QR.setVisibility(View.INVISIBLE);
        this.tv_qr.setVisibility(View.GONE);
        this.qr = (ImageButton) findViewById(R.id.codigQR);
        this.qr.setVisibility(View.VISIBLE);
        this.val_aduana = (TextView) findViewById(R.id.aduanaID);
        this.val_derecho = (TextView) findViewById(R.id.derechosID);
        this.val_servicio = (TextView) findViewById(R.id.serviciosID);
        this.val_total = (TextView) findViewById(R.id.totalID);
        this.tip_moneda = (TextView) findViewById(R.id.moneda);
        this.peso = (TextView) findViewById(R.id.pesoID);
        this.icon_pas = (ImageView) findViewById(R.id.iconPasad);
        this.pb = (ProgressBar) findViewById(R.id.pb);
        this.pb_qr = (ProgressBar) findViewById(R.id.cargarQR);
        this.pb_qr.setVisibility(View.INVISIBLE);
        Drawable drawable = getResources().getDrawable(R.drawable.vertical_progress_bar);
        Drawable drawable_rojo = getResources().getDrawable(R.drawable.bar_progress_rojo);
        this.pb.setProgress(0);
        this.pb.setSecondaryProgress(100);
        this.pb.setMax(100);
        this.pb.setProgressDrawable(drawable);
        this.pb_qr.setProgress(0);
        this.pb_qr.setSecondaryProgress(100);
        this.pb_qr.setMax(100);
        this.pb_qr.setProgressDrawable(drawable_rojo);

        this.direccion = (TextView) findViewById(R.id.direccion);
        this.direccion.setText(getResources().getString(R.string.direccionF));

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.id_historial = extras.getInt("id_historial");
            this.qr.setVisibility(View.INVISIBLE);
            this.tv_QR.setVisibility(View.INVISIBLE);
            this.tv_qr.setVisibility(View.INVISIBLE);
            findViewById(R.id.clearIB).setVisibility(View.INVISIBLE);
            AjusteEquipaje aj = Singleton.getInstance().getBd().consultarHistoPorID(id_historial).getAjusteEquip();
            this.ajuste = aj.getAjuste();
            this.list_productos = Singleton.getInstance().getBd().consultarProdEquip(aj.getList_prod_equip());

        } else {
            this.ajuste = Singleton.getInstance().getBd().consultAjustes();
        }

        if (this.ajuste != null) {
            this.peso.setText(Float.toString(this.ajuste.getPeso()));
            if (extras == null)
                this.clic.setOnClickListener(new C02341());
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        if (extras == null) {
//            toolbar.inflateMenu(R.menu.equipaje);
//            toolbar.setOnMenuItemClickListener(new C03572());
//        }
        this.recv = (RecyclerView) findViewById(R.id.recv11);
        this.recv.setLayoutManager(new LinearLayoutManager(this));
        this.recv.setHasFixedSize(true);

        if (this.ajuste != null) {
            cargarProductosEquip();
        }

    }

    private void cargarProductosEquip() {
        if (this.ajuste.getMetodo().equals("Valor/Peso") && this.ajuste.getPeso() > 25.0f) {
            this.value = (this.ajuste.getPeso() - 25.0f) * 10.0f;
        }
        if (this.ajuste.getMetodo().equals("Valor") && this.ajuste.getValor() > 0.0f) {
            this.value = this.ajuste.getValor();
        }
        if (id_historial == -1)
            this.list_productos = Singleton.getInstance().getBd().consultarProdEquip();

        float val_derech;
        if (this.list_productos.size() > 0) {
            for (int i = 0; i < this.list_productos.size(); i++) {
                this.value += (((float) this.list_productos.get(i).getCant_equp()) * this.list_productos.get(i).getPrecio());
            }
            val_derech = calcularValDerech(this.value);
            //this.val_derecho.setText(Float.toString(val_derech));
            this.val_derecho.setText(Float.toString(Math.abs(1000.0f - this.value)) + "0");
            this.val_servicio.setText(Float.toString(calcularValServ(val_derech)) + "0");
            this.val_total.setText("$" + calcularValTotal(val_derech, Float.valueOf(this.val_servicio.getText().toString()).floatValue()) + "0");
            if (this.val_total.length() >= 7) {
                this.val_total.setTextSize(16.0f);
                this.tip_moneda.setTextSize(13.0f);
            } else {
                this.val_total.setTextSize(19.0f);
                this.tip_moneda.setTextSize(19.0f);
            }
            if (id_historial == -1) {
                this.adaptador = new AdaptadorProd(this, this.list_productos, true);
            } else {
                this.adaptador = new AdaptorProdHist(this, this.list_productos, true);
            }
            this.adaptador.setVal_aduana(this.val_aduana);
            this.adaptador.setVal_derechos(this.val_derecho);
            this.adaptador.setVal_servicio(this.val_servicio);
            this.adaptador.setVal_total(this.val_total);
            this.adaptador.setIcon_pas(this.icon_pas);
            this.adaptador.setTip_moneda(this.tip_moneda);
            this.adaptador.setPb(this.pb);
            this.adaptador.setDireccion(this.direccion);
            this.recv.setAdapter(this.adaptador);

        } else {
            val_derech = calcularValDerech(this.value);
            this.val_derecho.setText(Float.toString(Math.abs(1000.0f - this.value)) + "0");
            this.val_servicio.setText(Float.toString(calcularValServ(val_derech)) + "0");
            this.val_total.setText("$" + calcularValTotal(val_derech, Float.valueOf(this.val_servicio.getText().toString()).floatValue()) + "0");
            if (this.val_total.length() >= 7) {
                this.val_total.setTextSize(16.0f);
                this.tip_moneda.setTextSize(13.0f);
            } else {
                this.val_total.setTextSize(19.0f);
                this.tip_moneda.setTextSize(19.0f);
            }
        }

        this.val_aduana.setText(Float.toString(this.value) + "0");


        if (this.value <= ((float) 1000)) {
            this.pb.setProgressDrawable(getResources().getDrawable(R.drawable.vertical_progress_bar));
            this.icon_pas.setVisibility(View.INVISIBLE);
            this.val_total.setVisibility(View.VISIBLE);
            this.tip_moneda.setVisibility(View.VISIBLE);
            this.pb.setProgress((int) porcientoNumero((double) this.value, (double) 1000));

            this.setDireccion(getResources().getString(R.string.direccionF));
        } else {
            this.pb.setProgress(100);
            this.pb.setProgressDrawable(getResources().getDrawable(R.drawable.vertical_progress_bar));
            this.icon_pas.setVisibility(View.VISIBLE);
            this.val_total.setVisibility(View.INVISIBLE);
            this.tip_moneda.setVisibility(View.INVISIBLE);

            this.setDireccion(getResources().getString(R.string.direccionS));
            mostrarInfo();
        }
        this.tip_moneda.setText(this.ajuste.getMoneda());
        //Float.valueOf(this.val_servicio.getText().toString());
    }

    public void setDireccion(String strDir) {
        this.direccion.setText(strDir);
    }

    public void mostrarInfo() {
        Builder builder = new Builder(this);
        View v = getLayoutInflater().inflate(R.layout.informacion, null);
        ((TextView) v.findViewById(R.id.infoID)).setText(R.string.info_pasado);
        builder.setView(v);
        builder.create().show();
    }

    public void irAtras(View v) {
        finish();
    }

    public void generarQR(View v) {
        this.qr.setVisibility(View.INVISIBLE);
        this.pb_qr.setVisibility(View.VISIBLE);
        this.tv_qr.setVisibility(View.GONE);
        this.tv_QR.setVisibility(View.VISIBLE);
        this.ATCargaDatos = new AsyncTaskCargaDatos(getApplicationContext());
        this.ATCargaDatos.execute(new Void[0]);
    }

    private void mostrarCodigoQR(final String JSonEquipaje) {
        Builder builder = new Builder(this);
        View v = getLayoutInflater().inflate(R.layout.historial, null);

        fecha_viaje = (DatePicker) v.findViewById(R.id.datePicker);

        desc_viaje = (EditText) v.findViewById(R.id.edtHistorial);


        Button guardar = (Button) v.findViewById(R.id.guardar);

        builder.setView(v);
        AlertDialog ad = builder.create();
        final AlertDialog tempAlert_dialog = ad;
        guardar.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {

                try {
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(fecha_viaje.getYear(), fecha_viaje.getMonth(), fecha_viaje.getDayOfMonth());
                    final String desc = desc_viaje.getText().toString();

                    Historial histoViaje = new Historial(calendar.getTime(), desc, JSonEquipaje, null);

                    if (Singleton.getInstance().getBd().insertHistorial(histoViaje) != -1) {
                        Toast.makeText(EquipActivity.this.getApplicationContext(), getResources().getString(R.string.msg_historial), 1).show();
                        qr.setVisibility(View.INVISIBLE);
                        tv_QR.setVisibility(View.INVISIBLE);
                        tv_qr.setVisibility(View.GONE);
                    }

                } catch (Exception e) {

                    Toast.makeText(EquipActivity.this.getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }


                tempAlert_dialog.dismiss();
            }
        });
        ad.show();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.equipaje, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.settings) {
            mostrarConfig();
        }
        return super.onOptionsItemSelected(item);
    }

    private float calcularValDerech(float val_aduana) {
        float valor_a_pagar = val_aduana - 50.0f;
        float result = 0.0f;
        if (((double) val_aduana) > 50.0d && ((double) val_aduana) <= 500.0d) {
            result = valor_a_pagar;
        }
        if (valor_a_pagar > 500.0f && valor_a_pagar <= 1000.0f) {
            result = 450.0f + (2.0f * (valor_a_pagar - 450.0f));
        }
//        if ((this.ajuste.getCategoria().equals("Nacional Residente") || this.ajuste.getCategoria().equals("Extranjero Residente")) && this.ajuste.getImportacion() == 2) {
//            return result * 25.0f;
//        }

//        if (this.ajuste.getCategoria().equals("Cubano o Extranjero Residente") && ajuste.getImportacion() == 2) {
//            return result;
//        }
        return result;
    }

    private float calcularValServ(float val_derecho) {
        if (val_derecho > 0.0f) {
            return 2.0f;
        }
        return 0.0f;
    }

    private float calcularValTotal(float val_derecho, float val_servicio) {
        return val_derecho + val_servicio;
    }

    private double porcientoNumero(double parte, double total) {
        return (parte / total) * 100.0d;
    }

    public void mostrarConfig() {
        Builder builder = new Builder(this);
        View v = getLayoutInflater().inflate(R.layout.activity_config, null);
        this.ajuste = Singleton.getInstance().getBd().consultAjustes();
        this.radioGC = (RadioGroup) v.findViewById(R.id.rgCateg);
        this.radioG = (RadioGroup) v.findViewById(R.id.radioGID);
        this.peso_aj = (TextView) v.findViewById(R.id.tvPeso);
        this.valor_aj = (TextView) v.findViewById(R.id.tvValor);
        this.rell = (RelativeLayout) v.findViewById(R.id.rellay);
        this.rell2 = (RelativeLayout) v.findViewById(R.id.rellay2);
        this.spin = (Spinner) v.findViewById(R.id.spinner_categ);

        String categ[] = getResources().getStringArray(R.array.categoria_viajero);

        final ArrayAdapter<String> adaptador_categ = new ArrayAdapter(this, 17367043, categ);
        this.spin.setAdapter(adaptador_categ);
        if (this.ajuste != null) {
            this.categoria = this.ajuste.getCategoria();
            this.metodo = this.ajuste.getMetodo();
            this.moneda = this.ajuste.getMoneda();
            this.importacion = this.ajuste.getImportacion();
            this.v_peso = this.ajuste.getPeso();
            this.v_valor = this.ajuste.getValor();
            for (int i = 0; i < categ.length; i++) {
                if (categ[i].equals(this.categoria)) {
                    this.spin.setSelection(i);
                    break;
                }
            }
            this.string_peso = Float.toString(this.v_peso) + " kg ";
            this.string_valor = " $ " + Float.toString(this.v_valor);
            this.peso_aj.setText(this.string_peso);
            this.valor_aj.setText(this.string_valor);
        }
        this.spin.setOnItemSelectedListener(new OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                EquipActivity.this.categoria = adaptador_categ.getItem(position);
                if (/*position == 0 ||*/ position == 1) {
                    EquipActivity.this.radioGC.setVisibility(View.VISIBLE);
                    if (EquipActivity.this.importacion == 2) {
                        EquipActivity.this.radioGC.check(R.id.segundaID);
                        EquipActivity.this.moneda = "cuc";
                    } else
                        EquipActivity.this.moneda = "cup";

                    EquipActivity.this.radioGC.setOnCheckedChangeListener(new C02361());
                    EquipActivity.this.moneda = "cup";
                    return;
                }
                EquipActivity.this.radioGC.setVisibility(View.INVISIBLE);
                EquipActivity.this.moneda = "cuc";
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }

            class C02361 implements OnCheckedChangeListener {
                C02361() {
                }

                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (checkedId == R.id.primeraID) {
                        EquipActivity.this.importacion = 1;
                    }
                    if (checkedId == R.id.segundaID) {
                        EquipActivity.this.importacion = 2;
                    }
                }
            }
        });
        if (this.metodo.equals("Valor")) {
            this.radioG.check(R.id.valorID);
            this.rell.setVisibility(View.INVISIBLE);
            this.rell2.setVisibility(View.VISIBLE);
        }
        this.radioG.setOnCheckedChangeListener(new C02385());
        this.peso_aj.setOnClickListener(new C02396());
        this.valor_aj.setOnClickListener(new C02407());
        Button guardar = (Button) v.findViewById(R.id.guardarID);
        builder.setView(v);
        AlertDialog ad = builder.create();
        final AlertDialog tempAlert_dialog = ad;
        guardar.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (tempAlert_dialog.isShowing()) {
                    if (EquipActivity.this.metodo.equals("Valor/Peso")) {
                        Singleton.getInstance().getBd().deleteMiscEquipaje();
                    }
                    Singleton.getInstance().getBd().updateAjuste(EquipActivity.this.categoria, EquipActivity.this.importacion, EquipActivity.this.v_peso, EquipActivity.this.v_valor, EquipActivity.this.moneda, EquipActivity.this.metodo);
                    EquipActivity.this.ajuste = Singleton.getInstance().getBd().consultAjustes();
                    EquipActivity.this.value = 0.0f;
                    EquipActivity.this.peso.setText(Float.toString(EquipActivity.this.ajuste.getPeso()));
                    EquipActivity.this.cargarProductosEquip();
                    tempAlert_dialog.dismiss();
                }
            }
        });
        ad.show();
    }

    private void mostrarSeleccPeso(final int config) {
        if ((this.ad_aux != null && !this.ad_aux.isShowing()) || this.ad_aux == null) {
            this.ad_aux = null;
            Builder builder = new Builder(this);
            View v = getLayoutInflater().inflate(R.layout.selec_peso, null);
            final NumberPicker npE = (NumberPicker) v.findViewById(R.id.npEntero);
            npE.setMinValue(0);
            npE.setMaxValue(125);
            this.v_peso = this.ajuste.getPeso();
            if (this.v_peso > 0.0f) {
                npE.setValue((int) this.v_peso);
            }
            Button aceptar = (Button) v.findViewById(R.id.aceptar);
            builder.setView(v);
            this.ad_aux = builder.create();
            final AlertDialog tempAlert_dialog = this.ad_aux;
            aceptar.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    if (tempAlert_dialog.isShowing()) {
                        EquipActivity.this.v_peso = Float.valueOf(npE.getValue() + ".0").floatValue();
                        if (config == 1) {
                            EquipActivity.this.peso_aj.setText(npE.getValue() + ".0" + " kg ");
                        } else {
                            float aux = EquipActivity.this.ajuste.getPeso();
                            Singleton.getInstance().getBd().updateAjuste(EquipActivity.this.categoria, EquipActivity.this.importacion, EquipActivity.this.v_peso, EquipActivity.this.v_valor, EquipActivity.this.moneda, EquipActivity.this.metodo);
                            EquipActivity.this.ajuste = Singleton.getInstance().getBd().consultAjustes();
                            EquipActivity.this.peso.setText(Float.toString(EquipActivity.this.ajuste.getPeso()));
                            float rest = 0.0f;
                            if (aux > 25.0f) {
                                rest = (aux - 25.0f) * 10.0f;
                            }
                            if (EquipActivity.this.ajuste.getPeso() > 25.0f) {
                                EquipActivity.this.value = (Float.valueOf(EquipActivity.this.val_aduana.getText().toString()).floatValue() - rest) + ((EquipActivity.this.ajuste.getPeso() - 25.0f) * 10.0f);
                            } else {
                                EquipActivity.this.value = Float.valueOf(EquipActivity.this.val_aduana.getText().toString()).floatValue() - rest;
                            }
                            EquipActivity.this.val_aduana.setText(Float.toString(EquipActivity.this.value) + "0");
                            float val_derech = EquipActivity.this.calcularValDerech(Float.valueOf(EquipActivity.this.val_aduana.getText().toString()).floatValue());
                            //EquipActivity.this.val_derecho.setText(Float.toString(val_derech));
                            EquipActivity.this.val_derecho.setText(Float.toString(Math.abs(1000.0f - EquipActivity.this.value)) + "0");
                            EquipActivity.this.val_servicio.setText(Float.toString(EquipActivity.this.calcularValServ(val_derech)) + "0");
                            EquipActivity.this.val_total.setText("$" + EquipActivity.this.calcularValTotal(val_derech, Float.valueOf(EquipActivity.this.val_servicio.getText().toString()).floatValue()) + "0");
                            if (EquipActivity.this.val_total.length() >= 7) {
                                EquipActivity.this.val_total.setTextSize(16.0f);
                                EquipActivity.this.tip_moneda.setTextSize(13.0f);
                            } else {
                                EquipActivity.this.val_total.setTextSize(19.0f);
                                EquipActivity.this.tip_moneda.setTextSize(19.0f);
                            }
                            EquipActivity.this.val_aduana.setText(Float.toString(EquipActivity.this.value) + "0");
                            if (EquipActivity.this.value <= ((float) 1000)) {
                                EquipActivity.this.pb.setProgressDrawable(EquipActivity.this.getResources().getDrawable(R.drawable.bar_progress));
                                EquipActivity.this.icon_pas.setVisibility(View.INVISIBLE);
                                EquipActivity.this.val_total.setVisibility(View.VISIBLE);
                                EquipActivity.this.tip_moneda.setVisibility(View.VISIBLE);
                                EquipActivity.this.pb.setProgress((int) EquipActivity.this.porcientoNumero((double) EquipActivity.this.value, (double) 1000));

                                EquipActivity.this.direccion.setText(getResources().getString(R.string.direccionF));
                            } else {
                                EquipActivity.this.pb.setProgress(100);
                                EquipActivity.this.pb.setProgressDrawable(EquipActivity.this.getResources().getDrawable(R.drawable.bar_progress));
                                EquipActivity.this.icon_pas.setVisibility(View.VISIBLE);
                                EquipActivity.this.val_total.setVisibility(View.INVISIBLE);
                                EquipActivity.this.tip_moneda.setVisibility(View.INVISIBLE);

                                EquipActivity.this.direccion.setText(getResources().getString(R.string.direccionS));
                                EquipActivity.this.mostrarInfo();
                            }
                            EquipActivity.this.tip_moneda.setText(EquipActivity.this.ajuste.getMoneda());
                            //Float.valueOf(EquipActivity.this.val_servicio.getText().toString());
                        }
                        tempAlert_dialog.dismiss();
                    }
                }
            });
            this.ad_aux.show();
        }
    }

    private void mostrarSeleccValor() {
        if ((this.ad_aux != null && !this.ad_aux.isShowing()) || this.ad_aux == null) {
            this.ad_aux = null;
            Builder builder = new Builder(this);
            View v = getLayoutInflater().inflate(R.layout.selec_valor, null);
            final NumberPicker npE = (NumberPicker) v.findViewById(R.id.npEntero);
            npE.setMinValue(0);
            npE.setMaxValue(1000);
            if (this.v_valor > 0.0f) {
                npE.setValue((int) this.v_valor);
            }
            Button aceptar = (Button) v.findViewById(R.id.aceptar);
            builder.setView(v);
            this.ad_aux = builder.create();
            final AlertDialog tempAlert_dialog = this.ad_aux;
            aceptar.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    if (tempAlert_dialog.isShowing()) {
                        EquipActivity.this.valor_aj.setText(" $ " + npE.getValue() + ".0");
                        EquipActivity.this.v_valor = Float.valueOf(npE.getValue() + ".0").floatValue();
                        tempAlert_dialog.dismiss();
                    }
                }
            });
            this.ad_aux.show();
        }
    }

    public void clearEquipaje() {
        Singleton.getInstance().getBd().clearEquipaje();
        Singleton.getInstance().getBd().updateAjuste(null, 0, 0.0f, 0.0f, null, null);
        this.ajuste.setPeso(0.0f);
        this.ajuste.setValor(0.0f);
        if (!this.list_productos.isEmpty()) {
            this.list_productos.clear();

            if (this.adaptador != null || !this.adaptador.getDatos().isEmpty()) {
                this.adaptador.getDatos().clear();
                this.adaptador.notifyDataSetChanged();
            }
        }
        if (this.value >= 1000.0f) {
            this.value = 0.0f;
            this.pb.setProgressDrawable(getResources().getDrawable(R.drawable.vertical_progress_bar));
            this.icon_pas.setVisibility(View.INVISIBLE);
            this.val_total.setVisibility(View.VISIBLE);
            this.tip_moneda.setVisibility(View.VISIBLE);
        }
        this.pb.setProgress(0);
        this.val_aduana.setText("0.00");
        this.direccion.setText(getResources().getString(R.string.direccionF));
        this.val_derecho.setText("1000.00");
        this.val_servicio.setText("0.00");
        this.val_total.setText("EXENTO");
        this.val_total.setTextSize(19.0f);
        this.peso.setText("0.0");
        this.tip_moneda.setTextSize(19.0f);
    }

    protected void onDestroy() {
        super.onDestroy();
        if (this.ATCargaDatos != null && !this.ATCargaDatos.isCancelled()) {
            this.ATCargaDatos.cancel(true);
        }
        this.value = 0.0f;
    }

    public void clear(View v) {
        clearEquipaje();
    }

    public void savePDF(View v) {
        Builder builder = new Builder(this);
        v = getLayoutInflater().inflate(R.layout.save_declaracion, null);

        txtNombrePDF = (EditText) v.findViewById(R.id.txtNombrePDF);

        Button guardar = (Button) v.findViewById(R.id.guardar);

        builder.setView(v);
        AlertDialog ad = builder.create();
        final AlertDialog tempAlert_dialog = ad;
        guardar.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {

                Singleton.printDeclaracion(EquipActivity.this.txtNombrePDF.getText().toString() + ".pdf",EquipActivity.this);

                tempAlert_dialog.dismiss();
            }
        });
        ad.show();

    }

    public ArrayList<Producto> getListaProductos(){
        return this.list_productos;
    }

    class C02341 implements OnClickListener {
        C02341() {
        }

        public void onClick(View v) {
            EquipActivity.this.metodo = EquipActivity.this.ajuste.getMetodo();
            if (EquipActivity.this.metodo.equals("Valor/Peso")) {
                EquipActivity.this.mostrarSeleccPeso(0);
            } else {
                Toast.makeText(EquipActivity.this.getApplicationContext(), "No se puede insertar el peso. Cambie al método Valor/Peso en Ajustes.", 0).show();
            }
        }
    }

    class C02385 implements OnCheckedChangeListener {
        C02385() {
        }

        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.pesoVID) {
                EquipActivity.this.metodo = "Valor/Peso";
                EquipActivity.this.v_valor = 0.0f;
                EquipActivity.this.valor_aj.setText("$ 0.00");
                EquipActivity.this.rell.setVisibility(0);
                EquipActivity.this.rell2.setVisibility(4);
                return;
            }
            EquipActivity.this.metodo = "Valor";
            EquipActivity.this.v_peso = 0.0f;
            EquipActivity.this.peso_aj.setText("0.0 kg");
            EquipActivity.this.rell.setVisibility(4);
            EquipActivity.this.rell2.setVisibility(0);
        }
    }

    class C02396 implements OnClickListener {
        C02396() {
        }

        public void onClick(View v) {
            EquipActivity.this.mostrarSeleccPeso(1);
        }
    }

    class C02407 implements OnClickListener {
        C02407() {
        }

        public void onClick(View v) {
            EquipActivity.this.mostrarSeleccValor();
        }
    }

    public class AsyncTaskCargaDatos extends AsyncTask<Void, Integer, Void> {
        AjusteEquipaje datos_qr = null;
        int f5i = 0;
        String json = "null";
        ArrayList<Equipaje> list_equip = null;
        Context mContext;

        AsyncTaskCargaDatos(Context context) {
            this.mContext = context;
        }

        protected void onPreExecute() {
            super.onPreExecute();
            this.list_equip = Singleton.getInstance().getBd().consultarEquip();
            this.datos_qr = new AjusteEquipaje(EquipActivity.this.ajuste, this.list_equip);
            EquipActivity.this.getWindow().setFlags(16, 16);
        }

        protected Void doInBackground(Void... params) {
            publishProgress(new Integer[]{Integer.valueOf(0)});
            if (this.datos_qr != null) {
                this.json = new Gson().toJson(this.datos_qr);
            }
            while (this.f5i < 100) {
                try {
                    Thread.sleep(50);
                    publishProgress(new Integer[]{Integer.valueOf(this.f5i)});
                } catch (InterruptedException e2) {
                    e2.printStackTrace();
                }
                this.f5i++;
            }
            return null;
        }

        protected void onProgressUpdate(Integer... value) {
            EquipActivity.this.pb_qr.setProgress(this.f5i);
        }

        protected void onPostExecute(Void result) {
            EquipActivity.this.mostrarCodigoQR(this.json);
            EquipActivity.this.qr.setVisibility(View.VISIBLE);
            EquipActivity.this.pb_qr.setProgress(View.VISIBLE);
            EquipActivity.this.pb_qr.setVisibility(View.INVISIBLE);
            //EquipActivity.this.tv_qr.setVisibility(View.VISIBLE);
            EquipActivity.this.tv_QR.setVisibility(View.INVISIBLE);
            EquipActivity.this.getWindow().clearFlags(16);
        }
    }

    class C03572 implements OnMenuItemClickListener {
        C03572() {
        }

        public boolean onMenuItemClick(MenuItem item) {
            if (item.getItemId() == R.id.settings) {
                EquipActivity.this.mostrarConfig();
            }
            return false;
        }
    }
}
