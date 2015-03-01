package ca.syncron.socketmsg;

import ca.syncron.msg.MessageWrapper;

public interface IMessenger {

 

	public void 			sendMessage(MessageWrapper msg);
	public MessageWrapper 	readMessage( );
	//public void sendMessage(MessageWrapper msg, ObjectStreamer s);
	//public MessageWrapper readMessage(ObjectStreamer s );

}
