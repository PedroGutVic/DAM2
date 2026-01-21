
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 *
 * @author Pedro Gutiérrez Vico
 */
public class socket {
    public static void main(String[] args) {
        try {
            Enumeration<NetworkInterface> eths = NetworkInterface.getNetworkInterfaces();
            while (eths.hasMoreElements()) {
            NetworkInterface eth =  eths.nextElement();
                if (eth.isUp() && !eth.isLoopback() && !eth.getDisplayName().contains("Virtual")
                && !eth.getDisplayName().contains("Hyper-V") && !eth.getDisplayName().contains("Npcap")
                && !eth.getDisplayName().contains("Virtual")
                ) {
                    System.out.println("Nombre de la interfaz " + eth.getName());
                    System.out.println("Display name " + eth.getDisplayName());

                    Enumeration<InetAddress> ips = eth.getInetAddresses();
                    while (ips.hasMoreElements()) {
                        InetAddress ip=  ips.nextElement();
                        if(ip instanceof Inet4Address){
                            System.out.println("Direccion IP : " + ip.getHostAddress());
                        }
                    }
                    System.out.println("--------------------------------------------------");
                    
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
