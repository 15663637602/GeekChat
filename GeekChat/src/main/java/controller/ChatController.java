package controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import po.Message;
import po.User;
import service.UserDao;
import websocket.MyWebSocketHandler;

import com.google.gson.GsonBuilder;
/**
 * 
 * @author Yuqi Li
 * date: Dec 2, 2017 1:06:56 AM
 */
@Controller
public class ChatController {
	@Autowired
	MyWebSocketHandler handler;
	
	
	@Autowired
	UserDao userDao;
	
	@RequestMapping(value="onlineusers",method = RequestMethod.GET)
	@ResponseBody
	public Set<String> onlineusers(HttpSession session) throws InterruptedException{
		Map<Long, WebSocketSession> map=MyWebSocketHandler.userSocketSessionMap;
		Set<Long> set=map.keySet();
		Iterator<Long> it = set.iterator();
		Set<String> nameset=new HashSet<String>();
		while(it.hasNext()){
			Long entry = it.next();
			Map<String,Object> params=new HashMap<String,Object>();
			String collectionName = "user";
			params.put("uid", entry);
			String name=userDao.getnamebyid(params, collectionName);
			String user=(String)session.getAttribute("username");
			if(!user.equals(name))
				nameset.add(name);
		}//
		return nameset;
	}
	
	// broadcast
		@ResponseBody
		@RequestMapping(value = "broadcast", method = RequestMethod.POST)
		public void broadcast(@RequestParam("text") String text) throws IOException {
			System.out.println("broadcast");
			Message msg = new Message();
			msg.setDate(new Date());
			msg.setFrom(-1L);//-1 indicates system broadcast
			msg.setFromName("system broadcast");
			msg.setTo(0L);
			msg.setText(text);
			handler.broadcast(new TextMessage(new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create().toJson(msg)));
		}
		
	@RequestMapping(value = "getuid", method = RequestMethod.POST)
	@ResponseBody
	public User getuid(@RequestParam("username")String username){
		Map<String,Object> params=new HashMap<String,Object>();
		String collectionName = "user";
		params.put("username", username);
		Long a = userDao.getidbyname(params, collectionName);
		User u=new User();
		u.setU_id(a);
		return u;
	}
}
