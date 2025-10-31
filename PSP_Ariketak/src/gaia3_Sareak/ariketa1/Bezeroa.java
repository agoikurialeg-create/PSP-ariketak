package gaia3_Sareak.ariketa1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Klase hau ikasleen zerbitzarira konektatzen da (5000 portuan adibidez),
 * bere izena bidaltzen du eta konexioa irekita mantentzen du.
 */
public class Bezeroa {
    public static void main(String[] args) {
        // Aldatu zure izenarekin
        String nireIzena = "Ane";  

        // Zure ikaskideen IP helbideak (edo localhost probetan)
        String[] zerbitzariak = {
            "192.168.36.88", //Asier
            "192.168.36.169", //Jon
            "192.168.36.101", //Aritz
            "192.168.36.129" //Unatz
        };

        // Zerbitzari bakoitzera konektatu haria erabiliz
        for (String ip : zerbitzariak) {
            new Thread(() -> konektatuZerbitzarira(ip, 5000, nireIzena)).start();
        }
    }

    private static void konektatuZerbitzarira(String ip, int portua, String izena) {
        try (Socket socket = new Socket(ip, portua)) {
            System.out.println("Konektatu naiz zerbitzarira: " + ip);

            PrintWriter irteera = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader sarrera = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));

            // Bidali izena zerbitzariari
            irteera.println(izena);
            System.out.println("Bidalitako mezua (" + ip + "): " + izena);

            // Konexioa irekita mantendu eta zerbitzariaren mezuak entzun
            String lerroa;
            while ((lerroa = sarrera.readLine()) != null) {
                System.out.println("Jasotako mezua " + ip + " --> " + lerroa);
            }

        } catch (IOException e) {
            System.err.println("Ezin izan da konektatu " + ip + ": " + e.getMessage());
        }
    }
}
