/**
 * @author Pedro Gutiérrez Vico
 */

import java.io.File;
import java.io.IOException;
import java.net.http.HttpClient;
import java.util.Scanner; // importación de un paquete externo para poder utilizar la clase Scanner

public class examen2526 {
    public static void main(String[] args) throws IOException, InterruptedException {
        //crear Proceso de generador y productor

        System.out.println("Inserte el Apellido que desea buscar");
        Scanner sc = new Scanner(System.in);
        String Linea= sc.nextLine();

        ProcessBuilder psbgenerador = new ProcessBuilder();
        ProcessBuilder psbProductor = new ProcessBuilder();

        psbgenerador.redirectInput(ProcessBuilder.Redirect.INHERIT);
        psbgenerador.redirectOutput();

        psbgenerador.command("java", "generador");
        psbProductor.command("java", "productor");
        Process p1 =psbgenerador.start();
        Process p2 = psbProductor.start();
        p1.waitFor();
        

    }
}