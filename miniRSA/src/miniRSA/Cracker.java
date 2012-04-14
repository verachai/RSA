package miniRSA;

import java.util.Scanner;

public class Cracker {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (RSA.primes.isEmpty()) RSA.loadPrimes();
		
		Scanner read = new Scanner(System.in);

		System.out.println("Enter the pulic key value (e, c): first e, then c");
		int public_e = read.nextInt();
		int public_c = read.nextInt();
		int aa = 0, bb = 0;
		
		for (int p: RSA.primes) {
			if (public_c % p == 0) {
				aa = p;
				break;
			}
		}
		bb = public_c / aa;
		long mm = (aa - 1) * (bb - 1);
		System.out.println("a was " + aa + ", b was " + bb);
		System.out.println("The totient was " + RSA.totient(mm));
		long dd = RSA.mod_inverse(public_e, mm);
		System.out.println("D was found to be " + dd);
		
		while(true) {
			System.out.println("Enter a number to encrypt/decrypt, or quit to exit");
			if (!read.hasNextInt()) break;
			int num = read.nextInt();
			long result = RSA.endecrypt(num, dd, public_c);
			System.out.println("This char decrypted to " + result);
			System.out.println("The letter is " + (char)result);
		}
	}
}
