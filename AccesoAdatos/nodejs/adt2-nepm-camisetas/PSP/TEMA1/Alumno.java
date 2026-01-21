/**
 * @author Pedro Gutiérrez Vico
 */

import java.util.ArrayList;
import java.util.Scanner; // importación de un paquete externo para poder utilizar la clase Scanner

public class Alumno {
    private String nombre;
    private String apellido;
    private int edad;
    private String curso;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    @Override
    public String toString() {
        return "nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", edad=" + edad +
                ", curso='" + curso + '\'';
    }

    public static void main(String[] args) {

        ArrayList<Alumno> alumnos = new ArrayList<Alumno>();


        Scanner sc = new Scanner(System.in);
        Alumno Alumno1 = new Alumno();
        Alumno1.nombre = "Pedro";
        Alumno1.apellido = "Gutiérrez";
        Alumno1.edad = 19;
        Alumno1.curso = "2º DAM";

        alumnos.add(Alumno1);

        Alumno Alumno2 = new Alumno();
        Alumno2.nombre = "Javier";
        Alumno2.apellido = "Aguilar";
        Alumno2.edad = 19;
        Alumno2.curso = "2º DAM";

        alumnos.add(Alumno2);

        System.out.println("Inserte el numero del alumno ");
        int numeroAlumno=sc.nextInt();
        sc.nextLine();

        System.out.println(alumnos.get(numeroAlumno));

        System.out.println("-----------");


    }

}