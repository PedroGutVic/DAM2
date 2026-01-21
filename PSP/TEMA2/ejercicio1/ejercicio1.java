public class ejercicio1 {
    public static void main(String[] args) {
        Thread hilo1Thread = new Thread();

        hilo1Thread.start();
        System.out.println();

        System.out.println("Esta el hilo vivo? "+hilo1Thread.isAlive() +
        " \n  el hilo actual es :" + hilo1Thread.currentThread()  +
        " \n  la clase de hilo es : " +hilo1Thread.getClass() + 
        " \n el estado del hilo es :" + hilo1Thread.getState() );



    }
    
}
