package com.molanco.bibijagua;

import Adaptadores.AdaptadorFaq;
import Modelo.Pregunta;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class FaqActivity extends AppCompatActivity {
    private AdaptadorFaq adaptador;
    private ArrayList<Pregunta> list_preguntas = new ArrayList();
    private RecyclerView recv;
    private TextView titul;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__faq);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        this.titul = (TextView) findViewById(R.id.title);
        this.titul.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/Rubik-Bold.ttf"));
        this.titul.setText(getString(R.string.faq));
        setSupportActionBar(toolbar);
        this.recv = (RecyclerView) findViewById(R.id.recv);
        this.recv.setLayoutManager(new LinearLayoutManager(this));
        this.list_preguntas.add(new Pregunta("¿Cuáles son los artículos exentos del pago?", getResources().getString(R.string.P1)));
        this.list_preguntas.add(new Pregunta("¿En qué consiste el método Valor/Peso?", getResources().getString(R.string.P2)));
        this.list_preguntas.add(new Pregunta("¿Qué son las Misceláneas?", getResources().getString(R.string.P3)));
        this.list_preguntas.add(new Pregunta("¿Qué se entiende por efectos personales?", getResources().getString(R.string.P4)));
        this.list_preguntas.add(new Pregunta("¿Qué se entiende por Carácter Comercial y Carácter No Comercial?", getResources().getString(R.string.P5)));
        this.list_preguntas.add(new Pregunta("¿Puedo importar medicamentos?", getResources().getString(R.string.P6)));
        this.list_preguntas.add(new Pregunta("¿Puedo importar Alimentos?", getResources().getString(R.string.P7)));
        this.list_preguntas.add(new Pregunta("¿Cuándo debo llenar la declaración de aduanas a la entrada al país?", getResources().getString(R.string.P8)));
        this.list_preguntas.add(new Pregunta("¿Cuáles artículos debo declarar?", getResources().getString(R.string.P9)));
        this.list_preguntas.add(new Pregunta("¿Cuál es el límite de lo que puedo importar?", getResources().getString(R.string.P10)));
        this.list_preguntas.add(new Pregunta("¿Qué sucede si importo artículos por más de $1,000.00 ?", getResources().getString(R.string.P11)));
        this.list_preguntas.add(new Pregunta("¿Cuánto es el importe o arancel que se aplica a los artículos que no clasifican como efectos personales?", getResources().getString(R.string.P12)));
        this.list_preguntas.add(new Pregunta("¿En qué moneda se pagan los derechos de aduanas?", getResources().getString(R.string.P13)));
        this.list_preguntas.add(new Pregunta("¿Los menores de edad pueden realizar importaciones de artículos a Cuba en su condición de pasajeros?", getResources().getString(R.string.P14)));
        this.list_preguntas.add(new Pregunta("¿Puedo llevar conmigo animales de compañía?", getResources().getString(R.string.P15)));
        this.list_preguntas.add(new Pregunta("¿Un turista puede importar artículos que no clasifiquen como efectos personales?", getResources().getString(R.string.P16)));
        this.list_preguntas.add(new Pregunta("¿Qué cantidades de tabaco torcido puede llevar un viajero a su salida del país?", getResources().getString(R.string.P17)));
        this.adaptador = new AdaptadorFaq(this, this.list_preguntas);
        this.recv.setAdapter(this.adaptador);
    }

    public void irAtras(View v) {
        finish();
    }
}
