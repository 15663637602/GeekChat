package message;

import java.util.Map;

public class GetIdbyName {
	public Map<String,Object> params;
	public String collectionName;
	
	public GetIdbyName(Map<String,Object> params, String collectionName){
		this.params = params;
		this.collectionName = collectionName;
	}

}
