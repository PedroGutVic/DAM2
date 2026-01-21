/*
* Crea un proceso type alumnos.txt y lo manda
* a su flujo de salida.
*  @author Santiago Rodenas Herraiz
 * @version PSP 25/26
 */

import java.io.IOException;
import java.util.Scanner;
/*
 * Desde windows, cambia el comando cat alumnos.txt. Por eso, dije  que
 * utilizárais Ubuntu.
 * En ubuntu.   ProcessBuilder pbCat = new ProcessBuilder("cat alumnos.txt");

 */

public class Generador {
    public static void main(String [] args){
        Scanner scParm = new Scanner(System.in); //Lo utilizaré para leer el apellido
        
        String apellido = scParm.nextLine(); //Aquí tengo el apellido.
        /*
         * Ahora, tengo que lanzar el proceso con el cmd.exe /c type alumnos.txt
         * cmd.exe es el comando
         * /c, significa que al acabar el type, debe cerrarse la terminal (cmd)
         * type alumnos.txt es el parámetro que ejecuta el cmd.exe
         */
        
         //En Ubuntu: ProcessBuilder pbCat = new ProcessBuilder("cat alumnos.txt");

        ProcessBuilder pbCat = new ProcessBuilder("cmd.exe", "/c", "type alumnos.txt");
        
        try{
            Process pCat = pbCat.start();
            Scanner sc = new Scanner(pCat.getInputStream());
            String linea = null;

            while (sc.hasNextLine()){
                linea = sc.nextLine();
                if (linea.contains(apellido))
                    System.out.println(linea.toUpperCase());  //mandamos a la salida standar.
            }
            sc.close();
            pCat.waitFor();

        }catch (IOException e){
            e.printStackTrace();
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        

    }
}
