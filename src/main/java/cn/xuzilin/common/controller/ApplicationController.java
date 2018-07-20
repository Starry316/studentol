package cn.xuzilin.common.controller;

import cn.xuzilin.common.po.ApplicationEntity;
import cn.xuzilin.common.po.StudentEntity;
import cn.xuzilin.common.po.TaskScoreEntity;
import cn.xuzilin.common.service.ApplicationService;
import cn.xuzilin.common.service.TaskService;
import cn.xuzilin.common.utils.ResponesUtil;
import cn.xuzilin.common.vo.MessageVo;
import cn.xuzilin.core.shiro.token.TokenManager;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

@RestController
public class ApplicationController {

    @Resource
    private ApplicationService ApplicationService;

    @Resource
    private TaskService taskService;
    @PostMapping("/api/v1/form")
    public MessageVo form(@RequestBody Map<String,String> map){
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
        ApplicationService.SignUp(application);
        return ResponesUtil.success("success");
    }

    @GetMapping("/api/v1/process")
    public MessageVo process(){
        StudentEntity student = TokenManager.getStudentToken();
        ApplicationEntity application = ApplicationService.ReturnData(student);
        JSONArray jsonObject = new JSONArray();
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("intention",application.getIntention());
        jsonObject1.put("stage",application.getStage());
        JSONObject jsonObject2 = new JSONObject();
        jsonObject2.put("intention",application.getIntention2());
        jsonObject2.put("stage",application.getStage2());
        jsonObject.add(jsonObject1);
        jsonObject.add(jsonObject2);
        return ResponesUtil.success("success",jsonObject);
    }

    @GetMapping("/nxms/info/get")
    public MessageVo info_get(@RequestParam Map<String,String> map){
        if (TokenManager.get("manager")==null)
            return ResponesUtil.systemError("登录信息失效!");
        List<String> list = ApplicationService.ReturnByCampus(map.get("campus"));
        List<String> student_ids = new ArrayList<>();
        JSONArray jsonArray = new JSONArray();
        for (String student_id : list){
            student_ids.add(ApplicationService.ReturnId(student_id,map.get("department"),map.get("stage")));
        }
        for (String student_id : student_ids){
            StudentEntity studentEntity = ApplicationService.StudentInfo(student_id);
            ApplicationEntity applicationEntity = ApplicationService.ApplicationInfo(student_id);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id",studentEntity.getId());
            jsonObject.put("name",studentEntity.getStudent_name());
            jsonObject.put("sex",studentEntity.getSex());
            jsonObject.put("stu_no",studentEntity.getStudent_id());
            jsonObject.put("campus",studentEntity.getCampus());
            jsonObject.put("academy",studentEntity.getAcademy());
            jsonObject.put("from",studentEntity.getFrom());
            String[] intentions = new String[2];
            intentions[0]=applicationEntity.getIntention();
            intentions[1]=applicationEntity.getIntention2();
            jsonObject.put("department",intentions);
            jsonObject.put("tel",studentEntity.getPhone_number());
            jsonObject.put("qq",studentEntity.getQq());
            String[] stages = new String[2];
            stages[0] = applicationEntity.getStage();
            stages[1] = applicationEntity.getStage2();
            jsonObject.put("stage",stages);
            jsonObject.put("introduction",applicationEntity.getIntroduction());
            jsonArray.add(jsonObject);
        }
        return ResponesUtil.success("success",jsonArray);
    }

    @PostMapping("/nxms/info/manage")
    public  MessageVo info_manage(@RequestParam Map<String,String> map){
        if (TokenManager.get("manager")==null)
            return ResponesUtil.systemError("登录信息失效!");
        //通过面试后创建task空记录
        int sid = Integer.parseInt(map.get("id"));
        int type = Integer.parseInt(map.get("type"));
        int department = Integer.parseInt("department");
        if (type == 2){
            taskService.create(sid,department);
        }
        //TODO 这里有问题需要修改
        ApplicationService.ChangeStage(map.get("id"),map.get("type"),map.get("department"));
        return ResponesUtil.success("success");
    }

    @PostMapping("/nxms/info/add")
    public MessageVo info_add(@RequestParam Map<String,String> map){
        if (TokenManager.get("manager")==null)
            return ResponesUtil.systemError("登录信息失效!");
        StudentEntity studentEntity = new StudentEntity();
        ApplicationEntity applicationEntity = new ApplicationEntity();
        studentEntity.setStudent_name(map.get("name"));
        System.out.println(map.get("name"));
        studentEntity.setSex(map.get("sex"));
        studentEntity.setStudent_id(map.get("stu_no"));
        studentEntity.setCampus(map.get("campus"));
        studentEntity.setAcademy(map.get("academy"));
        studentEntity.setFrom(map.get("from"));
        studentEntity.setPhone_number(map.get("tel"));
        studentEntity.setQq(map.get("qq"));
        applicationEntity.setStudent_id(map.get("stu_no"));
        applicationEntity.setIntention(map.get("department1"));
        applicationEntity.setIntention(map.get("department2"));
        applicationEntity.setIntroduction(map.get("introduction"));
        Date date = new Date();
        applicationEntity.setSign_time(date);
        applicationEntity.setStage("1");
        applicationEntity.setStage2("1");
        int i = ApplicationService.IsStudentExist(studentEntity,studentEntity.getStudent_id());
        int j = ApplicationService.IsSignUp(applicationEntity,applicationEntity.getStudent_id());
        if (i ==0&&j==0)
            return ResponesUtil.success("该学生已注册，已报名");
        else if (i==0&&j==1)
            return ResponesUtil.success("该学生已注册，报名成功");
        else if (i==1&&j==0)
            return ResponesUtil.success("该学生注册成功，已报名");
        else
            return ResponesUtil.success("该学生注册成功，报名成功");
    }

}
