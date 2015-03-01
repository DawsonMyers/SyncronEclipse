/**
 * 
 */
package ca.syncron.coms.udp.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.syncron.coms.MsgPacket;
import ca.syncron.coms.UdpMessenger;
import ca.syncron.coms.udp.AbstractUdpDispatcher;
import ca.syncron.coms.udp.AbstractUdpHandler;
import ca.syncron.coms.udp.IUdp;
import ca.syncron.msg.MsgTimer;

/**
 * @author Dawson
 *
 */
public class UdpServerSender extends AbstractUdpDispatcher implements IUdp {

	public final static Logger	log		= LoggerFactory.getLogger(UdpServerSender.class.getName());
	public static MsgTimer		timer	= new MsgTimer();

	public UdpServerSender() {}
	public UdpServerSender(AbstractUdpHandler handler) {
		super(handler);
	}

	@Override
	public void handleMessage() {
		//MsgPacket msgPacket;
		if(msgBuffer.queSize() > 0) {
			MsgPacket msgPacket = msgBuffer.nextFromQue();
			sendMessage(msgPacket);
		}
	}

	 
	@Override
	public void sendMessage(MsgPacket msgPacket) {
		UdpMessenger.sendUDP(msgPacket);
	}

}
