package util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author Yuqi Li
 * date: Dec 2, 2017 11:58:50 PM
 */
public class GetCurrentTime {
	
	public String getTime(){
		Date date = new Date();
		SimpleDateFormat dateFm = new SimpleDateFormat("E: yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
		String dateStr = dateFm.format(date);
		return dateStr;
	}

}
