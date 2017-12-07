
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import akka.actor.ActorSystem;



/**
 * @author Yuqi Li
 * date: Dec 2, 2017 2:01:27 AM
 */
public class test {
	public static void main(String[] args) {
		ApplicationContext ac = new ClassPathXmlApplicationContext("classpath*:conf/spring-mvc");
		ActorSystem system = (ActorSystem) ac.getBean("actorSystem");
		System.out.println(system);
	}
}
