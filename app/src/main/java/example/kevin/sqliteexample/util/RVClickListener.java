package example.kevin.sqliteexample.util;

import android.view.View;

public interface RVClickListener {
    //tambien se puede crear la interface dentro de contactos adapter
    //esto es para poder identificar que celda se hizo click
    void onItemClick(View view, Object object);
}
