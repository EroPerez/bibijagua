package com.molanco.bibijagua;

import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import Modelo.Foto;
import SingletonBD.Singleton;

public class RegulActivity extends AppCompatActivity {
    CollapsingToolbarLayout ct;
    TextView descripcion;
    ImageView imagen_titul;
    NestedScrollView nsv;
    String texto;
    TextView titulo;
    Toolbar toolbar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__regul);
        this.imagen_titul = (ImageView) findViewById(R.id.imagen);
        this.nsv = (NestedScrollView) findViewById(R.id.nscrollv);
        this.ct = (CollapsingToolbarLayout) findViewById(R.id.ct);
        this.ct.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        this.ct.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);
        this.ct.setCollapsedTitleTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/Rubik-Bold.ttf"));
        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.descripcion = (TextView) findViewById(R.id.descID);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int id_tipo_producto = extras.getInt("id_tipo_producto");
            String articulo = extras.getString("articulos");
            String imagen = extras.getString("imagen");
            this.texto = Singleton.getInstance().getBd().consultarDescProdProh(id_tipo_producto);
            this.ct.setTitle(articulo);
            Foto foto = Singleton.getInstance().buscarFoto(imagen);
            Options options = new Options();
            options.inSampleSize = 3;
            this.imagen_titul.setImageBitmap(BitmapFactory.decodeResource(getApplicationContext().getResources(), foto.getId(), options));
            this.descripcion.setText(this.texto);
            this.descripcion.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/Rubik-Bold.ttf"));
        }
    }

    public void irAtras(View v) {
        finish();
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        this.descripcion.getLayoutParams().height = this.descripcion.getHeight() + 90;
        this.descripcion.setLayoutParams(this.descripcion.getLayoutParams());
    }
}
