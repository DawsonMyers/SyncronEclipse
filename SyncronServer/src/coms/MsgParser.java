/**
 * 
 */
package coms;

//import org.json.simple.JSONObject;
//import org.json.simple.parser.JSONParser;
//import org.json.simple.parser.ParseException;
import java.util.Map;

import net.sf.json.JSONObject;

import org.perf4j.aop.Profiled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codesnippets4all.json.parsers.JSONParser;
import com.codesnippets4all.json.parsers.JsonParserFactory;
//import com.google.gson.JsonParser;
/**
 * @author Dawson
 *
 */
public class MsgParser {
public final static Logger log = LoggerFactory.getLogger(MsgParser.class.getName());
	public static JSONObject	json;
	public Map msgMap;
	/**
	 * 
	 */
	public MsgParser() {}
	@Profiled
	public static Map<String,Object> parseMsg(MsgPacket msgPacket) {
		 //JSONSerializer parser = new JSONSerializer();
		String jsonString = msgPacket.getJsonMsg();
		log.info("Parsing JSON string");
		 JsonParserFactory factory=JsonParserFactory.getInstance();
		 JSONParser parser = factory.newJsonParser();
		 Map<String,Object> jMap = parser.parseJson(jsonString);
		 
		 //Map<String,String> jsonData = parser.parseJson(jsonString);

		 //String value=(String)jsonData.get("key2");
		
		// parser.
//		try {
//			json = (JSONObject) JSONSerializer.toJSON(jsonString);
//		} catch ( Exception e) {
//			e.printStackTrace();
//		}
		
		// extract data from json obj
		//msgPacket.extractJsonData(jMap);
		return jMap;

	}

}
