package es.dit.upm.adsw;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ListaCompraActivity extends ListActivity {
	public static final String POSICION = "posicion";
	public static final String REQUEST_CODE = "requestCode";
	public static final int CREA_PRODUCTO = 0;
	public static final int MODIFICA_PRODUCTO = 1;
	private static final String TAG = ListaCompraActivity.class.getSimpleName();
	private ArrayAdapter<Producto> adaptador;
	private List<Producto> listaProductos;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lista);		
		listaProductos = new ArrayList<Producto>();
		
		adaptador = new ArrayAdapter<Producto>(this,
				android.R.layout.simple_expandable_list_item_1,
				android.R.id.text1, listaProductos);
		rellenaLista();
		setListAdapter(adaptador);
	}

	@Override
	public void onStart() {
		super.onStart();
		Log.d(TAG, "onStart");

	}

	@Override
	public void onResume() {
		super.onResume();
		Log.d(TAG, "onResume");
		adaptador.notifyDataSetChanged();
	}

	@Override
	public void onPause() {
		super.onPause();
		Log.d(TAG, "onPause");
	}

	@Override
	public void onStop() {
		super.onStop();
		Log.d(TAG, "onStop");
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Intent intent = new Intent(this, DetalleProductoActivity.class);
		intent.putExtra(POSICION, position);
		intent.putExtra(REQUEST_CODE, MODIFICA_PRODUCTO);
		Producto cambiado = adaptador.getItem(position);
		intent.putExtra(Producto.NOMBRE, cambiado.getNombre());
		intent.putExtra(Producto.CANTIDAD, cambiado.getCantidad());
		startActivityForResult(intent, MODIFICA_PRODUCTO);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.ayuda: {
				Toast.makeText(this, getString(R.string.msg_ayuda),
						Toast.LENGTH_SHORT).show();
				return true;
			}
			case R.id.annadir: {
				Intent miIntent = new Intent(this,
						DetalleProductoActivity.class);
				miIntent.putExtra(REQUEST_CODE, CREA_PRODUCTO);
				startActivityForResult(miIntent, CREA_PRODUCTO);
				return true;
			}
			default: {
				Log.w(TAG, "Opción desconocida " + item.getItemId());
				return false;
			}
		}
	}

	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);
		if (resultCode == RESULT_CANCELED) {
			Log.i(TAG, "Cancelado");
			return;
		}
		Bundle extras = intent.getExtras();
		if (extras == null) {
			Toast.makeText(this, getString(R.string.error_detalle),
					Toast.LENGTH_SHORT).show();
			return;
		}

	
		String nombre = extras.getString(Producto.NOMBRE);
		int cantidad = extras.getInt(Producto.CANTIDAD);
		Producto producto = new Producto(nombre, cantidad);

		switch (requestCode) {
			case CREA_PRODUCTO: {
				adaptador.add(producto);
				adaptador.notifyDataSetChanged();
				break;
			}
			case MODIFICA_PRODUCTO: {
				int posicion = extras.getInt(POSICION);
				if (posicion == -1) {
					Log.e(TAG, getString(R.string.error_detalle));
					finish();
				}
				Producto anterior = adaptador.getItem(posicion);
		
				adaptador.remove(anterior); // borra y desplaza
				adaptador.insert(producto, posicion); // añade el elemento en la posición
				
				adaptador.notifyDataSetChanged();
				break;
			}
			default: {
				Log.e(TAG, "Opción no conocida " + requestCode);
			}
		}

	}
	
	private void rellenaLista() {
		adaptador.add(new Producto("Manzanas", 2));
		adaptador.add(new Producto("Pan de molde", 1));
		
	}
}
