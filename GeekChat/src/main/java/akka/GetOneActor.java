package akka;

import java.util.concurrent.TimeUnit;

import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;

import akka.actor.ActorIdentity;
import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.Identify;
import akka.actor.Kill;
import akka.actor.UntypedActor;
import akka.pattern.AskableActorSelection;
import akka.util.Timeout;

import message.FindOne;
import message.GetOneResult;

import scala.concurrent.Await;
import scala.concurrent.Future;
import service.UserDao;

@Named("GetOneActor")
@Scope("prototype")
public class GetOneActor extends UntypedActor{
	
	@Autowired
	UserDao userDao;
	
	public GetOneActor(Object msg){
		ActorSelection selection = getContext().actorSelection("akka://default/user/Mongodb");
		Timeout t = new Timeout(5, TimeUnit.SECONDS);
		AskableActorSelection asker = new AskableActorSelection(selection);
		Future<Object> fut = asker.ask(new Identify(1), t);
		try {
			ActorIdentity ident = (ActorIdentity) Await.result(fut, t.duration());
			ActorRef ref = ident.getRef();
			if (ref == null) System.out.println("No Mongodb");
			ref.tell(msg, getSelf());
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	
	

	@Override
	public void onReceive(Object msg) throws Exception {
		// TODO Auto-generated method stub
		if (msg instanceof GetOneResult) {
			GetOneResult getoneresule = (GetOneResult)msg;
			userDao.returnUser(getoneresule.user);
			userDao.changestate(true);
			
			getSelf().tell(Kill.getInstance(), getSelf());
		
		}
		else{
			unhandled(msg);
		}
	}

}
