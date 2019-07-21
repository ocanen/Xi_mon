package ejemplo.presentacion;


import java.util.Locale;
import ejemplo.presentacion.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;

public class menuprincipal extends Activity implements TextToSpeech.OnInitListener{
    
	private Button btnJuego;
	private Button btnDespedida;
	private Button btnRecord;
	private Button btnInstrucciones;
	private Button sound;
	
	private int sonido;
	private datosonido ring;
	private int valor=0;
	private int Column1;
	private int yaentre; 
	private TextToSpeech tts;
	private Context actividadEsta;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menuprincipal);
    	yaentre=0;
    	actividadEsta=this;
    	
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
        	sonido = bundle.getInt("song");
        	yaentre=1;
        }
        else {
        	sonido=1;
          	ring = new datosonido();
        	ring.crearBaseDatos("nombreBase",this);
        	ring.crearTabla();
        	Cursor c=ring.inicalizacursor();
    		Column1 = c.getColumnIndex("sonido");
        	if(c.moveToFirst()){	
        		valor=c.getInt(Column1);
        		sonido=valor;
        		Log.i("hola",String.valueOf(valor));
        	}
        }
        
        btnJuego = (Button) findViewById(R.id.Button01);
        btnInstrucciones = (Button) findViewById(R.id.Button02);
        btnRecord = (Button) findViewById(R.id.Button03);
        btnDespedida = (Button) findViewById(R.id.Button04);
        sound = (Button) findViewById(R.id.Button05);
        
        //inicializamos el sitetizador
        tts = new TextToSpeech(this, this);        
        
        //segun el lenguaje definimos el texto
        btnJuego.setText(R.string.stringMenuJuego);
        btnInstrucciones.setText(R.string.stringMenuInstrucciones);
        btnRecord.setText(R.string.stringMenuLogros);
        btnDespedida.setText(R.string.stringMenuDespedida);
        //sound.setText(R.string.stringMenusound);

        //configuramos el estado del boton al principio
  	   if(sonido==1)
 	   {
 		   sound.setText(R.string.stringMenusoundEnable);
 	   }else 
 	   {
 		   sound.setText(R.string.stringMenusoundDisable);
 	   }
        
        btnJuego.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                //paramos el sonido del tts
         	   if (tts != null){
                    tts.stop();
                    tts.shutdown();
                    tts = null;
                }
         	   
         	    //indicamos a la clase donde vamos ha ir
            	Intent intent = new Intent(menuprincipal.this, introducirnombre.class);
    			//parametros que vamos a pasar a la clase introducirnombre
            	intent.putExtra("song", sonido);
            	//nos cambiamos a la siguiente actividad
    			startActivity(intent);	
    			//finalizamos esta actividad
    	    	finish();
            }
        });
        
       btnJuego.setOnFocusChangeListener(new OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {

            	//detecta que el sonido no esta desabilitado y que realmente se ha pulsado el boton
            	if(sonido==1 && hasFocus){
          		   tts.stop();
            		tts.speak(getString(R.string.phrase03), TextToSpeech.QUEUE_FLUSH, null);
            	}
            }
        });

       sound.setOnClickListener(new OnClickListener() {
           public void onClick(View v) {
        	   //deshabilita el sonido
        	   if(sonido==1)
        	   {
                   //paramos el audio que esta reproduciendo el tts
            	   if (tts != null){
                       tts.stop();
                   }
        		   sound.setText(getString(R.string.phrase04));
        		   tts.speak(getString(R.string.phrase05), TextToSpeech.QUEUE_ADD, null);
        		   sonido=0;
        	   }else //habilita el sonido
        	   {
                   //paramos el audio que esta reproduciendo el tts
            	   if (tts != null){
                       tts.stop();
                   }
        		   sound.setText(getString(R.string.phrase06));
                   tts.speak(getString(R.string.phrase07), TextToSpeech.QUEUE_ADD, null);
        		   sonido=1;
        	   }        	   
           }
       });
       
       sound.setOnFocusChangeListener(new OnFocusChangeListener() {
           public void onFocusChange(View v, boolean hasFocus) {
           	//detecta que realmente se ha pulsado el boton
           	if(hasFocus){
           			tts.speak("", TextToSpeech.QUEUE_FLUSH, null);
           			tts.speak(getString(R.string.phrase08), TextToSpeech.QUEUE_ADD, null);
           		}
           	}
       });
       
       btnInstrucciones.setOnClickListener(new OnClickListener() {
           public void onClick(View v) {
               //paramos el sonido del tts
        	   if (tts != null){
                   tts.stop();
                   tts.shutdown();
                   tts = null;
               }
        	Intent intent = new Intent(menuprincipal.this, instrucciones.class);
   			intent.putExtra("song", sonido);
   			startActivity(intent);	
   	    	finish();
           }
       });
       
       btnInstrucciones.setOnFocusChangeListener(new OnFocusChangeListener() {

           public void onFocusChange(View v, boolean hasFocus) {

        	   //detecta que el sonido no esta desabilitado y que realmente se ha pulsado el boton
           	if(sonido==1 && hasFocus){
           		   tts.stop();
        		   tts.speak(getString(R.string.phrase09), TextToSpeech.QUEUE_FLUSH, null);
        	   }
           }
       });
        
       btnRecord.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                //paramos el sonido del tts
          	   if (tts != null){
                     tts.stop();
                     tts.shutdown();
                     tts = null;
                 }

            	Intent intent = new Intent(menuprincipal.this, record.class);
    			intent.putExtra("song", sonido);
    			startActivity(intent);	
    	    	finish();
            }
        });
       
        btnRecord.setOnFocusChangeListener(new OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {
            	//detecta que el sonido no esta desabilitado y que realmente se ha pulsado el boton
            	if(sonido==1 && hasFocus){
          		   tts.stop();
          		   tts.speak(getString(R.string.phrase10), TextToSpeech.QUEUE_FLUSH, null);
            	}
            }
        });

        //btnJuego.setFocusable(true);

        btnDespedida.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	//cerramos totalmente la aplicacion

            	//Paramos el sonido del tts
          	   if (tts != null){
                     tts.stop();
                     tts.shutdown();
                     tts = null;
                 }
          	   
          	   //guardamos el valor del sonido
          	   if(ring!=null){
	               	//recuperamos el valor en la base de datos
	               	Cursor c = ring.inicalizacursor();
	           		Column1 = c.getColumnIndex("sonido");
	               	if(c.moveToFirst()){	
	               		valor=c.getInt(Column1);
	               	}
	   	           	//eliminamos el valor guardado
	               	ring.eliminardatos(valor);
		           	ring.insertardatos(sonido);

          	   }else{
                 	ring = new datosonido();
                	ring.crearBaseDatos("nombreBase",actividadEsta);
                	ring.crearTabla();

                	//recuperamos el valor en la base de datos
                	Cursor c=ring.inicalizacursor();
            		Column1 = c.getColumnIndex("sonido");
                	if(c.moveToFirst()){	
                		valor=c.getInt(Column1);
                	}
                	
	   	           	//eliminamos el valor guardado
                	ring.eliminardatos(valor);
    	           	
                	ring.insertardatos(sonido);
          	   }
	           	//salimos totalmente de la aplicaion
            	finish();
            }
        });
        
        btnDespedida.setOnFocusChangeListener(new OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {
     		    
            	//detecta que el sonido no esta desabilitado y que realmente se ha pulsado el boton
            	if(sonido==1 && hasFocus){
          		    tts.stop();
            		tts.speak(getString(R.string.phrase11), TextToSpeech.QUEUE_FLUSH, null);
            	}
            }
        });
       
    } 
	   //evito que se teclee la tecla back
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    		if(keyCode==KeyEvent.KEYCODE_BACK){
    			Log.v("2222","tecla back");
    			return true;
	    	}
    	Log.v("2222","teclaggg back");
		return false; 
    }

    public void salir(){
    	finish();
    	onDestroy();
    }
    
    public void onPause(){
        super.onPause();
	        try{
	        	//para llamando cuando pierde foco
	                if (tts != null){
	                        tts.stop();
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
		if(tts.isLanguageAvailable(loc) >= TextToSpeech.LANG_AVAILABLE){
			tts.setLanguage(loc);
		}
		//reproduce el texto dependiendo del idioma anterior si lo tenemos habilitado
		if(sonido==1){
 		    tts.speak("", TextToSpeech.QUEUE_FLUSH, null);
			if(yaentre==0)
				tts.speak(getString(R.string.phrase12), TextToSpeech.QUEUE_FLUSH, null);
			else tts.speak(getString(R.string.phrase13), TextToSpeech.QUEUE_FLUSH, null);
		}
	}
 }
