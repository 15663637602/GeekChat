package message;

import java.util.Map;


/**
 * @author Yongjin Chen
 * date: Dec 7
 */

public class FindOne {
	public Map<String,Object> params;
	public String collectionName;
	
	public FindOne(Map<String,Object> params, String collectionName){
		this.params = params;
		this.collectionName = collectionName;
	}
}
