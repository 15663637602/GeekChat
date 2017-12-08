package message;

import java.util.Map;

public class CheckUsername {
	public Map<String,Object> params;
	public String collectionName;
	
	public CheckUsername(Map<String,Object> params, String collectionName){
		this.params = params;
		this.collectionName = collectionName;
	}

}
