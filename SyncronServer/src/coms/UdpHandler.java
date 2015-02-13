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
import org.perf4j.aop.Profiled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcabi.aspects.Loggable;

import sync.system.SyncUtils;

/**
 * @author Dawson
 *
 */
public class UdpHandler {

	 private final static Logger  log = LoggerFactory.getLogger(UdpHandler.class.getName());

	  
	public static int								counter					= 0;
	public static MsgTimer							timer					= new MsgTimer();
	public static volatile Map<String, Client>		connectedClients		= new HashMap<>();
	public static volatile LinkedList<Msg>			MessageQue				= new LinkedList<>();

	// public static volatile MessageBuffer<Msg> incomingMsgBufferPACKET = new
	// MessageBuffer<Msg>();
	public static volatile MessageBuffer<MsgPacket>	incomingMsgBufferPACKET	= new MessageBuffer<MsgPacket>();
	public static volatile MessageBuffer<MsgPacket>	outgoingMsgBuffer		= new MessageBuffer<MsgPacket>();

	public static UdpMessenger						udpMessenger;
	public static Thread							udpMessengerThrd;

	
	
	
	@Profiled
	public UdpHandler() {
		//final Logger slf4jLogger = LoggerFactory.getLogger(this.getClass());
		log.info("Logger started");
	}

	
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

	//
	// ///////////////////////////////////////////////////////////////////////////////////

	/**
	 * @param args
	 */
	@Profiled
	@Loggable(Loggable.DEBUG) 
	public static void main(String[] args) {

		udpMessenger = new UdpMessenger();

		udpMessengerThrd = new Thread(udpMessenger);
		udpMessengerThrd.start();

		log.info("Starting msgHandler");
		startMsgHandler();

		log.info("Starting msgDispatchHandler");
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

			s = "empty";
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

	// Incomming msg que
	// ///////////////////////////////////////////////////////////////////////////////////


	// Msg handler
	// ///////////////////////////////////////////////////////////////////////////////////


	public static Thread	msgHandlerThread	= null;
	@Profiled
	@Loggable(Loggable.DEBUG) 
	public static synchronized void startMsgHandler() {

		Thread msgHandlerThread = new Thread("MsgHandler") {

			JSONObject	json		= null;
			ActiveMsg	activeMsg	= null;

			public void run() {

				while (true) {

					if (incomingMsgBufferPACKET.queEmpty()) {
						synchronized (incomingMsgBufferPACKET) {
							try {
								incomingMsgBufferPACKET.wait();
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
			@Profiled
			private synchronized void handleMsg() {
				MsgPacket msgPacket = incomingMsgBufferPACKET.nextFromQue();

				// Parse and extract msg data
				MsgParser.parseMsg(msgPacket);

				timer.finish();
				System.out.print("\t\t\t\t\t\t\t\t");
				timer.print();
				System.out.println("Received msg contents: \n" + msgPacket.getJasonMsg()); // activeMsg.toString());

			}
		};
		msgHandlerThread.start();
	}

	// Dispatcher
	// ///////////////////////////////////////////////////////////////////////////////////
	@Profiled
	@Loggable(Loggable.DEBUG) 
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
			@Profiled
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
