package interceptor;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
/**
 * 
 * @author Yuqi Li
 * date: Dec 2, 2017 1:07:53 AM
 */
public class LoginIntercepter extends HandlerInterceptorAdapter{
    @Override    
    public boolean preHandle(HttpServletRequest request,    
            HttpServletResponse response, Object handler) throws Exception {
        String username =  (String)request.getSession().getAttribute("username");   
        if(username == null){  
            request.getRequestDispatcher("login.jsp").forward(request, response);  
            return false;  
        }else  
            return true;     
    }    
    
    @Override    
    public void postHandle(HttpServletRequest request,    
            HttpServletResponse response, Object handler,    
            ModelAndView modelAndView) throws Exception {     
    }    
    
    @Override    
    public void afterCompletion(HttpServletRequest request,    
            HttpServletResponse response, Object handler, Exception ex)    
            throws Exception {    
    }    
  
}
