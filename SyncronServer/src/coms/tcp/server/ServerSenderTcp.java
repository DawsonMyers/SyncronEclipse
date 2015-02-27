/**
 * 
 */
package coms.tcp.server;

import msg.MsgTimer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import coms.tcp.MessageTcp;
import coms.MsgPacket;
import coms.UdpMessenger;
import coms.tcp.AbstractTcpDispatcher;
import coms.tcp.AbstractTcpHandler;
import coms.tcp.ITcp;
import coms.tcp.MessageTcp;
import coms.udp.AbstractUdpHandler;
import coms.udp.IUdp;

/**
 * @author Dawson
 *
 */
public class ServerSenderTcp extends AbstractTcpDispatcher implements ITcp {

	public final static Logger	log		= LoggerFactory.getLogger(ServerSenderTcp.class.getName());
	public static MsgTimer		timer	= new MsgTimer();

	public ServerSenderTcp() {}

	public ServerSenderTcp(AbstractTcpHandler handler) {
		super(handler);
	}

	// Each call is run in new thread
	@Override
	public void handleMessage() {

		if (msgBuffer.queSize() > 0) {
			MessageTcp msg = (MessageTcp) msgBuffer.nextFromQue();
			new Thread(() -> sendMessage(msg), "TcpSender").start();

		}
	}

	@Override
	public void sendMessage(MessageTcp msg) {
		if (msg.isValid()) {
			msg.getTargetSock().write(msg.getTargetMsgBytes());
			log.info("Message sent to " + msg.getTargetUser().getName());
		} else {
			log.error("Could not send invalid message ");
		}

	}

}
