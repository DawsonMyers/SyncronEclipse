/**
 * 
 */
package coms.tcp.node;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import coms.ComConstants;
import coms.tcp.server.ServerHandlerTcp;
import coms.udp.MsgMetaData;

/**
 * @author Dawson
 *
 */
public class ClientMsg extends MsgMetaData implements ComConstants {
	public final static Logger	log	= LoggerFactory.getLogger(ClientMsg.class.getName());

	public ClientMsg() {}

	public ClientMsg(NodeClientTcp client, String msg) {
		mClient = client;
		clientHandler = client.handler;
		setJsonMsg(msg);
		parseJsonToMap();
	}

}
