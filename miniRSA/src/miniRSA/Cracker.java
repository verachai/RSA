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
		
		long dd = crack(public_e, public_c);
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
	
	/**
	 * return d
	 * @param e
	 * @param c
	 * @return
	 */
	public static long crack(long e, long c) {
		if (RSA.primes.isEmpty()) RSA.loadPrimes();

		long public_e = e;
		long public_c = c;
		
		boolean isCracked = false;
		long aa = -1;
		for (int p: RSA.primes) {
			aa = p;
			if (public_c % p == 0) {
				isCracked = true;
				break;
			}
		}
		long dd = -1;
		if (isCracked) {
			long bb = public_c / aa;
			long mm = (aa - 1) * (bb - 1);
			dd = RSA.mod_inverse(public_e, mm);
		} else {
			PrimeGenerator pg = new PrimeGenerator();
			int n = RSA.primes.size();
			while (!isCracked) {
				aa = pg.get(n);
				if (public_c % aa == 0) {
					isCracked = true;
					long bb = public_c / aa;
					long mm = (aa - 1) * (bb - 1);
					dd = RSA.mod_inverse(public_e, mm);
					break;
				}
				n ++;
			}
		}
		return dd;
	}
}
