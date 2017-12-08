package message;

import java.util.Map;

public class GetNamebyId {
	public Map<String,Object> params;
	public String collectionName;
	
	public GetNamebyId(Map<String,Object> params, String collectionName){
		this.params = params;
		this.collectionName = collectionName;
	}
}
