package es.upm.dit.adsw.listacompra;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DetalleProductoActivity extends Activity {
	private static final String TAG = DetalleProductoActivity.class
			.getSimpleName();

	private EditText editNombre;
	private EditText editCantidad;
	private Button buttonSave;
	private Button buttonCancel;

	private long id;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detalle);

		editNombre = (EditText) findViewById(R.id.editTextNombre);
		editCantidad = (EditText) findViewById(R.id.editTextCantidad);
		buttonSave = (Button) findViewById(R.id.buttonSave);
		buttonSave.setOnClickListener(new MiButtonSaveOnClickListener());
		buttonCancel = (Button) findViewById(R.id.buttonCancel);
		buttonCancel.setOnClickListener(new MiButtonCancelOnClickListener());

		Bundle extras = getIntent().getExtras();
		if (extras == null) {
			Toast.makeText(this, getString(R.string.error_detalle),
					Toast.LENGTH_SHORT).show();
			Log.e(TAG, "Error sin extras ");
			finish();
		}
		int requestCode = extras.getInt(ListaCompraActivity.REQUEST_CODE);
		if (requestCode == ListaCompraActivity.MODIFICA_PRODUCTO) {
			String nombre = extras.getString(Producto.NOMBRE);
			int cantidad = extras.getInt(Producto.CANTIDAD);
			id = extras.getLong(ProductoDbAdaptador.COL_ID);			
			if (nombre != null) {editNombre.setText(nombre);};
			editCantidad.setText("" + cantidad);
			Log.d(TAG, "Edito " + nombre);
		}
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

	private class MiButtonSaveOnClickListener implements View.OnClickListener {
		@Override
		public void onClick(View v) {
			Log.d(TAG, "onClick SaveButton");
			

			Intent miIntent = new Intent();
		
			miIntent.putExtra(ProductoDbAdaptador.COL_ID, id);
			miIntent.putExtra(Producto.NOMBRE, editNombre
					.getText().toString());
			miIntent.putExtra(Producto.CANTIDAD,
					Integer.parseInt(editCantidad.getText().toString()));
		
			setResult(RESULT_OK, miIntent);
			finish();
		}
	}

	private class MiButtonCancelOnClickListener implements View.OnClickListener {
		@Override
		public void onClick(View v) {
			Log.d(TAG, "onClick CancelButton");
			setResult(RESULT_CANCELED);
			finish();
		}
	}
}