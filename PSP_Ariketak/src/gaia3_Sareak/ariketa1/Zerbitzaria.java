package gaia3_Sareak.ariketa1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Zerbitzaria — bezero hariekin.
 * Bezero bakoitzak izena bidaltzen du eta konexioa irekita mantentzen da.
 * Zerbitzariak 5 segunduro erakusten du zenbat bezero konektatuta dauden.
 */
public class Zerbitzaria {
    
    // Bezero aktibo guztiak (hari seguru)
    private static final List<BezeroKudeatzailea> bezeroak = new CopyOnWriteArrayList<>();

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(5000)) {
            System.out.println("Zerbitzaria 5000 portuan entzuten...");

            // Hari bat estatistika erakusteko 5 segunduro
            new Thread(() -> monitorizatuBezeroak()).start();

            // Bezero berriak onartu
            while (true) {
                Socket socket = serverSocket.accept();
                BezeroKudeatzailea kudeatzailea = new BezeroKudeatzailea(socket);
                bezeroak.add(kudeatzailea);
                new Thread(kudeatzailea).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 5 segunduro inprimatzen du zenbat bezero konektatuta dauden eta beraien izenak.
     */
    private static void monitorizatuBezeroak() {
        while (true) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                return;
            }

            System.out.println("\n Bezero kopurua: " + bezeroak.size());
            if (bezeroak.isEmpty()) {
                System.out.println("Ez dago bezero aktiborik.");
            } else {
                System.out.println("Bezeroak konektatuta:");
                for (BezeroKudeatzailea b : bezeroak) {
                    System.out.println("   - " + b.getIzena());
                }
            }
        }
    }

    /**
     * Bezero bakoitzaren komunikazioa kudeatzen duen klasea.
     */
    private static class BezeroKudeatzailea implements Runnable {
        private Socket socket;
        private String izena = "Ezezaguna";

        public BezeroKudeatzailea(Socket socket) {
            this.socket = socket;
        }

        public String getIzena() {
            return izena;
        }

        @Override
        public void run() {
            try (BufferedReader sarrera = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter irteera = new PrintWriter(socket.getOutputStream(), true)) {

                // Lehenengo lerroan jasoko dugu bezeroaren izena
                izena = sarrera.readLine();
                System.out.println("Konexioa jaso da bezeroarengandik: " + izena);

                // Konexioa irekita mantendu (bezeroaren mezuak irakurtzeko)
                String lerroa;
                while ((lerroa = sarrera.readLine()) != null) {
                    System.out.println("Jasotako mezua [" + izena + "] " + lerroa);
                    irteera.println("Jasota: " + lerroa.toUpperCase());
                }

            } catch (IOException e) {
                System.err.println( izena + " deskonektatu da: " + e.getMessage());
            } finally {
                bezeroak.remove(this);
            }
        }
    }
}