
import java.util.Random;

/**
 *
 * @author mio
 */
public class cocina {
    final static int NUMERO_COCINEROS = 6;
    private static int aguaFria = 60000; // se pone en cl para que sea mas facil
    private static int aguaCaliente = 30000; // se pone en cl para que sea mas facil
    static Random rm = new Random();
    static boolean quedaAguaCliente = true;
    static boolean quedaAguaFria = true;

    synchronized public static int getAguaFria() {
        return aguaFria;
    }

    synchronized public static int getAguaCaliente() {
        return aguaCaliente;
    }

    public void setAguaFria(int aguafria) {
        this.aguaFria += aguafria;
    }

    public void setAguaCaliente(int aguaCaliente) {
        this.aguaCaliente += aguaCaliente;
    }

    synchronized public int consumirAguaFria() {

        int cantidadAguaEmpleada = rm.nextInt(25, 2000);

        while (aguaFria < 25 || cantidadAguaEmpleada > aguaFria) {
            try {
                quedaAguaFria = false;
                this.wait();
            } catch (InterruptedException e) {

                e.printStackTrace();
            }
        }

        System.out.println("La cantidad de agua fria empleada es de : " + cantidadAguaEmpleada);
        aguaFria -= cantidadAguaEmpleada;
        System.out.println("Queda en el almacen : aguaFria (" + aguaFria + ")" + "aguaCaliente (" + aguaCaliente + ")");
        notifyAll();
        return cantidadAguaEmpleada;
    }

    synchronized public void notificarAtodos() {
        notifyAll();
        System.out.println();
        System.out.println("Llamamos a todos los cocineros");
        System.out.println();
    }

    synchronized public int consumirAguaCaliente() {
        int cantidadAguaEmpleada = rm.nextInt(25, 2000);
        while (aguaCaliente < 25 || cantidadAguaEmpleada > aguaCaliente) {
            try {
                quedaAguaCliente = false;
                this.wait();
            } catch (InterruptedException e) {

                e.printStackTrace();
            }
        }
        System.out.println();
        System.out.println("La cantidad de agua caliente empleada es de : " + cantidadAguaEmpleada);
        System.out.println("Queda en el almacen : aguaFria (" + aguaFria + ")" + "aguaCaliente (" + aguaCaliente + ")");
        aguaCaliente -= cantidadAguaEmpleada;
        notifyAll();
        System.out.println();
        return cantidadAguaEmpleada;

    }

    public static void main(String[] args) {
        cocina kitchen = new cocina();
        asistente as = new asistente(kitchen);
        as.start();
        cocineros[] cocinero = new cocineros[NUMERO_COCINEROS];
        for (int i = 0; i < cocinero.length; i++) {
            cocinero[i] = new cocineros(kitchen);
            cocinero[i].start();
        }

        // intento que al no haber agua ni fria ni caliente me salte un mensaje

        while (true) {
            if (!quedaAguaCliente || !quedaAguaFria) {
                for (int j = 0; j < cocinero.length; j++) {
                    System.out.println();
                    System.out.println("Soy el cocinero " + cocinero[j].getName() + " y e utilizado :");
                    System.out.println("agua Caliente" + cocinero[j].getAguaCalienteUtilizada());
                    System.out.println("agua fria" + cocinero[j].getAguaFriaUtilizada());
                    System.out.println();
                }
            }
        }

    }
}

class cocineros extends Thread {
    Random rm = new Random();
    private int numeroDePlatos = 0;
    private int aguaFriaUtilizada = 0;
    private int aguaCalienteUtilizada = 0;
    private cocina kitchen;

    public cocineros(cocina kitchen2) {
        this.kitchen = kitchen2;
    }

    public int getNumeroDePlatos() {
        return this.numeroDePlatos;
    }

    public void setNumeroDePlatos(int numeroDePlatos) {
        this.numeroDePlatos = numeroDePlatos;
    }

    public int getAguaFriaUtilizada() {
        return this.aguaFriaUtilizada;
    }

    public void setAguaFriaUtilizada(int aguaFriaUtilizada) {
        this.aguaFriaUtilizada = aguaFriaUtilizada;
    }

    public int getAguaCalienteUtilizada() {
        return this.aguaCalienteUtilizada;
    }

    public void setAguaCalienteUtilizada(int aguaCalienteUtilizada) {
        this.aguaCalienteUtilizada = aguaCalienteUtilizada;
    }

    @Override
    public void run() {

        while (true) {
            int random = rm.nextInt(1, 3);
            if (random == 1 && kitchen.quedaAguaFria) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {

                    e.printStackTrace();
                }
                this.aguaFriaUtilizada += kitchen.consumirAguaFria();
                System.out.println("Creando Plato ");
                System.out.println();
                System.out.println();

            } else if (random == 2 && kitchen.quedaAguaCliente) {
                this.aguaCalienteUtilizada += kitchen.consumirAguaCaliente();
            }
        }

    }

}

class asistente extends Thread {
    cocina kitchen;

    public asistente(cocina kitchen2) {
        this.kitchen = kitchen2;
    }

    @Override
    public void run() {

        while (true) {
            synchronized (this) {
                while (kitchen.getAguaFria() < 550 && kitchen.getAguaCaliente() < 250) {
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                // zona de exclusion mutua del asistente
                System.out.println("El asistente va a por agua");
                try {
                    this.sleep(4000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                kitchen.setAguaCaliente(50);
                kitchen.setAguaFria(50);
                System.out.println("Se a añadido agua a los dos taques");
                System.out.println("Agua fria : " + kitchen.getAguaFria());
                System.out.println("Agua caliente : " + kitchen.getAguaCaliente());
                kitchen.notificarAtodos();
                kitchen.quedaAguaCliente = true;
                kitchen.quedaAguaFria = true;
            }
        }

    }

}
