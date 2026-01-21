public class Puente {
    final int MAXPESO = 5000;
    final int NUMEROMAXCOCHES = 3;
    int numCochesActual = 0;
    int pesoActual = 0;

    public static void main(String[] args) {
        Coche[] coches = new Coche[100];
        Puente puente = new Puente();

        for (int i = 0; i < coches.length; i++) {
            coches[i] = new Coche(puente);
        }

        for (Coche coche : coches) {
            coche.start();
        }

    }

}

class Coche extends Thread {
    private final int MINPESO = 800;
    private final int MAXPESO = 2000;
    private final int LLEGARPUENTEMIN = 100;
    private final int LLEGARPUENTEMAX = 500;
    private final int TIEMPOMAXPASAR = 500;
    private final int TIEMPOMINPASAR = 100;
    private static int numCocheGlobal = 0;
    private static Puente puente;
    private int idCoche;
    private int peso;
    private int tiempoEnLlegar;
    private int tiempoEnPasar;

    public int getPeso() {
        return this.peso;
    }

    public int getNumCoche() {
        return this.idCoche;
    }

    public int getMINPESO() {
        return this.MINPESO;
    }

    public int getMAXPESO() {
        return this.MAXPESO;
    }

    public int getLLEGARPUENTEMIN() {
        return this.LLEGARPUENTEMIN;
    }

    public int getLLEGARPUENTEMAX() {
        return this.LLEGARPUENTEMAX;
    }

    public int getIdCoche() {
        return this.idCoche;
    }

    public int getTiempoEnLlegar() {
        return this.tiempoEnLlegar;
    }

    public int getTIEMPOMAXPASAR() {
        return this.TIEMPOMAXPASAR;
    }

    public int getTIEMPOMINPASAR() {
        return this.TIEMPOMINPASAR;
    }

    public int getTiempoEnPasar() {
        return this.tiempoEnPasar;
    }

    @Override
    public void run() {

        try {
            Thread.sleep(this.getTiempoEnLlegar());
            sePermitePaso(this);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void sePermitePaso(Coche coche) {
        boolean comprobante = false;

        while (!comprobante) {
            synchronized (puente) {
                if (puente.numCochesActual + 1 <= puente.NUMEROMAXCOCHES) {
                    if (puente.pesoActual + coche.getPeso() <= puente.MAXPESO) {
                        System.out.println(
                                "Ha entrado el coche " + coche.getNumCoche() + "con peso : " + coche.getPeso());
                        puente.pesoActual += coche.getPeso();
                        puente.numCochesActual++;
                        comprobante = true;
                        System.out.println("El coche esta pasando");
                    } else {

                    }
                } else {

                }
            }
        }
        try {

            coche.sleep(coche.getTiempoEnPasar());
            System.out.println();
            System.out.println();
            System.out.println("El coche" + coche.getNumCoche() + " ya a pasado");
            puente.pesoActual -= coche.getPeso();
            puente.numCochesActual -= 1;
            System.out.println();
            System.out.println();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public Coche(Puente puente) {
        numCocheGlobal++;
        this.idCoche = numCocheGlobal;
        this.peso = (int) (Math.random() * (MAXPESO - MINPESO + 1)) + MINPESO;
        this.tiempoEnLlegar = (int) (Math.random() * (LLEGARPUENTEMAX - LLEGARPUENTEMIN + 1)) + LLEGARPUENTEMIN;
        this.tiempoEnPasar = (int) (Math.random() * (TIEMPOMAXPASAR - TIEMPOMINPASAR + 1)) + TIEMPOMINPASAR;
        this.puente = puente;
    }
}
