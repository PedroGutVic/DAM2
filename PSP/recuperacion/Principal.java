
/**
 * Clase principal del programa.
 * 
 * Crea el recurso compartido y lanza los hilos
 * productores y consumidores.
 */

// Archivo: Principal.java
import java.util.ArrayList;
import java.util.List;

public class Principal {

    /**
     * Punto de entrada de la aplicación.
     *
     * @param args argumentos de línea de comandos
     */

    public static void main(String[] args) {

          // Recurso compartido entre todos los hilos
        recursoCompartido recurso = new recursoCompartido();

         // Lista de hilos
        List<ProductorConsumidor> hilos = new ArrayList<>();

        // Crear hilos productores y consumidores
        for (int i = 0; i < 4; i++) {
            ProductorConsumidor productor = new ProductorConsumidor(recurso, true);
            productor.setName("Productor-" + (i + 1));
            hilos.add(productor);
            productor.start();
        }
        for (int i = 0; i < 6; i++) {
            ProductorConsumidor consumidor = new ProductorConsumidor(recurso, false);
            consumidor.setName("Consumidor-" + (i + 1));
            hilos.add(consumidor);
            consumidor.start();
        }
    }
}



