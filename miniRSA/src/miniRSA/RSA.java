package miniRSA;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class RSA {

	public static long GCD(long a, long b) {
		long x = a, y = b;
		while(true) {
	        long remainder = x % y;
	        x = y;
	        y = remainder;
	        if (remainder == 0) break;
		}
		return x;
	}
		
	public static long[] complicatedGCD(long a, long b) {
	    long x = GCD(a, b);
	    long y0 = 0, y = 1;
	    long z0 = 1, z = 0;
	    long aa = a, bb = b;
	    while (bb != 0) {
		    long quotient = aa / bb;
		    long temp1 = bb;
		    long temp2 = aa % bb;
		    aa = temp1;
		    bb = temp2;
		    temp1 = y - quotient * y0;
		    temp2 = y0;
		    y0 = temp1;
		    y = temp2;
		    temp1 = z - quotient * z0;
		    temp2 = z0;
		    z0 = temp1;
		    z = temp2;
	    }
    	return new long[]{x, y, z};
	}
	
	public static long coprime(long x) {
		Random rand = new Random();
		while(true) {
			long y = rand.nextInt((int) x);
			if (GCD(x, y) == 1)
				return y;
		}
	}
	
	public static long mod_inverse(long base, long m) {
	    long[] ary = complicatedGCD(base, m);
	    if (ary[0] != 1) return 0;
        if (ary[1] < 0)  ary[1] += m;
	    return ary[1];
	}
	
	private static ArrayList<Long> long2baseTwo(long x) {
	    ArrayList<Long> ret = new ArrayList<Long>();
	    long xx = x;
	    while (xx / 2 != 0) {
	    	ret.add(xx % 2);
	    	xx /= 2;
	    }
	    ret.add(xx);
	    return ret;
	}
	
	public static long modulo(long a, long b, long c) {
	    ArrayList<Long> d2base = long2baseTwo(b);
	    long base = a;
	    long base0 = 1;
	    for (int i = 0; i < d2base.size(); i++) {
	        if (d2base.get(i) == 1) {
	            base0 *= base;
	            base0 = base0 % c;
	        }
	        base = (long) Math.pow(base, 2);
	        base = base % c;
	    }
	    return base0;
	}
	
	public static long totient(long n) {
		if (isPrime(n)) return n-1; 
	
		ArrayList<Long[]> fac = factorize(n);
		
		long firstFactor = fac.get(0)[0];
		if (fac.size() == 1) return n - n / firstFactor;
		
		long secondPart = fac.get(1)[0];
		return totient(secondPart) * totient(n/secondPart);
	}
	
	private static ArrayList<Long[]> factorize(long n) {
		long x = 2;
		long count = 0;
		long m = n;
		ArrayList<Long[]> ret = new ArrayList<Long[]>();
		while (x * x <= n) {
			if (n % x == 0 && isPrime(x)) break;
			x++;
		}
		while ((m % x) == 0) {
			count++;
			m = m / x;
		}
		ret.add(new Long[]{x, count});
		if (m != 1) 
			ret.add(new Long[]{(long) m, (long) 1});
		return ret;
	}
	
	private static boolean isPrime(long n) {
		for (int i = 2; i * i <= n; i++) {
			if (n % i == 0) return false;
		}
		return true;
	}

	public static long endecrypt(long msg_or_cipher, long key, long c) {
		return modulo(msg_or_cipher, key, c);
	}

	public static void main(String[] args) {
		ArrayList<Integer> prime = new ArrayList<Integer>();
		try {
			FileReader file = new FileReader("Prime Number List.txt");
			BufferedReader reader = new BufferedReader(file);
			
			while (reader.ready()) {
				prime.add(Integer.parseInt(reader.readLine()));
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		generateKeys(prime);
//		runEncryption(prime);
//		runDecryption(prime);
//		runCracking(prime);
		
	}
	
	private static void generateKeys(ArrayList<Integer> prime) {
		Scanner read = new Scanner(System.in);

		System.out.println("Enter the nth and mth prime number to compute");
		
		int n1 = read.nextInt();
		int n2 = read.nextInt();
		int a = prime.get(n1);
		int b = prime.get(n2);
		long c = a * b;
		long m = (a - 1) * (b - 1);
		long e = coprime(m);
		long d = mod_inverse(e, m);
		System.out.println(n1 + "th prime = " + a + ", " + n2 + "th prime = " + b);
		System.out.println("c = " + c + ", m = " + m + ", e = " + e + ", d = " + d);
		System.out.println("Public Key = (" + e + ", " + c + ")");
		System.out.println("Private Key = (" + d + ", " + c + ")");
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
	
	private static void runDecryption(ArrayList<Integer> prime) {
		Scanner read = new Scanner(System.in);

		System.out.println("Please enter the private key (d, c): first d, then c");
		int private_d = read.nextInt();
		int private_c = read.nextInt();
		
		while (true) {
			System.out.println("Enter next char cipher value as an int, type quit to quit");
			if (!read.hasNextInt()) break;
			int cipher = read.nextInt();
			long decryption = endecrypt(cipher, private_d, private_c);
			System.out.println((char)decryption + " " + decryption);
		}

	}
	
	private static void runCracking(ArrayList<Integer> prime) {
		Scanner read = new Scanner(System.in);

		System.out.println("Enter the pulic key value (e, c): first e, then c");
		int public_e = read.nextInt();
		int public_c = read.nextInt();
		int aa = 0, bb = 0;
		
		for (int p: prime) {
			if (public_c % p == 0) {
				aa = p;
				break;
			}
		}
		bb = public_c / aa;
		long mm = (aa - 1) * (bb - 1);
		System.out.println("a was " + aa + ", b was " + bb);
		System.out.println("The totient was " + totient(mm));
		long dd = mod_inverse(public_e, mm);
		System.out.println("D was found to be " + dd);
		
		while(true) {
			System.out.println("Enter a number to encrypt/decrypt, or quit to exit");
			if (!read.hasNextInt()) break;
			int num = read.nextInt();
			long result = endecrypt(num, dd, public_c);
			System.out.println("This char decrypted to " + result);
			System.out.println("The letter is " + (char)result);
		}
	}
}
