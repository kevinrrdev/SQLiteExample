package example.kevin.sqliteexample.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import example.kevin.sqliteexample.database.dao.ContactoDAO;
import example.kevin.sqliteexample.util.Constantes;

public class ContactoDBHelper extends SQLiteOpenHelper {

    // el contexto , el nombre de la base de datos ,factory,numero de la version
    //SQLiteDatabase.CursorFactory factory -> se crea una clase y extender
    // de cursorfactory y aplicar nuestra logica
    //para usar el nativo se le pasa null
    public ContactoDBHelper(Context context) {
        super(context, Constantes.DB_NAME, null, Constantes.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Crear la bd (TABLAS)
        db.execSQL(ContactoDAO.CREAR_TABLA);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
