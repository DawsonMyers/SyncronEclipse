/**
 * 
 */
package ca.syncron.sync.serial;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zu.ardulink.Link;
import org.zu.ardulink.event.ConnectionEvent;
import org.zu.ardulink.event.ConnectionListener;
import org.zu.ardulink.event.DisconnectionEvent;

import ca.syncron.sync.controller.ServerController;
import ca.syncron.sync.main.RunClient;
 

/**
 * @author Dawson
 *
 */
public class ArdulinkSerial extends Thread implements SerialConstants{
public final static Logger log = LoggerFactory.getLogger(ArdulinkSerial.class.getName());
	ServerController	controller	= ServerController.getInstance();
	String tag = this.getName();
	public static int[]		serialAnalogVals	= new int[ANALOG_PINS];
 @Syncron
 //@TODO     
	public static String	PORT				= PORT_SERIAL_LINUX;//PORT_SERIAL_LINUX;	//"/dev/ttyS10"; //			"COM8";PORT_SERIAL_WINDOWS
 public static String	tempPort				= 	null;
	public static int		pin					= 3;
	public static int		pinVar				= 2;
	public static int		delay				= 100;
	public static int		pinVar1				= 2;
	private static int		quit;
	private static boolean	stop				= false;
	public static int		pinQuit				= 10;
	private static int		noLoop				= 0;
	public static boolean	pinStatus3			= false;
	public static Link link;
	public static AnalogPin[] analogPins	= new AnalogPin[ANALOG_PINS];
	//public static Map<AnalogPins> analogPinsAl= ;
	/**
	 * 
	 */
public static ArdulinkSerial aSerial = new ArdulinkSerial();
 
	//public static void main(String[] args) {
public void run() {
//	tempPort = RunClient.getSerrialPort();
//	if (tempPort  != null) {
//		PORT = tempPort;
//	}
//		udpServerHandler = new UdpServerHandler();
//		udpServerHandler.start();
		//udpHandler = new UdpHandler();
		//udpHandler.start();
		  link = Link.getDefaultInstance();

		link.addConnectionListener(new ConnectionListener() {

			@Override
			public void disconnected(DisconnectionEvent e) {
				System.out.println("Disconnected from Arduino");
			}

			@Override
			public void connected(ConnectionEvent e) {
				System.out.println("Connected to Arduino");
			}
		});

		link.connect(PORT);
		log.debug(tag,"Setting analog pins");
		for(int i = 0; i < analogPins.length; i++) {
			analogPins[i] = new AnalogPin();
			analogPins[i].initPin(link, i, aSerial);
		}
		
		

	}

	public ArdulinkSerial() {
		
		aSerial = this;
	}

	public static ArdulinkSerial getInstance() {
		
		return aSerial;
		
	}
	
	
	public static void setPin(int pin, int state) {
		link = Link.getDefaultInstance();
		link.sendPowerPinSwitch(pin, state);
		if(!link.isConnected()) System.out.println("ARDUINO IS NOT CONNECTED");
	}

}
