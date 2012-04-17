package miniRSA;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import java.util.ArrayList;
import java.util.Scanner;

public class ChatClient {
	
	public static String SERVER_ADDRESS;
	public static int SERVER_PORT;
	public static PubKey PUB_KEY;

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// parse parameters
		if (args.length != 4) {
			System.out.println("Wrong argument format.");
			System.out.println("ChatClient <serverAddress> <serverPort> <PubKey d> <PubKey c>");
			return;
		}
		try {
			SERVER_ADDRESS = args[0];
			SERVER_PORT = Integer.valueOf(args[1]);
			PUB_KEY = new PubKey(Long.valueOf(args[2]), Long.valueOf(args[3]));
		} catch (Exception e) {
			System.out.println("Wrong argument format.");
			System.out.println("ChatClient <serverAddress> <serverPort> <PubKey d> <PubKey c>");
			return;
		}

		while (true) {
			// Read input and send
			Scanner read = new Scanner(System.in);
			System.out.println("Enter the text, \".bye\" to quit: ");
			String tempLine = read.nextLine();
			if (tempLine.equalsIgnoreCase(".bye")) {
				read.close();
				return;
			}
			String cyperText = "";
			for (int i = 0; i < tempLine.length(); ++i) {
				long cyperLetter = RSA.endecrypt(Long.valueOf(tempLine.charAt(i)), PUB_KEY.e, PUB_KEY.c);
				cyperText = cyperText + String.valueOf(cyperLetter) + " ";
			}
			
			// general socket and connect
			Socket clientSocket = null;
			PrintWriter out = null;
			try {
				clientSocket = new Socket(SERVER_ADDRESS, SERVER_PORT);
	            out = new PrintWriter(clientSocket.getOutputStream(), true);
			} catch (UnknownHostException e) {
				System.err.println("Unknown Host.");
			} catch (IOException e) {
				System.err.println("I/O Exception.");
			}
			
			System.out.println("Plain Text: " + tempLine);
			System.out.println("Cyper Text: " + cyperText);
			out.write(cyperText);
			System.out.println("Cyper Text sent to " + clientSocket.getRemoteSocketAddress().toString());
			System.out.println();

			try {
				out.close();
				clientSocket.close();
			} catch (IOException e) {
			}

		}
		
	}

}
