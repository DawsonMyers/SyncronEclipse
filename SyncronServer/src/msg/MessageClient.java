package msg;

import sync.controller.ServerController;

public class MessageClient implements MsgConstants {
	ServerController				controller	= ServerController.getInstance();

	public static String			IP			= IP_HOME;							// "192.168.1.109";
	public static int				port		= PORT_SERVER;						// 6005;
	public static MessageWrapper	msg			= new MessageWrapper();

	public synchronized static void main(String[] args) {
		//for (int count = 0; count < 5; count++) {

			ObjectMessengerThread messenger = new ObjectMessengerThread(IP, port);
			messenger.setName("MsgClientTread");
			
			long msgTime = System.currentTimeMillis();
			System.out.println("Message sent to server at time" + msgTime + "ms");
			
			// set message request id and query so that the server will be able
			// to get the data that was requested
			msg.setRequestId(SQL);
			msg.setRequestSql(QUERY4);
			msg = messenger.sendReqest(msg, IP, port);

			msgTime = System.currentTimeMillis() - msgTime;
			System.out.println("Message Receivedo from server at time" + msgTime + "ms");
			System.out.println(msg.messageObj.dbBundle.rowData);
			//}	for
		// messenger.start();
		// messenger.sendMessage(msg);
		// synchronized (messenger) {
		// try {
		// messenger.wait( );
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// }
		//
		// }
		System.out.println("terminating");
}

}
