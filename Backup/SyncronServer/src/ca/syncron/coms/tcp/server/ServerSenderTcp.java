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
