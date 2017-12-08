package message;

import java.util.Map;

public class CheckEmail {

	public Map<String,Object> params;
	public String collectionName;
	
	public CheckEmail(Map<String,Object> params, String collectionName){
		this.params = params;
		this.collectionName = collectionName;
	}
}
