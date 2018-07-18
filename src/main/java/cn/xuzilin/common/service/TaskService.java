package cn.xuzilin.common.service;

import cn.xuzilin.common.dao.ApplicationEntityMapper;
import cn.xuzilin.common.dao.TaskEntityMapper;
import cn.xuzilin.common.dao.TaskScoreEntityMapper;
import cn.xuzilin.common.po.ApplicationEntity;
import cn.xuzilin.common.po.TaskEntity;
import cn.xuzilin.common.po.TaskScoreEntity;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Starry on 2018/7/18.
 */
@Service
public class TaskService {
    @Resource
    TaskScoreEntityMapper taskScoreMapper;
    @Resource
    TaskEntityMapper taskMapper;
    @Resource
    ApplicationEntityMapper applicationMapper;

    public ApplicationEntity getAppsBySid(String sid){
        return applicationMapper.selectBySid(sid);
    }

    public JSONArray getWorkListByIntentionAndLocalSid(String intention, int localSid){
        List<TaskEntity> tList = taskMapper.selectByIntention(intention);
        JSONArray jsonArray = new JSONArray();
        for (TaskEntity i: tList){
            TaskScoreEntity taskScore = taskScoreMapper.selectByTidAndLocalSid(i.getId(),localSid);
            if (taskScore != null){
                JSONObject object = new JSONObject();
                object.put("score",taskScore.getScore());
                object.put("workName",i.getTask_name());
                jsonArray.add(object);
            }
        }
        return jsonArray;
    }
}
