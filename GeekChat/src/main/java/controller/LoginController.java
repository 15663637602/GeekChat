package controller;


import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import po.User;
import service.UserDao;

/**
 * 
 * @author Yuqi Li
 * date: Dec 2, 2017 1:07:38 AM
 */
@Controller
public class LoginController {
	@Autowired
	UserDao userDao;
	
	@RequestMapping("mongovalidate")
	public String mongovalidate(@RequestParam("username") String username,@RequestParam("password") String pwd,HttpSession httpSession,HttpServletRequest request){
		if(username==null)
			return "login";
		Map<String,Object> params=new HashMap<String,Object>();
		String collectionName = "user";
		params.put("username", username);
		User user = userDao.findOne(params, collectionName);
		if(user!=null&&pwd.equals(user.getPassword()))
		{
			httpSession.setAttribute("username", username);
			httpSession.setAttribute("uid",user.getU_id());
			return "/WEB-INF/chatroom";
		}else
			request.setAttribute("hint", "Incorrect username or password");
			return "login";
	}
	
	
	
	
	@RequestMapping("login")
	public String login(){
		return "login";
	}
	
	@RequestMapping("logout")
	public String logout(HttpSession httpSession){
		httpSession.removeAttribute("username");
		httpSession.removeAttribute("uid");
		return "login";
	}
  }
