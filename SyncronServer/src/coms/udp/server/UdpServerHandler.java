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
		incomingHandler = new UdpServerReceiver(this);
		new Thread(incomingHandler, "IncomingUdpHandler").start();

		outgoingHandler = new UdpServerSender(this);
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

	//	Message received callbacks
	// ///////////////////////////////////////////////////////////////////////////////////

	
	@Override
	public void handleDigitalMessage(MsgPacket msgPacket) {
		System.out.println("UdpServerHandler::handleDigitalMessage");
	}

	@Override
	public void handleAnalogMessage(MsgPacket msgPacket) {
		System.out.println("UdpServerHandler::handleAnalogMessage");
	}

	@Override
	public void handleAdminMessage(MsgPacket msgPacket) {
		System.out.println("UdpServerHandler::handleAdminMessage");
	}

	@Override
	public void handleUpdateMessage(MsgPacket msgPacket) {
		System.out.println("UdpServerHandler::handleUpdateMessage");
	}

	@Override
	public void handleRegisterMessage(MsgPacket msgPacket) {
		System.out.println("UdpServerHandler::handleRegisterMessage");
	}

	@Override
	public void handleStatusMessage(MsgPacket msgPacket) {
		System.out.println("UdpServerHandler::handleStatusMessage");
	}

	@Override
	public void handleLoginMessage(MsgPacket msgPacket) {
		System.out.println("UdpServerHandler::handleLoginMessage");
	}

	@Override
	public void handleUserMessage(MsgPacket msgPacket) {
		System.out.println("UdpServerHandler::handleUserMessage");
	}

}
