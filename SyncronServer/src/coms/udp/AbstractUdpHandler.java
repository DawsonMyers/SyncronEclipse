/**
 * 
 */
package coms.udp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import msg.MsgTimer;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.json.simple.JSONObject;
import org.perf4j.aop.Profiled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sync.serial.ArdulinkSerial;
import sync.system.SyncUtils;

import com.jcabi.aspects.Loggable;

import coms.ActiveMsg;
import coms.Client;
import coms.ComConstants;
import coms.MessageBuffer;
import coms.Msg;
import coms.MsgPacket;
import coms.MsgParser;
import coms.Tester;
import coms.UdpHandler;
import coms.UdpMessenger;

/**
 * @author Dawson
 *
 */
public abstract class AbstractUdpHandler extends Thread implements ComConstants {

	public final static Logger				log					= LoggerFactory.getLogger(AbstractUdpHandler.class.getName());

	public static int						counter				= 0;
	public static MsgTimer					timer				= new MsgTimer();
	public static volatile Map<String, Client>	connectedClients		= UdpHandler.connectedClients;							// new
																												// HashMap<>();
	public static volatile Map<String, Client>	connectedNodeClients	= new HashMap<>();
	public static volatile Map<String, Client>	connectedAndroidClients	= new HashMap<>();
	public static volatile LinkedList<Msg>		MessageQue			= new LinkedList<>();
	public Map<String, Object>				implementedMap			= null;
	// public static volatile MessageBuffer<Msg> incomingMsgBufferPACKET = new
	// MessageBuffer<Msg>();
	// public static volatile MessageBuffer<MsgPacket> incomingMsgBufferPACKET
	// = new MessageBuffer<MsgPacket>();
	// public static volatile MessageBuffer<MsgPacket> outgoingMsgBuffer = new
	// MessageBuffer<MsgPacket>();

	public static UdpMessenger				udpMessenger;
	public static Thread					udpMessengerThrd;
	public static AbstractUdpHandler			handler;

	// Started in ArdulinkSerial

	// public static void main(String[] args) {
	// handler = new UdpHandler();
	//
	// }

	public abstract MessageBuffer<MsgPacket> getIncomingBuffer();

	public abstract MessageBuffer<MsgPacket> getOutgoingBuffer();

	public abstract void startMsgHandlers();

	public abstract void handleIncomingMessage(MsgPacket msg);

	public abstract void handleOutgoingMessage(MsgPacket msg);

	public abstract void sendMessage(MsgPacket msg);

	//
	// ///////////////////////////////////////////////////////////////////////////////////

	public AbstractUdpHandler() {
		log.info("Logger started");

	}

	public void run() {
		udpMessenger = new UdpMessenger(this);

		udpMessengerThrd = new Thread(udpMessenger);
		udpMessengerThrd.start();

		log.info("Starting Handlers");
		// startMsgHandlers();
	}

	public void processMessage(MsgPacket msgPacket) {
		log.debug("Extracting data");
		// protocol = (String) json.get(PROTOCAL);

		// Map<String, Object> jMap = msgPacket.getjMap();

		String type = msgPacket.getType();
		switch (type) {
			case tDIGITAL:
				handleDigitalMessage(msgPacket);
				break;
			case tANALOG:
				handleAnalogMessage(msgPacket);
				break;
			case tADMIN:
				handleAdminMessage(msgPacket);
				break;
			case tUPDATE:
				handleUpdateMessage(msgPacket);
				break;
			case tREGISTER:
				handleRegisterMessage(msgPacket);
				break;
			case tSTATUS:
				handleStatusMessage(msgPacket);
				break;
			case tLOGIN:
				handleLoginMessage(msgPacket);
				break;
			case tUSER:
				handleUserMessage(msgPacket);
				break;

			default:
				log.error("message could not be identified");
				break;
		}

	}

	/**
	 * Abstract callback methods that are triggered whenever the corresponding
	 * message type is received. The methods must be implemented differently
	 * according to whether it is being run on the node, server, or android
	 * 
	 * @param msgPacket
	 */
	public abstract void handleDigitalMessage(MsgPacket msgPacket);

	public abstract void handleAnalogMessage(MsgPacket msgPacket);

	public abstract void handleAdminMessage(MsgPacket msgPacket);

	public abstract void handleUpdateMessage(MsgPacket msgPacket);

	public abstract void handleRegisterMessage(MsgPacket msgPacket);

	public abstract void handleStatusMessage(MsgPacket msgPacket);

	public abstract void handleLoginMessage(MsgPacket msgPacket);

	public abstract void handleUserMessage(MsgPacket msgPacket);
	public abstract void implementedMapConfig();

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
