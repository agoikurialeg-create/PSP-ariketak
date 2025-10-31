package gaia2_Hariak;


public class Sinkronizazioa {
    public static void main(String[] args) throws InterruptedException {

        Kontainer kontainerra = new Kontainer();

        // 1.Haria: segundoak eguneratzen ditu
        Thread eguneratzaileHaria = new Thread(() -> {
            Thread.currentThread().setName("EGUNERATZAILE_HARIA");
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                kontainerra.bidaliMezua(null);
            }
        });

        // 2.Haria: segunduro mezua erakusten du
        Thread segunduHaria = new Thread(() -> {
            Thread.currentThread().setName("SEGUNDU_HARIA");
            while (true) {
                kontainerra.itxaronMezua();
                System.out.println(kontainerra.getSegunduak() + ". segundua");
            }
        });

        // 3.Haria: bost segunduro mezua erakusten du
        Thread bostSegunduHaria = new Thread(() -> {
            Thread.currentThread().setName("BOST_SEGUNDU_HARIA");
            while (true) {
                kontainerra.itxaronMezua();
                int s = kontainerra.getSegunduak();
                if (s % 5 == 0) {
                    System.out.println(s + " segundu pasatu dira");
                }
            }
        });

        // Hariak abiarazi
        eguneratzaileHaria.start();
        segunduHaria.start();
        bostSegunduHaria.start();
    }
}
