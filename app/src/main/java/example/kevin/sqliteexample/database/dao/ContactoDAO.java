package example.kevin.sqliteexample.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import example.kevin.sqliteexample.database.ContactoDBHelper;
import example.kevin.sqliteexample.model.ContactoModel;

//dao data acces object
public class ContactoDAO {
    //Nombre de la tabla
    private static String TABLA="contactos";
    //nombre de las columnas
    private static String COLUMNA_ID = "_id";
    private static String COLUMNA_NOMBRES = "nombres";
    private static String COLUMNA_TELEFONO = "telefono";
    // statica las demas variables tiene que ser estaticas
    public static String CREAR_TABLA = "CREATE TABLE "+TABLA+" ( "+
            COLUMNA_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            COLUMNA_NOMBRES+" TEXT,"+
            COLUMNA_TELEFONO+" TEXT "+
            " );";
    private ContactoDBHelper dbHelper;

    public ContactoDAO(Context context){
        dbHelper= new ContactoDBHelper(context);

    }

    //si se tiene mas campos para insertar lo recomendable es mandar un objeto
    //como parametro
    public void agregarContacto(String nombre,String telefono){

        SQLiteDatabase database= dbHelper.getWritableDatabase();
        //tabla,Columnas
        //content values se usa para indicar el valor que va a tener un campo
        //nuesta columna nombre tenga el nombre que definimos
        ContentValues values= new ContentValues();
        values.put(COLUMNA_NOMBRES,nombre);
        values.put(COLUMNA_TELEFONO,telefono);

        database.insert(TABLA,null,values);


    }


    public List<ContactoModel> obtenerContactos(){

        List<ContactoModel> list = new ArrayList<>();

        SQLiteDatabase database = dbHelper.getWritableDatabase();

        //query para hacer un select
        // otra llamada rawQuery

        //database.rawQuery("SELECT * FROM "+TABLA,null);

        //query (tabla,las columnas se crea un stringarray string[]{"nombre columna"}
        //si le pones null te trae todas las columnas
        //selection -> esto es el where "ID ="+1+"AND NOMBRES = "+"KEVIN"
        //selectionARGS -> son los valores de los where por buenas practicas si no hay nada null
        //Group by -> agrupar por
        // having -> calculos
        // orderby -> ordenar por
        // limit -> usar el limit para la consulta

        Cursor cursor = database.query(TABLA,null,null,null,null,null,null,null);

        if(cursor.getColumnCount()>0){
            //mientras el cursor pueda moverse al siguiente
            while (cursor.moveToNext()){
                ContactoModel objContacto= new ContactoModel();
                //index el nombre de la columna
                objContacto.set_id(cursor.getString(cursor.getColumnIndex(COLUMNA_ID)));
                //getcolumnName el indice de la columna
                objContacto.setNombre(cursor.getString(cursor.getColumnIndex(COLUMNA_NOMBRES)));
                objContacto.setTelefono(cursor.getString(cursor.getColumnIndex(COLUMNA_TELEFONO)));

                list.add(objContacto);
            }
        }


        return list;
    }


    public void borrarContacto(String idContacto) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        // EL ? ES EL COMODIN QUE SE REEMPLAZA con el string[] segun el orden
        //database.delete(TABLA,COLUMNA_ID+" =? AND OTRACOLUMNA =?", new String[]{idContacto,"OTRODATO"});
        database.delete(TABLA,COLUMNA_ID+" =? ", new String[]{idContacto});
    }

    public void actualizarContacto(ContactoModel contacto) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues values= new ContentValues();
        values.put(COLUMNA_NOMBRES,contacto.getNombre());
        values.put(COLUMNA_TELEFONO,contacto.getTelefono());

        database.update(TABLA, values,COLUMNA_ID + " =?",new String[]{contacto.get_id()});


    }
}
