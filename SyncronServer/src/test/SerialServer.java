/**
 * 
 */
package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.rmi.server.SocketSecurityException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @author Dawson
 *
 */
public class SerialServer  implements SerialConstants{


	public static String				host				= "192.168.1.109";

	public static int					port				= 5000;


	// Protocal prototype:
	// <syncron:senderName:int actionId:int pinNumber:value>
	// <syncron:node:2:4:200>
	public static String				PROTOCAL_START		= "<";
	public static String				PROTOCAL_END		= ">";
	public static String				PROTOCAL			= "syncron";
	public static String				PROTOCAL_DIVIDER	= ":";


	public static String				protoID				= "node";


	public static int					protoActionId		= -1;
	public static int					protoPinId			= -1;


	public static int					ACT_ANALOG			= 0;
	public static int					ACT_DIGITAL			= 1;

	private static InetAddress	receiverAddress;

	private static DatagramSocket	udpSocket;

	private static byte[]	UdpBuffer;

	//private static int	udpPort;
	public SerialNode.SerialNodeThread	nodeThread			= null;

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		sendUDP();
		ServerSocket socket = null;
		try {
			socket = new ServerSocket(port);
		} catch (IOException e1) {
			e1.printStackTrace();
			System.out.println("[ERROR - " + (new SimpleDateFormat("[MMM-dd HH.mm.ss.SSS]")).format(new Date()) + "] -> [SerialServer::main]TYPE = IOException | VAR = e1");
		}
		while (true) {
			try {
				Socket cliSock = socket.accept();
				SerialServerThread nodeThread = new SerialServerThread(cliSock);
				nodeThread.start();

				synchronized (nodeThread) {

					try {
						nodeThread.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
						System.out.println("[ERROR - " + (new SimpleDateFormat("[MMM-dd HH.mm.ss.SSS]")).format(new Date())
											+ "] -> [SerialServer::main]TYPE = InterruptedException | VAR = e");
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("[ERROR - " + (new SimpleDateFormat("[MMM-dd HH.mm.ss.SSS]")).format(new Date()) + "] -> [SerialServer::main]TYPE = IOException | VAR = e");
			}
		}
	}

	
	
	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// [UDP]
		public synchronized static void initUDP() throws SocketException, UnknownHostException {
			udpSocket = new DatagramSocket();
			receiverAddress = InetAddress.getByName(IP); // getLocalHost();
		}

		public synchronized static void sendUDP( )  {
		 
			
			try {
				if (udpSocket == null) initUDP();
				byte[] cmdBuf = "<syncron:3:1>".getBytes();
				DatagramPacket packet = new DatagramPacket(cmdBuf, cmdBuf.length, receiverAddress, udpInputPort);
				
				
				udpSocket.send(packet);
				
				
			} catch (SocketException e) {
				e.printStackTrace();
				System.out.println("[ERROR - " + (new SimpleDateFormat("[MMM-dd HH.mm.ss.SSS]")).format(new Date()) + "] -> [SerialServer::sendUDP]TYPE = SocketException | VAR = e");
			} catch (UnknownHostException e) {
				e.printStackTrace();
				System.out.println("[ERROR - " + (new SimpleDateFormat("[MMM-dd HH.mm.ss.SSS]")).format(new Date()) + "] -> [SerialServer::sendUDP]TYPE = UnknownHostException | VAR = e");
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("[ERROR - " + (new SimpleDateFormat("[MMM-dd HH.mm.ss.SSS]")).format(new Date()) + "] -> [SerialServer::sendUDP]TYPE = IOException | VAR = e");
			}
		}
	
		
	

	public static class SerialServerThread extends Thread implements SerialConstants {


		public Socket			socket				= null;

		public BufferedReader	bufferedReader;
		public InputStream		inputStream;
		public PrintWriter		printWriter;
		public OutputStream		outputStream;

		public boolean			handshakeComplete	= false;


		/**
		 * 
		 */

		public SerialServerThread(Socket socket) {
			this.socket = socket;
		}


		public void run() {


			try 
				(InputStream inputStream = socket.getInputStream();
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
				OutputStream outputStream = socket.getOutputStream()){
				 
			
//			} catch (UnknownHostException e) {
//				e.printStackTrace();
//				System.out.println("[ERROR - " + (new SimpleDateFormat("[MMM-dd HH.mm.ss.SSS]")).format(new Date())
//									+ "] -> [SerialNodeThread::run]TYPE = UnknownHostException | VAR = e");
//			} catch (IOException e) {
//				e.printStackTrace();
//				System.out.println("[ERROR - " + (new SimpleDateFormat("[MMM-dd HH.mm.ss.SSS]")).format(new Date()) + "] -> [SerialNodeThread::run]TYPE = IOException | VAR = e");
//			}
			printWriter = new PrintWriter(outputStream, true);


			// String msg = "<syncron:node:0:4:200>";


			// printWriter.write(msg);

			String input = "";
			try {
				input = bufferedReader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("[ERROR - " + (new SimpleDateFormat("[MMM-dd HH.mm.ss.SSS]")).format(new Date()) + "] -> [SerialNodeThread::run]TYPE = IOException | VAR = e");
			}


			String[] inputArray = new String[4];
			if (input != null & input.startsWith(PROTOCAL_START)) {
				input.replace("<", "").replace(">", "");
				inputArray = input.split(PROTOCAL_DIVIDER);
			}

			System.out.println(input);
			String msg = "OK";// "<syncron:node:0:4:200>";


			printWriter.println(msg);
		} catch (IOException e1) {
			e1.printStackTrace();
			System.out.println("[ERROR - " + (new SimpleDateFormat("[MMM-dd HH.mm.ss.SSS]")).format(new Date()) + "] -> [SerialServerThread::run]TYPE = IOException | VAR = e1");
		}
		}


	}
	public static class SerialCmdThread extends Thread implements SerialConstants{

		
		public SerialCmdThread(String cmd) {
			strCmd = cmd;
		}
		public String strCmd = "";		
		
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
