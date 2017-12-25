package service.impl;

import java.util.HashMap;
import java.util.Map;
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
	public boolean get_result = false;
	public User user_result= null;

//    @Resource  
//    private MongoTemplate mongoTemplate;  
    
    @Autowired
    private ActorSystem actorsystem;
    
    @Autowired
	private SpringExt springExt;
  
    @Override  
    public void insert(User object,String collectionName) {
    	get_result = false;
    	
    	actorsystem.actorOf(springExt.props("GetOneActor",new Insert(object,collectionName)),  (String) object.getUsername()); 
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
    	get_result = false;
    	
    	actorsystem.actorOf(springExt.props("GetOneActor",new FindOne(params,collectionName)), "finduser"+ (String) params.get("username")); 
    	
    	try {
    		Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	if(get_result){
    		return user_result;
    	}
    	else{
    		return null;
    	}
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
    	System.out.println("check 0");
		get_result = false;
		System.out.println("check 1");
    	actorsystem.actorOf(springExt.props("GetOneActor",new GetNamebyId(params,collectionName)),  "getnamebyid"+(String) params.get("u_id")); 
    	System.out.println("check 2");

    	try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	System.out.println("check 3");
    	
    	if(get_result){
    		return user_result.getUsername();
    	}
    	else{
    		return null;
    	}
    	
    }

	@Override
	public Long getidbyname(Map<String,Object> params,String collectionName) {
		
		get_result = false;
    	
    	actorsystem.actorOf(springExt.props("GetOneActor",new GetIdbyName(params,collectionName)), "getidbyname"+ (String) params.get("username")); 
    	try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	if(get_result){
    		return user_result.getU_id();
    	}
    	else{
    		return null;
    	}
	}

	@Override
	public void changestate(boolean get_result) {
		// TODO Auto-generated method stub
		this.get_result = get_result;
		
	}

	@Override
	public void returnUser(User user) {
		// TODO Auto-generated method stub
		this.user_result = user;
	}

	/**
	 * 07.12 Yuqi Li
	 */
	@Override
	public User checkUsername(Map<String, Object> params, String collectionName) {
		
		get_result = false;
    	
    	actorsystem.actorOf(springExt.props("GetOneActor",new CheckUsername(params,collectionName)),  "checkusername"+(String) params.get("username")); 
    	try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	if(get_result){
    		return user_result;
    	}
    	else{
    		return null;
    	}
	}

	@Override
	public User checkEmail(Map<String, Object> params, String collectionName) {
		
		get_result = false;
    	
    	actorsystem.actorOf(springExt.props("GetOneActor",new CheckEmail(params,collectionName)), "checkemail"+ (String) params.get("email")); 
    	try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	if(get_result){
    		return user_result;
    	}
    	else{
    		return null;
    	}
	}
}
