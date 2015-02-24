/**
 * 
 */
package coms.udp.node;

import java.util.Iterator;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sync.serial.ArdulinkSerial;
import coms.MessageBuffer;
import coms.MsgPacket;
import coms.udp.AbstractUdpHandler;

/**
 * @author Dawson
 *
 */
public class UdpNodeHandler extends AbstractUdpHandler {
	public final static Logger	log	= LoggerFactory.getLogger(UdpNodeHandler.class.getName());

	UdpNodeReceiver			incomingHandler;
	UdpNodeSender			outgoingHandler;

	public   UdpNodeHandler() {
		//	handlers started in super
		startMsgHandlers();

	}

	@Override
	public synchronized void startMsgHandlers() {
		incomingHandler = new UdpNodeReceiver(this);
		new Thread(incomingHandler, "IncomingUdpHandler").start();

		outgoingHandler = new UdpNodeSender(this);
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
		
		ArdulinkSerial.setPin(msgPacket.getPin(), msgPacket.getIntValue());
		
//		System.out.println(connectedClients.size());
//		for(String id: connectedClients.keySet()) {
//			System.out.println("Connected Client:  " + id);
//		if (id.contains("node")) {
//			msgPacket.setClient(connectedClients.get(id));
//			log.error("Sending digital msg to node");
//			System.out.println(msgPacket.getClientId());
//			sendMessage(msgPacket);
//		}
//		}
		
		System.out.println("UdpNodeHandler::handleDigitalMessage");
	}

	@Override
	public void handleAnalogMessage(MsgPacket msgPacket) {
		System.out.println("UdpNodeHandler::handleAnalogMessage");
	}

	@Override
	public void handleAdminMessage(MsgPacket msgPacket) {
		System.out.println("UdpNodeHandler::handleAdminMessage");
	}

	@Override
	public void handleUpdateMessage(MsgPacket msgPacket) {
		System.out.println("UdpNodeHandler::handleUpdateMessage");
	}

	@Override
	public void handleRegisterMessage(MsgPacket msgPacket) {
		System.out.println("UdpNodeHandler::handleRegisterMessage");
	}

	@Override
	public void handleStatusMessage(MsgPacket msgPacket) {
		System.out.println("UdpNodeHandler::handleStatusMessage");
	}

	@Override
	public void handleLoginMessage(MsgPacket msgPacket) {
		System.out.println("UdpServerHandler::handleLoginMessage");
	}

	@Override
	public void handleUserMessage(MsgPacket msgPacket) {
		System.out.println("UdpServerHandler::handleUserMessage");
	}

	@Override
	public void sendMessage(MsgPacket msg) {
		outgoingHandler.sendMessage(msg);
	}

	@Override
	public void implementedMapConfig() {
		//implementedMap.put(fTYPE, "add type");
		implementedMap.put(fSENDER_TYPE, vNODE);
	}

}
