/**
 * 
 */
package coms.udp.server;

import msg.MsgTimer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sync.serial.ArdulinkSerial;
import coms.MsgPacket;
import coms.MsgParser;
import coms.UdpMessenger;
import coms.udp.AbstractUdpDispatcher;
import coms.udp.IUdp;

/**
 * @author Dawson
 *
 */
public class UdpServerReceiver extends AbstractUdpDispatcher implements IUdp {
	
	public final static Logger	log	= LoggerFactory.getLogger(UdpServerReceiver.class.getName());
	public static MsgTimer						timer				= new MsgTimer();

	public UdpServerReceiver() {

	}

	 
	@Override
	public
	void handleMessage() {

		MsgPacket msgPacket = msgBuffer.nextFromQue();
		msgPacket.addNewClient();
		

		// Parse and extract msg data
		MsgParser.parseMsg(msgPacket);

		timer.finish();
		timer.print();
	

		ArdulinkSerial.setPin(msgPacket.getPin(), msgPacket.getIntValue());
		if (msgPacket.type.equals("digital")) {
			log.info("INCOMMING MSG OF TYPE:  DIGITAL");
			msgPacket.setCmd("log");
			// msgPacket.setCmd("log");
			sendMessage(msgPacket);
		}
		if (msgPacket.type == "log") {
			log.info(msgPacket.value);
		}
	
	}


	/* (non-Javadoc)
	 * @see coms.udp.IUdp#sendMessage(coms.MsgPacket)
	 */
	@Override
	public void sendMessage(MsgPacket msgPacket) {
		UdpMessenger.sendUDP(msgPacket);
	}

}



