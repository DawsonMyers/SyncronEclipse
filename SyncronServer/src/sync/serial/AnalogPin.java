/**
 * 
 */
package sync.serial;

import org.zu.ardulink.Link;
import org.zu.ardulink.event.AnalogReadChangeEvent;
import org.zu.ardulink.event.AnalogReadChangeListener;

import sync.controller.DataHandler;

/**
 * @author Dawson
 *
 */
public class AnalogPin {


	public int	currentValue	= -1;

	/**
	 * 
	 */
	public AnalogPin() {}

	public void initPin(Link link, int pin, ArdulinkSerial context) {


		link.addAnalogReadChangeListener(new AnalogReadChangeListener() {

			@Override
			public int getPinListening() {
				return pin;
			}

			@Override
			public void stateChanged(AnalogReadChangeEvent e) {

				currentValue = e.getValue();
				context.serialAnalogVals[pin] = currentValue;
				context.controller.dataHandler.setAnalogValue(pin, currentValue);
				System.out.println(e.getValue());

			}

		});
	}
}
