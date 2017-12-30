package message;

import java.util.Map;

public class CheckUsername {
	public Map<String,Object> params;
	public String collectionName;
	public String msg_uuid;
	
	public CheckUsername(Map<String,Object> params, String collectionName, String messageid){
		this.params = params;
		this.collectionName = collectionName;
		this.msg_uuid = messageid;
	}

}
