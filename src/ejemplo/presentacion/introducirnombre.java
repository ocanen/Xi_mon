package ejemplo.presentacion;


import java.util.Locale;
import ejemplo.presentacion.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;

public class introducirnombre extends Activity implements TextToSpeech.OnInitListener{
    
	private Button btnPresioname;
	private EditText introNombre;
	private TextToSpeech tts;
	private int speaking;//donde guardamos si utilizamos el sintetizador o no
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.introducirnombre);
        
        tts = new TextToSpeech(this, this); 
        btnPresioname = (Button) findViewById(R.id.Button01);
        introNombre = (EditText) findViewById(R.id.EditText01);    
        
        //recogemos los datos pasados por la actividad menu
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
        	speaking = bundle.getInt("song");
        }
        introNombre.setOnFocusChangeListener(new OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {
         	   if(speaking==1 && hasFocus)
         	   {
         		   //tts.speak("", TextToSpeech.QUEUE_FLUSH, null);
	               tts.stop();
         		   tts.speak(getString(R.string.phrase22), TextToSpeech.QUEUE_FLUSH, null);
         	   }
            }
        });
        
        btnPresioname.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
	            //paramos el sonido del tts
	          	if (tts != null){
	               tts.stop();
	               tts.shutdown();
	               tts = null;
	            }
	          	   
    			Intent intent = new Intent(introducirnombre.this, juego.class);
    	        String textoRecogido= introNombre.getText().toString();    			
    			intent.putExtra("nombre", textoRecogido);
    			intent.putExtra("song", speaking);
    			startActivity(intent);
    			finish();
            }
        });
        
        //detecta que se esta indicando
        btnPresioname.setOnFocusChangeListener(new OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {
         	   if(speaking==1 && hasFocus)
         	   {
         		   //tts.speak("", TextToSpeech.QUEUE_FLUSH, null);
	               tts.stop();
         		   tts.speak(getString(R.string.phrase23), TextToSpeech.QUEUE_FLUSH, null);
         	   }
            		Log.i("oscar2","oscar2");
            }
        });
    }   
	   //evito que se teclee la tecla back
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	if(keyCode==KeyEvent.KEYCODE_BACK){
	    	Log.v("2222","tecla back");
	    	return true;
	    	}
		return false; 
    }
    
    public void onPause(){
        super.onPause();
	        try{
	        	//para llamando cuando pierde foco
	                if (tts != null){
	
	                        tts.stop();
	                        //speaking=0;
	                }
	        }catch(Exception e){}
	}

	public void onResume(){	
	        super.onResume();
	
	       // crea el objeto para que pueda leer.
	        tts = new TextToSpeech(this, this);
	}

	public void onDestroy(){
		super.onDestroy();
	        try{
	               // terminamos todos los servicios
	                if (tts != null){
	                        tts.shutdown();
	                        tts = null;
	                }
	        }
	        catch(Exception e){}
	}
    
	public void onInit(int status) {
		//definimos el idioma
		Locale loc = new Locale(getString(R.string.language), "","");
		if(speaking==1){
			if(tts.isLanguageAvailable(loc) >= TextToSpeech.LANG_AVAILABLE){
				tts.setLanguage(loc);
			}
		}
	}
 }