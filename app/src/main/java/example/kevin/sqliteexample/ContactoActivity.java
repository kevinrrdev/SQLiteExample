package example.kevin.sqliteexample;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import example.kevin.sqliteexample.database.dao.ContactoDAO;
import example.kevin.sqliteexample.model.ContactoModel;
import example.kevin.sqliteexample.util.Constantes;

public class ContactoActivity extends AppCompatActivity {

    EditText etNombres,etTelefono;
    Button btnGuardar;
    ContactoDAO contactoDAO;
    ContactoModel contacto;
    boolean insertar= true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_contacto);

        setTitle(R.string.titleAgregarContacto);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        contactoDAO= new ContactoDAO(ContactoActivity.this);
        etNombres= findViewById(R.id.etNombres);
        etTelefono= findViewById(R.id.etTelefono);
        btnGuardar= findViewById(R.id.btnGuardar);
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nombres = etNombres.getText().toString();
                String telefono = etTelefono.getText().toString();

                if (!nombres.equals("") && !telefono.equals("")){
                    // guardar en base de datos
                   if (insertar){
                       // insertar contacto en la base de datos
                       contactoDAO.agregarContacto(nombres,telefono);
                   }else {
                       contacto.setNombre(nombres);
                       contacto.setTelefono(telefono);
                       //actualizar contacto
                       contactoDAO.actualizarContacto(contacto);
                   }


                    //Intent iDatos = new Intent();
                    //iDatos.putExtra("AGREGAR",true);

                    //setResult(Activity.RESULT_OK,iDatos);
                    //siempre va a guardar 7w7
                    setResult(Activity.RESULT_OK);
                    finish();

                }

            }
        });

        validarObjetoRecibido();
    }

    private void validarObjetoRecibido(){

        Intent iRecibido= getIntent();
        contacto= (ContactoModel) iRecibido.getSerializableExtra(Constantes.KEY_CCONTACTO);
        if (contacto!=null){
            insertar=false;

            etNombres.setText(contacto.getNombre());
            etTelefono.setText(contacto.getTelefono());

            setTitle(R.string.titleEditarContacto);
            btnGuardar.setText(R.string.btnTextEditar);
        }
    }

}
