import java.util.Random;

/**
 * @author Pedro Gutiérrez Vico
 */

public class productor {
    static String linea = System.in.toString();
    static int contador = 0;

    public static void main(String[] args) {
        Random random = new Random();
        System.out.println(contador + linea + random.nextInt(0,10));
        contador++;
    }
}