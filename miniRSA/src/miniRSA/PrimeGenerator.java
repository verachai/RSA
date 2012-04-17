package miniRSA;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class PrimeGenerator {
	
	public static int INDEX = 0;
	
	public static ArrayList<Integer> prime = new ArrayList<Integer>();
	
	/**
	 * Constructor
	 */
	public PrimeGenerator() {
		loadPrimes();
	}
	
	public static void main(String[] args) {

		try {
			FileWriter fileName = new FileWriter("Prime Number List.txt");
			BufferedWriter bufWriter = new BufferedWriter(fileName);
			
			int x = 4;
			int j = 0;
			prime.add(2);
			prime.add(3);
			do {
				for (j = 0; j < prime.size(); j++) {
					if (x % prime.get(j) == 0) break;
				}
				if (j == prime.size()) prime.add(x);
				x++;
				if (x % 100000 == 0)
					System.out.println(x/100000 + "M");
			} while (x <= 2000000);

			for (int p: prime) {
				bufWriter.write(p + "\n");
			}
			bufWriter.close();
		} catch (Exception e) {
			System.out.println("File write error");
		}
	}
	
	
	/**
	 * Return the nth (1-based) prime number; if n exceeds the current number of prime numbers,
	 * an additional 100,000 number of integers will be checked and added into the ArrayList if
	 * it is prime.
	 * @param n the index of the prime number in the ArrayList
	 * @return the nth (1-based) prime number
	 */
	public int get(int n) {
		PrimeGenerator.INDEX = n;
		if (prime.size() >= n) {
			return prime.get(n-1);
		}
		int x = prime.get(prime.size()-1) + 1;
		int counter = 0;
		int j;
		do {
			for (j = 0; j < prime.size(); j++) {
				if (x % prime.get(j) == 0) break;
			}
			if (j == prime.size()) prime.add(x);
			x++;
			counter ++;
		} while (counter <= 100000);
		return get(n);
	}
	
	
	private static void loadPrimes() {
		try {
			FileReader file = new FileReader("Prime Number List.txt");
			BufferedReader reader = new BufferedReader(file);
			
			while (reader.ready()) {
				prime.add(Integer.parseInt(reader.readLine()));
			}
		} catch (Exception e) {
			prime.add(2);
			prime.add(3);
		}
	}
	
}
