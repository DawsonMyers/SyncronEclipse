package sync.main;

import java.sql.Date;
import java.text.SimpleDateFormat;

import sync.controller.ServerController;
import sync.sock.server.ServerThread;

public class RunServer {
	static // server;
long dateStarted;
	static long dateNow;

Date dateLast;
Date date;
ServerController controller = ServerController.getInstance();
private static SimpleDateFormat		sdf;

	RunServer() {

	}

	public static void main(String[] args) throws InterruptedException {
		sdf = new SimpleDateFormat("HH:mm:ss");
		dateStarted = System.currentTimeMillis();
	   StartServer();
		System.out.println("Server thread has terminated");
		// server.start(); <----Started in SeverThread Constructor
		dateNow = System.currentTimeMillis();
		if(dateNow - dateStarted <= 1000) {
			
			System.out.println("ERROR Server thread will not be restarted");
		}else{
			System.out.println("Restarting Server thread");
			StartServer();
		}
			
	
	}

	synchronized static void StartServer() throws InterruptedException {
		waitForServerQuit();
		
		}
		// TODO Auto-generated method stub
		// while(server.isAlive()) {
		// Thread.sleep(100);
		// }

	

	synchronized static void waitForServerQuit() throws InterruptedException {

		ServerThread server = new ServerThread(6005);
		//ServerThread.start

		synchronized (server) {
			Thread.sleep(100);
			server.wait();
		}
	}

}