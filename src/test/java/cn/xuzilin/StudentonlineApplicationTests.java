package cn.xuzilin;

import cn.xuzilin.common.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StudentonlineApplicationTests {
	@Resource
	private UserService userService;
	@Test
	public void contextLoads() {
//		userService.setPassword("123","1212");
	}

}
