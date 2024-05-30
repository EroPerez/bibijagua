package com.molanco.bibijagua;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import Adaptadores.AdaptadorMenu;
import Modelo.Opcion;

public class HelpActivity extends AppCompatActivity {
    ArrayList<Opcion> menu = new ArrayList();
    private RecyclerView recv;
    private AdaptadorMenu adaptador;
    private Toolbar toolbar;
    private CollapsingToolbarLayout ct;
    private AlertDialog ad_aux;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.ct = (CollapsingToolbarLayout) findViewById(R.id.ct);
        this.ct.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        this.ct.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);
        this.ct.setTitle(getResources().getString(R.string.ayuda));
        this.ct.setCollapsedTitleTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/Rubik-Bold.ttf"));

        this.menu.add(new Opcion(getString(R.string.prohibidos), R.drawable.icon_pasado));
        this.menu.add(new Opcion(getString(R.string.regulados), R.drawable.productos_prohib));
        this.menu.add(new Opcion(getString(R.string.faq), R.drawable.faq));
        this.menu.add(new Opcion(getString(R.string.acerca_de), R.drawable.icon_acerca_d));

        this.recv = (RecyclerView) findViewById(R.id.recv);
        this.recv.setLayoutManager(new LinearLayoutManager(this));
        this.recv.setHasFixedSize(true);
        this.adaptador = new AdaptadorMenu(this, this.menu);
        this.adaptador.setOnClickListener(new C02476());

        this.recv.setAdapter(this.adaptador);

    }

    public void irAtras(View v) {
        finish();
    }


    class C02476 implements View.OnClickListener {
        C02476() {
        }

        public void onClick(View v) {
            int option = HelpActivity.this.recv.getChildAdapterPosition(v);

            Intent it;

            switch (option) {

                case 0:

                    it = new Intent(HelpActivity.this, NavActivity.class);
                    it.putExtra("titulo", getString(R.string.prohibidos));

                    HelpActivity.this.startActivity(it);
                    HelpActivity.this.overridePendingTransition(R.anim.trans_entrar, R.anim.trans_salir);

                    break;
                case 1:
                    it = new Intent(HelpActivity.this, NavActivity.class);
                    it.putExtra("titulo", getString(R.string.regulados));

                    HelpActivity.this.startActivity(it);

                    HelpActivity.this.overridePendingTransition(R.anim.trans_entrar, R.anim.trans_salir);

                    break;
                case 2:

                    HelpActivity.this.startActivity(new Intent(HelpActivity.this, FaqActivity.class));
                    HelpActivity.this.overridePendingTransition(R.anim.trans_entrar, R.anim.trans_salir);

                    break;

                case 3:
                    if ((HelpActivity.this.ad_aux != null && !HelpActivity.this.ad_aux.isShowing()) || HelpActivity.this.ad_aux == null) {
                        HelpActivity.this.mostrarAcercaD();

                    }
                    break;
                default:
                    break;
            }
        }
    }

    public void mostrarAcercaD() {
        this.ad_aux = null;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view =getLayoutInflater().inflate(R.layout.activity_acercad, null);
        ((TextView) view.findViewById(R.id.tvappname)).setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/Rage-Italic-Regular.ttf"));
        ((TextView) view.findViewById(R.id.tvattabeira)).setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/Rage-Italic-Regular.ttf"));

        builder.setView(view);
        this.ad_aux = builder.create();
        this.ad_aux.show();
    }
}
