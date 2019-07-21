package ejemplo.presentacion;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class datosonido {
		 
	private SQLiteDatabase myDB= null;
	private String TableName = "sonido";
	private Cursor c;

	public void crearBaseDatos(String nombreBase,Context actividad){
	/* Create a Database. */
		myDB = actividad.openOrCreateDatabase(nombreBase, actividad.MODE_PRIVATE, null);	 
	}
	
	public void crearTabla()
	{
		/*borrar tabla*/
		//myDB.execSQL("DROP TABLE "+TableName); 	
	   /* Create a Table in the Database. */
	   myDB.execSQL("CREATE TABLE IF NOT EXISTS "
	     + TableName
	     + " (sonido INT(1));"); 
	}
	
	public void insertardatos(int sonido){
	   /* Insert data to a Table*/
	   myDB.execSQL("INSERT INTO "
	     + TableName
	     + " (sonido)"
	     + " VALUES ("+sonido+");");
	}
	
	public void actualizar()
	{
		
	}
	
	public void eliminardatos(int sonido){

		myDB.execSQL("DELETE FROM " + TableName + " WHERE " + "sonido" + "=" + sonido);
	}
	public Cursor inicalizacursor(){
	   /*retrieve data from database */
	   c = myDB.rawQuery("SELECT * FROM " + TableName +" ORDER BY sonido DESC" , null);

	   return c;
	}

	public void salirBaseDatos() {
			   if (myDB != null)
			    myDB.close();
	}
}