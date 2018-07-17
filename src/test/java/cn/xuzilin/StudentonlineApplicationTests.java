package cn.xuzilin;

import cn.xuzilin.common.dto.Msg;
import cn.xuzilin.common.utils.Spider;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StudentonlineApplicationTests {

	@Test
	public void contextLoads() {
		Spider spider = new Spider();
		Msg<String[]> msg= spider.getStudentInfo("201600301017","316289");
		System.out.println(msg.getMsg()+"    object =   ");
		String [] list = msg.getObj();
		for (String i:list){
			System.out.println(i);
		}
	}

}
