package service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;


import akka.FindOneActor;
import akka.MongodbActor;
import akka.SpringExt;
import akka.actor.ActorSystem;
import akka.actor.Props;

import message.FindOne;
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
    
    @Resource  
    private MongoTemplate mongoTemplate;  
    
    @Autowired
    private ActorSystem actorsystem;
    
    @Autowired
	private SpringExt springExt;
  
    @Override  
    public void insert(User object,String collectionName) {  
        mongoTemplate.insert(object, collectionName);  
    }  
  
    @Override  
    public User findOne(Map<String,Object> params,String collectionName) {
    	get_result = false;
    	
    	actorsystem.actorOf(springExt.props("FindOneActor",new FindOne(params,collectionName)),  (String) params.get("username")); 
    	try {
			Thread.sleep(500);
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
    public List<User> findAll(Map<String,Object> params,String collectionName) {  
        List<User> result = mongoTemplate.find(new Query(Criteria.where("age").lt(params.get("maxAge"))), User.class,collectionName);  
        return result;  
    }  
  
    @Override  
    public void update(Map<String,Object> params,String collectionName) {  
        mongoTemplate.upsert(new Query(Criteria.where("id").is(params.get("id"))), new Update().set("name", params.get("name")), User.class,collectionName);  
    }  
  
    @Override  
    public void createCollection(String name) {  
        mongoTemplate.createCollection(name);  
    }  
  
  
    @Override  
    public void remove(Map<String, Object> params,String collectionName) {  
        mongoTemplate.remove(new Query(Criteria.where("id").is(params.get("id"))),User.class,collectionName);  
    }  
    
    @Override  
    public String getnamebyid(Long l){
    	Map<String,Object> params=new HashMap<String,Object>();
		String collectionName = "user";
		params.put("u_id", l);
		User user = mongoTemplate.findOne(new Query(Criteria.where("u_id").is(params.get("u_id"))), User.class,collectionName);
		return user.getUsername();
    }

	@Override
	public Long getidbyname(String name) {
		
		Map<String,Object> params=new HashMap<String,Object>();
		String collectionName = "user";
		params.put("username", name);
		User user = mongoTemplate.findOne(new Query(Criteria.where("username").is(params.get("username"))), User.class,collectionName);
		return user.getU_id();
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
		User user = mongoTemplate.findOne(new Query(Criteria.where("username").is(params.get("username"))), User.class,collectionName);
		return user;
	}

	@Override
	public User checkEmail(Map<String, Object> params, String collectionName) {
		User user = mongoTemplate.findOne(new Query(Criteria.where("email").is(params.get("email"))), User.class,collectionName);
		return user;
	}
}
