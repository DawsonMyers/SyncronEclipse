/**
 * 
 */
package coms;

import java.net.DatagramPacket;

/**
 * @author Dawson
 *
 */
public class Msg {

	/**
	 * 
	 */
	public Msg() {}
	public Msg(String msg, DatagramPacket packet) {
		this.packet = packet;
		jsonMsg = msg;
	}
	DatagramPacket packet;
	/**
	 * @return object packet of type DatagramPacket
	 */
	public DatagramPacket getPacket() {
		return this.packet;
	}
	/**
	 * @param packet the packet to set
	 */
	public void setPacket(DatagramPacket packet) {
		this.packet = packet;
	}
	/**
	 * @return object jsonMsg of type String
	 */
	public String getJsonMsg() {
		return this.jsonMsg;
	}
	/**
	 * @param jsonMsg the jsonMsg to set
	 */
	public void setJsonMsg(String jsonMsg) {
		this.jsonMsg = jsonMsg;
	}
	String jsonMsg;

}



