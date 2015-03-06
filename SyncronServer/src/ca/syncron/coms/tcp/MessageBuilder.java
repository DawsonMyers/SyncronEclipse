/**
 * 
 */
package ca.syncron.coms.tcp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.mail.handlers.image_gif;

import ca.syncron.coms.ComConstants;
import ca.syncron.sync.system.SyncUtils;

/**
 * @author Dawson
 *
 */
public class MessageBuilder implements ComConstants {
	public final static Logger	log	= LoggerFactory.getLogger(MessageBuilder.class.getName());

	public static String		JSON_MSG_TEMPLATE;

	public static void main(String[] args) {
		
		System.out.println("sendMessage({message_type:\"digital\",sender_type:\"node\",value:\"TEST FROM NODE\",target_id:\"android\"}");
		
		MessageBuilder m = new MessageBuilder();
	}

	@SuppressWarnings({ "unchecked", "unused" })
	public MessageBuilder() {

		StringBuilder sb = new StringBuilder();
		JSON_MSG_TEMPLATE = "{";

		sb.append("{");
		sb.append(fMESSAGE_TYPE + mSEP);
		sb.append(fCONTENT + mSEP);
		// sb.append( + mSEP);
		// sb.append( + mSEP);
		// sb.append( + mSEP);

		JSONObject msg = new JSONObject();
		msg.put(fMESSAGE_TYPE, mNULL);
		msg.put(fCONTENT, mNULL);
		msg.put(fSENDER_TYPE, mNULL);
		msg.put(fADMIN_ID, mNULL);
		msg.put(fTARGET_ID, mNULL);
		msg.put(fDATA, mNULL);
		msg.put(fREQUIRE_ACK, mNULL);

		JSONObject dataChat = new JSONObject();
		msg.put(fMESSAGE, mNULL);

		JSONObject dataDigital = new JSONObject();
		dataDigital.put(fPIN, mNULL);
		dataDigital.put(fVALUE, mNULL);
		dataDigital.put(fSTATUS, mNULL);

		JSONObject dataAnalog = new JSONObject();
		dataAnalog.put(fVALUES, mNULL);

		JSONObject dataTest = new JSONObject();
		dataTest.put(fMESSAGE, mNULL);

		JSONObject dataQuery = new JSONObject();
		dataQuery.put(fQUERY, mNULL);
		dataQuery.put(fQUERY_RESULT, mNULL);

		JSONObject dataStream = new JSONObject();
		dataStream.put(fVALUE, mNULL);

		String[] array1 = new String[] { "bob", "mike", "rick", "shea", "dawson" };

		ArrayList<String> al = new ArrayList<>();
		for (int i = 0; i < array1.length; i++) {
			al.add(array1[i]);
		}

		String a1 = Arrays.toString(array1);

		JSONArray jarray = new JSONArray();
		// ArrayList<String> al = new ArrayList<>();
		jarray.addAll(al);

		System.out.println("TO STRING " + a1);
		dataAnalog.put(fVALUES, jarray);
		msg.replace(fDATA, dataAnalog);

		JSONParser p = new JSONParser();

		JSONObject jsonObject = new JSONObject();

		ArrayList<Object> sA =   getArray(msg, fDATA, fVALUES);
		System.out.println(sA.get(0));
//		JSONArray jAr = null;
//		JSONObject obj2 = null;
//		try {
//			jsonObject = (JSONObject) p.parse(msg.toJSONString());
//			obj2 = (JSONObject) jsonObject.get(fDATA);
//			jAr = (JSONArray) obj2.get(fVALUES);
//		} catch (ParseException e) {
//			e.printStackTrace();
//			SyncUtils.getDateBox();
//		}
//		Object[] ob = jAr.toArray();
//
//		System.out.println(jsonObject);
//		System.out.println(jAr.toJSONString());
//
//		for (int i = 0; i < array1.length; i++) {
//			array1[i] = (String) ob[i];
//			System.out.println(array1[i]);
//		}
//		JSONValue jVal = new JSONValue();
//
//		String strMsg = msg.toJSONString();
//		System.out.println(strMsg);
//
//		JSONObject getJ = new JSONObject();
//		getJ = (JSONObject) msg.get(fDATA);
//
//		System.out.println(getJ.toJSONString());

		//

		// ///////////////////////////////////////////////////////////////////////////////////
		/*
		 * System.out.println("=======decode======="); String s =
		 * "[0,{\"1\":{\"2\":{\"3\":{\"4\":[5,{\"6\":7}]}}}}]"; Object obj =
		 * JSONValue.parse(a1); //Object my = JSONValue.parse(a1); JSONArray
		 * array = (JSONArray) obj;
		 * System.out.println("======the 2nd element of array======");
		 * System.out.println(array.get(1)); System.out.println(); JSONObject
		 * obj2 = (JSONObject) array.get(1);
		 * System.out.println("======field \"1\"==========");
		 * System.out.println(obj2.get("1")); s = "{}"; obj =
		 * JSONValue.parse(s); System.out.println(obj); s = "[5,]"; obj =
		 * JSONValue.parse(s); System.out.println(obj); s = "[5,,2]"; obj =
		 * JSONValue.parse(s); System.out.println(obj); JSONParser parser =
		 * new JSONParser(); System.out.println("=======decode=======");
		 * String s1 = "[0,{\"1\":{\"2\":{\"3\":{\"4\":[5,{\"6\":7}]}}}}]";
		 * Object obj1 = null; try { obj1 = parser.parse(s1); } catch
		 * (ParseException e) { e.printStackTrace(); SyncUtils.getDateBox(); }
		 * JSONArray array11 = (JSONArray) obj1;
		 * System.out.println("======the 2nd element of array======");
		 * System.out.println(array11.get(1)); System.out.println();
		 * JSONObject obj21 = (JSONObject) array11.get(1);
		 * System.out.println("======field \"1\"==========");
		 * System.out.println(obj21.get("1")); s1 = "{}"; try { obj1 =
		 * parser.parse(s1); System.out.println(obj1); s1 = "[5,]"; obj1 =
		 * parser.parse(s1); System.out.println(obj1); s1 = "[5,,2]"; obj1 =
		 * parser.parse(s1); System.out.println(obj1); } catch (ParseException
		 * e) { e.printStackTrace(); SyncUtils.getDateBox(); } }
		 */

	}
	
	public ArrayList<Object> getArray(JSONObject obj,String jObjectName, String dataArrayName) {
		
		//JSONObject jsonObject = new JSONObject();

		JSONArray jArray = null;
		JSONObject dataObj = null;
		Object[] objArray = null;
		//jsonObject = (JSONObject) p.parse(msg.toJSONString());
		dataObj = (JSONObject) obj.get(jObjectName);
		jArray = (JSONArray) dataObj.get(dataArrayName);
//		JSONValue jValues = jArray;
//		ArrayList<Object> objList = jArray ;
//		objArray = jArray.toArray();
//
//		//System.out.println(jsonObject);
//		System.out.println(jArray.toJSONString());
//
//		for (int i = 0; i < objArray.length; i++) {
//			//objArray[i] = (String) objArray[i];
//			String s  = (String) objArray[i];
//			System.out.println(s);
//		}
		
		return jArray;
		
	}
}
