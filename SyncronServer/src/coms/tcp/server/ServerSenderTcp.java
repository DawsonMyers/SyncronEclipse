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
import coms.tcp.MessageTcp;
import coms.udp.AbstractUdpHandler;
import coms.udp.IUdp;

/**
 * @author Dawson
 *
 */
public class ServerSenderTcp extends AbstractTcpDispatcher implements IUdp {

	public final static Logger	log		= LoggerFactory.getLogger(ServerSenderTcp.class.getName());
	public static MsgTimer		timer	= new MsgTimer();

	public ServerSenderTcp() {}
	public ServerSenderTcp(AbstractTcpHandler handler) {
		super(handler);
	}

	@Override
	public void handleMessage() {
		//MessageTcp msg;
		if(msgBuffer.queSize() > 0) {
			MessageTcp msg = msgBuffer.nextFromQue();
			sendMessage(msg);
		}
	}

	 

	@Override
	public void sendMessage(MessageTcp msg) {}
	@Override
	public void sendMessage(MsgPacket msgPacket) {}

}
