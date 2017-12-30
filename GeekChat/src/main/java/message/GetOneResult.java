package message;

import po.User;

public class GetOneResult {
	public User user;
	public String msg_uuid;
	
	public GetOneResult(User user, String messageid){
		this.user = user;
		this.msg_uuid = messageid;
	}

}
