/**
 * 
 */
package sync.serial;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.zu.ardulink.Link;
import org.zu.ardulink.event.AnalogReadChangeEvent;
import org.zu.ardulink.event.AnalogReadChangeListener;
import org.zu.ardulink.event.ConnectionEvent;
import org.zu.ardulink.event.ConnectionListener;
import org.zu.ardulink.event.DisconnectionEvent;

/**
 * @author Dawson
 *
 */
public class ArdulinkSerial implements SerialConstants{

	
	
	public static int[]		serialAnalogVals	= new int[ANALOG_PINS];

	public static String	PORT				= "COM8";
	public static int		pin					= 3;
	public static int		pinVar				= 2;
	public static int		delay				= 100;
	public static int		pinVar1				= 2;
	private static int		quit;
	private static boolean	stop				= false;
	public static int		pinQuit				= 10;
	private static int		noLoop				= 0;
	public static boolean	pinStatus3			= false;

	/**
	 * 
	 */


	public static void main(String[] args) {


		Link link = Link.getDefaultInstance();

		link.addConnectionListener(new ConnectionListener() {

			@Override
			public void disconnected(DisconnectionEvent e) {
				System.out.println("Board disconnected");
			}

			@Override
			public void connected(ConnectionEvent e) {
				System.out.println("Board connected");
			}
		});

		link.connect(PORT);
		
		
		link.addAnalogReadChangeListener(new AnalogReadChangeListener() {
			/*
			 * (non-Javadoc)
			 * @see
			 * org.zu.ardulink.event.AnalogReadChangeListener#getPinListening()
			 */
			@Override
			public int getPinListening() {
				return pin;
			}

			@Override
			public void stateChanged(AnalogReadChangeEvent e) {

				serialAnalogVals[pin] = e.getValue();
				System.out.println(e.getValue());

			}

		});
	}

	public ArdulinkSerial() {


	}


}
