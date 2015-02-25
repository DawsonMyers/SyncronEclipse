/**
 *
 */
package coms.udp.node;

// import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.Format;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.json.simple.JSONObject;

import sync.system.SyncUtils;
import coms.ComConstants;
import coms.MessageBuffer;
import coms.MsgPacket;
import coms.udp.AbstractUdpHandler;

/**
 * @author Dawson
 *
 */
public class NodeUdpMessenger implements Runnable, ComConstants {

	public static DatagramSocket udpSocket = null;
	public static InetAddress receiverAddress = null;
	public static int UdpBufferLength = 1024;
	public static byte[] UdpBuffer = new byte[UdpBufferLength];
	public static String jasonString = "";
	public static String jasonMsg = "";
	public static JSONObject obj = new JSONObject();
	public static Thread sendThread = null;
	public static Thread listenerThread = null;
	public static int portUdp = 10000;
	public static int portSend = 10005;
	public static int count = 0;
	public static DatagramPacket nodePacket = null;
	public static MessageBuffer<MsgPacket> incomingBuffer = null;
	public static MessageBuffer<MsgPacket> outgoingBuffer = null;
	public static Map<String, Object> datagramMap = new HashMap<>();

	AbstractUdpHandler handlerBuffers = null;

	//
	// ///////////////////////////////////////////////////////////////////////////////////

	public NodeUdpMessenger() {
	}

	public NodeUdpMessenger(AbstractUdpHandler ioBuffers) {
		handlerBuffers = ioBuffers;
		incomingBuffer = ioBuffers.getIncomingBuffer();
		outgoingBuffer = ioBuffers.getOutgoingBuffer();
	}

	// son = (JSONObject)new JSONParser().parse(jason);

	@Override
	public String toString() {
		return "";// ReflectionToStringBuilder.toString(this,
					// ToStringStyle.MULTI_LINE_STYLE);
	}

	@Override
	public void run() {

		try {
			StartUDPListener();

		} catch (IOException e) {
			e.printStackTrace();
			SyncUtils.getDateBox();
		}// finally {udpSocket.close();}

	}

	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// [UDP]

	public synchronized static void initUDP() {
		try {
			udpSocket = new DatagramSocket(portSend);
			receiverAddress = InetAddress.getByName(IP); // getLocalHost();
		} catch (SocketException | UnknownHostException e) {
			e.printStackTrace();
			SyncUtils.getDateBox();
		}
	}

	// / jsonMsg = {message_type: "digital", sender_type:"node", value:"0"}

	// Sender
	// ///////////////////////////////////////////////////////////////////////////////////
	/**
	 * @Loggable(Loggable.DEBUG)
	 */

	public static MsgPacket checkinMsg = null;

	public static void startBeacon() {

		byte[] buf = new byte[1024];
		InetAddress address = null;
		try {
			address = InetAddress.getByName(IP_SERVER);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DatagramPacket dp = new DatagramPacket(buf, buf.length, address,PORT_UPD_SERVER);
		checkinMsg = new MsgPacket(dp);
		String update = "{message_type: \"checkin\", sender_type:\"node\"}";
		checkinMsg.setJsonMsg(update);

		Runnable keepAliveTask = () -> sendUDP(checkinMsg);

		ExecutorService executorService = Executors.newScheduledThreadPool(10);
		((ScheduledExecutorService) executorService).scheduleAtFixedRate(
				keepAliveTask, 0, 100, TimeUnit.MILLISECONDS);
		// executorService.execute(new Runnable() {
		// public void run() {
		// System.out.println("Asynchronous task");
		// }
		// });

		//executorService.shutdown();
	}

	public synchronized static void sendUDP(MsgPacket p) {
		// byte[] buf = UdpBuffer.clone();
		sendThread = new Thread("UdpSender") {
			public void run() {
				// Client c = p.getClient();
				try (DatagramSocket udpSocket = new DatagramSocket(10005)) {

					DatagramPacket pack = p.dp;

					byte[] buf = p.getJsonMsg().getBytes();

					SocketAddress address = p.dp.getSocketAddress();

					DatagramPacket dp = new DatagramPacket(buf, buf.length,
							address);
					DatagramPacket packetFromMap = searchMap(datagramMap, p.dp
							.getAddress().toString());
					if (packetFromMap != null) {
						System.out.println("Packet found in map");
						pack = packetFromMap;
						pack = nodePacket;
					}
					udpSocket.send(pack);
					udpSocket.send(dp);
					byte[] buf1 = "TESTER".getBytes();
					DatagramSocket udpSocket1 = new DatagramSocket();
					DatagramPacket dp1 = new DatagramPacket(buf1, buf1.length,
							inet, port);
					DatagramPacket dp2 = new DatagramPacket(buf1, buf1.length,
							sockAddress);
					udpSocket1.send(dp1);
					udpSocket1.send(dp2);
					// udpSocket.send(p.dp);
				} catch (IOException e) {
					e.printStackTrace();
					SyncUtils.getDateBox();
				}
			}
		};

		sendThread.start();
	}

	// Receiver
	// ///////////////////////////////////////////////////////////////////////////////////
	public static InetAddress inet = null;
	public static SocketAddress sockAddress = null;
	public static int port = 0;

	public synchronized static void StartUDPListener() throws IOException {

		// DatagramSocket sockListener = new DatagramSocket(portUdp);
		// if (sockListener == null) udpSocket = new DatagramSocket(portUdp);
		listenerThread = new Thread("UdpListener") {
			private int count;

			// DatagramSocket sockListener = null;

			public void run() {
				String jsonMsg = "";
				DatagramPacket packet;

				try (DatagramSocket sockListener = new DatagramSocket(portUdp)) {
					// if (sockListener == null)
					// sockListener = new DatagramSocket(portUdp);

					byte[] buf = UdpBuffer.clone();
					while (true) {

						packet = new DatagramPacket(buf, buf.length);
						try {
							sockListener.receive(packet);

							inet = packet.getAddress();
							port = packet.getPort();
							sockAddress = packet.getSocketAddress();
							nodePacket = packet;
							sockListener.send(packet);

							putIfAbsent(datagramMap, packet);
							System.out.println("++++++++++PACKET RECEIVED");

						} catch (IOException e) {
							e.printStackTrace();
							SyncUtils.getDateBox();
						}

						buf = packet.getData();
						System.out.println(packet.getData().toString());
						jsonMsg = new String(buf).trim().replace("}}", "}");

						System.out.println("\t\t\t\t\t\t\t\t MSG COUNT = "
								+ count);

						count++;

						System.out.println("jsonMsg = " + jsonMsg);
						// Msg msg = new Msg(jasonMsg, packet);
						MsgPacket msgPacket = new MsgPacket(jsonMsg, packet);

						// System.out.println(packet.getSocketAddress());

						// UdpHandler.incomingMsgBufferPACKET.addToQue(msg);
						incomingBuffer.addToQue(msgPacket);
						// UdpHandler.incomingMsgBufferPACKET.addToQue(msgPacket);

						System.out.println("msg pulled from list = "
								+ (incomingBuffer.queSize()));

						// sockListener.close();
					}
				} catch (SocketException e1) {
					e1.printStackTrace();
					SyncUtils.getDateBox();
				}

			}
		};
		listenerThread.start();
	}

	public static DatagramPacket searchMap(Map<String, Object> map,
			String keyFragment) {
		for (String id : map.keySet()) {
			System.out.println("Datagram map :  " + id);
			if (id.contains(keyFragment)) {
				return (DatagramPacket) map.get(id);
			}
		}
		return null;
	}

	public static void putIfAbsent(Map<String, Object> map,
			DatagramPacket packet) {
		// System.out.println("Datagram map :  " + id);
		String address = packet.getSocketAddress().toString();
		if (!map.containsKey(address))
			map.put(address, packet);

	}
}
