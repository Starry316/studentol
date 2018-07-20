package cn.xuzilin.common.controller;

import cn.xuzilin.common.consts.ConstPool;
import cn.xuzilin.common.po.ApplicationEntity;
import cn.xuzilin.common.po.StudentEntity;
import cn.xuzilin.common.service.ApplicationService;
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
    @Resource
    private ApplicationService applicationService;

    /**
     * 登录
     * @param password
     * @param studentId
     * @return
     */
    @PostMapping("/api/v1/login")
    public MessageVo login(@RequestParam("password")String password,
                            @RequestParam("stu_no")String studentId){
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
    @PostMapping("/api/v1/logout")
    public MessageVo logout(){
        if (TokenManager.getStudentToken() != null)
            TokenManager.studentLogout();
        return ResponesUtil.success("success");
    }

    /**
     * 获取用户信息
     * @return 当前登录的用户信息
     */
    @GetMapping("/api/v1/user")
    public MessageVo user(){
        StudentEntity student = TokenManager.getStudentToken();

        if (student == null)
            return ResponesUtil.systemError("目前没有登录用户！");
        student = studentService.getById(student.getId());
        JSONObject respData = studentService.toLphReqJSONData(student);
        return ResponesUtil.success("success",respData);
    }

    /**
     * 修改用户信息
     * @param map
     * @return
     */
    @PostMapping("/api/v1/user")
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
    @GetMapping("/api/v1/deadline")
    public MessageVo deadline(){
        return ResponesUtil.success("success", ConstPool.DDL);
    }

    /**
     * 获取试用期内作业完成情况
     * @return
     */
    @GetMapping("/api/v1/work")
    public MessageVo work(){
        JSONArray respData;
        try{
            respData =taskService.getWork();
        }catch (Exception e){
            return ResponesUtil.systemError(e.getMessage());
        }
        return ResponesUtil.success("success",respData);
    }

    @GetMapping("/api/v1/form")
    public MessageVo getForm(){
        StudentEntity student = TokenManager.getStudentToken();
        if (student == null)
            return ResponesUtil.systemError("登录信息失效，请重新登录");
        ApplicationEntity application = applicationService.getBySno(student.getStudent_id());
        JSONObject respData = new JSONObject();
        respData.put("intention",application.getIntention());
        respData.put("intention2",application.getIntention2());
        respData.put("introduction",application.getIntroduction());
        return ResponesUtil.success("success",respData);
    }


}
