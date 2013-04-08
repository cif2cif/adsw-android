package es.upm.dit.adsw;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import es.dit.upm.adsw.R;

public class EjemploIntencionesActivity extends ListActivity {

	private final static String TAG = "EjemploIntencionesActivity";
	private final static int PICKER_RESULT = 888;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		String[] opciones = { getString(R.string.op_llamar),
				getString(R.string.op_listar), getString(R.string.op_ver),
				getString(R.string.op_editar), getString(R.string.op_navegar),
				getString(R.string.op_mail), getString(R.string.op_escoger),
				getString(R.string.op_compartir) };
		setListAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, opciones));
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		String item = (String) getListAdapter().getItem(position);
		Toast.makeText(this,
				getString(R.string.opcion) + " " + position + " " + item,
				Toast.LENGTH_LONG).show();
		int opcion = (int) position;
		switch (opcion) {
			case 0: {
				llama();
				break;
			}
			case 1: {
				listaContactos();
				break;
			}
			case 2: {
				verContacto();
				break;
			}
			case 3: {
				editarContacto();
				break;
			}
			case 4: {
				verWeb();
				break;
			}
			case 5: {
				enviarCorreo();
				break;
			}
			case 6: {
				seleccionarContacto();
				break;
			}
			case 7: {
				compartir();
				break;
			}
			default:
				break;
		}
	}

	private void llama() {
		Uri uri = Uri.parse("tel:915495700");
		Intent miActividad = new Intent(Intent.ACTION_CALL, uri);
		startActivity(miActividad);
	}

	private void listaContactos() {
		Uri uri = Uri.parse("content://contacts/people/");
		Intent miActividad = new Intent(Intent.ACTION_VIEW, uri);
		startActivity(miActividad);
	}

	private void verContacto() {
		Uri uri = Uri.parse(ContactsContract.Contacts.CONTENT_LOOKUP_URI + "/"
				+ 1);
		Intent miActividad = new Intent(Intent.ACTION_VIEW, uri);
		startActivity(miActividad); // permiso READ_CONTACTS
	}

	private void editarContacto() {
		Uri uri = Uri.parse(ContactsContract.Contacts.CONTENT_LOOKUP_URI + "/"
				+ 1);
		Intent miActividad = new Intent(Intent.ACTION_EDIT, uri);
		startActivity(miActividad); // permiso WRITE_CONTACTS
	}

	private void verWeb() {
		Uri uri = Uri.parse("http://www.dit.upm.es");
		Intent miActividad = new Intent(Intent.ACTION_VIEW, uri);
		startActivity(miActividad); // requiere permiso INTERNET
	}

	private void enviarCorreo() {
		Uri uri = Uri.parse("mailto:adsw@dit.upm.es");
		Intent miActividad = new Intent(Intent.ACTION_SENDTO, uri);
		miActividad.putExtra(Intent.EXTRA_SUBJECT, "práctica");
		miActividad.putExtra(Intent.EXTRA_TEXT, "Hola");
		startActivity(miActividad);
	}

	private void seleccionarContacto() {
		Uri uri = Uri.parse("content://contacts/people/");
		Intent miActividad = new Intent(Intent.ACTION_PICK, uri);
		startActivityForResult(miActividad, PICKER_RESULT);

	}

	private void compartir() {
		String titulo = getString(R.string.compartir_con);
		Intent compartir = new Intent(Intent.ACTION_SEND);
		compartir.setType("text/plain");
		Intent escoger = Intent.createChooser(compartir, titulo);
		startActivity(escoger);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		try {
			switch (requestCode) {
				case (PICKER_RESULT): {
					if (resultCode == Activity.RESULT_OK) {
						String seleccionado = data.getDataString();
						Uri uri = Uri.parse(seleccionado);
						Log.i(TAG, "onActivityResult seleccionado " + uri);
						Intent miActividadVer = new Intent(Intent.ACTION_VIEW,
								uri);
						startActivity(miActividadVer);
					} else { // Activity.RESULT_CANCELLED
						Log.i(TAG, "onActivityResult nada seleccionado");
						Toast.makeText(getBaseContext(),
								getString(R.string.cancel), Toast.LENGTH_LONG)
								.show();
					}
				}
			}
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
			Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG)
					.show();
		} // catch
	} // onActivityResult
} // EjemploIntencionesActivity