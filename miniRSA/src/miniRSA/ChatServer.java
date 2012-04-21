package miniRSA;

public class ChatServer {
	
	public int portNumber = 8080;
	public static KeyPair keyPair;
	public boolean shouldStop = false;
	Thread disp;
	
	public ChatServer(int port) {
		portNumber = port;
	}

	public void init() {
		// load primes
		RSA.loadPrimes();
		// generate key
		KeyPair pair = RSA.generateKey();
		keyPair = pair;
		
		// Start dispatcher
		disp = new Thread(new Dispatcher(keyPair.pub, keyPair.pri, portNumber));
		disp.start();
		
		try {
			Thread.sleep(1000); //Wait for the dispatcher to be ready
		} catch (InterruptedException e1) {}
	}
	
	public void stopServer() {
		disp.interrupt();
		try {
			disp.join();
		} catch (InterruptedException e) {}
		
		System.out.println("[SYSTEM] ChatServer stopped.");
	}
}
