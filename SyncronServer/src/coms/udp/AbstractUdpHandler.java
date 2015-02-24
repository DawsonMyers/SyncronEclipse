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
import coms.MessageBuffer;
import coms.Msg;
import coms.MsgPacket;
import coms.MsgParser;
import coms.Tester;
import coms.UdpMessenger;

/**
 * @author Dawson
 *
 */
public abstract class AbstractUdpHandler extends Thread  {

	public final static Logger				log				= LoggerFactory.getLogger(AbstractUdpHandler.class.getName());

	public static int						counter			= 0;
	public static MsgTimer					timer			= new MsgTimer();
	public static volatile Map<String, Client>	connectedClients	= new HashMap<>();
	public static volatile LinkedList<Msg>		MessageQue		= new LinkedList<>();

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

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

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
		startMsgHandlers();
	}
	/*
	 * (non-Javadoc)
	 * @see coms.udp.IUdpBufferAccess#getIncomingBuffer()
	 */
	// @Override
	// public MessageBuffer<MsgPacket> getIncomingBuffer() {
	// return h;
	// }
	// /* (non-Javadoc)
	// * @see coms.udp.IUdpBufferAccess#getOutgoingBuffer()
	// */
	// @Override
	// public MessageBuffer<MsgPacket> getOutgoingBuffer() {
	// return null;
	// }

	// Msg handler
	// ///////////////////////////////////////////////////////////////////////////////////

	// public static Thread msgHandlerThread = null;
	// JSONObject json = null;
	// UdpServerReceiver incomingHandler;
	// UdpServerSender

	// public static synchronized void startIncomingHandler() {
	//
	// UdpServerReceiver incomingHandler = new UdpServerReceiver();
	// new Thread(incomingHandler, "IncomingHandler").start();
	// }

	//
	// UdpServerReceiver incomingHandler = new UdpServerReceiver();
	// new Thread(incomingHandler, "IncomingHandler").start();
	// }

}
