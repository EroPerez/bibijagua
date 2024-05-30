package com.molanco.bibijagua;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import Adaptadores.Adaptador;
import Adaptadores.AdaptadorMenu;
import Modelo.Capitulo;
import SingletonBD.Singleton;

public class NavActivity extends AppCompatActivity {

    private Adaptador adaptador_proh = null;
    private Adaptador adaptador_reg = null;

    private ArrayList<Capitulo> list_capitulos_proh = new ArrayList();
    private ArrayList<Capitulo> list_capitulos_reg = new ArrayList();

    private RecyclerView recv;
    private TextView titul;
    private TextView tv_no_exist;
    private TextView tv_ruta;
    private String titulo;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);


        drawer.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        this.titul = (TextView) findViewById(R.id.titul);
        this.titul.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/Rubik-Bold.ttf"));
        this.titul.setVisibility(View.VISIBLE);


        this.tv_no_exist = (TextView) findViewById(R.id.tvNoExist);
        this.tv_no_exist.setVisibility(View.INVISIBLE);
        this.tv_ruta = (TextView) findViewById(R.id.tvRuta);
        this.tv_ruta.setVisibility(View.INVISIBLE);

        this.recv = (RecyclerView) findViewById(R.id.recv);
        this.recv.setHasFixedSize(true);
        this.recv.setLayoutManager(new LinearLayoutManager(this));

        Bundle extras = this.getIntent().getExtras();

        if (extras != null) {

            this.titulo = extras.getString("titulo");
            this.titul.setText(this.titulo);

            if (this.titulo.equals(getString(R.string.prohibidos))) {
                this.list_capitulos_proh = Singleton.getInstance().getBd().consultarCap(-1, 1);

                if (!this.list_capitulos_proh.isEmpty()) {
                    this.adaptador_proh = new Adaptador(this, this.list_capitulos_proh);
                    this.adaptador_proh.setOnClickListener(new C02454());
                    this.recv.setAdapter(this.adaptador_proh);
                }
            } else {
                if (this.titulo.equals(getString(R.string.regulados))) {
                    this.list_capitulos_reg = Singleton.getInstance().getBd().consultarCap(-1, 2);
                    if (!this.list_capitulos_reg.isEmpty()) {
                        this.adaptador_reg = new Adaptador(this, this.list_capitulos_reg);
                        this.adaptador_reg.setOnClickListener(new C02465());
                        this.recv.setAdapter(this.adaptador_reg);
                    }

                }
            }


        }


    }

    public void irAtras(View v) {
        finish();
    }

    class C02454 implements View.OnClickListener {
        C02454() {
        }

        public void onClick(View v) {

                int id_tipo_producto = NavActivity.this.adaptador_proh.getDatos().get(NavActivity.this.recv.getChildLayoutPosition(v)).getId_tipo_producto();
                String articulo = NavActivity.this.adaptador_proh.getDatos().get(NavActivity.this.recv.getChildLayoutPosition(v)).getDescripcion();
                String imagen = NavActivity.this.adaptador_proh.getDatos().get(NavActivity.this.recv.getChildLayoutPosition(v)).getImagen();

                Intent intent = new Intent(NavActivity.this, RegulActivity.class);
                intent.putExtra("id_tipo_producto", id_tipo_producto);
                intent.putExtra("titulo", "Prohibidos");
                intent.putExtra("articulos", articulo);
                intent.putExtra("imagen", imagen);

                NavActivity.this.startActivity(intent);

        }
    }

    class C02465 implements View.OnClickListener {
        C02465() {
        }

        public void onClick(View v) {

                int id_tipo_producto = NavActivity.this.adaptador_reg.getDatos().get(NavActivity.this.recv.getChildLayoutPosition(v)).getId_tipo_producto();
                String articulo = NavActivity.this.adaptador_reg.getDatos().get(NavActivity.this.recv.getChildLayoutPosition(v)).getDescripcion();
                String imagen = NavActivity.this.adaptador_reg.getDatos().get(NavActivity.this.recv.getChildLayoutPosition(v)).getImagen();

                Intent intent = new Intent(NavActivity.this, RegulActivity.class);
                intent.putExtra("id_tipo_producto", id_tipo_producto);
                intent.putExtra("titulo", "Regulados");
                intent.putExtra("articulos", articulo);
                intent.putExtra("imagen", imagen);

                NavActivity.this.startActivity(intent);

        }
    }
}
