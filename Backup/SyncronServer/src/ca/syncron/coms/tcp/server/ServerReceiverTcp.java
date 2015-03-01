/**
 * 
 */
package ca.syncron.coms.tcp.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.syncron.coms.tcp.AbstractTcpDispatcher;
import ca.syncron.coms.tcp.AbstractTcpHandler;
import ca.syncron.coms.tcp.ITcp;
import ca.syncron.coms.tcp.MessageTcp;
import ca.syncron.msg.MsgTimer;
 
 

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

		MessageTcp msg = (MessageTcp) msgBuffer.nextFromQue();
	 
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
	 * @see ca.syncron.coms.udp.IUdp#sendMessage(ca.syncron.coms.MessageTcp)
	 */
	@Override
	public void sendMessage(MessageTcp msg) {
		//UdpMessenger.sendUDP(msg);
	}

 




}
