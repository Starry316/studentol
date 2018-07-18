package cn.xuzilin.common.controller;

import cn.xuzilin.common.po.ApplicationEntity;
import cn.xuzilin.common.po.StudentEntity;
import cn.xuzilin.common.service.SignUpService;
import cn.xuzilin.common.service.StudentService;
import cn.xuzilin.common.utils.ResponesUtil;
import cn.xuzilin.common.vo.MessageVo;
import cn.xuzilin.core.shiro.token.TokenManager;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import netscape.javascript.JSException;
import netscape.javascript.JSObject;
import org.apache.catalina.mapper.Mapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@RestController
public class ApplicationController {

    @Resource
    private SignUpService SignUpService;

    @PostMapping("/form")
    public MessageVo form(@RequestParam Map<String,String> map){
        ApplicationEntity application = new ApplicationEntity();
        StudentEntity student = TokenManager.getStudentToken();
        application.setStudent_id(student.getStudent_id());
        application.setIntention(map.get("intention"));
        application.setIntention2(map.get("intention2"));
        Date date = new Date();
        application.setSign_time(date);
        application.setIntroduction(map.get("introduction"));
        if (map.get("intention")!=null){
            application.setStage("1");
        }
        if (map.get("intention2")!=null){
            application.setStage2("1");
        }
        SignUpService.SignUp(application);
        return ResponesUtil.success("success");
    }

    @GetMapping("/process")
    public MessageVo process(){
        StudentEntity student = TokenManager.getStudentToken();
        ApplicationEntity application = SignUpService.ReturnData(student);
        JSONArray jsonObject = new JSONArray();
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("intention",application.getIntention());
        jsonObject1.put("stage",application.getStage());
        JSONObject jsonObject2 = new JSONObject();
        jsonObject2.put("intention2",application.getIntention2());
        jsonObject2.put("stage2",application.getStage2());
        jsonObject.add(jsonObject1);
        jsonObject.add(jsonObject2);
        return ResponesUtil.success("success",jsonObject);
    }



}
