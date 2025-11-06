package ebal1_AzterketaProba.ariketa2;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

// Zerbitzaria UDP bidez
public class Zerbitzaria {
    
    // Eskaera kopurua gordetzeko aldagaia (globala)
    private static int eskaeraKopurua = 0;

    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket(9100)) {
            System.out.println("Zerbitzaria martxan dago (UDP 9100 portuan).");

            // 10 segunduro jasotako eskaera kopurua erakusten duen haria
            Thread kontagailuHaria = new Thread(() -> {
                while (true) {
                    try {
                        Thread.sleep(10000);
                        System.out.println("👉 Guztira jasotako eskaerak: " + eskaeraKopurua);
                    } catch (InterruptedException e) {
                        break;
                    }
                }
            });
            kontagailuHaria.setDaemon(true);
            kontagailuHaria.start();

            // Zerbitzaria etengabe entzuten egongo da
            while (true) {
                byte[] jasotzekoDatuak = new byte[1024];
                DatagramPacket jasotzekoPaketea = new DatagramPacket(jasotzekoDatuak, jasotzekoDatuak.length);
                
                // Mezua jaso
                socket.receive(jasotzekoPaketea);
                eskaeraKopurua++;

                // Mezua testura bihurtu
                String mezua = new String(jasotzekoPaketea.getData(), 0, jasotzekoPaketea.getLength());
                System.out.println("📩 Mezua jaso da: " + mezua);

                // Haria berria eskaera bakoitzeko
                Thread hariEskaera = new Thread(() -> {
                    try {
                        // 6 segundu itxaron
                        Thread.sleep(6000);

                        // Jatorrizko mezuari " JASOTA" gehitu
                        String erantzuna = mezua + " JASOTA";

                        // Erantzuna bidali jatorrizko bezeroari
                        byte[] bidaltzekoDatuak = erantzuna.getBytes();
                        InetAddress bezeroa = jasotzekoPaketea.getAddress();
                        int portua = jasotzekoPaketea.getPort();
                        DatagramPacket bidaltzekoPaketea = new DatagramPacket(
                                bidaltzekoDatuak, bidaltzekoDatuak.length, bezeroa, portua);
                        socket.send(bidaltzekoPaketea);

                        System.out.println("📤 Erantzuna bidalita: " + erantzuna);

                    } catch (Exception e) {
                        System.out.println("Errorea harian: " + e.getMessage());
                    }
                });

                hariEskaera.start(); // hari berria abiarazi
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
