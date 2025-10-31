package gaia1_Prozesuak.ariketa5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.ProcessBuilder.Redirect;

/**
 * Klase honek egingo duena da: A Prozesuak abiarazi eta emaitzak B prozesura bidali
 */
public class ProzesuNagusia {

    public static void main(String[] args) {
        try {
            // Crear los procesos A (que generan resultados)
            ProcessBuilder[] processBuilders = new ProcessBuilder[]{
                new ProcessBuilder("java", "-cp", "bin", "gaia1_ariketa5.ProzesuA", "1", "250000000"),
                new ProcessBuilder("java", "-cp", "bin", "gaia1_ariketa5.ProzesuA", "250000001", "500000000"),
                new ProcessBuilder("java", "-cp", "bin", "gaia1_ariketa5.ProzesuA", "500000001", "750000000"),
                new ProcessBuilder("java", "-cp", "bin", "gaia1_ariketa5.ProzesuA", "750000001", "999999999")
            };

            for (ProcessBuilder pb : processBuilders) {
                pb.redirectError(Redirect.PIPE); // siempre capturamos resultados finales
                // ---------------------------------------------------------
                // OPCIÓN 1: Usar inheritIO para stdout (imprime directamente en consola)
                // pb.inheritIO();
                // ---------------------------------------------------------
                // OPCIÓN 2: Usar stdout con PIPE y leer manualmente (control total)
                pb.redirectOutput(Redirect.PIPE);
            }

            // Lanzar procesos A
            Process[] procesosA = new Process[4];
            for (int i = 0; i < 4; i++) {
                procesosA[i] = processBuilders[i].start();
            }
            System.out.println("NAGUSIA: A prozesuak abiarazi dira.");

            // B Prozesua
            ProcessBuilder pbB = new ProcessBuilder("java", "-cp", "bin", "gaia1_ariketa5.ProzesuB");
            pbB.redirectInput(Redirect.PIPE);
            pbB.redirectOutput(Redirect.INHERIT);
            pbB.redirectError(Redirect.INHERIT);
            Process pB = pbB.start();
            System.out.println("NAGUSIA: B Prozesua abiarazi da.");

            // Array para resultados finales de cada A
            String[] resultados = new String[4];

            for (int i = 0; i < 4; i++) {
                Process p = procesosA[i];
                // OPCIÓN 1
                //pb.inheritIO();
                // OPCIÓN 2
                BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
                
                BufferedReader errReader = new BufferedReader(new InputStreamReader(p.getErrorStream()));
                resultados[i] = errReader.readLine();
            }

            System.out.println("NAGUSIA: Emaitzak jaso dira -> " +
                    resultados[0] + ", " + resultados[1] + ", " +
                    resultados[2] + ", " + resultados[3]);

            // Esperar a que A termine y capturar exit codes
            int[] exitCodes = new int[4];
            for (int i = 0; i < 4; i++) {
                exitCodes[i] = procesosA[i].waitFor();
            }
            System.out.println("NAGUSIA: A Prozesuak bukatu dira irteera kode honekin: " +
                    "1=" + exitCodes[0] + " / 2=" + exitCodes[1] +
                    " / 3=" + exitCodes[2] + " / 4=" + exitCodes[3]);

            // Enviar resultados a B
            try (OutputStream outputToB = pB.getOutputStream()) {
                for (String res : resultados) {
                    outputToB.write((res + "\n").getBytes());
                }
                outputToB.flush();
            }

            // Esperar a que B termine
            int exitCodeB = pB.waitFor();
            System.out.println("NAGUSIA: B Prozesua bukatu da irteera kode honekin: " + exitCodeB);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
