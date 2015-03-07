/**
 * 
 */
package ca.syncron.coms.message;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author Dawson
 *
 */
@JsonInclude(Include.NON_NULL)
public class Message {
	public final static Logger	log	= LoggerFactory.getLogger(Message.class.getName());

	public enum MessageType {
		DIGITAL, ANALOG, ADMIN, UPDATE, REGISTER, LOGIN, STATUS, CHECKIN, USER, STREAM, CHAT, QUERY;
	}

	public enum UserType {
		NODE, SERVER, ANDROID;
	}

	public enum Chat {
		REGISTER, UPDATE, LOGIN, USERS, DISCONNECT;
	}

	public Message() {}

	public Message(MessageType msgType, UserType senderType) {
		mMessageType = msgType;
		mUserType = senderType;
	}

	MessageType		mMessageType;
	String			mPin;
	String			mValue;
	String			mUserId;
	UserType			mUserType;
	String			mSenderType;
	String			mSenderId;
	String			mTargetId;
	String			mTargetType;
	String			mChatMessage;
	String			mTestString;
	String			mLogin;
	String			mAdminId;
	Chat				mChatType;

	String			mQuery;
	Map<String, String>	mQueryResult;

	boolean			mAdmin;
	boolean			mReqResponse;

	int[]			mAnalogValues;
	boolean[]			mDigitalValues;

	String			mAdminCommad;


 

	/**
	 * @return object messageType of type MessageType
	 */
	public MessageType getMessageType() {
		return this.mMessageType;
	}

	/**
	 * @param messageType
	 *             the messageType to set
	 */
	public void setMessageType(MessageType messageType) {
		this.mMessageType = messageType;
	}

	/**
	 * @return object pin of type String
	 */
	public String getPin() {
		return this.mPin;
	}

	/**
	 * @param pin
	 *             the pin to set
	 */
	public void setPin(String pin) {
		this.mPin = pin;
	}

	/**
	 * @return object value of type String
	 */
	public String getValue() {
		return this.mValue;
	}

	/**
	 * @param value
	 *             the value to set
	 */
	public void setValue(String value) {
		this.mValue = value;
	}

	/**
	 * @return object userId of type String
	 */
	public String getUserId() {
		return this.mUserId;
	}

	/**
	 * @param userId
	 *             the userId to set
	 */
	public void setUserId(String userId) {
		this.mUserId = userId;
	}

	/**
	 * @return object userType of type UserType
	 */
	public UserType getUserType() {
		return this.mUserType;
	}

	/**
	 * @param userType
	 *             the userType to set
	 */
	public void setUserType(UserType userType) {
		this.mUserType = userType;
	}

	/**
	 * @return object senderType of type String
	 */
	public String getSenderType() {
		return this.mSenderType;
	}

	/**
	 * @param senderType
	 *             the senderType to set
	 */
	public void setSenderType(String senderType) {
		this.mSenderType = senderType;
	}

	/**
	 * @return object senderId of type String
	 */
	public String getSenderId() {
		return this.mSenderId;
	}

	/**
	 * @param senderId
	 *             the senderId to set
	 */
	public void setSenderId(String senderId) {
		this.mSenderId = senderId;
	}

	/**
	 * @return object targetId of type String
	 */
	public String getTargetId() {
		return this.mTargetId;
	}

	/**
	 * @param targetId
	 *             the targetId to set
	 */
	public void setTargetId(String targetId) {
		this.mTargetId = targetId;
	}

	/**
	 * @return object targetType of type String
	 */
	public String getTargetType() {
		return this.mTargetType;
	}

	/**
	 * @param targetType
	 *             the targetType to set
	 */
	public void setTargetType(String targetType) {
		this.mTargetType = targetType;
	}

	/**
	 * @return object chatMessage of type String
	 */
	public String getChatMessage() {
		return this.mChatMessage;
	}

	/**
	 * @param chatMessage
	 *             the chatMessage to set
	 */
	public void setChatMessage(String chatMessage) {
		this.mChatMessage = chatMessage;
	}

	/**
	 * @return object testString of type String
	 */
	public String getTestString() {
		return this.mTestString;
	}

	/**
	 * @param testString
	 *             the testString to set
	 */
	public void setTestString(String testString) {
		this.mTestString = testString;
	}

	/**
	 * @return object login of type String
	 */
	public String getLogin() {
		return this.mLogin;
	}

	/**
	 * @param login
	 *             the login to set
	 */
	public void setLogin(String login) {
		this.mLogin = login;
	}

	/**
	 * @return object adminId of type String
	 */
	public String getAdminId() {
		return this.mAdminId;
	}

	/**
	 * @param adminId
	 *             the adminId to set
	 */
	public void setAdminId(String adminId) {
		this.mAdminId = adminId;
	}

	/**
	 * @return object chatType of type Chat
	 */
	public Chat getChatType() {
		return this.mChatType;
	}

	/**
	 * @param chatType
	 *             the chatType to set
	 */
	public void setChatType(Chat chatType) {
		this.mChatType = chatType;
	}

	/**
	 * @return object query of type String
	 */
	public String getQuery() {
		return this.mQuery;
	}

	/**
	 * @param query
	 *             the query to set
	 */
	public void setQuery(String query) {
		this.mQuery = query;
	}

	/**
	 * @return object queryResult of type Map<String,String>
	 */
	public Map<String, String> getQueryResult() {
		return this.mQueryResult;
	}

	/**
	 * @param queryResult
	 *             the queryResult to set
	 */
	public void setQueryResult(Map<String, String> queryResult) {
		this.mQueryResult = queryResult;
	}

	/**
	 * @return object admin of type boolean
	 */
	public boolean isAdmin() {
		return this.mAdmin;
	}

	/**
	 * @param admin
	 *             the admin to set
	 */
	public void setAdmin(boolean admin) {
		this.mAdmin = admin;
	}

	/**
	 * @return object reqResponse of type boolean
	 */
	public boolean isReqResponse() {
		return this.mReqResponse;
	}

	/**
	 * @param reqResponse
	 *             the reqResponse to set
	 */
	public void setReqResponse(boolean reqResponse) {
		this.mReqResponse = reqResponse;
	}

	/**
	 * @return object analogValues of type int[]
	 */
	public int[] getAnalogValues() {
		return this.mAnalogValues;
	}

	/**
	 * @param analogValues
	 *             the analogValues to set
	 */
	public void setAnalogValues(int[] analogValues) {
		this.mAnalogValues = analogValues;
	}

	/**
	 * @return object digitalValues of type boolean[]
	 */
	public boolean[] getDigitalValues() {
		return this.mDigitalValues;
	}

	/**
	 * @param digitalValues
	 *             the digitalValues to set
	 */
	public void setDigitalValues(boolean[] digitalValues) {
		this.mDigitalValues = digitalValues;
	}

	/**
	 * @return object adminCommad of type String
	 */
	public String getAdminCommad() {
		return this.mAdminCommad;
	}

	/**
	 * @param adminCommad
	 *             the adminCommad to set
	 */
	public void setAdminCommad(String adminCommad) {
		this.mAdminCommad = adminCommad;
	}
}
