import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.SecureRandom;

public class ClientTftp {
    private static final int PORT_CONNECT_TFTP = 44444;
    // private static final String IP_SERVER = "localhost";
    private static final int TAM_MAX_BUFFER = 512;
    private static final String COD_TEXTO = "UTF-8";
    private static final int TIME_MAX_LISTEN = 4000;

    private static String IP_SERVER;
    private static DatagramSocket socket;
    private static InetAddress ipServer;

    private static Integer connectTftp() {
        DatagramPacket sendPacket;
        DatagramPacket receivePacket;

        try {
            socket = new DatagramSocket();
            ipServer = InetAddress.getByName(IP_SERVER);
            socket.setSoTimeout(TIME_MAX_LISTEN);
            byte[] bufferSend = new byte[TAM_MAX_BUFFER];
            byte[] bufferReceive = new byte[TAM_MAX_BUFFER];
            String msg = "Enviando petición de conexión";
            System.arraycopy(msg.getBytes(COD_TEXTO), 0, bufferSend, 0, msg.length());

            try {
                // mandamos petición de conexión....
                sendPacket = new DatagramPacket(bufferSend, bufferSend.length, ipServer, PORT_CONNECT_TFTP);
                socket.send(sendPacket);

                // recibimos nuevo puerto para conectar con el cliente......
                receivePacket = new DatagramPacket(bufferReceive, bufferReceive.length);
                socket.receive(receivePacket);

                // Ahora devolvemos el puerto recibido.
                Integer newPort = Integer
                        .parseInt(new String(receivePacket.getData(), 0, receivePacket.getLength(), COD_TEXTO));
                System.out.println("......Hemos recibido el puerto de conexión " + receivePacket.getAddress()
                        + "al puerto " + newPort);
                System.out.println("....Cerrando conexión, para establecer con nuevo puerto dinámico");

                socket.close(); // cerramos el puerto, porque debemos de abrir otro nuevo.
                return newPort; // devuelvo el nuevo puerto.

            } catch (SocketTimeoutException e) {
                System.out.println("Tiempo agotado.....");
                // return null;
            }
        } catch (Exception e) {
            System.out.println("Error en la creación del socket....");
            // return null;

        } finally {
            if (socket != null && !socket.isClosed())
                socket.close();

        }
        return null;
    }

    private static String generateRandomKey(int length) {
        final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom rnd = new SecureRandom();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        }
        return sb.toString();
    }

    // método, por el que se manda el fichero.
    public static void sendFile(int port , File file) {
        DatagramPacket sendPacket ;
        DatagramPacket receivePacket;

        try {
            socket = new DatagramSocket();
            ipServer = InetAddress.getByName(IP_SERVER);
            socket.setSoTimeout(4000);

            // 1) Enviar nombre del fichero
            byte[] nameBytes = file.getName().getBytes(COD_TEXTO);
            sendPacket = new DatagramPacket(nameBytes, nameBytes.length, ipServer, port);
            socket.send(sendPacket);
            System.out.println("Enviado nombre fichero: " + file.getName());

            // 2) Generar clave aleatoria de 40 caracteres y enviarla
            String key = generateRandomKey(40);
            byte[] keyBytes = key.getBytes(COD_TEXTO);
            sendPacket = new DatagramPacket(keyBytes, keyBytes.length, ipServer, port);
            socket.send(sendPacket);
            System.out.println("Enviada clave (40 chars): " + key);

            // 3) Enviar datos del fichero en múltiples datagramas
            byte[] all = Files.readAllBytes(Paths.get(file.getPath()));
            int offset = 0;
            int chunkSize = TAM_MAX_BUFFER; // usar 512 directamente
            while (offset < all.length) {
                int len = Math.min(chunkSize, all.length - offset);
                byte[] chunk = new byte[len];
                System.arraycopy(all, offset, chunk, 0, len);
                sendPacket = new DatagramPacket(chunk, chunk.length, ipServer, port);
                socket.send(sendPacket);
                offset += len;
            }
            System.out.println("Enviados " + offset + " bytes en " + Math.max(1, (all.length + chunkSize - 1) / chunkSize) + " datagramas.");

            // 4) Enviar datagrama final con la clave para indicar fin de fichero
            sendPacket = new DatagramPacket(keyBytes, keyBytes.length, ipServer, port);
            socket.send(sendPacket);
            System.out.println("Enviado datagrama final con la clave. Transferencia completada.");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (socket != null && !socket.isClosed())
                socket.close();
        }

    }

    public static void main(String[] args) throws IOException {

        if (args.length < 2) {
            System.out.println("Uso: java ClientTftp <server-ip> <file-path>");
            System.exit(-1);
        }

        IP_SERVER = args[0]; // dirección ip del servidor

        Integer portServerTftp = connectTftp();
        if (portServerTftp == null)
            System.exit(-1);

        // hemos recibido el puerto y ahora volvemos a conectar.
        sendFile(portServerTftp, new File(args[1]));

    }

}