package akka;

import java.util.List;

import org.apache.log4j.Logger;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import akka.actor.ActorSystem;
import akka.cluster.Cluster;

/**
 * @author Yuqi Li
 * date: Dec 6, 2017 1:52:25 PM
 */
public class AkkaSystem {
	private Logger logger = Logger.getLogger(AkkaSystem.class);

	/**
	 * 默认的Akka通信协议
	 */
	public static final String DEFAULT_PROTOCOL = "akka.tcp";

	/**
	 * 默认的Akka System Name
	 */
	public static final String DEFAULT_SYSTEM_NAME = "AkkaSystem";

	/**
	 * AkkaSystem Name，每一个System必须有一个命名，详情
	 * @see akka.actor.ActorSystem
	 */
	private String systemName = DEFAULT_SYSTEM_NAME;

	/**
	 * Akka集群中节点的角色，同时拥有多个角色用逗号隔开
	 * @see akka.cluster.Cluster
	 */
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

	private ActorSystem system;

	/**
	 * 同步线程
	 */
	private Thread thread = new Thread();

	/**
	 * 启动方法，通过进程同步，将加入集群变成同步
	 */
	public void start() {
		thread.start();
		System.out.println(">>>>> 启动Akka System 。。。");
		Config config = ConfigFactory
				.parseString(
						"akka.actor.provider=akka.cluster.ClusterActorRefProvider")
				.withFallback(
						ConfigFactory
								.parseString("akka.remote.netty.tcp.hostname="
										+ host))
				.withFallback(
						ConfigFactory.parseString("akka.remote.netty.tcp.port="
								+ port))
				.withFallback(
						ConfigFactory
								.parseString("akka.cluster.auto-down = on"));
		if (roles != null && !"".equals(roles)) {
			config = config.withFallback(ConfigFactory
					.parseString("akka.cluster.roles = [" + roles + "]"));
		}

		String nodes = "";
		for (int i = 0; i < seedNodes.size(); i++) {
			nodes += ",\"akka.tcp://" + systemName + "@" + seedNodes.get(i)
					+ "\"";
		}
		if (nodes.length() > 0) {
			nodes = nodes.substring(1);
			config = config.withFallback(ConfigFactory
					.parseString("akka.cluster.seed-nodes = [" + nodes + "]"));
		}

		system = ActorSystem.create(systemName, config);

		Cluster.get(system).registerOnMemberUp(new Runnable() {
			@Override
			public void run() {
				synchronized (thread) {
					logger.info(">>>>> 加入集群成功！！！");
					thread.notify();
				}
			}
		});
		synchronized (thread) {
			try {
				logger.info(">>>>> 尝试加入Akka集群中...");
				thread.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}

	/**
	 * 停止AkkaSystem，
	 */
	public void close() {
		Cluster.get(system).shutdown();
		system.shutdown();
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

	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	public List<String> getSeedNodes() {
		return seedNodes;
	}

	public void setSeedNodes(List<String> seedNodes) {
		this.seedNodes = seedNodes;
	}

	public ActorSystem getSystem() {
		return system;
	}
}
