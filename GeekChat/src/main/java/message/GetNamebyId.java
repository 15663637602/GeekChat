package message;

import java.util.Map;

public class GetNamebyId {
	public Map<String,Object> params;
	public String collectionName;
	public String msg_uuid;
	
	public GetNamebyId(Map<String,Object> params, String collectionName, String messageid){
		this.params = params;
		this.collectionName = collectionName;
		this.msg_uuid = messageid;
	}
}
