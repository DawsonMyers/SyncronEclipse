/**
 * 
 */
package ca.syncron.coms.udp;

import ca.syncron.coms.MessageBuffer;
import ca.syncron.coms.MsgPacket;

/**
 * @author Dawson
 *
 */
public interface IUdpBufferAccess {

	MessageBuffer<MsgPacket> getIncomingBuffer();
	MessageBuffer<MsgPacket> getOutgoingBuffer();
}



