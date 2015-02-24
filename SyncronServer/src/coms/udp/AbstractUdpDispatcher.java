/**
 * 
 */
package coms.udp;

import org.perf4j.aop.Profiled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sync.system.SyncUtils;

import coms.MessageBuffer;
import coms.MsgPacket;
import coms.UdpMessenger;

/**
 * @author Dawson
 *
 */
public abstract class AbstractUdpDispatcher implements Runnable {

	public final static Logger					log		= LoggerFactory.getLogger(AbstractUdpDispatcher.class.getName());
	public static volatile MessageBuffer<MsgPacket>	msgBuffer	= new MessageBuffer<MsgPacket>();

	/**
	 * 
	 */
	public AbstractUdpDispatcher() {}

	public AbstractUdpDispatcher(MessageBuffer<MsgPacket> messageBuffer) {
		msgBuffer = messageBuffer;
	}

	@Override
	public void run() {
//		System.out.println("MsgDispatcher started");
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

	@Profiled
	abstract void handleMessage();
	
	abstract void sendMessage(MsgPacket msgPacket);
	

}
