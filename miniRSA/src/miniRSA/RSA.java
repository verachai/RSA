package miniRSA;

import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class RSA {
	
	public static ArrayList<Integer> primes = new ArrayList<Integer>();
	
	public static void loadPrimes() {
		try {
			FileReader file = new FileReader("Prime Number List.txt");
			BufferedReader reader = new BufferedReader(file);
			
			while (reader.ready()) {
				primes.add(Integer.parseInt(reader.readLine()));
			}
			System.out.println("Read " + primes.size() + " primes from file.");
		} catch (Exception e) {
			System.err.println("Error reading prime list.");
		}
	}

	public static BigInteger GCD(BigInteger a, BigInteger b) {
		BigInteger x = a, y = b;
		while(true) {
	        BigInteger remainder = x.remainder(y);
	        x = y;
	        y = remainder;
	        if (remainder == BigInteger.ZERO) break;
		}
		return x;
	}
		
	public static BigInteger[] complicatedGCD(BigInteger a, BigInteger b) {
	    BigInteger x = GCD(a, b);
	    BigInteger y0 = BigInteger.ZERO, y = BigInteger.ONE;
	    BigInteger z0 = BigInteger.ONE, z = BigInteger.ZERO;
	    BigInteger aa = a, bb = b;
	    while (bb.compareTo(BigInteger.ZERO) != 0) {
	    	BigInteger quotient = aa.divide(bb);
	    	BigInteger temp1 = bb;
	    	BigInteger temp2 = aa.remainder(bb);
		    aa = temp1;
		    bb = temp2;
		    temp1 = y.add(quotient.multiply(y0).negate());
		    temp2 = y0;
		    y0 = temp1;
		    y = temp2;
		    temp1 = z.add(quotient.multiply(z0).negate());
		    temp2 = z0;
		    z0 = temp1;
		    z = temp2;
	    }
    	return new BigInteger[]{x, y, z};
	}
	
	public static BigInteger coprime(BigInteger x) {
		BigInteger y;
		do {
			y = new BigInteger(x.bitLength(), new Random());
		} while (y.compareTo(x) != -1 || y.gcd(x).compareTo(BigInteger.valueOf(1)) != 0);
		return y;
	}
	
	public static BigInteger mod_inverse(BigInteger base, BigInteger m) {
		BigInteger[] ary = complicatedGCD(base, m);
	    if (ary[0].compareTo(BigInteger.ONE) != 0) return BigInteger.ZERO;
        if (ary[1].compareTo(BigInteger.ZERO) == -1)  ary[1] = ary[1].add(m);
	    return ary[1];
	}
	
	private static ArrayList<BigInteger> long2baseTwo(BigInteger x) {
	    ArrayList<BigInteger> ret = new ArrayList<BigInteger>();
	    BigInteger xx = x;
	    while (xx.divide(BigInteger.valueOf(2)).compareTo(BigInteger.ZERO) != 0) {
	    	ret.add(xx.remainder(BigInteger.valueOf(2)));
	    	xx = xx.divide(BigInteger.valueOf(2));
	    }
	    ret.add(xx);
	    return ret;
	}
	
	public static BigInteger modulo(BigInteger a, BigInteger b, BigInteger c) {
	    ArrayList<BigInteger> d2base = long2baseTwo(b);
	    BigInteger base = a;
	    BigInteger base0 = BigInteger.ONE;
	    for (int i = 0; i < d2base.size(); i++) {
	        if (d2base.get(i).compareTo(BigInteger.ONE) == 0) {
	            base0 = base0.multiply(base);
	            base0 = base0.remainder(c);
	        }
	        base = base.multiply(base);
	        base = base.remainder(c);
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
			ret.add(new Long[]{m, (long) 1});
		return ret;
	}
	
	private static boolean isPrime(long n) {
		for (long i = 2; i * i <= n; i++) {
			if (n % i == 0) return false;
		}
		return true;
	}

	public static BigInteger endecrypt(BigInteger msg_or_cipher, BigInteger key, BigInteger c) {
		return modulo(msg_or_cipher, key, c);
	}


	public static KeyPair generateKey() {
		
		Scanner read = new Scanner(System.in);

		System.out.println("Enter the nth and mth prime number to compute: ");
		boolean inputValid = false;
		int n1;
		int n2;
		do {
			n1 = read.nextInt();
			n2 = read.nextInt();
			if (n1 >= 1 && n2 >= 1) {
				inputValid = true;
			} else {
				System.out.println("Input invalid. Please try again: ");
			}
		} while (!inputValid);
		int a = 0;
		int b = 0;
		if (n1 > primes.size() || n2 > primes.size()) {
			PrimeGenerator pg = new PrimeGenerator();
			a = pg.get(n1);
			b = pg.get(n2);
		} else {
			a = primes.get(n1 - 1);
			b = primes.get(n2 - 1);
		}
		BigInteger c = BigInteger.valueOf(a).multiply(BigInteger.valueOf(b));
		BigInteger m = BigInteger.valueOf(a-1).multiply(BigInteger.valueOf(b-1));
		BigInteger e = coprime(m);
		BigInteger d = mod_inverse(e, m);
		
		PubKey pub = new PubKey(e, c);
		PriKey pri = new PriKey(d, c);
		KeyPair pr = new KeyPair(pub, pri);
		
		System.out.println(n1 + "th prime = " + a + ", " + n2 + "th prime = " + b);
		System.out.println("c = " + c + ", m = " + m + ", e = " + e + ", d = " + d);
		System.out.println("Public Key = (" + e + ", " + c + ")");
		System.out.println("Private Key = (" + d + ", " + c + ")");
		
		return pr;

	}
	
}
