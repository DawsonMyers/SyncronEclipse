/**
 * 
 */
package coms;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import org.json.simple.JSONObject;

import sync.system.SyncUtils;

/**
 * @author Dawson
 *
 */
public  class UdpMessenger implements Runnable, ComConstants {

	public static DatagramSocket	udpSocket		= null;
	public static InetAddress		receiverAddress	= null;
	public static int				UdpBufferLength	= 1024;
	public static byte[]			UdpBuffer		= new byte[UdpBufferLength];
	public static String			jasonString		= "";
	public static String			jasonMsg		= "";
	public static JSONObject		obj				= new JSONObject();
	public static Thread			sendThread		= null;
	public static Thread			listenerThread	= null;
	public static int				portUdp			= 10000;
	public static int				portSend = 10005;


	public UdpMessenger() {}

	// son = (JSONObject)new JSONParser().parse(jason);


	
	
 
	@Override
	public void run() {
		
		try {
								StartUDPListener();
		} catch (IOException e) {
			e.printStackTrace();
			SyncUtils.getDateBox();
		}
		
	}
	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// [UDP]


	public synchronized static void initUDP()    {
		try {
			udpSocket = new DatagramSocket(portSend );
			receiverAddress = InetAddress.getByName(IP); // getLocalHost();
		} catch (SocketException | UnknownHostException e) {
			e.printStackTrace();
			SyncUtils.getDateBox();
		}
	}


	public synchronized static void sendUDP(MsgPacket p)   {
		//byte[] buf = UdpBuffer.clone();
		sendThread = new Thread("UdpSender") {
													public void run() {
														Client c = p.getClient();
														try {
															if (udpSocket == null) initUDP();
															byte[] buf = p.getJasonMsg().getBytes();
															DatagramPacket packet = new DatagramPacket(buf, buf.length, c.getAddress());
															
															DatagramPacket packet1 = new DatagramPacket(buf, buf.length, c.ip.getByName("192.168.1.109"), portUdp);
															packet1.setData(buf);
															udpSocket.send(packet1);
														} catch (IOException e) {
															e.printStackTrace();
															SyncUtils.getDateBox();
														}
													}
		};

		sendThread.start();
	}


	public synchronized static void StartUDPListener() throws IOException {

		DatagramSocket sockListener  = new DatagramSocket(portUdp);
	//	if (sockListener == null) udpSocket = new DatagramSocket(portUdp);
		listenerThread = new Thread("UdpListener") {
													public void run() {
										
														String jasonMsg = "";
														DatagramPacket packet;
														Msg msg = new Msg();
														byte[] buf = UdpBuffer.clone();
														while (true) {
															packet = new DatagramPacket(buf, buf.length);
															try {
																sockListener.receive(packet);
																
																System.out.println("++++++++++PACKET RECEIVED");
																
																
																
															} catch (IOException e) {
																e.printStackTrace();
																SyncUtils.getDateBox();
															}
										
															buf = packet.getData();
															jasonMsg = new String(buf);
										
															System.out.println("jasonMsg = " + jasonMsg);
															msg.setJsonMsg(jasonMsg);
															msg.setPacket(packet);
															System.out.println(packet.getSocketAddress());
															
															UdpHandler.addToQue(msg);
															System.out.println("msg pulled from list = " + (UdpHandler.QueSize()) );
														}
													}
		};
		listenerThread.start();
	}




}
