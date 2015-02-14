/**
 * 
 */
package coms;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.json.simple.JSONObject;

/**
 * @author Dawson
 *
 */
public class ActiveMsg implements ComConstants {

	public String	PROTOCOL	= "protocol";
	public String	CMD			= "cmd";
	public String	TARGET_ID	= "target";
	public String	PIN			= "pin";
	public String	VALUE		= "value";

	public String	protocol	= null;
	public String	cmd			= null;
	public String	targetId	= null;
	public String		pin			= ""; 
	public String	value		= null;
	public Client client  = null;
	
	

	// obj.put("protocol", "syncron");
	// obj.put("cmd", "digital");
	// obj.put("pin", "digital");
	// obj.put("value", s);

	/**
	 * 
	 */
	public ActiveMsg() {}

	public ActiveMsg(JSONObject json) {


		protocol = (String) json.get(PROTOCAL).toString();
		cmd = (String) json.get(CMD).toString();
		targetId = (String) json.get(TARGET_ID).toString();
		pin = (String) json.get(PIN).toString();
		value = (String) json.get(VALUE).toString();

	}
	public void init(JSONObject json) {
		
		
		protocol =  json.get(PROTOCAL).toString();
		cmd = (String) json.get(CMD).toString();
		targetId = (String) json.get(TARGET_ID).toString();
		pin = (String) json.get(PIN).toString();
		value = (String) json.get(VALUE).toString();
		
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
