/**
 * 
 */
package coms;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

import org.json.simple.JSONObject;

import sync.system.SyncUtils;
import msg.MsgConstants;

/**
 * @author Dawson
 *
 */
public class Tester implements ComConstants{

	
	
Client c;// = new Client();
	
	public static InetAddress ipAddress;
	 
	//DatagramPacket dp = new DatagramPacket(null, 0, null, 0 );
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

	
	public MsgPacket initPacket(String s) {
		
		  
			 
			 JSONObject obj=new JSONObject();

				obj.put("protocol", "syncron");
				obj.put("cmd", "digital");
				obj.put("pin", "digital");
				obj.put("value", s);

				String jason = obj.toJSONString();
				byte[] b = jason.getBytes();
				System.out.println(jason);
				System.out.println("Num of bytes = " + jason.getBytes().length);

				DatagramPacket dp = new DatagramPacket(b, b.length, ipAddress, udp_Port);
				 c = new Client(dp, 8888);
				
				MsgPacket msgPacket = new MsgPacket(c, jason, dp);
		
		return msgPacket;
		
	}
	
	/**
//	 * @param args
//	 */
//	public static void main(String[] args) {
//
//	}
	
	
	

}



