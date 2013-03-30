	package es.upm.dit.adsw;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;



public class SobreTelecoActivity extends Activity {
    /** Called when the activity is first created. */
	
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
    		foto.setImageResource(R.drawable.etsit);
    	} else {
    		foto.setImageResource(R.drawable.etsit_b);
    	}
    	imagenInicial = ! imagenInicial; //conmuta
    }
}
