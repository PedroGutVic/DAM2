/*
* Lee de su flujo de entrada
* lo filtra y lo manda a su flujo de salida
*  @author Santiago Rodenas Herraiz
 * @version PSP 25/26
 * 
 * Necesitamos un flujo de entrada: Primera lectura para leer el apellido y el resto para leer todas las líneas.
 * Es tan fácil, como leer el apellido desde su flujo de entrada (se lo manda Examen2526),
 * y a partir de ahí, tendrá que leer todas las líneas hasta null.
 * 
 * ejemplo:
 *           FLUJO ENTRADA  <--|Rodenas|Santigo Rodenas Herraiz|Rodenas López, Laura|....|Gómez Caballero, Pablo
 */

import java.util.Random;
import java.util.Scanner;

public class Productor {
    public static void main(String [] args){

        String linea = null;
        Scanner sc = new Scanner (System.in); //Este flujo es para leer el apellido
        int conta=1;
        Random r = new Random();
        
        while (sc.hasNextLine()){
            linea = sc.nextLine();
            System.out.println(conta + ": " + linea + " - " + r.nextInt(10));
            conta++;

        }
        sc.close();
    }
}