/**
 * 
 */
package coms.udp;

import msg.MsgTimer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sync.serial.ArdulinkSerial;
import coms.MsgPacket;
import coms.MsgParser;
import coms.UdpMessenger;

/**
 * @author Dawson
 *
 */
public class UdpServerSender extends AbstractUdpDispatcher implements IUdp {

	public final static Logger	log		= LoggerFactory.getLogger(UdpServerSender.class.getName());
	public static MsgTimer		timer	= new MsgTimer();

	public UdpServerSender() {

	}

	@Override
	void handleMessage() {
		MsgPacket msgPacket = msgBuffer.nextFromQue();
		sendMessage(msgPacket);
	}

	 
	@Override
	public void sendMessage(MsgPacket msgPacket) {
		UdpMessenger.sendUDP(msgPacket);
	}

}
