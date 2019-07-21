package ejemplo.presentacion;

import java.util.Locale;
import java.util.Random;
import ejemplo.presentacion.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Bitmap.Config;
import android.graphics.Paint.Style;
import android.graphics.Path.Direction;
import android.media.JetPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;
/*
 * Juego X-imon
 *  01.04.2010 by UAH-UPM
 *  Autores: 
 *  Oscar Canencia Rabazo
 *  David Moncunil
 *  Jose Manuel Baños Exposito
 */
public class juego extends Activity implements TextToSpeech.OnInitListener {
	  private TextView mDebug;
	    //posicion de los colores(alpha, red, green, blue)
	    
	    private int[] coloresEsta={0xAA0000B2,0xAA248F8F,0xAA008F00,0xAA82A714,0xAACC5200,0xAAB80000,0xAACC0052};
	    private int[] coloresPuls={0xff0000FF,0xff33CCCC,0xff00CC00,0xffA3D119,0xffFF6600,0xffE60000,0xffFF0066};
		private String[] sonidos={"do","do menor","fa","la","mi","re","si","sol"};
	    private TextToSpeech tts;
	    
	    private Context pantalla;
	    
	    private int[] sequencia={1,2,3,4};
	    private JetPlayer jetPlayer; // Para reproducir sonidos usamos el jetPlayer
	    int cantidad=4; //genera 4 notas;
    	int valores=7;//los 7 valores de las notas
    	int notas[]; // Las notas que juegan
    	
        int notas_ximon[]; //Las notas a repetir
    	int sequence=20;

    	//tiempo entre teclas si esta vale 650 en el hilo entre cada pulsacion tiene 400
        int tiempo=650;

        int nota; // la nota generada
	    
	    /* Funciones para sacar las notas iniciales y generar notas nuevas*/
	    
	    /* **************************************************************** */
	    /* generaNota: Genera una nota nueva entre las que estan en el      */
	    /*             array de enteros "notas".                            */
	    /* **************************************************************** */
	    
        private MyCustomButton btn1;
        private MyCustomButton btn2;
        private MyCustomButton btn3;
        private MyCustomButton btn4;
        private int[] pulsacion;
        private int contpulsacion;
        private String datoNombre;//donde guardamos el nombre de la persona
    	private int speaking;//donde guardamos si utilizamos el sintetizador o no
    	private int espera=1600;
        LinearLayout linLayoutMain;
        
        Handler progresshandle;
        
	    public int generaNota(int[] notas)
	    {
	    	int num=((int) (Math.random() * 100)) % notas.length;
	    	return notas[num];
	    }

	    /* **************************************************************** */
	    /* dameNotas: Genera las notas para los botones, según se indique   */
	    /*            en la variable cantidad. La variable "valores" indica */
	    /*			  que valores puede tomar cada una de las notas			*/
	    /*			  se controla que no existan notas repetidas.           */
	    /* **************************************************************** */
	    
	    public int[] dameNotas(int cantidad, int valores)
	    {
	    	Random rand = new Random();
	    	int notas[];
	    	notas = new int[cantidad];
	    	boolean existe=false;
	    	int i=0;
	    	int num;
	    	
	    	//Lleno el array a -1 para evitar problemas, si no lo hago no me genera el cero
	    	for(int z=0;z<notas.length;z++)
			{
				notas[z]=-1;    			
			}

	    	while(i < notas.length)
	    	{
	    		num = rand.nextInt( valores );
	    		
	    		for(int z=0;z<notas.length;z++)
	    		{
	    			if (num==notas[z])
	    			existe=true;    			
	    		}	    		
	    		if (existe==false)
	    		{
		    		notas[i]=num;
		    		i++;
	    		}
	    		existe=false;
	    	}
			return notas;
		}
	    
	    /** Called when the activity is first created. */
	    
	   public void onCreate(Bundle icicle)  {
	        super.onCreate(icicle);
	       
	        int colorOsc=coloresEsta[0];
	        int colorBri=coloresPuls[0];
	        int seg;
	        tts = new TextToSpeech(this, this);
	        contpulsacion=0;
	        pantalla=this;
	       /*Preparamos los Layouts*/ 

	        /* Layout para línea de puntos */ 
	        linLayoutMain = new LinearLayout(this);
	        linLayoutMain.setOrientation(LinearLayout.VERTICAL);  
	        
	        /* Layout con los primeros 2 botones*/
	        LinearLayout linLayoutButtons1 = new LinearLayout(this);
	        linLayoutButtons1.setOrientation(LinearLayout.HORIZONTAL);
	        linLayoutButtons1.setPadding(0, 0, 0, 0);
	        
	        /* Layout con los segundos 2 botones*/
	        LinearLayout linLayoutButtons2 = new LinearLayout(this);
	        linLayoutButtons2.setOrientation(LinearLayout.HORIZONTAL);
	        linLayoutButtons2.setPadding(0, 0, 0, 0);
	        
	        /* Generación de botones aquí, tengo que sacar segmento de música, color oscuro y color brillante */
	        notas = new int[cantidad];
        	notas=dameNotas(cantidad,valores);
        	notas_ximon= new int[sequence];
        	pulsacion=new int[sequence];
        	
	        // botones
	        colorOsc=coloresEsta[notas[0]];
	        colorBri=coloresPuls[notas[0]];
			seg=notas[0];       
	        btn1 = new MyCustomButton(this, "btn1",colorOsc,colorBri,seg);

	        colorOsc=coloresEsta[notas[1]];
	        colorBri=coloresPuls[notas[1]];
			seg=notas[1];       
	        btn2 = new MyCustomButton(this, "btn2",colorOsc,colorBri,seg);
	 
	        colorOsc=coloresEsta[notas[2]];
	        colorBri=coloresPuls[notas[2]];
			seg=notas[2];
	        btn3 = new MyCustomButton(this, "btn3",colorOsc,colorBri,seg);
	        
	        colorOsc=coloresEsta[notas[3]];
	        colorBri=coloresPuls[notas[3]];
			seg=notas[3];
	        btn4 = new MyCustomButton(this, "btn4",colorOsc,colorBri,seg);
	       
	        // a TextView for debugging output
	        mDebug = new TextView(this);
	       
	        // add button to the layout
	        linLayoutButtons1.addView(btn1,new LinearLayout.LayoutParams(160, 190));
	        linLayoutButtons1.addView(btn2,new LinearLayout.LayoutParams(160, 190));
	        linLayoutButtons2.addView(btn3,new LinearLayout.LayoutParams(160, 190));
	        linLayoutButtons2.addView(btn4,new LinearLayout.LayoutParams(160, 190));

		    linLayoutMain.addView(mDebug,new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
                        LayoutParams.WRAP_CONTENT));
	         
	        // add buttons layout and Text view to the Main Layout
	        linLayoutMain.addView(linLayoutButtons1,new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
	                         LayoutParams.WRAP_CONTENT));
	        
	        linLayoutMain.addView(linLayoutButtons2,new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
	                          LayoutParams.WRAP_CONTENT)); 
   			
	        mDebug.setTextSize(40);
	        mDebug.setText(" ");
	        
			this.jetPlayer = JetPlayer.getJetPlayer();
			this.jetPlayer.loadJetFile(getResources().openRawResourceFd(R.raw.ximon2)); 

			//recogemos los datos pasados por la anterior interfaz
	        Bundle bundle = getIntent().getExtras();
	        if(bundle!=null){
	        	datoNombre = bundle.getString("nombre");
	        	speaking = bundle.getInt("song");
	        }
			if(speaking==1)
				espera=16000;
			else espera=0;
	        setContentView(linLayoutMain);
	        
	        /*¿¿EL JUEGO EMPIEZA AQUÍ??*/
	        // nota=generaNota(notas); // Así se pide una nota nueva
	        //generar 4 notas para empezar
	        //reproducir 4 notas
	        //bucle comprobación-nueva nota-reproducción
	    }
	   public void onStart(){
	    	super.onStart();
	    	//generateSequence();
	    	nivelNormal();
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
				//espera=16000;//tiempo para que se reproduzcan las frases siguientes
				if(tts.isLanguageAvailable(loc) >= TextToSpeech.LANG_AVAILABLE){
					tts.setLanguage(loc);
				}
				//reproduce el texto dependiendo del idioma anterior
				tts.speak(getString(R.string.phrase14)+
						getString(R.string.phrase15) + sonidos[notas[0]]
						  +getString(R.string.phrase16) + sonidos[notas[1]]
						  +getString(R.string.phrase17)
						  +getString(R.string.phrase18) + sonidos[notas[2]]
						  +getString(R.string.phrase19) + sonidos[notas[3]]
						  , TextToSpeech.QUEUE_FLUSH, null);
			}
		}
		
	   public void nivelNormal(){
	
		   progresshandle =new Handler(){
			   
			   public void handleMessage(Message msg){
			    	int tiempoSuena;
			    	int t;

				   		if(notas[0]==msg.what)
				   		{
					    	t=0; //inicio de la secuencia
					    	tiempoSuena=400;
					    	t=tecla(btn1,t,tiempoSuena);
				   		}
				   		if(notas[1]==msg.what)
				   		{
					    	t=0; //inicio de la secuencia
					    	tiempoSuena=400;
					    	t=tecla(btn2,t,tiempoSuena);
				   		}
				   		if(notas[2]==msg.what)
				   		{
					    	t=0; //inicio de la secuencia
					    	tiempoSuena=400;
					    	t=tecla(btn3,t,tiempoSuena);
				   		}
				   		if(notas[3]==msg.what)
				   		{
					    	t=0; //inicio de la secuencia
					    	tiempoSuena=400;
					    	t=tecla(btn4,t,tiempoSuena);
				   		}
				   		
				   		//vaciamos teclas
				   		if(23==msg.what)
				   		{
					    	for(int z=0;z<pulsacion.length;z++)
							{    			
						    	//borrar array
						    	pulsacion[z]=-1;
						    	//
							}
					    	contpulsacion=0;
				   		}
				   		
				   		//sacamod por pantalla los resultados
				   		if(22==msg.what)
				   		{
				   			resultData datos1=(resultData) msg.obj;
				   			String resultado=String.valueOf(datos1.getaciertos());
				   			mDebug.setText(getString(R.string.phrase20)+resultado+getString(R.string.phrase21));
				   		}
				   		//terminamos el juego
				   		if(24==msg.what){
				   			
				   			//recogemos los resultados del juego
				   			resultData datos=(resultData) msg.obj;
					    	Log.i("disable",String.valueOf(datos.getaciertos()));				   			

					    	//paramos el sonido del tts
				         	   if (tts != null){
				                    tts.stop();
				                    tts.shutdown();
				                    tts = null;
				                }
				         	   
					   		//salimos de la pantalla juego
				         	Intent intent = new Intent(juego.this, gameover.class);
			    			//pasamos parametros a la siguiente actividad
			    			intent.putExtra("aciertos", datos.getaciertos());
			    			intent.putExtra("tiempo", datos.getTiempo());
			    			intent.putExtra("apodo",datoNombre);
			    			intent.putExtra("song", speaking);
			    			
			    			startActivity(intent);	
			    	    	finish();
				   		}
			   }
		   };
		   Thread t =new Thread(new hilo());
		   t.start();
	   }

	   //comprobamos pulsacion
	   public boolean comprobarPulsacion(int pulsado,int pulso){
		   if(pulsado==pulso)return true;
		   return false;
	   }
	   
	   public int tecla(MyCustomButton boton,int t,int tiempoSuena){
	    	
		   MyCount counter;
	    	int tiempoMinimo;
	    	t=t+2;
	    	tiempoMinimo=tiempoSuena*(t+1);
	    	counter = new MyCount(tiempoMinimo,tiempoSuena,boton,t);
	    	counter.start();
	    	boton.refreshDrawableState();
	    	return t;
	   }
	   
	   //evito que se teclee la tecla back
	    public boolean onKeyDown(int keyCode, KeyEvent event) {
	    	Log.i("puls",String.valueOf("hola"));
	    	if(keyCode==KeyEvent.KEYCODE_BACK){
		    	Log.v("disable","tecla back");
		    	return true;
		    	}
			return false; 
	    }
	    
	   //hilo
	   class hilo implements Runnable{

		   public void run(){
			    Tiempo tiempoJuego;
			    tiempoJuego=new Tiempo();
		        Message msg= new Message();
		        int cont=0;
		        int aciertos=0;
		        int reproduce=0;
		        boolean resultado=true;

		        contpulsacion=0;
		        
		    	for (int n=0;n<sequence;n++) //Comenzamos con 3 notas
		    	{
		    	
		    		notas_ximon[n]=generaNota(notas); // Así se pide una nota nueva
		    	}
		    	
		    	//tiempo de espera para que el sintetizador reproduzca el texto
		    	try {
					Thread.sleep(espera);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
		    	
				//inicamos la cuenta de tiempo del juego, puesto que usuario puede ir seleccionando las teclas mientras esta la secuencia
		    	tiempoJuego.start();
		        
		    	while((cont< notas_ximon.length) && resultado){
		        	
		        	do{
		        		//creamos el mensaje de pulso
		        		msg= new Message();
				        msg.what=notas_ximon[reproduce];
				        //enviamos el mensaje
				        progresshandle.sendMessage(msg);

				        try {
							Thread.sleep(tiempo);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						Log.i("paso uno",String.valueOf(notas_ximon[reproduce]));
						reproduce++;
					}while(reproduce<=cont);
	        		
		        	reproduce=0;
		        	resultado=true;
		        	contpulsacion=0;
		        	while(reproduce<=cont && resultado==true){
			        	while(contpulsacion==0){
		    				try {
								Thread.sleep(7);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
			        	}			        	
			        	if(contpulsacion==1){
			        		Log.i("notatodada",String.valueOf(notas_ximon[reproduce]));
			        		Log.i("notapulsada",String.valueOf(pulsacion[0]));
			        		if(pulsacion[0]!=notas_ximon[reproduce])
			        			resultado=false;
			        		else{
			        			aciertos++;
			        			
			        			//envio de aciertos por ahora
			        			msg= new Message();
			    		        msg.what=22;
			    		        resultData data= new resultData();

			    		        //guardamos los resultados
			    		        data.setaciertos(aciertos);
			    		        msg.obj=data;
			    		        progresshandle.sendMessage(msg);
			    		        
			        		}
				        	contpulsacion=0;
			        	}
			        	contpulsacion=0;
			        	reproduce++;
	        		}			        
					cont++;
					reproduce=0;
    				try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
		        tiempoJuego.stop();
		        
		        Log.i("salgo","salgo");
		        
		        //salimos
				msg= new Message();
		        msg.what=24;
		        
		        resultData data= new resultData();

		        //guardamos los resultados
		        data.setaciertos(aciertos);
		        data.setTiempo(tiempoJuego.getElapsedTimeMilli());
		        
		        msg.obj=data;
		        //enviamos el mensaje
		        progresshandle.sendMessage(msg);
		   }
	   }   	   
	    // Custom Button Class must extends Button,
	    // because drawableStateChanged() is needed
	    public class MyCustomButton extends Button {

	     static final int StateDefault = 0;
	     static final int StateFocused = 1;
	     static final int StatePressed = 2;
	     
	     private int mState = StateDefault;
	     private Bitmap mBitmapDefault;
	     // private Bitmap mBitmapFocused;
	     private Bitmap mBitmapPressed;
	     private String mCaption;
	     private int segm;
	     private Canvas canvas=null;
	          
	  public MyCustomButton(Context context, String caption,int colorOsc,int colorBri,int seg) {
		          super(context);
		          mCaption = caption;
		          segm=seg;
		          
		          setClickable(true);
		          // black Background on the View
		          setBackgroundColor(0xff000000);
		          
		          // create for each State a Bitmap
		          // white Image
		          
		          mBitmapDefault = Bitmap.createBitmap(200, 200, Config.ARGB_8888);
		          // Blue Image
		         //mBitmapFocused = Bitmap.createBitmap(130, 180, Config.ARGB_8888);
		          // Green Image
		          mBitmapPressed = Bitmap.createBitmap(200, 200, Config.ARGB_8888);
		          
		          // create the Canvas
		          canvas = new Canvas();
		          
		          // define on witch Bitmap should the Canvas draw
		          // default Bitmap
		          canvas.setBitmap(mBitmapDefault);
		          	          
		          // create the Drawing Tool (Brush)
		          Paint paint = new Paint();
		          paint.setAntiAlias(true);  // for a nicer paint
		          
		          // draw a rectangle with rounded edges
		          // white Line
		          paint.setColor(colorOsc);
		          // 3px line width
		          paint.setStrokeWidth(3);
		          // just the line, not filled
		          paint.setStyle(Style.FILL_AND_STROKE);
		          
		          // create the Path
		          Path path = new Path();
		          // rectangle with 10 px Radius
		          path.addRoundRect(new RectF(10, 10, 150, 180),
		                    10, 10, Direction.CCW);
		          
		          // draw path on Canvas with the defined "brush"
		          canvas.drawPath(path, paint);
		          
		          // la imagen cuando presionamos el boton
		          canvas.setBitmap(mBitmapPressed);
		          
		          // Greed Color
		          paint.setColor(colorBri);
		          canvas.drawPath(path, paint);
		          
		          // define OnClickListener for the Button
		          setOnClickListener(onClickListener);
		          
		     }

	  		protected void onDraw(Canvas canvas) {
	               switch (mState) {
	               case StateDefault:
	                    canvas.drawBitmap(mBitmapDefault, 0, 0, null);
	                    break;
	               case StatePressed:
	                    canvas.drawBitmap(mBitmapPressed, 0, 0, null);
	                    break;
	               }
	          }
	          
	          @Override
	          protected void drawableStateChanged() {
	               if (isPressed()) {
	                    mState = StatePressed;
	    				//guardamos la tecla pulsada
	                    	pulsacion[0]=segm;
	                    	contpulsacion=1;
	                    	//contpulsacion++;
	               } else if (hasFocus()) {
	                    //mState = StateFocused;
	               } else {
	                    mState = StateDefault;
	               }
	               // force the redraw of the Image
	               // onDraw will be called!
	               invalidate();
	          }
	      		protected void onFocusChanged(boolean gainFocus, int direction, Rect previouslyFocusedRect)
	    	{
	    		if (gainFocus == true)
	    		{
	    			this.setBackgroundColor(Color.TRANSPARENT);
	    		}
	    		else
	    		{
	       			this.setBackgroundColor(Color.TRANSPARENT);
	    		}
	    	}
	    	
	          private OnClickListener onClickListener =
	               new OnClickListener() {
	               public void onClick(View arg0) {
	                    //mDebug.append(mCaption + ":click\n");
	    				jetPlayer.clearQueue();
	    				//dependiendo del segmento
	    				jetPlayer.queueJetSegment(segm, -1, 0, 0, 0, (byte) 0);
	    				jetPlayer.play();
	    				try {
							Thread.sleep(300);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
	               }
	          };
	          
	          private void setsonido()
	          {
	 				jetPlayer.clearQueue();
	 				//dependiendo del segmento
	 				jetPlayer.queueJetSegment(segm, -1, 0, 0, 0, (byte) 0);
	 				jetPlayer.play();
	          } 
	          
	          private void setcolor()
	          {
	        	  	canvas.drawBitmap(mBitmapPressed, 0, 0, null);
	                 mState = StatePressed;
	                 invalidate();
	                 this.requestLayout();
	          }
	          
	          private void setcolorInicial()
	          {
	                 canvas.drawBitmap(mBitmapDefault, 0, 0, null);
	                 mState = StateDefault;
	                 invalidate();
	                 this.requestLayout();
	          }
	}
	    
	    //funcion de temporizacion
	    public class MyCount extends CountDownTimer{

	    	private MyCustomButton boton;
	    	private int t=0;
	    	private int pos=0;
	    	
			public MyCount(long millisInFuture, long countDownInterval) {
				super(millisInFuture, countDownInterval);
			}
			public MyCount(long millisInFuture, long countDownInterval,MyCustomButton boton,int pos) {
				super(millisInFuture, countDownInterval);
				this.boton=boton;
				t=0;
				this.pos=pos;
			}	
			
			public void onFinish() {		
				boton.setcolorInicial();		
			}
			
			public void onTick(long millisUntilFinished) {
				t++;
				if(t==pos){
					boton.setcolor();
					boton.setsonido();
				}
			}
		}
}