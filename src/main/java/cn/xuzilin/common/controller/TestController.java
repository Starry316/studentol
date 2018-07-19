package cn.xuzilin.common.controller;

import cn.xuzilin.common.po.UserEntity;
import cn.xuzilin.common.utils.PasswordUtil;
import cn.xuzilin.common.utils.ResponesUtil;
import cn.xuzilin.common.vo.MessageVo;
import cn.xuzilin.core.shiro.token.TokenManager;
import com.alibaba.fastjson.JSONObject;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by Starry on 2018/7/15.
 */
@RestController
public class TestController {
    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }
    @PostMapping("/testParam")
    public MessageVo testParam(@RequestParam Map<String , String > map){
        map.get("a2");
        return ResponesUtil.success("success", map.get("a1"));

    }


}
