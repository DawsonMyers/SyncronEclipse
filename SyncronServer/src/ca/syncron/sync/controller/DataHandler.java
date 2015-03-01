/**
 * handles all data transaction
 */
package ca.syncron.sync.controller;

//import sync.controller.ServerController;
import ca.syncron.msg.DbBundle;
import ca.syncron.msg.NodeMsgData;
import ca.syncron.msg.SqlMachine;

/**
 * @author Dawson
 *
 */
public class DataHandler {
	ServerController	controller	= ServerController.getInstance();
	static DataHandler dataHandler = new DataHandler();
	
	public static DataHandler getInstance() {
		  if(dataHandler == null) {
		     synchronized(DataHandler.class) {
		       if(dataHandler == null) {
		    	   dataHandler = new DataHandler();
		       }
		    }
		  }
		  return dataHandler;
		}
 
	
	// controller data interface
	// ///////////////////////////////////////////////////////////////////////////////////

	// Node data access
	// ////////////////////////////////////////////////////////////////

	public synchronized NodeMsgData getNodeData() {
		NodeMsgData msgData;
		try {
			msgData = controller.nodeData.getNodeMsgData();
		} catch (Exception e) {
			e.printStackTrace();
			msgData = new NodeMsgData();
		}
		return msgData;
				
	}

	public synchronized void getNodeData(NodeMsgData nodeMsgData) {
		try {
			controller.nodeData.setNodeMsgData(nodeMsgData);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("[DataHandler::setNodeMsgData] >> ERROR SETTING NodeMsgData");
		}
	}

	public synchronized void setNodeData(NodeMsgData nodeMsgData) {
		try {
			controller.nodeData.setNodeMsgData(nodeMsgData);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("[DataHandler::setNodeMsgData] >> ERROR GETTING NodeMsgData");
		}
	}

	// Database data access
	// ////////////////////////////////////////////////////////////////

	public synchronized DbBundle getFromDatabase(DbBundle dbBundle) {
		try {
			dbBundle = new SqlMachine(dbBundle).getSql();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("[DataHandler::getFromDatabase] >> ERROR GETTING DATA FROM DATABASE");
		}
		return dbBundle;
	}

	public synchronized int[] getAnalogArray() {
		return  controller.nodeData.getAnalogVals();
	}
	public synchronized void setAnalogValue(int index, int value) {
		   controller.nodeData.setAnalogValue(index, value);
	}

}
