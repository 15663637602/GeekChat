package service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import akka.SpringExt;
import akka.actor.ActorSystem;
import message.*;
import po.User;
import service.UserDao;

/**
 * @author Yuqi Li
 * date: Dec 2, 2017 10:50:44 PM
 */
@Repository("userDaoImpl")  
public class UserDaoImpl implements UserDao {  
	Map<String,User> results = new HashMap<String,User>();

//    @Resource  
//    private MongoTemplate mongoTemplate;  
    
    @Autowired
    private ActorSystem actorsystem;
    
    @Autowired
	private SpringExt springExt;
  
    @Override  
    public void insert(User object,String collectionName) {
    	String message_uuid = UUID.randomUUID().toString();
    	actorsystem.actorOf(springExt.props("GetOneActor",new Insert(object,collectionName,message_uuid)),  "insert"+object.getUsername()); 
    	try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return;
    }  
  
    @Override  
    public User findOne(Map<String,Object> params,String collectionName) {
    
    	String message_uuid = UUID.randomUUID().toString();
    	results.put(message_uuid, null);
    	actorsystem.actorOf(springExt.props("GetOneActor",new FindOne(params,collectionName,message_uuid)), "finduser"+ params.get("username")); 
    	
    	try {
    		Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	User this_result = results.get(message_uuid);
    	if(this_result!=null)
    	{
    		System.out.println("The get_result of Actor finduser"+params.get("username").toString()+" is not null");
    		System.out.println("The username of user_result is"+this_result.getUsername());
    	}
    	results.remove(message_uuid);
    	return this_result;
    }  
//  
//    @Override  
//    public List<User> findAll(Map<String,Object> params,String collectionName) {  
//        List<User> result = mongoTemplate.find(new Query(Criteria.where("age").lt(params.get("maxAge"))), User.class,collectionName);  
//        return result;  
//    }  
//  
//    @Override  
//    public void update(Map<String,Object> params,String collectionName) {  
//        mongoTemplate.upsert(new Query(Criteria.where("id").is(params.get("id"))), new Update().set("name", params.get("name")), User.class,collectionName);  
//    }  
//  
//    @Override  
//    public void createCollection(String name) {  
//        mongoTemplate.createCollection(name);  
//    }  
//  
//  
//    @Override  
//    public void remove(Map<String, Object> params,String collectionName) {  
//        mongoTemplate.remove(new Query(Criteria.where("id").is(params.get("id"))),User.class,collectionName);  
//    }  
//    
    @Override  
    public String getnamebyid(Map<String,Object> params,String collectionName) {
    	String message_uuid = UUID.randomUUID().toString();
    	results.put(message_uuid, null);
    	actorsystem.actorOf(springExt.props("GetOneActor",new GetNamebyId(params,collectionName,message_uuid)),  "getnamebyid"+params.get("u_id").toString()); 

    	try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	User this_result = results.get(message_uuid);
    	if(this_result!=null)
    	{
    		System.out.println("The get_result of Actor getnamebyid"+params.get("u_id").toString()+" is not null");
    		System.out.println("The username of user_result is"+this_result.getUsername());
    	}
    	results.remove(message_uuid);
    	return this_result.getUsername();
    	
    	
    }

	@Override
	public Long getidbyname(Map<String,Object> params,String collectionName) {
		String message_uuid = UUID.randomUUID().toString();
    	results.put(message_uuid, null);
    	
    	actorsystem.actorOf(springExt.props("GetOneActor",new GetIdbyName(params,collectionName,message_uuid)), "getidbyname"+ params.get("username")); 
    	try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	User this_result = results.get(message_uuid);
    	if(this_result!=null)
    	{
    		System.out.println("The get_result of Actor getidbyname"+params.get("username").toString()+" is not null");
    		System.out.println("The u_id of user_result is"+this_result.getU_id());
    	}
    	results.remove(message_uuid);
    	return this_result.getU_id();
	}
	
	@Override
	public void returnresult(GetOneResult result){
		results.put(result.msg_uuid,result.user);
	}

	
	/**
	 * 07.12 Yuqi Li
	 */
	@Override
	public User checkUsername(Map<String, Object> params, String collectionName) {
		String message_uuid = UUID.randomUUID().toString();
    	results.put(message_uuid, null);
    	
    	actorsystem.actorOf(springExt.props("GetOneActor",new CheckUsername(params,collectionName,message_uuid)),  "checkusername"+ params.get("username")); 
    	try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	User this_result = results.get(message_uuid);
    	results.remove(message_uuid);
    	return this_result;
	}

	@Override
	public User checkEmail(Map<String, Object> params, String collectionName) {
		String message_uuid = UUID.randomUUID().toString();
    	results.put(message_uuid, null);
    	
    	actorsystem.actorOf(springExt.props("GetOneActor",new CheckEmail(params,collectionName,message_uuid)), "checkemail"+  params.get("email")); 
    	try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	User this_result = results.get(message_uuid);
    	results.remove(message_uuid);
    	return this_result;
	}
}
