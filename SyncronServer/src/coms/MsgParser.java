/**
 * 
 */
package coms;


import java.util.Map;

import net.sf.json.JSONObject;

import org.perf4j.aop.Profiled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codesnippets4all.json.parsers.JSONParser;
import com.codesnippets4all.json.parsers.JsonParserFactory;

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
		 
		String jsonString = msgPacket.getJsonMsg();
		log.info("Parsing JSON string");
		 JsonParserFactory factory=JsonParserFactory.getInstance();
		 JSONParser parser = factory.newJsonParser();
		 Map<String,Object> jMap = parser.parseJson(jsonString);
		 msgPacket.setjMap(jMap);
		
		return jMap;

	}

}
