/**
 * Holds data from node
 */
package msg;

import java.io.Serializable;

/**
 * @author Dawson 
 */
public class NodeMsgData implements Serializable  {
	// Analog input values
	public int[] analogVals = null;

	// Digital inputs/outputs
	public boolean[] digitalInput = null;

	public boolean[] digitalOutput = null;
}
