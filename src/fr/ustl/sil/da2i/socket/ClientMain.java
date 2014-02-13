package fr.ustl.sil.da2i.socket;

public class ClientMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Client client = new Client();
		
		String resultat = client.envoyer(args[0]);
		
		System.out.println(resultat);
	}

}
