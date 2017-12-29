package websocket;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import po.Message;
import service.UserDao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Socket Handler
 * @author Yuqi Li
 * date: Dec 2, 2017 1:09:38 AM
 */
@Component
public class MyWebSocketHandler implements WebSocketHandler {
	//mapping relationship between HttpSession and WebSocketSession
	public static final Map<Long, WebSocketSession> userSocketSessionMap;

	
	@Autowired
	UserDao userDao;
	
	static {
		userSocketSessionMap = new ConcurrentHashMap<Long, WebSocketSession>();
	}
	
	/**
	 * After the connection is established, write the ID of the login user to the WebSocketSession
	 */
	public void afterConnectionEstablished(WebSocketSession session)
			throws Exception {
		Long uid = (Long) session.getAttributes().get("uid");
		System.out.println("before: "+uid);
		Map<String,Object> params=new HashMap<String,Object>();
		String collectionName = "user";
		params.put("u_id", uid);
		String username=userDao.getnamebyid(params, collectionName);
		System.out.println("tetstte");
		System.out.println("afterconn: "+username);
		if (userSocketSessionMap.get(uid) == null) {
			userSocketSessionMap.put(uid, session);
			Message msg = new Message();
			msg.setFrom(0L);//0 indicates the online message
			msg.setText(username);
			this.broadcast(new TextMessage(new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create().toJson(msg)));
		}
	}

	/**
	 * Message processing, the message sent by the Websocket API on the client side will go through here, and then the corresponding processing is done.
	 */
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
			if(message.getPayloadLength()==0)
				return;
			Message msg=new Gson().fromJson(message.getPayload().toString(),Message.class);
			msg.setDate(new Date());
			sendMessageToUser(msg.getTo(), new TextMessage(new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create().toJson(msg)));
	}
	
	/**
	 * Send a message to a user
	 * 
	 * @param userName
	 * @param message
	 * @throws IOException
	 */
	public void sendMessageToUser(Long uid, TextMessage message) throws IOException {
		WebSocketSession session = userSocketSessionMap.get(uid);
		if (session != null && session.isOpen()) {
			session.sendMessage(message);
		}
	}

	/**
	 * Message transmission error processing
	 */
	public void handleTransportError(WebSocketSession session,
			Throwable exception) throws Exception {
		if (session.isOpen()) {
			session.close();
		}
		Iterator<Entry<Long, WebSocketSession>> it = userSocketSessionMap.entrySet().iterator();
		// Remove the Socket session that currently throws the exception user
		while (it.hasNext()) {
			Entry<Long, WebSocketSession> entry = it.next();
			if (entry.getValue().getId().equals(session.getId())) {
				userSocketSessionMap.remove(entry.getKey());
				System.out.println("Remove the Socket session that currently throws the exception user: " + entry.getKey());
				Map<String,Object> params=new HashMap<String,Object>();
				String collectionName = "user";
				params.put("u_id", entry.getKey());
				String username=userDao.getnamebyid(params, collectionName);
				Message msg = new Message();
				msg.setFrom(-2L);
				msg.setText(username);
				this.broadcast(new TextMessage(new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create().toJson(msg)));
				break;
			}
		}
	}
	
	/**
	 * Sending messages to all online users
	 * @param message
	 * @throws IOException
	 */
	public void broadcast(final TextMessage message) throws IOException {
		Iterator<Entry<Long, WebSocketSession>> it = userSocketSessionMap.entrySet().iterator();

		//Multithreading group send
		while (it.hasNext()) {

			final Entry<Long, WebSocketSession> entry = it.next();

			if (entry.getValue().isOpen()) {
				// entry.getValue().sendMessage(message);
				new Thread(new Runnable() {

					public void run() {
						try {
							if (entry.getValue().isOpen()) {
								entry.getValue().sendMessage(message);
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
					}

				}).start();
			}

		}
	}

	/**
	 * After closing the connection
	 */
	public void afterConnectionClosed(WebSocketSession session,CloseStatus closeStatus) throws Exception {
		System.out.println("Websocket:" + session.getId() + "closed");
		Iterator<Entry<Long, WebSocketSession>> it = userSocketSessionMap.entrySet().iterator();
		// Remove the current user's Socket session
		while (it.hasNext()) {
			Entry<Long, WebSocketSession> entry = it.next();
			if (entry.getValue().getId().equals(session.getId())) {
				userSocketSessionMap.remove(entry.getKey());
				System.out.println("Remove the Socket session that currently throws the exception user: " + entry.getKey());
				Map<String,Object> params=new HashMap<String,Object>();
				String collectionName = "user";
				params.put("u_id", entry.getKey());
				String username=userDao.getnamebyid(params, collectionName);
				Message msg = new Message();
				msg.setFrom(-2L);//Downline message, expressed in -2
				msg.setText(username);
				this.broadcast(new TextMessage(new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create().toJson(msg)));
				break;
			}
		}
	}

	public boolean supportsPartialMessages() {
		return false;
	}

	

	

}
