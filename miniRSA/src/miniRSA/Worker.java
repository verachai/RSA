package miniRSA;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Class to handle a request from client
 * 
 * @author yue
 *
 */
public class Worker implements Runnable {
	
	private Socket socket;

	Worker(Socket s) {
		this.socket = s;
	}
	
	@Override
	public void run() {
		BufferedReader in = null;
		try {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			System.out.println("Error getting input stream from socket.");
		}
		while (socket.isConnected()) {
			try {
				String cyperText = in.readLine();
			} catch (IOException e) {
				System.out.println("I/O Error with " + socket.getRemoteSocketAddress().toString());
			}
			
		}

	}

}
