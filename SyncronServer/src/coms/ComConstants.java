/**
 *
 */
package coms;

import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * @author Dawson
 *
 */
public interface ComConstants {

	public static int			ANALOG_PINS			= 12;
	public static String		PORT					= "COM8";

	public int				PORT_CMD				= 5000;
	public int				PORT_DATA				= 5005;
	public static final String	PROTOCAL_START			= "<";
	public static final String	PROTOCAL_END			= ">";
	public static final String	PROTOCAL				= "syncron";
	public static final String	PROTOCAL_CMD_DIVIDER	= ":";
	public static final String	PROTOCAL_VALUE_DIVIDER	= "_";

	public static final int		ACT_ANALOG			= 0;
	public static final int		ACT_DIGITAL			= 1;
	public static final int		ACT_CMD				= 30;

	public static final String	protoID				= "node";

	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// [UDP]
	public static InetAddress	receiverAddress		= null;
	public static int			UdpBufferLength		= 49;
	public static byte[]		UdpBuffer				= new byte[UdpBufferLength];
	public static int			UdpInputBufferLength	= 10;
	public static byte[]		UdpInputBuffer			= new byte[UdpInputBufferLength];
	public static String		UdpInputBufferString	= new String(UdpInputBuffer);
	public static int			udp_Port				= 10005;
	public static int			udpInputPort			= 10005;
	public static String		serverIP				= "192.168.1.109";

	public static String		IP_HOME				= "192.168.1.109";
	public static String		IP					= IP_HOME;
	public static String		IP_SERVER				= "192.163.250.179";
	public static String		HTTP_SYNCRON			= "http://syncron.ca";
	public static String		HTTP_DAWSON			= "http://dawsonmyers.ca";
	public static String		HTTP_SERVER			= "http://dawsonmyers.ca";
	public static final int		PORT_SERVER			= 6005;
	public static final int		PORT_UPD_SERVER			= 10000;

	// /////////////// message fields
	// meta data - every msg has these fields
	public String				fPROTOCOL				= "protocol";
	public String				fTYPE				= "message_type";
	public String				fTARGET_ID			= "target_id";
	public String				fADMIN_ID				= "admin_id";
	public String				fSENDER_ID			= "sender_id";
	public String				fSENDER_TYPE			= "sender_type";
	public String				fMESSAGE_ID			= "message_id";
	public String				fMESSAGE_TYPE			= "message_type";
	public String				fDATA_ID				= "data_id";
	public String				fID					= "id";
	public String				fREQUIRE_ACK			= "require_ack";

	public String				fVALUE				= "value";
	public String				fPIN					= "pin";
	public String				fDATA				= "data";

	// Message types
	public String				tDIGITAL				= "digital";
	public String				tANALOG				= "analog";
	public String				tADMIN				= "admin";
	public String				tUPDATE				= "update";
	public String				tREGISTER				= "register";
	public String				tSTATUS				= "status";
	public String				tLOGIN				= "login";
	public String				tUSER				= "user";
	public String				tCHECKIN				= "checkin";


	public String				vSERVER				= "server";
	public String				vANDROID				= "android";
	public String				vNODE				= "node";

	public String				msgTRUE				= "1";
	public String				msgFAULSE				= "0";
}
