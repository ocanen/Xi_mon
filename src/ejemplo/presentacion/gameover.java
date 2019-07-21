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

public class gameover extends Activity implements TextToSpeech.OnInitListener{
    

	private Button btnMenuprincipal;
	private Button btnJuego;
	private TextView fallos;
	private TextView aciertos;
    private long datoTiempo=0;
    private int datoAciertos=0;	
    private String datoNombre="";
    private int speaking;
    
	private baseDatos juego;
	
	private TextToSpeech tts;
	private String resultado;
	
	private int Column1;
	private int Column2;
	private int Column3;
	private TextView objeto[];

	private TextView titulo;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gameover);
       
       //configuramos la base de datos
 	   juego = new baseDatos();
	   juego.crearBaseDatos("nombreBase",this);
	   juego.crearTabla();
	   
	   //creamos el interprete
	   tts = new TextToSpeech(this, this);

	   objeto=new TextView[5];
	   objeto[0] = (TextView) findViewById(R.id.TextView06);
	   objeto[1] = (TextView) findViewById(R.id.TextView07);
	   objeto[2] = (TextView) findViewById(R.id.TextView08);
	   objeto[3] = (TextView) findViewById(R.id.TextView09);
	   objeto[4] = (TextView) findViewById(R.id.TextView10);
	   titulo=(TextView) findViewById(R.id.TextView05);
	   titulo.setText(getString(R.string.phrase52));
        
       btnMenuprincipal = (Button) findViewById(R.id.Button02);
       
       btnJuego = (Button) findViewById(R.id.Button01);
        
       fallos = (TextView) findViewById(R.id.TextView01);
        
        aciertos = (TextView) findViewById(R.id.TextView02);
        
        //leo los resultados del juego:

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
        	datoTiempo = bundle.getLong("tiempo");
        	datoAciertos = bundle.getInt("aciertos");
        	datoNombre = bundle.getString("apodo");
        	speaking = bundle.getInt("song");
        }

        resultado=getString(R.string.phrase45)+datoNombre+getString(R.string.phrase46)+datoTiempo+getString(R.string.phrase47)
        			+datoAciertos+getString(R.string.phrase48);
        fallos.setText("  "+String.valueOf(datoTiempo)+" Seg");	
        aciertos.setText("  "+String.valueOf(datoAciertos));
        juego.insertardatos(datoNombre,datoTiempo,datoAciertos);
	   Cursor c=juego.inicalizacursor();
	   Column1 = c.getColumnIndex("apodo");
	   Column2 = c.getColumnIndex("tiempo");
	   Column3 = c.getColumnIndex("acierto");
	   
	   // Check if our result was valid.
	   c.moveToFirst();		
	   int i=0;
			   //game.getPosicion()+game.getnombre()+game.getTiempoJuego()+game.getaciertos();
	   do {
	   	   
		   objeto[i].setText(String.valueOf(i+1)+" "+c.getString(Column1)+"   "+c.getLong(Column2)+"Seg    "+c.getInt(Column3));
		   i++;
	   }while(c.moveToNext() && i<5);
        btnMenuprincipal.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	
            	//paramos el sonido del tts
	         	if (tts != null){
	                 tts.stop();
	                 tts.shutdown();
	                 tts = null;
	            }
	         	   
    			Intent intent = new Intent(gameover.this, menuprincipal.class);
    			intent.putExtra("song", speaking);
    			startActivity(intent);	
    	    	finish();
            }
        });
        
        btnMenuprincipal.setOnFocusChangeListener(new OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {
          	   if(speaking==1 && hasFocus)
         	   {
         		   tts.speak("", TextToSpeech.QUEUE_FLUSH, null);
         		   tts.speak(getString(R.string.phrase49), TextToSpeech.QUEUE_ADD, null);
         	   }
            		Log.i("oscar2","oscar2");
            }
        });
        
        btnJuego.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	
            	//paramos el sonido del tts
	         	if (tts != null){
	               tts.stop();
	               tts.shutdown();
	               tts = null;
	            }
	      
    			Intent intent = new Intent(gameover.this, juego.class);
    			intent.putExtra("nombre", datoNombre);
    			intent.putExtra("song", speaking);
    			startActivity(intent);	
    	    	finish();
            }
        });
        
        btnJuego.setOnFocusChangeListener(new OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {
          	   if(speaking==1 && hasFocus)
         	   {
         		   tts.speak("", TextToSpeech.QUEUE_FLUSH, null);
         		   tts.speak(getString(R.string.phrase51), TextToSpeech.QUEUE_ADD, null);
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
	
	public void onInit(int arg0) {
		//definimos el idioma
		Locale loc = new Locale(getString(R.string.language), "","");
		if(speaking==1){
			if(tts.isLanguageAvailable(loc) >= TextToSpeech.LANG_AVAILABLE){
				tts.setLanguage(loc);
			}
			//reproduce el texto dependiendo del idioma anterior
			tts.speak(getString(R.string.phrase50)+"  "+resultado, TextToSpeech.QUEUE_FLUSH, null);	
		}
	}
 }