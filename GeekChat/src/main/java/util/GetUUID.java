package util;

import java.util.UUID;

/**
 * @author Yuqi Li
 * date: Dec 3, 2017 12:00:55 AM
 */
public class GetUUID {
	public String getUUID(){
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
}
