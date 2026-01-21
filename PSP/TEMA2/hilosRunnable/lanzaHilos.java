package hilosRunnable;


public class lanzaHilos {
    public static void main(String[] args) {

        Thread h1 = new Thread(new hiloRunnable("H1 del profesor Santi"));
        Thread h2 = new Thread(new hiloRunnable("H2 del profesor Gabriel"));
        h1.start();
        h2.start();
        System.out.println("Hilo principal terminado. Mis hijos han terminado.\n");
    }
}
