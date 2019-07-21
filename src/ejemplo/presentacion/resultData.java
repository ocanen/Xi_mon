package ejemplo.presentacion;

public class resultData {
	private int aciertos=0;
	private int fallos=0;
	private long tiempo=-1;
	private boolean resultados=false;
	private String apodo=" ";
	
	public void setaciertos(int aciertos){
		this.aciertos=aciertos;
	}
	
	public int getaciertos(){
		return this.aciertos;
	}
	
	public long getTiempo(){
		return tiempo;
	}
	
	public void setTiempo(long tiempo){
		this.tiempo=tiempo;
	}
	
	public void setfallos(int fallos){
		this.fallos=fallos;
	}
	
	public int getfallos(){
		return this.fallos;
	}
	
	public boolean getresultados(){
		return resultados;
	} 
	
	public void setresultados(boolean resultados){
		this.resultados=resultados;
	}

	public void setapodo(String apodo){
		this.apodo=apodo;
	}
	
	public String getapodo(){
		return this.apodo;
	}

}