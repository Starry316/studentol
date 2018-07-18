package cn.xuzilin.common.controller;

import cn.xuzilin.common.po.TaskEntity;
import cn.xuzilin.common.po.TaskScoreEntity;
import cn.xuzilin.common.service.TaskService;
import cn.xuzilin.common.utils.ResponesUtil;
import cn.xuzilin.common.vo.MessageVo;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    @PostMapping("/score")
    public MessageVo score(@RequestParam Map<String,String> map){
        String department = map.get("department");
        String scores = map.get("scores");
        JSONArray socreArray = JSON.parseArray(scores);
        for (int i = 0 ;i < socreArray.size(); i++){
            JSONObject score = socreArray.getJSONObject(i);
            int sid = score.getInteger("id");
            List<TaskScoreEntity> tsList = taskService.getTaskScoreByIntentionAndlocalSid(department,sid);
            for (int j = 0;j<4;j++){
                Integer value = score.getInteger(j+1+"");
                if (value!=null){
                    TaskScoreEntity taskScore = tsList.get(j);
                    taskScore.setScore(value);
                    taskService.updateTaskScore(taskScore);
                }
            }
        }
        return ResponesUtil.success("success","success");
    }
}
