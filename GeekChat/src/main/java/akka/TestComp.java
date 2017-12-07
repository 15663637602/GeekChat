package akka;

import org.springframework.stereotype.Component;

/**
 * @author Yuqi Li
 * date: Dec 6, 2017 2:12:26 PM
 */
@Component
public class TestComp {

	private String name ="GeekChat测试";
	private int id = 1;
	
	public void test(){
		System.out.println(name+" "+id);
	}
}
