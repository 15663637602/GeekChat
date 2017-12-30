package message;

import java.util.Map;

public class CheckEmail {

	public Map<String,Object> params;
	public String collectionName;
	public String msg_uuid;
	
	public CheckEmail(Map<String,Object> params, String collectionName, String messageid){
		this.params = params;
		this.collectionName = collectionName;
		this.msg_uuid = messageid;
	}
}
