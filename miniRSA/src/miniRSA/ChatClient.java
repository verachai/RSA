package miniRSA;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.Socket;
import java.net.UnknownHostException;

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
		if (args.length != 2) {
			System.out.println("Wrong argument format.");
//			System.out.println("ChatClient <serverAddress> <serverPort> <PubKey d> <PubKey c>");
			System.out.println("ChatClient <serverAddress> <serverPort>");
			return;
		}
		try {
			SERVER_ADDRESS = args[0];
			SERVER_PORT = Integer.valueOf(args[1]);
//			PUB_KEY = new PubKey(new BigInteger(args[2]), new BigInteger(args[3]));
		} catch (Exception e) {
			System.out.println("Wrong argument format.");
//			System.out.println("ChatClient <serverAddress> <serverPort> <PubKey d> <PubKey c>");
			System.out.println("ChatClient <serverAddress> <serverPort>");
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
			// general socket and connect
			Socket clientSocket = null;
			PrintWriter out = null;
			try {
				clientSocket = new Socket(SERVER_ADDRESS, SERVER_PORT);
				BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				// read and generate the pub key
				String pub = in.readLine();
				String [] params = pub.split(" ");
				PubKey pk = new PubKey(new BigInteger(params[0]), new BigInteger(params[1]));
				PUB_KEY = pk;
				System.out.println("Receive public key: (" + pk.e + ", " + pk.c + ")");
	            out = new PrintWriter(clientSocket.getOutputStream(), true);
			} catch (UnknownHostException e) {
				System.err.println("Unknown Host.");
			} catch (IOException e) {
				System.err.println("I/O Exception.");
			}
			// Encrypt with the public key
			String cyperText = "";
			for (int i = 0; i < tempLine.length(); ++i) {
				BigInteger cyperLetter = RSA.endecrypt(BigInteger.valueOf(tempLine.charAt(i)), 
												 PUB_KEY.e, PUB_KEY.c);
				cyperText = cyperText + cyperLetter + " ";
			}
			System.out.println();
			System.out.println("Plain Text: " + tempLine);
			System.out.println("Cyper Text: " + cyperText);
			// output the cyperText
			out.write(cyperText);
			System.out.println("Cyper Text sent to " + clientSocket.getRemoteSocketAddress().toString());
			System.out.println();

			try {
				out.close();
				clientSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		
	}

}
