/**
 * 
 */
package coms;

import org.perf4j.aop.Profiled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sync.system.SyncUtils;

/**
 * @author Dawson
 *
 */
public class UdpSender implements Runnable {

	public final static Logger log = LoggerFactory.getLogger(UdpSender.class.getName());
	public static volatile MessageBuffer<MsgPacket>	outgoingMsgBuffer		= new MessageBuffer<MsgPacket>();
	/**
	 * 
	 */
	public UdpSender() {		
	}
	public UdpSender(MessageBuffer<MsgPacket> outgoingBuff) {
		outgoingMsgBuffer = outgoingBuff;
	}
	@Override
	public void run() {
		System.out.println("MsgDispatcher started");
		log.info("MsgDispatcher	started");
		while (true) {

			if (outgoingMsgBuffer.queEmpty()) {
				synchronized (outgoingMsgBuffer) {
					try {

						outgoingMsgBuffer.wait();
						log.info("MsgDispatcher	NOTIFIED");

					} catch (InterruptedException e) {
						e.printStackTrace();
						SyncUtils.getDateBox();
					}
				}

			} else {
				log.info("MsgDispatcher	is processing a msg");
				handleSendMsg();

			}
		}
	}

	@Profiled
	private synchronized void handleSendMsg() {
		log.info("MsgDispatcher	handling send msg");
		MsgPacket msg = outgoingMsgBuffer.nextFromQue();
		log.info("MsgDispatcher	pulled msg from que to send");

		UdpMessenger.sendUDP(msg);
	}

	 

}



