package controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import po.User;
import service.UserDao;
import util.GetCurrentTime;
import util.GetUUID;

/**
 * @author Yuqi Li date: Dec 3, 2017 12:15:32 AM
 */
@RestController
public class UserController {

	String collectionName = "user";

	@Autowired
	UserDao userDao;

	@RequestMapping(value = "insertUser", method = RequestMethod.POST)
	public ModelAndView insertUser(ModelMap map, HttpServletRequest request, HttpServletResponse response) {
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		user.setEmail(email);
		user.setU_id(System.currentTimeMillis());
		user.setLast_update(new GetCurrentTime().getTime());
		userDao.insert(user, collectionName);
		request.setAttribute("hint", "Registering successfully");
		return new ModelAndView("login");
	}

	@RequestMapping(value = "testname", method = RequestMethod.POST)
	public void checkUsername(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String data;
		Map<String, Object> params = new HashMap<String, Object>();

		String username = request.getParameter("username");
		params.put("username", username);
		User user = userDao.checkUsername(params, collectionName);
		if (user != null) {
			data = "Username already exits";
		} else {
			data = "You can use this name";
		}
		response.setContentType("text/xml;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		response.getWriter().write(data);
	}

	@RequestMapping(value = "testemail", method = RequestMethod.POST)
	public void checkEmail(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String data;
		Map<String, Object> params = new HashMap<String, Object>();

		String email = request.getParameter("email");
		params.put("email", email);
		User user = userDao.checkEmail(params, collectionName);
		if (user != null) {
			data = "Email already used";
		} else {
			data = "You can use this email";
		}
		response.setContentType("text/xml;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		response.getWriter().write(data);
	}

}
