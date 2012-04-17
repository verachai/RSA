package miniRSA;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.Socket;

/**
 * Class to handle a request from client
 * 
 * @author Yue
 *
 */
public class Worker implements Runnable {
	
	private Socket socket;
	private PriKey privateKey;

	Worker(Socket s, PriKey pri) {
		this.socket = s;
		this.privateKey = pri;
	}
	
	@Override
	public void run() {
		BufferedReader in = null;
		try {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			System.out.println("[SYSTEM] Error getting input stream from socket.");
		}
		
		try {
			String cyperText = in.readLine();
			String [] cyperLetters = cyperText.split(" ");
			System.out.println("[COMMUNICATION] Received message from " + socket.getRemoteSocketAddress().toString());
			System.out.println("[COMMUNICATION] cyper text: " + cyperText);
			System.out.print("[COMMUNICATION] plain text: ");

			for (int i = 0; i < cyperLetters.length; ++i) {
				try {
					if (cyperLetters[i].length() > 0)
						System.out.print((char) RSA.endecrypt(new BigInteger(cyperLetters[i]), 
								  privateKey.d, privateKey.c).intValue());
				} catch (Exception e) {
					
				}
			}
			System.out.println();
			System.out.println();

			socket.close();
			
		} catch (Exception e) {
			System.out.println("[SYSTEM] I/O Error with " + socket.getRemoteSocketAddress().toString());
		}
			

	}

}
