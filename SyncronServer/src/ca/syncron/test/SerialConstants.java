/**
 * 
 */
package ca.syncron.test;

import java.net.InetAddress;

/**
 * @author Dawson
 *
 */
public interface SerialConstants {
	
	public static int ANALOG_PINS = 12;
	public static String	PORT				= "COM8";

	public String	IP = "192.168.1.109";
	
	public int	PORT_CMD = 5000;
	public int	PORT_DATA= 5005;
	public static final String PROTOCAL_START = "<";
	public static final String PROTOCAL_END = ">";
	public static final String PROTOCAL = "syncron";
	public static final String PROTOCAL_CMD_DIVIDER= ":";
	public static final String PROTOCAL_VALUE_DIVIDER= "_";
	 
	
	
	
	public static final int ACT_ANALOG= 0;
	public static final int ACT_DIGITAL = 1;
	public static final int ACT_CMD = 30;
	
	
	public static final String protoID = "node";
	
	
	public static InetAddress		receiverAddress			= null;
	public static int				UdpBufferLength			= 49;
	public static byte[]			UdpBuffer				= new byte[UdpBufferLength];
	public static int				UdpInputBufferLength	= 10;
	public static byte[]			UdpInputBuffer			= new byte[UdpInputBufferLength];
	public static String			UdpInputBufferString	= new String(UdpInputBuffer);
	public static int				udpPort					= 10000;
	public static int				udpInputPort			= 10005;
	public static String			serverIP				= "192.168.1.109";
		
}



