class Taller {
    private int juegosDisponibles=3; // Para que podáis cambiarlo.
    private static Mecanico[] numMecanicos = new Mecanico[10];
    private static Thread CreadorDeCoche = new Thread();
    static int numeroCoche=0;
    public static void main(String[] args) {
        Taller taller = new Taller();
        CreadorDeCoche = new Thread(() -> crearCoche(taller));
        CreadorDeCoche.start();

        for (int i = 0; i < 10; i++) {
            
            numMecanicos[i] = new Mecanico(taller);
            numMecanicos[i].run();
        }

        for (int i = 0; i < 10; i++) {
            System.out.println(numMecanicos[i].getTiempo());
        }

        
        
    }

    public void CarritoDestornilladores(int juegos) {
        juegosDisponibles = juegos;
    }

    public void adquirir() {
        synchronized (this) {
            while (juegosDisponibles == 0) {
                try {
                    System.out.println("Esperando a un destornillador");
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            juegosDisponibles--;
            System.out.println("destornillador disponibles : "+juegosDisponibles);
        }
    }

    public static void crearCoche(Taller taller) {
        synchronized (taller) {
            while (true) {
                Coche coche = new Coche();
                numeroCoche++;
                taller.notifyAll();
                try {
                    Thread.sleep(5000);
                    
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
