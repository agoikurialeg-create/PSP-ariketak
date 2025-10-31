package ebal1_AzterketaProba.ariketa1;

public class AProzesua {
	 public static void main(String[] args) {
	        try {

	            int zen = Integer.parseInt(args[0]);

	            System.out.println("A Prozesua: Jasotako datua -> " + zen);
	            System.out.println("A Prozesua: Eragiketak egiten...");
	            
	            int emaitza = zen * 2;

	            System.out.println("A Prozesua: Eragiketaren emaitza -> " + emaitza);

	            System.err.println(emaitza); 

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
}
