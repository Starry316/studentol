package cn.xuzilin.common.controller;

import cn.xuzilin.common.consts.ConstPool;
import cn.xuzilin.common.po.ApplicationEntity;
import cn.xuzilin.common.po.StudentEntity;
import cn.xuzilin.common.service.StudentService;
import cn.xuzilin.common.service.TaskService;
import cn.xuzilin.common.utils.ResponesUtil;
import cn.xuzilin.common.vo.MessageVo;
import cn.xuzilin.core.shiro.token.TokenManager;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by Starry on 2018/7/16.
 */
@RestController
public class StudentController {
    @Resource
    private StudentService studentService;
    @Resource
    private TaskService taskService;

    /**
     * 登录
     * @param password
     * @param studentId
     * @return
     */
    @PostMapping("/login")
    public MessageVo login(@RequestParam("password")String password,
                            @RequestParam("studentId")String studentId){
        //先登录教务系统获取学生姓名，姓名为空为登录失败
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

    /**
     * 注销
     * @return
     */
    @PostMapping("/logout")
    public MessageVo logout(){
        if (TokenManager.getStudentToken() != null)
            TokenManager.studentLogout();
        return ResponesUtil.success("success");
    }

    /**
     * 获取用户信息
     * @return 当前登录的用户信息
     */
    @GetMapping("/user")
    public MessageVo user(){
        StudentEntity student = TokenManager.getStudentToken();
        if (student == null)
            return ResponesUtil.systemError("目前没有登录用户！");
        JSONObject respData = studentService.toLphReqJSONData(student);
        return ResponesUtil.success("success",respData);
    }

    /**
     * 修改用户信息
     * @param map
     * @return
     */
    @PostMapping("/user")
    public MessageVo updateUser(@RequestParam Map<String , String > map){
        StudentEntity student = TokenManager.getStudentToken();
        if (student == null)
            return ResponesUtil.systemError("用户未登录");
        try {
            studentService.updateStudentByMap(map,student.getId());
        }catch (Exception e){
            return ResponesUtil.systemError("更新出错 出错信息："+e.getMessage());
        }
        return ResponesUtil.success("success");
    }

    /**
     * 获取ddl
     * @return
     */
    @GetMapping("/deadline")
    public MessageVo deadline(){
        return ResponesUtil.success("success", ConstPool.DDL);
    }

    /**
     * 获取试用期内作业完成情况
     * @return
     */
    @GetMapping("/work")
    public MessageVo work(){
        StudentEntity student = TokenManager.getStudentToken();
        if (student == null)
            return ResponesUtil.systemError("当前没有登录用户");
        ApplicationEntity application = taskService.getAppsBySid(student.getStudent_id());
        JSONArray respData  = new JSONArray();
        //根据intention和用户信息获取作业信息
        String intention = application.getIntention();
        if (intention != null){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("intention",intention);
            jsonObject.put("works",taskService.getWorkListByIntentionAndLocalSid(intention,student.getId()));
            respData.add(jsonObject);
        }
        String intention2 = application.getIntention2();
        if (intention2 != null){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("intention",intention2);
            jsonObject.put("works",taskService.getWorkListByIntentionAndLocalSid(intention2,student.getId()));
            respData.add(jsonObject);
        }
        return ResponesUtil.success("success",respData);
    }


}
