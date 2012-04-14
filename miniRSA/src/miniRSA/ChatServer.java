package miniRSA;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 
 * @author Yue
 *
 */
public class ChatServer {
	
	public static int portNumber = 8081;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// load primes
		RSA.loadPrimes();
		// generate key
		KeyPair pair = RSA.generateKey();
		// Start dispatcher
		Thread disp;
		(disp = new Thread(new Dispatcher(pair.pub, pair.pri, portNumber))).start();
		
		try {
			Thread.sleep(1000); //Wait for the dispatcher to be ready
		} catch (InterruptedException e1) {}
		
		// interactive command
		BufferedReader sysCommand = null;
		String cmd = null;
		sysCommand = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("[SYSTEM] \"-S\" to Shutdown ChatServer.");
		while (true){
			try {
				cmd = sysCommand.readLine();
			} catch (IOException e) {}
			
			if (cmd.equalsIgnoreCase("-S")){  
				disp.interrupt();
				break;
			} else {
				System.out.println("[SYSTEM] Unrecognized Command.\n ");
			}
		}
		try {
			disp.join();
		} catch (InterruptedException e) {}
		System.out.println("[SYSTEM] ChatServer stopped.");
		
	}

}
