/**
 * Clase que representa un hilo productor o consumidor.
 */


class ProductorConsumidor extends Thread {
    private boolean productor;
    private recursoCompartido recursoCompartido;


    /**
     * Constructor.
     *
     * @param rec recurso compartido
     * @param productor true si es productor, false si es consumidor
     */

    public ProductorConsumidor(recursoCompartido rec ,boolean productor) {
        this.recursoCompartido = rec;
        this.productor = productor;
    }
    public boolean isProductor() {
        return productor;
    }

    /**
     * Ejecución del hilo.
     */
    @Override
    public void run() {
        java.util.Random rand = new java.util.Random();
        while (true) {
            try {
                if (productor) {
                    recursoCompartido.producirRecurso();
                    this.sleep(rand.nextInt(3000) + 200); // Espera aleatoria para simular llegada de hilos
                } else {
                    recursoCompartido.cosumirRecurso();
                    this.sleep(rand.nextInt(2000) + 200); // Espera aleatoria para simular llegada de hilos
                }
                // Espera aleatoria para simular llegada de hilos
                
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
    
    
}