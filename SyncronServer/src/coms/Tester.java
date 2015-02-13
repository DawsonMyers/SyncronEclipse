/**
 * 
 */
package coms;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.json.simple.JSONObject;

import sync.system.SyncUtils;

/**
 * @author Dawson
 *
 */
public class Tester implements ComConstants {
	public String				PROTOCOL	= "protocol";
	public String				CMD			= "cmd";
	public String				TARGET_ID	= "target";
	public String				PIN			= "pin";
	public String				VALUE		= "value";


	Client						c;							// = new Client();

	public static InetAddress	ipAddress;

	// DatagramPacket dp = new DatagramPacket(null, 0, null, 0 );
	/**
	 * 
	 */
	public Tester() {
		try {
			ipAddress = InetAddress.getByName(IP);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			SyncUtils.getDateBox();
		}


	}

	// Logic
	// ///////////////////////////////////////////////////////////////////////////////////

	@SuppressWarnings("unchecked")
	public MsgPacket initPacket(String s) {


		String address = ipAddress.toString().replace("/", "");
		JSONObject obj = new JSONObject();

		obj.put(PROTOCAL, "syncron");
		obj.put(CMD, "digitalOut");
		obj.put(PIN, "10");
		obj.put(VALUE, ""); // s);
		obj.put(TARGET_ID, address); // s);
		obj.put("value", s.trim()); // s);


		String json = obj.toJSONString();
		byte[] b = json.getBytes();
		 
		System.out.println("Num of bytes = " + json.getBytes().length);

		DatagramPacket dp = new DatagramPacket(b, b.length, ipAddress, udp_Port);
		c = new Client(dp, 8888);

		MsgPacket msgPacket = new MsgPacket(c, json, dp);

		return msgPacket;

	}

	/**
	 * // * @param args //
	 */
	// public static void main(String[] args) {
	//
	// }


	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
	}


}
