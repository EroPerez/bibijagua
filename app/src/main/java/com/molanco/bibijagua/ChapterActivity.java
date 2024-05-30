package com.molanco.bibijagua;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
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
import java.util.List;

import Adaptadores.Adaptador;
import Modelo.Ajuste;
import Modelo.Capitulo;
import Modelo.AjusteEquipaje;
import Modelo.Equipaje;
import Modelo.Historial;
import Modelo.Producto;
import SingletonBD.Singleton;


public class ChapterActivity extends AppCompatActivity {


    private AlertDialog ad_aux = null;
    private int flag = 0;
    private int flag_menu = 0;

    private RadioGroup radioG;
    private RadioGroup radioGC;
    private RelativeLayout rell;
    private RelativeLayout rell2;
    private RecyclerView revND;
    private Spinner spin;
    private String string_peso = "0.0 kg";
    private String string_valor = "$ 0.00 ";
    private TextView valor;
    private Adaptador adaptador = null;
    private Ajuste ajuste = null;
    private ImageButton b_buscar;
    private int buscar = 0;
    private String categoria = "PV";
    private EditText edit;
    private List<Capitulo> filterCap = new ArrayList();
    private int importacion = 1;

    private ArrayList<Capitulo> list_capitulos = new ArrayList();

    private String metodo = "Valor/Peso";
    private String moneda = "cup";
    private RecyclerView recv;

    private TextView tv_no_exist;
    private TextView tv_ruta;
    private TextView peso;
    private float v_peso = 0.0f;
    private float v_valor = 0.0f;

    private LinearLayout clic;
    private ImageView icon_pas;
    private ProgressBar pb;
    private ProgressBar pb_qr;
    private TextView tv_QR;
    private TextView tv_qr;
    private TextView peso_aj;
    private ImageButton qr;
    private TextView val_aduana;
    private TextView val_derecho;
    private TextView val_servicio;
    private TextView val_total;
    private TextView tip_moneda;
    private TextView valor_aj;
    private float value = 0.0f;
    private AsyncTaskCargaDatos ATCargaDatos;
    private DatePicker fecha_viaje;
    private EditText desc_viaje;

    private TextView direccion;

    public void irAtras(View v) {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capitulos);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // toolbar.inflateMenu(R.menu.configuracion);
        // toolbar.setOnMenuItemClickListener(new C03572());
        setSupportActionBar(toolbar);

        this.revND = (RecyclerView) findViewById(R.id.recvND);
        this.tv_no_exist = (TextView) findViewById(R.id.tvNoExist);
        this.tv_no_exist.setVisibility(View.INVISIBLE);
        this.tv_ruta = (TextView) findViewById(R.id.tvRuta);
        this.tv_ruta.setVisibility(View.INVISIBLE);
        this.edit = (EditText) findViewById(R.id.editT);
        this.edit.setVisibility(View.INVISIBLE);
        this.b_buscar = (ImageButton) findViewById(R.id.botonBuscar);
        this.b_buscar.setOnClickListener(new C02431());
        this.recv = (RecyclerView) findViewById(R.id.recv);
        this.recv.setHasFixedSize(true);

        this.direccion = (TextView) findViewById(R.id.direccion);
        this.direccion.setText(getResources().getString(R.string.direccionF));

//        final GridLayoutManager gl_mang = new GridLayoutManager(this, 2);
//        gl_mang.setSpanSizeLookup(new SpanSizeLookup() {
//            public int getSpanSize(int position) {
//                switch (ChapterActivity.this.adaptador.getItemViewType(position)) {
//                    case 1:
//                        return gl_mang.getSpanCount();
//                    case 2:
//                        return 1;
//                    default:
//                        return -1;
//                }
//            }
//        });
        this.recv.setLayoutManager(new LinearLayoutManager(this));

        if (Singleton.getInstance().getBd() != null) {

            this.ajuste = Singleton.getInstance().getBd().consultAjustes();
            if (this.ajuste != null /*&& this.ajuste.getMetodo().equals("Valor")*/) {

                this.list_capitulos = Singleton.getInstance().getBd().consultarCap(-1, 0);

            }
            if (!this.list_capitulos.isEmpty()) {
                this.adaptador = new Adaptador(this, this.list_capitulos);
                this.recv.setAdapter(this.adaptador);

                this.adaptador.setOnClickListener(new C02443());
            }

        } else {
            this.tv_no_exist.setVisibility(View.VISIBLE);
            this.tv_no_exist.setText("No existe la BD en la ruta: ");
            this.tv_ruta.setVisibility(View.VISIBLE);
            this.tv_ruta.setText(getResources().getString(R.string.folder_name));
        }


        this.peso = (TextView) findViewById(R.id.pesoID);
        this.clic = (LinearLayout) findViewById(R.id.clicpeso);
        this.tv_QR = (TextView) findViewById(R.id.tvQR);
        this.tv_qr = (TextView) findViewById(R.id.tvqr);
        this.tv_qr.setVisibility(View.VISIBLE);
        this.tv_QR.setVisibility(View.INVISIBLE);
        this.qr = (ImageButton) findViewById(R.id.codigQR);
        this.qr.setVisibility(View.VISIBLE);

        this.val_aduana = (TextView) findViewById(R.id.aduanaID);
        this.val_derecho = (TextView) findViewById(R.id.derechosID);
        this.val_servicio = (TextView) findViewById(R.id.serviciosID);
        this.val_total = (TextView) findViewById(R.id.totalID);
        this.tip_moneda = (TextView) findViewById(R.id.moneda);

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

        this.value = 0.0f;

        if (this.ajuste != null) {
            this.peso.setText(Float.toString(this.ajuste.getPeso()));
            this.clic.setOnClickListener(new C02341());
            cargarProductosEquip();
        }


    }

    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    private void desactivarBusqueda() {
        this.edit.setText("");
        this.b_buscar.setImageDrawable(getResources().getDrawable(R.drawable.icons8_search));
        this.buscar = 0;
        this.edit.setVisibility(View.INVISIBLE);
//        this.titul.setVisibility(0);
    }

    private void activarBusqueda() {
        this.b_buscar.setImageDrawable(getResources().getDrawable(R.drawable.icons8_delete));
        this.buscar = 1;
        //this.titul.setVisibility(4);
        this.edit.setVisibility(View.VISIBLE);
        filtrarDatos();
    }

    private void updateRecView(CharSequence c) {
        if (Singleton.getInstance().getBd() != null) {
            this.filterCap.clear();
            this.filterCap = Singleton.getInstance().getBd().filtrarCap(c, 0);
            this.recv.setLayoutManager(new LinearLayoutManager(this));
            this.adaptador = new Adaptador(this, this.filterCap);
            this.adaptador.setOnClickListener(new C02487());
            this.recv.setAdapter(this.adaptador);
            if (this.filterCap.isEmpty()) {
                this.filterCap.clear();
                this.tv_no_exist.setVisibility(View.VISIBLE);
                this.tv_no_exist.setText("No se encontraron coincidencias.");
                return;
            }
            this.tv_no_exist.setVisibility(View.INVISIBLE);
        }
    }

    public void filtrarDatos() {
        this.edit.addTextChangedListener(new C02498());
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
                ChapterActivity.this.categoria = adaptador_categ.getItem(position);
                if (/*position == 0 ||*/ position == 1) {
                    ChapterActivity.this.radioGC.setVisibility(View.VISIBLE);
                    if (ChapterActivity.this.importacion == 2) {
                        ChapterActivity.this.radioGC.check(R.id.segundaID);
                        ChapterActivity.this.moneda = "cuc";
                    } else
                        ChapterActivity.this.moneda = "cup";

                    ChapterActivity.this.radioGC.setOnCheckedChangeListener(new C02361());
                    return;
                }
                ChapterActivity.this.radioGC.setVisibility(View.INVISIBLE);
                ChapterActivity.this.moneda = "cuc";
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }

            class C02361 implements OnCheckedChangeListener {
                C02361() {
                }

                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (checkedId == R.id.primeraID) {
                        ChapterActivity.this.importacion = 1;
                    }
                    if (checkedId == R.id.segundaID) {
                        ChapterActivity.this.importacion = 2;
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
                    if (ChapterActivity.this.metodo.equals("Valor/Peso")) {
                        Singleton.getInstance().getBd().deleteMiscEquipaje();
                    }
                    Singleton.getInstance().getBd().updateAjuste(ChapterActivity.this.categoria, ChapterActivity.this.importacion, ChapterActivity.this.v_peso, ChapterActivity.this.v_valor, ChapterActivity.this.moneda, ChapterActivity.this.metodo);
                    ChapterActivity.this.ajuste = Singleton.getInstance().getBd().consultAjustes();
                    ChapterActivity.this.value = 0.00f;
                    ChapterActivity.this.peso.setText(Float.toString(ChapterActivity.this.ajuste.getPeso()));
                    ChapterActivity.this.cargarProductosEquip();
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
                        ChapterActivity.this.v_peso = Float.valueOf(npE.getValue() + ".0").floatValue();
                        if (config == 1) {
                            ChapterActivity.this.peso_aj.setText(npE.getValue() + ".0" + " kg ");
                        } else {
                            float aux = ChapterActivity.this.ajuste.getPeso();

                            Singleton.getInstance().getBd().updateAjuste(ChapterActivity.this.categoria, ChapterActivity.this.importacion, ChapterActivity.this.v_peso, ChapterActivity.this.v_valor, ChapterActivity.this.moneda, ChapterActivity.this.metodo);
                            ChapterActivity.this.ajuste = Singleton.getInstance().getBd().consultAjustes();
                            ChapterActivity.this.peso.setText(Float.toString(ChapterActivity.this.ajuste.getPeso()));

                            float rest = 0.0f;
                            if (aux > 25.0f) {
                                rest = (aux - 25.0f) * 10.0f;
                            }
                            if (ChapterActivity.this.ajuste.getPeso() > 25.0f) {
                                ChapterActivity.this.value = (Float.valueOf(ChapterActivity.this.val_aduana.getText().toString()).floatValue() - rest) + ((ChapterActivity.this.ajuste.getPeso() - 25.0f) * 10.0f);
                            } else {
                                ChapterActivity.this.value = Float.valueOf(ChapterActivity.this.val_aduana.getText().toString()).floatValue() - rest;
                            }
                            ChapterActivity.this.val_aduana.setText(Float.toString(ChapterActivity.this.value) + "0");
                            float val_derech = Singleton.calcularValDerech(Float.valueOf(ChapterActivity.this.val_aduana.getText().toString()).floatValue(), ChapterActivity.this.ajuste);
                            ChapterActivity.this.val_derecho.setText(Float.toString(Math.abs(1000.0f - ChapterActivity.this.value)) + "0");
                            ChapterActivity.this.val_servicio.setText(Float.toString(Singleton.calcularValServ(val_derech)) + "0");
                            ChapterActivity.this.val_total.setText("$" + Singleton.calcularValTotal(val_derech, Float.valueOf(ChapterActivity.this.val_servicio.getText().toString()).floatValue()) + "0");
                            if (ChapterActivity.this.val_total.length() >= 7) {
                                ChapterActivity.this.val_total.setTextSize(16.0f);
                                ChapterActivity.this.tip_moneda.setTextSize(13.0f);
                            } else {
                                ChapterActivity.this.val_total.setTextSize(19.0f);
                                ChapterActivity.this.tip_moneda.setTextSize(19.0f);
                            }
                            ChapterActivity.this.val_aduana.setText(Float.toString(ChapterActivity.this.value) + "0");
                            if (ChapterActivity.this.value <= 1000.0f) {
                                ChapterActivity.this.pb.setProgressDrawable(ChapterActivity.this.getResources().getDrawable(R.drawable.bar_progress));
                                ChapterActivity.this.icon_pas.setVisibility(View.INVISIBLE);
                                ChapterActivity.this.val_total.setVisibility(View.VISIBLE);
                                ChapterActivity.this.tip_moneda.setVisibility(View.VISIBLE);
                                ChapterActivity.this.pb.setProgress((int) Singleton.porcientoNumero((double) ChapterActivity.this.value, (double) 1000));

                                ChapterActivity.this.direccion.setText(getResources().getString(R.string.direccionF));
                            } else {
                                ChapterActivity.this.pb.setProgress(100);
                                ChapterActivity.this.pb.setProgressDrawable(ChapterActivity.this.getResources().getDrawable(R.drawable.bar_progress));
                                ChapterActivity.this.icon_pas.setVisibility(View.VISIBLE);
                                ChapterActivity.this.val_total.setVisibility(View.INVISIBLE);
                                ChapterActivity.this.tip_moneda.setVisibility(View.INVISIBLE);

                                ChapterActivity.this.direccion.setText(getResources().getString(R.string.direccionS));
                                Singleton.mostrarInfo(ChapterActivity.this);
                            }
                            ChapterActivity.this.tip_moneda.setText(ChapterActivity.this.ajuste.getMoneda());

                            //Float.valueOf(ChapterActivity.this.val_servicio.getText().toString());

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
                        ChapterActivity.this.valor_aj.setText(" $ " + npE.getValue() + ".0");
                        ChapterActivity.this.v_valor = Float.valueOf(npE.getValue() + ".0").floatValue();
                        tempAlert_dialog.dismiss();
                    }
                }
            });
            this.ad_aux.show();
        }
    }

    private void cargarProductosEquip() {

        if (this.ajuste.getMetodo().equals("Valor/Peso") && this.ajuste.getPeso() > 25.0f) {
            this.value += (this.ajuste.getPeso() - 25.0f) * 10.0f;
        } else if (this.ajuste.getMetodo().equals("Valor") && this.ajuste.getValor() > 0.0f) {
            this.value += this.ajuste.getValor();
        }
        ArrayList<Producto> list_productos = Singleton.getInstance().getBd().consultarProdEquip();
        float val_derech;
        if (list_productos.size() > 0) {
            for (int i = 0; i < list_productos.size(); i++) {
                this.value += (((float) list_productos.get(i).getCant_equp()) * list_productos.get(i).getPrecio());
            }
            val_derech = Singleton.calcularValDerech(this.value, this.ajuste);
            //this.val_derecho.setText(Float.toString(val_derech));
            this.val_derecho.setText(Float.toString(Math.abs(1000.0f - this.value)) + "0");
            this.val_servicio.setText(Float.toString(Singleton.calcularValServ(val_derech)) + "0");
            this.val_total.setText("$" + Singleton.calcularValTotal(val_derech, Float.valueOf(this.val_servicio.getText().toString()).floatValue()) + "0");
            if (this.val_total.length() >= 7) {
                this.val_total.setTextSize(16.0f);
                this.tip_moneda.setTextSize(13.0f);
            } else {
                this.val_total.setTextSize(19.0f);
                this.tip_moneda.setTextSize(19.0f);
            }

        } else {
            val_derech = Singleton.calcularValDerech(this.value, this.ajuste);
            this.val_derecho.setText(Float.toString(Math.abs(1000.0f - this.value)) + "0");

            this.val_servicio.setText(Float.toString(Singleton.calcularValServ(val_derech)) + "0");
            this.val_total.setText("$" + Singleton.calcularValTotal(val_derech, Float.valueOf(this.val_servicio.getText().toString()).floatValue()) + "0");
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
            this.pb.setProgress((int) Singleton.porcientoNumero((double) this.value, (double) 1000));

            ChapterActivity.this.direccion.setText(getResources().getString(R.string.direccionF));
        } else {
            this.pb.setProgress(100);
            this.pb.setProgressDrawable(getResources().getDrawable(R.drawable.vertical_progress_bar));
            this.icon_pas.setVisibility(View.VISIBLE);
            this.val_total.setVisibility(View.INVISIBLE);
            this.tip_moneda.setVisibility(View.INVISIBLE);

            ChapterActivity.this.direccion.setText(getResources().getString(R.string.direccionS));
            Singleton.mostrarInfo(this);
        }
        this.tip_moneda.setText(this.ajuste.getMoneda());
        //Float.valueOf(this.val_servicio.getText().toString());

        //Toast.makeText(this,"Actualizado",Toast.LENGTH_LONG).show();
    }


    public void clear(View v) {

        mostrarConfig();
    }

    public void generarQR(View v) {

        ChapterActivity.this.startActivity(new Intent(ChapterActivity.this, EquipActivity.class));
        ChapterActivity.this.overridePendingTransition(R.anim.trans_entrar, R.anim.trans_salir);
    }

    protected void onDestroy() {
        super.onDestroy();
        if (this.ATCargaDatos != null && !this.ATCargaDatos.isCancelled()) {

            this.ATCargaDatos.cancel(true);
        }
        this.value = 0.0f;
    }

    protected void onPostResume() {
        super.onPostResume();
        if (this.flag == 1) {
            this.flag = 0;
        }
        if (this.flag_menu == 1) {
            this.flag_menu = 0;
        }
        if (this.ajuste != null) {
            this.updateCalcImpuestos();
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.configuracion, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.settings) {
            mostrarConfig();
        } else {
            if (item.getItemId() == R.id.baggage) {
                ChapterActivity.this.startActivity(new Intent(ChapterActivity.this, EquipActivity.class));
                ChapterActivity.this.overridePendingTransition(R.anim.trans_entrar, R.anim.trans_salir);

            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void SalvarHistorial(final String JSonEquipaje) {
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

                    //save here
                    try {

                        Calendar calendar = Calendar.getInstance();
                        calendar.set(fecha_viaje.getYear(), fecha_viaje.getMonth(), fecha_viaje.getDayOfMonth());
                        final String desc = desc_viaje.getText().toString();

                        Historial histoViaje = new Historial(calendar.getTime(), desc, JSonEquipaje, null);

                        if (Singleton.getInstance().getBd().insertHistorial(histoViaje) != -1) {
                            Toast.makeText(ChapterActivity.this.getApplicationContext(), getResources().getString(R.string.msg_historial), 1).show();
                        }

                    } catch (Exception e) {

                        Toast.makeText(ChapterActivity.this.getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                //Toast.makeText(ChapterActivity.this.getApplicationContext(), getResources().getString(R.string.msg_historial), 1).show();
                tempAlert_dialog.dismiss();
            }
        });
        ad.show();
    }

    private void updateCalcImpuestos() {
        ChapterActivity.this.ajuste = Singleton.getInstance().getBd().consultAjustes();
        ChapterActivity.this.value = 0.0f;
        ChapterActivity.this.peso.setText(Float.toString(ChapterActivity.this.ajuste.getPeso()));
        ChapterActivity.this.cargarProductosEquip();

    }

    class C02431 implements OnClickListener {
        C02431() {
        }

        public void onClick(View v) {
            if (ChapterActivity.this.buscar == 1) {
                ChapterActivity.this.desactivarBusqueda();
            } else {
                ChapterActivity.this.activarBusqueda();
            }
        }
    }

    class C02443 implements OnClickListener {
        C02443() {
        }

        public void onClick(View v) {
            if (ChapterActivity.this.flag == 0) {
                ChapterActivity.this.flag = 1;
                int id_tipo_producto = ChapterActivity.this.adaptador.getDatos().get(ChapterActivity.this.recv.getChildLayoutPosition(v)).getId_tipo_producto();
                String articulo = ChapterActivity.this.adaptador.getDatos().get(ChapterActivity.this.recv.getChildLayoutPosition(v)).getDescripcion();
                String imagen = ChapterActivity.this.adaptador.getDatos().get(ChapterActivity.this.recv.getChildLayoutPosition(v)).getImagen();
                Intent intent = new Intent(ChapterActivity.this, ProductActivity.class);
                intent.putExtra("id_tipo_producto", id_tipo_producto);
                intent.putExtra("articulos", articulo);
                intent.putExtra("imagen", imagen);
                intent.putExtra("filtro", ChapterActivity.this.edit.getText().toString());
                ChapterActivity.this.startActivity(intent);
            }
        }
    }

    class C02487 implements OnClickListener {
        C02487() {
        }

        public void onClick(View v) {
            if (ChapterActivity.this.flag == 0) {
                ChapterActivity.this.flag = 1;
                int id_tipo_producto = ((Capitulo) ChapterActivity.this.adaptador.getDatos().get(ChapterActivity.this.recv.getChildLayoutPosition(v))).getId_tipo_producto();
                String articulo = ((Capitulo) ChapterActivity.this.adaptador.getDatos().get(ChapterActivity.this.recv.getChildLayoutPosition(v))).getDescripcion();
                String imagen = ((Capitulo) ChapterActivity.this.adaptador.getDatos().get(ChapterActivity.this.recv.getChildLayoutPosition(v))).getImagen();
                Intent intent = new Intent(ChapterActivity.this, ProductActivity.class);
                intent.putExtra("id_tipo_producto", id_tipo_producto);
                intent.putExtra("articulos", articulo);
                intent.putExtra("imagen", imagen);
                intent.putExtra("filtro", ChapterActivity.this.edit.getText().toString());
                ChapterActivity.this.startActivity(intent);
            }
        }
    }

    class C02498 implements TextWatcher {
        C02498() {
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            ChapterActivity.this.updateRecView(s);
        }

        public void afterTextChanged(Editable s) {
        }
    }

    class C02396 implements OnClickListener {
        C02396() {
        }

        public void onClick(View v) {
            ChapterActivity.this.mostrarSeleccPeso(1);
        }
    }

    class C02341 implements OnClickListener {
        C02341() {
        }

        public void onClick(View v) {
            ChapterActivity.this.metodo = ChapterActivity.this.ajuste.getMetodo();
            if (ChapterActivity.this.metodo.equals("Valor/Peso")) {
                ChapterActivity.this.mostrarSeleccPeso(0);
            } else {
                Toast.makeText(ChapterActivity.this.getApplicationContext(), "No se puede insertar el peso. Cambie al método Valor/Peso en Ajustes.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    class C02385 implements OnCheckedChangeListener {
        C02385() {
        }

        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.pesoVID) {
                ChapterActivity.this.metodo = "Valor/Peso";
                ChapterActivity.this.v_valor = 0.00f;
                ChapterActivity.this.valor_aj.setText("$ 0.00");
                ChapterActivity.this.rell.setVisibility(View.VISIBLE);
                ChapterActivity.this.rell2.setVisibility(View.INVISIBLE);

            } else {
                ChapterActivity.this.metodo = "Valor";
                ChapterActivity.this.v_peso = 0.00f;
                ChapterActivity.this.peso_aj.setText("0.0 kg");
                ChapterActivity.this.rell.setVisibility(View.INVISIBLE);
                ChapterActivity.this.rell2.setVisibility(View.VISIBLE);
            }
        }
    }

    class C02407 implements OnClickListener {
        C02407() {
        }

        public void onClick(View v) {
            ChapterActivity.this.mostrarSeleccValor();
        }
    }

    class C03572 implements Toolbar.OnMenuItemClickListener {
        C03572() {
        }

        public boolean onMenuItemClick(MenuItem item) {
            if (item.getItemId() == R.id.settings) {
                ChapterActivity.this.mostrarConfig();
            }
            return false;
        }
    }

    public class AsyncTaskCargaDatos extends AsyncTask<Void, Integer, Void> {
        AjusteEquipaje datos_hist = null;
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
            this.datos_hist = new AjusteEquipaje(ChapterActivity.this.ajuste, this.list_equip);
            ChapterActivity.this.getWindow().setFlags(16, 16);
        }

        protected Void doInBackground(Void... params) {
            publishProgress(new Integer[]{Integer.valueOf(0)});
            if (this.datos_hist != null) {
                this.json = new Gson().toJson(this.datos_hist);
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
            ChapterActivity.this.pb_qr.setProgress(this.f5i);
        }

        protected void onPostExecute(Void result) {
            ChapterActivity.this.SalvarHistorial(this.json);

            ChapterActivity.this.qr.setVisibility(View.VISIBLE);
            ChapterActivity.this.pb_qr.setProgress(View.VISIBLE);
            ChapterActivity.this.pb_qr.setVisibility(View.INVISIBLE);
            ChapterActivity.this.tv_qr.setVisibility(View.VISIBLE);
            ChapterActivity.this.tv_QR.setVisibility(View.INVISIBLE);
            ChapterActivity.this.getWindow().clearFlags(16);
        }
    }
}
