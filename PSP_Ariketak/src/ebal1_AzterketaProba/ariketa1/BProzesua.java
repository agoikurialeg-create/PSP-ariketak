package ebal1_AzterketaProba.ariketa1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BProzesua {
	public static void main(String[] args) {
        try {
        	
            // Erabiltzaileari mezua (irteera estandarra, SYSTEM.OUT)
            System.out.println("B Prozesua: A Prozesuaren emaitza itxaroten...");

            // Emaitza sarrera estandarretik irakurri (SYSTEM.IN)
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String jasotakoEmaitza = reader.readLine();  // Prozesu Nagusiak bidali dion emaitza irakurri
            
            int zen = Integer.parseInt(jasotakoEmaitza);
            int emaitza = zen + 1;
            
            // Erabiltzaileari mezua (irteera estandarra, SYSTEM.OUT)
            System.out.println("B Prozesua: Eragiketaren emaitza -> " + emaitza);
            
            System.err.println(emaitza); 
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
