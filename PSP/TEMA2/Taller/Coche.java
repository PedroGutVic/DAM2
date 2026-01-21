public class Coche extends Thread {
    private static int id;

    public Coche() {
        id++;
        System.out.println("Nuevo coche id: " + this.id);
    }
    
}
