package ebal1_AzterketaProba.ariketa1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.ProcessBuilder.Redirect;

public class ProzesuNagusia {
	 public static void main(String[] args) {
		 int zenbakia= 0;		 
		 
		 while(true) {
			 zenbakia ++;
			 String zenS =  Integer.toString(zenbakia);
			 System.out.println("NAGUSIA: Bidaliko den zenbakia "+ zenbakia + " da.");
			 
	        try {
	            // Crear los procesos A (que generan resultados)
	            ProcessBuilder pbA = new ProcessBuilder ("java", "-cp", "bin", "ebal1_AzterketaProba.ariketa1.AProzesua", zenS);
	            
	            pbA.inheritIO();
	            pbA.redirectError(Redirect.PIPE); // siempre capturamos resultados finales

	            // A Prozesua abiarazi
	             Process pA = pbA.start();
	             System.out.println("NAGUSIA: A Prozesua abiarazi da.");

	            // B Prozesua
	            ProcessBuilder pbB = new ProcessBuilder("java", "-cp", "bin", "ebal1_AzterketaProba.ariketa1.BProzesua");
	            //pbB.inheritIO();
	            pbB.redirectInput(Redirect.PIPE);
	            
	            
	            Process pB = pbB.start();
	            System.out.println("NAGUSIA: B Prozesua abiarazi da.");

	            // Emaitza jaso (getErrorStream) A Prozesutik
	            BufferedReader errorAReader = new BufferedReader(new InputStreamReader(pA.getErrorStream()));
	            String resultA = errorAReader.readLine();
	            System.out.println("NAGUSIA: A Prozesutik lortu den emaitza: " + resultA);

	            int exitCodeA = pA.waitFor();
	            //System.out.println("NAGUSIA: A Prozesua bukatu da irteera kode honekin: " + exitCodeA);
	            
	            // A Prozesutik lortu dugun emaitza B Prozesura pasatu (bere sarrera estandarraren bidez)
	            OutputStream outputToProcessB = pB.getOutputStream();
	            outputToProcessB.write(resultA.getBytes());  // A Prozesuaren emaitza idatzi
	            outputToProcessB.write("\n".getBytes());  // Lerro bukaera adierazten duen karakterea bidaltzen dela ziurtatu
	            outputToProcessB.flush();  // Ziurtatu datuak bidali direla
	            outputToProcessB.close();  // B Prozesuaren sarrera itxi datu gehiago ez direla egongo adierazteko
	            
	            BufferedReader errorBReader = new BufferedReader(new InputStreamReader(pB.getErrorStream()));
	            String resultB = errorBReader.readLine();
	            System.out.println("NAGUSIA: B Prozesutik lortu den emaitza: " + resultB);

	            // Esperar a que B termine
	            int exitCodeB = pB.waitFor();
	            //System.out.println("NAGUSIA: B Prozesua bukatu da irteera kode honekin: " + exitCodeB);
	            
				
               System.out.println("NAGUSIA: Lo egiten 2s.");
               Thread.sleep(2000);
		           
	        } catch (IOException | InterruptedException e) {
	            e.printStackTrace();
	            
            
	        }
		 }
	    }
}
