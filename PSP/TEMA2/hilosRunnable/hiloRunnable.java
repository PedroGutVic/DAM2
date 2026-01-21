package hilosRunnable;

public class hiloRunnable implements Runnable{
    /*La clase hilosRunnable.hiloRunnable debe implementar de Runnable*/
    private final String nombre;

    hiloRunnable(String nombre){
        this.nombre = nombre;
    }

    @Override
    public void run() {
        System.out.printf("Hola soy el hijo %s. \n" , this.nombre);
        System.out.println("Hilo terminado. \n");
    }
}

