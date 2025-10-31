package gaia1_Prozesuak.ariketa4;

public class Batuketa {

    public static void main(String[] args) {
    	long nanoS = System.nanoTime(); //START TIME
        long pid = ProcessHandle.current().pid();
        System.out.println("Nire PID da: " + pid + " - Sarrerak: " + args[0] + ", " + args[1]);
        System.out.println("Nire PID da: " + pid + " - Emaitza: " +  batura(args));
        long nanoE = System.nanoTime();//END TIME
        double seg =(double) (nanoE-nanoS)/1_000_000_000.0;//EMAITZA SEGUNDOTAN
		System.out.printf("Prozesuak " + seg + " segundo tardatu ditu");
    }
    public static long batura(String[] args) {
        long emaitza=0;
        for (int i=Integer.valueOf(args[0]);i<=Integer.valueOf(args[1]);i++){
        	emaitza+=i;
        }
        return emaitza; 
    }
}