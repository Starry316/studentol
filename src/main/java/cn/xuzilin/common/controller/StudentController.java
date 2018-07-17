package cn.xuzilin.common.controller;

import cn.xuzilin.common.po.StudentEntity;
import cn.xuzilin.common.service.StudentService;
import cn.xuzilin.common.utils.ResponesUtil;
import cn.xuzilin.common.vo.MessageVo;
import cn.xuzilin.core.shiro.token.TokenManager;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by Starry on 2018/7/16.
 */
@RestController
public class StudentController {
    @Resource
    private StudentService studentService;
    @PostMapping("/login")
    public MessageVo login(@RequestParam("password")String password,
                            @RequestParam("studentId")String studentId){
        String studentName = studentService.checkAccount(studentId,password);
        if (studentName == null){
            return ResponesUtil.systemError("用户名或账号错误，登录失败");
        }
        StudentEntity record = new StudentEntity();
        record.setStudent_id(studentId);
        record.setStudent_name(studentName);
        studentService.login(record);
        return ResponesUtil.success("success");
    }
    @PostMapping("/logout")
    public MessageVo logout(){
        if (TokenManager.getStudentToken() != null)
            TokenManager.studentLogout();
        return ResponesUtil.success("success");
    }
    @GetMapping("/user")
    public MessageVo user(){
        StudentEntity student = TokenManager.getStudentToken();
        if (student == null)
            return ResponesUtil.systemError("目前没有登录用户！");
        JSONObject respData = new JSONObject();
        respData.put("studentName",student.getStudent_name());
        respData.put("studentId",student.getStudent_id());
        respData.put("phoneNumber",student.getPhone_number());
        respData.put("area",student.getCollege());
        return ResponesUtil.success("success",respData);
    }
//    @PostMapping("/user")
//    public MessageVo updateUser(@RequestParam("")){
//
//    }
}
