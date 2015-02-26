/**
 * 
 */
package coms.udp;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import coms.ComConstants;
import coms.tcp.AbstractTcpHandler;
import coms.tcp.server.ServerHandlerTcp;

/**
 * @author Dawson
 *
 */
public class MsgMetaData implements ComConstants {
	public final static Logger	log			= LoggerFactory.getLogger(MsgMetaData.class.getName());

	public String				mJsonMsg		= "";
	public Map<String, Object>	jMap			= new HashMap<>();
	public AbstractTcpHandler	tcpHandler	= ServerHandlerTcp.getInstance();

	public String				protocol		= "";
	public String				type			= "";
	public String				targetId		= "";
	public String				senderId		= "";
	public String				senderType	= "";
	public String				messageId		= "";
	public String				dataId		= "";
	public String				pin			= "";
	public String				value		= "";
	public String				adminId		= "";

	public String				networkId		= "";
	public String				clientId		= "";

	public MsgMetaData() {}

	public MsgMetaData(Map<String, Object> jMap) {
		this.jMap = jMap;
		initMetaData();
	}

	/**
	 * @return object jMap of type Map<String,Object>
	 */
	public Map<String, Object> getjMap() {
		return this.jMap;
	}

	/**
	 * @param jMap
	 *             the jMap to set
	 */
	public void setjMap(Map<String, Object> jMap) {
		this.jMap = jMap;
	}

	/**
	 * 
	 */
	public void initMetaData() {
		type = extractMetaData(fMESSAGE_TYPE);
		targetId = extractMetaData(fTARGET_ID);
		senderId = extractMetaData(fSENDER_ID);
		senderType = extractMetaData(fSENDER_TYPE);
		messageId = extractMetaData(fMESSAGE_ID);
		dataId = extractMetaData(fDATA_ID);
		adminId = extractMetaData(fDATA_ID);
	}

	/**
	 * @param ftype
	 * @return
	 */
	private String extractMetaData(String field) {
		if (jMap.containsKey(field)) {
			return (String) jMap.get(field);
		} else {
			//log.error("Message field: " + field + " does not exist in received message");
			return "ERROR";
		}
	}
//
// ///////////////////////////////////////////////////////////////////////////////////

	
	
	
	
	
	// Message field setters/getters
	// ///////////////////////////////////////////////////////////////////////////////////

	/**
	 * @return object protocol of type String
	 */
	public String getProtocol() {
		return this.protocol;
	}

	/**
	 * @param protocol
	 *             the protocol to set
	 */
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	/**
	 * @return object type of type String
	 */
	public String getType() {
		return this.type;
	}

	/**
	 * @param type
	 *             the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return object targetId of type String
	 */
	public String getTargetId() {
		return this.targetId;
	}

	/**
	 * @param targetId
	 *             the targetId to set
	 */
	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}

	/**
	 * @return object senderId of type String
	 */
	public String getSenderId() {
		return this.senderId;
	}

	/**
	 * @param senderId
	 *             the senderId to set
	 */
	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}

	/**
	 * @return object senderType of type String
	 */
	public String getSenderType() {
		return this.senderType;
	}

	/**
	 * @param senderType
	 *             the senderType to set
	 */
	public void setSenderType(String senderType) {
		this.senderType = senderType;
	}

	/**
	 * @return object messageId of type String
	 */
	public String getMessageId() {
		return this.messageId;
	}

	/**
	 * @return object tcpHandler of type AbstractTcpHandler
	 */
	public AbstractTcpHandler getTcpHandler() {
		return this.tcpHandler;
	}

	/**
	 * @param tcpHandler the tcpHandler to set
	 */
	public void setTcpHandler(AbstractTcpHandler tcpHandler) {
		this.tcpHandler = tcpHandler;
	}

	/**
	 * @param messageId
	 *             the messageId to set
	 */
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	/**
	 * @return object dataId of type String
	 */
	public String getDataId() {
		return this.dataId;
	}

	/**
	 * @param dataId
	 *             the dataId to set
	 */
	public void setDataId(String dataId) {
		this.dataId = dataId;
	}

	/**
	 * @return object adminId of type String
	 */
	public String getAdminId() {
		return this.adminId;
	}

	/**
	 * @param adminId
	 *             the adminId to set
	 */
	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}

	/**
	 * @return object networkId of type String
	 */
	public String getNetworkId() {
		return this.networkId;
	}

	/**
	 * @param networkId
	 *             the networkId to set
	 */
	public void setNetworkId(String networkId) {
		this.networkId = networkId;
	}

	/**
	 * @return object clientId of type String
	 */
	public String getClientId() {
		return this.clientId;
	}

	/**
	 * @param clientId
	 *             the clientId to set
	 */
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	
	
	
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
