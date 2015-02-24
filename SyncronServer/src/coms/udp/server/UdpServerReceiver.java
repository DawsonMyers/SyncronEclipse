/**
 * 
 */
package coms.udp.server;

import java.util.Map;

import msg.MsgTimer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sync.serial.ArdulinkSerial;
import coms.MsgPacket;
import coms.MsgParser;
import coms.UdpMessenger;
import coms.udp.AbstractUdpDispatcher;
import coms.udp.AbstractUdpHandler;
import coms.udp.IUdp;

/**
 * @author Dawson
 *
 */
public class UdpServerReceiver extends AbstractUdpDispatcher implements IUdp {

	// AbstractUdpHandler mHandler = null;
	public final static Logger	log		= LoggerFactory.getLogger(UdpServerReceiver.class.getName());
	public static MsgTimer		timer	= new MsgTimer();

	public UdpServerReceiver() {}

	public UdpServerReceiver(AbstractUdpHandler handler) {
		super(handler);
		// mHandler = handler;
	}

	@Override
	public void handleMessage() {

		MsgPacket msgPacket = msgBuffer.nextFromQue();
		msgPacket.addNewClient();
		msgPacket.setHandler(udpHandler);
		msgPacket.getjMap();
		// Map<String, Object> jMap = MsgParser.parseMsg(msgPacket);
		udpHandler.processMessage(msgPacket);
		// Parse and extract msg data

		timer.finish();
		timer.print();
		//
		// ArdulinkSerial.setPin(msgPacket.getPin(), msgPacket.getIntValue());
		// if (msgPacket.type.equals("digital")) {
		// log.info("INCOMMING MSG OF TYPE:  DIGITAL");
		// msgPacket.setCmd("log");
		// // msgPacket.setCmd("log");
		// sendMessage(msgPacket);
		// }
		// if (msgPacket.type == "log") {
		// log.info(msgPacket.value);
		// }

	}

	/*
	 * (non-Javadoc)
	 * @see coms.udp.IUdp#sendMessage(coms.MsgPacket)
	 */
	@Override
	public void sendMessage(MsgPacket msgPacket) {
		UdpMessenger.sendUDP(msgPacket);
	}

}
