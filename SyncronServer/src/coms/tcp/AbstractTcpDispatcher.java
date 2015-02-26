/**
 * 
 */
package coms.tcp;

import org.perf4j.aop.Profiled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sync.system.SyncUtils;
import coms.MessageBuffer;
import coms.tcp.MessageTcp;
import coms.UdpMessenger;

/**
 * @author Dawson
 *
 */
public abstract class AbstractTcpDispatcher implements Runnable {

	public final static Logger				log			= LoggerFactory.getLogger(AbstractTcpDispatcher.class.getName());
	public volatile MessageBuffer<MessageTcp>	msgBuffer		= new MessageBuffer<MessageTcp>();
	public AbstractTcpHandler				tcpHandler	= null;

	/**
	 * 
	 */
	public AbstractTcpDispatcher() {}

	// using super so that the node, server, and android implementations will
	// all have access
	public AbstractTcpDispatcher(AbstractTcpHandler handler) {
		tcpHandler = handler;
	}

	public AbstractTcpDispatcher(MessageBuffer<MessageTcp> messageBuffer) {
		msgBuffer = messageBuffer;
	}

	@Override
	public void run() {
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
				
				 handleMessage() ;
				//new Thread(() -> handleMessage()).start();
			}
		}
	}

	public abstract void handleMessage();

	public abstract void sendMessage(MessageTcp msg);

}
