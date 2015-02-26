/**
 * 
 */
package coms;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.util.Date;

import coms.tcp.server.ServerTcp;
import naga.NIOSocket;

/**
 * @author Dawson
 *
 */
public class User_old {

	public InetAddress		ip			= null;
	public int			port			= 0;
	public String			id			= "";
	public String			mDeviceId		= "";
	public int			sendCount		= 0;
	public int			receiveCount	= 0;
	public Date			dateAdded		= null;
	public DatagramPacket	mPacket		= null;
	public long			lastActiveTime	= System.currentTimeMillis();
	public MsgPacket		mMsgPacket	= null;

	public String			mNetworkId	= "";
	public String			mClientId		= "";
	// = new
	// DatagramPacket(UdpBuffer,
	// UdpBuffer.length,
	// receiverAddress,
	// udpPort);
	public SocketAddress	address		= null;

	/**
	 * 
	 */
	public User_old() {}

	
	/**
	 * @param serverTcp
	 * @param nioSocket
	 */
//	public User(ServerTcp serverTcp, NIOSocket nioSocket) {}
	public User_old(MsgPacket msgPacket) { // , String id) {
		mMsgPacket = msgPacket;
		mPacket = mMsgPacket.getDp();
		this.id = mPacket.getSocketAddress().toString();
		port = mPacket.getPort();
		ip = mPacket.getAddress();
		address = mPacket.getSocketAddress();
		mNetworkId =mPacket.getSocketAddress().toString();
		
		setId();
		System.out.println("Id to add = " + mClientId);
		
		if (!UdpHandler.connectedClients.containsKey(getId())) {
			UdpHandler.connectedClients.put(getId(), this);
			System.out.println("id added");
		}

	}

	// Setters/Getters
	// ///////////////////////////////////////////////////////////////////////////////////

	
	/**
	 * @return object lastActiveTime of type long
	 */
	public long getLastActiveTime() {
		return this.lastActiveTime;
	}

	/**
	 * @param lastActiveTime
	 *             the lastActiveTime to set
	 */
	public void setLastActiveTime() {
		this.lastActiveTime = System.currentTimeMillis();
	}

	/**
	 * @return object address of type SocketAddress
	 */
	public SocketAddress getAddress() {
		return this.address;
	}

	/**
	 * @param address
	 *             the address to set
	 */
	public void setAddress(SocketAddress address) {
		this.address = address;
	}

	/**
	 * @return object port of type int
	 */
	public int getPort() {
		return this.port;
	}

	/**
	 * @param port
	 *             the port to set
	 */
	public void setPort(int port) {
		this.port = port;
	}

	public void incSend() {
		sendCount++;
	}

	public void incReceive() {
		receiveCount++;
	}

	/**
	 * @return object ip of type InetAddress
	 */
	public InetAddress getIp() {
		return this.ip;
	}

	/**
	 * @param ip
	 *             the ip to set
	 */
	public void setIp(InetAddress ip) {
		this.ip = ip;
	}

	/**
	 * @return object id of type String
	 */
	public String getId() {
		return this.id;
	}

	/**
	 * @param id
	 *             the id to set
	 */
	public void setId() {
		//id = (ip.getHostAddress().toString().replace("/", "") + "_" + port);
		id = (mMsgPacket.getSenderType() + getAddress());
		mClientId = id;
		System.out.println(mClientId);
				

	}

	/**
	 * @return object deviceId of type String
	 */
	public String getDeviceId() {
		return this.mDeviceId;
	}

	/**
	 * @param deviceId
	 *             the deviceId to set
	 */
	public void setDeviceId(String deviceId) {
		this.mDeviceId = deviceId;
	}

	/**
	 * @return object sendCount of type int
	 */
	public int getSendCount() {
		return this.sendCount;
	}

	/**
	 * @param sendCount
	 *             the sendCount to set
	 */
	public void setSendCount(int sendCount) {
		this.sendCount = sendCount;
	}

	/**
	 * @return object receiveCount of type int
	 */
	public int getReceiveCount() {
		return this.receiveCount;
	}

	/**
	 * @param receiveCount
	 *             the receiveCount to set
	 */
	public void setReceiveCount(int receiveCount) {
		this.receiveCount = receiveCount;
	}

	/**
	 * @return object dateAdded of type Date
	 */
	public Date getDateAdded() {
		return this.dateAdded;
	}

	/**
	 * @param dateAdded
	 *             the dateAdded to set
	 */
	public void setDateAdded(Date dateAdded) {
		this.dateAdded = dateAdded;
	}

	/**
	 * 
	 */
	public void init(MsgPacket msgPacket) {
		
		mMsgPacket = msgPacket;
		mMsgPacket.setDp(mPacket);
		mMsgPacket.setClientId(mClientId);
		System.out.println(mClientId);
		
	}

}
