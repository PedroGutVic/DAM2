/**
 * @author Pedro Gutiérrez Vico
 */

import java.io.*;
import java.util.Scanner; // importación de un paquete externo para poder utilizar la clase Scanner

public class generador {

    public static void main(String[] args) throws IOException {
        System.out.println("Comprobar que funciona");

        ProcessBuilder psb1 = new ProcessBuilder("cat" , "alumnos.txt");
        Process p1 = psb1.start();


        BufferedWriter bfw = new BufferedWriter(new FileWriter("nombres.txt",true));

//        File file = new File("archivo.txt");
//        //he intentado utilizar el BufferedReader br = new BufferedReader(new InputStreamReader())
        //pero me da error y no se porque
        try (BufferedReader bf = new BufferedReader(new FileReader("./alumnos.txt"))) {
            String linea;
            while ((linea = bf.readLine()) != null) {
                if(linea.contains(System.in.toString())){

                    Scanner sc = new Scanner(linea);
                    bfw.write(linea.toUpperCase());
                    bfw.flush();
                    System.out.println(linea);
                }else {
                    Scanner sc = new Scanner(linea);
                    bfw.write(linea);
                    bfw.flush();
                    System.out.println(linea);
                }


            }
            System.out.println();

        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }




    }
}