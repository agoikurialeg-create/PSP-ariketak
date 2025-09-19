package gaia1_ariketa3;

public class Itxaron {

	public static void main(String[] args) throws NumberFormatException, InterruptedException {
		long pid = ProcessHandle.current().pid();
		System.out.println("Nire PID da: " + pid + " - Sarrerak: " + args[0]);
		Thread.sleep(Integer.valueOf(args[0])*1000);
		System.out.println("Nire PID da: " + pid + " - Bukatu da");
	}

}
