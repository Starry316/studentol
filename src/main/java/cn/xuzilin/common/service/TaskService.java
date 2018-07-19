package cn.xuzilin.common.service;

import cn.xuzilin.common.dao.ApplicationEntityMapper;
import cn.xuzilin.common.dao.TaskScoreEntityMapper;
import cn.xuzilin.common.po.ApplicationEntity;
import cn.xuzilin.common.po.StudentEntity;
import cn.xuzilin.common.po.TaskScoreEntity;
import cn.xuzilin.core.shiro.token.TokenManager;
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
    ApplicationEntityMapper applicationMapper;

    public ApplicationEntity getAppsBySid(String sid){
        return applicationMapper.selectBySid(sid);
    }

    /**
     * 获取作业信息
     * 妈耶我为什么接了这么麻烦的锅
     * @return
     */
    public JSONArray getWork(){
        StudentEntity student = TokenManager.getStudentToken();
        List<TaskScoreEntity> tslist= taskScoreMapper.selectBySid(student.getId());
        JSONArray jsonArray = new JSONArray();
        if (tslist.size()==0)
            return null;
        //报了一个部门
        if (tslist.size()==1){
            TaskScoreEntity i = tslist.get(0);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("intention",i.getGroup());
            String scores = this.transferScore(i);
            jsonObject.put("works",scores);
            jsonArray.add(jsonObject);
            return jsonArray;
        }
        TaskScoreEntity i = tslist.get(0);
        TaskScoreEntity o = tslist.get(1);
        //相同事业群
        if ((i.getGroup()/10)==(o.getGroup()/10)){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("intention",i.getGroup()+","+o.getGroup());
            String scores = transferScore(i);
            jsonObject.put("works",scores);
            jsonArray.add(jsonObject);
            return jsonArray;
        }
        //两个部门不同事业群
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("intention",i.getGroup());
        jsonObject.put("works",transferScore(i));
        jsonArray.add(jsonObject);
        jsonObject = new JSONObject();
        jsonObject.put("intention",o.getGroup());
        jsonObject.put("works",transferScore(o));
        jsonArray.add(jsonObject);
        return jsonArray;
    }
    public String transferScore(TaskScoreEntity taskScore){
        String scores = "";
        if (taskScore.getScore1()>=0)
            scores+=taskScore.getScore1();
        scores+=",";
        if (taskScore.getScore2()>=0)
            scores+=taskScore.getScore2();
        scores+=",";
        if (taskScore.getScore3()>=0)
            scores+=taskScore.getScore3();
        scores+=",";
        if (taskScore.getScore4()>=0)
            scores+=taskScore.getScore4();
        return scores;

    }
    public void updateTaskScore(TaskScoreEntity taskScore){
        taskScoreMapper.updateByPrimaryKeySelective(taskScore);
    }
}
