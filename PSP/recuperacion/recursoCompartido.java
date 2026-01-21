import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

class recursoCompartido {

    // Tres recursos compartidos (pueden estar ocupados o vacíos)
    private Integer recurso1 = null;
    private Integer recurso2 = null;
    private Integer recurso3 = null;

    /**
     * Método consumidor.
     * Un hilo intenta consumir uno de los recursos disponibles.
     * Es synchronized para garantizar exclusión mutua.
     */
    public synchronized void cosumirRecurso() {

        // Mientras NO haya ningún recurso disponible, el hilo espera
        while (recurso1 == null && recurso2 == null && recurso3 == null) {
            try {
                wait(); // Libera el monitor y espera notificación
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }

        // Consume el primer recurso disponible
        if (recurso1 != null) {
            int val = recurso1;
            System.out.println(
                "Soy el hilo " + Thread.currentThread().getName() +
                ", he consumido un valor " + val +
                " y al multiplicarlo me sale " + (val * 2)
            );
            recurso1 = null; // Se libera el recurso
            notifyAll();     // Despierta a los hilos en espera

        } else if (recurso2 != null) {
            int val = recurso2;
            System.out.println(
                "Soy el hilo " + Thread.currentThread().getName() +
                ", he consumido un valor " + val +
                " y al multiplicarlo me sale " + (val * 2)
            );
            recurso2 = null;
            notifyAll();

        } else if (recurso3 != null) {
            int val = recurso3;
            System.out.println(
                "Soy el hilo " + Thread.currentThread().getName() +
                ", he consumido un valor " + val +
                " y al multiplicarlo me sale " + (val * 2)
            );
            recurso3 = null;
            notifyAll();
        }
    }

    /**
     * Método productor.
     * Produce un número aleatorio ejecutando un proceso externo
     * y lo guarda en el primer recurso libre.
     */
    public synchronized void producirRecurso() {

        // Mientras TODOS los recursos estén ocupados, el hilo espera
        while (recurso1 != null && recurso2 != null && recurso3 != null) {
            try {
                wait(); // Espera a que algún consumidor libere un recurso
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }

        // Variables para comunicación con el proceso externo
        BufferedReader br = null;
        BufferedWriter bw = null;
        Process proceso = null;

        try {
            // Lanza el proceso externo "Aleatorio"
            ProcessBuilder pb = new ProcessBuilder("java", "-cp", ".", "Aleatorio");
            proceso = pb.start();

            // Flujo de salida hacia el proceso
            bw = new BufferedWriter(new OutputStreamWriter(proceso.getOutputStream()));

            // Definimos el rango para el número aleatorio
            int minimo = 1;
            int maximo = 100;

            // Enviamos los valores al proceso Aleatorio
            bw.write(String.valueOf(minimo));
            bw.newLine();
            bw.write(String.valueOf(maximo));
            bw.newLine();
            bw.flush();

            // Flujo de entrada desde el proceso
            br = new BufferedReader(new InputStreamReader(proceso.getInputStream()));

            // Leemos el número generado
            String linea = br.readLine();

            if (linea == null) {
                throw new IOException("No se recibió valor del proceso Aleatorio");
            }

            int recurso = Integer.parseInt(linea.trim());
            proceso.waitFor(); // Espera a que el proceso termine

            // Guarda el recurso en la primera posición libre
            if (recurso1 == null) {
                recurso1 = recurso;
                System.out.println(
                    "Soy el hilo " + Thread.currentThread().getName() +
                    " y he producido el recurso 1: " + recurso
                );

            } else if (recurso2 == null) {
                recurso2 = recurso;
                System.out.println(
                    "Soy el hilo " + Thread.currentThread().getName() +
                    " y he producido el recurso 2: " + recurso2
                );

            } else if (recurso3 == null) {
                recurso3 = recurso;
                System.out.println(
                    "Soy el hilo " + Thread.currentThread().getName() +
                    " y he producido el recurso 3: " + recurso3
                );

            } else {
                // Caso poco probable gracias al while inicial
                System.out.println(
                    "Soy el hilo " + Thread.currentThread().getName() +
                    " y no he podido producir ningun recurso"
                );
            }

            // Despierta a los hilos consumidores
            notifyAll();

        } catch (IOException | InterruptedException | NumberFormatException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();

        }
    }
}