/**
 * 
 */
package ca.syncron.coms;

/**
 * @author Dawson
 *
 */
public class MsgAddress {

	public String ip = null;
	/**
	 * @return object ip of type String
	 */
	public String getIp() {
		return this.ip;
	}

	/**
	 * @param ip the ip to set
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * @return object port of type int
	 */
	public int getPort() {
		return this.port;
	}

	/**
	 * @param port the port to set
	 */
	public void setPort(int port) {
		this.port = port;
	}

	public int port = 0;
	
	/**
	 * 
	 */
	public MsgAddress(String ip, int port) {
		this.ip = ip;
		this.port = port;
	}
	

}



