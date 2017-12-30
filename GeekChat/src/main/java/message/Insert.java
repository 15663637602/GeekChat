package message;

import po.User;

public class Insert {
	public User user;
	public String collectionName;
	public String msg_uuid;
	
	public Insert(User user, String collectionName, String messageid){
		this.user = user;
		this.collectionName = collectionName;
		this.msg_uuid = messageid;
	}
}
