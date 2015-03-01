/**
 * 
 */
package ca.syncron.coms.udp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.syncron.coms.MessageBuffer;
import ca.syncron.coms.MsgPacket;
import ca.syncron.sync.system.SyncUtils;

 
/**
 * @author Dawson
 *
 */
public abstract class AbstractUdpDispatcher implements Runnable {

	public final static Logger				log		= LoggerFactory.getLogger(AbstractUdpDispatcher.class.getName());
	public volatile MessageBuffer<MsgPacket>	msgBuffer	= new MessageBuffer<MsgPacket>();
	public AbstractUdpHandler						udpHandler	= null;

	/**
	 * 
	 */
	public AbstractUdpDispatcher() {}

	
	//	using super so that the node, server, and android implementations will all have access
	public AbstractUdpDispatcher(AbstractUdpHandler handler) {
		udpHandler = handler;
	}

	public AbstractUdpDispatcher(MessageBuffer<MsgPacket> messageBuffer) {
		msgBuffer = messageBuffer;
	}

	@Override
	public void run() {
		// System.out.println("MsgDispatcher started");
		log.info("started");
		while (true) {

			if (msgBuffer.queEmpty()) {
				synchronized (msgBuffer) {
					try {

						msgBuffer.wait();
						log.info("NOTIFIED");

					} catch (InterruptedException e) {
						e.printStackTrace();
						SyncUtils.getDateBox();
					}
				}

			} else {
				log.info("processing a msg");
				handleMessage();

			}
		}
	}

	public abstract void handleMessage();

	public abstract void sendMessage(MsgPacket msgPacket);

}
