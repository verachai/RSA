package miniRSA;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;

public class PrimeGenerator {

	public static void main(String[] args) {
		ArrayList<Integer> prime = new ArrayList<Integer>();
		prime.add(2);
		prime.add(3);
		
		try {
			FileWriter fileName = new FileWriter("Prime Number List.txt");
			BufferedWriter bufWriter = new BufferedWriter(fileName);
			
			int x = 4;
			int j = 0;
			do {
				for (j = 0; j < prime.size(); j++) {
					if (x % prime.get(j) == 0) break;
				}
				if (j == prime.size()) prime.add(x);
				x++;
				if (x % 1000000 == 0)
					System.out.println(x/1000000 + "M");
			} while (x <= 1000000);

			for (int p: prime) {
				bufWriter.write(p + "\n");
			}
			bufWriter.close();
		} catch (Exception e) {
			System.out.println("File write error");
		}
	}
	
}
