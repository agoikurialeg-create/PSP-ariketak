package gaia1_Prozesuak.ariketa4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BatuketaAbiarazlea {
    public static void main(String[] args) {
        try {
        	String classpath = "bin";

            String[][] tarteak = {
                {"1", "250000000"},
                {"250000001", "500000000"},
                {"500000001", "750000000"},
                {"750000001", "999999999"}
            };

            Process[] prozesuak = new Process[4];

            // 4 prozesuak abiarazi
            for (int i = 0; i < 4; i++) {
                ProcessBuilder pb = new ProcessBuilder("java", "-cp", classpath,
                        "gaia1_ariketa4.Batuketa", tarteak[i][0], tarteak[i][1]);
                pb.redirectErrorStream(true);
                prozesuak[i] = pb.start();
            }

            long baturaFinala = 0;

            // Prozesu bakoitzaren irteera irakurri
            for (int i = 0; i < 4; i++) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(prozesuak[i].getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println("P" + (i+1) + ": " + line);
                    // Batuketaren emaitza lerrotik hartu
                    if (line.contains("Emaitza:")) {
                        String[] zatiak = line.split("Emaitza:");
                        baturaFinala += Long.parseLong(zatiak[1].trim());
                    }
                }
                prozesuak[i].waitFor();
            }

            System.out.println("\n>>> 4 prozesuen BATURA FINALA: " + baturaFinala);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}