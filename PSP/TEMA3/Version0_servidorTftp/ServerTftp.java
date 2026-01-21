import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;

public class ServerTftp {
    private static final int PORT_CONNECT_TFTP = 44445;
    private static final String IP_SERVER = "localhost";
    private static final int TAM_MAX_BUFFER = 512;
    private static final String COD_TEXTO = "UTF-8";
    private static final int TIME_MAX_LISTEN = 10000;

    private static DatagramSocket socket;
    private static InetAddress ipServer;

    public static void main(String[] args) {

        Resource r = new Resource(); // simulamos recurso compartido.
        DatagramPacket sendPacket;
        DatagramPacket receivePacket;

        try { // Sólo me encargo de comprobar errores en el socket.
            socket = new DatagramSocket(PORT_CONNECT_TFTP); // Como las arañas, escuchamos desde el puerto.
            socket.setSoTimeout(TIME_MAX_LISTEN);

            while (!r.endClient()) {
                try { // Errores en el envío/recepción de datagramas.
                    byte[] bufferReceiver = new byte[TAM_MAX_BUFFER];
                    DatagramPacket packetReceiver = new DatagramPacket(bufferReceiver, bufferReceiver.length);
                    socket.receive(packetReceiver);
                    InetAddress ipClient = packetReceiver.getAddress();

                    String msg = new String(packetReceiver.getData(), 0, packetReceiver.getLength(), COD_TEXTO);
                    System.out.println("Cliente con ip " + ipClient.toString() + msg);

                    // ahora toca mandale el nuevo puerto de conexión con el hilo. Simulamos iun
                    // puerto diferente.
                    int port = r.devPort(); // asignamos un posible puerto.
                    if (port != -1) {
                        byte[] bufferSend;
                        bufferSend = String.valueOf(port).getBytes(COD_TEXTO); // mandamos sólo el puerto.
                        sendPacket = new DatagramPacket(bufferSend, bufferSend.length, ipClient,
                                packetReceiver.getPort());
                        socket.send(sendPacket);
                        // Hemos mandado el nuevo puerto al cliente y lo sirve un hilo.

                        new ServerThread(r, port).start();

                    }
                } // fin try
                catch (SocketTimeoutException e) {
                    System.out.println("...... Continuamos a la escucha de clientes desde " + IP_SERVER
                            + "en el puerto " + PORT_CONNECT_TFTP); // Hay que quitarlo, sino finaliza
                }
            } // fin while

        } // fin try creación socket.
        catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (socket != null && !socket.isClosed()) {
                System.out.println("Cerrando socket...");
                socket.close();
            }

        }

    }
}
