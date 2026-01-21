public class Mecanico extends Thread{


    private final int tiempoMaxUsoDestornillador=800;
    private final int tiempoMinUsoDestornillador=300;
    private int tiempo;
    private Taller taller;
    

    public Mecanico() {
    }

    public Mecanico(Taller taller) {
        this.taller = taller;
    }

    public int getTiempo() {
        
        return this.tiempo = (int) Math.floor(Math.random() * (tiempoMaxUsoDestornillador - tiempoMinUsoDestornillador + 1) + tiempoMinUsoDestornillador);
    }



    public Taller getTaller() {
        return this.taller;
    }

    public void setTaller(Taller taller) {
        this.taller = taller;
    }

    public Mecanico taller(Taller taller) {
        setTaller(taller);
        return this;
    }



    @Override
    public void run() {
        System.out.println("Mecanico inactivo");
        if (taller == null) {
            return;
        }
        synchronized (taller) {
            while (taller.numeroCoche <= 0) {
                try {
                    taller.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    e.printStackTrace();
                    return;
                }
            }
            taller.adquirir();
        }
        try {
            Thread.sleep(this.getTiempo());
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }
}
