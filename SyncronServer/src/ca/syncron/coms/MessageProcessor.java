/**
 * 
 */
package ca.syncron.coms;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sync.system.SyncUtils;
 




import ca.syncron.coms.message.Message;
import ca.syncron.coms.message.Message.*;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * @author Dawson
 *
 */

public  class MessageProcessor {
	public static MessageProcessor mProcessor = new MessageProcessor();
	public final static Logger	log	= LoggerFactory.getLogger(MessageProcessor.class.getName());
	static Message msg;
	static StringWriter writer;
	static ObjectMapper mapper;
	
	public MessageProcessor() {
		writer = new StringWriter();
		mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
	}
	
 
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		MessageType type = MessageType.ADMIN;
		UserType userType = UserType.ANDROID;
		msg = new Message(type, userType);
		String j = serializeMessage(msg);
		System.out.println(j);
		
		Message msgFromStr = deserializeMessage(j);
		System.out.println(msgFromStr.getMessageType());
	}
	
	
	public static String serializeMessage(Message msg){
		  mapper = new ObjectMapper();

		try {
			mapper.writeValue(writer, msg);
		} catch (IOException e) {
			e.printStackTrace();
			SyncUtils.getDateBox();
		}
		return writer.toString();
	}
	
	public static Message deserializeMessage(String msgString) {
		try {
			msg =  mapper.readValue(msgString , Message.class);
		} catch (IOException e) {
			e.printStackTrace();
			SyncUtils.getDateBox();
		}
	return msg;
	}
		/*try {
//			MessageType type = MessageType.ADMIN;
//			UserType userType = UserType.ANDROID;
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
			mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
		 
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
			
			//  msg = new Message(type, userType);
			msg.setAdminId("daw");
			 
			mapper.writeValue(writer, msg);
			 String str = writer.toString();
			System.out.println(str);
			String json = mapper.writeValueAsString(msg);
			System.out.println(json);
			Message msg_1 = mapper.readValue(json , Message.class);
			System.out.println("msg_1.mMessageType = " + msg_1.getMessageType());
			
			// convert Object to json string
			Message msg1 = new Message();
			Message msg2 = new Message();
			
			

		

			msg1.setDigitalValues(new boolean[]{true});
			msg1.setMessageType(MessageType.CHAT);
			// writing to console, can write to any output stream such as
			// file
			
			mapper.writeValueAsString(msg1) ;
			System.out.println("Message JSON is\n" +  "");

			String jsonData = "";
			System.out.println("Value as string: " + jsonData);
			
//		 
		} catch (IOException e) {
			e.printStackTrace();
			SyncUtils.getDateBox();
		}
	}*/
	
	 

}

 

 