/**
 * 
 */
package sync.controller;

import msg.MessageWrapper;
import msg.MsgConstants;
import msg.NodeMsgData;

/**
 * @author Dawson Main server singleton class to store all global application
 *         data. 
 *         access with: ServerController controller =
 *         ServerController.getInstance();
 */
public class ServerController implements MsgConstants {
	private static ServerController	serverController	= new ServerController();

	// handle received messages
	public static DataHandler		dataHandler			= DataHandler.getInstance();

	MessageWrapper					msg					= new MessageWrapper();
	public static NodeData			nodeData			= new NodeData();

	/**
	 * A private Constructor prevents any other class from instantiating.
	 */
	private ServerController() {}

	public synchronized static ServerController getInstance() {
		return serverController;
	}
}
