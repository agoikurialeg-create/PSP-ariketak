package gaia1_Prozesuak.ariketa5;

public class ProzesuA {
    public static void main(String[] args) {
        try {
            long nanoS = System.nanoTime(); // START TIME
            long pid = ProcessHandle.current().pid();

            long hasiera = Long.parseLong(args[0]);
            long bukaera = Long.parseLong(args[1]);

            System.out.println("A Prozesua: Nire PID da: " + pid + " - Sarrerak: " + hasiera + ", " + bukaera);
            System.out.println("A Prozesua: " + hasiera + " - " + bukaera + " tartea kalkulatzen...");

            long emaitza = 0;
            for (long i = hasiera; i <= bukaera; i++) {
                emaitza += i;
            }

            System.out.println("A Prozesua: Nire PID da: " + pid + " - Emaitza: " + emaitza);

            long nanoE = System.nanoTime(); // END TIME
            double seg = (double) (nanoE - nanoS) / 1_000_000_000.0;
            System.out.printf("A Prozesua: Nire PID da: %d - Prozesuak %.6f segundo tardatu ditu%n", pid, seg);

            System.err.println(emaitza); 

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
