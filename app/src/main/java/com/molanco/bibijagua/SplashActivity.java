package com.molanco.bibijagua;

import android.app.Application;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.faraji.environment3.Environment3;

import Adaptadores.Adaptador;
import SingletonBD.Singleton;
import util.Utils;

import static java.lang.Thread.sleep;

public class SplashActivity extends AppCompatActivity {
    private Animation anim_imag;
    private Animation anim_titul;
    private ImageView imagen;
    private TextView titulo;
    private String path;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        if (Build.VERSION.SDK_INT >= 23 && !checkPermission()) {
            requestPermission();
        } else {
            try {
                AsyncJob job = new AsyncJob();
                job.execute(getApplication());
            } catch (Exception e) {
                e.printStackTrace();
            }

        }


        this.imagen = (ImageView) findViewById(R.id.imagenID);
        this.titulo = (TextView) findViewById(R.id.titulo_bienv);
        this.titulo.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/Rage-Italic-Regular.ttf"));
        this.anim_imag = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.escalar_imag);
        this.anim_titul = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.trans_titulo);


        this.anim_titul.setAnimationListener(new C02521());
        this.imagen.startAnimation(this.anim_imag);
        this.titulo.startAnimation(this.anim_titul);

    }

    private boolean checkPermission() {
        if (ContextCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE") == 0) {
            return true;
        }
        return false;
    }

    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, "android.permission.WRITE_EXTERNAL_STORAGE")) {
            Toast.makeText(this, "Write External Storage permission allows us to do store images. Please allow this permission in App Settings.", 1).show();
            return;
        }
        ActivityCompat.requestPermissions(this, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, 1);
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length <= 0 || grantResults[0] != 0) {
                    Toast.makeText(this, "Permission Denied, You cannot use local drive .", 0).show();
                    return;
                } else {
                    Toast.makeText(this, "Permission Granted, Now you can use local drive .", 0).show();
                    //Load db first time
                    try {
                        AsyncJob job = new AsyncJob();
                        job.execute(getApplication());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return;
                }
            default:
                return;
        }
    }

    private class C02521 implements AnimationListener {
        C02521() {
        }

        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            //Display for 3 seconds
            try {
                sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            SplashActivity.this.finish();
            SplashActivity.this.startActivity(new Intent(SplashActivity.this, MenuActivity.class));
            SplashActivity.this.overridePendingTransition(R.anim.trans_entrar, R.anim.trans_salir);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
        }
    }

    private class AsyncJob extends AsyncTask<Application, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Application... params) {

            SplashActivity.this.path = Environment3.getCardDirectory().getPath();


            SplashActivity.this.path = SplashActivity.this.path + "/" + getResources().getString(R.string.folder_name);

            Log.e("Bibijagua", SplashActivity.this.path);

            if (!Utils.fileExist("aduanadb.sqlite", SplashActivity.this.path)) {
                Utils.copyFileToSdcard(params[0], R.raw.aduanadb, "aduanadb.sqlite", SplashActivity.this.path);
            }


            return true;
        }

        @Override
        protected void onProgressUpdate(Void... values) {

        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(Boolean result) {


        }

        @Override
        protected void onCancelled() {

        }
    }
}
