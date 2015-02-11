/**
 * 
 */
package test;

import interfaces.SyncronNetwork;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

import sync.serial.ArdulinkSerial;

/**
 * @author Dawson
 *
 */
public class SerialNode implements SerialConstants {
	public static Thread			udpListenerThread		= null;
	public static DatagramSocket	udpSocket				= null;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		startUdpListener();
		
		SerialNodeThread node = new SerialNodeThread();
		node.start();
		
		synchronized (node) {
			try {
				node.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
				System.out.println("[ERROR - " + (new SimpleDateFormat("[MMM-dd HH.mm.ss.SSS]")).format(new Date()) + "] -> [SerialServer::main]TYPE = InterruptedException | VAR = e");
			}
		}
		

	}
	
	public synchronized static String receiveUDP(byte[] buf) throws IOException {
		if (udpSocket == null) udpSocket = new DatagramSocket(udpInputPort);
		// byte[] buffer = new byte[10];
		
		DatagramPacket packet = new DatagramPacket(buf, buf.length);
		udpSocket.receive(packet);
		buf = packet.getData();
		return UdpInputBufferString = new String(buf);
	}
	
	public static byte[]			UdpBuffer				= new byte[UdpBufferLength];
	public static int				UdpInputBufferLength	= 20;
	public static byte[]			UdpInputBuffer			= new byte[UdpInputBufferLength];
	public static String			UdpInputBufferString	= new String(UdpInputBuffer);

	
	private static void startUdpListener() {
		udpListenerThread = new Thread("UdpListenerThread") {
			public void run() {
				String cmd = "";
				while (true) {
					try {

						cmd = receiveUDP(UdpInputBuffer);
					} catch (IOException e) {
						e.printStackTrace();
					}
					
					String str = cmd.substring(1, cmd.indexOf(">") );
					str.replace("<", "").replace(">", "");
					
					
					
					String[] cmdArray = new String[3];
					
					if (cmd != null & cmd.startsWith(PROTOCAL_START)) {
						cmdArray = cmd.split(PROTOCAL_CMD_DIVIDER);
					}
					
					ArdulinkSerial.setPin(Integer.parseInt(cmdArray[1]), Integer.parseInt(cmdArray[2]));
					
				}
			}
		};
		udpListenerThread.start();
	}
	
	
	
	public static class SerialNodeThread extends Thread implements SerialConstants{
		
		
		private Socket socket = null;

		private BufferedReader bufferedReader;
		private InputStream inputStream;
		private PrintWriter printWriter;
		private OutputStream outputStream;
		
		public boolean handshakeComplete = false;
		
		
		//	Protocal prototype:
		//	<syncron:senderName:int actionId:int pinNumber:value>
		//	<syncron:node:2:4:200>

		
 
		
		
		public static final String protoID = "node";
		
		
		public static int protoActionId = -1;
		public static int protoPinId = -1;
		
		
		/**
		 * 
		 */
		public SerialNodeThread() {}
		
		
		public void run() {
			
			
			try {
				socket = new Socket(IP, PORT_CMD);

				inputStream = socket.getInputStream();
				bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
				outputStream = socket.getOutputStream();
			} catch (UnknownHostException e) {
				e.printStackTrace();
				System.out.println("[ERROR - " + (new SimpleDateFormat("[MMM-dd HH.mm.ss.SSS]")).format(new Date()) + "] -> [SerialNodeThread::run]TYPE = UnknownHostException | VAR = e");
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("[ERROR - " + (new SimpleDateFormat("[MMM-dd HH.mm.ss.SSS]")).format(new Date()) + "] -> [SerialNodeThread::run]TYPE = IOException | VAR = e");
			}
			printWriter = new PrintWriter(outputStream, true);
			
			
			String msg = "<syncron:node:0:4:200>";
			
			
			printWriter.println(msg);
			
			String input = "";
			try {
				input = bufferedReader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("[ERROR - " + (new SimpleDateFormat("[MMM-dd HH.mm.ss.SSS]")).format(new Date()) + "] -> [SerialNodeThread::run]TYPE = IOException | VAR = e");
			}
			
			
			String[] inputArray = new String[4];
			if (input != null & input.startsWith(PROTOCAL_START)) {
				inputArray = input.split(PROTOCAL_CMD_DIVIDER);
			}
		}
		
		
		
	}
	
	public static class SerialCmdThread extends Thread implements SerialConstants{

		
public SerialCmdThread() {}
		
private Socket socket = null;

private BufferedReader bufferedReader;
private InputStream inputStream;
private PrintWriter printWriter;
private OutputStream outputStream;


 
		public void run() {
			
			
			try {
				socket = new Socket(IP, PORT_CMD);

				inputStream = socket.getInputStream();
				bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
				outputStream = socket.getOutputStream();
			} catch (UnknownHostException e) {
				e.printStackTrace();
				System.out.println("[ERROR - " + (new SimpleDateFormat("[MMM-dd HH.mm.ss.SSS]")).format(new Date()) + "] -> [SerialNodeThread::run]TYPE = UnknownHostException | VAR = e");
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("[ERROR - " + (new SimpleDateFormat("[MMM-dd HH.mm.ss.SSS]")).format(new Date()) + "] -> [SerialNodeThread::run]TYPE = IOException | VAR = e");
			}
			printWriter = new PrintWriter(outputStream, true);
			
			//							dPin	state
			//		<syncron:node:0:1>
			String msg = "<syncron:node:0:4:200>";
			String[] cmdArray = new String[3];
			
			
			String input = "";
			
			try {
				input = bufferedReader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("[ERROR - " + (new SimpleDateFormat("[MMM-dd HH.mm.ss.SSS]")).format(new Date()) + "] -> [SerialNodeThread::run]TYPE = IOException | VAR = e");
			}
			
			printWriter.println(msg);

			String[] inputArray = new String[4];
			if (input != null & input.startsWith(PROTOCAL_START)) {
				input.replace("<", "").replace(">", "");
				inputArray = input.split(PROTOCAL_CMD_DIVIDER);
			}
		}
}
}


