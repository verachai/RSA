package miniRSA;

import java.util.ArrayList;
import java.util.Scanner;

public class ChatClient {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		

	}

	private static void runEncryption(ArrayList<Integer> prime) {
		Scanner read = new Scanner(System.in);
		
		System.out.println("Enter the pulic key value (e, c): first e, then c");
		int public_e = read.nextInt();
		int public_c = read.nextInt();

		System.out.println("\nPlease enter a sentence to encrypt: ");
		read = new Scanner(System.in);
		String sentence = read.nextLine();
		long[] encryption = new long[sentence.length()];
		for (int i = 0; i < sentence.length(); i++) {
			encryption[i] = endecrypt(sentence.charAt(i), public_e, public_c);
			System.out.println(encryption[i]);
		}
	}
}
