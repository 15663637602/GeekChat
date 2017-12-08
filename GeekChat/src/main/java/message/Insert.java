package message;

import po.User;

public class Insert {
	public User user;
	public String collectionName;
	
	public Insert(User user, String collectionName){
		this.user = user;
		this.collectionName = collectionName;
	}
}
