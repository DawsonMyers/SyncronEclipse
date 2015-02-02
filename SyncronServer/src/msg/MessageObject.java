package msg;

import java.io.Serializable;

/**
 * Created by Dawson on 1/29/2015.
 */
public class MessageObject implements Serializable, MsgConstants {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6985940817546646082L;
	public int mMsgId, mObjectId, mUserId, mActionId, mStatus;
	public String[] mStringsArgs;
	public int[] mIntArgs;
	public String mIntent;

	public NodeMsgData nodeData = new NodeMsgData();
	public MsgObject msgObj = new MsgObject();
	public DbBundle dbBundle = new DbBundle();
	Long mTime;
	
	public MessageObject(){}
	
	// data members
	// ///////////////////////////////////////////////////////////////////////////////////
	// Analog input values
	public int[] analogVals = null;
	// Digital inputs/outputs
	public boolean keepStreaming = false;
	public boolean[] digitalInput = null;

	public boolean[] digitalOutput = null;
	Object mMsgObject;
	// methods
	// ///////////////////////////////////////////////////////////////////////////////////
	public synchronized int[] getAnalogVals() {
		return this.analogVals.clone();
	}

	public synchronized void setAnalogVals(int[] analogVals) {
		this.analogVals = analogVals.clone();
	}
	public void setAnalogValues(int[] analogVals) {
		nodeData.analogVals = analogVals;
	}
	public int[] getAnalogValues() {
		return nodeData.analogVals = analogVals;
	}

	public void setRequestSql(String query) {
		dbBundle.sqlQuery = query;
	}
}
