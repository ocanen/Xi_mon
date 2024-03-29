package ejemplo.presentacion;

public class Tiempo {
	   
    private long startTime = 0;
    private long stopTime = 0;
    private long pauseTime = 0;
    private long elapsed = 0;
    private boolean running = false;
   
    public void start() {
        this.startTime = System.nanoTime();
        this.running = true;
    }

   
    public void stop() {
        this.stopTime = System.nanoTime();
        this.running = false;
    }

    public void pause(){
    	this.pauseTime=(System.nanoTime()-this.startTime);
        this.running = false;		
	}
    
    public void continuar(){
        this.startTime = (System.nanoTime()-this.pauseTime);
        this.running = true;
    }
    
    public void reset() {
        this.startTime = 0;
        this.stopTime = 0;
        this.running = false;
    }    
   
    //elaspsed time in microseconds
    public long getElapsedTimeMicro() {
        if (running) {
            elapsed = ((System.nanoTime() - startTime) / 1000);
        }
        else {
            elapsed = ((stopTime - startTime) / 1000);
        }
        return elapsed;
    }
     
    //elaspsed time in milliseconds
    public long getElapsedTimeMilli() {
        if (running) {
            elapsed = ((System.nanoTime() - startTime) / 1000000000);
        }
        else {
            elapsed = ((stopTime - startTime) / 1000000000);
        }
        return elapsed;
    }
}

