package cn.xuzilin.common.controller;

import cn.xuzilin.common.po.UserEntity;
import cn.xuzilin.common.service.UserService;
import cn.xuzilin.common.utils.PasswordUtil;
import cn.xuzilin.common.vo.MessageVo;
import cn.xuzilin.core.shiro.token.TokenManager;
import com.alibaba.fastjson.JSONObject;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by Starry on 2018/7/15.
 */
@RestController
public class TestController {
    @Resource
    private UserService userService;
    @PostMapping("/hello")
    public String hello(){
        return "hello";
    }

    @GetMapping("/login")
    public MessageVo login(@RequestParam ("acc") String acc , @RequestParam ("pass") String pass){
        UserEntity userEntity = new UserEntity();
        userEntity.setAccount(acc);
        userEntity.setPass( pass);
        System.out.println("Controller"+ acc+userEntity.getPass());
        try {
            TokenManager.login(userEntity);
        }catch (IncorrectCredentialsException e){
            MessageVo messageVo = new MessageVo();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name","徐子林");
            jsonObject.put("sid","201600301017");
            messageVo.setData(jsonObject);
            messageVo.setStatus(200);
            messageVo.setMessage("用户名或者密码错误！请重试");
            return messageVo;
        }
        return new MessageVo();
    }

}
