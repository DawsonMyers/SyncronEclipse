/**
 * 
 */
package coms;

import java.net.DatagramPacket;
import java.util.Map;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.json.JSONObject;
//import net.sf.json.JSONSerializer;

/**
 * @author Dawson
 */
public class MsgPacket implements ComConstants {
public final static Logger log = LoggerFactory.getLogger(MsgPacket.class.getName());
	public DatagramPacket	dp;
	private Client			mClient;

	private String			mJsonMsg;

	public String			protocol	= null;
	public String			cmd			= null;
	public String			targetId	= null;
	public String			pin			= "";
	public String			value		= null;
	public Client			client		= null;

	// Constructors
	// ///////////////////////////////////////////////////////////////////////////////////

	public MsgPacket() {}

	public MsgPacket(Client client, String jasonMsg, DatagramPacket dp) {
		setDp(dp);
		setClient(client);
		setJsonMsg(jasonMsg);
	}

	public MsgPacket(Client client, String jasonMsg) {

		setClient(client);
		setJsonMsg(jasonMsg);
	}

	public MsgPacket(String jsonMsg, DatagramPacket dp) {
		setDp(dp);
		setClient(client);
		setJsonMsg(jsonMsg);
	}

	// Getters/Setters
	// ///////////////////////////////////////////////////////////////////////////////////

	/**
	 * @return object dp of type DatagramPacket
	 */
	public DatagramPacket getDp() {
		return this.dp;
	}

	/**
	 * @param dp
	 *            the dp to set
	 */
	public void setDp(DatagramPacket dp) {
		this.dp = dp;
	}


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
		return this.mJsonMsg;
	}

	/**
	 * @param jsonMsg
	 *            the jasonMsg to set
	 */
	public void setJsonMsg(String jsonMsg) {
		this.mJsonMsg = jsonMsg;
	}

	public void extractJsonData(Map<String,String> json) {
		log.debug("Extracting data");
		//protocol = (String) json.get(PROTOCAL);
		cmd = (String) json.get(CMD).toString();
		//targetId = (String) json.get(TARGET_ID).toString();
		pin = (String) json.get(PIN).toString();
		value = (String) json.get(VALUE).toString();
	}


	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

	/**
	 * @return
	 */
	public String getJsonMsg() {
		return mJsonMsg;
	}
	public int getPin() {
		return Integer.parseInt(pin);
		
	}
	public int getValue() {
		return Integer.parseInt(value);
		
	}
}
