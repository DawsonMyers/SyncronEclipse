package sync.system;

import java.text.SimpleDateFormat;
import java.util.Date;

import coms.UdpHandler;
import coms.UdpMessenger;

/**
 * Created by Dawson on 2/4/2015.
 */
public class SyncUtils {

	public static String getDate() {
		return (new SimpleDateFormat("MMM-dd HH.mm.ss.SSS")).format(new Date()).toString();
	}
	public static String getDateBox() {
		return (new SimpleDateFormat("[MMM-dd HH.mm.ss.SSS]")).format(new Date()).toString();
	}

	
 
}
