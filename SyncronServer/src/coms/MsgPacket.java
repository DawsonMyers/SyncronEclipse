/**
 *
 */
package coms;

import java.net.DatagramPacket;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.json.simple.JSONObject;
// import net.sf.json.JSONObject;
// import net.sf.json.JSONSerializer;

import com.sun.mail.imap.protocol.ID;

import coms.udp.AbstractUdpHandler;
import coms.udp.MsgMetaData;
import sync.system.SyncUtils;

/**
 * @author Dawson
 */
public class MsgPacket extends MsgMetaData implements ComConstants {
	public final static Logger	log		= LoggerFactory.getLogger(MsgPacket.class.getName());
	public DatagramPacket		dp		= null;										;
	private Client				mClient	= null;


	// Constructors
	// ///////////////////////////////////////////////////////////////////////////////////

	public MsgPacket() {}
	public MsgPacket(DatagramPacket dp) {
		setDp(dp);
	}

	// sending
	public MsgPacket(Client client, String jsonMsg, DatagramPacket dp) {
		setDp(dp);
		setClient(client);
		setJsonMsg(jsonMsg);
		// addNewClient();
	}

	public MsgPacket(Client client, String jsonMsg) {

		setClient(client);
		setJsonMsg(jsonMsg);
		// addNewClient();
	}

	public MsgPacket(Client client, Map<String, Object> jMap) {

		setClient(client);
		prepareJsonFromMap(jMap);
		// addNewClient();
	}

	// Receiving
	public MsgPacket(String jsonMsg, DatagramPacket dp) {
		setDp(dp);
		setJsonMsg(jsonMsg);
		// Also parses
		parseJsonToMap();

		addNewClient();
	}

	// Processing
	// ///////////////////////////////////////////////////////////////////////////////////

	/**
	 * @param jsonMsg
	 */
	private void prepareJsonFromMap(Map<String, Object> jMap) {

		setjMap(jMap);
		JSONObject obj = new JSONObject();
		String jMsg = obj.toJSONString(jMap);
		setJsonMsg(jMsg);
	}

	// Message Getters/Setters
	// ///////////////////////////////////////////////////////////////////////////////////

	public void reinitClient() {
		mClient.init(this);
	}

	/**
	 *
	 */
	public void parseJsonToMap() {
		MsgParser.parseMsg(this);
		initMetaData();
	}

	public void addNewClient() {
		mClient = new Client(this);

	}

	/**
	 * @return object dp of type DatagramPacket
	 */
	public DatagramPacket getDp() {
		return this.dp;
	}

	/**
	 * @param dp
	 *             the dp to set
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
	 *             the client to set
	 */
	public void setClient(Client client) {
		if (client == null) addNewClient();
		else  mClient = client;
		mClient.init(this);
	}

	/**
	 * @return object jasonMsg of type String
	 */
	// public String getJsonMsg() {
	// return this.mJsonMsg;
	// }

	// Set message data
	// ///////////////////////////////////////////////////////////////////////////////////

	/**
	 * @param jsonMsg
	 *             the jasonMsg to set
	 */
	public void setJsonMsg(String jsonMsg) {
		this.mJsonMsg = jsonMsg;
		//setPacketData();
	}

	public void setPacketData(String msgString) {
		dp.setData(msgString.getBytes());
	}

	public void setPacketData() {
		dp.setData(mJsonMsg.getBytes());
	}

	public String toJsonString(Map<String, Object> jMap) {
		try {
			JSONObject json = new JSONObject();
			mJsonMsg = json.toJSONString(jMap);
		} catch (Exception e) {
			e.printStackTrace();
			SyncUtils.getDateBox();
		}
		return mJsonMsg;
	}

	// Member setter/getter
	// ///////////////////////////////////////////////////////////////////////////////////

	/**
	 * @return object jMap of type Map<String,Object>
	 */
	public Map<String, Object> getjMap() {
		if (mJsonMsg != null) {
			//jMap = MsgParser.parseMsg(this);
			//extractMetaData();
		}
		return this.jMap;
	}

	/**
	 *
	 */
	public void extractMetaData() {
		if (jMap != null) initMetaData();
	}

	/**
	 * @param jMap
	 *             the jMap to set
	 */
	public void setjMap(Map<String, Object> jMap) {
		this.jMap = jMap;
		//initMetaData();
	}

	/**
	 * @return object jasonMsg of type String
	 */
	public String getJsonMsg() {
		return mJsonMsg;
	}

	public int getPin() {
		return Integer.parseInt(pin);

	}

	public int getIntValue() {
		return Integer.parseInt(value);

	}

	public String getStringValue() {
		return value;

	}

	public void setPin(String pin) {
		this.pin = pin;

	}

	public void setValue(String val) {
		value = val;

	}

	public void setCmd(String cmd) {
		this.type = cmd;

	}

	/**
	 * @param udpHandler
	 */
	public void setHandler(AbstractUdpHandler udpHandler) {
		this.udpHandler = udpHandler;
	}
}
