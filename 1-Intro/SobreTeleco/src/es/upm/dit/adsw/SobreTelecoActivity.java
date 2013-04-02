	package es.upm.dit.adsw;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;



public class SobreTelecoActivity extends Activity {
   
	private final static String TAG = SobreTelecoActivity.class.getName();
	private boolean imagenInicial = true;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
    
    public void muestraMensaje(View v) {
    	Toast.makeText(this, R.string.sobre_teleco_mensaje,
    			Toast.LENGTH_LONG).show();
    }
    
    public void intercambiaImagen(View v) {
    	ImageView foto = (ImageView) findViewById(R.id.imageViewEtsit);
    	if (imagenInicial) {
    		Log.i(TAG, "Puesta imagen inicial");
    		foto.setImageResource(R.drawable.etsit_b);
    	} else {
    		Log.i(TAG, "Puesta imagen alternativa");
    		foto.setImageResource(R.drawable.etsit);
    	}
    	imagenInicial = ! imagenInicial; //conmuta
    }
}
