/**
 * 
 * @author Pedro Gutiérrez Vico
 */
import java.util.Scanner; // importación de un paquete externo para poder utilizar la clase Scanner

public class Pares {
    private static int min;
    private static int max;
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.println("Inserte el numero minimo: ");
        min = sc.nextInt();
        System.out.println("Inserte el numero maximo: ");
        max = sc.nextInt();
        for(int i = min; i <= max; i++){
            if(i % 2 == 0){
                System.out.print(i + " ");
            }
        }
    }
}