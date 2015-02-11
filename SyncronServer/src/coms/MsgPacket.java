/**
 * 
 */
package coms;

import java.net.DatagramPacket;

/**
 * @author Dawson
 */
public class MsgPacket {

	public DatagramPacket dp;

	/**
	 * @return object dp of type DatagramPacket
	 */
	public DatagramPacket getDp() {
		return this.dp;
	}

	/**
	 * @param dp the dp to set
	 */
	public void setDp(DatagramPacket dp) {
		this.dp = dp;
	}

	public MsgPacket(Client client, String jasonMsg, DatagramPacket dp) {
		setDp(dp);
		setClient(client);
		setJasonMsg(jasonMsg);
	}

	/**
	 * 
	 */
	private Client	mClient;
	/**
	 * 
	 */
	private String	mJasonMsg;

	/**
	 * 
	 */
	public MsgPacket() {}

	/**
	 * @return object client of type Client
	 */
	public Client getClient() {
		return this.mClient;
	}

	/**
	 * @param client
	 *            the client to set
	 */
	public void setClient(Client client) {
		this.mClient = client;
	}

	/**
	 * @return object jasonMsg of type String
	 */
	public String getJasonMsg() {
		return this.mJasonMsg;
	}

	/**
	 * @param jasonMsg
	 *            the jasonMsg to set
	 */
	public void setJasonMsg(String jasonMsg) {
		this.mJasonMsg = jasonMsg;
	}
}
