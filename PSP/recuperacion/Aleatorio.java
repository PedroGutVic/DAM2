import java.util.Scanner;

/**
 * Clase que genera un número aleatorio a partir de un rango
 * recibido por entrada estándar.
 */
public class Aleatorio {

    /**
     * Punto de entrada del proceso externo.
     *
     * @param args argumentos de línea de comandos
     */
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {

            int minimo = sc.nextInt();
            int maximo = sc.nextInt();

            int resultado = (int) (Math.random() * (maximo - minimo + 1)) + minimo;
            System.out.println(resultado);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
