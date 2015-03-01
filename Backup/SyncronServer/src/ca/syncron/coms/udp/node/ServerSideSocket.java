package ca.syncron.coms.udp.node;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class ServerSideSocket {

	public static void main(String[] args) {
		ServerSideSocket srv = new ServerSideSocket();
		srv.run();
	}

	// TODO Auto-generated method stub
	public void run() {
		try {
			int serverPort = 4020;
			ServerSocket serverSocket = new ServerSocket(serverPort);
			serverSocket.setSoTimeout(100000);
			while (true) {
				System.out.println("Waiting for client on port "
						+ serverSocket.getLocalPort() + "...");

				Socket server = serverSocket.accept();
				System.out.println("Just connected to "
						+ server.getRemoteSocketAddress());

				PrintWriter toClient = new PrintWriter(
						server.getOutputStream(), true);
				BufferedReader fromClient = new BufferedReader(
						new InputStreamReader(server.getInputStream()));

				String s = "empty";
				BufferedReader cin = new BufferedReader(new InputStreamReader(
						System.in));

			 
					
				String line = null;
				while(server.isConnected() & line != "q"){
					try {
						s = (String) cin.readLine();
						toClient.println(s);
					} catch (IOException e) {
						e.printStackTrace();
					}
//					  line = fromClient.readLine();
//					System.out.println("Server received: " + line);
				}
				toClient.println("Thank you for connecting to "
						+ server.getLocalSocketAddress() + "\nGoodbye!");
			}
		} catch (UnknownHostException ex) {
			ex.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}