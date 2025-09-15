package gaia1_ariketa3;

import java.io.IOException;

public class ItxaronAbiarazlea {
	public static void main(String[] args) {
		try {
			// Zehaztu classpath-a ("bin" Eclipse erabiltzean adibidez, moldatu beharrrezkoa bada)
			String classpath = "bin"; // `.` karpeta berdinean bada

			// Batuketa klasea abiarazi, paketea zehaztuz
			ProcessBuilder processBuilder_1 = new ProcessBuilder("java", "-cp", classpath,
					"prozesuAnitzekoProgramazioa_1.Batuketa", "6", "10");

			// Sarrera eta irteera jarauntsi (heredar)
			processBuilder_1.inheritIO();

			// Prozesua abiarazi
			Process process_1 = processBuilder_1.start();

			// Itxaron prozesua bukatu arte
			int exitCode_1 = process_1.waitFor();
			System.out.println("\n 1 Prozesua bukatu da irteera kode honekin: " + exitCode_1);

		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
}