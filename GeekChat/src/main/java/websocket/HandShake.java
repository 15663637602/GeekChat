
package websocket;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;


/**
 * HandShake
 * @author Yuqi Li
 * date: Dec 2, 2017 1:09:23 AM
 */
public class HandShake implements HandshakeInterceptor {

	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
		if (request instanceof ServletServerHttpRequest) {
			ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
			//as
			HttpSession session = servletRequest.getServletRequest().getSession(false);
			// Mark Users
			Long uid = (Long) session.getAttribute("uid");
			if(uid!=null){
				attributes.put("uid", uid);
			}else{
				return false;
			}
		}
		return true;
	}

	public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
	//System.out.println("after hand");
	
	}

}
