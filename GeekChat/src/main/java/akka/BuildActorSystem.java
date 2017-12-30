package akka;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

/**
 * @author Yuqi Li
 * date: Dec 6, 2017 1:53:12 PM
 */
public class BuildActorSystem {
	private String roles;
	/**
	 * Akka集群，底层netty启动监听host
	 */
	private String host;
	/**
	 * Akka集群，底层netty启动监听端口号
	 */
	private int port;
	/**
	 * 集群的其他节点地址，like {192.168.1.100:5111}
	 */
	private List<String> seedNodes;
	
	@Autowired
	private ActorSystem actorsystem;
	
	@Autowired
	private SpringExt springExt;
	
	public void start(){
		System.out.println("start");
		System.out.println(port);
		System.out.println(host);
		System.out.println(roles);
		
		actorsystem.actorOf(springExt.props("MongodbActor"), "Mongodb"); 
		
	}
	
	public void close(){
		System.out.println("close");
	}
	
	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}
	
	public List<String> getSeedNodes() {
		return seedNodes;
	}

	public void setSeedNodes(List<String> seedNodes) {
		this.seedNodes = seedNodes;
	}
}
