/**
 * 
 */
package ca.syncron.tester;

import java.io.BufferedReader;
import java.util.Scanner;

import org.zu.ardulink.*;
import org.zu.ardulink.event.DigitalReadChangeEvent;
import org.zu.ardulink.Link;
import org.zu.ardulink.event.ConnectionEvent;
import org.zu.ardulink.event.ConnectionListener;
import org.zu.ardulink.event.DigitalReadChangeEvent;
import org.zu.ardulink.event.DigitalReadChangeListener;
import org.zu.ardulink.event.DisconnectionEvent;
import org.zu.ardulink.protocol.IProtocol;

import ca.syncron.sync.serial.SerialConstants;

/**
 * @author Dawson
 *
 */

/*
public class Tester {

	
	*//**
	 * @param args
	 *//*
	public static void main(String[] args) {
		
	}
	
	private Link linkA;
	
	public void coordinate() {
		linkA = Link.createInstance("linkA");
		linkA.connect(PORT);
		

		linkA.addDigitalReadChangeListener(
		  new DigitalReadChangeListener() {

			@Override
			public void stateChanged(DigitalReadChangeEvent e) {

				linkB.sendPowerPinSwitch(12, e.getValue());
				linkC.sendPowerPinSwitch(13, e.getValue());

			}

			@Override
			public int getPinListening() {
				return 11;
			}
		});
	}
	*/

	public class InputTest implements SerialConstants {
		public static String PORT  =PORT_SERIAL_LINUX;// "COM4";

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
			Scanner sc;
			 do {
				 sc = new Scanner(System.in);
				System.out.println("Current Port: " + PORT);
				System.out.println("New port");
				String s = sc.nextLine();
				if (s != "" || s == "0") {
					PORT = s;

				}
				link.connect(PORT);
				if (link.isConnected()) {
					break;
				}
			} while (!link.isConnected());
			try {
				System.out.println("wait for a while");
				Thread.sleep(2000);
				System.out.println("proceed");
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			
			System.out.println("start Listening");
			link.addDigitalReadChangeListener(new DigitalReadChangeListener() {
				
				@Override
				public void stateChanged(DigitalReadChangeEvent e) {
					System.out.println("PIN: " + e.getPin() + " STATE: " + e.getValue());
					System.out.println(e.getIncomingMessage());
				}
				
				@Override
				public int getPinListening() {
					return 3;
				}
			});

			for(int i = 0; i < 5; i++) {
				try {
					Thread.sleep(1000);
					System.out.println("sendPowerON");
					link.sendPowerPinSwitch(5, IProtocol.HIGH);
					Thread.sleep(1000);
					System.out.println("sendPowerOFF");
					link.sendPowerPinSwitch(5, IProtocol.LOW);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
			
			try {
				System.out.println("wait for a while");
				Thread.sleep(2000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			link.disconnect();
		}
	}
	




