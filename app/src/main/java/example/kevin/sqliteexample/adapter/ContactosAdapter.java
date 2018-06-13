package example.kevin.sqliteexample.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import example.kevin.sqliteexample.R;
import example.kevin.sqliteexample.model.ContactoModel;
import example.kevin.sqliteexample.util.RVClickListener;

//utiliza la clase contacto holder
public class ContactosAdapter extends RecyclerView.Adapter<ContactosAdapter.ContactoHolder> {

    public void setListaContactos(List<ContactoModel> listaContactos) {
        this.listaContactos = listaContactos;
    }

    //el adaptador usara una lista de contactosmodel
    private List<ContactoModel> listaContactos = new ArrayList<>();
    private RVClickListener iClickListner;

    public void setiClickListner(RVClickListener iClickListner) {
        this.iClickListner = iClickListner;
    }

    //nos sirve para inflar la celda
    //Retornamos un nuevo contacto holder y el contacto holder necesita un view
    //por eso necesitamos la clase layout inflater desde el contexto para inflar
    //un layout que va a ser la celda llamada item contacto
    @Override
    public ContactoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ContactoHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_contacto, parent, false));
    }

    //sirve para pintar cada una de esas celdas, para pintar el contenido
    //o para hacer alguna logica especial con cada una de estas seldas
    @Override
    public void onBindViewHolder(final ContactoHolder holder, int position) {
        //tenemos el holder que vendria a ser el objeto y la position
        final ContactoModel itemLista = listaContactos.get(position);

        holder.tvNombres.setText(itemLista.getNombre());
        holder.tvTelefono.setText(itemLista.getTelefono());

        holder.ivEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //view es el imageview que estamos seleccionando
                //el objeto es el item que quiero borrar o editar
                iClickListner.onItemClick(holder.ivEditar,itemLista);

            }
        });

        holder.ivBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickListner.onItemClick(holder.ivBorrar,itemLista);
            }
        });

    }

    //retornar la cuantos valores hay en la lista de contactos
    @Override
    public int getItemCount() {
        return listaContactos.size();
    }

    //la clase holder es la que define que elementos visuales es la que contiene cada celda
    class ContactoHolder extends RecyclerView.ViewHolder {

        TextView tvNombres, tvTelefono;
        //action de editar y borrar
        ImageView ivEditar, ivBorrar;

        public ContactoHolder(View itemView) {
            super(itemView);
            //apartir de la version 26.+ ya no se necesita castear
            tvNombres = (TextView) itemView.findViewById(R.id.tvNombres);
            tvTelefono = itemView.findViewById(R.id.tvTelefono);

            ivEditar = itemView.findViewById(R.id.ivEditar);
            ivBorrar = itemView.findViewById(R.id.ivBorrar);

        }

    }

}
