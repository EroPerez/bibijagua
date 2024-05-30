package com.molanco.bibijagua;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView.OnNavigationItemSelectedListener;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import Adaptadores.AdaptadorMenu;
import Adaptadores.AdaptadorProd;
import Modelo.Ajuste;
import Modelo.Capitulo;
import Modelo.Foto;
import Modelo.Opcion;
import Modelo.Producto;
import SingletonBD.Singleton;

public class ProductActivity extends AppCompatActivity implements OnNavigationItemSelectedListener {
    private AdaptadorProd adaptador;
    private Ajuste ajuste = null;
    private Capitulo capitulo = null;
    CollapsingToolbarLayout ct;
    private TextView descripcion;
    ImageView imagen_titul;
    private ArrayList<Producto> list_productos = new ArrayList();

    private RecyclerView recv;

    Toolbar toolbar;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_activity_prod_sinm);
        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.ct = (CollapsingToolbarLayout) findViewById(R.id.ct);
        this.ct.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        this.ct.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);
        this.ct.setCollapsedTitleTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/Rubik-Bold.ttf"));
        this.imagen_titul = (ImageView) findViewById(R.id.imagen);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int id_tipo_producto = extras.getInt("id_tipo_producto");
            String filtro = extras.getString("filtro");
            String articulo = extras.getString("articulos");
            String imagen = extras.getString("imagen");
            this.ct.setTitle(articulo);
            Foto foto = Singleton.getInstance().buscarFoto(imagen);
            Options options = new Options();
            options.inSampleSize = 3;
            this.imagen_titul.setImageBitmap(BitmapFactory.decodeResource(getApplicationContext().getResources(), foto.getId(), options));
            this.capitulo = Singleton.getInstance().getBd().consultarCapID(id_tipo_producto);
            this.ajuste = Singleton.getInstance().getBd().consultAjustes();
            this.list_productos = Singleton.getInstance().getBd().consultarProd(id_tipo_producto, filtro);
            if (this.capitulo != null && this.ajuste != null && this.ajuste.getMetodo().equals("Valor/Peso") && Singleton.getInstance().getBd().existMisc(id_tipo_producto)) {
                mostrarInform();
            }
        }
        this.descripcion = (TextView) findViewById(R.id.descID);
    }

    public boolean onNavigationItemSelected(MenuItem item) {

        ((DrawerLayout) findViewById(R.id.drawer_layout)).closeDrawer((int) GravityCompat.START);
        return true;
    }

    protected void onPostResume() {
        super.onPostResume();
        this.recv = (RecyclerView) findViewById(R.id.recv);
        this.recv.setLayoutManager(new LinearLayoutManager(this));
        if (!this.list_productos.isEmpty()) {
            this.adaptador = new AdaptadorProd(this, this.list_productos, false);
            if (this.capitulo != null) {
                this.adaptador.setCapitulo(this.capitulo);
            }
            if (this.ajuste != null) {
                this.adaptador.setAjuste(this.ajuste);
            }
            this.recv.setAdapter(this.adaptador);
            Singleton.getInstance().setContext(this);
        }
    }

    public void irAtras(View v) {
        finish();
    }

    private void mostrarInform() {
        Builder builder = new Builder(this);
        View v = getLayoutInflater().inflate(R.layout.informacion, null);
        ((TextView) v.findViewById(R.id.infoID)).setText(getResources().getString(R.string.info_no_misc));
        builder.setView(v);
        AlertDialog ad = builder.create();
        ad.setCancelable(true);
        ad.show();
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        this.recv.getLayoutParams().height = this.recv.getHeight() + 90;
        this.recv.setLayoutParams(this.recv.getLayoutParams());
    }
}
