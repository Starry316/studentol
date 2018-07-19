package cn.xuzilin.common.controller;

import cn.xuzilin.common.consts.ConstPool;
import cn.xuzilin.common.po.StudentEntity;
import cn.xuzilin.common.po.TaskScoreEntity;
import cn.xuzilin.common.service.StudentService;
import cn.xuzilin.common.service.TaskService;
import cn.xuzilin.common.utils.ResponesUtil;
import cn.xuzilin.common.vo.MessageVo;
import cn.xuzilin.core.shiro.token.TokenManager;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by Starry on 2018/7/18.
 */
@RestController
public class BackController {
    @Resource
    private TaskService taskService;
    @Resource
    private StudentService studentService;
    @PostMapping("/nxms/score")
    public MessageVo score(@RequestParam Map<String,String> map){
        if (TokenManager.get("manager")==null)
            return ResponesUtil.systemError("登录信息失效!");
        int group = Integer.parseInt(map.get("group"));
        String scores = map.get("scores");
        JSONArray socreArray = JSON.parseArray(scores);
        for (int i=0;i<socreArray.size();i++){
            JSONObject jsonObject = socreArray.getJSONObject(i);
            int id = jsonObject.getInteger("id");
            Integer s1 = jsonObject.getInteger("1");
            Integer s2 = jsonObject.getInteger("2");
            Integer s3 = jsonObject.getInteger("3");
            Integer s4 = jsonObject.getInteger("4");
            List<TaskScoreEntity> tskList = taskService.getBySidGroup(id,group);
            for (TaskScoreEntity  tsk:tskList){
                if (s1!=null) tsk.setScore1(s1);
                if (s2!=null) tsk.setScore1(s2);
                if (s3!=null) tsk.setScore1(s3);
                if (s4!=null) tsk.setScore1(s4);
                taskService.updateTaskScore(tsk);
            }
        }
        return ResponesUtil.success("success","success");
    }
    @GetMapping("/nxms/score")
    public MessageVo getScore(@RequestParam("campus")int campus,@RequestParam("group")int group){
        if (TokenManager.get("manager")==null)
            return ResponesUtil.systemError("登录信息失效!");
        JSONArray respData = new JSONArray();
        List<TaskScoreEntity> tsklist = taskService.getByGroup(group);
        for (TaskScoreEntity tsk : tsklist){
            StudentEntity student = studentService.getBySidCompus(tsk.getSid(),campus);
            if (student!=null){
                JSONObject data = new JSONObject();
                data.put("id",student.getId());
                data.put("name",student.getStudent_name());
                data.put("1",tsk.getScore1());
                data.put("2",tsk.getScore2());
                data.put("3",tsk.getScore3());
                data.put("4",tsk.getScore4());
                respData.add(data);
            }
        }
        return ResponesUtil.success("success",respData);
    }
    @PostMapping("/nxms/login")
    public MessageVo login(@RequestParam("token") String token){
        if (token == ConstPool.TOKEN){
            TokenManager.save("manager",token);
            return ResponesUtil.success("success","success");
        }
        return ResponesUtil.systemError("fail","fail");
    }
}
