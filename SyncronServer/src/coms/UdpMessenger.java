/**
 *
 */
package coms;

// import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import org.json.simple.JSONObject;

import sync.system.SyncUtils;
import coms.udp.AbstractUdpHandler;
import coms.udp.IUdpBufferAccess;

/**
 * @author Dawson
 *
 */
public class UdpMessenger implements Runnable, ComConstants {

	public static DatagramSocket			udpSocket			= null;
	public static InetAddress			receiverAddress	= null;
	public static int					UdpBufferLength	= 1024;
	public static byte[]				UdpBuffer			= new byte[UdpBufferLength];
	public static String				jasonString		= "";
	public static String				jasonMsg			= "";
	public static JSONObject				obj				= new JSONObject();
	public static Thread				sendThread		= null;
	public static Thread				listenerThread		= null;
	public static int					portUdp			= 10000;
	public static int					portSend			= 10005;
	public static int					count			= 0;
	public static MessageBuffer<MsgPacket>	incomingBuffer		= null;
	public static MessageBuffer<MsgPacket>	outgoingBuffer		= null;
	AbstractUdpHandler					handlerBuffers		= null;

	//
	// ///////////////////////////////////////////////////////////////////////////////////

	public UdpMessenger() {}

	public UdpMessenger(AbstractUdpHandler ioBuffers) {
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

	///			jsonMsg = {message_type: "digital", sender_type:"node", value:"0"}

	// Sender
	// ///////////////////////////////////////////////////////////////////////////////////
	/**
	 * @Loggable(Loggable.DEBUG)
	 */
	public synchronized static void sendUDP(MsgPacket p) {
		// byte[] buf = UdpBuffer.clone();
		sendThread = new Thread("UdpSender") {
			public void run() {
				// Client c = p.getClient();
				try (DatagramSocket udpSocket = new DatagramSocket()) {

					byte[] buf = p.getJsonMsg().getBytes();

					SocketAddress address= p.dp.getSocketAddress();


					DatagramPacket dp =new DatagramPacket(buf, buf.length, address );

					udpSocket.send(dp);
					//udpSocket.send(p.dp);
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

	public synchronized static void StartUDPListener() throws IOException {

		// DatagramSocket sockListener = new DatagramSocket(portUdp);
		// if (sockListener == null) udpSocket = new DatagramSocket(portUdp);
		listenerThread = new Thread("UdpListener") {
			private int	count;

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

							System.out.println("++++++++++PACKET RECEIVED");

						} catch (IOException e) {
							e.printStackTrace();
							SyncUtils.getDateBox();
						}

						buf = packet.getData();
						System.out.println(packet.getData().toString());
						jsonMsg = new String(buf).trim().replace("}}", "}");

						System.out.println("\t\t\t\t\t\t\t\t MSG COUNT = " + count);

						count++;

						System.out.println("jsonMsg = " + jsonMsg);
						// Msg msg = new Msg(jasonMsg, packet);
						MsgPacket msgPacket = new MsgPacket(jsonMsg, packet);

						// System.out.println(packet.getSocketAddress());

						// UdpHandler.incomingMsgBufferPACKET.addToQue(msg);
						incomingBuffer.addToQue(msgPacket);
						// UdpHandler.incomingMsgBufferPACKET.addToQue(msgPacket);

						System.out.println("msg pulled from list = " + (incomingBuffer.queSize()));

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

}
