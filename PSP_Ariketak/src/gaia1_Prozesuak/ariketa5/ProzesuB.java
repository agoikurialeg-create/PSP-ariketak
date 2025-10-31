package gaia1_Prozesuak.ariketa5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ProzesuB {
    public static void main(String[] args) {
        try {
            System.out.println("B Prozesua: Emaitzak jasotzen...");

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            long total = 0;
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.isEmpty()) {
                    total += Long.parseLong(line.trim());
                }
            }

            System.out.println("B Prozesua: Azken emaitza da " + total);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}