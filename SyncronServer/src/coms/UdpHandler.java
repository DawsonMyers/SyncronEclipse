/**
 * 
 */
package coms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import sync.system.SyncUtils;
/**
 * @author Dawson
 *
 */
public class UdpHandler {

 
	public static Map<String,Client> connectionMap = new HashMap<>();
	public static LinkedList<Msg> MessageQue = new LinkedList<>();
	
	public static UdpMessenger udpMessenger;
	public static Thread udpMessengerThrd;
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		udpMessenger = new UdpMessenger();
		
		udpMessengerThrd = new Thread(udpMessenger);
		udpMessengerThrd.start();
		 
		
		
		BufferedReader cin = new BufferedReader(new InputStreamReader(System.in));
		
		while(true) {
		String s = "empty";
		try {
			s = (String)cin.readLine();
		} catch (IOException e) {
			e.printStackTrace();
			SyncUtils.getDateBox();
		}
		
		//byte[] b = s.getBytes();
        Tester t = new Tester();
        MsgPacket packet = t.initPacket(s);
        UdpMessenger.sendUDP(packet );
		}
        
	}
	
	/**
	 * 
	 */
	public UdpHandler() {}

	public static synchronized void addToQue(Msg msg) {
		
		MessageQue.addLast(msg);
		System.out.println("[handler:addToQue] Msg added to List");
	}
	public static synchronized Msg nextFromQue() {
		return MessageQue.pollFirst();
		
	}
	public static synchronized int QueSize() {
		return MessageQue.size();
		
	}
	
	public static synchronized  void startMsgHandler() {
		
		Thread t = new Thread("MsgHandler") {
			
			JSONObject json = null;
			public void run() {
				if(QueSize() > 0) {
					handdleMsg();
				}else {
					try {
						sleep(50);
					} catch (InterruptedException e) {
						e.printStackTrace();
						SyncUtils.getDateBox();
					}
				}
			}

			private void handdleMsg() {
				Msg msg = nextFromQue();
				
				try {
					json = (JSONObject)new JSONParser().parse(msg.getJsonMsg());
				} catch (ParseException e) {
					e.printStackTrace();
				}
				
				json.put("REPLY", "added and replyed");
				
			}
		};
		t.start();
	  
}
	
	
	
	
	
}



