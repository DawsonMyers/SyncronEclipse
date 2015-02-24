/**
 * 
 */
package coms.udp;

import coms.MessageBuffer;
import coms.MsgPacket;

/**
 * @author Dawson
 *
 */
public interface IUdpBufferAccess {

	MessageBuffer<MsgPacket> getIncomingBuffer();
	MessageBuffer<MsgPacket> getOutgoingBuffer();
}



