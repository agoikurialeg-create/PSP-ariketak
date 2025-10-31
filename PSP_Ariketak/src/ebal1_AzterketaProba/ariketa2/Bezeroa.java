package ebal1_AzterketaProba.ariketa2;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Bezeroa {
    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket()) {
            InetAddress address = InetAddress.getByName("localhost");
            System.out.println("Bezeroa abiarazita!");

            Thread bidaliHaria = new Thread(() -> {
                try {
                    int mezuZenbakia = 1;
                    while (!Thread.interrupted() && mezuZenbakia < 11) {
                       
                        String mezua = "Mezu zenbakia: " + mezuZenbakia++;
                        byte[] bidaltzekoDatuak = mezua.getBytes();
                        DatagramPacket bidaltzekoPaketea = new DatagramPacket(
                            bidaltzekoDatuak,
                            bidaltzekoDatuak.length,
                            address,
                            9100
                        );
                        socket.send(bidaltzekoPaketea);
                        System.out.println("Bidalita: " + mezua);

                        Thread.sleep(2000);
                    }
                } catch (IOException | InterruptedException e) {
                    System.out.println("Haria gelditu da: " + e.getMessage());
                }
            });

            bidaliHaria.start();

            while (true) {
                byte[] jasotzekoDatuak = new byte[1024];
                DatagramPacket jasotzekoPacket = new DatagramPacket(jasotzekoDatuak, jasotzekoDatuak.length);
                socket.receive(jasotzekoPacket);
                
                String erantzuna = new String(jasotzekoPacket.getData(), 0, jasotzekoPacket.getLength());
                System.out.println("Zerbitzariaren erantzuna: " + erantzuna);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}