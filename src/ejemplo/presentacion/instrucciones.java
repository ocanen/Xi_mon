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
import android.widget.TextView;

public class instrucciones extends Activity implements TextToSpeech.OnInitListener {
    
	private Button btnMenuprincipal;
	private Button btnReproduccir;
	private TextToSpeech tts;
	private int speaking;//donde guardamos si utilizamos el sintetizador o no
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.instrucciones);
		
        tts = new TextToSpeech(this, this);
        
        //recogemos los datos pasados por la actividad menu
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
        	speaking = bundle.getInt("song");
        }
        
        TextView objeto = (TextView) findViewById(R.id.TextView02);
        objeto.setText(getString(R.string.phrase24) +"\n"+
        		getString(R.string.phrase25) +"\n"+
        		getString(R.string.phrase26) +"\n"+
        		getString(R.string.phrase27) +"\n"+
        		getString(R.string.phrase28) +"\n"+
        		getString(R.string.phrase29) +"\n"+
        		getString(R.string.phrase30) +"\n"+
        		getString(R.string.phrase31) +"\n"+
        		getString(R.string.phrase32) +"\n"+
        		getString(R.string.phrase33) +"\n"+
        		getString(R.string.phrase34) +"\n"+
        		getString(R.string.phrase35) +"\n"+
        		getString(R.string.phrase36) +"\n"+
        		getString(R.string.phrase37) +"\n"+
        		getString(R.string.phrase38) +"\n"+
        		getString(R.string.phrase39) +"\n"+
        		getString(R.string.phrase40) +"\n"+
        		getString(R.string.phrase41));
        
        btnMenuprincipal = (Button) findViewById(R.id.Button02);
        btnReproduccir = (Button) findViewById(R.id.Button01);
        
        btnMenuprincipal.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
		    	
            	//paramos el sonido del tts
	         	   if (tts != null){
	                    tts.stop();
	                    tts.shutdown();
	                    tts = null;
	                }
	         	   
    			Intent intent = new Intent(instrucciones.this, menuprincipal.class);
    			intent.putExtra("song", speaking);
    			startActivity(intent);	
    	    	finish();
            }
        });
        
        btnMenuprincipal.setOnFocusChangeListener(new OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {
         	   if(speaking==1 && hasFocus)
         	   {
         		   tts.stop();
         		   tts.speak(getString(R.string.phrase42), TextToSpeech.QUEUE_FLUSH, null);
         	   }
            }
        });
        
        btnReproduccir.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
          	   if(speaking==1)
         	   {
       			tts.speak(getString(R.string.phrase24) +"\n"+
       	        		getString(R.string.phrase25) +"\n"+
       	        		getString(R.string.phrase26) +"\n"+
       	        		getString(R.string.phrase27) +"\n"+
       	        		getString(R.string.phrase28) +"\n"+
       	        		getString(R.string.phrase29) +"\n"+
       	        		getString(R.string.phrase30) +"\n"+
       	        		getString(R.string.phrase31) +"\n"+
       	        		getString(R.string.phrase32) +"\n"+
       	        		getString(R.string.phrase33) +"\n"+
       	        		getString(R.string.phrase34) +"\n"+
       	        		getString(R.string.phrase35) +"\n"+
       	        		getString(R.string.phrase36) +"\n"+
       	        		getString(R.string.phrase37) +"\n"+
       	        		getString(R.string.phrase38) +"\n"+
       	        		getString(R.string.phrase39) +"\n"+
       	        		getString(R.string.phrase40) +"\n"+
       	        		getString(R.string.phrase41), TextToSpeech.QUEUE_ADD, null);
         	   }
            }
        });
        
        btnReproduccir.setOnFocusChangeListener(new OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {
         	   if(speaking==1 && hasFocus)
         	   {
         		   tts.stop();
         		   tts.speak(getString(R.string.phrase43), TextToSpeech.QUEUE_ADD, null);
         	   }
            }
        });
        if(speaking==1)nivelNormal();
    }
    
    public void nivelNormal(){
		
    	Thread t =new Thread(new hilo());
		   t.start();
	}
	   //hilo
	class hilo implements Runnable{

		   public void run(){
			   try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}/*
			tts.speak("Xi-Mon es un juego musical de tipo puzzle, " +"\n"+
	        		"que te ayudará a mejorar tu memoria e " +"\n"+
	        		"incluso a aprender música, haciéndote " +"\n"+
	        		"capaz de distinguir las notas musicales. " +"\n"+
	        		"El juego está basado en el tradicional " +"\n"+
	        		"Simón, con la diferencia de que los 4 " +"\n"+
	        		"botones que se generan (dos arriba y dos " +"\n"+
	        		"abajo) son distintos en cada partida y se " +"\n"+
	        		"eligen entre las 7 notas musicales de la " +"\n"+
	        		"escala de Do menor. En cada partida los " +"\n"+
	        		"botones son diferentes y se colocan de " +"\n"+
	        		"forma diferente, eso sí, a cada botón " +"\n"+
	        		"siempre le corresponde el mismo color. " +"\n"+
	        		"¿Sabías que las notas musicales se asocian " +"\n"+
	        		"con los colores por su frecuencia?. " +"\n"+
	        		"Esperamos que te diviertas jugando a " +"\n"+
	        		"Xi-Mon, que ejercites tu cerebro y que " +"\n"+
	        		"aprendas mucho.", TextToSpeech.QUEUE_ADD, null);
				*/	   
		   }
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
	        	//Stop talking when we lose focus
	                if (tts != null){
	
	                        tts.stop();
	                        //speaking=0;
	                }
	        }catch(Exception e){}
	}

	public void onResume(){
	
	        super.onResume();
	
	       // Create our text to speech object.
	        tts =  new TextToSpeech(this, this);
	}

	public void onDestroy(){
	
	        super.onDestroy();
	        try{
	               // We're closing down so kill it with fire.
	                if (tts != null){
	                        tts.shutdown();
	
	                        tts = null;
	                }
	        }
	        catch(Exception e){}
	}

	public void onInit(int arg0) {
		//definimos el idioma
		Locale loc = new Locale(getString(R.string.language), "","");
		if(speaking==1){
			if(tts.isLanguageAvailable(loc) >= TextToSpeech.LANG_AVAILABLE){
				tts.setLanguage(loc);
			}
			//reproduce el texto dependiendo del idioma anterior si lo tenemos habilitado
			tts.speak(getString(R.string.phrase44), TextToSpeech.QUEUE_FLUSH, null);	
		}
	}
 }
