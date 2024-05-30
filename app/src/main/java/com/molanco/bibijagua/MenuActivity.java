package com.molanco.bibijagua;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import SingletonBD.Singleton;

public class MenuActivity extends AppCompatActivity {

    private TextView diario;
    private TextView calculadora;
    private TextView ayuda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        ((TextView) findViewById(R.id.tvAppname)).setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/Rage-Italic-Regular.ttf"));

        Singleton.getInstance().setContext(getApplication());
        Singleton.getInstance().cargarBD();

        this.diario = (TextView) findViewById(R.id.txtVDiario);
        this.diario.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/Rubik-Bold.ttf"));
        this.diario.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                MenuActivity.this.startActivity(new Intent(MenuActivity.this, HistoryActivity.class));
                MenuActivity.this.overridePendingTransition(R.anim.trans_entrar, R.anim.trans_salir);

            }
        });
        this.calculadora = (TextView) findViewById(R.id.txtVCalc);
        this.calculadora.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/Rubik-Bold.ttf"));
        this.calculadora.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                MenuActivity.this.startActivity(new Intent(MenuActivity.this, SettingActivity.class));
                MenuActivity.this.overridePendingTransition(R.anim.trans_entrar, R.anim.trans_salir);

            }
        });
        this.ayuda = (TextView) findViewById(R.id.txtVAyuda);
        this.ayuda.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/Rubik-Bold.ttf"));
        this.ayuda .setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                MenuActivity.this.startActivity(new Intent(MenuActivity.this, HelpActivity.class));
                MenuActivity.this.overridePendingTransition(R.anim.trans_entrar, R.anim.trans_salir);

            }
        });



    }
}
