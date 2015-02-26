/**
 * 
 */
package coms.tcp.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import coms.MessageBuffer;
import coms.tcp.AbstractTcpHandler;
import coms.tcp.MessageTcp;

/**
 * @author Dawson
 *
 */
public class ServerHandlerTcp extends AbstractTcpHandler {
	public final static Logger		log		= LoggerFactory.getLogger(ServerHandlerTcp.class.getName());

	ServerReceiverTcp				incomingHandler;
	ServerSenderTcp				outgoingHandler;
	public static ServerHandlerTcp	mHandler	= null;
	public static ServerTcp			mServer	= ServerTcp.getInstance();

	// jsonMsg = {message_type: "digital", sender_type:"node",value:"0"}

	public ServerHandlerTcp() {
		// handlers started in super
		startMsgHandlers();
		mHandler = this;
		connectedClients = mServer.connectedClients;
		mServer = ServerTcp.getInstance();
	}

	public static ServerHandlerTcp getInstance() {
		if (mHandler == null) {
			mHandler = new ServerHandlerTcp();
		}
		return mHandler;

	}

	@Override
	public synchronized void startMsgHandlers() {
		incomingHandler = new ServerReceiverTcp(this);
		new Thread(incomingHandler, "Incoming Handler").start();

		outgoingHandler = new ServerSenderTcp(this);
		new Thread(outgoingHandler, "Outgoing Handler").start();
	}

	@Override
	public void handleIncomingMessage(MessageTcp msg) {}

	@Override
	public void handleOutgoingMessage(MessageTcp msg) {}

	@Override
	public MessageBuffer<MessageTcp> getIncomingBuffer() {
		return incomingHandler.msgBuffer;
	}

	@Override
	public MessageBuffer<MessageTcp> getOutgoingBuffer() {
		return outgoingHandler.msgBuffer;
	}

	// Message received callbacks
	// ///////////////////////////////////////////////////////////////////////////////////

	@Override
	public void handleDigitalMessage(MessageTcp msg) {
		System.out.println("Connected Users = " + ServerTcp.connectedClients.size());
		for (String id : ServerTcp.connectedClients.keySet()) {
			System.out.println("Connected Client:  " + id);
			// if (id.contains("node")) {

			if (id.contains(msg.getTargetId())) {
				//msg.getUser().sendToTarget(id, msg.getJsonMsg());
				msg.setTargetUser(ServerTcp.connectedClients.get(id));
				if(msg.getTargetMsg().length() < 2) msg.setTargetMsg(msg.getJsonMsg());
				log.info("Sending digital msg to " + msg.getTargetUser().getName());
				System.out.println(msg.getClientId());
				sendMessage(msg);
			}
		}

		System.out.println("ServerHandlerTcp::handleDigitalMessage");
	}

	@Override
	public void handleAnalogMessage(MessageTcp msg) {
		System.out.println("ServerHandlerTcp::handleAnalogMessage");
	}

	@Override
	public void handleAdminMessage(MessageTcp msg) {
		System.out.println("ServerHandlerTcp::handleAdminMessage");
		System.exit(0);
	}

	@Override
	public void handleUpdateMessage(MessageTcp msg) {
		System.out.println("ServerHandlerTcp::handleUpdateMessage");
	}

	@Override
	public void handleRegisterMessage(MessageTcp msg) {
		System.out.println("ServerHandlerTcp::handleRegisterMessage");
	}

	@Override
	public void handleStatusMessage(MessageTcp msg) {
		System.out.println("ServerHandlerTcp::handleStatusMessage");
	}

	@Override
	public void handleLoginMessage(MessageTcp msg) {
		System.out.println("ServerHandlerTcp::handleLoginMessage");
	}

	@Override
	public void handleUserMessage(MessageTcp msg) {
		System.out.println("ServerHandlerTcp::handleUserMessage");
	}

	@Override
	public void sendMessage(MessageTcp msg) {
		outgoingHandler.sendMessage(msg);
	}

	@Override
	public void implementedMapConfig() {
		// implementedMap.put(fTYPE, "add type");
		implementedMap.put(fSENDER_TYPE, vSERVER);
	}

}
