/**
 * handles all data transaction
 */
package sync.controller;

import msg.DbBundle;
import msg.NodeMsgData;
import msg.SqlMachine;

/**
 * @author Dawson
 *
 */
public class DataHandler {
	ServerController	controller	= ServerController.getInstance();


	// controller data interface
	// ///////////////////////////////////////////////////////////////////////////////////

	// Node data access
	// ////////////////////////////////////////////////////////////////

	public synchronized NodeMsgData getNodeData() {
		return controller.nodeData.getNodeMsgData();
	}

	public synchronized void getNodeData(NodeMsgData nodeMsgData) {
		controller.nodeData.setNodeMsgData(nodeMsgData);
	}

	public synchronized void setNodeData(NodeMsgData nodeMsgData) {
		controller.nodeData.setNodeMsgData(nodeMsgData);
	}

	// Database data access
	// ////////////////////////////////////////////////////////////////

	public synchronized DbBundle getFromDatabase(DbBundle dbBundle) {
		dbBundle = new SqlMachine(dbBundle).getSql();
		return dbBundle;
	}



}
