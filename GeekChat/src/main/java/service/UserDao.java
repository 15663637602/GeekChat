package service;

import java.util.Map;

import po.User;

/**
 * @author Yuqi Li
 * date: Dec 2, 2017 10:50:15 PM
 */
public interface UserDao extends MongoBase<User>{
	String getnamebyid(Map<String,Object> params,String collectionName);
	Long getidbyname(Map<String,Object> params,String collectionName);
	User checkUsername(Map<String,Object> params,String collectionName);
	User checkEmail(Map<String,Object> params,String collectionName);
}
