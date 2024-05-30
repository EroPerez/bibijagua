package com.molanco.bibijagua;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import Modelo.Ajuste;
import SingletonBD.Singleton;

public class SettingActivity extends AppCompatActivity {

    //Controles
    private AlertDialog alertDialogPV = null;

    private RadioGroup radioG;
    private RadioGroup radioGC;
    private RelativeLayout rell;
    private RelativeLayout rell2;
    private Spinner spin;
    private TextView peso;
    private TextView valor;

    //Variables
    private Ajuste ajuste = null;

    private String categoria = "PV";
    private int importacion = 1;
    private String metodo = "Valor/Peso";
    private String moneda = "cup";
    private String string_peso = "0.0 kg";
    private float v_peso = 0.0f;
    private String string_valor = "$ 0.00";
    private float v_valor = 0.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        ((TextView) findViewById(R.id.tvAppname)).setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/Rage-Italic-Regular.ttf"));

        setSupportActionBar(toolbar);
        mostrarConfig();
    }

    public void mostrarConfig() {

        this.radioGC = (RadioGroup) findViewById(R.id.rgCateg);
        this.radioG = (RadioGroup) findViewById(R.id.radioGID);
        this.peso = (TextView) findViewById(R.id.tvPeso);
        this.valor = (TextView) findViewById(R.id.tvValor);
        this.rell = (RelativeLayout) findViewById(R.id.rellay);
        this.rell2 = (RelativeLayout) findViewById(R.id.rellay2);

        this.spin = (Spinner) findViewById(R.id.spinner_categ);
        String categ[] = getResources().getStringArray(R.array.categoria_viajero);
        final ArrayAdapter<String> adaptador_categ = new ArrayAdapter(this, 17367049, categ);
        this.spin.setAdapter(adaptador_categ);

        this.ajuste = Singleton.getInstance().getBd().consultAjustes();
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
            this.peso.setText(this.string_peso);
            this.valor.setText(this.string_valor);
        }
        this.spin.setOnItemSelectedListener(new OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                SettingActivity.this.categoria = adaptador_categ.getItem(position);
                if (/*position == 0 ||*/ position == 1) {
                    SettingActivity.this.radioGC.setVisibility(View.VISIBLE);
                    if (SettingActivity.this.importacion == 2) {
                        SettingActivity.this.radioGC.check(R.id.segundaID);
                        SettingActivity.this.moneda = "cuc";
                    }else {
                        SettingActivity.this.moneda = "cup";
                    }

                    SettingActivity.this.radioGC.setOnCheckedChangeListener(new C02501());
                }else{
                    SettingActivity.this.radioGC.setVisibility(View.INVISIBLE);
                    SettingActivity.this.moneda = "cuc";
                }
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }

            class C02501 implements OnCheckedChangeListener {
                C02501() {
                }

                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (checkedId == R.id.primeraID) {
                        SettingActivity.this.importacion = 1;
                    }
                    if (checkedId == R.id.segundaID) {
                        SettingActivity.this.importacion = 2;
                    }
                }
            }
        });

        if (this.metodo.equals("Valor")) {
            this.radioG.check(R.id.valorID);
            this.rell.setVisibility(View.INVISIBLE);
            this.rell2.setVisibility(View.VISIBLE);
        }
        this.radioG.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.pesoVID) {
                    SettingActivity.this.metodo = "Valor/Peso";
                    SettingActivity.this.v_valor = 0.0f;
                    SettingActivity.this.valor.setText("$ 0.00");
                    SettingActivity.this.rell.setVisibility(View.VISIBLE);
                    SettingActivity.this.rell2.setVisibility(View.INVISIBLE);

                }else{
                    SettingActivity.this.metodo = "Valor";
                    SettingActivity.this.v_peso = 0.0f;
                    SettingActivity.this.peso.setText("0.0 kg");
                    SettingActivity.this.rell.setVisibility(View.INVISIBLE);
                    SettingActivity.this.rell2.setVisibility(View.VISIBLE);
                }

            }
        });
        this.peso.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                SettingActivity.this.mostrarSeleccPeso();
            }
        });
        this.valor.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                SettingActivity.this.mostrarSeleccValor();
            }
        });
        Button guardar = (Button) findViewById(R.id.guardarID);

        guardar.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {

                if (SettingActivity.this.metodo.equals("Valor/Peso")) {
                    Singleton.getInstance().getBd().deleteMiscEquipaje();
                }
                Singleton.getInstance().getBd().updateAjuste(SettingActivity.this.categoria, SettingActivity.this.importacion, SettingActivity.this.v_peso, SettingActivity.this.v_valor, SettingActivity.this.moneda, SettingActivity.this.metodo);
                Toast.makeText(SettingActivity.this, getString(R.string.ajute_salvados), Toast.LENGTH_SHORT).show();

                SettingActivity.this.finish();
                SettingActivity.this.startActivity(new Intent(SettingActivity.this, ChapterActivity.class));
                SettingActivity.this.overridePendingTransition(R.anim.trans_entrar, R.anim.trans_salir);
            }
        });
    }

    private void mostrarSeleccPeso() {
        if ((this.alertDialogPV != null && !this.alertDialogPV.isShowing()) || this.alertDialogPV == null) {
            this.alertDialogPV = null;
            Builder builder = new Builder(this);
            View v = getLayoutInflater().inflate(R.layout.selec_peso, null);
            final NumberPicker npE = (NumberPicker) v.findViewById(R.id.npEntero);
            npE.setMinValue(0);
            npE.setMaxValue(125);
            if (this.v_peso > 0.0f) {
                npE.setValue((int) this.v_peso);
            }
            Button aceptar = (Button) v.findViewById(R.id.aceptar);
            builder.setView(v);
            this.alertDialogPV = builder.create();
            final AlertDialog tempAlert_dialog = this.alertDialogPV;
            aceptar.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    if (tempAlert_dialog.isShowing()) {
                        SettingActivity.this.peso.setText(npE.getValue() + ".0" + " kg ");
                        SettingActivity.this.v_peso = Float.valueOf(npE.getValue() + ".00").floatValue();
                        tempAlert_dialog.dismiss();
                    }
                }
            });
            this.alertDialogPV.show();
        }
    }

    private void mostrarSeleccValor() {
        if ((this.alertDialogPV != null && !this.alertDialogPV.isShowing()) || this.alertDialogPV == null) {
            this.alertDialogPV = null;
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
            this.alertDialogPV = builder.create();
            final AlertDialog tempAlert_dialog = this.alertDialogPV;
            aceptar.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    if (tempAlert_dialog.isShowing()) {
                        SettingActivity.this.valor.setText(" $ " + npE.getValue() + ".00");
                        SettingActivity.this.v_valor = Float.valueOf(npE.getValue() + ".00").floatValue();
                        tempAlert_dialog.dismiss();
                    }
                }
            });
            this.alertDialogPV.show();
        }
    }
}
