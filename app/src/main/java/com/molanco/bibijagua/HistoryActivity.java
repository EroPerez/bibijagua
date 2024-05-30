package com.molanco.bibijagua;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import Adaptadores.AdaptadorDiario;
import Modelo.Historial;
import SingletonBD.Singleton;

public class HistoryActivity extends AppCompatActivity {

    private RecyclerView recv;
    private AdaptadorDiario adaptador;
    private ArrayList<Historial> list_historial;
    private Toolbar toolbar;
    private CollapsingToolbarLayout ct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.ct = (CollapsingToolbarLayout) findViewById(R.id.ct);
        this.ct.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        this.ct.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);
        this.ct.setTitle(getResources().getString(R.string.diario_de_negocio));
        this.ct.setCollapsedTitleTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/Rubik-Bold.ttf"));

        this.recv = (RecyclerView) findViewById(R.id.recv);
        this.recv.setLayoutManager(new LinearLayoutManager(this));
        this.recv.setHasFixedSize(true);
        this.list_historial = Singleton.getInstance().getBd().consultarHistorial();

        if (!this.list_historial.isEmpty()) {
            this.adaptador = new AdaptadorDiario(this, this.list_historial);

            this.recv.setAdapter(this.adaptador);

            this.adaptador.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {

                    int IDHistorial = HistoryActivity.this.adaptador.getDatos().get(HistoryActivity.this.recv.getChildLayoutPosition(v)).getId();

                    Intent intent = new Intent(HistoryActivity.this, EquipActivity.class);

                    intent.putExtra("id_historial", IDHistorial);

                    HistoryActivity.this.startActivity(intent);
                }

            });
        }


    }

    public void irAtras(View v) {
        finish();
    }

    public void clear(View v) {

        if (this.adaptador.items2Delete.isEmpty()) {

            if (!this.list_historial.isEmpty()) {
                Singleton.getInstance().getBd().clearHistorial();
                this.adaptador.getDatos().clear();
                this.adaptador.notifyDataSetChanged();
            } else
                Toast.makeText(this, "Diario de viaje esta vacío.", Toast.LENGTH_SHORT).show();
        } else {

            Singleton.getInstance().getBd().deleteHistorial(this.adaptador.items2Delete.toArray());
            for (Object id : this.adaptador.items2Delete
                    ) {
                this.adaptador.getDatos().remove((Historial) id);
                this.adaptador.notifyDataSetChanged();
            }
            this.adaptador.items2Delete.clear();

        }
    }
}
