package akka;

import javax.annotation.Resource;
import javax.inject.Named;


import org.springframework.context.annotation.Scope;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import akka.actor.UntypedActor;
import message.FindOne;
import message.*;
import po.User;




@Named("MongodbActor")
@Scope("prototype") 
public class MongodbActor extends UntypedActor{
	
	@Resource  
    private MongoTemplate mongoTemplate;  
	
	@Override
	public void onReceive(Object Msg) throws Exception {
		// TODO Auto-generated method stub
		if (Msg instanceof FindOne){
			FindOne findone = (FindOne)Msg;
			User user = mongoTemplate.findOne(new Query(Criteria.where("username").is(findone.params.get("username"))), User.class,findone.collectionName);
			getSender().tell(new GetOneResult(user,findone.msg_uuid), getSelf());
		}
		else if(Msg instanceof GetNamebyId){
			GetNamebyId getnamebyid = (GetNamebyId) Msg;
			User user = mongoTemplate.findOne(new Query(Criteria.where("u_id").is(getnamebyid.params.get("u_id"))), User.class,getnamebyid.collectionName);
			getSender().tell(new GetOneResult(user,getnamebyid.msg_uuid), getSelf());
		}
		else if(Msg instanceof GetIdbyName){
			GetIdbyName getidbyname = (GetIdbyName) Msg;
			User user = mongoTemplate.findOne(new Query(Criteria.where("username").is(getidbyname.params.get("username"))), User.class,getidbyname.collectionName);
			getSender().tell(new GetOneResult(user,getidbyname.msg_uuid), getSelf());
		}
		else if(Msg instanceof CheckUsername){
			CheckUsername checkusername = (CheckUsername) Msg;
			User user = mongoTemplate.findOne(new Query(Criteria.where("username").is(checkusername.params.get("username"))), User.class,checkusername.collectionName);
			getSender().tell(new GetOneResult(user,checkusername.msg_uuid), getSelf());
		}
		else if(Msg instanceof CheckEmail){
			CheckEmail checkemail = (CheckEmail) Msg;
			User user = mongoTemplate.findOne(new Query(Criteria.where("email").is(checkemail.params.get("email"))), User.class,checkemail.collectionName);
			getSender().tell(new GetOneResult(user,checkemail.msg_uuid), getSelf());
		}
		else if(Msg instanceof Insert){
			Insert insert = (Insert) Msg;
			mongoTemplate.insert(insert.user, insert.collectionName);
			getSender().tell(new GetOneResult(insert.user,insert.msg_uuid), getSelf());
		}
		else{
			unhandled(Msg);
		}
	}

}
