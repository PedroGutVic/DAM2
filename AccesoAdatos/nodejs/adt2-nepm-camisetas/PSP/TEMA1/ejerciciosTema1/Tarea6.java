package org.example;

import java.io.*;

public class Tarea6{
    public static void main(String[] args) throws IOException {
        Process process =  Runtime.getRuntime().exec("cmd /c dir "+args[0]);


        BufferedReader brr = new BufferedReader(new InputStreamReader(process.getInputStream()));
        FileWriter fileWriter = new FileWriter("Tarea6.txt");
        BufferedWriter bfw = new BufferedWriter(fileWriter);

        String linea;
        while ((linea = brr.readLine()) != null) {
            bfw.write(linea);
            bfw.newLine();
        }

        bfw.close();
        brr.close();

    }
}