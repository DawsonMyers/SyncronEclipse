/**
 * 
 */
package tester;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * @author Dawson
 *
 */
public class EchoUdpServer {

	/**
	 * 
	 */
	public EchoUdpServer() {}

	static final int	BUFFERSIZE	= 1024;

	public static void main(String[] args) {
		DatagramSocket sock;
		DatagramPacket pack = new DatagramPacket(new byte[BUFFERSIZE], BUFFERSIZE);
		try {
			sock = new DatagramSocket(10000);
		} catch (SocketException e) {
			System.out.println(e);
			return;
		}
		System.out.println("Echo Server running on port: "  + 10000);
		// echo back everything
		while (true) {
			try {
				sock.receive(pack);
				System.out.println("msg Received");
				sock.send(pack);
				System.out.println("msg echoed");
			} catch (IOException ioe) {
				System.out.println(ioe);
			}
		}
	}

}



