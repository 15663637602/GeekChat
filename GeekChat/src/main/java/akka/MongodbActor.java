package akka;

import javax.annotation.Resource;
import javax.inject.Named;


import org.springframework.context.annotation.Scope;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import akka.actor.UntypedActor;
import message.FindOne;
import message.FindOneResult;
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
			getSender().tell(new FindOneResult(user), getSelf());
		}
		else{
			unhandled(Msg);
		}
	}

}
