package sync.sock.server;

import interfaces.SyncronNetwork;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

import msg.NodeMsgData;
import sync.controller.NodeData;
import sync.controller.ServerController;
import jssc.SerialPortException;

public class UDPServerThread extends Thread implements SyncronNetwork {
	ServerController				controller			= ServerController.getInstance();
	// [General]
	// to display time
	private SimpleDateFormat		sdf;

	// [UDP] Socket
	public static Thread			udpThread			= null;
	public static DatagramSocket	udpSocket			= null;
	// ip of server to send to
	public static InetAddress		receiverAddress		= null;
	public static InetAddress		returnIP			= null;
	// 49 bytes in typical formated 12 value analog data string //
	// "0123456789".getBytes();
	public static boolean[]			digiInput			= new boolean[10];
	public static boolean[]			digiOutput			= new boolean[10];

	public static int				UdpOutBufferLength	= 50;
	public static byte[]			UdpOutBuffer		= new byte[UdpOutBufferLength];

	public static int				UdpBufferLength		= 49;
	public static byte[]			UdpBuffer			= new byte[UdpBufferLength];
	public static byte[]			UdpBuffer1			= new byte[UdpBufferLength];
	public static int				udpPort				= 10000;							// arbitrary
	public static int				returnPort			= 10005;							// arbitrary

	public static NodeMsgData		nodeMsgData			= new NodeMsgData();

	public static String			serverIP			= "192.168.1.109";
	public static String			syncronIP			= "192.163.250.179";
	public static String			IP					= syncronIP;
	// UDP Processing
	public static int[]				analogVals			= null;

	// ////////////////////////////////////////////////////////////////////////////////////////////////////
	// [UDP]
	//

	UDPServerThread() {
		super("UDPServerThread");

		sdf = new SimpleDateFormat("HH:mm:ss");
		try {
			initServerUDP();
		} catch (SocketException | UnknownHostException e) {
			e.printStackTrace();
		}
	}


	UDPServerThread(byte[] buffer) {
		this();
		UdpBuffer = buffer;
	}

	public synchronized void run() {
		while (true) {
			try {
				receiveUDP(UdpBuffer);
				// digiOutput = ServerThread.getDigitalOutput();
				// UdpOutBuffer = ( toString(digiOutput)).getBytes();
				// sendUDP(UdpOutBuffer);
			} catch (IOException e) {
				e.printStackTrace();
			}
			// System.out.println("UDP Packet Received");

			processAnalog();

		}
	}

	static String toString(boolean[] out) {
		StringBuffer sb = new StringBuffer();
		for (boolean b : out)
			sb.append(b == true ? "1" : "0");
		return sb.toString();
	}

	public synchronized void processAnalog() throws NumberFormatException {
		long t = System.currentTimeMillis();
		byte[] data;
		String temp = null;
		// try{
		int len = 0;
		StringBuffer sb = new StringBuffer();

		// System.out.println("processing UDP Packet data");
		temp = new String(UdpBuffer, 0, UdpBuffer.length);
		String[] str = temp.toString().replace("<", "").replace(">", "").replace(" ", "").trim().split("_");
		int[] dataInt = new int[12]; // str.length];
		int i = 0;
		for (String s : str) {
			dataInt[i] = Integer.parseInt(s);
			i++;
			if (i > 11) {
				break;
			}
		}
		
//		ServerThread.setAnalogVals(dataInt);
//		ServerThread.setAnalogString(temp);
		String date = sdf.format(new Date());
		// System.out.println("[" + date + "] " + "Received data string: " +
		// temp);
		// System.out.println("processed data: " +
		// SyncronNetwork.toString(ServerThread.getDigitalOutput()));
		// System.out.println("Processing Complete");
		
		//	Send data to controller
		nodeMsgData.analogVals = dataInt.clone();
		temp = null;
		controller.dataHandler.setNodeData(nodeMsgData);
	}

	/*
	 * public synchronized static void StartUdpThread() throws SocketException,
	 * UnknownHostException { udpThread = new Thread{ public void run() {
	 * while(true) { receiveUDP(UdpBuffer); } } }; }
	 */

	public synchronized static void initServerUDP() throws SocketException, UnknownHostException {
		udpSocket = new DatagramSocket(udpPort);
		receiverAddress = InetAddress.getByName(serverIP);
	}

	public synchronized static void initClientUDP() throws SocketException, UnknownHostException {
		udpSocket = new DatagramSocket();
		receiverAddress = InetAddress.getLocalHost();
	}

	public synchronized static void sendUDP(byte[] buf) throws IOException {
		// UdpBuffer = buf.clone();
		if (udpSocket == null) initClientUDP();
		if (returnIP != null) receiverAddress = returnIP;

		/*
		 * //
		 * ///////////////////////////////////////////////////////////////////
		 * ////////// // BufferedReader inLine = new BufferedReader(new
		 * InputStreamReader(System.in)); String input = ""; try { while
		 * (!input.equals("p")) { input = inLine.readLine(); if
		 * (input.equals("set")) { buf[0]=49; buf[1]=49; buf[2]=49; break; } if
		 * (input.equals("reset")) { buf[0]=48; buf[1]=48; buf[2]=48; break; } }
		 * } catch (IOException e3) { e3.printStackTrace(); } //
		 * ////////////////
		 * /////////////////////////////////////////////////////////////
		 */
		DatagramPacket packet = new DatagramPacket(buf, buf.length, returnIP, returnPort);
		udpSocket.send(packet);
	}

	public synchronized static void receiveUDP(byte[] buf) throws IOException {
		if (udpSocket == null) udpSocket = new DatagramSocket(udpPort);
		// byte[] buffer = new byte[10];
		DatagramPacket packet = new DatagramPacket(buf, buf.length);
		udpSocket.receive(packet);
		buf = packet.getData();
		returnIP = packet.getAddress();
		returnPort = packet.getPort();
	}

	// ////////////////////////////////////////////////////////////////////////////////////////////////////
}
