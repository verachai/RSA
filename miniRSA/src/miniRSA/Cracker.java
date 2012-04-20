package miniRSA;

import java.math.BigInteger;
import java.util.Scanner;

public class Cracker {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (RSA.primes.isEmpty()) RSA.loadPrimes();
		
		Scanner read = new Scanner(System.in);

		System.out.println("Enter the pulic key value (e, c): first e, then c");
		BigInteger public_e = read.nextBigInteger();
		BigInteger public_c = read.nextBigInteger();
		
		BigInteger dd = crack(public_e, public_c);
		System.out.println("d was found to be " + dd);
		
		while(true) {
			System.out.println("\nEnter a number to encrypt/decrypt, or .quit to exit.");
			String s = read.next();
			if (s.equalsIgnoreCase(".quit")) { 
				System.out.println("Cracking finished. Bye!");
				break; 
			}

			while (!s.matches("[0-9]+")) {
				System.out.println("This is not a valid number. Enter a number to encrypt/decrypt, or quit to exit.");
				s = read.next();
			}
			BigInteger num = new BigInteger(s);
			BigInteger result = RSA.endecrypt(num, dd, public_c);
			System.out.println("This char decrypted to " + result);
			System.out.println("The letter is " + (char) result.intValue());
		}
	}
	
	/**
	 * return d
	 * @param e
	 * @param c
	 * @return
	 */
	public static BigInteger crack(BigInteger e, BigInteger c) {
		if (RSA.primes.isEmpty()) RSA.loadPrimes();

		BigInteger public_e = e;
		BigInteger public_c = c;
		
		boolean isCracked = false;
		long aa = -1;
		for (int p: RSA.primes) {
			aa = p;
			if (public_c.mod(BigInteger.valueOf(p)).compareTo(BigInteger.ZERO) == 0) {
				isCracked = true;
				break;
			}
		}
		BigInteger dd = BigInteger.valueOf(-1);
		if (isCracked) {
			long bb = public_c.divide(BigInteger.valueOf(aa)).longValue();
			BigInteger mm = BigInteger.valueOf(aa - 1).multiply(BigInteger.valueOf(bb-1));
			dd = RSA.mod_inverse(public_e, mm);
		} else {
			PrimeGenerator pg = new PrimeGenerator();
			int n = RSA.primes.size();
			while (!isCracked) {
				aa = pg.get(n);
				if (public_c.mod(BigInteger.valueOf(aa)).compareTo(BigInteger.ZERO) == 0) {
					isCracked = true;
					long bb = public_c.divide(BigInteger.valueOf(aa)).longValue();
					BigInteger mm = BigInteger.valueOf(aa - 1).multiply(BigInteger.valueOf(bb-1));
					dd = RSA.mod_inverse(public_e, mm);
					break;
				}
				n++;
			}
		}
		return dd;
	}
}
