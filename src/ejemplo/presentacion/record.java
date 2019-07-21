package ejemplo.presentacion;


import java.util.Locale;

import ejemplo.presentacion.R;
import android.app.Activity;
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
import android.widget.TextView;

public class record extends Activity implements TextToSpeech.OnInitListener {
    
	private Button btnMenuprincipal;
	private Button btnreproduccion;
	private int Column1;
	private int Column2;
	private int Column3;
	private TextView objeto[];
	private String datos[];
	private baseDatos juego;
	private TextView titulo;
	private int speaking;
	private TextToSpeech tts;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record);
       
  	   juego = new baseDatos();
	   juego.crearBaseDatos("nombreBase",this);
	   juego.crearTabla();
	   
	   String posicion="";
	   String apodo="";
	   String tiempo="";
	   String aciert="";
	   
       tts = new TextToSpeech(this, this);
       
       //recogemos los datos pasados por la actividad menu
       Bundle bundle = getIntent().getExtras();
       if(bundle!=null){
       		speaking = bundle.getInt("song");
       }
       
	   objeto=new TextView[5];
	   objeto[0] = (TextView) findViewById(R.id.TextView02);
	   objeto[1] = (TextView) findViewById(R.id.TextView03);
	   objeto[2] = (TextView) findViewById(R.id.TextView04);
	   objeto[3] = (TextView) findViewById(R.id.TextView05);
	   objeto[4] = (TextView) findViewById(R.id.TextView06);
	   
	   datos=new String[5];
	   titulo=(TextView) findViewById(R.id.TextView01);
	   titulo.setText("Apodo "+"Tiempo "+"Aciertos");

        
       btnMenuprincipal = (Button) findViewById(R.id.Button02);
       btnreproduccion= (Button) findViewById(R.id.Button01);
       
	   Cursor c=juego.inicalizacursor();
	   Column1 = c.getColumnIndex("apodo");
	   Column2 = c.getColumnIndex("tiempo");
	   Column3 = c.getColumnIndex("acierto");
	   datos[0]="";
	   // Check if our result was valid.
	   if(c.moveToFirst()){		
		   int i=0;
				   //game.getPosicion()+game.getnombre()+game.getTiempoJuego()+game.getaciertos();
		   do {
			   posicion=String.valueOf(i+1);
			   apodo=c.getString(Column1);
			   tiempo=String.valueOf(c.getLong(Column2));
			   aciert=String.valueOf(c.getInt(Column3));
			   objeto[i].setText(posicion+" "+apodo+"   "+tiempo+"Seg  "+aciert);
			   datos[0]=datos[0]+". "+"posicion "+posicion+" apodo "+apodo+"  tiempo "+tiempo+"Segundos  "+" y aciertos "+aciert;
			   i++;
		   }while(c.moveToNext() && i<5);  
	   }

        btnMenuprincipal.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	
            	//paramos el sonido del tts
	         	if (tts != null){
	               tts.stop();
	               tts.shutdown();
	               tts = null;
	            }
    			
	         	Intent intent = new Intent(record.this, menuprincipal.class);
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
         		   tts.speak("", TextToSpeech.QUEUE_FLUSH, null);
         		   tts.speak("Se ha situado en ir a menu", TextToSpeech.QUEUE_ADD, null);
         	   }
            }
        });
 
        btnreproduccion.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
          	   if(speaking==1)
         	   {
	               tts.stop();
          		   tts.speak(datos[0], TextToSpeech.QUEUE_ADD, null);
         	   }
            }
        });
        
        btnreproduccion.setOnFocusChangeListener(new OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
         	   if(speaking==1 && hasFocus)
         	   {
	               tts.stop();
         		   tts.speak("Esta situado en reproducir logros", TextToSpeech.QUEUE_ADD, null);
         	   }
            }
        });
        
        //se crea el hilo si el sonido esta activo
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
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
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
	                }
	        }catch(Exception e){}
	}

	public void onResume(){
	        super.onResume();
	       // Create our text to speech object.
	        tts = new TextToSpeech(this, this);
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

	public void onInit(int status) {
		//definimos el idioma
		Locale loc = new Locale(getString(R.string.language), "","");
		if(speaking==1){
			if(tts.isLanguageAvailable(loc) >= TextToSpeech.LANG_AVAILABLE){
				tts.setLanguage(loc);
			}
			//reproduce el texto dependiendo del idioma anterior si lo tenemos habilitado
			tts.speak("logros ", TextToSpeech.QUEUE_FLUSH, null);	
		}
	}
 }
