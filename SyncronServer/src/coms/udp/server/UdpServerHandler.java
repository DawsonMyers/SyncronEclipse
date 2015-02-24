/**
 * 
 */
package coms.udp.server;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import coms.MessageBuffer;
import coms.MsgPacket;
import coms.udp.AbstractUdpHandler;

/**
 * @author Dawson
 *
 */
public class UdpServerHandler extends AbstractUdpHandler {
	public final static Logger	log	= LoggerFactory.getLogger(UdpServerHandler.class.getName());

	UdpServerReceiver			incomingHandler;
	UdpServerSender			outgoingHandler;

	public UdpServerHandler() {
		startMsgHandlers();

	}


	@Override
	public void startMsgHandlers() {
		incomingHandler = new UdpServerReceiver();
		new Thread(incomingHandler, "IncomingUdpHandler").start();

		outgoingHandler = new UdpServerSender();
		new Thread(outgoingHandler, "OutgoingUdpHandler").start();
	}

	
	@Override
	public void handleIncomingMessage(MsgPacket msg) {}

	@Override
	public void handleOutgoingMessage(MsgPacket msg) {}

	@Override
	public MessageBuffer<MsgPacket> getIncomingBuffer() {
		return incomingHandler.msgBuffer;
	}

	@Override
	public MessageBuffer<MsgPacket> getOutgoingBuffer() {
		return outgoingHandler.msgBuffer;
	}


	@Override
	public void handleDigitalMessage(Map<String, Object> jmap) {}



	@Override
	public void handleAnalogMessage(Map<String, Object> jmap) {}


	
	@Override
	public void handleAdminMessage(Map<String, Object> jmap) {}


	@Override
	public void handleUpdateMessage(Map<String, Object> jmap) {}

}
