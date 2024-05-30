package BaseDatos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.faraji.environment3.Environment3;
import com.molanco.bibijagua.R;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import Modelo.Ajuste;
import Modelo.Capitulo;
import Modelo.Equipaje;
import Modelo.Historial;
import Modelo.Producto;

public class BaseDatos {
    private static final String NOMBRE_BD = "aduanadb.sqlite";
    private static final int VERSION = 1;
    public static String path = "/storage/sdcard0/carpetaBD";
    private final Context context;
    private String CANT = "CANT_E";
    private String CANT_PROD = "CANT";
    private String CATEG = "CATEGORIA";
    private String CODIGO = "CODIGO";
    private String CREATED_AT = "CREATED_AT";
    private String DELETED_AT = "DELETED_AT";
    private String DESC = "DESCRIPCION";
    private String DESC_PR = "DESCRIPCION";
    private String FECHA_FIN = "F_FIN";
    private String FECHA_INIC = "F_INICIO";
    private String ID_AJUST = "ID_AJUSTES";
    private String ID_GRUPO = "ID_GRUPO_PRODUCTO";
    private String ID_PROD = "ID_PRODUCTO";
    private String ID_TIPO_PROD = "ID_TIPO_PRODUCTO";
    private String IMAGEN = "imagen";
    private String IMPORT = "IMPORTACION";
    private String METOD = "METODO";
    private String MISCELANEA = "MISCELANEA";
    private String MONED = "MONEDA";
    private String NOMBRE_TAB_HISTO = "HISTORIAL";
    private String NOMBRE_TAB_AJUST = "AJUSTES";
    private String NOMBRE_TAB_CAP = "TC_TIPO_PRODUCTO";
    private String NOMBRE_TAB_EQUIP = "EQUIPAJE";
    private String NOMBRE_TAB_MISC = "MISCELANEA";
    private String NOMBRE_TAB_PR = "PROHIBIDOS_REGULADOS";
    private String NOMBRE_TAB_PROD = "TC_PRODUCTO";
    private String PESO = "PESO";
    private String PROHIBIDO = "PROHIBIDO";
    private String VAL = "VALOR";
    private String VALOR = "VALOR";
    private MiBaseDatos bd;
    private SQLiteDatabase myDataBase;

    public BaseDatos(Context context) {
        this.context = context;
    }

    public void abrir() {
        this.bd = new MiBaseDatos(this.context);
        this.bd.abrirBD();
    }

    public void cerrar() {
        if (this.bd != null) {
            this.bd.close();
        }
    }

    public ArrayList<Capitulo> consultarCap(int id_prod, int prohibido) {
        ArrayList<Capitulo> result = new ArrayList();
        if (this.myDataBase != null) {
            Cursor cursor;
            String[] columnas = new String[]{this.CODIGO, this.ID_TIPO_PROD, this.DESC, this.FECHA_INIC, this.FECHA_FIN, this.CREATED_AT, this.DELETED_AT, this.IMAGEN, this.PROHIBIDO, this.MISCELANEA};
            String order_by = "MISCELANEA";
            if (id_prod == -1) {
                cursor = this.myDataBase.query(this.NOMBRE_TAB_CAP, columnas, "PROHIBIDO = ?", new String[]{Integer.toString(prohibido)}, null, null, order_by);
            } else {
                cursor = this.myDataBase.query(this.NOMBRE_TAB_CAP, columnas, String.valueOf(new String[]{"ID_TIPO_PRODUCTO = ?", "PROHIBIDO = ?"}), new String[]{Integer.toString(id_prod), Integer.toString(prohibido)}, null, null, order_by);
            }
            int pos_column_COD = cursor.getColumnIndex(this.CODIGO);
            int pos_column_ID = cursor.getColumnIndex(this.ID_TIPO_PROD);
            int pos_column_DESC = cursor.getColumnIndex(this.DESC);
            int pos_column_F_IN = cursor.getColumnIndex(this.FECHA_INIC);
            int pos_column_F_FIN = cursor.getColumnIndex(this.FECHA_FIN);
            int pos_column_CREAT = cursor.getColumnIndex(this.CREATED_AT);
            int pos_column_DEL = cursor.getColumnIndex(this.DELETED_AT);
            int pos_column_IMAG = cursor.getColumnIndex(this.IMAGEN);
            int pos_column_PROHI = cursor.getColumnIndex(this.PROHIBIDO);
            int pos_column_MISCE = cursor.getColumnIndex(this.MISCELANEA);
            if (cursor != null) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    result.add(new Capitulo(cursor.getInt(pos_column_COD), cursor.getInt(pos_column_ID), cursor.getString(pos_column_DESC), cursor.getString(pos_column_F_IN), cursor.getString(pos_column_F_FIN), cursor.getString(pos_column_CREAT), cursor.getString(pos_column_DEL), cursor.getString(pos_column_IMAG), cursor.getInt(pos_column_PROHI), Boolean.valueOf(cursor.getString(pos_column_MISCE)).booleanValue()));
                    cursor.moveToNext();
                }
            } else {
                Toast.makeText(this.context, "No existe cursor.", Toast.LENGTH_LONG).show();
            }
        }
        return result;
    }

    public String consultarDescProdProh(int id_cap) {
        String result = "";
        if (this.myDataBase != null) {
            Cursor cursor = this.myDataBase.query(this.NOMBRE_TAB_PR, new String[]{this.ID_TIPO_PROD, this.DESC}, "ID_TIPO_PRODUCTO = ?", new String[]{Integer.toString(id_cap)}, null, null, null);
            int pos_column_ID = cursor.getColumnIndex(this.ID_TIPO_PROD);
            int pos_column_DESC = cursor.getColumnIndex(this.DESC_PR);
            if (cursor != null) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    int id_t_prod = cursor.getInt(pos_column_ID);
                    result = cursor.getString(pos_column_DESC);
                    cursor.moveToNext();
                }
            } else {
                Toast.makeText(this.context, "No existe cursor.", Toast.LENGTH_LONG).show();
            }
        }
        return result;
    }

    public Capitulo consultarCapID(int id_cap) {
        if (this.myDataBase != null) {
            Cursor cursor = this.myDataBase.query(this.NOMBRE_TAB_CAP, new String[]{this.CODIGO, this.ID_TIPO_PROD, this.DESC, this.FECHA_INIC, this.FECHA_FIN, this.CREATED_AT, this.DELETED_AT, this.IMAGEN, this.PROHIBIDO, this.MISCELANEA}, "ID_TIPO_PRODUCTO = ?", new String[]{Integer.toString(id_cap)}, null, null, null);
            int pos_column_COD = cursor.getColumnIndex(this.CODIGO);
            int pos_column_ID = cursor.getColumnIndex(this.ID_TIPO_PROD);
            int pos_column_DESC = cursor.getColumnIndex(this.DESC);
            int pos_column_F_IN = cursor.getColumnIndex(this.FECHA_INIC);
            int pos_column_F_FIN = cursor.getColumnIndex(this.FECHA_FIN);
            int pos_column_CREAT = cursor.getColumnIndex(this.CREATED_AT);
            int pos_column_DEL = cursor.getColumnIndex(this.DELETED_AT);
            int pos_column_IMAG = cursor.getColumnIndex(this.IMAGEN);
            int pos_column_PROHI = cursor.getColumnIndex(this.PROHIBIDO);
            int pos_column_MISCE = cursor.getColumnIndex(this.MISCELANEA);
            if (cursor != null) {
                cursor.moveToFirst();
                Capitulo result = null;
                while (!cursor.isAfterLast()) {
                    result = new Capitulo(cursor.getInt(pos_column_COD), cursor.getInt(pos_column_ID), cursor.getString(pos_column_DESC), cursor.getString(pos_column_F_IN), cursor.getString(pos_column_F_FIN), cursor.getString(pos_column_CREAT), cursor.getString(pos_column_DEL), cursor.getString(pos_column_IMAG), cursor.getInt(pos_column_PROHI), Boolean.valueOf(cursor.getString(pos_column_MISCE)).booleanValue());
                    cursor.moveToNext();
                }
                return result;
            }
            Toast.makeText(this.context, "No existe cursor.", Toast.LENGTH_LONG).show();
        }
        return null;
    }

    public ArrayList<Producto> consultarProd(int id_cap, CharSequence c) {
        ArrayList<Producto> result = new ArrayList();
        if (this.myDataBase != null) {
            String[] ids = new String[]{Integer.toString(id_cap)};
            String[] columnas = new String[]{this.ID_TIPO_PROD, this.ID_PROD, this.DESC, this.CANT_PROD, this.VALOR, this.ID_GRUPO};
            Cursor cursor = this.myDataBase.rawQuery("SELECT * FROM TC_PRODUCTO WHERE ID_TIPO_PRODUCTO = ? and DESCRIPCION LIKE '%" + c + "%'", ids);
            int pos_column_ID_CAP = cursor.getColumnIndex(this.ID_TIPO_PROD);
            int pos_column_ID_PROD = cursor.getColumnIndex(this.ID_PROD);
            int pos_column_DESC = cursor.getColumnIndex(this.DESC);
            int pos_column_CANT = cursor.getColumnIndex(this.CANT_PROD);
            int pos_column_VALOR = cursor.getColumnIndex(this.VALOR);
            int pos_column_GRUPO = cursor.getColumnIndex(this.ID_GRUPO);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                ArrayList<Producto> arrayList = result;
                arrayList.add(new Producto(cursor.getString(pos_column_DESC), cursor.getInt(pos_column_ID_PROD), cursor.getInt(pos_column_ID_CAP), cursor.getInt(pos_column_CANT), cursor.getFloat(pos_column_VALOR), cursor.getInt(pos_column_GRUPO)));
                cursor.moveToNext();
            }
        }
        return result;
    }

    public int consultCantProdGrup(int id_grupo) {
        int result = 0;
        if (this.myDataBase != null) {
            String[] args = new String[]{Integer.toString(id_grupo)};
            String[] columnas = new String[]{this.ID_PROD, this.CANT};
            Cursor cursor = this.myDataBase.rawQuery("select * from EQUIPAJE JOIN TC_PRODUCTO where TC_PRODUCTO.ID_PRODUCTO = EQUIPAJE.ID_PRODUCTO and TC_PRODUCTO.ID_GRUPO_PRODUCTO = ?", args);
            int pos_column_CANT = cursor.getColumnIndex(this.CANT);
            int pos_column_IDP = cursor.getColumnIndex(this.ID_PROD);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                int cant_prod = cursor.getInt(pos_column_CANT);
                int id = cursor.getInt(pos_column_IDP);
                result += cant_prod;
                cursor.moveToNext();
            }
        }
        return result;
    }

    public void updateAjuste(String categoria, int importacion, float peso, float valor, String moneda, String metodo) {
        if (this.myDataBase != null) {
            ContentValues content = new ContentValues();
            if (categoria != null) {
                content.put(this.CATEG, categoria);
            }
            if (importacion != 0) {
                content.put(this.IMPORT, Integer.valueOf(importacion));
            }
            if (peso != -1.0f) {
                content.put(this.PESO, Float.valueOf(peso));
            }
            if (valor != -1.0f) {
                content.put(this.VAL, Float.valueOf(valor));
            }
            if (moneda != null) {
                content.put(this.MONED, moneda);
            }
            if (metodo != null) {
                content.put(this.METOD, metodo);
            }
            this.myDataBase.update(this.NOMBRE_TAB_AJUST, content, null, null);
        }
    }

    public Ajuste consultAjustes() {
        Ajuste result = null;
        if (this.myDataBase != null) {
            String[] columnas = new String[]{this.CATEG, this.IMPORT, this.PESO, this.VAL, this.MONED, this.METOD};
            Cursor cursor = this.myDataBase.rawQuery("select * from AJUSTES", null);
            int pos_column_CAT = cursor.getColumnIndex(this.CATEG);
            int pos_column_IMP = cursor.getColumnIndex(this.IMPORT);
            int pos_column_PES = cursor.getColumnIndex(this.PESO);
            int pos_column_VAL = cursor.getColumnIndex(this.VAL);
            int pos_column_MON = cursor.getColumnIndex(this.MONED);
            int pos_column_MET = cursor.getColumnIndex(this.METOD);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                result = new Ajuste(cursor.getString(pos_column_CAT), cursor.getInt(pos_column_IMP), cursor.getFloat(pos_column_PES), cursor.getFloat(pos_column_VAL), cursor.getString(pos_column_MON), cursor.getString(pos_column_MET));
                cursor.moveToNext();
            }
        }
        return result;
    }

    public void insertEquipaje(int id_prod, int cant) {
        ContentValues content = new ContentValues();
        content.put(this.ID_PROD, Integer.valueOf(id_prod));
        content.put(this.CANT, Integer.valueOf(cant));
        this.myDataBase.insert(this.NOMBRE_TAB_EQUIP, null, content);
    }

    public void updateEquipaje(int cant_equip, int id_prod) {
        ContentValues content = new ContentValues();
        content.put(this.CANT, Integer.valueOf(cant_equip));
        this.myDataBase.update(this.NOMBRE_TAB_EQUIP, content, this.NOMBRE_TAB_EQUIP + ".ID_PRODUCTO = ?", new String[]{Integer.toString(id_prod)});
    }

    public void deleteEquipaje(int id_prod) {
        this.myDataBase.delete(this.NOMBRE_TAB_EQUIP, "ID_PRODUCTO = " + Integer.toString(id_prod), null);
    }

    public void clearEquipaje() {
        this.myDataBase.delete(this.NOMBRE_TAB_EQUIP, null, null);
    }

    public void deleteMiscEquipaje() {
        if (this.myDataBase != null) {
            this.myDataBase.delete(this.NOMBRE_TAB_EQUIP, "ID_PRODUCTO in" + "( select ID_PRODUCTO from MISCELANEA )", null);
            return;
        }
        Toast.makeText(this.context, "No Existe la BD : ", 1).show();
    }

    public boolean existMisc(int id_cap) {
        boolean result = false;
        if (this.myDataBase != null) {
            String[] args = new String[]{Integer.toString(id_cap)};
            String[] columnas = new String[]{this.ID_PROD, this.DESC};
            Cursor cursor = this.myDataBase.rawQuery("select ID_PRODUCTO from " + this.NOMBRE_TAB_MISC + " where MISCELANEA.ID_PRODUCTO in ( " + "SELECT ID_PRODUCTO from TC_PRODUCTO where TC_PRODUCTO.ID_TIPO_PRODUCTO = ?" + ")", args);
            int pos_column_ID = cursor.getColumnIndex(this.ID_PROD);
            int pos_column_IMP = cursor.getColumnIndex(this.DESC);
            cursor.moveToFirst();
            while (!cursor.isAfterLast() && !result) {
                result = true;
                cursor.moveToNext();
            }
        }
        return result;
    }

    public int isMisc(int id_prod) {
        int result = 0;
        if (this.myDataBase != null) {
            String[] args = new String[]{Integer.toString(id_prod)};
            String[] columnas = new String[]{this.ID_PROD, this.DESC};
            Cursor cursor = this.myDataBase.rawQuery("select * from " + this.NOMBRE_TAB_MISC + " where ID_PRODUCTO = ?", args);
            int pos_column_ID = cursor.getColumnIndex(this.ID_PROD);
            int pos_column_IMP = cursor.getColumnIndex(this.DESC);
            cursor.moveToFirst();
            while (!cursor.isAfterLast() && result == 0) {
                result = 1;
                cursor.moveToNext();
            }
        }
        return result;
    }

    public ArrayList<Producto> consultarProdEquip() {
        ArrayList<Producto> result = new ArrayList();
        if (this.myDataBase != null) {
//            new String[1][0] = "TC_PRODUCTO.ID_PRODUCTO";
            String[] columnas = new String[]{this.ID_TIPO_PROD, this.ID_PROD, this.DESC, this.CANT_PROD, this.VALOR, this.ID_GRUPO, this.CANT};
            Cursor cursor = this.myDataBase.rawQuery("select * from TC_PRODUCTO join EQUIPAJE where EQUIPAJE.ID_PRODUCTO = TC_PRODUCTO.ID_PRODUCTO", null);
            int pos_column_ID_CAP = cursor.getColumnIndex(this.ID_TIPO_PROD);
            int pos_column_ID_PROD = cursor.getColumnIndex(this.ID_PROD);
            int pos_column_DESC = cursor.getColumnIndex(this.DESC);
            int pos_column_CANT = cursor.getColumnIndex(this.CANT_PROD);
            int pos_column_VALOR = cursor.getColumnIndex(this.VALOR);
            int pos_column_GRUPO = cursor.getColumnIndex(this.ID_GRUPO);
            int pos_column_CANT_E = cursor.getColumnIndex(this.CANT);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                String descripcion = cursor.getString(pos_column_DESC);
                int id_cap = cursor.getInt(pos_column_ID_CAP);
                int id_prod = cursor.getInt(pos_column_ID_PROD);
                int cant_prod = cursor.getInt(pos_column_CANT);
                float precio = cursor.getFloat(pos_column_VALOR);
                int grupo = cursor.getInt(pos_column_GRUPO);
                int cant_e = cursor.getInt(pos_column_CANT_E);
                Producto prod = new Producto(descripcion, id_prod, id_cap, cant_prod, precio, grupo);
                prod.setCant_equp(cant_e);
                result.add(prod);
                cursor.moveToNext();
            }
        }
        return result;
    }

    public int consultCantProdEquip(int id_prod) {
        int result = 0;
        if (this.myDataBase != null) {
            String[] args = new String[]{Integer.toString(id_prod)};
            String[] columnas = new String[]{this.ID_PROD, this.CANT};
            Cursor cursor = this.myDataBase.rawQuery("select * from EQUIPAJE where ID_PRODUCTO = ?", args);
            int pos_column_CANT = cursor.getColumnIndex(this.CANT);
            int pos_column_IDP = cursor.getColumnIndex(this.ID_PROD);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                result = cursor.getInt(pos_column_CANT);
                int id = cursor.getInt(pos_column_IDP);
                cursor.moveToNext();
            }
        }
        return result;
    }

    public ArrayList<Capitulo> filtrarCap(CharSequence c, int prohibido) {
        ArrayList<Capitulo> result = new ArrayList();
        if (this.myDataBase != null) {
            String[] ids = new String[]{Integer.toString(prohibido)};
            Cursor cursor = this.myDataBase.rawQuery("SELECT * FROM TC_TIPO_PRODUCTO where ID_TIPO_PRODUCTO IN (" + ("SELECT ID_TIPO_PRODUCTO FROM TC_PRODUCTO where DESCRIPCION LIKE '%" + c + "%'") + ") and PROHIBIDO = ? ORDER BY MISCELANEA", ids);
            int pos_column_COD = cursor.getColumnIndex(this.CODIGO);
            int pos_column_ID = cursor.getColumnIndex(this.ID_TIPO_PROD);
            int pos_column_DESC = cursor.getColumnIndex(this.DESC);
            int pos_column_F_IN = cursor.getColumnIndex(this.FECHA_INIC);
            int pos_column_F_FIN = cursor.getColumnIndex(this.FECHA_FIN);
            int pos_column_CREAT = cursor.getColumnIndex(this.CREATED_AT);
            int pos_column_DEL = cursor.getColumnIndex(this.DELETED_AT);
            int pos_column_IMAG = cursor.getColumnIndex(this.IMAGEN);
            int pos_column_PROHI = cursor.getColumnIndex(this.PROHIBIDO);
            int pos_column_MISCE = cursor.getColumnIndex(this.MISCELANEA);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                result.add(new Capitulo(cursor.getInt(pos_column_COD), cursor.getInt(pos_column_ID), cursor.getString(pos_column_DESC), cursor.getString(pos_column_F_IN), cursor.getString(pos_column_F_FIN), cursor.getString(pos_column_CREAT), cursor.getString(pos_column_DEL), cursor.getString(pos_column_IMAG), cursor.getInt(pos_column_PROHI), Boolean.valueOf(cursor.getString(pos_column_MISCE)).booleanValue()));
                cursor.moveToNext();
            }
        }
        return result;
    }

    public boolean existIdProdEquipaje(int id_producto) {
        boolean encontrado = false;
        if (this.myDataBase != null) {
            String[] columnas = new String[]{this.ID_PROD, this.CANT};
            Cursor cursor = null;
            try {
                cursor = this.myDataBase.rawQuery("SELECT * FROM EQUIPAJE where ID_PRODUCTO = ?", new String[]{Integer.toString(id_producto)});
                int pos_column_ID_PROD = cursor.getColumnIndex(this.ID_PROD);
                int pos_column_CANT = cursor.getColumnIndex(this.CANT);
                cursor.moveToFirst();
                while (!cursor.isAfterLast() && !encontrado) {
                    if (cursor.getInt(pos_column_ID_PROD) == id_producto) {
                        encontrado = true;
                    }
                    cursor.moveToNext();
                }
                if (cursor != null) {
                    cursor.close();
                }
            } catch (Throwable th) {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
        return encontrado;
    }

    public Equipaje ProdEquipaje(int id_producto) {
        Equipaje result = null;
        if (this.myDataBase != null) {
            String[] args = new String[]{Integer.toString(id_producto)};
            String[] columnas = new String[]{this.ID_PROD, this.CANT};
            Cursor cursor = this.myDataBase.rawQuery("SELECT * FROM EQUIPAJE where ID_PRODUCTO = ?", args);
            int pos_column_ID_PROD = cursor.getColumnIndex(this.ID_PROD);
            int pos_column_CANT = cursor.getColumnIndex(this.CANT);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                result = new Equipaje(cursor.getInt(pos_column_ID_PROD), cursor.getInt(pos_column_CANT));
                cursor.moveToNext();
            }
        }
        return result;
    }

    public ArrayList<Equipaje> consultarEquip() {
        ArrayList<Equipaje> result = new ArrayList();
        if (this.myDataBase != null) {
            String[] columnas = new String[]{this.ID_PROD, this.CANT};
            Cursor cursor = this.myDataBase.rawQuery("select * from EQUIPAJE", null);
            int pos_column_ID_PROD = cursor.getColumnIndex(this.ID_PROD);
            int pos_column_CANT = cursor.getColumnIndex(this.CANT);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                result.add(new Equipaje(cursor.getInt(pos_column_ID_PROD), cursor.getInt(pos_column_CANT)));
                cursor.moveToNext();
            }
        }
        return result;
    }

    public long insertHistorial(Historial histo) {
        ContentValues content = new ContentValues();

        content.put("FECHA_VIAJE", this.getDateTime(histo.getFecha_viaje()));
        content.put("DESC", histo.getDesc());
        content.put("EQUIPAJE", histo.getEquipaje().getBytes());

        return this.myDataBase.insert(this.NOMBRE_TAB_HISTO, null, content);

    }

    public ArrayList<Historial> consultarHistorial() {
        ArrayList<Historial> result = new ArrayList();

        if (this.myDataBase != null) {
            Cursor cursor = this.myDataBase.rawQuery("select * from HISTORIAL order by FECHA_VIAJE desc", null);
            int pos_column_ID = cursor.getColumnIndex("ID");
            int pos_column_DESC = cursor.getColumnIndex("DESC");
            int pos_column_EQUI = cursor.getColumnIndex("EQUIPAJE");
            int pos_column_FECHA = cursor.getColumnIndex("FECHA_VIAJE");
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {

                Date FECHA_VIAJE = ConvertToDate(cursor.getString(pos_column_FECHA));
                String equipaje = new String(cursor.getBlob(pos_column_EQUI));

                result.add(new Historial(FECHA_VIAJE, cursor.getString(pos_column_DESC), equipaje, cursor.getInt(pos_column_ID)));
                cursor.moveToNext();
            }
        }
        return result;

    }

    public void clearHistorial() {
        this.myDataBase.delete(this.NOMBRE_TAB_HISTO, null, null);
    }

    public void deleteHistorial(Object[] ids) {

        for (Object id : ids)

            this.myDataBase.delete(this.NOMBRE_TAB_HISTO, "ID = ?", new String[]{String.valueOf(id)});
    }


    public Historial consultarHistoPorID(int ID) {
        if (this.myDataBase != null) {
            Cursor cursor = this.myDataBase.query(this.NOMBRE_TAB_HISTO, new String[]{"ID", "DESC", "EQUIPAJE", "FECHA_VIAJE"}, "ID = ?", new String[]{Integer.toString(ID)}, null, null, null);
            int pos_column_ID = cursor.getColumnIndex("ID");
            int pos_column_DESC = cursor.getColumnIndex("DESC");
            int pos_column_EQUI = cursor.getColumnIndex("EQUIPAJE");
            int pos_column_FECHA = cursor.getColumnIndex("FECHA_VIAJE");
            if (cursor != null) {
                cursor.moveToFirst();
                Historial result = null;
                while (!cursor.isAfterLast()) {
                    Date FECHA_VIAJE = ConvertToDate(cursor.getString(pos_column_FECHA));
                    String equipaje = new String(cursor.getBlob(pos_column_EQUI));
                    result = new Historial(FECHA_VIAJE, cursor.getString(pos_column_DESC), equipaje, cursor.getInt(pos_column_ID));
                    cursor.moveToNext();
                }
                return result;
            }
            Toast.makeText(this.context, "No existe el historial buscado.", Toast.LENGTH_LONG).show();
        }

        return null;
    }

    public ArrayList<Producto> consultarProdEquip(ArrayList<Equipaje> equiList) {
        ArrayList<Producto> result = new ArrayList();
        if (this.myDataBase != null) {
            for (Equipaje E : equiList) {

                String[] ids = new String[]{Integer.toString(E.getId_producto())};
                Cursor cursor = this.myDataBase.rawQuery("SELECT * FROM TC_PRODUCTO WHERE ID_PRODUCTO = ?", ids);
                int pos_column_ID_CAP = cursor.getColumnIndex(this.ID_TIPO_PROD);
                int pos_column_ID_PROD = cursor.getColumnIndex(this.ID_PROD);
                int pos_column_DESC = cursor.getColumnIndex(this.DESC);
                int pos_column_CANT = cursor.getColumnIndex(this.CANT_PROD);
                int pos_column_VALOR = cursor.getColumnIndex(this.VALOR);
                int pos_column_GRUPO = cursor.getColumnIndex(this.ID_GRUPO);
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {

                    String descripcion = cursor.getString(pos_column_DESC);
                    int id_cap = cursor.getInt(pos_column_ID_CAP);
                    int id_prod = cursor.getInt(pos_column_ID_PROD);
                    int cant_prod = cursor.getInt(pos_column_CANT);
                    float precio = cursor.getFloat(pos_column_VALOR);
                    int grupo = cursor.getInt(pos_column_GRUPO);

                    Producto prod = new Producto(descripcion, id_prod, id_cap, cant_prod, precio, grupo);
                    prod.setCant_equp(E.getCant());
                    result.add(prod);
                    cursor.moveToNext();
                }
            }
        }
        return result;
    }

    private String getDateTime(java.util.Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());

        return dateFormat.format(date);
    }

    private Date ConvertToDate(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date convertedDate = new Date();
        try {
            convertedDate = dateFormat.parse(dateString);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return convertedDate;
    }

    private class MiBaseDatos extends SQLiteOpenHelper {
        public MiBaseDatos(Context context) {
            super(context, BaseDatos.NOMBRE_BD, null, 1);
        }


        public void onCreate(SQLiteDatabase db) {
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }

        public void abrirBD() {
            if (!checkDataBase()) {
                Toast.makeText(BaseDatos.this.context, "No se encuentra la BD en: " + path, Toast.LENGTH_LONG).show();
            }
        }

        private boolean checkDataBase() {
            SQLiteDatabase checkDB = null;

            BaseDatos.path = Environment3.getCardDirectory().getPath();

            BaseDatos.path = BaseDatos.path + "/" + BaseDatos.this.context.getResources().getString(R.string.folder_name);

            File file;
            file = new File(BaseDatos.path + "/" + BaseDatos.NOMBRE_BD);
            if (file.exists()) {
                try {
                    checkDB = SQLiteDatabase.openDatabase(file.getAbsolutePath(), null, 0);
                    BaseDatos.this.myDataBase = checkDB;

                } catch (Exception e) {
                    checkDB = null;
                    Toast.makeText(BaseDatos.this.context, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            return !(checkDB == null);
        }

    }
}
