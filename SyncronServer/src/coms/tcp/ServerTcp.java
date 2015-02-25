package coms.tcp;

// public class ChatServer {
//
// public static void main(String[] args) {
// // TODO Auto-generated method stub
//
// }
//
// }

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

 
import coms.ComConstants;
import coms.MessageBuffer;
import coms.Msg;
import coms.MsgPacket;
import coms.UdpHandler;
import naga.ConnectionAcceptor;
import naga.NIOServerSocket;
import naga.NIOSocket;
import naga.ServerSocketObserver;
import naga.SocketObserver;
import naga.eventmachine.DelayedEvent;
import naga.eventmachine.EventMachine;
import naga.packetreader.AsciiLinePacketReader;
import naga.packetwriter.AsciiLinePacketWriter;

/**
 * Creates a very simple chat server.
 * <p>
 * Run using {@code java naga.examples.ChatServer [port]}
 *
 * @author Christoffer Lerno
 */
public class ServerTcp implements ServerSocketObserver, Runnable, ComConstants {

	public static MessageBuffer<MsgPacket>		incomingBuffer			= null;
	public static MessageBuffer<MsgPacket>		outgoingBuffer			= null;

	public static volatile Map<String, User>	connectedClients		= new HashMap<String, User>();	// new

	public static volatile Map<String, User>	connectedNodeClients	= new HashMap<>();
	public static volatile Map<String, User>	connectedAndroidClients	= new HashMap<>();
	public static volatile LinkedList<Msg>		MessageQue			= new LinkedList<>();
	public Map<String, Object>				implementedMap			= null;

	public final EventMachine				m_eventMachine;
	// private final LinkedBlockingQueue<User> m_users = new
	// LinkedBlockingQueue<>();
	public final List<User>					m_users;

	ServerTcp(EventMachine machine) {
		m_eventMachine = machine;
		m_users = new ArrayList<User>();
		// m_users = new ArrayList<User>();
	}

	public void acceptFailed(IOException exception) {
		System.out.println("Failed to accept connection: " + exception);
	}

	public void serverSocketDied(Exception exception) {
		// If the server socket dies, we could possibly try to open a new
		// socket.
		System.out.println("Server socket died.");
		System.exit(-1);
	}

	public void newConnection(NIOSocket nioSocket) {
		// Create a new user to hande the new connection.
		System.out.println("New user connected from " + nioSocket.getIp() + ".");
		m_users.add(new User(this, nioSocket));
	}

	public void removeUser(User user) {
		System.out.println("Removing user " + user + ".");
		m_users.remove(user);
	}

	public void broadcast(User sender, String string) {
		// We convert the packet, then send it to all users except the sender.
		byte[] bytesToSend = string.getBytes();
		for (User user : m_users) {
			if (user != sender) user.sendBroadcast(bytesToSend);
		}
	}

	/**
	 * Runs the echo server.
	 *
	 * @param args
	 *             command line arguments, assumed to be a 1 length string
	 *             containing a port.
	 */
	public static void main(String... args) {
		int port = 6500;// Integer.parseInt(args[0]);
		try {
			EventMachine machine = new EventMachine();
			NIOServerSocket socket = machine.getNIOService().openServerSocket(port);
			socket.listen(new ServerTcp(machine));
			socket.setConnectionAcceptor(ConnectionAcceptor.ALLOW);
			machine.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public EventMachine getEventMachine() {
		return m_eventMachine;
	}

	/*private static class User implements SocketObserver {
		private final static long	LOGIN_TIMEOUT		= 30 * 1000;
		private final static long	INACTIVITY_TIMEOUT	= 5 * 60 * 1000;
		private final ServerTcp		m_server;
		private final NIOSocket		m_socket;
		private String				m_name;
		private DelayedEvent		m_disconnectEvent;

		private User(ServerTcp server, NIOSocket socket) {
			m_server = server;
			m_socket = socket;
			m_socket.setPacketReader(new AsciiLinePacketReader());
			m_socket.setPacketWriter(new AsciiLinePacketWriter());
			m_socket.listen(this);
			m_name = null;
		}

		public void connectionOpened(NIOSocket nioSocket) {
			// We start by scheduling a disconnect event for the login.
			m_disconnectEvent = m_server.getEventMachine().executeLater(new Runnable() {
				public void run() {
					m_socket.write("Disconnecting due to inactivity".getBytes());
					m_socket.closeAfterWrite();
				}
			}, LOGIN_TIMEOUT);

			// Send the request to log in.
			nioSocket.write("Please enter your name:".getBytes());
		}

		public String toString() {
			return m_name != null ? m_name + "@" + m_socket.getIp() : "anon@" + m_socket.getIp();
		}

		public void connectionBroken(NIOSocket nioSocket, Exception exception) {
			// Inform the other users if the user was logged in.
			if (m_name != null) {
				m_server.broadcast(this, m_name + " left the chat.");
			}
			// Remove the user.
			m_server.removeUser(this);
		}

		private void scheduleInactivityEvent() {
			// Cancel the last disconnect event, schedule another.
			if (m_disconnectEvent != null) m_disconnectEvent.cancel();
			m_disconnectEvent = m_server.getEventMachine().executeLater(new Runnable() {
				public void run() {
					m_socket.write("Disconnected due to inactivity.".getBytes());
					m_socket.closeAfterWrite();
				}
			}, INACTIVITY_TIMEOUT);
		}

		public void packetReceived(NIOSocket socket, byte[] packet) {
			// Create the string. For real life scenarios, you'd handle
			// exceptions here.
			String message = new String(packet).trim();

			// Ignore empty lines
			if (message.length() == 0) return;

			// Reset inactivity timer.
			scheduleInactivityEvent();

			// ONLY happens on first connect
			// In this protocol, the first line entered is the name.
			if (m_name == null) {
				// User joined the chat.
				m_name = message;
				System.out.println(this + " logged in.");
				m_server.broadcast(this, m_name + " has joined the chat.");
				m_socket.write(("Welcome " + m_name + ". There are " + m_server.m_users.size() + " user(s) currently logged in.").getBytes());
				return;
			}
			m_server.broadcast(this, m_name + ": " + message);
		}

		public void packetSent(NIOSocket socket, Object tag) {
			// No need to handle this case.
		}

		public void sendBroadcast(byte[] bytesToSend) {
			// Only send broadcast to users logged in.
			if (m_name != null) {
				m_socket.write(bytesToSend);
			}

		}
	}*/

	@Override
	public void run() {}

}
