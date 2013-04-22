package es.upm.dit.adsw.listacompra;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class ListaCompraActivity extends ListActivity {
	public static final String POSICION = "posicion";
	public static final String REQUEST_CODE = "requestCode";
	public static final int CREA_PRODUCTO = 0;
	public static final int MODIFICA_PRODUCTO = 1;
	private static final String TAG = ListaCompraActivity.class.getSimpleName();

	private ProductoDbAdaptador productoDbAdaptador;
	private Cursor productosCursor;
	private SimpleCursorAdapter cursorAdapter;

	private int indiceNombre;
	private int indiceCantidad;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lista);
		productoDbAdaptador = new ProductoDbAdaptador(this);
	
	
		//Rellena lista
		productosCursor = productoDbAdaptador.recuperaTodosLosProductos();
		startManagingCursor(productosCursor); // le dice a la actividad que gestione el cursor

	
		indiceNombre = productosCursor
				.getColumnIndexOrThrow(ProductoDbAdaptador.COL_NOMBRE);
		indiceCantidad = productosCursor
				.getColumnIndexOrThrow(ProductoDbAdaptador.COL_CANTIDAD);

		// Crea un array para indicar los campos que queremos mostrar en la lista
		String[] from = new String[] { ProductoDbAdaptador.COL_NOMBRE, 
				                       ProductoDbAdaptador.COL_CANTIDAD };

		// y un array con los campos de la plantilla que queremos asignarles
		
		int[] to = new int[] {R.id.text1, R.id.text2};

		// Creamos un SimpleCursorAdaptor y escogemos una plantilla de android para mostrar 2 campos
		cursorAdapter = new SimpleCursorAdapter(this,
				R.layout.row, // Podría usarse android.R.layout.simple_list_item_2
				productosCursor, from, to);
		setListAdapter(cursorAdapter);		
	}

	private void actualizaLista() {
		productosCursor.requery();
		cursorAdapter.notifyDataSetChanged();
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

		Cursor c = productosCursor;
		c.moveToPosition(position);
		Intent intent = new Intent(this, DetalleProductoActivity.class);
		intent.putExtra(ProductoDbAdaptador.COL_ID, id);
		intent.putExtra(Producto.NOMBRE, c.getString(indiceNombre));
		intent.putExtra(Producto.CANTIDAD, c.getInt(indiceCantidad));
		intent.putExtra(REQUEST_CODE, MODIFICA_PRODUCTO);
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
			case R.id.borrar: {
				borrarLista();
				actualizaLista();
				return true;
			}
			default: {
				Log.w(TAG, "Opción desconocida " + item.getItemId());
				return false;
			}
		}
	}
;
	private void borrarLista() {
		AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
		dialogo.setTitle(R.string.borrar_lista_dialogo);
		dialogo.setMessage(R.string.borrar_lista_dialogo);
		dialogo.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				productoDbAdaptador.borraTodosLosProductos();		
			}
		});
		dialogo.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Log.w(TAG, "Cancelado borrar lista");
				
			}
		});
		AlertDialog confirma = dialogo.create();
		confirma.show();
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

		for (String s : extras.keySet()) {
			Log.i(TAG, "extra " + s);
		}
		String nombre = extras.getString(Producto.NOMBRE);
		int cantidad = extras.getInt(Producto.CANTIDAD); 

		switch (requestCode) {
			case CREA_PRODUCTO: {
				Log.i(TAG, "Crea producto " + nombre + " cantidad " + cantidad);
				productoDbAdaptador.creaProducto(nombre, cantidad);

				cursorAdapter.notifyDataSetChanged();
				actualizaLista();
				break;
			}
			case MODIFICA_PRODUCTO: {
				Long id = extras.getLong(ProductoDbAdaptador.COL_ID);
				Log.i(TAG, "Modifica producto " + id);
				if (id == null) {
					Log.e(TAG, getString(R.string.error_detalle));
					finish();
				}

				productoDbAdaptador.actualizaProducto(id, nombre, cantidad);
				actualizaLista();
				break;
			}
			default: {
				Log.e(TAG, "Opción no conocida " + requestCode);
			}
		}

	}

}
