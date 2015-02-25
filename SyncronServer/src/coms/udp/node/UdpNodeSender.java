/**
 * 
 */
package coms.udp.node;

import msg.MsgTimer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import coms.MsgPacket;
import coms.UdpMessenger;
import coms.udp.AbstractUdpDispatcher;
import coms.udp.AbstractUdpHandler;
import coms.udp.IUdp;

/**
 * @author Dawson
 *
 */
public class UdpNodeSender extends AbstractUdpDispatcher implements IUdp {

	public final static Logger	log		= LoggerFactory.getLogger(UdpNodeSender.class.getName());
	public static MsgTimer		timer	= new MsgTimer();

	public UdpNodeSender() {}
	public UdpNodeSender(AbstractUdpHandler handler) {
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