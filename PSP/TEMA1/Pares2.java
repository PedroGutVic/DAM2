/**
 * @author Pedro Gutiérrez Vico
 */

import java.util.ArrayList;
import java.util.Scanner; // importación de un paquete externo para poder utilizar la clase Scanner

public class Pares2 {
private static int min;
private static int max;
    public static void main(String[] args) {
        Sonpares();
    }

    public static ArrayList<Integer> Sonpares(){
        Scanner sc = new Scanner(System.in);

        ArrayList resultado = new ArrayList();

        System.out.println("Inserte el numero minimo");
        min = sc.nextInt();
        sc.nextLine();
        System.out.println("Inserte el numero maximo");
        max = sc.nextInt();
        sc.nextLine();

        for(int i = min; i <= max; i++){
            if(i % 2 == 0){
                resultado.add(i);
            }
        }
        System.out.println(resultado);

        return resultado;
    }
}