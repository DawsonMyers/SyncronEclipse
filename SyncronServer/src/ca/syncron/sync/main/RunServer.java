package ca.syncron.sync.main;

import java.text.SimpleDateFormat;
import java.util.Date;

import ca.syncron.coms.tcp.server.ServerTcp;
import ca.syncron.coms.udp.server.UdpServerHandler;
import ca.syncron.msg.MessageServerThread;
import ca.syncron.sync.controller.ServerController;

public class RunServer {
	static// server;
	long							dateStarted;
	static long					dateNow;
	public static int				UdpBufferLength	= 49;
	public static byte[]			UdpBuffer			= new byte[UdpBufferLength];
	Date							dateLast;
	Date							date;
	ServerController				controller		= ServerController.getInstance();
	private static SimpleDateFormat	sdf;
	public static ServerTcp			mServer = null;
	public static UdpServerHandler	udpServerHandler	= null;

	RunServer() {

	}

	public static void main(String[] args) {
		sdf = new SimpleDateFormat("HH:mm:ss");
		dateStarted = System.currentTimeMillis();
		StartServer();
		System.out.println("Server thread has terminated");
		// server.start(); <----Started in SeverThread Constructor
		dateNow = System.currentTimeMillis();
		if (dateNow - dateStarted <= 1000) {

			System.out.println("ERROR Server thread will not be restarted");
		} else {
			System.out.println("Restarting Server thread");
			StartServer();
		}

	}

	synchronized static void StartServer() {
		waitForServerQuit();

	}

	// TODO Auto-generated method stub
	// while(server.isAlive()) {
	// Thread.sleep(100);
	// }

	synchronized static void waitForServerQuit() {
		try {

			(mServer = new ServerTcp()).start(); 
			udpServerHandler = new UdpServerHandler();
			udpServerHandler.start();

			MessageServerThread msgServer = null;
			try {
				msgServer = new MessageServerThread();
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("[ERROR - " + (new SimpleDateFormat("HH:mm:ss")).format(new Date()) + "] -> [RunServer::waitForServerQuit]TYPE = Exception | VAR = e");
			}

			try {
//				UDPServerThread udpThread = new UDPServerThread(UdpBuffer);
//				udpThread.start();
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("[ERROR - " + (new SimpleDateFormat("HH:mm:ss")).format(new Date()) + "] -> [RunServer::waitForServerQuit]TYPE = Exception | VAR = e");
			}

			// ServerThread server = new ServerThread(6005);
			// ServerThread.start

			synchronized (msgServer) {
				// Thread.sleep(100);
				msgServer.wait();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
			System.out.println("[ERROR - " + (new SimpleDateFormat("HH:mm:ss")).format(new Date()) + "] -> [RunServer::waitForServerQuit]TYPE = InterruptedException | VAR = e");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("[ERROR - " + (new SimpleDateFormat("HH:mm:ss")).format(new Date()) + "] -> [RunServer::waitForServerQuit]TYPE = Exception | VAR = e");
		}
	}

}
