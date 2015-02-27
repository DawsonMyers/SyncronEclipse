package sync.main;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

import coms.tcp.node.NodeClientTcp;
import jssc.SerialPort;
import jssc.SerialPortException;
import sync.serial.ArdulinkSerial;
import sync.serial.SerialThread;
import sync.sock.client.SocketClientThread;
import sync.sock.server.ServerThread;

public class RunClient {

	public static Socket			socket;
	public static SerialThread		serialThread;
	public static SocketClientThread	clientThread;
	public static String			serverIP		= "192.168.1.109";
	public static String			syncronIP		= "http://syncron.ca";
	public static int				serverPort	= 6004;
	static Thread					thread;
	public static String			buff;
	public static boolean			isReady		= false;
	static SerialPort				serialPort	= new SerialPort("COM4");
	public static NodeClientTcp		client		= null;
	public static ArdulinkSerial		serial		= null;

	public static void main(String[] args) {

		StartClientServer();

	}

	synchronized static void StartClientServer() {
		(client = new NodeClientTcp()).start();
		(serial= new ArdulinkSerial()).start();
		
//		serialThread = new SerialThread();
//		serialThread.start();
		waitForClientServerQuit();

	}

	synchronized static void waitForClientServerQuit() {// throws
												// InterruptedException,
												// UnknownHostException,
												// IOException,
												// SerialPortException
												// {

		try {
			socket = null; // new Socket(serverIP, serverPort);
			clientThread = new SocketClientThread(socket);
			clientThread.start();
		} catch (Exception e1) {
			e1.printStackTrace();
			System.out.println("[ERROR - " + (new SimpleDateFormat("[MMM-dd HH.mm.ss.SSS]")).format(new Date())
							+ "] -> [RunClient::waitForClientServerQuit]TYPE = Exception | VAR = e1");
		}

		// while(true) readVals();

		try {
			synchronized (clientThread) {
				Thread.sleep(100);
				clientThread.wait();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
			System.out.println("[ERROR - " + (new SimpleDateFormat("[MMM-dd HH.mm.ss.SSS]")).format(new Date())
							+ "] -> [RunClient::waitForClientServerQuit]TYPE = Exception | VAR = e");
		}

	}

	public static void readVals() throws SerialPortException, InterruptedException {
		String temp = null;
		StringBuffer sb = new StringBuffer();

		// serialPort.openPort();
		System.out.println("Port opened: " + serialPort.openPort());
		System.out.println("Params setted: " + serialPort.setParams(115200, 8, 1, 0));

		int inBuffer = serialPort.getInputBufferBytesCount();
		int outBuf = serialPort.getOutputBufferBytesCount();
		// int outBuf = serialPort.;
		serialPort.purgePort(serialPort.PURGE_RXCLEAR | serialPort.PURGE_TXCLEAR);

		while (serialPort.isOpened()) { // /////////////////////////// Loop
									// while port is open
			if (serialPort.getInputBufferBytesCount() > 0) serialPort.readBytes(serialPort.getInputBufferBytesCount());// try
																									// to
																									// clear
																									// buffers
			serialPort.purgePort(serialPort.PURGE_RXCLEAR | serialPort.PURGE_TXCLEAR);
			serialPort.writeString("S");
			// serialPort.writeBytes("S".getBytes());
			Thread.sleep(15);
			if (serialPort.getInputBufferBytesCount() > 0) {
				temp = serialPort.readString();
				// buff = buff == null ? temp : buff + temp;
				// if (buff.contains(">")) isReady = true;
				isReady = true;
				System.out.println(temp);
				String[] str = temp.toString().split("#");
				int[] dataInt = new int[12]; // str.length];
				int i = 0;
				for (String s : str) {
					dataInt[i] = Integer.parseInt(s);
					i++;
					if (i > 11) {
						serialPort.readString(); // try to clear buffers
						break;
					}
				}

				temp = null;
				SocketClientThread.setAnalogVals(dataInt);
				synchronized (clientThread) {
					clientThread.wait();
				}
				// SocketClientThread thread = new
				// SocketClientThread().setAnalogVals(dataInt);
			}
		}
	}
}
