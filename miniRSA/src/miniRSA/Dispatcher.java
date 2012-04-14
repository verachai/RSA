package miniRSA;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

/**
 * The multithreaded server running on a Thread
 * 
 * @author yue
 *
 */
public class Dispatcher implements Runnable {

	private static PriKey privateKey;
	public static PubKey publicKey;
	
	private int port;
	/**
	 * Constructor
	 */
	public Dispatcher(PubKey pubkey, PriKey prikey, int port) {
		Dispatcher.privateKey = prikey;
		Dispatcher.publicKey = pubkey;
		this.port = port;
	}
	
	public static void setKey(PubKey pubkey, PriKey prikey) {
		Dispatcher.privateKey = prikey;
		Dispatcher.publicKey = pubkey;
	}
	
	@Override
	public void run() {
		try {
			// Bind to port
			ServerSocket listener = new ServerSocket(port);
			listener.setSoTimeout(1000);
			System.out.println("[SYSTEM] Server Started: " + listener.toString());
			// Listen and accept
			while(true) {
				try {
					Thread wk;
					(wk = new Thread(new Worker(listener.accept(), privateKey))).start();
				} catch(SocketTimeoutException e) {
					
				}
				
				if (Thread.interrupted()) {
					System.out.println("[SYSTEM] Dispatcher Stopped.");
					return;
				}
			}
		} catch (IOException e) {
			System.err.println("[SYSTEM] Error binding to port " + port + ".");
		}
		
	}

}
