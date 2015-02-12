/**
 * 
 */
package coms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.activity.ActivityRequiredException;

import msg.MsgTimer;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import sync.system.SyncUtils;

/**
 * @author Dawson
 *
 */
public class UdpHandler {

	
	public static int counter	= 0;
	public static MsgTimer							timer				= new MsgTimer();
	public static volatile Map<String, Client>		connectedClients	= new HashMap<>();
	public static volatile LinkedList<Msg>			MessageQue			= new LinkedList<>();

	public static volatile MessageBuffer<Msg>		incomingMsgBuffer	= new MessageBuffer<Msg>();
	public static volatile MessageBuffer<MsgPacket>	outgoingMsgBuffer	= new MessageBuffer<MsgPacket>();

	public static UdpMessenger						udpMessenger;
	public static Thread							udpMessengerThrd;

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

	//
	// ///////////////////////////////////////////////////////////////////////////////////

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		udpMessenger = new UdpMessenger();

		udpMessengerThrd = new Thread(udpMessenger);
		udpMessengerThrd.start();


		startMsgHandler();

		msgDispatchHandler();

		BufferedReader cin = new BufferedReader(new InputStreamReader(System.in));

		while (true) {
			String s = "empty";
			try {
				s = (String) cin.readLine();
			} catch (IOException e) {
				e.printStackTrace();
				SyncUtils.getDateBox();
			}


			timer.start();
			// byte[] b = s.getBytes();
			Tester t = new Tester();
			MsgPacket packet = t.initPacket(s);
			// UdpMessenger.sendUDP(packet);
			
			outgoingMsgBuffer.addToQue(packet);
			outgoingMsgBuffer.addToQue(packet);
			
		}

	}

	/**
	 * 
	 */
	public UdpHandler() {}

	// Incomming msg que
	// ///////////////////////////////////////////////////////////////////////////////////


	// Msg handler
	// ///////////////////////////////////////////////////////////////////////////////////


	public static Thread	msgHandlerThread	= null;

	public static synchronized void startMsgHandler() {

		Thread msgHandlerThread = new Thread("MsgHandler") {

			JSONObject	  json		= null;
			ActiveMsg	activeMsg	= null;

			public void run() {

				while (true) {

					if (incomingMsgBuffer.queEmpty()) {
						synchronized (incomingMsgBuffer) {
							try {
								incomingMsgBuffer.wait();
								System.out.println("msgHandler is waiting");
							} catch (InterruptedException e) {
								e.printStackTrace();
								SyncUtils.getDateBox();
							}
						}

					} else {
						System.out.println("msgHandler is processing a msg");
						handleMsg();

					}
				}
			}

			private synchronized void handleMsg() {
				Msg msg = incomingMsgBuffer.nextFromQue();


				JSONParser parser = new JSONParser();
				String  jsonString = msg.getJsonMsg();
				// parser.
				try {
					json = (JSONObject) new JSONParser().parse(jsonString);
				} catch (ParseException e) {
					e.printStackTrace();
				}

				activeMsg = new ActiveMsg(json);

				// @TODO switch to dispatch msg

				timer.finish();
				System.out.print("\t\t\t\t\t\t\t\t");
				timer.print();
				System.out.println("Received msg contents: \n" + activeMsg.toString());
				// json.put("REPLY", "added and replyed");

			}
		};
		msgHandlerThread.start();

	}

	public static synchronized void msgDispatchHandler() {
		Thread t = new Thread("MsgDispatcher") {

			public void run() {
				System.out.println("MsgDispatcher	started");
				while (true) {

					if (outgoingMsgBuffer.queEmpty()) {
						synchronized (outgoingMsgBuffer) {
							try {

								outgoingMsgBuffer.wait();
								System.out.println("MsgDispatcher	NOTIFIED");

							} catch (InterruptedException e) {
								e.printStackTrace();
								SyncUtils.getDateBox();
							}
						}

					} else {
						System.out.println("  MsgDispatcher is processing a msg");
						handleSendMsg();

					}
				}
			}

			private synchronized void handleSendMsg() {
				System.out.println("MsgDispatcher	handling send msg");
				MsgPacket msg = outgoingMsgBuffer.nextFromQue();
				System.out.println("MsgDispatcher	pulled msg from que to send");

				UdpMessenger.sendUDP(msg);
			}
		};

		t.start();

	}
}
