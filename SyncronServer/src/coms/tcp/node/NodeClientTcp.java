/**
 * 
 */
package coms.tcp.node;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;

import javax.mail.internet.InternetAddress;

import naga.ConnectionAcceptor;
import naga.NIOServerSocket;
import naga.NIOSocket;
import naga.ServerSocketObserver;
import naga.SocketObserver;
import naga.eventmachine.EventMachine;
import naga.packetreader.AsciiLinePacketReader;
import naga.packetwriter.AsciiLinePacketWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import coms.ComConstants;
import coms.tcp.server.ServerTcp;

/**
 * @author Dawson
 *
 */
public class NodeClientTcp extends Thread implements SocketObserver,  ComConstants {
	public final static Logger	log			= LoggerFactory.getLogger(NodeClientTcp.class.getName());
	public EventMachine	mEventMachine;
	public static boolean		isConnected	= false;
	public static NIOSocket		socket		= null;
	public static NodeClientTcp mClient = null;
	public ClientHandlerTcp handler = null;
	// public NodeClientTcp() {
	// }
	public NodeClientTcp() {}
	public void init(EventMachine machine) {
		mEventMachine = machine;
		mClient = this;
		handler = new ClientHandlerTcp();
	}
	public NodeClientTcp(EventMachine machine) {
		mEventMachine = machine;
		mClient = this;
		handler = new ClientHandlerTcp();
	}
	public  static NodeClientTcp getInstance() {return mClient;}

	/**
	 * @param args
	 */
	@Override
	public void run() {
	//public static void main(String[] args) {
		int port = 6500;// Integer.parseInt(args[0]);
		String host = IP_LOCAL; // IP_SERVER;
		InetSocketAddress address = new InetSocketAddress(host, port);
		try {
			EventMachine machine = new EventMachine();
			// InetAddress ip = InetAddress.getByName(HTTP_SERVER);
			socket = machine.getNIOService().openSocket(host, port);
			socket.listen(new NodeClientTcp(machine));
			socket.setPacketReader(new AsciiLinePacketReader());
			socket.setPacketWriter(new AsciiLinePacketWriter());
			machine.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	

	@Override
	public void connectionOpened(NIOSocket nioSocket) {
		log.info("Connected to server");
		setConnected(true);
	}

	@Override
	public void connectionBroken(NIOSocket nioSocket, Exception exception) {
		log.info("Disconnected from server");
		setConnected(false);
	}

	@Override
	public void packetReceived(NIOSocket socket, byte[] packet) {
		log.info("packet received");
		String message = new String(packet).trim();

		if (message.length() == 0) return;
		// register name with server
		if (message.contains(sysREGISTER_REQUEST)) {
			sendMessage(sysID_NODE);
			log.info("Sending registration ID to server");
		}
		System.out.println("Received message: \n->" + message);
		//System.out.println(testMsg);
		//sendMessage("{message_type:\"digital\",sender_type:\"node\",value:\"TEST FROM NODE\",target_id:\"android\"}");

		handler.addToQue(message);
	}

	@Override
	public void packetSent(NIOSocket socket, Object tag) {
		log.info("Packet sent");
	}

	public void sendMessage(String msg) {
		socket.write(msg.getBytes());
	}

	public void setConnected(boolean b) {
		isConnected = b;
	}

	public boolean isConnected() {
		return isConnected;
	}
}
