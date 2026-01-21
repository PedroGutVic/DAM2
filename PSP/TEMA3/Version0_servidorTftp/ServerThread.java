import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketTimeoutException;

/*
 * Este hilo, se encarga de crear un socket y ponerlo a la escucha del nuevo puerto.
 */
public class ServerThread extends Thread {
    private Resource r; // Para siguientes versiones.
    private int port;

    private static int TAM_MAX_BUFFER = 512;
    private static String COD_TEXTO = "UTF-8";
    private static final int TIME_MAX_LISTEN = 4000;

    public ServerThread(Resource _r, int portLisen) {
        port = portLisen;
        r = _r;
    }

    @Override
    public void run() {
        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket(port); // escuchamos por el puerto del hilo.
            socket.setSoTimeout(TIME_MAX_LISTEN);
            // Protocolo: 1) nombre fichero, 2) clave de 40 chars, 3) datagramas de datos, 4) datagrama final con la clave

            byte[] bufferReceive = new byte[TAM_MAX_BUFFER];
            try {
                // 1) recibir nombre fichero
                DatagramPacket packet = new DatagramPacket(bufferReceive, bufferReceive.length);
                socket.receive(packet);
                String filename = new String(packet.getData(), 0, packet.getLength(), COD_TEXTO);
                System.out.println("[Port " + port + "] Nombre fichero recibido: " + filename);

                // 2) recibir clave
                packet = new DatagramPacket(bufferReceive, bufferReceive.length);
                socket.receive(packet);
                String key = new String(packet.getData(), 0, packet.getLength(), COD_TEXTO);
                byte[] keyBytes = key.getBytes(COD_TEXTO);
                System.out.println("[Port " + port + "] Clave recibida: " + key);

                // 3) abrir fichero para escritura
                String outName = "recv_" + filename;
                FileOutputStream fos = new FileOutputStream(outName);

                // 4) loop de recepción hasta recibir datagrama con la clave exacta
                while (true) {
                    packet = new DatagramPacket(new byte[TAM_MAX_BUFFER], TAM_MAX_BUFFER);
                    socket.receive(packet);
                    int len = packet.getLength();
                    byte[] data = packet.getData();

                    // comprobar si es el paquete final (misma longitud y contenido igual a la clave)
                    if (len == keyBytes.length) {
                        String s = new String(data, 0, len, COD_TEXTO);
                        if (s.equals(key)) {
                            System.out.println("[Port " + port + "] Paquete final (clave) recibido. Finalizando escritura.");
                            break;
                        }
                    }

                    // si no, escribir los datos al fichero
                    fos.write(data, 0, len);
                }

                fos.close();
                System.out.println("[Port " + port + "] Fichero escrito: " + outName);

            } catch (SocketTimeoutException e) {
                System.out.println("[Port " + port + "] Tiempo de espera agotado ......");
            } catch (IOException e) {
                System.out.println("[Port " + port + "] Error I/O: " + e.getMessage());
            }

        } catch (Exception e) {
            System.out.println("Error inesperado");
        } finally {
            if (socket != null && !socket.isClosed())
                socket.close();
        }
    }
}
