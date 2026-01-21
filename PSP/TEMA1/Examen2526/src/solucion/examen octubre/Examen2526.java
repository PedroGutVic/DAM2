import java.io.IOException;
import java.util.Scanner;
import java.io.PrintWriter;

/*
 * La idea, es la misma que vimos en clase.
 * 1.- Creamos los ProccessBuilder, sabiendo que:
 *     - La salida de Productor estará compartida con la del padre (Examen2526).
 *     - Necesito de Generador un OutputStream, para pasarle el apellido leído desde teclado.
 *     - Necesito de Generador un InputStream, para leer todas las líneas filtradas.
 *     - Necesito de Productor, un InputStream para leer todas las líneas tratadas en Productor.
 * 2.- Necesito Lanzar los dos procesos (Generador y Productor) y esperar a que terminen.
 *     - Pasamos el apellido leído por teclado, utilizando el output a Generador.
 *     - Leemos todas las líneas, que me manda Generador a partir de un input.
 *     - Mandamos todas las líneas recibidas de Generador, a Productor a partir de un output.
 *     - Cerramos flujos
 * 
 * Otra idea, sería: ¿Puedo pasar el apellido sin utilizar un Output? La respuesta sería, si.
 *              apellido = teclado.nextLine();
 *              pbGen.command(new String [] {"java" , "Generador", apellido});
 *      Claro está, desde Generador sólo tengo que recoger el apellido con argv[0].
 */

public class Examen2526 {
    
    public static void main(String[] args) {
        
        Scanner teclado = new Scanner(System.in); //Para leer por teclado.
        ProcessBuilder pbGen = new ProcessBuilder(); //Creará Generdor
        ProcessBuilder pbProd = new ProcessBuilder(); //Creará Productor

        //Preparamos los procesos que posteriormente lanzaremos.
        pbGen.command(new String [] {"java" , "Generador"});
        pbProd.command(new String [] {"java", "Productor"});
        pbProd.redirectOutput(ProcessBuilder.Redirect.INHERIT); //hereda la salida del padre.
        try{

            System.out.println( "Introduce el Apellido a filtrar: "); //Pedimos al usuario, el apellido.
            String apellido = teclado.nextLine();
           
            //Lanzamos los procesos
            Process pGen = pbGen.start();   //lanzo Generador
            Process pProd = pbProd.start();   //lanzo Recogedor

            //Primero, necesitamos pasarle a Generador el apellido
            PrintWriter pwParm = new PrintWriter(pGen.getOutputStream());
            pwParm.println(apellido);
            pwParm.flush(); //PARA QUE SE ENVÍE INMEDIATAMENTE.


            /*
            Ahora, necesitamos leer de Generador todas las líneas filtradas
            y mandárselas a Productor. Para ello, necesito un InputStream de
            Generador y un OutputStream de Productor.
            */
            
            Scanner scannerGen = new Scanner (pGen.getInputStream()); //Necesito un input, para leer.
            PrintWriter pwProd = new PrintWriter(pProd.getOutputStream()); //Necesito un output, para escribir.
          
            /*
             * Ahora, leemos todas las líneas hasta null y por cada línea
             * que leemos de Generador, se la mandamos a Productor.
             */
            String linea = null;
            while (scannerGen.hasNextLine()){
                linea = scannerGen.nextLine(); //leemos de generador
                pwProd.println(linea);
                pwProd.flush();  //me aseguro que se envíen inmediatamente.
            }
            pwProd.close();
            scannerGen.close();

            int codGen = pGen.waitFor();
            int codRec = pProd.waitFor();

            String salida = (codGen == 0 && codRec == 0) ? "Todo bien" : "Ejecución errónea";
            System.out.println (salida);

        }catch (IOException e){
            e.printStackTrace();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        
    }
}
