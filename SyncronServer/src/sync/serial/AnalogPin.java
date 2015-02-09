/**
 * 
 */
package sync.serial;

import org.zu.ardulink.Link;
import org.zu.ardulink.event.AnalogReadChangeEvent;
import org.zu.ardulink.event.AnalogReadChangeListener;

/**
 * @author Dawson
 *
 */
public class AnalogPin {

	/**
	 * 
	 */
	public AnalogPin(Link link, int pin) {
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
	
}



