package gaia3_Sareak.ariketa2;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

//LAGUNTZAREKIN EGINDA

public class ZerbitzariaUDP {
    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket(9000)) {
            System.out.println("UDP zerbitzaria martxan 9000 portuan...");

            // Gelditu barik jasotzen egon
            while (true) {
                byte[] jasotzekoDatuak = new byte[1024];
                DatagramPacket jasotzekoPacket = new DatagramPacket(jasotzekoDatuak, jasotzekoDatuak.length);

                // Paketea jaso
                socket.receive(jasotzekoPacket);

                // Haria sortu datagrama prozesatzeko
                new Thread(() -> {
                    try {
                        String mezua = new String(jasotzekoPacket.getData(), 0, jasotzekoPacket.getLength());
                        System.out.println("JASOTAKO MEZUA: " + mezua
                                + " (" + jasotzekoPacket.getAddress() + ":" + jasotzekoPacket.getPort() + ")");

                        String erantzuna = mezua.toUpperCase();
                        byte[] bidaltzekoDatuak = erantzuna.getBytes();

                        DatagramPacket bidaltzekoPaketea = new DatagramPacket(
                                bidaltzekoDatuak,
                                bidaltzekoDatuak.length,
                                jasotzekoPacket.getAddress(),
                                jasotzekoPacket.getPort()
                        );
                        socket.send(bidaltzekoPaketea);
                        System.out.println("ERANTZUNA BIDALITA: " + erantzuna);
                    } catch (IOException e) {
                        System.err.println("Errorea bidaltzean/prozesatzean: " + e.getMessage());
                    }
                }, "Prozesatu-" + System.currentTimeMillis()).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
