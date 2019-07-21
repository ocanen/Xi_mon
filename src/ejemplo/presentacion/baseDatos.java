package ejemplo.presentacion;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class baseDatos {
		 
	private SQLiteDatabase myDB= null;
	private String TableName = "myTable";
	private Cursor c;
	
	public void crearBaseDatos(String nombreBase,Context actividad){
	/* Create a Database. */
		myDB = actividad.openOrCreateDatabase(nombreBase, actividad.MODE_PRIVATE, null);	 
	}
	
	public void crearTabla()
	{
		/*comando para borrar tabla*/
		//myDB.execSQL("DROP TABLE "+TableName); 	
	   /* Crea una tabla en la basededatos. */
	   myDB.execSQL("CREATE TABLE IF NOT EXISTS "
	     + TableName
	     + " (apodo VARCHAR, tiempo LONG, acierto INT(3));"); 
	}
	
	public void insertardatos(String apodo, long tiempo, int acierto){
	   /* Inserta un dato en la tabla*/
	   myDB.execSQL("INSERT INTO "
	     + TableName
	     + " (apodo, tiempo, acierto)"
	     + " VALUES ('"+apodo+"', "+tiempo+", "+acierto+");");
	}
	
	public void eliminardatos(String posicion){
		myDB.execSQL("DELETE FROM " + TableName + " WHERE " + "posicion" + "=" + posicion);
	}
	public Cursor inicalizacursor(){
	   /*retrieve data from database */
	   c = myDB.rawQuery("SELECT * FROM " + TableName +" ORDER BY acierto DESC, tiempo ASC" , null);
	   return c;
	}
	
	public record buscarRecord(int pos){
		   return null;	
	}
	
	public void salirBaseDatos() {
			   if (myDB != null)
			    myDB.close();
	}
}