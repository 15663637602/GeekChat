package message;

import java.util.Map;

public class GetIdbyName {
	public Map<String,Object> params;
	public String collectionName;
	public String msg_uuid;
	
	public GetIdbyName(Map<String,Object> params, String collectionName, String messageid){
		this.params = params;
		this.collectionName = collectionName;
		this.msg_uuid = messageid;
	}

}
