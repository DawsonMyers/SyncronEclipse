/**
 * 
 */
package msg;

import sync.controller.ServerController;

/**
 * @author Dawson
 *
 */

public  class MsgResponseHandler implements MsgConstants {
	public MessageWrapper mMsg;
	ServerController controller = ServerController.getInstance();
	 
	//	main
	// ///////////////////////////////////////////////////////////////////////////////////
	public MsgResponseHandler(MessageWrapper msg){
		switch (msg.getRequestId()) {
			case STREAM:
				msg.messageObj.nodeData = controller.dataHandler.getNodeData();
				break;
			case SQL:
				msg.messageObj.dbBundle = controller.dataHandler.getFromDatabase(msg.messageObj.dbBundle);
				break;
			case TEST:
				System.out.println("Test message responder");
				 
				break;
			default:
				break;
	}
	}
}
