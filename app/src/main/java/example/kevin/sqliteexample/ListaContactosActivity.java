package example.kevin.sqliteexample;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import example.kevin.sqliteexample.adapter.ContactosAdapter;
import example.kevin.sqliteexample.database.dao.ContactoDAO;
import example.kevin.sqliteexample.model.ContactoModel;
import example.kevin.sqliteexample.util.Constantes;
import example.kevin.sqliteexample.util.RVClickListener;

public class ListaContactosActivity extends AppCompatActivity implements RVClickListener {

    FloatingActionButton fabAgregar;
    RecyclerView rvContactos;
    ContactoDAO contactoDAO;
    ContactosAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_contactos);
        //instanciar un objeto de tipo contactodao
        contactoDAO = new ContactoDAO(ListaContactosActivity.this);
        fabAgregar= findViewById(R.id.fabAdd);
        fabAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iAgregar = new Intent(ListaContactosActivity.this,ContactoActivity.class);
                startActivityForResult(iAgregar, Constantes.RQ_AGREGAR);
            }
        });
        rvContactos = findViewById(R.id.rvContactos);

        //ADMINISTRADOR DE DISEÑO
        LinearLayoutManager layoutManager= new LinearLayoutManager(ListaContactosActivity.this);
        rvContactos.setLayoutManager(layoutManager);
        //ADAPTADOR
        adapter= new ContactosAdapter();
        cargarDatos();

    }

    void cargarDatos(){
        //Data
        adapter.setListaContactos(contactoDAO.obtenerContactos());

        //1ra forma
        /*adapter.setiClickListner(new RVClickListener() {
            @Override
            public void onItemClick(View view, Object object) {

            }
        });
        */
        adapter.setiClickListner(this);

        rvContactos.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == Constantes.RQ_AGREGAR){
            if (resultCode== Activity.RESULT_OK){
                // llenar el recycler view
                cargarDatos();


            }
        }
    }

    //vemos que vista a seleccionado el usuario
    @Override
    public void onItemClick(View view, Object object) {

        final ContactoModel contacto= (ContactoModel) object;

        if(view.getId()==R.id.ivEditar){//editar contacto
            Intent iEditar= new Intent(ListaContactosActivity.this,ContactoActivity.class);
            //serializamos y deserializamos la clase contactomodelo
            iEditar.putExtra(Constantes.KEY_CCONTACTO,contacto);
            startActivityForResult(iEditar,Constantes.RQ_AGREGAR);

        }
        else{// BORRAR CONTACTO

            // popup para consultar si quiere borrar
            //configurar alert dialog
            AlertDialog.Builder builder= new AlertDialog.Builder(ListaContactosActivity.this);
            builder.setMessage(R.string.alertPregunta);
            builder.setPositiveButton(R.string.alertYes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    contactoDAO.borrarContacto(contacto.get_id());
                    cargarDatos();
                }
            });

            // si no quiero que haga nada le pasamos null al listener
            builder.setNegativeButton(R.string.alertNo,null);
            //mostrar el alert dialog
            builder.show();



        }
    }
}
