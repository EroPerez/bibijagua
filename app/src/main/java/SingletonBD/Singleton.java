package SingletonBD;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.molanco.bibijagua.EquipActivity;
import com.molanco.bibijagua.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import BaseDatos.BaseDatos;
import Modelo.Ajuste;
import Modelo.Equipaje;
import Modelo.Foto;
import Modelo.Producto;
import harmony.java.awt.Color;

public class Singleton {
    private static AlertDialog ad = null;
    private static Singleton singleton = null;
    private Context context = null;
    private BaseDatos bd = null;
    private Foto[] list_fotos = new Foto[]{new Foto("calzado.jpg", R.drawable.calzado),
            new Foto("canastilla.jpg", R.drawable.canastilla),
            new Foto("comestibles.jpg", R.drawable.comestibles),
            new Foto("confecciones.jpg", R.drawable.confecciones),
            new Foto("construccion.jpg", R.drawable.construccion),
            new Foto("cosmeticos.jpg", R.drawable.cosmeticos),
            new Foto("electrodomesticos.jpg", R.drawable.electrodomesticos),
            new Foto("fotografia.jpg", R.drawable.fotografia),
            new Foto("herramientas.jpg", R.drawable.herramientas),
            new Foto("insmusicales.jpg", R.drawable.insmusicales),
            new Foto("juguetes.jpg", R.drawable.juguetes),
            new Foto("lenceria.jpg", R.drawable.lenceria),
            new Foto("mobiliario.jpg", R.drawable.mobiliario),
            new Foto("joyeria.jpg", R.drawable.joyeria),
            new Foto("utiles.jpg", R.drawable.utiles),
            new Foto("pintura.jpg", R.drawable.pintura),
            new Foto("vehiculo.jpg", R.drawable.vehiculo),
            new Foto("carro.jpg", R.drawable.carro),
            new Foto("medica.jpg", R.drawable.medica),
            new Foto("tabaco.jpg", R.drawable.tabaco),
            new Foto("floraf.jpg", R.drawable.floraf),
            new Foto("comunica.jpg", R.drawable.comunica),
            new Foto("alimentos.jpg", R.drawable.alimentos),
            new Foto("bienes.jpg", R.drawable.bienes),
            new Foto("efectos.jpg", R.drawable.efectos),
            new Foto("armas.jpg", R.drawable.armas),
            new Foto("valores.jpg", R.drawable.valores),
            new Foto("menaje.jpg", R.drawable.menaje),
            new Foto("regulacv.jpg", R.drawable.regulacv),
            new Foto("asesorios.jpg", R.drawable.asesorios)
    };

    private float val_aduana = 0.0f;

    private Singleton() {
    }

    public static Singleton getInstance() {

        if (singleton == null) {
            singleton = new Singleton();
        }

        return singleton;
    }

    public static Producto mostrarAgregarProd(Producto prod, Context context_actv,
                                              int cant_prod, View v1, View v2, View v3, View v4,
                                              View v5, View val_aduana, View icon_pas, View tip_moneda,
                                              View val_derechos, View val_servicio, View val_total, View pb) {
        if ((ad == null || ad.isShowing()) && ad != null) {
            return null;
        }
        ad = null;
        AlertDialog.Builder builder = new AlertDialog.Builder(context_actv);
        View v = ((Activity) context_actv).getLayoutInflater().inflate(R.layout.agregar_prod, null);
        final TextView valor = (TextView) v.findViewById(R.id.valorID);
        valor.setText(Integer.toString(cant_prod));
        ImageButton rest = (ImageButton) v.findViewById(R.id.restarID);
        Button aceptar = (Button) v.findViewById(R.id.aceptar);
        Producto producto = prod;
        final Producto finalProducto1 = producto;
        ((ImageButton) v.findViewById(R.id.adicionarID)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int cant = Integer.parseInt(valor.getText().toString());
                if (cant >= 1 && cant < finalProducto1.getCant()) {
                    cant++;
                }
                valor.setText(Integer.toString(cant));
            }
        });
        producto = prod;
        final Producto finalProducto = producto;
        rest.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                int cant = Integer.parseInt(valor.getText().toString());
                if (cant > 1 && cant <= finalProducto.getCant()) {
                    cant--;
                }
                valor.setText(Integer.toString(cant));
            }
        });
        builder.setView(v);
        ad = builder.create();
        final AlertDialog tempAlert_dialog = ad;
        final Producto producto2 = prod;
        final View view = v1;
        final Context context = context_actv;
        final View val_aduana1 = val_aduana;
        final View pb1 = pb;
        final View icon_pas1 = icon_pas;
        final View val_total1 = val_total;
        final View tip_moneda1 = tip_moneda;
        final View val_derechos1 = val_derechos;
        final View val_servicio1 = val_servicio;
        final View view9 = v4;
        final View view10 = v3;
        final View view11 = v2;
        final View view12 = v5;
        aceptar.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (tempAlert_dialog.isShowing()) {
                    int grupo = producto2.getId_grupo();
                    int cant = Integer.parseInt(valor.getText().toString());
                    int cant_grupo = Singleton.getInstance().getBd().consultCantProdGrup(grupo);
                    Equipaje equip = Singleton.getInstance().getBd().ProdEquipaje(producto2.getId_prod());
                    int cant_aux;
                    if (equip != null) {
                        cant_aux = (cant_grupo - equip.getCant()) + cant;
                    } else {
                        cant_aux = cant_grupo + cant;
                    }
                    if (producto2.getCant() <= 0 || producto2.getCant() < cant_aux) {
                        Toast.makeText(Singleton.getInstance().getContext(), "Límites de artículos excedido. Revise su equipaje.", 1).show();
                        return;
                    }
                    ((ImageButton) view).setImageDrawable(Singleton.getInstance().getContext().getResources().getDrawable(R.drawable.edit_remove));
                    producto2.setIscheck(true);
                    if (Singleton.getInstance().getBd().existIdProdEquipaje(producto2.getId_prod())) {
                        if (((Activity) context).getLocalClassName().equals("EquipActivity")) {
                            Singleton.actualizValAduan((TextView) val_aduana1, cant, producto2, (ProgressBar) pb1, icon_pas1, val_total1, tip_moneda1, context);

                            float val_der = Singleton.calcularValDerech(Float.valueOf(((TextView) val_aduana1).getText().toString()).floatValue(), null);
                            ((TextView) val_derechos1).setText(Math.abs(1000.0f - Float.valueOf(((TextView) val_aduana1).getText().toString()).floatValue()) + "0");

                            //Singleton.calcularValDerech((TextView) val_derechos1, Float.valueOf(((TextView) val_aduana1).getText().toString()).floatValue());

                            Singleton.calcularValServ((TextView) val_servicio1, val_der);
                            Singleton.calcularValTotal((TextView) val_total1, (TextView) tip_moneda1, val_der, Float.valueOf(((TextView) val_servicio1).getText().toString()).floatValue());
                        }
                        producto2.setCant_equp(cant);
                        Singleton.getInstance().getBd().updateEquipaje(cant, producto2.getId_prod());
                    } else {
                        producto2.setCant_equp(cant);
                        Singleton.getInstance().getBd().insertEquipaje(producto2.getId_prod(), cant);

                    }
                    view9.setVisibility(View.INVISIBLE);
                    view10.setVisibility(View.VISIBLE);
                    TextView cant_carr = (TextView) view11;
                    cant_carr.setVisibility(View.VISIBLE);
                    cant_carr.setText(Integer.toString(cant));
                    ((TextView) view12).setText("$" + (((float) cant) * producto2.getPrecio()) + "0");
                    tempAlert_dialog.dismiss();
                    if (((Activity) context).getLocalClassName().equals("ProductActivity")) {
                        Singleton.updateCalc();

                    }
                }
            }
        });
        ad.show();
        return prod;
    }

    public static void actualizValAduan(TextView view, int cant, Producto prod, ProgressBar prog_bar, View icon_pas, View val_total, View tip_moneda, Context context_aux) {
        double total = Double.valueOf(view.getText().toString()).doubleValue();
        double new_val_aduan = 0.0d;
        if (cant < prod.getCant_equp()) {
            new_val_aduan = total - ((double) (((float) (prod.getCant_equp() - cant)) * prod.getPrecio()));
        }
        if (cant > prod.getCant_equp()) {
            new_val_aduan = total + ((double) (((float) (cant - prod.getCant_equp())) * prod.getPrecio()));
        }
        if (cant != prod.getCant_equp()) {
            if (cant == -1) {
                new_val_aduan = total - ((double) (((float) prod.getCant_equp()) * prod.getPrecio()));
            }
            view.setText(Double.toString(new_val_aduan) + "0");

            prog_bar.setProgress((int) porcientoNumero(new_val_aduan, 1000.0d));
            if (new_val_aduan > 1000.0d) {
                prog_bar.setProgressDrawable(Singleton.getInstance().getContext().getResources().getDrawable(R.drawable.bar_progress));
                icon_pas.setVisibility(View.VISIBLE);
                val_total.setVisibility(View.INVISIBLE);
                tip_moneda.setVisibility(View.INVISIBLE);
                ((EquipActivity) context_aux).setDireccion(context_aux.getResources().getString(R.string.direccionS));
                mostrarInfo(context_aux);

            } else {
                icon_pas.setVisibility(View.INVISIBLE);
                val_total.setVisibility(View.VISIBLE);
                tip_moneda.setVisibility(View.VISIBLE);
                ((EquipActivity) context_aux).setDireccion(context_aux.getResources().getString(R.string.direccionF));
                prog_bar.setProgressDrawable(Singleton.getInstance().getContext().getResources().getDrawable(R.drawable.bar_progress));
            }
        }
    }

    public static double porcientoNumero(double parte, double total) {
        return (parte / total) * 100.0d;
    }

    public static void calcularValDerech(TextView view, float val_aduana) {
        float valor_a_pagar = val_aduana - 50.0f;
        float result = 0.0f;
        if (((double) val_aduana) > 50.0d && ((double) val_aduana) <= 500.0d) {
            result = valor_a_pagar;
        }
        if (valor_a_pagar > 500.0f && valor_a_pagar <= 1000.0f) {
            result = 450.0f + (2.0f * (valor_a_pagar - 450.0f));
        }
        view.setText(Float.toString(result) + "0");
    }

    public static float calcularValDerech(float val_aduana, Ajuste ajuste) {
        float valor_a_pagar = val_aduana - 50.0f;
        float result = 0.0f;
        if (((double) val_aduana) > 50.0d && ((double) val_aduana) <= 500.0d) {
            result = valor_a_pagar;
        }
        if (valor_a_pagar > 500.0f && valor_a_pagar <= 1000.0f) {
            result = 450.0f + (2.0f * (valor_a_pagar - 450.0f));
        }
//        if ((ajuste.getCategoria().equals("Nacional Residente") || ajuste.getCategoria().equals("Extranjero Residente")) && ajuste.getImportacion() == 2) {
//            return result * 25.0f;
//        }

//        if (ajuste.getCategoria().equals("Cubano o Extranjero Residente") && ajuste.getImportacion() == 2) {
//            return result;
//        }

        return result;
    }

    public static float calcularValServ(TextView view, float val_derecho) {
        if (val_derecho > 0.0f) {
            view.setText(Float.toString(2.0f) + "0");
            return 2.0f;
        }
        view.setText(Float.toString(0.0f) + "0");
        return 0.0f;
    }

    public static float calcularValServ(float val_derecho) {
        if (val_derecho > 0.0f) {
            return 2.0f;
        }
        return 0.0f;
    }

    public static void calcularValTotal(TextView val_total, TextView tip_moneda, float val_derecho, float val_servicio) {
        val_total.setText("$" + Float.toString(val_derecho + val_servicio) + "0");
        if (val_total.length() >= 7) {
            val_total.setTextSize(16.0f);
            tip_moneda.setTextSize(13.0f);
            return;
        }
        val_total.setTextSize(19.0f);
        tip_moneda.setTextSize(19.0f);
    }

    public static float calcularValTotal(float val_derecho, float val_servicio) {
        return val_derecho + val_servicio;
    }

    public static void mostrarInfo(Context contex_aux) {
        AlertDialog.Builder builder = new AlertDialog.Builder(contex_aux);
        View v = ((Activity) contex_aux).getLayoutInflater().inflate(R.layout.informacion, null);
        ((TextView) v.findViewById(R.id.infoID)).setText(R.string.info_pasado);
        builder.setView(v);
        builder.create().show();
    }

    public void cleanSingleton() {
        this.bd.cerrar();
        this.bd = null;
        singleton = null;
    }

    public void cargarBD() {

        this.bd = new BaseDatos(this.context);
        this.bd.abrir();
    }

    public float getVal_aduana() {
        return this.val_aduana;
    }

    public void setVal_aduana(float val_aduana) {
        this.val_aduana = val_aduana;
    }

    public BaseDatos getBd() {
        return this.bd;
    }

    public Context getContext() {
        return this.context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Foto buscarFoto(String nombre) {
        for (int i = 0; i < list_fotos.length; i++) {
            if (list_fotos[i].getNombre().equals(nombre)) {
                return new Foto(list_fotos[i].getNombre(), list_fotos[i].getId());
            }
        }
        return null;
    }

    //

    public static void printDeclaracion(String NOMBRE_DOCUMENTO, Context activ) {

        // Creamos el documento.
        Document documento = new Document(PageSize.LETTER);

        try {

            // Creamos el fichero con el nombre que deseemos.
            File f = crearFichero(NOMBRE_DOCUMENTO);

            // Creamos el flujo de datos de salida para el fichero donde
            // guardaremos el pdf.
            FileOutputStream ficheroPdf = new FileOutputStream(
                    f.getAbsolutePath());

            // Asociamos el flujo que acabamos de crear al documento.
            PdfWriter writer = PdfWriter.getInstance(documento, ficheroPdf);

            // Añadimos un título con una fuente personalizada.
            Font font = FontFactory.getFont(FontFactory.TIMES, 14,
                    Font.NORMAL, Color.BLACK);


            // Incluimos el píe de página y una cabecera
            HeaderFooter cabecera = new HeaderFooter(new Paragraph("Relaciones los equipos y artículos(electrodomésticos y otros) que trae(traen), que no clasifican como miscelaneas. / List the equipments and items(electrical appliances and others)other than miscellanea that you bring with you.", font), false);
            HeaderFooter pie = new HeaderFooter(new Paragraph("\n¿Tiene (tienen) algo que declarar ante la Aduana?/\nDo you have anything else to declare at the Customs?                 Si/Yes ____\t\t\t No ____ \n\nSi marco NO usted es pasajero del Canal Verde, firme y entregue su Declaración al funcionario de aduana ubicado en la puerta de salida. En caso de haber marcado SI, firme su Declaración y preséntese en el area de despacho de la Audana./ If you check the NO box you are Green Channel passenger, Sing and hand over your Declaration to the Customs Offices at the exit door. If you check YES box, Sing your Declaration and approach the Customs dispatch area.", font), true);

            documento.setHeader(cabecera);
            documento.setFooter(pie);

            // Abrimos el documento.
            documento.open();

            // Productos no miscelaneas
            ArrayList<Producto> list_productos = ((EquipActivity) activ).getListaProductos();


            int pages = (int) Math.ceil(list_productos.size() / 6.0);
            int offset;
            int itemCounter;

            if (pages > 0) {

                for (int page = 0; page < pages; page++) {

                    offset = page * 6;
                    PdfPTable tabla = new PdfPTable(3);
                    tabla.addCell(new Paragraph("Artículos/Items", font));
                    tabla.addCell(new Paragraph("Cantidad/Amount", font));
                    tabla.addCell(new Paragraph("Valor/Value", font));
                    itemCounter = 1;

                    for (int i = offset; i < list_productos.size(); i++) {

                        // Insertamos una tabla.

                        Producto p = list_productos.get(i);
                        tabla.addCell(new Paragraph(p.getDescripcion(), font));
                        Paragraph cantidad = new Paragraph(String.valueOf(p.getCant_equp()), font);
                        cantidad.setAlignment(1);
                        tabla.addCell(cantidad);
                        Paragraph valor = new Paragraph("$" + String.valueOf(p.getCant_equp() * p.getPrecio()) + "0", font);
                        valor.setAlignment(1);
                        tabla.addCell(valor);

                        if (itemCounter == 6) break;

                        itemCounter++;
                    }

                    documento.add(tabla);
                    documento.newPage();
                }
            } else {

                PdfPTable tabla = new PdfPTable(3);
                tabla.addCell(new Paragraph("Artículos/Items", font));
                tabla.addCell(new Paragraph("Cantidad/Amount", font));
                tabla.addCell(new Paragraph("Valor/Value", font));
                for (int i = 0; i < 15; i++)
                    tabla.addCell(new Paragraph(" ", font));
                documento.add(tabla);
            }

            Toast.makeText(activ, "Guardado en : " + f.getAbsolutePath(), Toast.LENGTH_LONG).show();

        } catch (DocumentException e) {

            Log.e("Bibijaguas", e.getMessage());

        } catch (IOException e) {

            Log.e("Bibijaguas", e.getMessage());

        } finally {

            // Cerramos el documento.
            documento.close();
        }
    }

    /**
     * Crea un fichero con el nombre que se le pasa a la función y en la ruta
     * especificada.
     *
     * @param nombreFichero
     * @return
     * @throws IOException
     */
    public static File crearFichero(String nombreFichero) throws IOException {
        File ruta = getRuta();
        File fichero = null;
        if (ruta != null)
            fichero = new File(ruta, nombreFichero);
        return fichero;
    }

    /**
     * Obtenemos la ruta donde vamos a almacenar el fichero.
     *
     * @return
     */
    public static File getRuta() {

        // El fichero será almacenado en un directorio dentro del directorio
        // Descargas
        File ruta = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())) {
            ruta = new File(
                    Environment
                            .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                    "Bibijaguas");

            if (ruta != null) {
                if (!ruta.mkdirs()) {
                    if (!ruta.exists()) {
                        return null;
                    }
                }
            }
        }

        return ruta;
    }

    public static void updateCalc() {

        LayoutInflater inflater = ((Activity) Singleton.getInstance().getContext()).getLayoutInflater();

        View view = inflater.inflate(R.layout.resumenlayout, null);


        TextView val_aduana = (TextView) view.findViewById(R.id.aduanaID);
        TextView val_derecho = (TextView) view.findViewById(R.id.derechosID);
        TextView val_servicio = (TextView) view.findViewById(R.id.serviciosID);
        TextView val_total = (TextView) view.findViewById(R.id.totalID);
        TextView tip_moneda = (TextView) view.findViewById(R.id.moneda);
        ImageView icon_pas = (ImageView) view.findViewById(R.id.iconPasad);

        ProgressBar pb = (ProgressBar) view.findViewById(R.id.pb);
        Drawable drawable = Singleton.getInstance().getContext().getResources().getDrawable(R.drawable.vertical_progress_bar);
        pb.setProgress(0);
        pb.setSecondaryProgress(100);
        pb.setMax(100);
        pb.setProgressDrawable(drawable);

        TextView direccion = (TextView) view.findViewById(R.id.direccion);
        TextView peso = (TextView) view.findViewById(R.id.pesoID);

        Ajuste aj = Singleton.getInstance().getBd().consultAjustes();

        peso.setText(Float.toString(aj.getPeso()));

        float val_adua = 0.0f;

        if (aj.getMetodo().equals("Valor/Peso") && aj.getPeso() > 25.0f) {
            val_adua = (aj.getPeso() - 25.0f) * 10.0f;
        }
        if (aj.getMetodo().equals("Valor") && aj.getValor() > 0.0f) {
            val_adua = aj.getValor();
        }

        ArrayList<Producto> list_productos = Singleton.getInstance().getBd().consultarProdEquip();

        for (int i = 0; i < list_productos.size(); i++) {
            val_adua += (((float) list_productos.get(i).getCant_equp()) * list_productos.get(i).getPrecio());
        }

        float val_der = calcularValDerech(val_adua, aj);
        float val_serv = calcularValServ(val_der);
        float val_tot = calcularValTotal(val_der, val_serv);
        float val_estado = Math.abs(1000.0f - val_adua);

        val_derecho.setText(Float.toString(val_estado) + "0");
        val_servicio.setText(Float.toString(val_serv) + "0");
        val_total.setText("$" + Float.toString(val_tot) + "0");
        val_aduana.setText(Float.toString(val_adua) + "0");

        if (val_total.length() >= 7) {
            val_total.setTextSize(16.0f);
            tip_moneda.setTextSize(13.0f);
        } else {
            val_total.setTextSize(19.0f);
            tip_moneda.setTextSize(19.0f);
        }

        if (val_adua <= ((float) 1000)) {
            pb.setProgressDrawable(Singleton.getInstance().getContext().getResources().getDrawable(R.drawable.vertical_progress_bar));
            icon_pas.setVisibility(View.INVISIBLE);
            val_total.setVisibility(View.VISIBLE);
            tip_moneda.setVisibility(View.VISIBLE);
            pb.setProgress((int) porcientoNumero((double) val_adua, (double) 1000));

            direccion.setText(Singleton.getInstance().getContext().getResources().getString(R.string.direccionF));
        } else {
            pb.setProgress(100);
            pb.setProgressDrawable(Singleton.getInstance().getContext().getResources().getDrawable(R.drawable.vertical_progress_bar));
            icon_pas.setVisibility(View.VISIBLE);
            val_total.setVisibility(View.INVISIBLE);
            tip_moneda.setVisibility(View.INVISIBLE);

            direccion.setText(Singleton.getInstance().getContext().getResources().getString(R.string.direccionS));
        }
        tip_moneda.setText(aj.getMoneda());


        Toast customtoast = new Toast(Singleton.getInstance().getContext());

        customtoast.setView(view);
        customtoast.setGravity(Gravity.TOP | Gravity.CENTER_VERTICAL, 0, 0);
        customtoast.setDuration(Toast.LENGTH_LONG);
        customtoast.show();
    }


}
