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

	private static long privateKey;
	public static long publicKey;
	
	private int port;
	/**
	 * Constructor
	 */
	public Dispatcher(long pubkey, long prikey, int port) {
		Dispatcher.privateKey = prikey;
		Dispatcher.publicKey = pubkey;
		this.port = port;
	}
	
	public static void setKey(long pubkey, long prikey) {
		Dispatcher.privateKey = prikey;
		Dispatcher.publicKey = pubkey;
	}
	
	@Override
	public void run() {
		try {
			// Bind to port
			ServerSocket listener = new ServerSocket(port);
			listener.setSoTimeout(1000);
			System.out.println("Server Started: " + listener.toString());
			// Listen and accept
			while(true) {
				try {
					Thread wk;
					(wk = new Thread(new Worker(listener.accept()))).run();
				} catch(SocketTimeoutException e) {
					
				}
				
				if (Thread.interrupted()) {
					System.out.println("Dispatcher Stopped.");
					return;
				}
			}
		} catch (IOException e) {
			System.err.println("Error binding to port " + port + ".");
		}
		
	}

}
