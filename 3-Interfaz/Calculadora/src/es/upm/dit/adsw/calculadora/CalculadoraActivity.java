package es.upm.dit.adsw.calculadora;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CalculadoraActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_calculadora);
		final EditText value1 = (EditText) findViewById(R.id.valor1);
		final EditText value2 = (EditText) findViewById(R.id.valor2);
		final TextView resultado = (TextView) findViewById(R.id.resultado);
		Button botonSumar = (Button) findViewById(R.id.botonSumar);
		Button botonMultiplicar = (Button) findViewById(R.id.botonMultiplicar);
		
		botonSumar.setOnClickListener( new OnClickListener() {	
			@Override
			public void onClick(View v) {
				try {
					int val1 = Integer.parseInt(value1.getText().toString());
					int val2 = Integer.parseInt(value2.getText().toString());
					int suma = val1 + val2;
					resultado.setText("" + suma);
				}catch(Exception e) {
					Toast.makeText(getApplicationContext(), getString(R.string.error_operar), Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		botonMultiplicar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int val1 = Integer.parseInt(value1.getText().toString());
				int val2 = Integer.parseInt(value2.getText().toString());
				int producto = val1 * val2;
				resultado.setText("" + producto);
			}
		});		
	}

}
