package msg;

import java.net.Socket;

import socketmsg.ObjectMessengerBase;
import java.util.concurrent.locks.Lock;

public class ObjectMessengerThread extends ObjectMessengerBase implements MsgConstants{
	// MessageWrapper msg = new MessageWrapper();
	public boolean mode = false;
	public String ip = "192.168.1.109";
	public int serverPort = 6005;
	public Socket socket;
	public ObjLock objLock = new ObjLock();

	// public ObjLock objLock = new ObjLock();

	// Constructors
	// ///////////////////////////////////////////////////////////////////////////////////
	public ObjectMessengerThread() {
		super();

	}

	public ObjectMessengerThread(Socket socket) {
		// super(socket);
		this();
		this.socket = socket;
		start();
	}

	public ObjectMessengerThread(String ip, int port) {
		// super(ip, port);
		this();
	}

	// Send/receive message
	// ///////////////////////////////////////////////////////////////////////////////////
	public MessageWrapper sendReqest(MessageWrapper msg,
			String ip, int port) {
		this.ip = ip;
		this.serverPort = port;
		this.msg = msg;
		this.start();
		synchronized (objLock) {
			try {
				// this.sleep(20);
				objLock.wait();
				os.close();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return this.msg;
	}

	//	Send with only msg
	public void sendReqest(MessageWrapper msg) {
		this.ip = msg.IP;
		this.serverPort = msg.PORT_SERVER;
		this.msg = msg;
		this.start();
	}
	
	// Run
	// ///////////////////////////////////////////////////////////////////////////////////
	// either send-receive (client) or receive-send (server)
	public synchronized void run() {
		if (socket == null) {
			//	client request
			
			os.connectClient(ip, serverPort);
			//MessageWrapper msg = new MessageWrapper();
			//msg.mStatus = 0;
			timer.start();
			sendMessage(this.msg);
			msg = readMessage();
			timer.finish(msgTime);
			timer.print();
			System.out.println("Message #" + msg.mStatus);
		} else {
			//	server response
			os.connectServer(socket);
			msg = os.readMessage();
			if(msg.getQuery().length() < 5) msg.setRequestId(STREAM); 
			MsgResponseHandler msgHandler = new MsgResponseHandler(msg);
			msg.mStatus = MessageServer.getCount();
			sendMessage(msg);

		}
		// notifies waiting threads
		os.close();
		synchronized (this) {
			this.notify();
		}
		synchronized (objLock) {
			objLock.notifyAll();
		}
	}

}
