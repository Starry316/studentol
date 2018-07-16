package cn.xuzilin;

import cn.xuzilin.common.dao.UserEntityMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ServletComponentScan
@EnableScheduling
@MapperScan(basePackageClasses = UserEntityMapper.class)
public class StudentonlineApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudentonlineApplication.class, args);
	}
}
