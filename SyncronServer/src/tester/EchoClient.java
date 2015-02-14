/**
 * 
 */
package tester;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import sync.system.SyncUtils;
import coms.ComConstants;

/**
 * @author Dawson
 *
 */
public class EchoClient implements ComConstants{
	public static InetAddress	ipAddress;
	
	
	/**
	 * 
	 */
	public EchoClient() {}
	  static final int BUFFERSIZE = 1024;
	  
	  
	 @SuppressWarnings("resource")
	public static void main(String[] args) {
		 
		 try {
				ipAddress = InetAddress.getByName(IP_SERVER);
			} catch (UnknownHostException e) {
				e.printStackTrace();
				SyncUtils.getDateBox();
			}
		 
		    DatagramSocket sock;
		    DatagramPacket pack = new DatagramPacket(new byte[BUFFERSIZE],
		        BUFFERSIZE, ipAddress, 10000);
		    try {
		      sock = new DatagramSocket(10001);
		    } catch (SocketException e) {
		      System.out.println(e);
		      return;
		    }
		    // echo back everything
		    while (true) {
		      try {
		    	  System.out.println("msg sent");
		        sock.send(pack);
		        System.out.println("msg Received");
		        sock.receive(pack);
		        try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
					SyncUtils.getDateBox();
				}
		      } catch (IOException ioe) {
		        System.out.println(ioe);
		      }
		    }
		  }
}



