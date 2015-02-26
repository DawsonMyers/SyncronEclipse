/**
 * 
 */
package coms.tcp.server;

import java.util.Map;

import msg.MsgTimer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sync.serial.ArdulinkSerial;
import coms.tcp.MessageTcp;
import coms.MsgPacket;
import coms.MsgParser;
 
import coms.tcp.AbstractTcpDispatcher;
import coms.tcp.AbstractTcpHandler;
 
import coms.tcp.ITcp;

/**
 * @author Dawson
 *
 */
public class ServerReceiverTcp extends AbstractTcpDispatcher implements ITcp {

	// AbstractUdpHandler mHandler = null;
	public final static Logger	log		= LoggerFactory.getLogger(ServerReceiverTcp.class.getName());
	public static MsgTimer		timer	= new MsgTimer();

	public ServerReceiverTcp() {}

	public ServerReceiverTcp(AbstractTcpHandler handler) {
		super(handler);
		// mHandler = handler;
	}

	@Override
	public void handleMessage() {

		MessageTcp msg = msgBuffer.nextFromQue();
	 
		msg.setHandler(tcpHandler);
		 
		tcpHandler.processMessage(msg);
		// Parse and extract msg data

//		timer.finish();
//		timer.print();
		//
		// ArdulinkSerial.setPin(msg.getPin(), msg.getIntValue());
		// if (msg.type.equals("digital")) {
		// log.info("INCOMMING MSG OF TYPE:  DIGITAL");
		// msg.setCmd("log");
		// // msg.setCmd("log");
		// sendMessage(msg);
		// }
		// if (msg.type == "log") {
		// log.info(msg.value);
		// }

	}

	/*
	 * (non-Javadoc)
	 * @see coms.udp.IUdp#sendMessage(coms.MessageTcp)
	 */
	@Override
	public void sendMessage(MessageTcp msg) {
		//UdpMessenger.sendUDP(msg);
	}

 




}
