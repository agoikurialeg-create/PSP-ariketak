package gaia1_ariketa3;

import java.io.IOException;
import java.util.Iterator;

public class ItxaronAbiarazlea {
	public static void main(String[] args) {
		try {
			
			// Zehaztu classpath-a ("bin" Eclipse erabiltzean adibidez, moldatu beharrrezkoa bada)
			String classpath = "bin"; // `.` karpeta berdinean bada
			String[] segunduak = args[0].split(",");
			for (String param : segunduak) {

			// Batuketa klasea abiarazi, paketea zehaztuz
			ProcessBuilder processBuilder_1 = new ProcessBuilder("java", "-cp", classpath,
					"gaia1_ariketa3.Itxaron", param);
			

			// Sarrera eta irteera jarauntsi (heredar)
			processBuilder_1.inheritIO();

			// Prozesua abiarazi
			Process process_1 = processBuilder_1.start();

			// Itxaron prozesua bukatu arte
			//int exitCode_1 = process_1.waitFor();
			//System.out.println("\n 1 Prozesua bukatu da irteera kode honekin: " + exitCode_1);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}