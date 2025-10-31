package gaia2_Hariak;

/**
 * WAIT eta NOTIFY metodoen erabilera hariak sinkronizatzeko Wait () eta notify
 * () metodoak "synchronized" bloke edo metodo sinkronizatu baten barrutik
 * aipatu behar dira. Kasu honetan, metodoak sinkronizatzeak "itxaronMezua()"
 * eta "bidaliMezua()" ziurtatzen du harien arteko koordinazioa eta baliabide
 * partekatua (mezua) modu seguruan eta kontsistentean erabiltzea.
 * 
 * Javako objektu bakoitzak monitore (edo "giltzarrapo") bat du lotuta,
 * sinkronizazioa kontrolatzeko erabiltzen dena. wait() eta notify() metodoek
 * objektu horren monitorean eragiten dute, eta horrek esan nahi du bi hariek
 * sinkronizatuta egon behar dutela monitore berean (objektu berean) koordinatu
 * ahal izateko.
 * 
 * 
 */

class Kontainer {
    private int segunduak = 0;

    // Hariak itxaronarazten ditu segunduak eguneratu arte
    public synchronized void itxaronMezua() {
        try {
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Segundu kopurua eguneratzen du eta hariak abisatzen ditu
    public synchronized void bidaliMezua(String msg) {
        segunduak++;
        notifyAll();
    }

    // Segundu kopurua lortzeko metodoa
    public synchronized int getSegunduak() {
        return segunduak;
    }
}
