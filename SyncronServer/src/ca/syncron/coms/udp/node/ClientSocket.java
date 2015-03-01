package ca.syncron.coms.udp.node;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import ca.syncron.coms.ComConstants;

public class ClientSocket implements ComConstants {
	String ip = serverIP; //"localhost";//serverIP
	public void run() {
		try {
			int serverPort = 4020;
			InetAddress host = InetAddress.getByName("dawsonmyers.ca");
			System.out.println("Connecting to server on port " + serverPort);

			Socket socket = new Socket(host, serverPort);
			// Socket socket = new Socket("127.0.0.1", serverPort);
			System.out.println("Just connected to "
					+ socket.getRemoteSocketAddress());
			PrintWriter toServer = new PrintWriter(socket.getOutputStream(),
					true);
			BufferedReader fromServer = new BufferedReader(
					new InputStreamReader(socket.getInputStream()));
			toServer.println("Hello from " + socket.getLocalSocketAddress());


			String line = null;
			while(socket.isConnected() & line != "q"){
			line = fromServer.readLine();

			System.out.println("Client received: " + line + " from Server");
			}
			toServer.close();
			fromServer.close();
			socket.close();
		} catch (UnknownHostException ex) {
			ex.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		ClientSocket client = new ClientSocket();
		client.run();
	}
}