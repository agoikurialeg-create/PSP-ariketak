package gaia3_Sareak.ariketa2;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

//LAGUNTZAREKIN EGINDA

public class BezeroaUDP {
    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket()) {
            InetAddress address = InetAddress.getByName("localhost");
            System.out.println("Bezeroa abiarazita dau UDP zerbitzariarengana konektatzeko...");

            // Haria 1: 10 segundotik behin mezua bidaliko deu
            Thread bidaltzaileHaria = new Thread(() -> {
                int kontagailua = 1;
                while (!Thread.currentThread().isInterrupted()) {
                    try {
                    	kontagailua++;
                        String mezua = "Kaixo UDP zerbitzaria! ->" + kontagailua +"º mezua naiz";
                        byte[] bidaltzekoDatuak = mezua.getBytes();
                        DatagramPacket bidaltzekoPaketea = new DatagramPacket(
                                bidaltzekoDatuak,
                                bidaltzekoDatuak.length,
                                address,
                                9000
                        );
                        socket.send(bidaltzekoPaketea);
                        System.out.println("DATAGRAMA bidalita: " + mezua);

                        Thread.sleep(10_000);
                    } catch (IOException e) {
                        System.err.println("Errorea bidaltzean: " + e.getMessage());
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }, "Bidaltzailea");

            // Haria 2: zerbitzaritik datozen datagramak etengabe irakurriko dauz
            Thread jasotzaileHaria = new Thread(() -> {
                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        byte[] jasotzekoDatuak = new byte[1024];
                        DatagramPacket jasotzekoPacket = new DatagramPacket(jasotzekoDatuak, jasotzekoDatuak.length);
                        socket.receive(jasotzekoPacket);

                        String erantzuna = new String(jasotzekoPacket.getData(), 0, jasotzekoPacket.getLength());
                        System.out.println("Zerbitzariaren ERANTZUNA: " + erantzuna);
                    } catch (IOException e) {
                        System.err.println("Errorea jasotzean: " + e.getMessage());
                    }
                }
            }, "Jasotzailea");

            
            bidaltzaileHaria.start();
            jasotzaileHaria.start();

            bidaltzaileHaria.join();
            jasotzaileHaria.join();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
