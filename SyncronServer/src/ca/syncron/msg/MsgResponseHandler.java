/**
 * 
 */
package ca.syncron.msg;

 
import ca.syncron.sync.controller.ServerController;

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
				try {
					 
					msg.messageObj.nodeData = controller.dataHandler.getNodeData();
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("[MsgResponseHandler] >> ERROR GETTING NODE DATA");
				}
				
				break;
			case SQL:
				try {
					msg.messageObj.dbBundle = controller.dataHandler.getFromDatabase(msg.messageObj.dbBundle);
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("[MsgResponseHandler] >> ERROR GETTING DATA FROM DATABASE");
				}
				break;
			case TEST:
				System.out.println("Test message responder");
				 
				break;
			default:
				break;
	}
	}
}
