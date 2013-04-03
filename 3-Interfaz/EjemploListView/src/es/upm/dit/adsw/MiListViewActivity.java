package es.upm.dit.adsw;

import es.dit.upm.adsw.R;
import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MiListViewActivity extends ListActivity {
	
	private final static String TAG = "MiListViewActivity";
	
	private String[] opciones = { "Llamar por teléfono",
			"Listar contactos", "Ver contacto", "Editar contacto", 
			"Ver web", "Enviar correo",  "Escoger un contacto"};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		setListAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, opciones));
	}
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		String item = (String) getListAdapter().getItem(position);
		String pulsado = "Opción " + position + " " + item;
		Toast.makeText(this, pulsado, Toast.LENGTH_LONG).show();
		Log.i(TAG, pulsado);
	}
}



