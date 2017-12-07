package po;

import java.io.Serializable;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Yuqi Li
 * date: Dec 2, 2017 10:49:38 PM
 */
@Document  
public class User implements Serializable {  
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long u_id;
	private String email;
	private String username;
	private String password;
	private String last_update;
	
	public Long getU_id() {
		return u_id;
	}
	public void setU_id(Long u_id) {
		this.u_id = u_id;
	}
	public String getLast_update() {
		return last_update;
	}
	public void setLast_update(String last_update) {
		this.last_update = last_update;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
      
      
  
}  
